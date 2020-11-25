package com.ruoyi.system.fantang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.Date;
import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import com.ruoyi.system.fantang.domain.FtSubsidyDao;
import com.ruoyi.system.fantang.service.IFtSubsidyDaoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 补贴管理Controller
 * 
 * @author ft
 * @date 2020-11-25
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/subsidy" )
public class FtSubsidyDaoController extends BaseController {

    private final IFtSubsidyDaoService iFtSubsidyDaoService;

    /**
     * 查询补贴管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:subsidy:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtSubsidyDao ftSubsidyDao)
    {
        startPage();
        LambdaQueryWrapper<FtSubsidyDao> lqw = Wrappers.lambdaQuery(ftSubsidyDao);
        if (StringUtils.isNotBlank(ftSubsidyDao.getType())){
            lqw.eq(FtSubsidyDao::getType ,ftSubsidyDao.getType());
        }
        if (ftSubsidyDao.getPrice() != null){
            lqw.eq(FtSubsidyDao::getPrice ,ftSubsidyDao.getPrice());
        }
        if (ftSubsidyDao.getCreateAt() != null){
            lqw.eq(FtSubsidyDao::getCreateAt ,ftSubsidyDao.getCreateAt());
        }
        if (StringUtils.isNotBlank(ftSubsidyDao.getCreateBy())){
            lqw.eq(FtSubsidyDao::getCreateBy ,ftSubsidyDao.getCreateBy());
        }
        List<FtSubsidyDao> list = iFtSubsidyDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出补贴管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:subsidy:export')" )
    @Log(title = "补贴管理" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(FtSubsidyDao ftSubsidyDao) {
        LambdaQueryWrapper<FtSubsidyDao> lqw = new LambdaQueryWrapper<FtSubsidyDao>(ftSubsidyDao);
        List<FtSubsidyDao> list = iFtSubsidyDaoService.list(lqw);
        ExcelUtil<FtSubsidyDao> util = new ExcelUtil<FtSubsidyDao>(FtSubsidyDao. class);
        return util.exportExcel(list, "subsidy" );
    }

    /**
     * 获取补贴管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:subsidy:query')" )
    @GetMapping(value = "/{subsidyId}" )
    public AjaxResult getInfo(@PathVariable("subsidyId" ) Long subsidyId) {
        return AjaxResult.success(iFtSubsidyDaoService.getById(subsidyId));
    }

    /**
     * 新增补贴管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:subsidy:add')" )
    @Log(title = "补贴管理" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtSubsidyDao ftSubsidyDao) {

        ftSubsidyDao.setCreateAt(new Date());
        return toAjax(iFtSubsidyDaoService.save(ftSubsidyDao) ? 1 : 0);
    }

    /**
     * 修改补贴管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:subsidy:edit')" )
    @Log(title = "补贴管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtSubsidyDao ftSubsidyDao) {
        return toAjax(iFtSubsidyDaoService.updateById(ftSubsidyDao) ? 1 : 0);
    }

    /**
     * 删除补贴管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:subsidy:remove')" )
    @Log(title = "补贴管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{subsidyIds}" )
    public AjaxResult remove(@PathVariable Long[] subsidyIds) {
        return toAjax(iFtSubsidyDaoService.removeByIds(Arrays.asList(subsidyIds)) ? 1 : 0);
    }
}
