package com.ruoyi.isc.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.utils.TreeUtils;
import com.ruoyi.isc.domain.IscServiceCate;
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
import com.ruoyi.isc.domain.vo.IscServiceCateVo;
import com.ruoyi.isc.domain.bo.IscServiceCateBo;
import com.ruoyi.isc.service.IIscServiceCateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 服务分类Controller
 *
 * @author wenchao gong
 * @date 2021-08-22
 */
@Validated
@Api(value = "服务分类控制器", tags = {"服务分类管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/isc/cate")
public class IscServiceCateController extends BaseController {

    private final IIscServiceCateService cateService;

    /**
     * 查询服务分类列表
     */
    @ApiOperation("查询服务分类列表")
    @PreAuthorize("@ss.hasPermi('isc:cate:list')")
    @GetMapping("/list")
    public AjaxResult<List<IscServiceCateVo>> list(@Validated IscServiceCateBo bo)
    {
        List<IscServiceCateVo> list = cateService.queryList(bo);
        return AjaxResult.success(list);
    }

    /**
     * 获取分类下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect()
    {
        List<IscServiceCate> cates = cateService.selectCateList();
        return AjaxResult.success(TreeUtils.build(cates, (cate, tree) -> {
            tree.setId(cate.getCateId());
            tree.setParentId(cate.getParentId());
            tree.setName(cate.getCateName());
            tree.setWeight(cate.getOrderNum());
            tree.putExtra("fullPath", cate.getFullPath());
        }));
    }

    /**
     * 导出服务分类列表
     */
    @ApiOperation("导出服务分类列表")
    @PreAuthorize("@ss.hasPermi('isc:cate:export')")
    @Log(title = "服务分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated IscServiceCateBo bo, HttpServletResponse response)
    {
        List<IscServiceCateVo> list = cateService.queryList(bo);
        ExcelUtil.exportExcel(list, "服务分类", IscServiceCateVo.class, response);
    }

    /**
     * 获取服务分类详细信息
     */
    @ApiOperation("获取服务分类详细信息")
    @PreAuthorize("@ss.hasPermi('isc:cate:query')")
    @GetMapping("/{cateId}")
    public AjaxResult<IscServiceCateVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable("cateId") Long cateId)
    {
        return AjaxResult.success(cateService.queryById(cateId));
    }

    /**
     * 新增服务分类
     */
    @ApiOperation("新增服务分类")
    @PreAuthorize("@ss.hasPermi('isc:cate:add')")
    @Log(title = "服务分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody IscServiceCateBo bo)
    {
        return toAjax(cateService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改服务分类
     */
    @ApiOperation("修改服务分类")
    @PreAuthorize("@ss.hasPermi('isc:cate:edit')")
    @Log(title = "服务分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody IscServiceCateBo bo)
    {
        return toAjax(cateService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除服务分类
     */
    @ApiOperation("删除服务分类")
    @PreAuthorize("@ss.hasPermi('isc:cate:remove')")
    @Log(title = "服务分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{cateIds}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] cateIds)
    {
        return toAjax(cateService.deleteWithValidByIds(Arrays.asList(cateIds), true) ? 1 : 0);
    }
}
