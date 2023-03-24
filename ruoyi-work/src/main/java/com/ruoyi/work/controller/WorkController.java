package com.ruoyi.work.controller;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.work.domain.ActProcess;
import com.ruoyi.work.domain.HisProcess;
import com.ruoyi.work.domain.TProcess;
import com.ruoyi.work.domain.vo.ProcessVo;
import com.ruoyi.work.dto.BusinessDTO;
import com.ruoyi.work.dto.ProcessVoResultDto;
import com.ruoyi.work.mapper.ProcessMapper;
import com.ruoyi.work.utils.WorkComplyUtils;
import com.ruoyi.work.utils.WorkUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 流程相关
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/work")
@Transactional
public class WorkController extends BaseController {
    private final ProcessMapper processMapper;


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
        LambdaQueryWrapper<TProcess> wrapper = new LambdaQueryWrapper<TProcess>()
            .eq(TProcess::getProcessKey, actProcess.getProcessKey());
        List<TProcess> tProcesses = processMapper.selectList(wrapper);
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
    public void getStep(@RequestBody BusinessDTO businessDTO){
        LambdaQueryWrapper<TProcess> wrapper = new LambdaQueryWrapper<TProcess>()
            .eq(TProcess::getProcessKey, businessDTO.getProcessKey());
        List<TProcess> tProcesses = processMapper.selectList(wrapper);
        if (tProcesses.size()==0){
            throw new ServiceException("当前流程不存在");
        }
        Map<String, Object> map = WorkUtils.getInfoToMap(tProcesses.get(0).getBean(),businessDTO.getBusinessId());
        businessDTO.setParams(map);
        WorkComplyUtils.getStep(businessDTO);
    }



    /**
     * 流程办理
     */
    @Log(title = "流程办理",businessType = BusinessType.OTHER)
    @SaCheckPermission("work:task:batchDeleted")
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
        LambdaQueryWrapper<TProcess> wrapper = new LambdaQueryWrapper<TProcess>()
            .eq(TProcess::getProcessKey, hisProcess.getProcessKey());
        List<TProcess> tProcesses = processMapper.selectList(wrapper);
        if (tProcesses.size()==0){
            throw new ServiceException("当前流程不存在");
        }
        Map<String, Object> map = WorkUtils.getInfoToMap(tProcesses.get(0).getBean(),hisProcess.getBusinessId());
        hisProcess.setParams(map);
        String s = WorkComplyUtils.batchDeleted(hisProcess);

        if (s.equals(Constants.NONENTITY)){
            return R.fail("暂无审核");
        }else{
            processMapper.updateCommonByBusinessId(tProcesses.get(0).getBean(),s, hisProcess.getBusinessId());
        }
        System.out.println("s = " + s);
        return R.ok();
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

}

