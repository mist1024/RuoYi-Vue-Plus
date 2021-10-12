package com.ruoyi.demo.controller;

import java.util.List;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import javax.validation.constraints.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.demo.domain.vo.HjcSelectionVo;
import com.ruoyi.demo.domain.bo.HjcSelectionBo;
import com.ruoyi.demo.service.IHjcSelectionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 图片选择参数Controller
 *
 * @author qianlan
 * @date 2021-10-12
 */
@Validated
@Api(value = "图片选择参数控制器", tags = {"图片选择参数管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/demo/selection")
public class HjcSelectionController extends BaseController {

    private final IHjcSelectionService iHjcSelectionService;

    /**
     * 查询图片选择参数列表
     */
    @ApiOperation("查询图片选择参数列表")
    @PreAuthorize("@ss.hasPermi('demo:selection:list')")
    @GetMapping("/list")
    public TableDataInfo<HjcSelectionVo> list(@Validated HjcSelectionBo bo) {
        return iHjcSelectionService.queryPageList(bo);
    }

    /**
     * 导出图片选择参数列表
     */
    @ApiOperation("导出图片选择参数列表")
    @PreAuthorize("@ss.hasPermi('demo:selection:export')")
    @Log(title = "图片选择参数", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult<HjcSelectionVo> export(@Validated HjcSelectionBo bo) {
        List<HjcSelectionVo> list = iHjcSelectionService.queryList(bo);
        ExcelUtil<HjcSelectionVo> util = new ExcelUtil<HjcSelectionVo>(HjcSelectionVo.class);
        return util.exportExcel(list, "图片选择参数");
    }

    /**
     * 获取图片选择参数详细信息
     */
    @ApiOperation("获取图片选择参数详细信息")
    @PreAuthorize("@ss.hasPermi('demo:selection:query')")
    @GetMapping("/{sid}")
    public AjaxResult<HjcSelectionVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("sid") Long sid) {
        return AjaxResult.success(iHjcSelectionService.queryById(sid));
    }

    /**
     * 新增图片选择参数
     */
    @ApiOperation("新增图片选择参数")
    @PreAuthorize("@ss.hasPermi('demo:selection:add')")
    @Log(title = "图片选择参数", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody HjcSelectionBo bo) {
        return toAjax(iHjcSelectionService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改图片选择参数
     */
    @ApiOperation("修改图片选择参数")
    @PreAuthorize("@ss.hasPermi('demo:selection:edit')")
    @Log(title = "图片选择参数", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody HjcSelectionBo bo) {
        return toAjax(iHjcSelectionService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除图片选择参数
     */
    @ApiOperation("删除图片选择参数")
    @PreAuthorize("@ss.hasPermi('demo:selection:remove')")
    @Log(title = "图片选择参数" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{sids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] sids) {
        return toAjax(iHjcSelectionService.deleteWithValidByIds(Arrays.asList(sids), true) ? 1 : 0);
    }
}
