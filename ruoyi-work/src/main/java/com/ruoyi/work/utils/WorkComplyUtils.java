package com.ruoyi.work.utils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.work.domain.*;
import com.ruoyi.work.domain.vo.ActProcessVo;
import com.ruoyi.work.domain.vo.ProcessVo;
import com.ruoyi.work.dto.BusinessDTO;
import com.ruoyi.work.dto.HisProcessVoResultDto;
import com.ruoyi.work.dto.ProcessVoResultDto;
import com.ruoyi.work.mapper.*;
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
@Transactional(rollbackFor = Exception.class)
public class WorkComplyUtils {
    private static final ProcessMapper processMapper = SpringUtils.getBean(ProcessMapper.class);
    private static final ActProcessMapper actProcessMapper = SpringUtils.getBean(ActProcessMapper.class);
    private static final HisProcessMapper hisProcessMapper = SpringUtils.getBean(HisProcessMapper.class);
    private static final RollBackLogMapper rollBackLogMapper= SpringUtils.getBean(RollBackLogMapper.class);
    private static final AuditLogMapper auditLogMapper  =SpringUtils.getBean(AuditLogMapper.class);


    /**
     * 任务创建
     * @param processVo
     */
    public  static  void comply(ProcessVo processVo){
        ActProcess actProcessVo = new ActProcess();
        List<ProcessVo> processVoList = processMapper.selectVoList(new LambdaQueryWrapper<>(TProcess.class)
            .eq(TProcess::getProcessKey, processVo.getProcessKey()));
        //判断这条业务是否在进行中
        List<ActProcessVo> actProcessVos = actProcessMapper.selectVoList(new LambdaQueryWrapper<>(ActProcess.class)
            .eq(ActProcess::getBusinessId,processVo.getBusinessId())
            .in(ActProcess::getProcessId,processVoList.stream().map(ProcessVo::getId).collect(Collectors.toList())));
        if (actProcessVos.size()>0){
            throw new ServiceException("当前业务正在审核中,请耐心等待");
        }
        Map params = processVo.getParams();
        if (ObjectUtil.isNull(params)){
            throw new ServiceException("params不可为空");
        }
        ProcessVo tProcessByKey =processVoList.stream().filter(p ->p.getStep().equals(processVo.getStep())).collect(Collectors.toList()).get(0);
        params.put("step",tProcessByKey.getStep());
        Object companyName = params.get("companyName");
        if (ObjectUtil.isNotNull(companyName)){
            actProcessVo.setCompanyName(String.valueOf(companyName));
        }
        Object cardId = params.get("card");
        Object card = params.get("cardId");

        if (ObjectUtil.isNotNull(cardId)){
            actProcessVo.setCardId(String.valueOf(cardId));
        }
        if (ObjectUtil.isNotNull(card)){
            actProcessVo.setCardId(String.valueOf(card));
        }
        if (ObjectUtil.isNotNull(processVo.getUserId())){
            actProcessVo.setUserId(processVo.getUserId());
        }else {
            actProcessVo.setUserId(String.valueOf(LoginHelper.getUserId()));
        }
        actProcessVo.setCardId(processVo.getCardId());
        actProcessVo.setCompanyName(processVo.getCompanyName());
        actProcessVo.setCreateTime(DateUtils.getNowDate());
        actProcessVo.setCheckType(tProcessByKey.getCheckType());
        actProcessVo.setStep(tProcessByKey.getStep());
        actProcessVo.setStartUser(processVo.getStartUser());
        actProcessVo.setProcessId(tProcessByKey.getId().toString());
        actProcessVo.setBusinessId(processVo.getBusinessId());
        actProcessVo.setDescription(tProcessByKey.getDescription());
        actProcessVo.setIsNext(tProcessByKey.getIsNext());
        if ("1".equals(tProcessByKey.getType())){//普通流程,只需要将人员设置到运行流程中即可
            actProcessVo.setType(tProcessByKey.getType());
            if (1L==tProcessByKey.getProcessCheck()) {
                if("4".equals(tProcessByKey.getCheckType())){//企业审核
                    Object param = processVo.getParams().get(tProcessByKey.getParam());
                    if (ObjectUtil.isNull(param)){
                        throw new ServiceException("param不可为空");
                    }
                    actProcessVo.setAudit(param.toString());

                    Object paramName = processVo.getParams().get(tProcessByKey.getParamName());
                    if (ObjectUtil.isNull(paramName)){
                        throw new ServiceException("paramName不可为空");
                    }
                    actProcessVo.setCompanyName(paramName.toString());
                }else {
                    actProcessVo.setAudit(tProcessByKey.getAudit());
                }
            }else if (2==tProcessByKey.getProcessCheck()){
                // 根据接口获取
                if (ObjectUtil.isNull(tProcessByKey.getRuleId())){
                    throw new ServiceException("业务id不可为空");
                }
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
                List<String> personByRule = WorkUtils.getPersonByRule(tProcessByKey.getRuleId(),processVo.getParams());
                //将某些固定的审核人员添加进去
                if (ObjectUtil.isNotEmpty(tProcessByKey.getAudit())){
                    String[] split = tProcessByKey.getAudit().split(",");
                    Collections.addAll(personByRule,split);
                }
                for (String s : personByRule) {
                    ActProcess actProcess = new ActProcess();
                    BeanUtil.copyProperties(actProcessVo,actProcess);
                    actProcess.setAudit(s);
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

    public static  Map batchDeleted(HisProcess hisProcess,List<Long> ids){
        HashMap<String, Object> map = new HashMap<>();
        hisProcess.setEndTime(DateUtils.getNowDate());
        //获取当前购房信息
        //1.得到当前这条运行任务
        LambdaQueryWrapper<ActProcess> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ActProcess::getBusinessId,hisProcess.getBusinessId());
        lqw.eq(ObjectUtil.isNotNull(hisProcess.getStep()) && ObjectUtil.isNotEmpty(hisProcess.getStep()),ActProcess::getStep,hisProcess.getStep());
        lqw.in(ActProcess::getProcessId,ids);
        List<ActProcessVo> actProcessVos = actProcessMapper.selectVoList(lqw);
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (actProcessVos.size()>0){
            //根据审核人员类型判断流程中那条数据属于当前人的
            String checkType = actProcessVos.get(0).getCheckType();
            String  audit ="";
            if ("1".equals(checkType)){
                audit=loginUser.getUserId().toString();
            }else if ("2".equals(checkType)){
                audit = loginUser.getDeptId().toString();
            }else if ("3".equals(checkType)){
                audit = loginUser.getRoleId().toString();
            }else if ("4".equals(checkType)) {
                if (ObjectUtil.isNotNull(hisProcess.getCompanyId())) {
                    audit = hisProcess.getCompanyId();
                } else {
                    audit = String.valueOf(loginUser.getCompanyId());
                }
            }
            String finalAudit = audit;
            List<ActProcessVo> collect = actProcessVos.stream().filter(e -> checkType.equals(e.getCheckType()) && finalAudit.equals(e.getAudit())).collect(Collectors.toList());
            if (collect.size()>0) {
                ActProcessVo actProcessVo = collect.get(0);
                hisProcess.setCheckType(actProcessVo.getCheckType());
                //往历史表里面添加当前数据
                hisProcess.setProcessId(actProcessVo.getProcessId());
                hisProcess.setCardId(actProcessVo.getCardId());
                hisProcess.setCompanyName(actProcessVo.getCompanyName());
                hisProcess.setUserId(actProcessVo.getUserId());
                hisProcess.setCreateTime(actProcessVo.getCreateTime());
                hisProcess.setStep(actProcessVo.getStep());
                hisProcess.setType(actProcessVo.getType());
                hisProcess.setDescription(actProcessVo.getDescription());
                hisProcess.setStartUser(actProcessVo.getStartUser());
                hisProcess.setIsNext(actProcessVo.getIsNext());
                //获取当前业务处理耗时
                Date date1 = hisProcess.getCreateTime();
                Date date2 = hisProcess.getEndTime();
                //精确到秒
                String formatBetween  = DateUtil.formatBetween(date1, date2, BetweenFormatter.Level.SECOND);
                hisProcess.setTimeBetween(formatBetween);
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
//                    String userId = String.valueOf(hisProcess.getParams().get("companyId"));
                    hisProcess.setCompanyName(hisProcess.getParams().get("companyName").toString());
                    hisProcess.setAudit(actProcessVo.getAudit());
                }
                hisProcess.setId(null);
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
                auditLog.setAdminUserName(loginUser.getNickName());
                auditLog.setStep(actProcessVo.getStep());
                auditLogMapper.insert(auditLog);
                //如果成功就删除当前条0
                if ("1".equals(hisProcess.getStatus())) {//失败删除全部当前业务相关数据
                    //新增系统消息
                    //获取流程的名称
//                    TProcess tProcess = processMapper.selectById(actProcessVo.getProcessId());
                    //删除当前业务的数据
                    List<Long> collect1 = actProcessVos.stream().map(ActProcessVo::getId).collect(Collectors.toList());
                    actProcessMapper.deleteBatchIds(collect1);
                    map.put("status",Constants.FAILD);
                    return map;
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
                            processVo.setCompanyName(actProcessVo.getCompanyName());
                            processVo.setCardId(actProcessVo.getCardId());
                            comply(processVo);
                            map.put("status",Constants.WAIT);
                            map.put("step",step);
                            return map;
                        } else {
                            //业务办结
                            map.put("status",Constants.SUCCEED);
                            return map;

                        }
                    }
                    map.put("status",Constants.WAIT);
                    return map;
                }
            }else {
                throw new ServiceException("当前不具备审核资格");
            }
        }
        map.put("status",Constants.NONENTITY);
        return map;
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
        Object updateTime = rollBackLog.getParams().get("updateTime");
        if (ObjectUtil.isNull(updateTime)){
            throw new ServiceException("updateTime不可为空");
        }
        //获取出当前流程所在的步骤
        LambdaQueryWrapper<ActProcess> lwq = new LambdaQueryWrapper<>();
        lwq.eq(ActProcess::getBusinessId,rollBackLog.getBusinessId());
        List<ActProcessVo> actProcessVos = actProcessMapper.selectVoList(lwq);
        //获取去可退回步骤
        QueryWrapper<HisProcess> wrapper = new QueryWrapper<>();
        wrapper.eq("business_id",rollBackLog.getBusinessId());
        wrapper.ge("h.step","1");
        wrapper.le("h.step",actProcessVos.get(0).getStep());
        wrapper.ge("h.create_time",updateTime);
        wrapper.groupBy("h.audit");
        wrapper.orderByAsc("h.step");
        List<HisProcess> hisProcessVos = hisProcessMapper.selectVoHisList(wrapper);
        //如果只有一步就提示可直接退回
        if (hisProcessVos.size()==1){
            throw new ServiceException("当前流程不允许退回,请直接驳回");
        }
        //取出当前流程中最后的步骤
        String step = hisProcessVos.stream().max(Comparator.comparing(HisProcess::getStep)).get().getStep();
        //判断当前撤销的步骤是否是跳步
        if (new Integer(rollBackLog.getStep())< new Integer(step)){
            //回退当前rollBackLog中的id,将大于step的id给删除
            List<Long> collect = hisProcessVos.stream().filter(h -> !h.getId().equals(rollBackLog.getId())).map(HisProcess::getId).collect(Collectors.toList());
            hisProcessMapper.deleteBatchIds(collect);
        }else {
            List<Long> collect = hisProcessVos.stream().filter(h -> h.getId().equals(rollBackLog.getId())).map(HisProcess::getId).collect(Collectors.toList());
            hisProcessMapper.deleteBatchIds(collect);
        }
        //把当前流程运行表中的数据删除
        List<Long> collect = actProcessVos.stream().map(ActProcessVo::getId).collect(Collectors.toList());
        actProcessMapper.deleteBatchIds(collect);
        HisProcess hisProcess = hisProcessVos.stream().filter(h -> h.getId().equals(rollBackLog.getId())).collect(Collectors.toList()).get(0);
        String param = JsonUtils.toJsonString(hisProcess);
        ActProcess actProcess = new ActProcess();
        actProcess.setIsNext(hisProcess.getIsNext());
        actProcess.setType(hisProcess.getType());
        actProcess.setUserId(hisProcess.getUserId());
        actProcess.setCheckType(hisProcess.getCheckType());
        actProcess.setCompanyName(hisProcess.getCompanyName());
        actProcess.setCreateTime(hisProcess.getCreateTime());
        actProcess.setStep(hisProcess.getStep());
        actProcess.setDescription(hisProcess.getDescription());
        actProcess.setStartUser(hisProcess.getStartUser());
        actProcess.setBusinessId(hisProcess.getBusinessId());
        actProcess.setAudit(hisProcess.getAudit());
        actProcess.setProcessId(hisProcess.getProcessId());
        int insert =  actProcessMapper.insert(actProcess);
        rollBackLog.setCreateTime(DateUtils.getNowDate());
        rollBackLog.setParam(param);
        rollBackLog.setUpdateBy(userId.toString());
        insert += rollBackLogMapper.insert(rollBackLog);
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
//            if (!"1".equals(step)){
            //获取历史表中在该业务流程步骤之前的人员数据,根据业务表的更新时间为节点
            Object updateTime = businessDTO.getParams().get("updateTime");
            if (ObjectUtil.isNull(updateTime)){
                throw new ServiceException("updateTime不可为空");
            }
            QueryWrapper<HisProcess> wrapper = new QueryWrapper<>();
            wrapper.eq("business_id",businessDTO.getBusinessId());
//                wrapper.ge("h.step","1");
            wrapper.le("h.step",step);
            wrapper.ge("h.create_time",updateTime);
            wrapper.groupBy("h.audit");
            wrapper.orderByAsc("h.step");
            List<HisProcess> hisProcessVos = hisProcessMapper.selectVoHisList(wrapper);
            if (hisProcessVos.size()==0){
                throw new ServiceException("当前流程没有可退回人员");
            }
            for (HisProcess hisProcessVo : hisProcessVos) {
                if ("4".equals(hisProcessVo.getCheckType())) {//企业审核
                    hisProcessVo.setAudit1(hisProcessVo.getCompanyName());
                } else if ("1".equals(hisProcessVo.getCheckType())) {
                    //根据id获取人员信息
                    SysUser sysUser = processMapper.selectUserById(new Long(hisProcessVo.getAudit()));
                    hisProcessVo.setAudit1(sysUser.getUserName());
                } else if ("2".equals(hisProcessVo.getCheckType())) {
                    SysDept sysDept = processMapper.selectDeptById(new Long(hisProcessVo.getAudit()));
                    hisProcessVo.setAudit1(sysDept.getDeptName());
                } else if ("3".equals(hisProcessVo.getCheckType())) {
                    SysRole sysRole = processMapper.selectRoleById(new Long(hisProcessVo.getAudit()));
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
        Page<HisProcess> hisProcessPage = new Page<>();
        if ("specific".equals(hisProcess.getTiger())){
            hisProcessPage = hisProcessMapper.selectListPageBySpecific(pageQuery.build(), hisProcess);
        }else {
            hisProcessPage = hisProcessMapper.selectVoListPage(pageQuery.build(), hisProcess);
            if (hisProcessPage.getRecords().size() == 0) {
                hisProcessPage = hisProcessMapper.selectListPageByCC(pageQuery.build(), hisProcess);
            }
        }
        List<HisProcess> records = hisProcessPage.getRecords();
        for (HisProcess record : records) {
            if("4".equals(record.getCheckType())){//企业审核
                record.setAudit(record.getCompanyName());
            }else if("1".equals(record.getCheckType())) {
                //根据id获取人员信息
                SysUser sysUser = processMapper.selectUserById(new Long( record.getAudit()));
                record.setAudit(sysUser.getUserName());
            }else if ("2".equals(record.getCheckType())){
                SysDept sysDept = processMapper.selectDeptById(new Long(record.getAudit()));
                record.setAudit(sysDept.getDeptName());
            }else if ("3".equals(record.getCheckType())){
                SysRole sysRole = processMapper.selectRoleById(new Long(record.getAudit()));
                record.setAudit(sysRole.getRoleName());
            }
        }
        return TableDataInfo.build(hisProcessPage);
    }

    /**
     * 获取待办业务
     */
    public static TableDataInfo<ActProcess> getWaitTaskList(ActProcess actProcess,PageQuery pageQuery){
        if (ObjectUtil.isNotNull(actProcess.getStepList()) && actProcess.getStepList().length>0){
            actProcess.setProcessKey(actProcess.getStepList()[0]);
            actProcess.setStep(actProcess.getStepList()[1]);
        }
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (ObjectUtil.isNull(loginUser.getUserId())){
            throw new ServiceException("请先登录");
        }
        actProcess.setUserId(loginUser.getUserId().toString());
        //获取所有流程中的抄送人员
        //后台查看待审核页面
        if (!"4".equals(actProcess.getCheckType())){
            Page<ActProcess> actProcessVoCCList =new Page<>();
            if ("specific".equals(actProcess.getTiger())){
                actProcessVoCCList = actProcessMapper.selectSpecificList(pageQuery.build(), actProcess);
            }else {
                actProcessVoCCList = actProcessMapper.selectCCList(pageQuery.build(), actProcess);
            }
            if (actProcessVoCCList.getRecords().size() > 0) {
                for (ActProcess record : actProcessVoCCList.getRecords()) {
                    if ("4".equals(record.getCheckType())){//公司
                        record.setAudit(record.getCompanyName());
                    }else if("1".equals(record.getCheckType())) {
                        //根据id获取人员信息
                        SysUser sysUser = processMapper.selectUserById(new Long( record.getAudit()));
                        if (ObjectUtil.isNull(sysUser)){
                            throw new ServiceException("用户获取失败");
                        }
                        record.setAudit(sysUser.getUserName());
                    }else if ("2".equals(record.getCheckType())){
                        SysDept sysDept = processMapper.selectDeptById(new Long(record.getAudit()));
                        if (ObjectUtil.isNull(sysDept)){
                            throw new ServiceException("部门获取失败");
                        }
                        record.setAudit(sysDept.getDeptName());
                    }else if ("3".equals(record.getCheckType())){
                        SysRole sysRole = processMapper.selectRoleById(new Long(record.getAudit()));
                        if (ObjectUtil.isNull(sysRole)){
                            throw new ServiceException("角色获取失败");
                        }
                        record.setAudit(sysRole.getRoleName());
                    }
                    if (ObjectUtil.isNotEmpty(record.getTimeout())&& ObjectUtil.isNotNull(record.getTimeout())){
                        DateTime beforeTime = DateUtil.offsetDay(record.getCreateTime(), new Integer(record.getTimeout()));
                        Date nowDate = DateUtils.getNowDate();
                        long between = DateHolidayUtils.getBetweenDays(beforeTime, nowDate);
                        if (between<0){
                            long abs = Math.abs(between);
                            record.setTimeout("超时"+abs+"天");
                        }else {
                            record.setTimeout("剩余"+between+"天");
                        }
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
                    long between = DateHolidayUtils.getBetweenDays(beforeTime, nowDate);
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
        actProcess.setUserId(ObjectUtil.isNotNull(loginUser.getUserId())?loginUser.getUserId().toString():"");
        actProcess.setRoleId(ObjectUtil.isNotNull(loginUser.getRoleId())?loginUser.getRoleId().toString():"");
        actProcess.setDeptId(ObjectUtil.isNotNull(loginUser.getDeptId())?loginUser.getDeptId().toString():"");
        Page<ActProcess> actProcessVoList = actProcessMapper.selectVoListPage(pageQuery.build(),actProcess);
        List<ActProcess> records = actProcessVoList.getRecords();
        for (ActProcess record : records) {
            if("1".equals(record.getCheckType())) {
                //根据id获取人员信息
                SysUser sysUser = processMapper.selectUserById(new Long( record.getAudit()));
                record.setAudit(sysUser.getUserName());
            }else if ("2".equals(record.getCheckType())){
                SysDept sysDept = processMapper.selectDeptById(new Long(record.getAudit()));
                record.setAudit(sysDept.getDeptName());
            }else if ("3".equals(record.getCheckType())){
                SysRole sysRole = processMapper.selectRoleById(new Long(record.getAudit()));
                record.setAudit(sysRole.getRoleName());
            }
            if (ObjectUtil.isNotEmpty(record.getTimeout())&& ObjectUtil.isNotNull(record.getTimeout())){
                DateTime beforeTime = DateUtil.offsetDay(record.getCreateTime(), new Integer(record.getTimeout()));
                Date nowDate = DateUtils.getNowDate();
                long between = DateHolidayUtils.getBetweenDays(beforeTime, nowDate);
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
        Object processKey = params.get("processKey");
        if (ObjectUtil.isNull(processKey)){
            throw new ServiceException("processKey不可为空");
        }

        List<ProcessVo> processVos = processMapper.selectVoList(new LambdaQueryWrapper<TProcess>()
            .eq(TProcess::getProcessKey, params.get("processKey"))
            .orderByAsc(TProcess::getStep));

        List<ActProcessVo> actProcessVoList = actProcessMapper.selectVoList(new LambdaQueryWrapper<ActProcess>()
            .eq(ActProcess::getBusinessId, businessId)
            .in(ActProcess::getProcessId,processVos.stream().map(ProcessVo::getId).collect(Collectors.toList())));

        List<AuditLog> auditLogs = auditLogMapper.selectList(new LambdaQueryWrapper<AuditLog>()
            .eq(AuditLog::getProcessKey, processKey)
            .eq(AuditLog::getOtherId, businessId));
//            .orderByAsc(AuditLog::getCreateTime));
        List<ProcessVo> list1 = new ArrayList<>();
        processVos.stream().forEach(e ->{
            e.setAuditLogList1(auditLogs);
            e.setActProcessVoList(actProcessVoList);
            e.setBusinessId(businessId);
            params.put("step",e.getStep());
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
                        e.setChecked("");
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
                        CollectionUtil.addAll(personByRule,split);
                    }
                    for (String s : personByRule) {
                        e.setAudit(s);
                        e.setChecked("");
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
            switch (e.getCheckType()){
                case "1":e.setCheckType("人员");
                    break;
                case "2":e.setCheckType("部门");
                    break;
                case "3":e.setCheckType("角色");
                    break;
                case "4":e.setCheckType("公司");
                    break;
                case "5":e.setCheckType("人员");
                    break;
                default:e.setCheckType("未知类型");
                    break;
            }
            e.setAuditLogList1(null);
            e.setId(null);
            e.setProcessKey(null);
            e.setType(null);
            e.setProcessCheck(null);
            e.setCc(null);
            e.setIsNext(null);
            e.setRuleId(null);
            e.setBusinessId(null);
            e.setParams(null);
            e.setParam(null);
            e.setParamName(null);
            e.setTimeout(null);
            e.setBean(null);
            e.setAudit1(null);
            e.setActProcessVoList(null);
            List<AuditLog> auditLogList = e.getAuditLogList();
            auditLogList.forEach(a ->{
                a.setCreateBy(null);
                a.setUpdateBy(null);
                a.setId(null);
                a.setAuditId(null);
                a.setOtherId(null);
                a.setRoleName(null);
                a.setPushLog(null);
                a.setProcessKey(null);
                a.setAudit(null);
                a.setStep(null);
                a.setAuditType(null);
            });
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

        String processStatus = params.get("processStatus").toString();
        if (Constants.CANCEL.equals(processStatus)){
            List<AuditLog> arrayList = new ArrayList<>();
            AuditLog auditLog1 = auditLogs.get(auditLogs.size() - 1);
            auditLog1.setStatus("取消认定");
            arrayList.add(auditLog1);
            ProcessVo processVo1 = new ProcessVo();
            processVo1.setAudit(auditLogs.get(auditLogs.size()-1).getAdminUserName());
            processVo1.setName("安居资格认定");
            processVo1.setChecked("2");
            processVo1.setAuditLogList(arrayList);
            List<ProcessVo> list = new ArrayList<>();
            list.add(processVo1);

            ProcessVoResultDto processVoResultDto = new ProcessVoResultDto();
            processVoResultDto.setStep("安居资格取消");
            processVoResultDto.setProcessVoList(list);
            collect1.add(processVoResultDto);
        }

        System.out.println("collect = " + collect1);
        return collect1;
    }

    /**
     * 返回审核人公共类
     * @param e
     */
    public static void dd(ProcessVo e){
        if("4".equals(e.getCheckType())){//企业审核
            Map params = e.getParams();
            if (ObjectUtil.isNull(e.getParamName())){
                throw  new ServiceException("param_name不可为空");
            }
            String o = params.get(e.getParamName()).toString();
            e.setAudit1(params.get(e.getParam()).toString());
            e.setAudit(o);
        }else if("1".equals(e.getCheckType()) || "5".equals(e.getChecked())) {
            //根据id获取人员信息
            SysUser sysUser = processMapper.selectUserById(Long.valueOf(e.getAudit()));
            if(ObjectUtil.isNull(sysUser)){
                throw new ServiceException("根据id获取人员信息失败");
            }
            e.setAudit1(e.getAudit());
            e.setAudit(sysUser.getNickName());
        }else if ("2".equals(e.getCheckType())){
            SysDept sysDept = processMapper.selectDeptById(Long.valueOf(e.getAudit()));
            if(ObjectUtil.isNull(sysDept)){
                throw new ServiceException("根据id获取部门信息失败");
            }
            e.setAudit1(e.getAudit());
            e.setAudit(sysDept.getDeptName());
        }else if ("3".equals(e.getCheckType())){
            SysRole sysRole = processMapper.selectRoleById(Long.valueOf(e.getAudit()));
            if(ObjectUtil.isNull(sysRole)){
                throw new ServiceException("根据id获取角色信息失败");
            }
            e.setAudit1(e.getAudit());
            e.setAudit(sysRole.getRoleName());
        }
        List<ActProcessVo> actProcessVoList =e.getActProcessVoList();
        List<AuditLog> auditLogs = e.getAuditLogList1();
        List<ActProcessVo> collect1 = actProcessVoList.stream().filter(a -> a.getAudit().equals(e.getAudit1()) && a.getCheckType().equals(e.getCheckType()) && a.getStep().equals(e.getStep()) && a.getType().equals(e.getType())).collect(Collectors.toList());
        if (actProcessVoList.size()>0){
            actProcessVoList.stream().forEach(a ->{
                if ( new Integer(e.getStep())< new Integer(a.getStep())){
                    e.setChecked("1");//绿色
                }else if ( new Integer(e.getStep()).equals(new Integer(a.getStep()))){
                    if (collect1.size()>0){
                        e.setChecked("4");//红色
                    }else {
                        e.setChecked("1");
                    }
                }else {
                    e.setChecked("3");//无色
                }
            });
        }else {
            Map map = e.getParams();
            Object process_status = map.get("processStatus");
            if (ObjectUtil.isNotNull(process_status)){
                if (Constants.SUCCEED.equals(process_status.toString())){
                    e.setChecked("1");
                }else if (Constants.FAILD.equals(process_status.toString())){
                    if (auditLogs.size()>0){
                        AuditLog auditLog = auditLogs.get(auditLogs.size()-1);
                        if (e.getAudit1().equals(auditLog.getAudit()) && e.getStep().equals(auditLog.getStep()) && e.getCheckType().equals(auditLog.getAuditType())){
                            e.setChecked("2");
                        }
                    }
                }else if (Constants.CANCEL.equals(process_status.toString())){
                    e.setChecked("3");//无色
                }
            }else {
                e.setChecked("3");
            }
        }

        if (auditLogs.size()>0) {
            if ("4".equals(e.getCheckType())){
                Object companyId = e.getParams().get("companyId");
                if (ObjectUtil.isNull(companyId)){
                    throw new ServiceException("companyId不可为空");
                }
            }
            //状态 1.待提交 2.受理中 3.受理退件 4.受理驳回 5.初审中 6初审不通过 7.初审退件 8.审定中 9.审定不通过 10.审定通过,11.审定退件 12.资格取消
            //2,3,4,5--->step1
            //6,7,8---->step2
            //9,10---->step3
            List<AuditLog> collect = auditLogs.stream().filter(a -> e.getStep().equals(a.getStep()) && e.getAudit1().equals(a.getAudit())).collect(Collectors.toList());
            if (e.getProcessKey().equals("apply_house") && collect.size()==0 ) {
                if ("1".equals(e.getStep())) {
                    collect = auditLogs.stream().filter(a -> e.getAudit1().equals(a.getAudit()) &&
                        (a.getStatus().equals("1") || a.getStatus().equals("2") || a.getStatus().equals("3")
                            || a.getStatus().equals("4") || a.getStatus().equals("5")) ).collect(Collectors.toList());
                }else if ("2".equals(e.getStep())){
                    collect = auditLogs.stream().filter(a -> e.getAudit1().equals(a.getAudit()) &&
                        (a.getStatus().equals("6") || a.getStatus().equals("7") || a.getStatus().equals("8"))).collect(Collectors.toList());
                }else if ("3".equals(e.getStep())){
                    collect = auditLogs.stream().filter(a -> e.getAudit1().equals(a.getAudit()) &&
                        (a.getStatus().equals("9") || a.getStatus().equals("10") || a.getStatus().equals("11") || a.getStatus().equals("12"))).collect(Collectors.toList());
                }

                collect.stream().forEach(a -> {
                    if (ObjectUtil.isNotNull(a.getStatus())) {
                        switch (a.getStatus()) {
                            case "1":
                                a.setStatus("待提交");
                                break;
                            case "2":
                                a.setStatus("受理中");
                                break;
                            case "3":
                                a.setStatus("受理退件");
                                break;
                            case "4":
                                a.setStatus("受理驳回");
                                break;
                            case "5":
                                a.setStatus("受理成功");
                                break;
                            case "6":
                                a.setStatus("初审不通过");
                                break;
                            case "7":
                                a.setStatus("初审退件");
                                break;
                            case "8":
                                a.setStatus("初审成功");
                                break;
                            case "9":
                                a.setStatus("审定不通过");
                                break;
                            case "10":
                                a.setStatus("审定通过");
                                break;
                            case "11":
                                a.setStatus("审定退件");
                                break;
                            case "12":
                                a.setStatus("资格取消");
                                break;
                            default:
                                a.setStatus("未知状态");
                                break;
                        }
                    }
                });
            }else {
                collect.stream().forEach(a -> {
                    if (ObjectUtil.isNotNull(a.getStatus())) {
                        switch (a.getStatus()) {
                            case "1":
                                a.setStatus(e.getDescription()+"失败");
                                break;
                            case "2":
                                a.setStatus(e.getDescription()+"成功");
                                break;
                            case "3":
                                a.setStatus("受理退件");
                                break;
                            case "4":
                                a.setStatus("受理驳回");
                                break;
                            case "5":
                                a.setStatus("受理成功");
                                break;
                            case "6":
                                a.setStatus("初审不通过");
                                break;
                            case "7":
                                a.setStatus("初审退件");
                                break;
                            case "8":
                                a.setStatus("初审成功");
                                break;
                            case "9":
                                a.setStatus("审定不通过");
                                break;
                            case "10":
                                a.setStatus("审定通过");
                                break;
                            case "11":
                                a.setStatus("审定退件");
                                break;
                            case "12":
                                a.setStatus("资格取消");
                                break;
                            default:
                                a.setStatus("未知状态");
                                break;
                        }
                    }
                });
            }

            e.setAuditLogList(collect);
        }else {
            e.setAuditLogList(new ArrayList<>());
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
