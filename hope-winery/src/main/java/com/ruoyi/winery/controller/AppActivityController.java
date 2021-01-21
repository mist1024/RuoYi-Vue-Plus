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
import com.ruoyi.winery.domain.AppActivity;
import com.ruoyi.winery.service.IAppActivityService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 活动Controller
 *
 * @author ruoyi
 * @date 2021-01-20
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/winery/activity")
public class AppActivityController extends BaseController {

    private final IAppActivityService iAppActivityService;

    /**
     * 查询活动列表
     */
    @PreAuthorize("@ss.hasPermi('winery:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppActivity appActivity) {
        startPage();
        LambdaQueryWrapper<AppActivity> lqw = Wrappers.lambdaQuery(appActivity);
        if (StringUtils.isNotBlank(appActivity.getUrl())) {
            lqw.eq(AppActivity::getUrl, appActivity.getUrl());
        }
        if (appActivity.getType() != null) {
            lqw.eq(AppActivity::getType, appActivity.getType());
        }
        if (StringUtils.isNotBlank(appActivity.getImage())) {
            lqw.eq(AppActivity::getImage, appActivity.getImage());
        }
        if (appActivity.getImageHeight() != null) {
            lqw.eq(AppActivity::getImageHeight, appActivity.getImageHeight());
        }
        lqw.orderByAsc(AppActivity::getSort);
        List<AppActivity> list = iAppActivityService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出活动列表
     */
    @PreAuthorize("@ss.hasPermi('winery:activity:export')")
    @Log(title = "活动", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AppActivity appActivity) {
        LambdaQueryWrapper<AppActivity> lqw = new LambdaQueryWrapper<AppActivity>(appActivity);
        List<AppActivity> list = iAppActivityService.list(lqw);
        ExcelUtil<AppActivity> util = new ExcelUtil<AppActivity>(AppActivity.class);
        return util.exportExcel(list, "activity");
    }

    /**
     * 获取活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('winery:activity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(iAppActivityService.getById(id));
    }

    /**
     * 新增活动
     */
    @PreAuthorize("@ss.hasPermi('winery:activity:add')")
    @Log(title = "活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppActivity appActivity) {
        return toAjax(iAppActivityService.save(appActivity) ? 1 : 0);
    }

    /**
     * 修改活动
     */
    @PreAuthorize("@ss.hasPermi('winery:activity:edit')")
    @Log(title = "活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppActivity appActivity) {
        return toAjax(iAppActivityService.updateById(appActivity) ? 1 : 0);
    }

    /**
     * 删除活动
     */
    @PreAuthorize("@ss.hasPermi('winery:activity:remove')")
    @Log(title = "活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(iAppActivityService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }


    /**
     * 查询活动列表(开放)
     */
    @GetMapping("/open/notice")
    public AjaxResult openNotice() {


        String notice =
                "手机尾号4707的用户购买了「山之语·赤霞珠干红葡萄酒」,　" +
                "手机尾号5266的用户购买了「2021辛丑牛年纪念酒」" +
                "手机尾号3062的用户购买了「留世传奇红葡萄酒」";
        return AjaxResult.success(notice);
    }

    /**
     * 查询活动列表(开放)
     */
    @GetMapping("/open/list")
    public TableDataInfo openList(AppActivity appActivity) {
        startPage();
        LambdaQueryWrapper<AppActivity> lqw = Wrappers.lambdaQuery(appActivity);
        if (StringUtils.isNotBlank(appActivity.getUrl())) {
            lqw.eq(AppActivity::getUrl, appActivity.getUrl());
        }
        if (appActivity.getType() != null) {
            lqw.eq(AppActivity::getType, appActivity.getType());
        }
        if (StringUtils.isNotBlank(appActivity.getImage())) {
            lqw.eq(AppActivity::getImage, appActivity.getImage());
        }
        if (appActivity.getImageHeight() != null) {
            lqw.eq(AppActivity::getImageHeight, appActivity.getImageHeight());
        }
        lqw.orderByAsc(AppActivity::getSort);
        List<AppActivity> list = iAppActivityService.list(lqw);
        return getDataTable(list);
    }
}
