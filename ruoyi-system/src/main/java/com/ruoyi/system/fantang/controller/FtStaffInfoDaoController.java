package com.ruoyi.system.fantang.controller;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtStaffInfoDao;
import com.ruoyi.system.fantang.service.IFtStaffInfoDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 员工管理Controller
 *
 * @author ft
 * @date 2020-11-24
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/staffInfo")
public class FtStaffInfoDaoController extends BaseController {

    private final IFtStaffInfoDaoService iFtStaffInfoDaoService;

    /**
     * 查询员工管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:list')")
    @GetMapping("/staffList")
    public TableDataInfo staffList(FtStaffInfoDao ftStaffInfoDao) {
        startPage();
        LambdaQueryWrapper<FtStaffInfoDao> lqw = Wrappers.lambdaQuery(ftStaffInfoDao);
        lqw.eq(FtStaffInfoDao::getStaffType, 1);
        if (StringUtils.isNotBlank(ftStaffInfoDao.getName())) {
            lqw.like(FtStaffInfoDao::getName, ftStaffInfoDao.getName());
        }
        if (StringUtils.isNotBlank(ftStaffInfoDao.getPost())) {
            lqw.eq(FtStaffInfoDao::getPost, ftStaffInfoDao.getPost());
        }
        if (ftStaffInfoDao.getFlag() != null) {
            lqw.eq(FtStaffInfoDao::getFlag, ftStaffInfoDao.getFlag());
        }
        List<FtStaffInfoDao> list = iFtStaffInfoDaoService.list(lqw);
        return getDataTable(list);
    }

    @GetMapping("/staffListWithDepart")
    public AjaxResult staffListWithDepart(FtStaffInfoDao ftStaffInfoDao) {
        return AjaxResult.success(iFtStaffInfoDaoService.selectStaffInfoWithDepart(ftStaffInfoDao));
    }

    /**
     * 查询护工管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:list')")
    @GetMapping("/careStaffList")
    public TableDataInfo careStaffList(FtStaffInfoDao ftStaffInfoDao) {
        startPage();
        LambdaQueryWrapper<FtStaffInfoDao> lqw = Wrappers.lambdaQuery(ftStaffInfoDao);
        lqw.eq(FtStaffInfoDao::getStaffType, 2);
        if (StringUtils.isNotBlank(ftStaffInfoDao.getName())) {
            lqw.like(FtStaffInfoDao::getName, ftStaffInfoDao.getName());
        }
        if (StringUtils.isNotBlank(ftStaffInfoDao.getPost())) {
            lqw.eq(FtStaffInfoDao::getPost, ftStaffInfoDao.getPost());
        }
        List<FtStaffInfoDao> list = iFtStaffInfoDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出员工管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:export')")
    @Log(title = "员工管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtStaffInfoDao ftStaffInfoDao) {
        LambdaQueryWrapper<FtStaffInfoDao> lqw = new LambdaQueryWrapper<FtStaffInfoDao>(ftStaffInfoDao);
        List<FtStaffInfoDao> list = iFtStaffInfoDaoService.list(lqw);
        ExcelUtil<FtStaffInfoDao> util = new ExcelUtil<FtStaffInfoDao>(FtStaffInfoDao.class);
        return util.exportExcel(list, "staffInfo");
    }

    /**
     * 获取员工管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:query')")
    @GetMapping(value = "/nursing/{staffId}")
    public AjaxResult getNursingInfo(@PathVariable("staffId") Long staffId) {
        return AjaxResult.success(iFtStaffInfoDaoService.getById(staffId));
    }

    /**
     * 获取护工管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:query')")
    @GetMapping(value = "/{staffId}")
    public AjaxResult getInfo(@PathVariable("staffId") Long staffId) {
        return AjaxResult.success(iFtStaffInfoDaoService.getById(staffId));
    }


    /**
     * 新增员工管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:add')")
    @Log(title = "员工管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtStaffInfoDao ftStaffInfoDao) {

        List<FtStaffInfoDao> list = iFtStaffInfoDaoService.list(null);
        for (FtStaffInfoDao staffInfoDao : list) {
            if (ftStaffInfoDao.getTel() != null && ftStaffInfoDao.getTel().equals(staffInfoDao.getTel())) {
                return AjaxResult.error("该电话号码已存在");
            }
        }

        // 判断是否有所属公司
        if (ftStaffInfoDao.getCorpName() == null) {
            ftStaffInfoDao.setStaffType(1L);
        } else {
            ftStaffInfoDao.setStaffType(2L);
        }

        // 判断密码是否为空
        if (ftStaffInfoDao.getPassword() == null || ftStaffInfoDao.getPassword().equals("")) {
            ftStaffInfoDao.setPassword("123456");
        }

        ftStaffInfoDao.setCreateAt(new Date());
        ftStaffInfoDao.setDeptList(ftStaffInfoDao.getDeptList());

        iFtStaffInfoDaoService.save(ftStaffInfoDao);

        return AjaxResult.success("添加成功");
    }

    /**
     * 修改员工管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:edit')")
    @Log(title = "员工管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtStaffInfoDao ftStaffInfoDao) {
        return toAjax(iFtStaffInfoDaoService.updateById(ftStaffInfoDao) ? 1 : 0);
    }


    /**
     * 修改护工管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:edit')")
    @Log(title = "护工管理", businessType = BusinessType.UPDATE)
    @PutMapping("/nursing")
    public AjaxResult nursingEdit(@RequestBody JSONObject param) {
        FtStaffInfoDao dao = new FtStaffInfoDao();
        dao.setStaffId(param.getLong("staffId"));
        dao.setCorpName(param.getString("corpName"));
        dao.setCreateAt(new DateTime());
        dao.setSex(param.getInteger("sex"));
        dao.setDeptList(param.getString("deptList"));
        dao.setStaffType(param.getLong("staffType"));
        dao.setName(param.getString("name"));
        return toAjax(iFtStaffInfoDaoService.updateById(dao) ? 1 : 0);
    }


    /**
     * 删除员工管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:staffInfo:remove')")
    @Log(title = "员工管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{staffIds}")
    public AjaxResult remove(@PathVariable Long[] staffIds) {
        return toAjax(iFtStaffInfoDaoService.removeByIds(Arrays.asList(staffIds)) ? 1 : 0);
    }
}