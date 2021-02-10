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
import com.ruoyi.system.fantang.domain.FtPatientDao;
import com.ruoyi.system.fantang.domain.FtSyncConflictGenDao;
import com.ruoyi.system.fantang.service.IFtPatientDaoService;
import com.ruoyi.system.fantang.service.IFtSyncConflictGenDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 同步冲突Controller
 *
 * @author ft
 * @date 2020-12-24
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/syncConflict")
public class FtSyncConflictGenDaoController extends BaseController {

    private final IFtSyncConflictGenDaoService iFtSyncConflictGenDaoService;

    private final IFtPatientDaoService iFftPatientDaoService;


    /**
     * 处理冲突
     */
    @PostMapping("/solveConflict")
    public AjaxResult solveConflict(@RequestBody FtSyncConflictGenDao syncConflictGenDao) {

        Integer patientFlag = syncConflictGenDao.getPatientFlag();
        Long patientId = syncConflictGenDao.getPatientId();

        if (patientFlag==2){
            if (syncConflictGenDao.getHospitalId().equals(syncConflictGenDao.getOldHospitalId())){
                FtPatientDao patientDao = iFftPatientDaoService.getById(patientId);
                patientDao.setDepartId(syncConflictGenDao.getDepartId());
                patientDao.setBedId(syncConflictGenDao.getBedId());
                patientDao.setName(syncConflictGenDao.getName());
                iFftPatientDaoService.updateById(patientDao);
            }else {
                FtPatientDao ftPatientDao = new FtPatientDao();
                ftPatientDao.setDepartId(syncConflictGenDao.getDepartId());
                ftPatientDao.setBedId(syncConflictGenDao.getBedId());
                ftPatientDao.setName(syncConflictGenDao.getName());
                iFftPatientDaoService.save(ftPatientDao);
            }
        }

        syncConflictGenDao.setIsSolve(1);
        iFtSyncConflictGenDaoService.updateById(syncConflictGenDao);

        return AjaxResult.success("已处理");
    }

    /**
     * 查询同步冲突列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:syncConflict:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtSyncConflictGenDao ftSyncConflictGenDao) {
        startPage();
        LambdaQueryWrapper<FtSyncConflictGenDao> lqw = Wrappers.lambdaQuery(ftSyncConflictGenDao);
        if (StringUtils.isNotBlank(ftSyncConflictGenDao.getHospitalId())) {
            lqw.eq(FtSyncConflictGenDao::getHospitalId, ftSyncConflictGenDao.getHospitalId());
        }
        if (StringUtils.isNotBlank(ftSyncConflictGenDao.getName())) {
            lqw.like(FtSyncConflictGenDao::getName, ftSyncConflictGenDao.getName());
        }
        if (StringUtils.isNotBlank(ftSyncConflictGenDao.getDepartName())) {
            lqw.like(FtSyncConflictGenDao::getDepartName, ftSyncConflictGenDao.getDepartName());
        }
        if (StringUtils.isNotBlank(ftSyncConflictGenDao.getBedId())) {
            lqw.eq(FtSyncConflictGenDao::getBedId, ftSyncConflictGenDao.getBedId());
        }
        if (StringUtils.isNotBlank(ftSyncConflictGenDao.getOldHospitalId())) {
            lqw.eq(FtSyncConflictGenDao::getOldHospitalId, ftSyncConflictGenDao.getOldHospitalId());
        }
        if (StringUtils.isNotBlank(ftSyncConflictGenDao.getOldName())) {
            lqw.like(FtSyncConflictGenDao::getOldName, ftSyncConflictGenDao.getOldName());
        }
        if (StringUtils.isNotBlank(ftSyncConflictGenDao.getOldDepartName())) {
            lqw.like(FtSyncConflictGenDao::getOldDepartName, ftSyncConflictGenDao.getOldDepartName());
        }
        if (StringUtils.isNotBlank(ftSyncConflictGenDao.getOldBedId())) {
            lqw.eq(FtSyncConflictGenDao::getOldBedId, ftSyncConflictGenDao.getOldBedId());
        }
        if (ftSyncConflictGenDao.getDepartId() != null) {
            lqw.eq(FtSyncConflictGenDao::getDepartId, ftSyncConflictGenDao.getDepartId());
        }
        if (ftSyncConflictGenDao.getOldDepartId() != null) {
            lqw.eq(FtSyncConflictGenDao::getOldDepartId, ftSyncConflictGenDao.getOldDepartId());
        }
        if (ftSyncConflictGenDao.getIsSolve()!=null){
            lqw.eq(FtSyncConflictGenDao::getIsSolve,ftSyncConflictGenDao.getIsSolve());
        }
        List<FtSyncConflictGenDao> list = iFtSyncConflictGenDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出同步冲突列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:syncConflict:export')")
    @Log(title = "同步冲突", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtSyncConflictGenDao ftSyncConflictGenDao) {
        LambdaQueryWrapper<FtSyncConflictGenDao> lqw = new LambdaQueryWrapper<FtSyncConflictGenDao>(ftSyncConflictGenDao);
        List<FtSyncConflictGenDao> list = iFtSyncConflictGenDaoService.list(lqw);
        ExcelUtil<FtSyncConflictGenDao> util = new ExcelUtil<FtSyncConflictGenDao>(FtSyncConflictGenDao.class);
        return util.exportExcel(list, "syncConflict");
    }

    /**
     * 获取同步冲突详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:syncConflict:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iFtSyncConflictGenDaoService.getById(id));
    }

    /**
     * 新增同步冲突
     */
    @PreAuthorize("@ss.hasPermi('fantang:syncConflict:add')")
    @Log(title = "同步冲突", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtSyncConflictGenDao ftSyncConflictGenDao) {
        return toAjax(iFtSyncConflictGenDaoService.save(ftSyncConflictGenDao) ? 1 : 0);
    }

    /**
     * 修改同步冲突
     */
    @PreAuthorize("@ss.hasPermi('fantang:syncConflict:edit')")
    @Log(title = "同步冲突", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtSyncConflictGenDao ftSyncConflictGenDao) {
        return toAjax(iFtSyncConflictGenDaoService.updateById(ftSyncConflictGenDao) ? 1 : 0);
    }

    /**
     * 删除同步冲突
     */
    @PreAuthorize("@ss.hasPermi('fantang:syncConflict:remove')")
    @Log(title = "同步冲突", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtSyncConflictGenDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
