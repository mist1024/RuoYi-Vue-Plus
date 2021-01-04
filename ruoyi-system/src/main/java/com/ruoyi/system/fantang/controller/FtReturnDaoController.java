package com.ruoyi.system.fantang.controller;

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
import com.ruoyi.system.fantang.domain.FtReturnDao;
import com.ruoyi.system.fantang.service.IFtReturnDaoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 回款登记Controller
 * 
 * @author ft
 * @date 2021-01-04
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/invoiceReturn" )
public class FtReturnDaoController extends BaseController {

    private final IFtReturnDaoService iFtReturnDaoService;

    /**
     * 查询回款登记列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoiceReturn:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtReturnDao ftReturnDao) {
        startPage();
        List<FtReturnDao> list = iFtReturnDaoService.queryList(ftReturnDao);
        return getDataTable(list);
    }

    /**
     * 导出回款登记列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoiceReturn:export')" )
    @Log(title = "回款登记" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(FtReturnDao ftReturnDao) {
        List<FtReturnDao> list = iFtReturnDaoService.queryList(ftReturnDao);
        ExcelUtil<FtReturnDao> util = new ExcelUtil<FtReturnDao>(FtReturnDao.class);
        return util.exportExcel(list, "invoiceReturn" );
    }

    /**
     * 获取回款登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoiceReturn:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iFtReturnDaoService.getById(id));
    }

    /**
     * 新增回款登记
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoiceReturn:add')" )
    @Log(title = "回款登记" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtReturnDao ftReturnDao) {
        return toAjax(iFtReturnDaoService.save(ftReturnDao) ? 1 : 0);
    }

    /**
     * 修改回款登记
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoiceReturn:edit')" )
    @Log(title = "回款登记" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtReturnDao ftReturnDao) {
        return toAjax(iFtReturnDaoService.updateById(ftReturnDao) ? 1 : 0);
    }

    /**
     * 删除回款登记
     */
    @PreAuthorize("@ss.hasPermi('fantang:invoiceReturn:remove')" )
    @Log(title = "回款登记" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtReturnDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
