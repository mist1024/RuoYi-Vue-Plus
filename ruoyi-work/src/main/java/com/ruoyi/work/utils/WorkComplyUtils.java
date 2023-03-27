package com.ruoyi.work.utils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.BeanCopyUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.work.domain.*;
import com.ruoyi.work.domain.vo.ActProcessVo;
import com.ruoyi.work.domain.vo.HisProcessVo;
import com.ruoyi.work.domain.vo.ProcessVo;
import com.ruoyi.work.dto.BusinessDTO;
import com.ruoyi.work.dto.HisProcessVoResultDto;
import com.ruoyi.work.dto.ProcessVoResultDto;
import com.ruoyi.work.mapper.*;
import com.ruoyi.work.service.IWorkSysDeptService;
import com.ruoyi.work.service.IWorkSysRoleService;
import com.ruoyi.work.service.IWorkSysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程相关工具类
 */
@Component
@RequiredArgsConstructor
@Transactional
public class WorkComplyUtils {

    private static final ProcessMapper processMapper = SpringUtils.getBean(ProcessMapper.class);
    private static final ActProcessMapper actProcessMapper = SpringUtils.getBean(ActProcessMapper.class);
    private static final HisProcessMapper hisProcessMapper = SpringUtils.getBean(HisProcessMapper.class);

    private static final IWorkSysRoleService sysRoleMapper= SpringUtils.getBean(IWorkSysRoleService.class);

    private static final RollBackLogMapper rollBackLogMapper= SpringUtils.getBean(RollBackLogMapper.class);

    private static final IWorkSysUserService sysUserMapper = SpringUtils.getBean(IWorkSysUserService.class);

    private static final IWorkSysDeptService sysDeptMapper = SpringUtils.getBean(IWorkSysDeptService.class);

    private static final AuditLogMapper auditLogMapper  =SpringUtils.getBean(AuditLogMapper.class);


    /**
     * 任务创建
     * @param processVo
     */
    public  static   void comply(ProcessVo processVo){
        ActProcess actProcessVo = new ActProcess();
        LambdaQueryWrapper<TProcess> lqw = Wrappers.lambdaQuery();
        lqw.eq( TProcess::getProcessKey, processVo.getProcessKey());
        lqw.eq(TProcess::getStep, processVo.getStep());

        //判断这条业务是否在进行中
        LambdaQueryWrapper<ActProcess> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActProcess::getBusinessId,processVo.getBusinessId());
        List<ActProcessVo> actProcessVos = actProcessMapper.selectVoList(queryWrapper);
        if (actProcessVos.size()>0){
            throw new ServiceException("当前业务正在审核中,请耐心等待");
        }
        ProcessVo tProcessByKey = processMapper.selectVoOne(lqw);
        actProcessVo.setCreateTime(DateUtils.getNowDate());
        actProcessVo.setCheckType(tProcessByKey.getCheckType());
        actProcessVo.setStep(tProcessByKey.getStep());
        actProcessVo.setStartUser(processVo.getStartUser());
        actProcessVo.setProcessId(tProcessByKey.getId().toString());
        actProcessVo.setBusinessId(processVo.getBusinessId());
        actProcessVo.setIsNext(tProcessByKey.getIsNext());
        if ("1".equals(tProcessByKey.getType())){//普通流程,只需要将人员设置到运行流程中即可
            actProcessVo.setType(tProcessByKey.getType());
            if (1L==tProcessByKey.getProcessCheck()) {
                if("4".equals(tProcessByKey.getCheckType())){//企业审核
                    String audit = processVo.getParams().get(tProcessByKey.getParam()).toString();
                    actProcessVo.setAudit(audit);
                }else {
                    actProcessVo.setAudit(tProcessByKey.getAudit());
                }
            }else if (2==tProcessByKey.getProcessCheck()){
                // 根据接口获取
                List<String> personByRule = WorkUtils.getPersonByRule(tProcessByKey.getRuleId(),processVo.getParams());
                actProcessVo.setAudit(personByRule.get(0));
            }
            actProcessMapper.insert(actProcessVo);
        }else if ("2".equals(tProcessByKey.getType())){//会签
            actProcessVo.setType(tProcessByKey.getType());
            //将check以逗号拆分
            List<ActProcess> list = new ArrayList<>();
            if (1L==tProcessByKey.getProcessCheck()) {
                String[] split = tProcessByKey.getAudit().split(",");
                for (String s : split) {
                    ActProcess actProcess = new ActProcess();
                    BeanCopyUtils.copy(actProcessVo,actProcess);
                    actProcess.setAudit(s);
                    list.add(actProcess);
                }
            }else if (2L==tProcessByKey.getProcessCheck()){
                //根据接口获取
                ArrayList<String> personByRule = WorkUtils.getPersonByRule(tProcessByKey.getRuleId(),processVo.getParams());
                //将某些固定的审核人员添加进去
                if (ObjectUtil.isNotEmpty(tProcessByKey.getAudit())){
                    String[] split = tProcessByKey.getAudit().split(",");
                    Collections.addAll(personByRule,split);
                }
                for (String s : personByRule) {
                    ActProcess actProcess = new ActProcess();
                    BeanUtil.copyProperties(actProcessVo,actProcess);
                    actProcessVo.setAudit(s);
                    list.add(actProcess);
                }
            }
            boolean b = actProcessMapper.insertBatch(list);
            System.out.println("b = " + b);
        }
    }

    /**
     * 业务办理
     * wait,等待,success成功,fail失败
     * 状态:1已提交待审核,2审核成功,3失败
     * 1.往hisProcess添加数据
     * 2.根据businessId删除actProcess
     */

    public static  String batchDeleted(HisProcess hisProcess){
        hisProcess.setEndTime(DateUtils.getNowDate());
        //获取当前购房信息
        //1.得到当前这条运行任务
        LambdaQueryWrapper<ActProcess> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ActProcess::getBusinessId,hisProcess.getBusinessId());
        List<ActProcessVo> actProcessVos = actProcessMapper.selectVoList(lqw);
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (actProcessVos.size()>0){
            //根据审核人员类型判断流程中那条数据属于当前人的
            String checkType = actProcessVos.get(0).getCheckType();
            String  audit ="";
            if ("1".equals(checkType) ||  "4".equals(checkType)){
                audit=loginUser.getUserId().toString();
            }else if ("2".equals(checkType)){
                audit = loginUser.getDeptId().toString();
            }else if ("3".equals(checkType)){
                audit = loginUser.getRoleId().toString();
            }
            String finalAudit = audit;
            List<ActProcessVo> collect = actProcessVos.stream().filter(e -> checkType.equals(e.getCheckType()) && finalAudit.equals(e.getAudit())).collect(Collectors.toList());
            if (collect.size()>0) {
                ActProcessVo actProcessVo = collect.get(0);
                hisProcess.setCheckType(actProcessVo.getCheckType());
                //往历史表里面添加当前数据
                hisProcess.setProcessId(actProcessVo.getProcessId());
                hisProcess.setCreateTime(actProcessVo.getCreateTime());
                hisProcess.setStep(actProcessVo.getStep());
                hisProcess.setType(actProcessVo.getType());
                hisProcess.setStartUser(actProcessVo.getStartUser());
                if ("1".equals(actProcessVo.getCheckType())) {//人员id
                    Long userId = loginUser.getUserId();
                    hisProcess.setAudit(ObjectUtil.isNotEmpty(userId) ? userId.toString() : "");
                } else if ("2".equals(actProcessVo.getCheckType())) {//部门
                    Long deptId = loginUser.getDeptId();
                    hisProcess.setAudit(ObjectUtil.isNotEmpty(deptId) ? deptId.toString() : "");
                } else if ("3".equals(actProcessVo.getCheckType())) {//角色
                    Long roleId = loginUser.getRoleId();
                    hisProcess.setAudit(ObjectUtil.isNotEmpty(roleId) ? roleId.toString() : "");
                } else if ("4".equals(actProcessVo.getCheckType())) {//企业
                    Long userId = loginUser.getUserId();
                    hisProcess.setCompanyName(hisProcess.getParams().get("companyName").toString());
                    hisProcess.setAudit(ObjectUtil.isNotEmpty(userId) ? userId.toString() : "");
                }
                //先添加一个属于当前用户的记录
                hisProcessMapper.insert(hisProcess);

                //todo 保存一条审核日志
                AuditLog auditLog = new AuditLog();
                auditLog.setOtherId(hisProcess.getBusinessId());//业务id
                auditLog.setProcessKey(hisProcess.getProcessKey());//流程key
                auditLog.setAuditId(loginUser.getUserId().toString());//审核人id
                auditLog.setAuditType(checkType);//审核类型
                auditLog.setStatus(hisProcess.getStatus());//审核状态
                auditLog.setReply(hisProcess.getReply());//原因
                auditLog.setAudit(hisProcess.getAudit());
                auditLog.setAdminUserName(loginUser.getUsername());
                auditLog.setStep(actProcessVo.getStep());
                auditLogMapper.insert(auditLog);
                //如果成功就删除当前条0
                if ("1".equals(hisProcess.getStatus())) {//失败删除全部当前业务相关数据
                    //删除当前业务的数据
                    List<Long> collect1 = actProcessVos.stream().map(ActProcessVo::getId).collect(Collectors.toList());
                    actProcessMapper.deleteBatchIds(collect1);
                    return Constants.FAILD;
                } else if ("2".equals(hisProcess.getStatus())) {//审核成功
                    //修改业务表的状态,0保持,1提交,审核中,2成功,3失败
                    //判断是否只剩最后一条记录
                    //删除当前运行数据
                    actProcessMapper.deleteById(actProcessVo.getId());
                    if (actProcessVos.size() == 1) {//只存在当前流程的最后一条业务
                        //判断是否还有下一步
                        if (actProcessVo.getIsNext()) {//存在下一级
                            ProcessVo processVo = new ProcessVo();
                            processVo.setProcessKey(hisProcess.getProcessKey());
                            Integer step = new Integer(actProcessVo.getStep()) + 1;
                            processVo.setStep(step.toString());
                            processVo.setParams(hisProcess.getParams());
                            processVo.setBusinessId(hisProcess.getBusinessId());
                            processVo.setStartUser(actProcessVo.getStartUser());
                            comply(processVo);
                            return Constants.WAIT;
                        } else {
                            //业务办结
                            return Constants.SUCCEED;
                        }
                    }
                    return Constants.WAIT;
                }
            }else {
                throw new ServiceException("当前不具备审核资格");
            }
        }
        return Constants.NONENTITY;
    }

    /**
     * 业务撤回
     * 后台人员执行撤销,可以撤销到任何地步
     */
    public static int rollBack(RollBackLog rollBackLog){
        Long userId = LoginHelper.getUserId();
        if (ObjectUtil.isNull(userId)){
            throw new ServiceException("请先登录");
        }
        //获取到历史表中的需要回退到那一步的最新数据
        LambdaQueryWrapper<HisProcess> lqw = new LambdaQueryWrapper<HisProcess>()
            .eq(HisProcess::getStep,rollBackLog.getStep())
            .eq(HisProcess::getBusinessId,rollBackLog.getBusinessId())
            .eq(HisProcess::getCheckType,rollBackLog.getCheckType())
            .eq(HisProcess::getAudit,rollBackLog.getAudit())
            .orderByDesc(HisProcess::getEndTime);
        List<HisProcessVo> hisProcessVos = hisProcessMapper.selectVoList(lqw);
        String param ="";
        //获取当前流程的数据
        LambdaQueryWrapper<ActProcess> eq = new LambdaQueryWrapper<ActProcess>()
            .eq(ObjectUtil.isNotEmpty(rollBackLog.getBusinessId()), ActProcess::getBusinessId, rollBackLog.getBusinessId());
        ActProcess actProcesses = actProcessMapper.selectList(eq).get(0);
        //如果选择回退的步骤在历史表之前,将流程表中的流程替换为当前历史表中的数据
        if (!rollBackLog.getStep().equals(actProcesses.getStep())){
            LambdaQueryWrapper<ActProcess> eq1 = new LambdaQueryWrapper<ActProcess>()
                .eq(ObjectUtil.isNotNull(rollBackLog.getBusinessId()), ActProcess::getBusinessId, rollBackLog.getBusinessId());
            List<ActProcess> actProcesses1 = actProcessMapper.selectList(eq1);
            List<Long> collect = actProcesses1.stream().map(ActProcess::getId).collect(Collectors.toList());
            param = JsonUtils.toJsonString(actProcesses1);
            actProcessMapper.deleteBatchIds(collect);
        }else {
            param = JsonUtils.toJsonString(hisProcessVos.get(0));
        }
        //如果回退的是当前流程中的步骤(会签),当前会签流程已走了一半,将流程历史表中的记录返回到流程表中
        HisProcessVo hisProcessVo = hisProcessVos.get(0);
        ActProcess actProcess = new ActProcess();
        actProcess.setIsNext(actProcesses.getIsNext());
        actProcess.setType(hisProcessVo.getType());
        actProcess.setCheckType(hisProcessVo.getCheckType());
        actProcess.setCompanyName(hisProcessVo.getCompanyName());
        actProcess.setCreateTime(hisProcessVo.getCreateTime());
        actProcess.setStep(hisProcessVo.getStep());
        actProcess.setStartUser(hisProcessVo.getStartUser());
        actProcess.setBusinessId(hisProcessVo.getBusinessId());
        actProcess.setAudit(hisProcessVo.getAudit());
        actProcess.setProcessId(hisProcessVo.getProcessId());
        int insert = actProcessMapper.insert(actProcess);
        rollBackLog.setCreateTime(DateUtils.getNowDate());
        rollBackLog.setParam(param);
        rollBackLog.setUpdateBy(userId.toString());
        rollBackLogMapper.insert(rollBackLog);
        return insert;
    }
    /**
     * 申请人业务撤回
     */
    public static void businessRollBack(RollBackLog rollBackLog){
        Long userId = LoginHelper.getUserId();
        if (ObjectUtil.isNull(userId)){
            throw new ServiceException("请先登录");
        }
        //判断当前业务是否可以撤销(如果正在运行表中已无业务id则该条业务已完成)
        LambdaQueryWrapper<ActProcess> eq = new LambdaQueryWrapper<ActProcess>()
            .eq(ActProcess::getBusinessId, rollBackLog.getBusinessId());
        List<ActProcess> actProcesses = actProcessMapper.selectList(eq);
        if (actProcesses.size()==0){
            throw new ServiceException("当前业务不允许撤销");
        }
        //创建一条撤销记录,
        String param = JsonUtils.toJsonString(actProcesses);
        rollBackLog.setCreateTime(DateUtils.getNowDate());
        rollBackLog.setParam(param);
        rollBackLog.setType("1");
        rollBackLog.setUpdateBy(userId.toString());
        rollBackLogMapper.insert(rollBackLog);
        //删除正在运行的流程记录
        List<Long> collect = actProcesses.stream().map(ActProcess::getId).collect(Collectors.toList());
        actProcessMapper.deleteBatchIds(collect);
    }

    /**获取已办步骤
     * 获取可以撤销的步骤以及人员,需要判断是后台还是申请人
     * 先判断这条流程走到那一步了,可以撤回到那几步
     */

    public static List<HisProcessVoResultDto> getStep(BusinessDTO businessDTO){
        //获取当前任务执行到那一步了
        LambdaQueryWrapper<ActProcess> lwq = new LambdaQueryWrapper<>();
        lwq.eq(ActProcess::getBusinessId,businessDTO.getBusinessId());
        List<ActProcessVo> actProcessVos = actProcessMapper.selectVoList(lwq);
        if (actProcessVos.size()==0){
            throw new ServiceException("当前任务已完结");
        }else {
            ActProcessVo actProcessVo = actProcessVos.get(0);
            String step = actProcessVo.getStep();
            //如果当前业务就是在第一步,则不返回数据
            if (!"1".equals(step)){
                //获取历史表中在该业务流程步骤之前的人员数据,根据业务表的更新时间为节点
                Object updateTime = businessDTO.getParams().get("updateTime");
                if (ObjectUtil.isNull(updateTime)){
                    throw new ServiceException("updateTime不可为空");
                }
                QueryWrapper<HisProcess> wrapper = new QueryWrapper<>();
                wrapper.eq("business_id",businessDTO.getBusinessId());
                wrapper.ge("h.step","1");
                wrapper.le("h.step",step);
                wrapper.ge("h.create_time",updateTime);
                wrapper.orderByAsc("h.step");
                List<HisProcess> hisProcessVos = hisProcessMapper.selectVoHisList(wrapper);
                for (HisProcess hisProcessVo : hisProcessVos) {
                    if ("4".equals(hisProcessVo.getCheckType())) {//企业审核
                        hisProcessVo.setAudit1(hisProcessVo.getCompanyName());
                    } else if ("1".equals(hisProcessVo.getCheckType())) {
                        //根据id获取人员信息
                        SysUser sysUser = sysUserMapper.selectUserById(new Long(hisProcessVo.getAudit()));
                        hisProcessVo.setAudit1(sysUser.getUserName());
                    } else if ("2".equals(hisProcessVo.getCheckType())) {
                        SysDept sysDept = sysDeptMapper.selectDeptById(new Long(hisProcessVo.getAudit()));
                        hisProcessVo.setAudit1(sysDept.getDeptName());
                    } else if ("3".equals(hisProcessVo.getCheckType())) {
                        SysRole sysRole = sysRoleMapper.selectRoleById(new Long(hisProcessVo.getAudit()));
                        hisProcessVo.setAudit1(sysRole.getRoleName());
                    }
                }

                //将每个步骤处理为单个对象
                Map<String, List<HisProcess>> collect = hisProcessVos.stream().collect(Collectors.groupingBy(HisProcess::getStep));
                List<HisProcessVoResultDto> collect1 = collect.entrySet()
                    .stream()
                    .map(e -> new HisProcessVoResultDto(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());

                collect1.forEach(e ->{
                    String s = e.getHisProcessVoList().stream().map(HisProcess::getDescription).collect(Collectors.toList()).get(0);
                    e.setStep(s);
                });
                System.out.println("collect = " + collect1);
                return collect1;
            }
        }
        return null;

    }

    /**
     * 获取已办任务
     */
    public static TableDataInfo<HisProcess> getHistoryList(HisProcess hisProcess,PageQuery pageQuery){
        LoginUser loginUser = LoginHelper.getLoginUser();
        hisProcess.setUserId(ObjectUtil.isNotNull(loginUser.getUserId())?loginUser.getUserId().toString():"");
        hisProcess.setRoleId(ObjectUtil.isNotNull(loginUser.getRoleId())?loginUser.getRoleId().toString():"");
        hisProcess.setDeptId(ObjectUtil.isNotNull(loginUser.getDeptId())?loginUser.getDeptId().toString():"");
        if ("4".equals(hisProcess.getCheckType())){
            hisProcess.setCompanyUserId(ObjectUtil.isNotNull(loginUser.getUserId())?loginUser.getUserId().toString():"");
        }
        Page<HisProcess> hisProcessPage = hisProcessMapper.selectVoListPage(pageQuery.build(), hisProcess);
        List<HisProcess> records = hisProcessPage.getRecords();
        for (HisProcess record : records) {
            if("4".equals(record.getCheckType())){//企业审核
                record.setAudit(record.getCompanyName());
            }else if("1".equals(record.getCheckType())) {
                //根据id获取人员信息
                SysUser sysUser = sysUserMapper.selectUserById(new Long( record.getAudit()));
                record.setAudit(sysUser.getUserName());
            }else if ("2".equals(record.getCheckType())){
                SysDept sysDept = sysDeptMapper.selectDeptById(new Long(record.getAudit()));
                record.setAudit(sysDept.getDeptName());
            }else if ("3".equals(record.getCheckType())){
                SysRole sysRole = sysRoleMapper.selectRoleById(new Long(record.getAudit()));
                record.setAudit(sysRole.getRoleName());
            }
            switch (record.getType()){
                case "1" :record.setType("基本类型");
                    break;
                case "2" : record.setType("会签");
                    break;
                default:record.setType("未知类型");
                    break;
            }
            //获取当前业务处理耗时
            Date date1 = record.getCreateTime();
            Date date2 = record.getEndTime();
            //精确到秒
            String formatBetween  = DateUtil.formatBetween(date1, date2, BetweenFormatter.Level.SECOND);
            record.setTimeBetween(formatBetween);
        }
        return TableDataInfo.build(hisProcessPage);
    }

    /**
     * 获取待办业务
     */
    public static TableDataInfo<ActProcess> getWaitTaskList(ActProcess actProcess,PageQuery pageQuery){
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (ObjectUtil.isNull(loginUser.getUserId())){
            throw new ServiceException("请先登录");
        }
        actProcess.setUserId(loginUser.getUserId().toString());
        //获取所有流程中的抄送人员
        //后台查看待审核页面
        if (!"4".equals(actProcess)) {
            Page<ActProcess> actProcessVoCCList = actProcessMapper.selectCCList(pageQuery.build(), actProcess);
            if (actProcessVoCCList.getRecords().size() > 0) {
                for (ActProcess record : actProcessVoCCList.getRecords()) {
                    if ("1".equals(record.getCheckType())){//人员
                        String username = loginUser.getUsername();
                        record.setAudit(username);
                    }
                    if ("2".equals(record.getCheckType())){//部门
                        String deptName = loginUser.getDeptName();
                        record.setAudit(deptName);
                    }
                    if ("3".equals(record.getCheckType())){//角色
                        Long roleId = loginUser.getRoleId();
                        SysRole sysRole = sysRoleMapper.selectRoleById(roleId);
                        record.setAudit(sysRole.getRoleName());
                    }
                    if ("4".equals(record.getCheckType())){//公司
                        record.setAudit(record.getCompanyName());
                    }
                    if (ObjectUtil.isNotEmpty(record.getTimeout())&& ObjectUtil.isNotNull(record.getTimeout())){
                        DateTime beforeTime = DateUtil.offsetDay(record.getCreateTime(), new Integer(record.getTimeout()));
                        Date nowDate = DateUtils.getNowDate();
                        long between = DateUtil.between(nowDate, beforeTime, DateUnit.DAY);
                        if (between<0){
                            long abs = Math.abs(between);
                            record.setTimeout("超时"+abs+"天");
                        }else {
                            record.setTimeout("剩余"+between+"天");
                        }
                    }
                    switch (record.getType()){
                        case "1" :record.setType("基本类型");
                            break;
                        case "2" : record.setType("会签");
                            break;
                        default:record.setType("未知类型");
                            break;
                    }
                }
                return TableDataInfo.build(actProcessVoCCList);
            }
        }

        if ("4".equals(actProcess.getCheckType())){
            actProcess.setCompanyUserId(ObjectUtil.isNotNull(loginUser.getUserId())?loginUser.getUserId().toString():"");
            Page<ActProcess> actProcessVoList = actProcessMapper.selectVoListPage(pageQuery.build(),actProcess);
            List<ActProcess> records = actProcessVoList.getRecords();
            for (ActProcess record : records) {
                if ("4".equals(record.getCheckType())){//公司
                    record.setAudit(record.getCompanyName());
                }
                if (ObjectUtil.isNotEmpty(record.getTimeout())&& ObjectUtil.isNotNull(record.getTimeout())){
                    DateTime beforeTime = DateUtil.offsetDay(record.getCreateTime(), new Integer(record.getTimeout()));
                    Date nowDate = DateUtils.getNowDate();
                    long between = DateUtil.between(nowDate, beforeTime, DateUnit.DAY);
                    if (between<0){
                        long abs = Math.abs(between);
                        record.setTimeout("超时"+abs+"天");
                    }else {
                        record.setTimeout("剩余"+between+"天");
                    }
                }
                switch (record.getType()){
                    case "1" :record.setType("基本类型");
                        break;
                    case "2" : record.setType("会签");
                        break;
                    default:record.setType("未知类型");
                        break;
                }
            }
            return TableDataInfo.build(actProcessVoList);
        }

        actProcess.setUserId(ObjectUtil.isNotNull(loginUser.getUserId())?loginUser.getUserId().toString():"");
        actProcess.setRoleId(ObjectUtil.isNotNull(loginUser.getRoleId())?loginUser.getRoleId().toString():"");
        actProcess.setDeptId(ObjectUtil.isNotNull(loginUser.getDeptId())?loginUser.getDeptId().toString():"");
        Page<ActProcess> actProcessVoList = actProcessMapper.selectVoListPage(pageQuery.build(),actProcess);
        List<ActProcess> records = actProcessVoList.getRecords();
        for (ActProcess record : records) {
            if ("1".equals(record.getCheckType())){//人员
                String username = loginUser.getUsername();
                record.setAudit(username);
            }
            if ("2".equals(record.getCheckType())){//部门
                String deptName = loginUser.getDeptName();
                record.setAudit(deptName);
            }
            if ("3".equals(record.getCheckType())){//角色
                Long roleId = loginUser.getRoleId();
                SysRole sysRole = sysRoleMapper.selectRoleById(roleId);
                record.setAudit(sysRole.getRoleName());
            }
            switch (record.getType()){
                case "1" :record.setType("基本类型");
                    break;
                case "2" : record.setType("会签");
                    break;
                default:record.setType("未知类型");
                    break;
            }
            if (ObjectUtil.isNotEmpty(record.getTimeout())&& ObjectUtil.isNotNull(record.getTimeout())){
                DateTime beforeTime = DateUtil.offsetDay(record.getCreateTime(), new Integer(record.getTimeout()));
                Date nowDate = DateUtils.getNowDate();
                long between = DateUtil.between(nowDate, beforeTime, DateUnit.DAY);
                if (between<0){
                    long abs = Math.abs(between);
                    record.setTimeout("超时"+abs+"天");
                }else {
                    record.setTimeout("剩余"+between+"天");
                }
            }
        }
        return TableDataInfo.build(actProcessVoList);
    }

    /**
     * 获取流程进度
     */

    public static List<ProcessVoResultDto>getProcessPlan(ProcessVo processVo){
        //获取当前业务运行到那一步
        String businessId = processVo.getBusinessId();
        if (ObjectUtil.isNull(businessId)){
            throw new ServiceException("业务id不可为空");
        }

        Map params = processVo.getParams();
        if (ObjectUtil.isEmpty(params)){
            throw new ServiceException("params不可为空");
        }
        LambdaQueryWrapper<TProcess> wrapper = new LambdaQueryWrapper<TProcess>()
            .eq(TProcess::getProcessKey, params.get("processKey"))
            .orderByAsc(TProcess::getStep);
        List<ProcessVo> processVos = processMapper.selectVoList(wrapper);
        List<ProcessVo> list1 = new ArrayList<>();
        processVos.forEach(e ->{
            e.setBusinessId(businessId);
            e.setParams(params);
            //遍历出他每一步所需要的审核人
            if ("1".equals(e.getType())){//普通流程,只需查出将人员设置到运行流程中即可
                if (1L==e.getProcessCheck()) {
                    //获取根据当前审核人的流程给前端判断是否需要点亮操作
                    dd(e);
                    ProcessVo processVo1 = new ProcessVo();
                    BeanCopyUtils.copy(e,processVo1);
                    list1.add(processVo1);
                }else if (2L==e.getProcessCheck()) {
                    // 根据接口获取
                    List<String> personByRule = WorkUtils.getPersonByRule(e.getRuleId(), e.getParams());
                    e.setAudit(personByRule.get(0));
                    dd(e);
                    ProcessVo processVo1 = new ProcessVo();
                    BeanCopyUtils.copy(e,processVo1);
                    list1.add(processVo1);
                }
            }else if ("2".equals(e.getType())) {//会签
                //将check以逗号拆分
                List<ProcessVo> list = new ArrayList<>();
                if (1L == e.getProcessCheck()) {
                    String[] split = e.getAudit().split(",");
                    for (String s : split) {
                        e.setAudit(s);
                        dd(e);
                        ProcessVo processVo1 = new ProcessVo();
                        BeanCopyUtils.copy(e,processVo1);
                        list.add(processVo1);
                    }
                    list1.addAll(list);
                } else if (2L == e.getProcessCheck()) {
                    //根据接口获取
                    List<String> personByRule = WorkUtils.getPersonByRule(e.getRuleId(), e.getParams());
                    //将某些固定的审核人员添加进去
                    if (ObjectUtil.isNotEmpty(e.getAudit())) {
                        String[] split = e.getAudit().split(",");
                        Collections.addAll(personByRule, split);
                    }
                    for (String s : personByRule) {
                        e.setAudit(s);
                        dd(e);
                        ProcessVo processVo1 = new ProcessVo();
                        BeanCopyUtils.copy(e,processVo1);
                        list.add(processVo1);
                    }
                    list1.addAll(list);
                }
            }
        });

        list1.forEach(e ->{
            e.setParams(null);
        });

        Map<String, List<ProcessVo>> collect = list1.stream().collect(Collectors.groupingBy(ProcessVo::getStep));
        List<ProcessVoResultDto> collect1 = collect.entrySet()
            .stream()
            .map(e -> new ProcessVoResultDto(e.getKey(), e.getValue()))
            .collect(Collectors.toList());

        collect1.forEach(e ->{
            String s = e.getProcessVoList().stream().map(ProcessVo::getDescription).collect(Collectors.toList()).get(0);
            e.setStep(s);
        });
        System.out.println("collect = " + collect1);
        return collect1;
    }

    /**
     * 返回审核人公共类
     * @param e
     */
    public static void dd(ProcessVo e){
        LambdaQueryWrapper<ActProcess> queryWrapper = new LambdaQueryWrapper<ActProcess>()
            .eq(ActProcess::getBusinessId, e.getBusinessId());
        List<ActProcessVo> actProcessVoList = actProcessMapper.selectVoList(queryWrapper);

        if (actProcessVoList.size()>0){
            actProcessVoList.forEach(a ->{
                if ( new Integer(e.getStep())< new Integer(a.getStep())){
                    e.setChecked("1");
                }else if ( new Integer(e.getStep()).equals(new Integer(a.getStep()))){
                    e.setChecked("2");
                }else {
                    e.setChecked("3");
                }
            });

        }else {
            Map map = e.getParams();
            Object process_status = map.get("processStatus");
            if (ObjectUtil.isNotNull(process_status)){
                if (Constants.SUCCEED.equals(process_status.toString())){
                    e.setChecked("1");
                }
            }else {
                e.setChecked("3");
            }
        }
        e.setAuditLogList(null);
        LambdaQueryWrapper<AuditLog> wrapper = new LambdaQueryWrapper<AuditLog>()
            .eq(ObjectUtil.isNotNull(e.getProcessKey()) && StringUtils.isNotEmpty(e.getProcessKey()),AuditLog::getProcessKey, e.getProcessKey())
            .eq(AuditLog::getOtherId, e.getBusinessId())
            .orderByAsc(AuditLog::getCreateTime);
        List<AuditLog> auditLogs = auditLogMapper.selectList(wrapper);
        if (auditLogs.size()>0) {
            List<AuditLog> collect = auditLogs.stream().filter(a -> e.getStep().equals(a.getStep()) && e.getAudit().equals(a.getAudit())).collect(Collectors.toList());
            collect.forEach(a -> {
                if (ObjectUtil.isNotNull(a.getStatus())) {
                    switch (a.getStatus()) {
                        case "1":
                            a.setStatus("审核失败");
                            break;
                        case "2":
                            a.setStatus("审核成功");
                            break;
                        default:
                            a.setStatus("未知状态");
                            break;
                    }
                }
            });
            e.setAuditLogList(collect);
        }else {
            e.setAuditLogList(new ArrayList<>());
        }
        if("4".equals(e.getCheckType())){//企业审核
            Map params = e.getParams();
            if (ObjectUtil.isNull(e.getParamName())){
                throw  new ServiceException("param_name不可为空");
            }
            String o = params.get(e.getParamName()).toString();
            e.setAudit(o);
        }else if("1".equals(e.getCheckType())) {
            //根据id获取人员信息
            SysUser sysUser = sysUserMapper.selectUserById(new Long( e.getAudit()));
            e.setAudit(sysUser.getUserName());
        }else if ("2".equals(e.getCheckType())){
            SysDept sysDept = sysDeptMapper.selectDeptById(new Long(e.getAudit()));
            e.setAudit(sysDept.getDeptName());
        }else if ("3".equals(e.getCheckType())){
            SysRole sysRole = sysRoleMapper.selectRoleById(new Long(e.getAudit()));
            e.setAudit(sysRole.getRoleName());
        }
    }

    /**
     * 获取审核日志
     * @param businessDTO
     * @return
     */
    public static R<?> getAuditLogListByOtherId(BusinessDTO businessDTO){
        LambdaQueryWrapper<AuditLog> wrapper = new LambdaQueryWrapper<AuditLog>()
            .eq(StringUtils.isNotEmpty(businessDTO.getProcessKey()),AuditLog::getProcessKey, businessDTO.getProcessKey())
            .eq(AuditLog::getOtherId, businessDTO.getBusinessId());
        List<AuditLog> auditLogs = auditLogMapper.selectList(wrapper);
        return R.ok(auditLogs);
    }

}
