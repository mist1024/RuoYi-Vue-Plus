package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import com.ruoyi.system.fantang.domain.FtConfigDao;
import com.ruoyi.system.fantang.service.IFtConfigDaoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 饭堂参数Controller
 * 
 * @author ft
 * @date 2020-12-07
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/fantangConfig" )
public class FtConfigDaoController extends BaseController {

    private final IFtConfigDaoService iFtConfigDaoService;

    /**
     * 查询饭堂参数列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:fantangConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtConfigDao ftConfigDao)
    {
        startPage();
        LambdaQueryWrapper<FtConfigDao> lqw = Wrappers.lambdaQuery(ftConfigDao);
        if (ftConfigDao.getCorpId() != null){
            lqw.eq(FtConfigDao::getCorpId ,ftConfigDao.getCorpId());
        }
        if (StringUtils.isNotBlank(ftConfigDao.getConfigKey())){
            lqw.eq(FtConfigDao::getConfigKey ,ftConfigDao.getConfigKey());
        }
        if (StringUtils.isNotBlank(ftConfigDao.getConfigValue())){
            lqw.eq(FtConfigDao::getConfigValue ,ftConfigDao.getConfigValue());
        }
        if (ftConfigDao.getFlag() != null){
            lqw.eq(FtConfigDao::getFlag ,ftConfigDao.getFlag());
        }
        List<FtConfigDao> list = iFtConfigDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出饭堂参数列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:fantangConfig:export')" )
    @Log(title = "饭堂参数" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(FtConfigDao ftConfigDao) {
        LambdaQueryWrapper<FtConfigDao> lqw = new LambdaQueryWrapper<FtConfigDao>(ftConfigDao);
        List<FtConfigDao> list = iFtConfigDaoService.list(lqw);
        ExcelUtil<FtConfigDao> util = new ExcelUtil<FtConfigDao>(FtConfigDao. class);
        return util.exportExcel(list, "fantangConfig" );
    }

    /**
     * 获取饭堂参数详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:fantangConfig:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iFtConfigDaoService.getById(id));
    }

    /**
     * 新增饭堂参数
     */
    @PreAuthorize("@ss.hasPermi('fantang:fantangConfig:add')" )
    @Log(title = "饭堂参数" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtConfigDao ftConfigDao) {
        return toAjax(iFtConfigDaoService.save(ftConfigDao) ? 1 : 0);
    }

    /**
     * 修改饭堂参数
     */
    @PreAuthorize("@ss.hasPermi('fantang:fantangConfig:edit')" )
    @Log(title = "饭堂参数" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtConfigDao ftConfigDao) {
        return toAjax(iFtConfigDaoService.updateById(ftConfigDao) ? 1 : 0);
    }

    /**
     * 删除饭堂参数
     */
    @PreAuthorize("@ss.hasPermi('fantang:fantangConfig:remove')" )
    @Log(title = "饭堂参数" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtConfigDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
