package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtCateringDao;
import com.ruoyi.system.fantang.domain.FtFoodDemandDao;
import com.ruoyi.system.fantang.service.IFtCateringDaoService;
import com.ruoyi.system.fantang.service.IFtFoodDemandDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 配餐功能Controller
 *
 * @author ft
 * @date 2020-12-07
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/catering")
public class FtCateringDaoController extends BaseController {

    private final IFtCateringDaoService iFtCateringDaoService;

    private final IFtFoodDemandDaoService iFtFoodDemandDaoService;

    /**
     * 查询配餐功能列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtCateringDao ftCateringDao) {
        startPage();
        List<FtCateringDao> list = iFtCateringDaoService.listNewFormatter(ftCateringDao);
        return getDataTable(list);
    }

    /**
     * 导出配餐功能列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:export')")
    @Log(title = "配餐功能", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtCateringDao ftCateringDao) {
        LambdaQueryWrapper<FtCateringDao> lqw = new LambdaQueryWrapper<FtCateringDao>(ftCateringDao);
        List<FtCateringDao> list = iFtCateringDaoService.list(lqw);
        ExcelUtil<FtCateringDao> util = new ExcelUtil<FtCateringDao>(FtCateringDao.class);
        return util.exportExcel(list, "catering");
    }

    /**
     * 获取配餐功能详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        FtCateringDao ftCateringDao = iFtCateringDaoService.getByIdNewFormatter(id);
        return AjaxResult.success(ftCateringDao);
    }

    /**
     * 新增配餐功能
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:add')")
    @Log(title = "配餐功能", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtCateringDao ftCateringDao) {
        List<Integer> types = ftCateringDao.getTypes();
        for (Integer type : types) {
            Long patientId = ftCateringDao.getPatientId();
            FtCateringDao ftCatering = new FtCateringDao();
            ftCatering.setPatientId(patientId);
            ftCatering.setNumber(ftCateringDao.getNumber());
            ftCatering.setFrequency(ftCateringDao.getFrequency());
            ftCatering.setCateringUsage(ftCateringDao.getCateringUsage());
            ftCatering.setFlag(true);
            ftCatering.setCreateAt(new Date());
            ftCatering.setType(type);
            iFtCateringDaoService.save(ftCatering);

            // 如果属于加餐，新增一条病患配餐记录，否则修改
            if (type == 4) {
                FtFoodDemandDao foodDemandDao = new FtFoodDemandDao();
                foodDemandDao.setPatientId(patientId);
                foodDemandDao.setType(type);
                foodDemandDao.setNutritionFoodId(ftCateringDao.getNumber());
                foodDemandDao.setNutritionFoodFlag(1);
                foodDemandDao.setCreateAt(new Date());
                iFtFoodDemandDaoService.save(foodDemandDao);
            } else {
                QueryWrapper<FtFoodDemandDao> wrapper = new QueryWrapper<>();
                wrapper.eq("patient_id", patientId);
                wrapper.eq("type", type);
                FtFoodDemandDao foodDemandDao = iFtFoodDemandDaoService.getOne(wrapper);
                foodDemandDao.setNutritionFoodId(ftCateringDao.getNumber());
                foodDemandDao.setNutritionFoodFlag(1);
                foodDemandDao.setUpdateAt(new Date());
                iFtFoodDemandDaoService.updateById(foodDemandDao);
            }
        }

        return AjaxResult.success("新增成功");
    }

    /**
     * 修改配餐功能
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:edit')")
    @Log(title = "配餐功能", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtCateringDao ftCateringDao) {

        Long patientId = ftCateringDao.getPatientId();
        Integer type = ftCateringDao.getType();
        Long number = ftCateringDao.getNumber();

        QueryWrapper<FtFoodDemandDao> wrapper = new QueryWrapper<>();
        wrapper.eq("patient_id", patientId);
        wrapper.eq("type", type);
        FtFoodDemandDao foodDemandDao = iFtFoodDemandDaoService.getOne(wrapper);
        foodDemandDao.setNutritionFoodId(number);
        foodDemandDao.setNutritionFoodFlag(1);
        foodDemandDao.setUpdateAt(new Date());
        iFtFoodDemandDaoService.updateById(foodDemandDao);

        return toAjax(iFtCateringDaoService.updateById(ftCateringDao) ? 1 : 0);
    }

    /**
     * 删除配餐功能
     */
    @PreAuthorize("@ss.hasPermi('fantang:catering:remove')")
    @Log(title = "配餐功能", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtCateringDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
