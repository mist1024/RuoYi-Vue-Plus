package com.ruoyi.winery.controller.winery;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.List;
import java.util.Arrays;

import com.ruoyi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.winery.domain.winery.WineryGoodsSpec;
import com.ruoyi.winery.service.IWineryGoodsSpecService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商品规格Controller
 * 
 * @author ruoyi
 * @date 2020-12-28
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/winery/spec" )
public class WineryGoodsSpecController extends BaseController {

    private final IWineryGoodsSpecService iWineryGoodsSpecService;

    /**
     * 查询商品规格列表
     */
    @PreAuthorize("@ss.hasPermi('winery:spec:list')")
    @GetMapping("/list")
    public TableDataInfo list(WineryGoodsSpec wineryGoodsSpec)
    {
        startPage();
        LambdaQueryWrapper<WineryGoodsSpec> lqw = Wrappers.lambdaQuery(wineryGoodsSpec);
        if (wineryGoodsSpec.getDeptId() != null){
            lqw.eq(WineryGoodsSpec::getDeptId ,wineryGoodsSpec.getDeptId());
        }
        if (StringUtils.isNotBlank(wineryGoodsSpec.getSpecName())){
            lqw.like(WineryGoodsSpec::getSpecName ,wineryGoodsSpec.getSpecName());
        }
        if (StringUtils.isNotBlank(wineryGoodsSpec.getSpecDesc())){
            lqw.eq(WineryGoodsSpec::getSpecDesc ,wineryGoodsSpec.getSpecDesc());
        }
        if (StringUtils.isNotBlank(wineryGoodsSpec.getSpecImg())){
            lqw.eq(WineryGoodsSpec::getSpecImg ,wineryGoodsSpec.getSpecImg());
        }
        if (wineryGoodsSpec.getSpecPrice() != null){
            lqw.eq(WineryGoodsSpec::getSpecPrice ,wineryGoodsSpec.getSpecPrice());
        }
        if (wineryGoodsSpec.getDetailSpec() != null){
            lqw.eq(WineryGoodsSpec::getDetailSpec ,wineryGoodsSpec.getDetailSpec());
        }
        List<WineryGoodsSpec> list = iWineryGoodsSpecService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出商品规格列表
     */
    @PreAuthorize("@ss.hasPermi('winery:spec:export')" )
    @Log(title = "商品规格" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(WineryGoodsSpec wineryGoodsSpec) {
        LambdaQueryWrapper<WineryGoodsSpec> lqw = new LambdaQueryWrapper<WineryGoodsSpec>(wineryGoodsSpec);
        List<WineryGoodsSpec> list = iWineryGoodsSpecService.list(lqw);
        ExcelUtil<WineryGoodsSpec> util = new ExcelUtil<WineryGoodsSpec>(WineryGoodsSpec. class);
        return util.exportExcel(list, "spec" );
    }

    /**
     * 获取商品规格详细信息
     */
    @PreAuthorize("@ss.hasPermi('winery:spec:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iWineryGoodsSpecService.getById(id));
    }

    /**
     * 新增商品规格
     */
    @PreAuthorize("@ss.hasPermi('winery:spec:add')" )
    @Log(title = "商品规格" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WineryGoodsSpec wineryGoodsSpec) {
        return toAjax(iWineryGoodsSpecService.save(wineryGoodsSpec) ? 1 : 0);
    }

    /**
     * 修改商品规格
     */
    @PreAuthorize("@ss.hasPermi('winery:spec:edit')" )
    @Log(title = "商品规格" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WineryGoodsSpec wineryGoodsSpec) {
        return toAjax(iWineryGoodsSpecService.updateById(wineryGoodsSpec) ? 1 : 0);
    }

    /**
     * 删除商品规格
     */
    @PreAuthorize("@ss.hasPermi('winery:spec:remove')" )
    @Log(title = "商品规格" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iWineryGoodsSpecService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
