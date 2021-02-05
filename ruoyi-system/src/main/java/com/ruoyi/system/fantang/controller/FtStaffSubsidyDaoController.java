package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.*;
import com.ruoyi.system.fantang.mapper.FtStaffInfoDaoMapper;
import com.ruoyi.system.fantang.service.IFtConfigDaoService;
import com.ruoyi.system.fantang.service.IFtStaffInfoDaoService;
import com.ruoyi.system.fantang.service.IFtStaffSubsidyDaoService;
import com.ruoyi.system.fantang.vo.FtStaffSubsidyVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    private final IFtConfigDaoService configDaoService;

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
        wrapper.notIn("staff_id", noGiveoutList);
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

        Integer ret = iFtStaffSubsidyDaoService.insertBatchStaffSubsidy(ftStaffSubsidyDaoList);
        logger.info("发放补贴：{} 人", ret);

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("本次发放合计：%d 人;", ret));

        // 统计发放补贴后，有多少人的补贴超过了设置的上限150元；
        // 1 先去应用数据配置表中找到该企业配置的最大补贴上限
        QueryWrapper<FtConfigDao> wrapper1 = new QueryWrapper<>();
        wrapper1
                .eq("config_key", "max_subsidy")
                .eq("corp_id", 1);
        List<FtConfigDao> list = configDaoService.list(wrapper1);
        if (list.size() <= 0 ) {
            logger.error("获取系统配置的最大补贴数据失败！");
            return AjaxResult.error("获取系统配置的最大补贴数据失败！");
        }
        FtConfigDao configDao = list.get(0);
        Integer maxSubsidy = Integer.parseInt(configDao.getConfigValue());

        // 2 去员工信息表中统计余额信息超过 最大补贴数的信息
        QueryWrapper<FtStaffInfoDao> wrapper2 = new QueryWrapper<>();
        wrapper2.gt("balance", 150);
        int overflowCount = staffInfoDaoService.count(wrapper2);
        builder.append(String.format("本次统计超出余额上限员工合计：%d", overflowCount));

        if (overflowCount <= 0) {
            builder.append(String.format("本次补贴发放没有累计余额超出上限员工"));
            return AjaxResult.success(builder);
        }

        // 3 如果有超过的余额的，进行余额冲减操作
        iFtStaffSubsidyDaoService.reBalance(subsidy.getType(), maxSubsidy);

        // 4 更新员工表的余额信息，超过 最大数的全部冲减
        UpdateWrapper<FtStaffInfoDao> updateWrapper=new UpdateWrapper<>();
        FtStaffInfoDao staffInfoDao = new FtStaffInfoDao();
        staffInfoDao.setBalance(BigDecimal.valueOf(maxSubsidy));
        updateWrapper.gt("balance", maxSubsidy);
        staffInfoDaoService.update(staffInfoDao, updateWrapper);
        builder.append(String.format("本次超出余额上限员工全部进行冲减，请查看日志"));
        return AjaxResult.success(builder);
    }
}
