package com.ruoyi.work.controller;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.event.PushLogEvent;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.work.domain.ActProcess;
import com.ruoyi.work.domain.HisProcess;
import com.ruoyi.work.domain.RollBackLog;
import com.ruoyi.work.domain.TProcess;
import com.ruoyi.work.domain.vo.ProcessVo;
import com.ruoyi.work.dto.BusinessDTO;
import com.ruoyi.work.dto.HisProcessVoResultDto;
import com.ruoyi.work.dto.HousingConstructionBureauPushDto;
import com.ruoyi.work.dto.ProcessVoResultDto;
import com.ruoyi.work.mapper.ProcessMapper;
import com.ruoyi.work.utils.WorkComplyUtils;
import com.ruoyi.work.utils.WorkUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 公共流程相关
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/work")
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class WorkController extends BaseController {
    private final ProcessMapper processMapper;
    private  final HousingConstructionBureauPushDto housingConstructionBureauPushDto;

    /**
     * 获取待办
     */
    @Log(title = "获取待办数据",businessType = BusinessType.OTHER)
    @SaCheckPermission("work:task:taskList")
    @GetMapping("/getWaitTaskList")
    public TableDataInfo<ActProcess> getWaitTaskList(ActProcess actProcess, PageQuery pageQuery){
        return WorkComplyUtils.getWaitTaskList(actProcess,pageQuery);
    }

    /**
     * 获取已办
     */
    @Log(title = "获取已办数据",businessType = BusinessType.OTHER)
    @SaCheckPermission("work:task:historyList")
    @GetMapping("/getHistoryList")
    public TableDataInfo<HisProcess> getHistoryList(HisProcess hisProcess, PageQuery pageQuery){
        return WorkComplyUtils.getHistoryList(hisProcess,pageQuery);
    }

    /**
     * 获取当前业务进行步骤
     */
    @Log(title = "获取当前业务运行到那一步",businessType = BusinessType.OTHER)
    @SaCheckPermission("work:task:processPlan")
    @PostMapping("/processPlan")
    public R<?> processPlan(@RequestBody ActProcess actProcess){
        if (ObjectUtil.isNull(actProcess.getBusinessId())){
            return R.fail("businessId不可为空");
        }
        if (ObjectUtil.isNull(actProcess.getProcessKey())){
            return R.fail("key不可为空");
        }
        List<TProcess> tProcesses = processMapper.selectList(new LambdaQueryWrapper<TProcess>()
            .eq(TProcess::getProcessKey, actProcess.getProcessKey()));
        if (tProcesses.size()==0){
            throw new ServiceException("当前流程不存在");
        }
        Map<String, Object> map = WorkUtils.getInfoToMap(tProcesses.get(0).getBean(), actProcess.getBusinessId());
        ProcessVo processVo = new ProcessVo();
        processVo.setBusinessId(actProcess.getBusinessId());
        processVo.setParams(map);
        List<ProcessVoResultDto>  processPlan= WorkComplyUtils.getProcessPlan(processVo);
        return R.ok(processPlan);
    }

    /**
     * 获取可退回步骤
     */
    @Log(title = "获取可退回步骤",businessType = BusinessType.OTHER)
    @PostMapping("/getStep")
    public R<?> getStep(@RequestBody BusinessDTO businessDTO){
        LambdaQueryWrapper<TProcess> wrapper = new LambdaQueryWrapper<TProcess>()
            .eq(TProcess::getProcessKey, businessDTO.getProcessKey());
        List<TProcess> tProcesses = processMapper.selectList(wrapper);
        if (tProcesses.size()==0){
            throw new ServiceException("当前流程不存在");
        }
        Map<String, Object> map = WorkUtils.getInfoToMap(tProcesses.get(0).getBean(),businessDTO.getBusinessId());
        businessDTO.setParams(map);
        List<HisProcessVoResultDto> step = WorkComplyUtils.getStep(businessDTO);
        return R.ok(step);
    }
    /**
     * 流程办理
     */
    @Log(title = "流程办理",businessType = BusinessType.OTHER)
//    @SaCheckPermission("work:task:batchDeleted")
    @PostMapping("/batchDeleted")
    public R<?> batchDeleted(@RequestBody HisProcess hisProcess){
        if (ObjectUtil.isNull(hisProcess.getStatus())){
            return R.fail("状态不可为空");
        }
        if (ObjectUtil.isNull(hisProcess.getProcessKey())){
            return R.fail("流程key不可为空");
        }
        if (ObjectUtil.isNull(hisProcess.getBusinessId())){
            return R.fail("业务id不可为空");
        }
        return batchDeletedCommon(hisProcess);
    }

    /**
     * 流程办理公共方法
     * @param hisProcess
     * @return
     */
    private R batchDeletedCommon(HisProcess hisProcess){
        System.out.println("hisProcess.getBusinessId() = " + hisProcess.getBusinessId());
        List<TProcess> tProcesses = processMapper.selectList(new LambdaQueryWrapper<TProcess>()
            .eq(TProcess::getProcessKey, hisProcess.getProcessKey()));
        if (tProcesses.size()==0){
            throw new ServiceException("当前流程不存在");
        }
        Map<String, Object> map = WorkUtils.getInfoToMap(tProcesses.get(0).getBean(), hisProcess.getBusinessId());
        hisProcess.setParams(map);
        Map map1 = WorkComplyUtils.batchDeleted(hisProcess,tProcesses.stream().map(TProcess::getId).collect(Collectors.toList()));
        String s = String.valueOf(map1.get("status"));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", String.valueOf(map.get("id")));
        String deptName = LoginHelper.getLoginUser().getDeptName();
        if (ObjectUtil.isNotNull(deptName)){
            hashMap.put("auditDepartName",deptName);
        }
        if (s.equals(Constants.NONENTITY)){
            throw new ServiceException("当前数据已被他人审核,请刷新页面!");
        }else{
            if ("apply_house".equals(hisProcess.getProcessKey())) {
                if (Constants.SUCCEED.equals(s)) {//审核成功推送数据
                    //公园城市局推送
                    String virtualcode = String.valueOf(map.get("virtualcode"));
                    map.put("virtualcode", virtualcode == "3" ? "010" : "009");
                    String cardType = String.valueOf(map.get("nationality"));
                    map.put("cardType", "中国籍".equals(cardType) ? 1 : 4);
                    String dateString = DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS);
                    map.put("creatTime",dateString);
                    map.put("qyStatus", "4");
                    map.put("gyStatus", "4");
                    map.put("status", "10");
                    map.put("shStatus", "4");
                    map.put("buyHousesMemberList", "null");
                    map.put("buyHousesLogList", "null");
                    String s1 = JSONUtil.toJsonPrettyStr(map);
                    log.info("map:"+s1);
                    housingConstructionBureauPushDto.openUrl("https://jcfw.cdzjryb.com/CCSRegistryCenter/rest", map, "253");
//                    housingConstructionBureauPushDto.openUrl("https://171.221.172.13:8088/CCSRegistryCenter/rest", map, "253");
                    //人才通推送
                    hashMap.put("status", "00T");
                } else if (Constants.FAILD.equals(s)) {
                    hashMap.put("status", "00N");
                    hashMap.put("description", hisProcess.getReply());
                } else {
                    hashMap.put("status", "00D");
                }
                Object apiKey = map.get("apiKey");
                if (ObjectUtil.isNotNull(apiKey) && String.valueOf(apiKey).equals("gaoxingongyuanchengshiju")) {
                    String s1 = JSONUtil.toJsonPrettyStr(hashMap);
                    log.info("hashMap:"+s1);
//                    housingConstructionBureauPushDto.send3(hashMap, "http://218.89.220.30:9200/rctopen/api/anju/openBuyHousesCallback");
                    housingConstructionBureauPushDto.send3(hashMap, "https://www.cdhtrct.com/route/open/api/anju/openBuyHousesCallback");
                }
            }
            Object step = map1.get("step");
            processMapper.updateCommonByBusinessId(tProcesses.get(0).getBean(),s, hisProcess.getBusinessId(),step);
        }
        System.out.println("s = " + s);
        return R.ok();
    }

    /**
     * 流程批量办理
     */
    @Log(title = "流程批量办理",businessType = BusinessType.OTHER)
    @SaCheckPermission("work:task:batchReviewPassed")
    @PostMapping("/batchReviewPassed")
    public R<?> batchReviewPassed(@RequestBody HisProcess hisProcess){
        hisProcess.setStatus("2");
        hisProcess.setProcessKey("house_review");
        if (ObjectUtil.isNull(hisProcess.getIds()) || hisProcess.getIds().length==0){
            return R.fail("业务id不可为空");
        }
        for (String id : hisProcess.getIds()) {
            //调用方法
            hisProcess.setBusinessId(id);
            batchDeletedCommon(hisProcess);
        }
        return R.ok("批量办理失败!");
    }

    /**
     * 获取业务id详情数据
     * @param actProcess
     * @return
     */
    @Log(title = "获取业务id详情数据",businessType = BusinessType.OTHER)
    @SaCheckPermission("work:task:taskInfo")
    @PostMapping("/taskInfo")
    public R<?> getInfoById(@RequestBody ActProcess actProcess){
        if (ObjectUtil.isNull(actProcess.getProcessKey())){
            return R.fail("key不可为空");
        }
        if (ObjectUtil.isNull(actProcess.getBusinessId())){
            return R.fail("businessId不可为空");
        }
        LambdaQueryWrapper<TProcess> wrapper = new LambdaQueryWrapper<TProcess>()
            .eq(TProcess::getProcessKey, actProcess.getProcessKey());
        List<TProcess> tProcesses = processMapper.selectList(wrapper);
        Map<String, Object> map = WorkUtils.getInfoToMap(tProcesses.get(0).getBean(),actProcess.getBusinessId());
        return R.ok(map);
    }


    @Log(title = "获取审核日志",businessType = BusinessType.OTHER)
    @PostMapping("/auditLogList")
    public R<?> getAuditLogListByOtherId(@RequestBody BusinessDTO businessDTO){
        return WorkComplyUtils.getAuditLogListByOtherId(businessDTO);
    }

    /**
     * 退回操作
     * @param rollBackLog
     * @return
     */
    @Log(title = "回退",businessType = BusinessType.OTHER)
    @PostMapping("/rollBackLog")
    public R<?> rollBackLog(@RequestBody RollBackLog rollBackLog){
        LambdaQueryWrapper<TProcess> wrapper = new LambdaQueryWrapper<TProcess>()
            .eq(TProcess::getProcessKey, rollBackLog.getProcessKey());
        List<TProcess> tProcesses = processMapper.selectList(wrapper);
        if (tProcesses.size()==0){
            throw new ServiceException("当前流程不存在");
        }
        Map<String, Object> map = WorkUtils.getInfoToMap(tProcesses.get(0).getBean(),rollBackLog.getBusinessId());
        rollBackLog.setParams(map);
        return toAjax(WorkComplyUtils.rollBack(rollBackLog)>1?1:0);
    }

}

