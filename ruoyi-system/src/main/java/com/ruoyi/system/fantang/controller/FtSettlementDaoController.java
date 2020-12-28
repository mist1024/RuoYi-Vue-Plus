package com.ruoyi.system.fantang.controller;

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
import com.ruoyi.system.fantang.domain.FtSettlementDao;
import com.ruoyi.system.fantang.service.IFtSettlementDaoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 结算管理Controller
 * 
 * @author ft
 * @date 2020-12-25
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/settlement" )
public class FtSettlementDaoController extends BaseController {

    private final IFtSettlementDaoService iFtSettlementDaoService;

    /**
     * 查询结算管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:settlement:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtSettlementDao ftSettlementDao)
    {
        startPage();
        LambdaQueryWrapper<FtSettlementDao> lqw = Wrappers.lambdaQuery(ftSettlementDao);
        if (ftSettlementDao.getSettleAt() != null){
            lqw.eq(FtSettlementDao::getSettleAt ,ftSettlementDao.getSettleAt());
        }
        if (ftSettlementDao.getPrice() != null){
            lqw.eq(FtSettlementDao::getPrice ,ftSettlementDao.getPrice());
        }
        if (ftSettlementDao.getPayable() != null){
            lqw.eq(FtSettlementDao::getPayable ,ftSettlementDao.getPayable());
        }
        if (ftSettlementDao.getReceipts() != null){
            lqw.eq(FtSettlementDao::getReceipts ,ftSettlementDao.getReceipts());
        }
        if (StringUtils.isNotBlank(ftSettlementDao.getType())){
            lqw.eq(FtSettlementDao::getType ,ftSettlementDao.getType());
        }
        if (ftSettlementDao.getRefund() != null){
            lqw.eq(FtSettlementDao::getRefund ,ftSettlementDao.getRefund());
        }
        List<FtSettlementDao> list = iFtSettlementDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出结算管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:settlement:export')" )
    @Log(title = "结算管理" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(FtSettlementDao ftSettlementDao) {
        LambdaQueryWrapper<FtSettlementDao> lqw = new LambdaQueryWrapper<FtSettlementDao>(ftSettlementDao);
        List<FtSettlementDao> list = iFtSettlementDaoService.list(lqw);
        ExcelUtil<FtSettlementDao> util = new ExcelUtil<FtSettlementDao>(FtSettlementDao. class);
        return util.exportExcel(list, "settlement" );
    }

    /**
     * 获取结算管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:settlement:query')" )
    @GetMapping(value = "/{settleId}" )
    public AjaxResult getInfo(@PathVariable("settleId" ) Long settleId) {
        return AjaxResult.success(iFtSettlementDaoService.getById(settleId));
    }

    /**
     * 新增结算管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:settlement:add')" )
    @Log(title = "结算管理" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtSettlementDao ftSettlementDao) {
        return toAjax(iFtSettlementDaoService.save(ftSettlementDao) ? 1 : 0);
    }

    /**
     * 修改结算管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:settlement:edit')" )
    @Log(title = "结算管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtSettlementDao ftSettlementDao) {
        return toAjax(iFtSettlementDaoService.updateById(ftSettlementDao) ? 1 : 0);
    }

    /**
     * 删除结算管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:settlement:remove')" )
    @Log(title = "结算管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{settleIds}" )
    public AjaxResult remove(@PathVariable Long[] settleIds) {
        return toAjax(iFtSettlementDaoService.removeByIds(Arrays.asList(settleIds)) ? 1 : 0);
    }
}
