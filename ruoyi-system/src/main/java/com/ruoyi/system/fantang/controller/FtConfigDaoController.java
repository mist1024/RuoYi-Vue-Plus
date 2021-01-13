package com.ruoyi.system.fantang.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtConfigDao;
import com.ruoyi.system.fantang.service.IFtConfigDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 饭堂参数Controller
 *
 * @author ft
 * @date 2020-12-07
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/fantangConfig")
public class FtConfigDaoController extends BaseController {

    private final IFtConfigDaoService iFtConfigDaoService;

    /**
     * 查询饭堂参数列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:fantangConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtConfigDao ftConfigDao) {
        startPage();
        LambdaQueryWrapper<FtConfigDao> lqw = Wrappers.lambdaQuery(ftConfigDao);
        if (ftConfigDao.getCorpId() != null) {
            lqw.eq(FtConfigDao::getCorpId, ftConfigDao.getCorpId());
        }
        if (StringUtils.isNotBlank(ftConfigDao.getConfigKey())) {
            lqw.eq(FtConfigDao::getConfigKey, ftConfigDao.getConfigKey());
        }
        if (StringUtils.isNotBlank(ftConfigDao.getConfigValue())) {
            lqw.eq(FtConfigDao::getConfigValue, ftConfigDao.getConfigValue());
        }
        if (ftConfigDao.getFlag() != null) {
            lqw.eq(FtConfigDao::getFlag, ftConfigDao.getFlag());
        }
        List<FtConfigDao> list = iFtConfigDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出饭堂参数列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:fantangConfig:export')")
    @Log(title = "饭堂参数", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtConfigDao ftConfigDao) {
        LambdaQueryWrapper<FtConfigDao> lqw = new LambdaQueryWrapper<FtConfigDao>(ftConfigDao);
        List<FtConfigDao> list = iFtConfigDaoService.list(lqw);
        ExcelUtil<FtConfigDao> util = new ExcelUtil<FtConfigDao>(FtConfigDao.class);
        return util.exportExcel(list, "fantangConfig");
    }

    /**
     * 获取饭堂参数详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:fantangConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iFtConfigDaoService.getById(id));
    }

    /**
     * 新增饭堂参数
     */
    @PreAuthorize("@ss.hasPermi('fantang:fantangConfig:add')")
    @Log(title = "饭堂参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtConfigDao ftConfigDao) {
        return toAjax(iFtConfigDaoService.save(ftConfigDao) ? 1 : 0);
    }

    /**
     * 修改饭堂参数
     */
    @PreAuthorize("@ss.hasPermi('fantang:fantangConfig:edit')")
    @Log(title = "饭堂参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtConfigDao ftConfigDao) {
        return toAjax(iFtConfigDaoService.updateById(ftConfigDao) ? 1 : 0);
    }

    /**
     * 删除饭堂参数
     */
    @PreAuthorize("@ss.hasPermi('fantang:fantangConfig:remove')")
    @Log(title = "饭堂参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtConfigDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }

    @PostMapping("/updateDinnerTime")
    public AjaxResult updateDinnerTime(@RequestBody JSONObject params) {


        StringBuilder configValue = new StringBuilder();


        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JSONArray breakfastJson = params.getJSONArray("breakfast");
        JSONArray lunchJson = params.getJSONArray("lunch");
        JSONArray dinnerJson = params.getJSONArray("dinner");

        List<Date> breakfastTime = breakfastJson.toJavaList(Date.class);
        for (Date date : breakfastTime) {
            String time = sdf.format(date);
            System.out.println(time);
            configValue.append(time).append(",");
        }

        List<Date> lunchTime = lunchJson.toJavaList(Date.class);
        for (Date date : lunchTime) {
            String time = sdf.format(date);
            System.out.println(time);
            configValue.append(time).append(",");
        }

        List<Date> dinnerTime = dinnerJson.toJavaList(Date.class);

        for (int i = 0; i < dinnerTime.size(); i++) {
            String time = sdf.format(dinnerTime.get(i));
            configValue.append(time);
            if (i != dinnerTime.size() - 1) {
                configValue.append(",");
            }
        }

        UpdateWrapper<FtConfigDao> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", params.getLong("id"));
        FtConfigDao ftConfigDao = new FtConfigDao();
        ftConfigDao.setConfigValue(configValue.toString());

        iFtConfigDaoService.update(ftConfigDao, wrapper);
        return AjaxResult.success("已修改");
    }

    /**
     * 修改人脸识别设备信息
     */
    @PostMapping("/updateFaceDevice")
    public AjaxResult updateFaceDevice(@RequestBody JSONObject params) {

        String deviceId = params.getString("deviceId");
        Long deviceConfigId = params.getLong("deviceConfigId");
        iFtConfigDaoService.updateConfigValue(deviceConfigId, deviceId);

        String deviceFlag = params.getString("deviceFlag");
        Long flagConfigId = params.getLong("flagConfigId");
        iFtConfigDaoService.updateConfigValue(flagConfigId, deviceFlag);

        return AjaxResult.success("已修改");
    }
}
