package com.ruoyi.winery.controller.goods;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.List;
import java.util.Arrays;

import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.select.Elements;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.winery.utils.RichTextUtil;
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
import com.ruoyi.winery.domain.goods.GoodsMain;
import com.ruoyi.winery.service.IGoodsMainService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import static com.ruoyi.common.utils.SecurityUtils.*;

/**
 * 商品信息Controller
 *
 * @author ruoyi
 * @date 2020-12-28
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/goods/goods_main")
public class GoodsMainController extends BaseController {

    private final IGoodsMainService iWineryGoodsService;

    /**
     * 查询商品信息列表
     */
    @PreAuthorize("@ss.hasPermi('goods:goods_main:list')")
    @GetMapping("/list")
    public TableDataInfo list(GoodsMain goodsMain) {
        startPage();
        LambdaQueryWrapper<GoodsMain> lqw = Wrappers.lambdaQuery(goodsMain);


        // 不是系统管理员且不是小程序用户的时候仅能看到自己部门的
        lqw.eq(!isAdmin() && !getUsername().contains("mini-"),
                GoodsMain::getDeptId, getDeptId());


        if (StringUtils.isNotBlank(goodsMain.getGoodsName())) {
            lqw.like(GoodsMain::getGoodsName, goodsMain.getGoodsName());
        }
        if (StringUtils.isNotBlank(goodsMain.getGoodsAlias())) {
            lqw.eq(GoodsMain::getGoodsAlias, goodsMain.getGoodsAlias());
        }
        if (StringUtils.isNotBlank(goodsMain.getGoodsType())) {
            lqw.eq(GoodsMain::getGoodsType, goodsMain.getGoodsType());
        }
        if (StringUtils.isNotBlank(goodsMain.getGoodsSpec())) {
            lqw.eq(GoodsMain::getGoodsSpec, goodsMain.getGoodsSpec());
        }
        if (StringUtils.isNotBlank(goodsMain.getGoodsDesc())) {
            lqw.eq(GoodsMain::getGoodsDesc, goodsMain.getGoodsDesc());
        }
        if (StringUtils.isNotBlank(goodsMain.getGoodsFaceImg())) {
            lqw.eq(GoodsMain::getGoodsFaceImg, goodsMain.getGoodsFaceImg());
        }
        if (StringUtils.isNotBlank(goodsMain.getGoodsImg())) {
            lqw.eq(GoodsMain::getGoodsImg, goodsMain.getGoodsImg());
        }
        lqw.orderByAsc(GoodsMain::getSort);

        List<GoodsMain> list = iWineryGoodsService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出商品信息列表
     */
    @PreAuthorize("@ss.hasPermi('goods:goods_main:export')")
    @Log(title = "商品信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GoodsMain goodsMain) {
        LambdaQueryWrapper<GoodsMain> lqw = new LambdaQueryWrapper<GoodsMain>(goodsMain);
        List<GoodsMain> list = iWineryGoodsService.list(lqw);
        ExcelUtil<GoodsMain> util = new ExcelUtil<GoodsMain>(GoodsMain.class);
        return util.exportExcel(list, "winery_goods");
    }

    /**
     * 获取商品信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:goods_main:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(iWineryGoodsService.getById(id));
    }

    /**
     * 新增商品信息
     */
    @PreAuthorize("@ss.hasPermi('goods:goods_main:add')")
    @Log(title = "商品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(UsernamePasswordAuthenticationToken token, @RequestBody GoodsMain goodsMain) {
//        goodsMain.setDeptId(getDeptId());
        goodsMain.setCreateBy(getUsername());
        String richText = goodsMain.getGoodsDesc();
        if (richText != null && StringUtils.isNotEmpty(richText)) {
            Document doc = RichTextUtil.setImgStyle(richText, "width: 100%");
            goodsMain.setGoodsDesc(doc.body().children().toString());
        }
        return toAjax(iWineryGoodsService.save(goodsMain) ? 1 : 0);
    }

    /**
     * 修改商品信息
     */
    @PreAuthorize("@ss.hasPermi('goods:goods_main:edit')")
    @Log(title = "商品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsMain goodsMain) {
        goodsMain.setUpdateBy(getUsername());
        String richText = goodsMain.getGoodsDesc();
        if (richText != null && StringUtils.isNotEmpty(richText)) {
            Document doc = RichTextUtil.setImgStyle(richText, "width: 100%");
            goodsMain.setGoodsDesc(doc.body().children().toString());
        }
        return toAjax(iWineryGoodsService.updateById(goodsMain) ? 1 : 0);
    }

    /**
     * 删除商品信息
     */
    @PreAuthorize("@ss.hasPermi('goods:goods_main:remove')")
    @Log(title = "商品信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iWineryGoodsService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
