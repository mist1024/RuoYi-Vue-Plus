package com.ruoyi.system.huiyuan.controller;

import java.util.List;
import java.util.Arrays;

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
import com.ruoyi.system.huiyuan.domain.HyProjTalentDao;
import com.ruoyi.system.huiyuan.service.IHyProjTalentDaoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 优才项目Controller
 * 
 * @author ryo
 * @date 2021-01-09
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/huiyuan/talent" )
public class HyProjTalentDaoController extends BaseController {

    private final IHyProjTalentDaoService iHyProjTalentDaoService;

    /**
     * 查询优才项目列表
     */
    @PreAuthorize("@ss.hasPermi('huiyuan:talent:list')")
    @GetMapping("/list")
    public TableDataInfo list(HyProjTalentDao hyProjTalentDao) {
        startPage();
        List<HyProjTalentDao> list = iHyProjTalentDaoService.queryList(hyProjTalentDao);
        return getDataTable(list);
    }

    /**
     * 导出优才项目列表
     */
    @PreAuthorize("@ss.hasPermi('huiyuan:talent:export')" )
    @Log(title = "优才项目" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(HyProjTalentDao hyProjTalentDao) {
        List<HyProjTalentDao> list = iHyProjTalentDaoService.queryList(hyProjTalentDao);
        ExcelUtil<HyProjTalentDao> util = new ExcelUtil<HyProjTalentDao>(HyProjTalentDao.class);
        return util.exportExcel(list, "talent" );
    }

    /**
     * 获取优才项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('huiyuan:talent:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iHyProjTalentDaoService.getById(id));
    }

    /**
     * 新增优才项目
     */
    @PreAuthorize("@ss.hasPermi('huiyuan:talent:add')" )
    @Log(title = "优才项目" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HyProjTalentDao hyProjTalentDao) {
        return toAjax(iHyProjTalentDaoService.save(hyProjTalentDao) ? 1 : 0);
    }

    /**
     * 修改优才项目
     */
    @PreAuthorize("@ss.hasPermi('huiyuan:talent:edit')" )
    @Log(title = "优才项目" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HyProjTalentDao hyProjTalentDao) {
        return toAjax(iHyProjTalentDaoService.updateById(hyProjTalentDao) ? 1 : 0);
    }

    /**
     * 删除优才项目
     */
    @PreAuthorize("@ss.hasPermi('huiyuan:talent:remove')" )
    @Log(title = "优才项目" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iHyProjTalentDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
