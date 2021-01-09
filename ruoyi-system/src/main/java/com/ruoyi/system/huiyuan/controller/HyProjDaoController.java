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
import com.ruoyi.system.huiyuan.domain.HyProjDao;
import com.ruoyi.system.huiyuan.service.IHyProjDaoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 项目信息Controller
 * 
 * @author ryo
 * @date 2021-01-09
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/huiyuan/proj" )
public class HyProjDaoController extends BaseController {

    private final IHyProjDaoService iHyProjDaoService;

    /**
     * 查询项目信息列表
     */
    @PreAuthorize("@ss.hasPermi('huiyuan:proj:list')")
    @GetMapping("/list")
    public TableDataInfo list(HyProjDao hyProjDao) {
        startPage();
        List<HyProjDao> list = iHyProjDaoService.queryList(hyProjDao);
        return getDataTable(list);
    }

    /**
     * 导出项目信息列表
     */
    @PreAuthorize("@ss.hasPermi('huiyuan:proj:export')" )
    @Log(title = "项目信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(HyProjDao hyProjDao) {
        List<HyProjDao> list = iHyProjDaoService.queryList(hyProjDao);
        ExcelUtil<HyProjDao> util = new ExcelUtil<HyProjDao>(HyProjDao.class);
        return util.exportExcel(list, "proj" );
    }

    /**
     * 获取项目信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('huiyuan:proj:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iHyProjDaoService.getById(id));
    }

    /**
     * 新增项目信息
     */
    @PreAuthorize("@ss.hasPermi('huiyuan:proj:add')" )
    @Log(title = "项目信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HyProjDao hyProjDao) {
        return toAjax(iHyProjDaoService.save(hyProjDao) ? 1 : 0);
    }

    /**
     * 修改项目信息
     */
    @PreAuthorize("@ss.hasPermi('huiyuan:proj:edit')" )
    @Log(title = "项目信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HyProjDao hyProjDao) {
        return toAjax(iHyProjDaoService.updateById(hyProjDao) ? 1 : 0);
    }

    /**
     * 删除项目信息
     */
    @PreAuthorize("@ss.hasPermi('huiyuan:proj:remove')" )
    @Log(title = "项目信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iHyProjDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
