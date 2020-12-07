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
import com.ruoyi.system.fantang.domain.FtCateringDao;
import com.ruoyi.system.fantang.service.IFtCateringDaoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 配餐功能Controller
 * 
 * @author ft
 * @date 2020-12-07
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/catering" )
public class FtCateringDaoController extends BaseController {

    private final IFtCateringDaoService iFtCateringDaoService;

    /**
     * 查询配餐功能列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtCateringDao ftCateringDao)
    {
        startPage();
        LambdaQueryWrapper<FtCateringDao> lqw = Wrappers.lambdaQuery(ftCateringDao);
        if (ftCateringDao.getFlag() != null){
            lqw.eq(FtCateringDao::getFlag ,ftCateringDao.getFlag());
        }
        List<FtCateringDao> list = iFtCateringDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出配餐功能列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:export')" )
    @Log(title = "配餐功能" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(FtCateringDao ftCateringDao) {
        LambdaQueryWrapper<FtCateringDao> lqw = new LambdaQueryWrapper<FtCateringDao>(ftCateringDao);
        List<FtCateringDao> list = iFtCateringDaoService.list(lqw);
        ExcelUtil<FtCateringDao> util = new ExcelUtil<FtCateringDao>(FtCateringDao. class);
        return util.exportExcel(list, "catering" );
    }

    /**
     * 获取配餐功能详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iFtCateringDaoService.getById(id));
    }

    /**
     * 新增配餐功能
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:add')" )
    @Log(title = "配餐功能" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtCateringDao ftCateringDao) {
        return toAjax(iFtCateringDaoService.save(ftCateringDao) ? 1 : 0);
    }

    /**
     * 修改配餐功能
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:edit')" )
    @Log(title = "配餐功能" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtCateringDao ftCateringDao) {
        return toAjax(iFtCateringDaoService.updateById(ftCateringDao) ? 1 : 0);
    }

    /**
     * 删除配餐功能
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:remove')" )
    @Log(title = "配餐功能" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtCateringDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
