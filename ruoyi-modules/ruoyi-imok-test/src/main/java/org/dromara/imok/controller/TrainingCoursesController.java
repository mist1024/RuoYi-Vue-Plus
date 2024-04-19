package org.dromara.imok.controller;

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
import org.dromara.imok.domain.vo.TrainingCoursesVo;
import org.dromara.imok.domain.bo.TrainingCoursesBo;
import org.dromara.imok.service.ITrainingCoursesService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 培训课程管理
 *
 * @author sungk
 * @date 2024-04-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/imok/courses")
public class TrainingCoursesController extends BaseController {

    private final ITrainingCoursesService trainingCoursesService;

    /**
     * 查询培训课程管理列表
     */
    @SaCheckPermission("imok:courses:list")
    @GetMapping("/list")
    public TableDataInfo<TrainingCoursesVo> list(TrainingCoursesBo bo, PageQuery pageQuery) {
        return trainingCoursesService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出培训课程管理列表
     */
    @SaCheckPermission("imok:courses:export")
    @Log(title = "培训课程管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TrainingCoursesBo bo, HttpServletResponse response) {
        List<TrainingCoursesVo> list = trainingCoursesService.queryList(bo);
        ExcelUtil.exportExcel(list, "培训课程管理", TrainingCoursesVo.class, response);
    }

    /**
     * 获取培训课程管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("imok:courses:query")
    @GetMapping("/{id}")
    public R<TrainingCoursesVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String id) {
        return R.ok(trainingCoursesService.queryById(id));
    }

    /**
     * 新增培训课程管理
     */
    @SaCheckPermission("imok:courses:add")
    @Log(title = "培训课程管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TrainingCoursesBo bo) {
        return toAjax(trainingCoursesService.insertByBo(bo));
    }

    /**
     * 修改培训课程管理
     */
    @SaCheckPermission("imok:courses:edit")
    @Log(title = "培训课程管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TrainingCoursesBo bo) {
        return toAjax(trainingCoursesService.updateByBo(bo));
    }

    /**
     * 删除培训课程管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("imok:courses:remove")
    @Log(title = "培训课程管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        return toAjax(trainingCoursesService.deleteWithValidByIds(List.of(ids), true));
    }
}
