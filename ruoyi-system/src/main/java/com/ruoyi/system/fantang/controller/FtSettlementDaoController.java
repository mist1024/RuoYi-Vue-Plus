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
import com.ruoyi.system.fantang.domain.FtSettlementDao;
import com.ruoyi.system.fantang.service.IFtSettlementDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 结算管理Controller
 *
 * @author ft
 * @date 2020-12-25
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/settlement")
public class FtSettlementDaoController extends BaseController {

    private final IFtSettlementDaoService iFtSettlementDaoService;

    /**
     * 查询结算管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:settlement:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtSettlementDao ftSettlementDao) {
        startPage();
        List<FtSettlementDao> list = iFtSettlementDaoService.listWithPatient(ftSettlementDao);
        return getDataTable(list);
    }

    /**
     * 导出结算管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:settlement:export')")
    @Log(title = "结算管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtSettlementDao ftSettlementDao) {
        LambdaQueryWrapper<FtSettlementDao> lqw = new LambdaQueryWrapper<FtSettlementDao>(ftSettlementDao);
        List<FtSettlementDao> list = iFtSettlementDaoService.list(lqw);
        ExcelUtil<FtSettlementDao> util = new ExcelUtil<FtSettlementDao>(FtSettlementDao.class);
        return util.exportExcel(list, "settlement");
    }

    /**
     * 获取结算管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:settlement:query')")
    @GetMapping(value = "/{settleId}")
    public AjaxResult getInfo(@PathVariable("settleId") Long settleId) {
        return AjaxResult.success(iFtSettlementDaoService.getById(settleId));
    }

    /**
     * 新增结算管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:settlement:add')")
    @Log(title = "结算管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtSettlementDao ftSettlementDao) {
        return toAjax(iFtSettlementDaoService.save(ftSettlementDao) ? 1 : 0);
    }

    /**
     * 修改结算管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:settlement:edit')")
    @Log(title = "结算管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtSettlementDao ftSettlementDao) {
        return toAjax(iFtSettlementDaoService.updateById(ftSettlementDao) ? 1 : 0);
    }

    /**
     * 删除结算管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:settlement:remove')")
    @Log(title = "结算管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{settleIds}")
    public AjaxResult remove(@PathVariable Long[] settleIds) {
        return toAjax(iFtSettlementDaoService.removeByIds(Arrays.asList(settleIds)) ? 1 : 0);
    }
}
