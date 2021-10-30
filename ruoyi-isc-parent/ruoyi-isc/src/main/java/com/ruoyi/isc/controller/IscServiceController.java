package com.ruoyi.isc.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.isc.domain.bo.IscAuditBo;
import com.ruoyi.isc.domain.bo.IscServiceBo;
import com.ruoyi.isc.domain.vo.IscServiceVo;
import com.ruoyi.isc.service.IIscServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 服务信息Controller
 *
 * @author Wenchao Gong
 * @date 2021-08-22
 */
@Validated
@Api(value = "服务信息控制器", tags = {"服务信息管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/isc/service")
public class IscServiceController extends BaseController {

    private final IIscServiceService iIscServiceService;

    /**
     * 查询服务信息列表
     */
    @ApiOperation("查询服务信息列表")
    @PreAuthorize("@ss.hasPermi('isc:service:list')")
    @GetMapping("/list")
    public TableDataInfo<IscServiceVo> list(@Validated IscServiceBo bo) {
        return iIscServiceService.queryPageList(bo);
    }

    /**
     * 导出服务信息列表
     */
    @ApiOperation("导出服务信息列表")
    @PreAuthorize("@ss.hasPermi('isc:service:export')")
    @Log(title = "服务信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated IscServiceBo bo, HttpServletResponse response) {
        List<IscServiceVo> list = iIscServiceService.queryList(bo);
        ExcelUtil.exportExcel(list, "服务信息", IscServiceVo.class, response);
    }

    /**
     * 获取服务信息详细信息
     */
    @ApiOperation("获取服务信息详细信息")
    @PreAuthorize("@ss.hasPermi('isc:service:query')")
    @GetMapping("/{serviceId}")
    public AjaxResult<IscServiceVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("serviceId") Long serviceId) {
        return AjaxResult.success(iIscServiceService.queryById(serviceId));
    }

    /**
     * 新增服务信息
     */
    @ApiOperation("新增服务信息")
    @PreAuthorize("@ss.hasPermi('isc:service:add')")
    @Log(title = "服务信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody IscServiceBo bo) {
        return toAjax(iIscServiceService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改服务信息
     */
    @ApiOperation("修改服务信息")
    @PreAuthorize("@ss.hasPermi('isc:service:edit')")
    @Log(title = "服务信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody IscServiceBo bo) {
        return toAjax(iIscServiceService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除服务信息
     */
    @ApiOperation("删除服务信息")
    @PreAuthorize("@ss.hasPermi('isc:service:remove')")
    @Log(title = "服务信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{serviceIds}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] serviceIds) {
        return toAjax(iIscServiceService.deleteWithValidByIds(Arrays.asList(serviceIds), true) ? 1 : 0);
    }

    /**
     * 服务信息审核
     */
    @ApiOperation("服务信息审核")
    @PreAuthorize("@ss.hasPermi('isc:service:audit')")
    @Log(title = "服务信息审核", businessType = BusinessType.AUDIT)
    @RepeatSubmit()
    @PutMapping("/audit")
    public AjaxResult<Void> audit(@Validated @RequestBody IscAuditBo bo) {
        return toAjax(iIscServiceService.audit(bo) ? 1 : 0);
    }
}
