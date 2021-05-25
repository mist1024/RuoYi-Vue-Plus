package com.ruoyi.edu.controller;

import java.util.List;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import javax.validation.constraints.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.edu.vo.EduStudentExamVo;
import com.ruoyi.edu.bo.EduStudentExamQueryBo;
import com.ruoyi.edu.bo.EduStudentExamAddBo;
import com.ruoyi.edu.bo.EduStudentExamEditBo;
import com.ruoyi.edu.service.IEduStudentExamService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 学生考试信息Controller
 * 
 * @author keyleaf
 * @date 2021-05-23
 */
@Api(value = "学生考试信息控制器", tags = {"学生考试信息管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/edu/score")
public class EduStudentExamController extends BaseController {

    private final IEduStudentExamService iEduStudentExamService;

    /**
     * 查询学生考试信息列表
     */
    @ApiOperation("查询学生考试信息列表")
    @PreAuthorize("@ss.hasPermi('edu:score:list')")
    @GetMapping("/list")
    public TableDataInfo<EduStudentExamVo> list(@Validated EduStudentExamQueryBo bo) {
        return iEduStudentExamService.queryPageList(bo);
    }

    /**
     * 导出学生考试信息列表
     */
    @ApiOperation("导出学生考试信息列表")
    @PreAuthorize("@ss.hasPermi('edu:score:export')")
    @Log(title = "学生考试信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult<EduStudentExamVo> export(@Validated EduStudentExamQueryBo bo) {
        List<EduStudentExamVo> list = iEduStudentExamService.queryList(bo);
        ExcelUtil<EduStudentExamVo> util = new ExcelUtil<EduStudentExamVo>(EduStudentExamVo.class);
        return util.exportExcel(list, "学生考试信息");
    }

    /**
     * 获取学生考试信息详细信息
     */
    @ApiOperation("获取学生考试信息详细信息")
    @PreAuthorize("@ss.hasPermi('edu:score:query')")
    @GetMapping("/{id}")
    public AjaxResult<EduStudentExamVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return AjaxResult.success(iEduStudentExamService.queryById(id));
    }

    /**
     * 新增学生考试信息
     */
    @ApiOperation("新增学生考试信息")
    @PreAuthorize("@ss.hasPermi('edu:score:add')")
    @Log(title = "学生考试信息", businessType = BusinessType.INSERT)
    @PostMapping()
    public AjaxResult<Void> add(@Validated @RequestBody EduStudentExamAddBo bo) {
        return toAjax(iEduStudentExamService.insertByAddBo(bo) ? 1 : 0);
    }

    /**
     * 修改学生考试信息
     */
    @ApiOperation("修改学生考试信息")
    @PreAuthorize("@ss.hasPermi('edu:score:edit')")
    @Log(title = "学生考试信息", businessType = BusinessType.UPDATE)
    @PutMapping()
    public AjaxResult<Void> edit(@Validated @RequestBody EduStudentExamEditBo bo) {
        return toAjax(iEduStudentExamService.updateByEditBo(bo) ? 1 : 0);
    }

    /**
     * 删除学生考试信息
     */
    @ApiOperation("删除学生考试信息")
    @PreAuthorize("@ss.hasPermi('edu:score:remove')")
    @Log(title = "学生考试信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iEduStudentExamService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
