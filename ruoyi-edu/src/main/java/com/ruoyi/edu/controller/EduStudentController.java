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
import com.ruoyi.edu.vo.EduStudentVo;
import com.ruoyi.edu.bo.EduStudentQueryBo;
import com.ruoyi.edu.bo.EduStudentAddBo;
import com.ruoyi.edu.bo.EduStudentEditBo;
import com.ruoyi.edu.service.IEduStudentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 学生信息Controller
 *
 * @author keyleaf
 * @date 2021-05-22
 */
@Api(value = "学生信息控制器", tags = {"学生信息管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/edu/student")
public class EduStudentController extends BaseController {

    private final IEduStudentService iEduStudentService;

    /**
     * 查询学生信息列表
     */
    @ApiOperation("查询学生信息列表")
    @PreAuthorize("@ss.hasPermi('edu:student:list')")
    @GetMapping("/list")
    public TableDataInfo<EduStudentVo> list(@Validated EduStudentQueryBo bo) {
        return iEduStudentService.queryPageList(bo);
    }

    /**
     * 导出学生信息列表
     */
    @ApiOperation("导出学生信息列表")
    @PreAuthorize("@ss.hasPermi('edu:student:export')")
    @Log(title = "学生信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult<EduStudentVo> export(@Validated EduStudentQueryBo bo) {
        List<EduStudentVo> list = iEduStudentService.queryList(bo);
        ExcelUtil<EduStudentVo> util = new ExcelUtil<EduStudentVo>(EduStudentVo.class);
        return util.exportExcel(list, "学生信息");
    }

    /**
     * 获取学生信息详细信息
     */
    @ApiOperation("获取学生信息详细信息")
    @PreAuthorize("@ss.hasPermi('edu:student:query')")
    @GetMapping("/{id}")
    public AjaxResult<EduStudentVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return AjaxResult.success(iEduStudentService.queryById(id));
    }

    /**
     * 新增学生信息
     */
    @ApiOperation("新增学生信息")
    @PreAuthorize("@ss.hasPermi('edu:student:add')")
    @Log(title = "学生信息", businessType = BusinessType.INSERT)
    @PostMapping()
    public AjaxResult<Void> add(@Validated @RequestBody EduStudentAddBo bo) {
        return toAjax(iEduStudentService.insertByAddBo(bo) ? 1 : 0);
    }

    /**
     * 修改学生信息
     */
    @ApiOperation("修改学生信息")
    @PreAuthorize("@ss.hasPermi('edu:student:edit')")
    @Log(title = "学生信息", businessType = BusinessType.UPDATE)
    @PutMapping()
    public AjaxResult<Void> edit(@Validated @RequestBody EduStudentEditBo bo) {
        return toAjax(iEduStudentService.updateByEditBo(bo) ? 1 : 0);
    }

    /**
     * 删除学生信息
     */
    @ApiOperation("删除学生信息")
    @PreAuthorize("@ss.hasPermi('edu:student:remove')")
    @Log(title = "学生信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iEduStudentService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
