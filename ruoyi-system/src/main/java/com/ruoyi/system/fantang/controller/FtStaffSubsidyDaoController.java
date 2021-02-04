package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtStaffInfoDao;
import com.ruoyi.system.fantang.domain.FtStaffSubsidyDao;
import com.ruoyi.system.fantang.domain.FtSubsidyDao;
import com.ruoyi.system.fantang.mapper.FtStaffInfoDaoMapper;
import com.ruoyi.system.fantang.service.IFtStaffInfoDaoService;
import com.ruoyi.system.fantang.service.IFtStaffSubsidyDaoService;
import com.ruoyi.system.fantang.vo.FtStaffSubsidyVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 补贴流水查看Controller
 *
 * @author ft
 * @date 2020-11-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/staffSubsidy")
public class FtStaffSubsidyDaoController extends BaseController {

    private final IFtStaffSubsidyDaoService iFtStaffSubsidyDaoService;

    private final IFtStaffInfoDaoService staffInfoDaoService;

    /**
     * 查询补贴流水查看列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffSubsidy:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtStaffSubsidyDao ftStaffSubsidyDao) {
        startPage();
        LambdaQueryWrapper<FtStaffSubsidyDao> lqw = Wrappers.lambdaQuery(ftStaffSubsidyDao);
        if (StringUtils.isNotBlank(ftStaffSubsidyDao.getSubsidyType())) {
            lqw.eq(FtStaffSubsidyDao::getSubsidyType, ftStaffSubsidyDao.getSubsidyType());
        }
        if (ftStaffSubsidyDao.getIncomeType() != null) {
            lqw.eq(FtStaffSubsidyDao::getIncomeType, ftStaffSubsidyDao.getIncomeType());
        }
        if (ftStaffSubsidyDao.getPrice() != null) {
            lqw.eq(FtStaffSubsidyDao::getPrice, ftStaffSubsidyDao.getPrice());
        }
        if (ftStaffSubsidyDao.getConsumAt() != null) {
            lqw.eq(FtStaffSubsidyDao::getConsumAt, ftStaffSubsidyDao.getConsumAt());
        }
        List<FtStaffSubsidyDao> list = iFtStaffSubsidyDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出补贴流水查看列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffSubsidy:export')")
    @Log(title = "补贴流水查看", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtStaffSubsidyDao ftStaffSubsidyDao) {
        LambdaQueryWrapper<FtStaffSubsidyDao> lqw = new LambdaQueryWrapper<FtStaffSubsidyDao>(ftStaffSubsidyDao);
        List<FtStaffSubsidyDao> list = iFtStaffSubsidyDaoService.list(lqw);
        ExcelUtil<FtStaffSubsidyDao> util = new ExcelUtil<FtStaffSubsidyDao>(FtStaffSubsidyDao.class);
        return util.exportExcel(list, "staffSubsidy");
    }

    /**
     * 获取补贴流水查看详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffSubsidy:query')")
    @GetMapping(value = "/{subsidyId}")
    public AjaxResult getInfo(@PathVariable("subsidyId") Long subsidyId) {
        return AjaxResult.success(iFtStaffSubsidyDaoService.getById(subsidyId));
    }

    /**
     * 新增补贴流水查看
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffSubsidy:add')")
    @Log(title = "补贴流水查看", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtStaffSubsidyDao ftStaffSubsidyDao) {
        return toAjax(iFtStaffSubsidyDaoService.save(ftStaffSubsidyDao) ? 1 : 0);
    }

    /**
     * 修改补贴流水查看
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffSubsidy:edit')")
    @Log(title = "补贴流水查看", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtStaffSubsidyDao ftStaffSubsidyDao) {
        return toAjax(iFtStaffSubsidyDaoService.updateById(ftStaffSubsidyDao) ? 1 : 0);
    }

    /**
     * 删除补贴流水查看
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffSubsidy:remove')")
    @Log(title = "补贴流水查看", businessType = BusinessType.DELETE)
    @DeleteMapping("/{subsidyIds}")
    public AjaxResult remove(@PathVariable Long[] subsidyIds) {
        return toAjax(iFtStaffSubsidyDaoService.removeByIds(Arrays.asList(subsidyIds)) ? 1 : 0);
    }

    /**
     * 发放员工补贴
     */
    @PostMapping("/submitGiveOutSubsidy")
    public AjaxResult submitGiveOutSubsidy(@RequestBody FtStaffSubsidyVo ftStaffSubsidyVo) {

        FtSubsidyDao subsidy = ftStaffSubsidyVo.getSubsidy();
        List<String> noGiveoutList = ftStaffSubsidyVo.getNoGiveoutList();
        Date giveOutDate = ftStaffSubsidyVo.getGiveOutDate();
        QueryWrapper<FtStaffInfoDao> wrapper = new QueryWrapper<>();
        wrapper.notIn("staff_id",noGiveoutList);
        List<FtStaffInfoDao> staffData = staffInfoDaoService.list(wrapper);

        List<FtStaffSubsidyDao> ftStaffSubsidyDaoList = new ArrayList<>();

        for (FtStaffInfoDao staffDatum : staffData) {
            if (staffDatum.getGiveOutFlag()) {
                FtStaffSubsidyDao ftStaffSubsidyDao = new FtStaffSubsidyDao();
                ftStaffSubsidyDao.setStaffId(staffDatum.getStaffId());
                ftStaffSubsidyDao.setSubsidyType(subsidy.getType());
                ftStaffSubsidyDao.setIncomeType(1);
                ftStaffSubsidyDao.setPrice(subsidy.getPrice());
                ftStaffSubsidyDao.setConsumAt(giveOutDate);
                ftStaffSubsidyDaoList.add(ftStaffSubsidyDao);
            }
        }

        iFtStaffSubsidyDaoService.insertBatchStaffSubsidy(ftStaffSubsidyDaoList);

        return AjaxResult.success("发放成功");
    }
}
