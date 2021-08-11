package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysOssConfig;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.SysOssConfigVo;
import com.ruoyi.system.domain.bo.SysOssConfigBo;
import com.ruoyi.system.service.ISysOssConfigService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 云存储配置Controller
 *
 * @author ruoyi
 * @date 2021-08-11
 */
@Validated
@Api(value = "云存储配置控制器", tags = {"云存储配置管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/sysOssConfig")
public class SysOssConfigController extends BaseController {

	private final ISysOssConfigService iSysOssConfigService;

	/**
	 * 查询云存储配置列表
	 */
	@ApiOperation("查询云存储配置列表")
	@PreAuthorize("@ss.hasPermi('system:sysOssConfig:list')")
	@GetMapping("/list")
	public TableDataInfo<SysOssConfigVo> list(@Validated SysOssConfigBo bo) {
		return iSysOssConfigService.queryPageList(bo);
	}

	/**
	 * 导出云存储配置列表
	 */
	@ApiOperation("导出云存储配置列表")
	@PreAuthorize("@ss.hasPermi('system:sysOssConfig:export')")
	@Log(title = "云存储配置", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public void export(@Validated SysOssConfigBo bo, HttpServletResponse response) {
		List<SysOssConfigVo> list = iSysOssConfigService.queryList(bo);
		ExcelUtil.exportExcel(list, "云存储配置", SysOssConfigVo.class, response);
	}

	/**
	 * 获取云存储配置详细信息
	 */
	@ApiOperation("获取云存储配置详细信息")
	@PreAuthorize("@ss.hasPermi('system:sysOssConfig:query')")
	@GetMapping("/{ossConfigId}")
	public AjaxResult<SysOssConfigVo> getInfo(@NotNull(message = "主键不能为空")
											  @PathVariable("ossConfigId") Integer ossConfigId) {
		return AjaxResult.success(iSysOssConfigService.queryById(ossConfigId));
	}

	/**
	 * 新增云存储配置
	 */
	@ApiOperation("新增云存储配置")
	@PreAuthorize("@ss.hasPermi('system:sysOssConfig:add')")
	@Log(title = "云存储配置", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody SysOssConfigBo bo) {
		if (StringUtils.isNotEmpty(bo.getConfigKey())
			&& UserConstants.NOT_UNIQUE.equals(iSysOssConfigService.checkConfigKeyUnique(bo))) {
			return AjaxResult.error("新增云配置'" + bo.getConfigKey() + "'失败，configKey已存在");
		}
		return toAjax(iSysOssConfigService.insertByBo(bo) ? 1 : 0);
	}

	/**
	 * 修改云存储配置
	 */
	@ApiOperation("修改云存储配置")
	@PreAuthorize("@ss.hasPermi('system:sysOssConfig:edit')")
	@Log(title = "云存储配置", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody SysOssConfigBo bo) {
		if (StringUtils.isNotEmpty(bo.getConfigKey())
			&& UserConstants.NOT_UNIQUE.equals(iSysOssConfigService.checkConfigKeyUnique(bo))) {
			return AjaxResult.error("修改云配置'" + bo.getConfigKey() + "'失败，configKey已存在");
		}
		return toAjax(iSysOssConfigService.updateByBo(bo) ? 1 : 0);
	}

	/**
	 * 删除云存储配置
	 */
	@ApiOperation("删除云存储配置")
	@PreAuthorize("@ss.hasPermi('system:sysOssConfig:remove')")
	@Log(title = "云存储配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ossConfigIds}")
	public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
								   @PathVariable Integer[] ossConfigIds) {
		return toAjax(iSysOssConfigService.deleteWithValidByIds(Arrays.asList(ossConfigIds), true) ? 1 : 0);
	}

	/**
	 * 状态修改
	 */
	@PreAuthorize("@ss.hasPermi('system:sysOssConfig:edit')")
	@Log(title = "云存储状态修改", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public AjaxResult changeStatus(@RequestBody SysOssConfig sysOssConfig) {
		sysOssConfig.setUpdateBy(getUsername());
		return toAjax(iSysOssConfigService.updateOssConfigStatus(sysOssConfig));
	}
}
