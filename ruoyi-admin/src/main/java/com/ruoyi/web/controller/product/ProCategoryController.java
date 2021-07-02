package com.ruoyi.web.controller.product;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.product.bo.ProCategoryAddBo;
import com.ruoyi.product.bo.ProCategoryEditBo;
import com.ruoyi.product.bo.ProCategoryQueryBo;
import com.ruoyi.product.service.IProCategoryService;
import com.ruoyi.product.vo.ProCategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

/**
 * 商品服务类别Controller
 *
 * @author ruoyi
 * @date 2021-03-14
 */
@Api(value = "商品服务类别控制器", tags = {"商品服务类别管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/product/proCategory")
public class ProCategoryController extends BaseController {

	private final IProCategoryService iProCategoryService;

	/**
	 * 查询商品服务类别列表
	 */
	@ApiOperation("查询商品服务类别列表")
	@PreAuthorize("@ss.hasPermi('product:proCategory:list')")
	@GetMapping("/list")
	public TableDataInfo<ProCategoryVo> list(@Validated ProCategoryQueryBo bo) {
		return iProCategoryService.queryPageList(bo);
	}

	@PreAuthorize("@ss.hasPermi('product:proCategory:list')")
	@GetMapping("/listCategoriesWithoutPage")
	public List<ProCategoryVo> listCategoriesWithoutPage(ProCategoryQueryBo proCategory) {
		return iProCategoryService.queryList(proCategory);
	}

	/**
	 * 导出商品服务类别列表
	 */
	@ApiOperation("导出商品服务类别列表")
	@PreAuthorize("@ss.hasPermi('product:proCategory:export')")
	@Log(title = "商品服务类别", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult<ProCategoryVo> export(@Validated ProCategoryQueryBo bo) {
		List<ProCategoryVo> list = iProCategoryService.queryList(bo);
		ExcelUtil<ProCategoryVo> util = new ExcelUtil<>(ProCategoryVo.class);
		return util.exportExcel(list, "商品服务类别");
	}

	/**
	 * 获取商品服务类别详细信息
	 */
	@PreAuthorize("@ss.hasPermi('product:proCategory:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return AjaxResult.success(iProCategoryService.getById(id));
	}

	/**
	 * 新增商品服务类别
	 */
	@ApiOperation("新增商品服务类别")
	@PreAuthorize("@ss.hasPermi('product:proCategory:add')")
	@Log(title = "商品服务类别", businessType = BusinessType.INSERT)
	@RepeatSubmit
	@PostMapping()
	public AjaxResult<Void> add(@Validated @RequestBody ProCategoryAddBo bo) {
		return toAjax(iProCategoryService.insertByAddBo(bo) ? 1 : 0);
	}

	/**
	 * 修改商品服务类别
	 */
	@ApiOperation("修改商品服务类别")
	@PreAuthorize("@ss.hasPermi('product:proCategory:edit')")
	@Log(title = "商品服务类别", businessType = BusinessType.UPDATE)
	@RepeatSubmit
	@PutMapping()
	public AjaxResult<Void> edit(@Validated @RequestBody ProCategoryEditBo bo) {
		return toAjax(iProCategoryService.updateByEditBo(bo) ? 1 : 0);
	}

	/**
	 * 删除商品服务类别
	 */
	@ApiOperation("删除商品服务类别")
	@PreAuthorize("@ss.hasPermi('product:proCategory:remove')")
	@Log(title = "商品服务类别", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
								   @PathVariable Long[] ids) {
		return toAjax(iProCategoryService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
	}
}
