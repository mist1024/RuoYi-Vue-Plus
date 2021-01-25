package com.ruoyi.winery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.List;
import java.util.Arrays;

import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.winery.domain.goods.GoodsMain;
import com.ruoyi.winery.utils.RichTextUtil;
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
import com.ruoyi.winery.domain.AppMerchant;
import com.ruoyi.winery.service.IAppMerchantService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商户Controller
 *
 * @author ruoyi
 * @date 2021-01-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/winery/merchant")
public class AppMerchantController extends BaseController {

    private final IAppMerchantService iAppMerchantService;

    /**
     * 查询商户列表
     */
    @PreAuthorize("@ss.hasPermi('winery:merchant:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppMerchant appMerchant) {
        startPage();
        LambdaQueryWrapper<AppMerchant> lqw = Wrappers.lambdaQuery(appMerchant);
        if (StringUtils.isNotBlank(appMerchant.getMchName())) {
            lqw.like(AppMerchant::getMchName, appMerchant.getMchName());
        }
        if (StringUtils.isNotBlank(appMerchant.getSubtitle())) {
            lqw.eq(AppMerchant::getSubtitle, appMerchant.getSubtitle());
        }
        if (StringUtils.isNotBlank(appMerchant.getAvatar())) {
            lqw.eq(AppMerchant::getAvatar, appMerchant.getAvatar());
        }
        if (StringUtils.isNotBlank(appMerchant.getMchDesc())) {
            lqw.eq(AppMerchant::getMchDesc, appMerchant.getMchDesc());
        }
        lqw.orderByAsc(AppMerchant::getSort);
        List<AppMerchant> list = iAppMerchantService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出商户列表
     */
    @PreAuthorize("@ss.hasPermi('winery:merchant:export')")
    @Log(title = "商户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AppMerchant appMerchant) {
        LambdaQueryWrapper<AppMerchant> lqw = new LambdaQueryWrapper<AppMerchant>(appMerchant);
        List<AppMerchant> list = iAppMerchantService.list(lqw);
        ExcelUtil<AppMerchant> util = new ExcelUtil<AppMerchant>(AppMerchant.class);
        return util.exportExcel(list, "merchant");
    }

    /**
     * 获取商户详细信息
     */
    @PreAuthorize("@ss.hasPermi('winery:merchant:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(iAppMerchantService.getById(id));
    }


    /**
     * 获取商户详细信息
     */
    @PreAuthorize("@ss.hasPermi('winery:merchant:query')")
    @GetMapping(value = "dept/{deptId}")
    public AjaxResult getInfoByDeptId(@PathVariable("deptId") String deptId) {

        return AjaxResult.success(iAppMerchantService.getOne(new LambdaQueryWrapper<AppMerchant>().eq(AppMerchant::getDeptId, deptId)));
    }

    /**
     * 新增商户
     */
    @PreAuthorize("@ss.hasPermi('winery:merchant:add')")
    @Log(title = "商户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppMerchant appMerchant) {
        String richText = appMerchant.getMchDesc();
        if (richText != null && StringUtils.isNotEmpty(richText)) {
            Document doc = RichTextUtil.setImgStyle(richText, "width: 100%");
            appMerchant.setMchDesc(doc.body().children().toString());
        }
        return toAjax(iAppMerchantService.save(appMerchant) ? 1 : 0);
    }

    /**
     * 修改商户
     */
    @PreAuthorize("@ss.hasPermi('winery:merchant:edit')")
    @Log(title = "商户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppMerchant appMerchant) {
        String richText = appMerchant.getMchDesc();
        if (richText != null && StringUtils.isNotEmpty(richText)) {
            Document doc = RichTextUtil.setImgStyle(richText, "width: 100%");
            appMerchant.setMchDesc(doc.body().children().toString());
        }
        return toAjax(iAppMerchantService.updateById(appMerchant) ? 1 : 0);
    }

    /**
     * 删除商户
     */
    @PreAuthorize("@ss.hasPermi('winery:merchant:remove')")
    @Log(title = "商户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(iAppMerchantService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
