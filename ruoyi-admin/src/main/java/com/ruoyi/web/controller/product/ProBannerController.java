package com.ruoyi.web.controller.product;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.product.bo.ProBannerAddBo;
import com.ruoyi.product.bo.ProBannerEditBo;
import com.ruoyi.product.bo.ProBannerQueryBo;
import com.ruoyi.product.service.IProBannerService;
import com.ruoyi.product.vo.ProBannerVo;
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
 * banner管理Controller
 *
 * @author ruoyi
 * @date 2021-06-30
 */
@Api(value = "banner管理控制器", tags = {"banner管理管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/product/proBanner")
public class ProBannerController extends BaseController {

	private final IProBannerService iProBannerService;

	/**
	 * 查询banner管理列表
	 */
	@ApiOperation("查询banner管理列表")
	@PreAuthorize("@ss.hasPermi('system:banner:list')")
	@GetMapping("/list")
	public TableDataInfo<ProBannerVo> list(@Validated ProBannerQueryBo bo) {
		return iProBannerService.queryPageList(bo);
	}

	/**
	 * 导出banner管理列表
	 */
	@ApiOperation("导出banner管理列表")
	@PreAuthorize("@ss.hasPermi('system:banner:export')")
	@Log(title = "banner管理", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult<ProBannerVo> export(@Validated ProBannerQueryBo bo) {
		List<ProBannerVo> list = iProBannerService.queryList(bo);
		ExcelUtil<ProBannerVo> util = new ExcelUtil<ProBannerVo>(ProBannerVo.class);
		return util.exportExcel(list, "banner管理");
	}

	/**
	 * 获取banner管理详细信息
	 */
	@ApiOperation("获取banner管理详细信息")
	@PreAuthorize("@ss.hasPermi('system:banner:query')")
	@GetMapping("/{id}")
	public AjaxResult<ProBannerVo> getInfo(@NotNull(message = "主键不能为空")
										   @PathVariable("id") Long id) {
		return AjaxResult.success(iProBannerService.queryById(id));
	}

	/**
	 * 新增banner管理
	 */
	@ApiOperation("新增banner管理")
	@PreAuthorize("@ss.hasPermi('system:banner:add')")
	@Log(title = "banner管理", businessType = BusinessType.INSERT)
	@RepeatSubmit
	@PostMapping()
	public AjaxResult<Void> add(@Validated @RequestBody ProBannerAddBo bo) {
		return toAjax(iProBannerService.insertByAddBo(bo) ? 1 : 0);
	}

	/**
	 * 修改banner管理
	 */
	@ApiOperation("修改banner管理")
	@PreAuthorize("@ss.hasPermi('system:banner:edit')")
	@Log(title = "banner管理", businessType = BusinessType.UPDATE)
	@RepeatSubmit
	@PutMapping()
	public AjaxResult<Void> edit(@Validated @RequestBody ProBannerEditBo bo) {
		return toAjax(iProBannerService.updateByEditBo(bo) ? 1 : 0);
	}

	/**
	 * 删除banner管理
	 */
	@ApiOperation("删除banner管理")
	@PreAuthorize("@ss.hasPermi('system:banner:remove')")
	@Log(title = "banner管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
								   @PathVariable Long[] ids) {
		return toAjax(iProBannerService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
	}
}
