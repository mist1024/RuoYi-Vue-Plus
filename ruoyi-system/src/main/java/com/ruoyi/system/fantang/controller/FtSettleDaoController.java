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
import com.ruoyi.system.fantang.domain.FtSettleDao;
import com.ruoyi.system.fantang.service.IFtSettleDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 结算报Controller
 *
 * @author ft
 * @date 2020-11-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/settle")
public class FtSettleDaoController extends BaseController {

    private final IFtSettleDaoService iFtSettleDaoService;


    /**
     * 查询结算报列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:settle:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtSettleDao ftSettleDao) {
        startPage();
        LambdaQueryWrapper<FtSettleDao> lqw = Wrappers.lambdaQuery(ftSettleDao);
        if (ftSettleDao.getSettleAt() != null) {
            lqw.eq(FtSettleDao::getSettleAt, ftSettleDao.getSettleAt());
        }
        if (ftSettleDao.getPrice() != null) {
            lqw.eq(FtSettleDao::getPrice, ftSettleDao.getPrice());
        }
        if (ftSettleDao.getPayable() != null) {
            lqw.eq(FtSettleDao::getPayable, ftSettleDao.getPayable());
        }
        if (ftSettleDao.getReceipts() != null) {
            lqw.eq(FtSettleDao::getReceipts, ftSettleDao.getReceipts());
        }
        if (StringUtils.isNotBlank(ftSettleDao.getType())) {
            lqw.eq(FtSettleDao::getType, ftSettleDao.getType());
        }
        if (ftSettleDao.getRefund() != null) {
            lqw.eq(FtSettleDao::getRefund, ftSettleDao.getRefund());
        }
        List<FtSettleDao> list = iFtSettleDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出结算报列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:settle:export')")
    @Log(title = "结算报", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtSettleDao ftSettleDao) {
        LambdaQueryWrapper<FtSettleDao> lqw = new LambdaQueryWrapper<FtSettleDao>(ftSettleDao);
        List<FtSettleDao> list = iFtSettleDaoService.list(lqw);
        ExcelUtil<FtSettleDao> util = new ExcelUtil<FtSettleDao>(FtSettleDao.class);
        return util.exportExcel(list, "settle");
    }

    /**
     * 获取结算报详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:settle:query')")
    @GetMapping(value = "/{settleId}")
    public AjaxResult getInfo(@PathVariable("settleId") Long settleId) {
        return AjaxResult.success(iFtSettleDaoService.getById(settleId));
    }

    /**
     * 新增结算报
     */
    @PreAuthorize("@ss.hasPermi('fantang:settle:add')")
    @Log(title = "结算报", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtSettleDao ftSettleDao) {
        ftSettleDao.setSettleAt(new Date());
        ftSettleDao.setReceipts(ftSettleDao.getNetPeceipt());
        return toAjax(iFtSettleDaoService.save(ftSettleDao) ? 1 : 0);
    }

    /**
     * 修改结算报
     */
    @PreAuthorize("@ss.hasPermi('fantang:settle:edit')")
    @Log(title = "结算报", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtSettleDao ftSettleDao) {
        return toAjax(iFtSettleDaoService.updateById(ftSettleDao) ? 1 : 0);
    }

    /**
     * 删除结算报
     */
    @PreAuthorize("@ss.hasPermi('fantang:settle:remove')")
    @Log(title = "结算报", businessType = BusinessType.DELETE)
    @DeleteMapping("/{settleIds}")
    public AjaxResult remove(@PathVariable Long[] settleIds) {
        return toAjax(iFtSettleDaoService.removeByIds(Arrays.asList(settleIds)) ? 1 : 0);
    }
}
