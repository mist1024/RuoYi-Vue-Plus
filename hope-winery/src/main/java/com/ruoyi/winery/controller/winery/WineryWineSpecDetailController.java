package com.ruoyi.winery.controller.winery;

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
import com.ruoyi.winery.domain.winery.WineryWineSpecDetail;
import com.ruoyi.winery.service.IWineryWineSpecDetailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import static com.ruoyi.common.utils.SecurityUtils.getDeptId;
import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 葡萄酒规格详情Controller
 * 
 * @author ruoyi
 * @date 2020-12-28
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/winery/wine_detail" )
public class WineryWineSpecDetailController extends BaseController {

    private final IWineryWineSpecDetailService iWineryWineSpecDetailService;

    /**
     * 查询葡萄酒规格详情列表
     */
    @PreAuthorize("@ss.hasPermi('winery:wine_detail:list')")
    @GetMapping("/list")
    public TableDataInfo list(WineryWineSpecDetail wineryWineSpecDetail)
    {
        startPage();
        LambdaQueryWrapper<WineryWineSpecDetail> lqw = Wrappers.lambdaQuery(wineryWineSpecDetail);
        if (wineryWineSpecDetail.getDeptId() != null){
            lqw.eq(WineryWineSpecDetail::getDeptId ,wineryWineSpecDetail.getDeptId());
        }
        if (StringUtils.isNotBlank(wineryWineSpecDetail.getBrand())){
            lqw.eq(WineryWineSpecDetail::getBrand ,wineryWineSpecDetail.getBrand());
        }
        if (wineryWineSpecDetail.getCapacity() != null){
            lqw.eq(WineryWineSpecDetail::getCapacity ,wineryWineSpecDetail.getCapacity());
        }
        if (StringUtils.isNotBlank(wineryWineSpecDetail.getYear())){
            lqw.eq(WineryWineSpecDetail::getYear ,wineryWineSpecDetail.getYear());
        }
        if (StringUtils.isNotBlank(wineryWineSpecDetail.getAlcohol())){
            lqw.eq(WineryWineSpecDetail::getAlcohol ,wineryWineSpecDetail.getAlcohol());
        }
        if (StringUtils.isNotBlank(wineryWineSpecDetail.getWineType())){
            lqw.eq(WineryWineSpecDetail::getWineType ,wineryWineSpecDetail.getWineType());
        }
        if (StringUtils.isNotBlank(wineryWineSpecDetail.getWineLevel())){
            lqw.eq(WineryWineSpecDetail::getWineLevel ,wineryWineSpecDetail.getWineLevel());
        }
        if (StringUtils.isNotBlank(wineryWineSpecDetail.getGrape())){
            lqw.eq(WineryWineSpecDetail::getGrape ,wineryWineSpecDetail.getGrape());
        }
        if (StringUtils.isNotBlank(wineryWineSpecDetail.getSugarContent())){
            lqw.eq(WineryWineSpecDetail::getSugarContent ,wineryWineSpecDetail.getSugarContent());
        }
        if (StringUtils.isNotBlank(wineryWineSpecDetail.getPackingType())){
            lqw.eq(WineryWineSpecDetail::getPackingType ,wineryWineSpecDetail.getPackingType());
        }
        if (StringUtils.isNotBlank(wineryWineSpecDetail.getDecanteDuration())){
            lqw.eq(WineryWineSpecDetail::getDecanteDuration ,wineryWineSpecDetail.getDecanteDuration());
        }
        if (wineryWineSpecDetail.getCount() != null){
            lqw.eq(WineryWineSpecDetail::getCount ,wineryWineSpecDetail.getCount());
        }
        if (StringUtils.isNotBlank(wineryWineSpecDetail.getAroma())){
            lqw.eq(WineryWineSpecDetail::getAroma ,wineryWineSpecDetail.getAroma());
        }
        List<WineryWineSpecDetail> list = iWineryWineSpecDetailService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出葡萄酒规格详情列表
     */
    @PreAuthorize("@ss.hasPermi('winery:wine_detail:export')" )
    @Log(title = "葡萄酒规格详情" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(WineryWineSpecDetail wineryWineSpecDetail) {
        LambdaQueryWrapper<WineryWineSpecDetail> lqw = new LambdaQueryWrapper<WineryWineSpecDetail>(wineryWineSpecDetail);
        List<WineryWineSpecDetail> list = iWineryWineSpecDetailService.list(lqw);
        ExcelUtil<WineryWineSpecDetail> util = new ExcelUtil<WineryWineSpecDetail>(WineryWineSpecDetail. class);
        return util.exportExcel(list, "wine_detail" );
    }

    /**
     * 获取葡萄酒规格详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('winery:wine_detail:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iWineryWineSpecDetailService.getById(id));
    }

    /**
     * 新增葡萄酒规格详情
     */
    @PreAuthorize("@ss.hasPermi('winery:wine_detail:add')" )
    @Log(title = "葡萄酒规格详情" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WineryWineSpecDetail wineryWineSpecDetail) {
        wineryWineSpecDetail.setCreateBy(getUsername());
        wineryWineSpecDetail.setDeptId(getDeptId());
        return toAjax(iWineryWineSpecDetailService.save(wineryWineSpecDetail) ? 1 : 0);
    }

    /**
     * 修改葡萄酒规格详情
     */
    @PreAuthorize("@ss.hasPermi('winery:wine_detail:edit')" )
    @Log(title = "葡萄酒规格详情" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WineryWineSpecDetail wineryWineSpecDetail) {
        wineryWineSpecDetail.setUpdateBy(getUsername());
        return toAjax(iWineryWineSpecDetailService.updateById(wineryWineSpecDetail) ? 1 : 0);
    }

    /**
     * 删除葡萄酒规格详情
     */
    @PreAuthorize("@ss.hasPermi('winery:wine_detail:remove')" )
    @Log(title = "葡萄酒规格详情" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iWineryWineSpecDetailService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
