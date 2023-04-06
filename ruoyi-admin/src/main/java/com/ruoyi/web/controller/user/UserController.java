package com.ruoyi.web.controller.user;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.work.domain.ActProcess;
import com.ruoyi.work.domain.HisProcess;
import com.ruoyi.work.domain.TProcess;
import com.ruoyi.work.domain.vo.ProcessVo;
import com.ruoyi.work.dto.ProcessVoResultDto;
import com.ruoyi.work.mapper.ProcessMapper;
import com.ruoyi.work.utils.WorkComplyUtils;
import com.ruoyi.work.utils.WorkUtils;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.UserVo;
import com.ruoyi.system.domain.bo.UserBo;
import com.ruoyi.system.service.IUserService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 申报端用户项目
 *
 * @author ruoyi
 * @date 2023-04-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private final ProcessMapper processMapper;
    private final IUserService iUserService;

    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/list")
    public TableDataInfo<UserVo> list(UserBo bo, PageQuery pageQuery) {
        return iUserService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(UserBo bo, HttpServletResponse response) {
        List<UserVo> list = iUserService.queryList(bo);
        ExcelUtil.exportExcel(list, "【请填写功能名称】", UserVo.class, response);
    }

    /**
     * 获取【请填写功能名称】详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<UserVo> getInfo(@NotNull(message = "主键不能为空")
                             @PathVariable Long id) {
        return R.ok(iUserService.queryById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody UserBo bo) {
        return toAjax(iUserService.insertByBo(bo));
    }

    /**
     * 修改【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody UserBo bo) {
        return toAjax(iUserService.updateByBo(bo));
    }

    /**
     * 删除【请填写功能名称】
     *
     * @param ids 主键串
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iUserService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 判断这个人是否具备申请购房资格
     */
    @GetMapping("cardId/{cardId}")
    public R<?>getUserCandidateInfo(@PathVariable(value = "cardId") String cardId) throws Exception {
        return iUserService.getUserCandidateInfo(cardId);
    }

    /**
     * 获取当前业务进行步骤
     */
    @Log(title = "获取当前业务运行到那一步",businessType = BusinessType.OTHER)
    @PostMapping("/processPlan")
    public R<?> processPlan(@RequestBody ActProcess actProcess){
        List<TProcess> tProcesses = processMapper.selectList(new LambdaQueryWrapper<TProcess>()
            .eq(TProcess::getProcessKey, actProcess.getProcessKey()));
        if (tProcesses.size()==0){
            throw new ServiceException("当前流程不存在");
        }
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        Map<String, Object> map = WorkUtils.getInfoToMap(tProcesses.get(0).getBean(), actProcess.getBusinessId());
        ProcessVo processVo = new ProcessVo();
        processVo.setBusinessId(actProcess.getBusinessId());
        processVo.setParams(map);
        hashMap.put("status",map.get("processStatus"));
//        hashMap.put("cardId",map.get("cardId"));
        List<ProcessVoResultDto>  processPlan= WorkComplyUtils.getProcessPlan(processVo);
        hashMap.put("list",processPlan);
        return R.ok(hashMap);
    }

}
