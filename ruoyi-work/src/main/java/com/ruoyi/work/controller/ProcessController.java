package com.ruoyi.work.controller;

import java.util.List;
import java.util.Arrays;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.work.domain.bo.ProcessBo;
import com.ruoyi.work.domain.vo.ProcessVo;
import com.ruoyi.work.service.IProcessService;
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
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 流程配置模块
 *
 * @author ruoyi
 * @date 2023-03-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/process")
public class ProcessController extends BaseController {

    private final IProcessService iProcessService;

    /**
     * 查询流程列表
     */
    @SaCheckPermission("system:process:list")
    @GetMapping("/list")
    public TableDataInfo<ProcessVo> list(ProcessBo bo, PageQuery pageQuery) {
        return iProcessService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出流程列表
     */
    @SaCheckPermission("system:process:export")
    @Log(title = "流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ProcessBo bo, HttpServletResponse response) {
        List<ProcessVo> list = iProcessService.queryList(bo);
        ExcelUtil.exportExcel(list, "流程", ProcessVo.class, response);
    }

    /**
     * 获取流程详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:process:query")
    @GetMapping("/{id}")
    public R<ProcessVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iProcessService.queryById(id));
    }

    /**
     * 新增流程
     */
    @SaCheckPermission("system:process:add")
    @Log(title = "流程", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ProcessBo bo) {
        return toAjax(iProcessService.insertByBo(bo));
    }

    /**
     * 修改流程
     */
    @SaCheckPermission("system:process:edit")
    @Log(title = "流程", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProcessBo bo) {
        return toAjax(iProcessService.updateByBo(bo));
    }

    /**
     * 删除流程
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:process:remove")
    @Log(title = "流程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iProcessService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
