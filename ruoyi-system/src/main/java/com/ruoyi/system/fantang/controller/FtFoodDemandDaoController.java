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
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.service.IFtFoodDemandDaoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 病人报餐Controller
 * 
 * @author ft
 * @date 2020-11-26
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/foodDemand" )
public class FtFoodDemandDaoController extends BaseController {

    private final IFtFoodDemandDaoService iFtFoodDemandDaoService;

    /**
     * 查询病人报餐列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDemand:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtFoodDemandDao ftFoodDemandDao)
    {
        startPage();
        LambdaQueryWrapper<FtFoodDemandDao> lqw = Wrappers.lambdaQuery(ftFoodDemandDao);
        if (ftFoodDemandDao.getType() != null){
            lqw.eq(FtFoodDemandDao::getType ,ftFoodDemandDao.getType());
        }
        if (ftFoodDemandDao.getUpdateAt() != null){
            lqw.eq(FtFoodDemandDao::getUpdateAt ,ftFoodDemandDao.getUpdateAt());
        }
        if (ftFoodDemandDao.getUpdateFrom() != null){
            lqw.eq(FtFoodDemandDao::getUpdateFrom ,ftFoodDemandDao.getUpdateFrom());
        }
        List<FtFoodDemandDao> list = iFtFoodDemandDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出病人报餐列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDemand:export')" )
    @Log(title = "病人报餐" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(FtFoodDemandDao ftFoodDemandDao) {
        LambdaQueryWrapper<FtFoodDemandDao> lqw = new LambdaQueryWrapper<FtFoodDemandDao>(ftFoodDemandDao);
        List<FtFoodDemandDao> list = iFtFoodDemandDaoService.list(lqw);
        ExcelUtil<FtFoodDemandDao> util = new ExcelUtil<FtFoodDemandDao>(FtFoodDemandDao. class);
        return util.exportExcel(list, "foodDemand" );
    }

    /**
     * 获取病人报餐详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDemand:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iFtFoodDemandDaoService.getById(id));
    }

    /**
     * 新增病人报餐
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDemand:add')" )
    @Log(title = "病人报餐" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtFoodDemandDao ftFoodDemandDao) {
        return toAjax(iFtFoodDemandDaoService.save(ftFoodDemandDao) ? 1 : 0);
    }

    /**
     * 修改病人报餐
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDemand:edit')" )
    @Log(title = "病人报餐" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtFoodDemandDao ftFoodDemandDao) {
        return toAjax(iFtFoodDemandDaoService.updateById(ftFoodDemandDao) ? 1 : 0);
    }

    /**
     * 删除病人报餐
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDemand:remove')" )
    @Log(title = "病人报餐" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtFoodDemandDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
