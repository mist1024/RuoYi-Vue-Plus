package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtPrepaymentVo;
import com.ruoyi.system.fantang.service.IFtPrepaymentDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 收费管理Controller
 *
 * @author ft
 * @date 2020-11-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/prepayment")
public class FtPrepaymentDaoController extends BaseController {

    private final IFtPrepaymentDaoService iFtPrepaymentDaoService;


    @GetMapping("/getCountById/{patientId}")
    public AjaxResult getCountById(@PathVariable("patientId") Long patiendId) {
        return AjaxResult.success(iFtPrepaymentDaoService.getCountById(patiendId));
    }


    // 查询所有待缴费列表
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:list')")
    @GetMapping("/listNoPrepay")
    public TableDataInfo listNoPrepay() {
        List<com.ruoyi.system.fantang.vo.FtPrepaymentVo> list = iFtPrepaymentDaoService.listNoPrepay();
        return getDataTable(list);
    }

    // 查询所有已缴费列表
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:list')")
    @GetMapping("/listPrepay")
    public TableDataInfo listPrepay() {
        List<com.ruoyi.system.fantang.vo.FtPrepaymentVo> list = iFtPrepaymentDaoService.listPrepay();
        return getDataTable(list);
    }

    // 查询所有已结算列表
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:list')")
    @GetMapping("/listAllPrepay")
    public TableDataInfo listAllPrepay() {
        List<com.ruoyi.system.fantang.vo.FtPrepaymentVo> list = iFtPrepaymentDaoService.listAllPrepay();
        return getDataTable(list);
    }


    /**
     * 查询收费管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtPrepaymentVo ftPrepaymentDao) {
        startPage();
        LambdaQueryWrapper<FtPrepaymentVo> lqw = Wrappers.lambdaQuery(ftPrepaymentDao);
        if (ftPrepaymentDao.getCollectAt() != null) {
            lqw.eq(FtPrepaymentVo::getCollectAt, ftPrepaymentDao.getCollectAt());
        }
        if (ftPrepaymentDao.getSettlementAt() != null) {
            lqw.eq(FtPrepaymentVo::getSettlementAt, ftPrepaymentDao.getSettlementAt());
        }
        if (ftPrepaymentDao.getSettlementFlag() != null) {
            lqw.eq(FtPrepaymentVo::getSettlementFlag, ftPrepaymentDao.getSettlementFlag());
        }
        if (ftPrepaymentDao.getPrepaid() != null) {
            lqw.eq(FtPrepaymentVo::getPrepaid, ftPrepaymentDao.getPrepaid());
        }
        if (ftPrepaymentDao.getPrepaidAt() != null) {
            lqw.eq(FtPrepaymentVo::getPrepaidAt, ftPrepaymentDao.getPrepaidAt());
        }
        if (ftPrepaymentDao.getPrepaidAt() != null) {
            lqw.eq(FtPrepaymentVo::getPrepaidAt, ftPrepaymentDao.getPrepaidAt());
        }
        List<FtPrepaymentVo> list = iFtPrepaymentDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出收费管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:export')")
    @Log(title = "收费管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtPrepaymentVo ftPrepaymentDao) {
        LambdaQueryWrapper<FtPrepaymentVo> lqw = new LambdaQueryWrapper<FtPrepaymentVo>(ftPrepaymentDao);
        List<FtPrepaymentVo> list = iFtPrepaymentDaoService.list(lqw);
        ExcelUtil<FtPrepaymentVo> util = new ExcelUtil<FtPrepaymentVo>(FtPrepaymentVo.class);
        return util.exportExcel(list, "prepayment");
    }

    /**
     * 获取收费管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:query')")
    @GetMapping(value = "/{prepaymentId}")
    public AjaxResult getInfo(@PathVariable("prepaymentId") Long prepaymentId) {
        return AjaxResult.success(iFtPrepaymentDaoService.getById(prepaymentId));
    }

    /**
     * 新增收费管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:add')")
    @Log(title = "收费管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtPrepaymentVo ftPrepaymentDao) {

        Long patientId = ftPrepaymentDao.getPatientId();
        QueryWrapper<FtPrepaymentVo> wrapper = new QueryWrapper<>();
        wrapper.eq("patient_id", patientId);
        if (iFtPrepaymentDaoService.getOne(wrapper).getSettlementFlag() == 1) {
            return AjaxResult.error("该病人已付费");
        }

        ftPrepaymentDao.setCollectAt(new Date());
        ftPrepaymentDao.setSettlementFlag(0);
        return toAjax(iFtPrepaymentDaoService.save(ftPrepaymentDao) ? 1 : 0);
    }

    /**
     * 修改收费管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:edit')")
    @Log(title = "收费管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtPrepaymentVo ftPrepaymentDao) {
        return toAjax(iFtPrepaymentDaoService.updateById(ftPrepaymentDao) ? 1 : 0);
    }

    /**
     * 删除收费管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:remove')")
    @Log(title = "收费管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{prepaymentIds}")
    public AjaxResult remove(@PathVariable Long[] prepaymentIds) {
        return toAjax(iFtPrepaymentDaoService.removeByIds(Arrays.asList(prepaymentIds)) ? 1 : 0);
    }
}
