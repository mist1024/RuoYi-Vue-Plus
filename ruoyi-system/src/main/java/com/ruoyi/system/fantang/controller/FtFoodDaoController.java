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
import com.ruoyi.system.fantang.domain.FtFoodDao;
import com.ruoyi.system.fantang.service.IFtFoodDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 食品管理Controller
 *
 * @author ft
 * @date 2020-11-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/food")
public class FtFoodDaoController extends BaseController {

    private final IFtFoodDaoService iFtFoodDaoService;

    /**
     * 查询食品管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:food:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtFoodDao ftFoodDao) {
        startPage();
        LambdaQueryWrapper<FtFoodDao> lqw = Wrappers.lambdaQuery(ftFoodDao);
        if (StringUtils.isNotBlank(ftFoodDao.getName())) {
            lqw.like(FtFoodDao::getName, ftFoodDao.getName());
        }
        if (ftFoodDao.getPrice() != null) {
            lqw.eq(FtFoodDao::getPrice, ftFoodDao.getPrice());
        }
        if (ftFoodDao.getType() != null) {
            lqw.eq(FtFoodDao::getType, ftFoodDao.getType());
        }
        List<FtFoodDao> list = iFtFoodDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出食品管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:food:export')")
    @Log(title = "食品管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtFoodDao ftFoodDao) {
        LambdaQueryWrapper<FtFoodDao> lqw = new LambdaQueryWrapper<FtFoodDao>(ftFoodDao);
        List<FtFoodDao> list = iFtFoodDaoService.list(lqw);
        ExcelUtil<FtFoodDao> util = new ExcelUtil<FtFoodDao>(FtFoodDao.class);
        return util.exportExcel(list, "food");
    }

    /**
     * 获取食品管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:food:query')")
    @GetMapping(value = "/{foodId}")
    public AjaxResult getInfo(@PathVariable("foodId") Long foodId) {
        return AjaxResult.success(iFtFoodDaoService.getById(foodId));
    }

    /**
     * 新增食品管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:food:add')")
    @Log(title = "食品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtFoodDao ftFoodDao) {
        return toAjax(iFtFoodDaoService.save(ftFoodDao) ? 1 : 0);
    }

    /**
     * 修改食品管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:food:edit')")
    @Log(title = "食品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtFoodDao ftFoodDao) {
        return toAjax(iFtFoodDaoService.updateById(ftFoodDao) ? 1 : 0);
    }

    /**
     * 删除食品管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:food:remove')")
    @Log(title = "食品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{foodIds}")
    public AjaxResult remove(@PathVariable Long[] foodIds) {
        return toAjax(iFtFoodDaoService.removeByIds(Arrays.asList(foodIds)) ? 1 : 0);
    }
}
