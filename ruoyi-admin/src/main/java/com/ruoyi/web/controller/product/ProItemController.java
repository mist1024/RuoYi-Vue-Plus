package com.ruoyi.web.controller.product;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.product.bo.ProItemAddBo;
import com.ruoyi.product.bo.ProItemEditBo;
import com.ruoyi.product.bo.ProItemQueryBo;
import com.ruoyi.product.service.IProItemService;
import com.ruoyi.product.vo.ProItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 商品详情Controller
 *
 * @author ruoyi
 * @date 2021-07-01
 */
@Api(value = "商品详情控制器", tags = {"商品详情管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/product/proItem")
public class ProItemController extends BaseController {

	private final IProItemService iProItemService;

	/**
	 * 查询商品详情列表
	 */
	@ApiOperation("查询商品详情列表")
	@PreAuthorize("@ss.hasPermi('product:proItem:list')")
	@GetMapping("/list")
	public TableDataInfo<ProItemVo> list(@Validated ProItemQueryBo bo) {
		return iProItemService.queryPageList(bo);
	}

	/**
	 * 导出商品详情列表
	 */
	@ApiOperation("导出商品详情列表")
	@PreAuthorize("@ss.hasPermi('product:proItem:export')")
	@Log(title = "商品详情", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult<ProItemVo> export(@Validated ProItemQueryBo bo) {
		List<ProItemVo> list = iProItemService.queryList(bo);
		ExcelUtil<ProItemVo> util = new ExcelUtil<>(ProItemVo.class);
		return util.exportExcel(list, "商品详情");
	}

	/**
	 * 获取商品详情详细信息
	 */
	@ApiOperation("获取商品详情详细信息")
	@PreAuthorize("@ss.hasPermi('product:proItem:query')")
	@GetMapping("/{id}")
	public AjaxResult<ProItemVo> getInfo(@NotNull(message = "主键不能为空")
										 @PathVariable("id") Long id) {
		return AjaxResult.success(iProItemService.getProItemById(id));
	}

	/**
	 * 新增商品详情
	 */
	@ApiOperation("新增商品详情")
	@PreAuthorize("@ss.hasPermi('product:proItem:add')")
	@Log(title = "商品详情", businessType = BusinessType.INSERT)
	@RepeatSubmit
	@PostMapping()
	public AjaxResult<Void> add(@Validated @RequestBody ProItemAddBo bo) {
		return toAjax(iProItemService.insertByAddBo(bo) ? 1 : 0);
	}

	/**
	 * 修改商品详情
	 */
	@ApiOperation("修改商品详情")
	@PreAuthorize("@ss.hasPermi('product:proItem:edit')")
	@Log(title = "商品详情", businessType = BusinessType.UPDATE)
	@RepeatSubmit
	@PutMapping()
	public AjaxResult<Void> edit(@Validated @RequestBody ProItemEditBo bo) {
		return toAjax(iProItemService.updateByEditBo(bo) ? 1 : 0);
	}

	/**
	 * 删除商品详情
	 */
	@ApiOperation("删除商品详情")
	@PreAuthorize("@ss.hasPermi('product:proItem:remove')")
	@Log(title = "商品详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
								   @PathVariable Long[] ids) {
		return toAjax(iProItemService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
	}
}
