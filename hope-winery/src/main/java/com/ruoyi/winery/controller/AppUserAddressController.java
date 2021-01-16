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
import com.ruoyi.winery.domain.AppUserAddress;
import com.ruoyi.winery.service.IAppUserAddressService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户收货地址Controller
 * 
 * @author ruoyi
 * @date 2021-01-16
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/user/address" )
public class AppUserAddressController extends BaseController {

    private final IAppUserAddressService iAppUserAddressService;

    /**
     * 查询用户收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('user:address:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppUserAddress appUserAddress)
    {
        startPage();
        LambdaQueryWrapper<AppUserAddress> lqw = Wrappers.lambdaQuery(appUserAddress);
        if (appUserAddress.getDeptId() != null){
            lqw.eq(AppUserAddress::getDeptId ,appUserAddress.getDeptId());
        }
        if (StringUtils.isNotBlank(appUserAddress.getUserId())){
            lqw.eq(AppUserAddress::getUserId ,appUserAddress.getUserId());
        }
        if (appUserAddress.getIsDefault() != null){
            lqw.eq(AppUserAddress::getIsDefault ,appUserAddress.getIsDefault());
        }
        if (StringUtils.isNotBlank(appUserAddress.getMobile())){
            lqw.eq(AppUserAddress::getMobile ,appUserAddress.getMobile());
        }
        if (StringUtils.isNotBlank(appUserAddress.getName())){
            lqw.like(AppUserAddress::getName ,appUserAddress.getName());
        }
        if (StringUtils.isNotBlank(appUserAddress.getAddress())){
            lqw.eq(AppUserAddress::getAddress ,appUserAddress.getAddress());
        }
        if (StringUtils.isNotBlank(appUserAddress.getRegion())){
            lqw.eq(AppUserAddress::getRegion ,appUserAddress.getRegion());
        }
        List<AppUserAddress> list = iAppUserAddressService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出用户收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('user:address:export')" )
    @Log(title = "用户收货地址" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(AppUserAddress appUserAddress) {
        LambdaQueryWrapper<AppUserAddress> lqw = new LambdaQueryWrapper<AppUserAddress>(appUserAddress);
        List<AppUserAddress> list = iAppUserAddressService.list(lqw);
        ExcelUtil<AppUserAddress> util = new ExcelUtil<AppUserAddress>(AppUserAddress. class);
        return util.exportExcel(list, "address" );
    }

    /**
     * 获取用户收货地址详细信息
     */
    @PreAuthorize("@ss.hasPermi('user:address:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) String id) {
        return AjaxResult.success(iAppUserAddressService.getById(id));
    }

    /**
     * 新增用户收货地址
     */
    @PreAuthorize("@ss.hasPermi('user:address:add')" )
    @Log(title = "用户收货地址" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppUserAddress appUserAddress) {
        return toAjax(iAppUserAddressService.save(appUserAddress) ? 1 : 0);
    }

    /**
     * 修改用户收货地址
     */
    @PreAuthorize("@ss.hasPermi('user:address:edit')" )
    @Log(title = "用户收货地址" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppUserAddress appUserAddress) {
        return toAjax(iAppUserAddressService.updateById(appUserAddress) ? 1 : 0);
    }

    /**
     * 删除用户收货地址
     */
    @PreAuthorize("@ss.hasPermi('user:address:remove')" )
    @Log(title = "用户收货地址" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(iAppUserAddressService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
