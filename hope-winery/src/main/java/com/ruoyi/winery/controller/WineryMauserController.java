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
import com.ruoyi.winery.domain.WineryMauser;
import com.ruoyi.winery.service.IWineryMauserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 小程序用户Controller
 * 
 * @author ruoyi
 * @date 2020-12-17
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/winery/winery_mauser" )
public class WineryMauserController extends BaseController {

    private final IWineryMauserService iWineryMauserService;

    /**
     * 查询小程序用户列表
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_mauser:list')")
    @GetMapping("/list")
    public TableDataInfo list(WineryMauser wineryMauser)
    {
        startPage();
        LambdaQueryWrapper<WineryMauser> lqw = Wrappers.lambdaQuery(wineryMauser);
        if (StringUtils.isNotBlank(wineryMauser.getStatus())){
            lqw.eq(WineryMauser::getStatus ,wineryMauser.getStatus());
        }
        if (StringUtils.isNotBlank(wineryMauser.getMobile())){
            lqw.eq(WineryMauser::getMobile ,wineryMauser.getMobile());
        }
        if (StringUtils.isNotBlank(wineryMauser.getNickName())){
            lqw.like(WineryMauser::getNickName ,wineryMauser.getNickName());
        }
        if (StringUtils.isNotBlank(wineryMauser.getUnionId())){
            lqw.eq(WineryMauser::getUnionId ,wineryMauser.getUnionId());
        }
        if (wineryMauser.getCreateTime() != null){
            lqw.eq(WineryMauser::getCreateTime ,wineryMauser.getCreateTime());
        }
        if (StringUtils.isNotBlank(wineryMauser.getDeptId())){
            lqw.eq(WineryMauser::getDeptId ,wineryMauser.getDeptId());
        }
        List<WineryMauser> list = iWineryMauserService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出小程序用户列表
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_mauser:export')" )
    @Log(title = "小程序用户" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(WineryMauser wineryMauser) {
        LambdaQueryWrapper<WineryMauser> lqw = new LambdaQueryWrapper<WineryMauser>(wineryMauser);
        List<WineryMauser> list = iWineryMauserService.list(lqw);
        ExcelUtil<WineryMauser> util = new ExcelUtil<WineryMauser>(WineryMauser. class);
        return util.exportExcel(list, "winery_mauser" );
    }

    /**
     * 获取小程序用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_mauser:query')" )
    @GetMapping(value = "/{openId}" )
    public AjaxResult getInfo(@PathVariable("openId" ) String openId) {
        return AjaxResult.success(iWineryMauserService.getById(openId));
    }

    /**
     * 新增小程序用户
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_mauser:add')" )
    @Log(title = "小程序用户" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WineryMauser wineryMauser) {
        return toAjax(iWineryMauserService.save(wineryMauser) ? 1 : 0);
    }

    /**
     * 修改小程序用户
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_mauser:edit')" )
    @Log(title = "小程序用户" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WineryMauser wineryMauser) {
        return toAjax(iWineryMauserService.updateById(wineryMauser) ? 1 : 0);
    }

    /**
     * 删除小程序用户
     */
    @PreAuthorize("@ss.hasPermi('winery:winery_mauser:remove')" )
    @Log(title = "小程序用户" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{openIds}" )
    public AjaxResult remove(@PathVariable String[] openIds) {
        return toAjax(iWineryMauserService.removeByIds(Arrays.asList(openIds)) ? 1 : 0);
    }

}
