package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.service.IFtFoodDemandDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 病人报餐Controller
 *
 * @author ft
 * @date 2020-12-03
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/foodDemand")
public class FtFoodDemandDaoController extends BaseController {

    private final IFtFoodDemandDaoService iFtFoodDemandDaoService;

    /**
     * 查询病人报餐列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDemand:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtFoodDemandDao ftFoodDemandDao) {
        startPage();
//        LambdaQueryWrapper<FtFoodDemandDao> lqw = Wrappers.lambdaQuery(ftFoodDemandDao);
//        List<FtFoodDemandDao> list = iFtFoodDemandDaoService.list(lqw);
        List<FtFoodDemandDao> list = iFtFoodDemandDaoService.listNewFormatter(ftFoodDemandDao);
        return getDataTable(list);
    }

    /**
     * 导出病人报餐列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDemand:export')")
    @Log(title = "病人报餐", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtFoodDemandDao ftFoodDemandDao) {
        LambdaQueryWrapper<FtFoodDemandDao> lqw = new LambdaQueryWrapper<FtFoodDemandDao>(ftFoodDemandDao);
        List<FtFoodDemandDao> list = iFtFoodDemandDaoService.list(lqw);
        ExcelUtil<FtFoodDemandDao> util = new ExcelUtil<FtFoodDemandDao>(FtFoodDemandDao.class);
        return util.exportExcel(list, "foodDemand");
    }

    /**
     * 获取病人报餐详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDemand:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        FtFoodDemandDao ftFoodDemandDao =  iFtFoodDemandDaoService.getByIdNewFormatter(id);
        return AjaxResult.success(ftFoodDemandDao);
    }

    /**
     * 新增病人报餐
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDemand:add')")
    @Log(title = "病人报餐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtFoodDemandDao ftFoodDemandDao) {
        ftFoodDemandDao.setFlag(true);
        return toAjax(iFtFoodDemandDaoService.save(ftFoodDemandDao) ? 1 : 0);
    }

    /**
     * 修改病人报餐
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDemand:edit')")
    @Log(title = "病人报餐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtFoodDemandDao ftFoodDemandDao) {
        ftFoodDemandDao.setUpdateAt(new Date());
        return toAjax(iFtFoodDemandDaoService.updateById(ftFoodDemandDao) ? 1 : 0);
    }

    /**
     * 删除病人报餐
     */
    @PreAuthorize("@ss.hasPermi('fantang:foodDemand:remove')")
    @Log(title = "病人报餐", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtFoodDemandDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
