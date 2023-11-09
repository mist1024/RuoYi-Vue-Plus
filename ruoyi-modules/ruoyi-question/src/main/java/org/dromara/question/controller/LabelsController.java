package org.dromara.question.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.question.domain.vo.LabelsVo;
import org.dromara.question.domain.bo.LabelsBo;
import org.dromara.question.service.ILabelsService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 题目标签
 *
 * @author Lion Li
 * @date 2023-11-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/question/label")
public class LabelsController extends BaseController {

    private final ILabelsService labelsService;

    /**
     * 查询题目标签列表
     */
    @SaCheckPermission("question:label:list")
    @GetMapping("/list")
    public TableDataInfo<LabelsVo> list(LabelsBo bo, PageQuery pageQuery) {
        return labelsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出题目标签列表
     */
    @SaCheckPermission("question:label:export")
    @Log(title = "题目标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(LabelsBo bo, HttpServletResponse response) {
        List<LabelsVo> list = labelsService.queryList(bo);
        ExcelUtil.exportExcel(list, "题目标签", LabelsVo.class, response);
    }

    /**
     * 获取题目标签详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("question:label:query")
    @GetMapping("/{id}")
    public R<LabelsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(labelsService.queryById(id));
    }

    /**
     * 新增题目标签
     */
    @SaCheckPermission("question:label:add")
    @Log(title = "题目标签", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LabelsBo bo) {
        return toAjax(labelsService.insertByBo(bo));
    }

    /**
     * 修改题目标签
     */
    @SaCheckPermission("question:label:edit")
    @Log(title = "题目标签", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LabelsBo bo) {
        return toAjax(labelsService.updateByBo(bo));
    }

    @SaCheckPermission("question:label:edit")
    @Log(title = "题目标签", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("changeStatus")
    public R<Void> changeStatus(@RequestBody LabelsBo bo) {
        return toAjax(labelsService.updateStatusByBo(bo));
    }

    /**
     * 删除题目标签
     *
     * @param ids 主键串
     */
    @SaCheckPermission("question:label:remove")
    @Log(title = "题目标签", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(labelsService.deleteWithValidByIds(List.of(ids), true));
    }
}
