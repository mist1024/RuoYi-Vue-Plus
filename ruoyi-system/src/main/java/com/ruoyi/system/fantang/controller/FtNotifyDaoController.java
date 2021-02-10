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
import com.ruoyi.system.fantang.domain.FtNotifyDao;
import com.ruoyi.system.fantang.service.IFtNotifyDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统信息Controller
 *
 * @author ft
 * @date 2020-12-17
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/notify")
public class FtNotifyDaoController extends BaseController {

    private final IFtNotifyDaoService iFtNotifyDaoService;


    @GetMapping("/isHaveNewMsg")
    public AjaxResult isHaveNewMsg() {
        QueryWrapper<FtNotifyDao> wrapper = new QueryWrapper<>();
        wrapper.eq("read_flag", 0);
        wrapper.eq("message_type",1);
        List<FtNotifyDao> list = iFtNotifyDaoService.list(wrapper);
        int size = list.size();
        String msgBody = "";
        if (list.size() > 0) {
            msgBody = "有 " + list.size() + " 条病患冲突消息待处理";
        }
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("size", size);
        messageData.put("msgBody", msgBody);

        return AjaxResult.success(messageData);
    }


    /**
     * 查询系统信息列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:notify:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtNotifyDao ftNotifyDao) {
        startPage();
        LambdaQueryWrapper<FtNotifyDao> lqw = Wrappers.lambdaQuery(ftNotifyDao);
        if (ftNotifyDao.getMessageType() != null) {
            lqw.eq(FtNotifyDao::getMessageType, ftNotifyDao.getMessageType());
        }
        if (ftNotifyDao.getScope() != null) {
            lqw.eq(FtNotifyDao::getScope, ftNotifyDao.getScope());
        }
        if (StringUtils.isNotBlank(ftNotifyDao.getMessageBody())) {
            lqw.eq(FtNotifyDao::getMessageBody, ftNotifyDao.getMessageBody());
        }
        if (ftNotifyDao.getReadFlag() != null) {
            lqw.eq(FtNotifyDao::getReadFlag, ftNotifyDao.getReadFlag());
        }
        List<FtNotifyDao> list = iFtNotifyDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出系统信息列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:notify:export')")
    @Log(title = "系统信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtNotifyDao ftNotifyDao) {
        LambdaQueryWrapper<FtNotifyDao> lqw = new LambdaQueryWrapper<FtNotifyDao>(ftNotifyDao);
        List<FtNotifyDao> list = iFtNotifyDaoService.list(lqw);
        ExcelUtil<FtNotifyDao> util = new ExcelUtil<FtNotifyDao>(FtNotifyDao.class);
        return util.exportExcel(list, "notify");
    }

    /**
     * 获取系统信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:notify:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iFtNotifyDaoService.getById(id));
    }

    /**
     * 新增系统信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:notify:add')")
    @Log(title = "系统信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtNotifyDao ftNotifyDao) {
        return toAjax(iFtNotifyDaoService.save(ftNotifyDao) ? 1 : 0);
    }

    /**
     * 修改系统信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:notify:edit')")
    @Log(title = "系统信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtNotifyDao ftNotifyDao) {
        return toAjax(iFtNotifyDaoService.updateById(ftNotifyDao) ? 1 : 0);
    }

    /**
     * 删除系统信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:notify:remove')")
    @Log(title = "系统信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtNotifyDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
