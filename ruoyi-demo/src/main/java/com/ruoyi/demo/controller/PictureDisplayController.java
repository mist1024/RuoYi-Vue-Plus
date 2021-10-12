package com.ruoyi.demo.controller;

import java.util.List;
import java.util.Arrays;

import com.ruoyi.demo.domain.vo.PictureListVo;
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
import com.ruoyi.demo.domain.vo.PictureDisplayVo;
import com.ruoyi.demo.domain.bo.PictureDisplayBo;
import com.ruoyi.demo.service.IPictureDisplayService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 图片展示Controller
 *
 * @author qianlan
 * @date 2021-10-10
 */
@Validated
@Api(value = "图片展示控制器", tags = {"图片展示管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/demo/picture")
public class PictureDisplayController extends BaseController {

    private final IPictureDisplayService iPictureDisplayService;

	/**
	 * 查询没有被选中的图片，一次显示十条
	 * @return
	 */
	@ApiOperation("查询没有被选中的图片，一次显示十条")
    @GetMapping("/picList")
    public List<PictureListVo> pictureList(){
    	return iPictureDisplayService.queryListWithCondition();
	}

	/**
	 * 提交要修改的图片选项
	 * @return
	 */
	@ApiOperation("提交要修改的图片选项")
	@PostMapping("/commitPic")
	public AjaxResult commitPic(@RequestBody List<PictureDisplayVo> lists){
		return iPictureDisplayService.commitPic(lists);
	}

    /**
     * 查询图片展示列表
     */
    @ApiOperation("查询图片展示列表")
    @PreAuthorize("@ss.hasPermi('demo:picture:list')")
    @GetMapping("/list")
    public TableDataInfo<PictureDisplayVo> list(@Validated PictureDisplayBo bo) {
        return iPictureDisplayService.queryPageList(bo);
    }

    /**
     * 导出图片展示列表
     */
    @ApiOperation("导出图片展示列表")
    @PreAuthorize("@ss.hasPermi('demo:picture:export')")
    @Log(title = "图片展示", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult<PictureDisplayVo> export(@Validated PictureDisplayBo bo) {
        List<PictureDisplayVo> list = iPictureDisplayService.queryList(bo);
        ExcelUtil<PictureDisplayVo> util = new ExcelUtil<PictureDisplayVo>(PictureDisplayVo.class);
        return util.exportExcel(list, "图片展示");
    }

    /**
     * 获取图片展示详细信息
     */
    @ApiOperation("获取图片展示详细信息")
    @PreAuthorize("@ss.hasPermi('demo:picture:query')")
    @GetMapping("/{picId}")
    public AjaxResult<PictureDisplayVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("picId") Long picId) {
        return AjaxResult.success(iPictureDisplayService.queryById(picId));
    }

    /**
     * 新增图片展示
     */
    @ApiOperation("新增图片展示")
    @PreAuthorize("@ss.hasPermi('demo:picture:add')")
    @Log(title = "图片展示", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody PictureDisplayBo bo) {
        return toAjax(iPictureDisplayService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改图片展示
     */
    @ApiOperation("修改图片展示")
    @PreAuthorize("@ss.hasPermi('demo:picture:edit')")
    @Log(title = "图片展示", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody PictureDisplayBo bo) {
        return toAjax(iPictureDisplayService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除图片展示
     */
    @ApiOperation("删除图片展示")
    @PreAuthorize("@ss.hasPermi('demo:picture:remove')")
    @Log(title = "图片展示" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{picIds}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] picIds) {
        return toAjax(iPictureDisplayService.deleteWithValidByIds(Arrays.asList(picIds), true) ? 1 : 0);
    }
}
