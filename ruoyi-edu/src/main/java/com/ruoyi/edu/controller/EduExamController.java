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
import com.ruoyi.edu.vo.EduExamVo;
import com.ruoyi.edu.bo.EduExamQueryBo;
import com.ruoyi.edu.bo.EduExamAddBo;
import com.ruoyi.edu.bo.EduExamEditBo;
import com.ruoyi.edu.service.IEduExamService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 考试信息Controller
 *
 * @author keyleaf
 * @date 2021-05-23
 */
@Api(value = "考试信息控制器", tags = {"考试信息管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/edu/exam")
public class EduExamController extends BaseController {

    private final IEduExamService iEduExamService;

    /**
     * 查询考试信息列表
     */
    @ApiOperation("查询考试信息列表")
    @PreAuthorize("@ss.hasPermi('edu:exam:list')")
    @GetMapping("/list")
    public TableDataInfo<EduExamVo> list(@Validated EduExamQueryBo bo) {
        return iEduExamService.queryPageList(bo);
    }

    /**
     * 导出考试信息列表
     */
    @ApiOperation("导出考试信息列表")
    @PreAuthorize("@ss.hasPermi('edu:exam:export')")
    @Log(title = "考试信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult<EduExamVo> export(@Validated EduExamQueryBo bo) {
        List<EduExamVo> list = iEduExamService.queryList(bo);
        ExcelUtil<EduExamVo> util = new ExcelUtil<EduExamVo>(EduExamVo.class);
        return util.exportExcel(list, "考试信息");
    }

    /**
     * 获取考试信息详细信息
     */
    @ApiOperation("获取考试信息详细信息")
    @PreAuthorize("@ss.hasPermi('edu:exam:query')")
    @GetMapping("/{id}")
    public AjaxResult<EduExamVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return AjaxResult.success(iEduExamService.queryById(id));
    }

    /**
     * 新增考试信息
     */
    @ApiOperation("新增考试信息")
    @PreAuthorize("@ss.hasPermi('edu:exam:add')")
    @Log(title = "考试信息", businessType = BusinessType.INSERT)
    @PostMapping()
    public AjaxResult<Void> add(@Validated @RequestBody EduExamAddBo bo) {
        return toAjax(iEduExamService.insertByAddBo(bo) ? 1 : 0);
    }

    /**
     * 修改考试信息
     */
    @ApiOperation("修改考试信息")
    @PreAuthorize("@ss.hasPermi('edu:exam:edit')")
    @Log(title = "考试信息", businessType = BusinessType.UPDATE)
    @PutMapping()
    public AjaxResult<Void> edit(@Validated @RequestBody EduExamEditBo bo) {
        return toAjax(iEduExamService.updateByEditBo(bo) ? 1 : 0);
    }

    /**
     * 删除考试信息
     */
    @ApiOperation("删除考试信息")
    @PreAuthorize("@ss.hasPermi('edu:exam:remove')")
    @Log(title = "考试信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iEduExamService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
