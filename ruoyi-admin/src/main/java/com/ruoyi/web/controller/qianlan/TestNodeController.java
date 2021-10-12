package com.ruoyi.web.controller.qianlan;

import java.util.List;
import java.util.Arrays;
import java.util.Map;

import com.ruoyi.system.domain.TestNode;
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
import com.ruoyi.system.domain.vo.TestNodeVo;
import com.ruoyi.system.domain.bo.TestNodeBo;
import com.ruoyi.system.service.ITestNodeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 节点维护Controller
 *
 * @author qianlan
 * @date 2021-08-08
 */
@Validated
@Api(value = "节点维护控制器", tags = {"节点维护管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/node")
public class TestNodeController extends BaseController {

	private final ITestNodeService iTestNodeService;

	/**
	 * 获取根节点的名字用于前端页面展示
	 *
	 * @return
	 */
	@ApiOperation("/查询根节点的名字")
	@GetMapping("/listRootName")
	public AjaxResult getTags() {
		List list = iTestNodeService.getTags();
		return AjaxResult.success(list);
	}

	/**
	 * 查询节点以children的方式展示
	 */
	@ApiOperation("/查询节点以children形式展示")
	//@PreAuthorize("@ss.hasPermi('system:node:list')")
	@GetMapping("/listWithChildren")
	public AjaxResult listWithChildren(String nodeName) {
		List<TestNode> nodeList = iTestNodeService.queryListWithChildren(nodeName);

		if (nodeList == null) {
			List<TestNode> list = iTestNodeService.queryListWithSizhi(nodeName);
			return AjaxResult.success(list);
		} else {
			return AjaxResult.success(nodeList);
		}
	}

	/**
	 * 查询节点维护列表
	 */
	@ApiOperation("查询节点维护列表")
	@PreAuthorize("@ss.hasPermi('system:node:list')")
	@GetMapping("/list")
	public AjaxResult list(@Validated TestNode node) {
		List<TestNode> list = iTestNodeService.queryLists(node);
		return (list != null) ? AjaxResult.success(list) : AjaxResult.error("查询无果");
	}

	/**
	 * 导出节点维护列表
	 */
	@ApiOperation("导出节点维护列表")
	@PreAuthorize("@ss.hasPermi('system:node:export')")
	@Log(title = "节点维护", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult<TestNodeVo> export(@Validated TestNodeBo bo) {
		List<TestNodeVo> list = iTestNodeService.queryList(bo);
		ExcelUtil<TestNodeVo> util = new ExcelUtil<TestNodeVo>(TestNodeVo.class);
		return util.exportExcel(list, "节点维护");
	}

	/**
	 * 获取节点维护详细信息
	 */
	@ApiOperation("获取节点维护详细信息")
	@PreAuthorize("@ss.hasPermi('system:node:query')")
	@GetMapping("/{id}")
	public AjaxResult<TestNodeVo> getInfo(@NotNull(message = "主键不能为空")
										  @PathVariable("id") Long id) {
		return AjaxResult.success(iTestNodeService.queryById(id));
	}

	/**
	 * 新增节点维护
	 */
	@ApiOperation("新增节点维护")
	@PreAuthorize("@ss.hasPermi('system:node:add')")
	@Log(title = "节点维护", businessType = BusinessType.INSERT)
	@RepeatSubmit
	@PostMapping()
	public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody TestNodeBo bo) {
		return toAjax(iTestNodeService.insertByBo(bo) ? 1 : 0);
	}

	/**
	 * 新增多个节点维护
	 */
	@ApiOperation("新增多个节点维护")
	@Log(title = "节点维护", businessType = BusinessType.INSERT)
	@RepeatSubmit
	@PostMapping("/addMultiple")
	public AjaxResult<Void> addMultiple(@RequestBody Map map) {
		return iTestNodeService.addMultiple(map);
	}

	/**
	 * 修改节点维护
	 */
	@ApiOperation("修改节点维护")
	@PreAuthorize("@ss.hasPermi('system:node:edit')")
	@Log(title = "节点维护", businessType = BusinessType.UPDATE)
	@RepeatSubmit
	@PutMapping()
	public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody TestNodeBo bo) {
		return toAjax(iTestNodeService.updateByBo(bo) ? 1 : 0);
	}

	/**
	 * 删除节点维护
	 */
	@ApiOperation("删除节点维护")
	@PreAuthorize("@ss.hasPermi('system:node:remove')")
	@Log(title = "节点维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
								   @PathVariable Long[] ids) {
		return toAjax(iTestNodeService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
	}
}
