package com.wild.web.controller.monitor;

import com.wild.common.annotation.Log;
import com.wild.common.core.controller.BaseController;
import com.wild.common.core.domain.AjaxResult;
import com.wild.common.core.page.TableDataInfo;
import com.wild.common.enums.BusinessType;
import com.wild.common.utils.poi.ExcelUtil;
import com.wild.system.domain.SysOperLog;
import com.wild.system.service.ISysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 操作日志记录
 *
 * @author Lion Li
 */
@Validated
@Api(value = "操作日志记录", tags = {"操作日志记录管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController {

    private final ISysOperLogService operLogService;

    @ApiOperation("查询操作日志记录列表")
    @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
    @GetMapping("/list")
    public TableDataInfo<SysOperLog> list(SysOperLog operLog) {
        return operLogService.selectPageOperLogList(operLog);
    }

    @ApiOperation("导出操作日志记录列表")
    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @PostMapping("/export")
    public void export(SysOperLog operLog, HttpServletResponse response) {
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        ExcelUtil.exportExcel(list, "操作日志", SysOperLog.class, response);
    }

    @ApiOperation("删除操作日志记录")
    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/{operIds}")
    public AjaxResult<Void> remove(@PathVariable Long[] operIds) {
        return toAjax(operLogService.deleteOperLogByIds(operIds));
    }

    @ApiOperation("清空操作日志记录")
    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/clean")
    public AjaxResult<Void> clean() {
        operLogService.cleanOperLog();
        return AjaxResult.success();
    }
}
