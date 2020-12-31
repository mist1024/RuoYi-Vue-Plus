package com.ruoyi.winery.controller;

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
import com.ruoyi.winery.domain.WineryGoods;
import com.ruoyi.winery.service.IWineryGoodsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商品信息Controller
 * 
 * @author ruoyi
 * @date 2020-12-28
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/winery/winery_goods" )
public class WineryGoodsController extends BaseController {

    private final IWineryGoodsService iWineryGoodsService;

    /**
     * 查询商品信息列表
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_goods:list')")
    @GetMapping("/list")
    public TableDataInfo list(WineryGoods wineryGoods)
    {
        startPage();
        LambdaQueryWrapper<WineryGoods> lqw = Wrappers.lambdaQuery(wineryGoods);
        if (wineryGoods.getDeptId() != null){
            lqw.eq(WineryGoods::getDeptId ,wineryGoods.getDeptId());
        }
        if (StringUtils.isNotBlank(wineryGoods.getGoodsName())){
            lqw.like(WineryGoods::getGoodsName ,wineryGoods.getGoodsName());
        }
        if (StringUtils.isNotBlank(wineryGoods.getGoodsAlias())){
            lqw.eq(WineryGoods::getGoodsAlias ,wineryGoods.getGoodsAlias());
        }
        if (StringUtils.isNotBlank(wineryGoods.getGoodsType())){
            lqw.eq(WineryGoods::getGoodsType ,wineryGoods.getGoodsType());
        }
        if (StringUtils.isNotBlank(wineryGoods.getGoodsSpec())){
            lqw.eq(WineryGoods::getGoodsSpec ,wineryGoods.getGoodsSpec());
        }
        if (StringUtils.isNotBlank(wineryGoods.getGoodsDesc())){
            lqw.eq(WineryGoods::getGoodsDesc ,wineryGoods.getGoodsDesc());
        }
        if (StringUtils.isNotBlank(wineryGoods.getGoodsFaceImg())){
            lqw.eq(WineryGoods::getGoodsFaceImg ,wineryGoods.getGoodsFaceImg());
        }
        if (StringUtils.isNotBlank(wineryGoods.getGoodsImg())){
            lqw.eq(WineryGoods::getGoodsImg ,wineryGoods.getGoodsImg());
        }
        List<WineryGoods> list = iWineryGoodsService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出商品信息列表
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_goods:export')" )
    @Log(title = "商品信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(WineryGoods wineryGoods) {
        LambdaQueryWrapper<WineryGoods> lqw = new LambdaQueryWrapper<WineryGoods>(wineryGoods);
        List<WineryGoods> list = iWineryGoodsService.list(lqw);
        ExcelUtil<WineryGoods> util = new ExcelUtil<WineryGoods>(WineryGoods. class);
        return util.exportExcel(list, "winery_goods" );
    }

    /**
     * 获取商品信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_goods:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iWineryGoodsService.getById(id));
    }

    /**
     * 新增商品信息
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_goods:add')" )
    @Log(title = "商品信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WineryGoods wineryGoods) {
        return toAjax(iWineryGoodsService.save(wineryGoods) ? 1 : 0);
    }

    /**
     * 修改商品信息
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_goods:edit')" )
    @Log(title = "商品信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WineryGoods wineryGoods) {
        return toAjax(iWineryGoodsService.updateById(wineryGoods) ? 1 : 0);
    }

    /**
     * 删除商品信息
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_goods:remove')" )
    @Log(title = "商品信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iWineryGoodsService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
