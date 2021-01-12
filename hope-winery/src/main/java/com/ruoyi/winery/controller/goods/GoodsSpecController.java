package com.ruoyi.winery.controller.goods;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.List;
import java.util.Arrays;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.winery.domain.goods.GoodsMain;
import com.ruoyi.winery.domain.goods.GoodsSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import com.ruoyi.winery.service.IGoodsSpecService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import static com.ruoyi.common.utils.SecurityUtils.*;

/**
 * 商品规格Controller
 *
 * @author ruoyi
 * @date 2020-12-28
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/goods/goods_spec")
public class GoodsSpecController extends BaseController {

    private final IGoodsSpecService iWineryGoodsSpecService;

    /**
     * 查询商品规格列表
     */
    @PreAuthorize("@ss.hasPermi('goods:goods_spec:list')")
    @GetMapping("/list")
    public TableDataInfo list(UsernamePasswordAuthenticationToken token, GoodsSpec goodsSpec) {
        startPage();

        LambdaQueryWrapper<GoodsSpec> lqw = Wrappers.lambdaQuery(goodsSpec);

        lqw.eq(!isAdmin(), GoodsSpec::getDeptId, getDeptId());

        if (StringUtils.isNotBlank(goodsSpec.getSpecName())) {
            lqw.like(GoodsSpec::getSpecName, goodsSpec.getSpecName());
        }
        if (StringUtils.isNotBlank(goodsSpec.getSpecDesc())) {
            lqw.eq(GoodsSpec::getSpecDesc, goodsSpec.getSpecDesc());
        }
        if (StringUtils.isNotBlank(goodsSpec.getSpecImg())) {
            lqw.eq(GoodsSpec::getSpecImg, goodsSpec.getSpecImg());
        }
        if (goodsSpec.getSpecPrice() != null) {
            lqw.eq(GoodsSpec::getSpecPrice, goodsSpec.getSpecPrice());
        }
        if (goodsSpec.getDetailSpec() != null) {
            lqw.eq(GoodsSpec::getDetailSpec, goodsSpec.getDetailSpec());
        }
        List<GoodsSpec> list = iWineryGoodsSpecService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出商品规格列表
     */
    @PreAuthorize("@ss.hasPermi('goods:goods_spec:export')")
    @Log(title = "商品规格", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GoodsSpec goodsSpec) {
        LambdaQueryWrapper<GoodsSpec> lqw = new LambdaQueryWrapper<GoodsSpec>(goodsSpec);
        List<GoodsSpec> list = iWineryGoodsSpecService.list(lqw);
        ExcelUtil<GoodsSpec> util = new ExcelUtil<GoodsSpec>(GoodsSpec.class);
        return util.exportExcel(list, "spec");
    }

    /**
     * 获取商品规格详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:goods_spec:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(iWineryGoodsSpecService.getById(id));
    }

    /**
     * 新增商品规格
     */
    @PreAuthorize("@ss.hasPermi('goods:goods_spec:add')")
    @Log(title = "商品规格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(UsernamePasswordAuthenticationToken token, @RequestBody GoodsSpec goodsSpec) {
        goodsSpec.setDeptId(getDeptId());
        return toAjax(iWineryGoodsSpecService.save(goodsSpec) ? 1 : 0);
    }

    /**
     * 修改商品规格
     */
    @PreAuthorize("@ss.hasPermi('goods:goods_spec:edit')")
    @Log(title = "商品规格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsSpec goodsSpec) {
        goodsSpec.setUpdateBy(getUsername());
        return toAjax(iWineryGoodsSpecService.updateById(goodsSpec) ? 1 : 0);
    }

    /**
     * 删除商品规格
     */
    @PreAuthorize("@ss.hasPermi('goods:goods_spec:remove')")
    @Log(title = "商品规格", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iWineryGoodsSpecService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
