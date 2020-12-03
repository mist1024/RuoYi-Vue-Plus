package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtNutritionFoodDao;
import com.ruoyi.system.fantang.service.IFtNutritionFoodDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 病患营养配餐Controller
 *
 * @author ft
 * @date 2020-12-03
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/nutritionFood")
public class FtNutritionFoodDaoController extends BaseController {

    private final IFtNutritionFoodDaoService iFtNutritionFoodDaoService;

    /**
     * 查询病患营养配餐列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:nutritionFood:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtNutritionFoodDao ftNutritionFoodDao) {
        startPage();
        LambdaQueryWrapper<FtNutritionFoodDao> lqw = Wrappers.lambdaQuery(ftNutritionFoodDao);
        if (StringUtils.isNotBlank(ftNutritionFoodDao.getName())) {
            lqw.like(FtNutritionFoodDao::getName, ftNutritionFoodDao.getName());
        }
        if (ftNutritionFoodDao.getPrice() != null) {
            lqw.eq(FtNutritionFoodDao::getPrice, ftNutritionFoodDao.getPrice());
        }
        if (ftNutritionFoodDao.getFlag() != null) {
            lqw.eq(FtNutritionFoodDao::getFlag, ftNutritionFoodDao.getFlag());
        }
        List<FtNutritionFoodDao> list = iFtNutritionFoodDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出病患营养配餐列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:nutritionFood:export')")
    @Log(title = "病患营养配餐", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtNutritionFoodDao ftNutritionFoodDao) {
        LambdaQueryWrapper<FtNutritionFoodDao> lqw = new LambdaQueryWrapper<FtNutritionFoodDao>(ftNutritionFoodDao);
        List<FtNutritionFoodDao> list = iFtNutritionFoodDaoService.list(lqw);
        ExcelUtil<FtNutritionFoodDao> util = new ExcelUtil<FtNutritionFoodDao>(FtNutritionFoodDao.class);
        return util.exportExcel(list, "nutritionFood");
    }

    /**
     * 获取病患营养配餐详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:nutritionFood:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iFtNutritionFoodDaoService.getById(id));
    }

    /**
     * 新增病患营养配餐
     */
    @PreAuthorize("@ss.hasPermi('fantang:nutritionFood:add')")
    @Log(title = "病患营养配餐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtNutritionFoodDao ftNutritionFoodDao) {
        ftNutritionFoodDao.setFlag(true);
        ftNutritionFoodDao.setCreateAt(new Date());
        return toAjax(iFtNutritionFoodDaoService.save(ftNutritionFoodDao) ? 1 : 0);
    }

    /**
     * 修改病患营养配餐
     */
    @PreAuthorize("@ss.hasPermi('fantang:nutritionFood:edit')")
    @Log(title = "病患营养配餐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtNutritionFoodDao ftNutritionFoodDao) {
        return toAjax(iFtNutritionFoodDaoService.updateById(ftNutritionFoodDao) ? 1 : 0);
    }

    /**
     * 停用病患营养配餐
     */
    @PutMapping("/deactivate/{id}")
    public AjaxResult deactivate(@PathVariable("id") Long id) {
        FtNutritionFoodDao ftNutritionFoodDao = iFtNutritionFoodDaoService.getById(id);
        ftNutritionFoodDao.setFlag(false);
        iFtNutritionFoodDaoService.updateById(ftNutritionFoodDao);
        return AjaxResult.success("停用成功");
    }

    /**
     * 删除病患营养配餐
     */
    @PreAuthorize("@ss.hasPermi('fantang:nutritionFood:remove')")
    @Log(title = "病患营养配餐", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtNutritionFoodDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
