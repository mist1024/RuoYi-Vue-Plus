package com.ruoyi.isc.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.isc.domain.vo.IscAppServiceVo;
import com.ruoyi.isc.domain.bo.IscAppServiceBo;
import com.ruoyi.isc.service.IIscAppServiceService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 应用服务Controller
 *
 * @author Wenchao Gong
 * @date 2021-09-08
 */
@Validated
@Api(value = "应用服务控制器", tags = {"应用服务管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/isc/appservice")
public class IscAppServiceController extends BaseController {

    private final IIscAppServiceService iIscAppServiceService;

    /**
     * 查询应用服务列表
     */
    @ApiOperation("查询应用服务列表")
    @PreAuthorize("@ss.hasPermi('isc:appservice:list')")
    @GetMapping("/list")
    public TableDataInfo<IscAppServiceVo> list(@Validated(QueryGroup.class) IscAppServiceBo bo) {
        return iIscAppServiceService.queryPageList(bo);
    }

    /**
     * 导出应用服务列表
     */
    @ApiOperation("导出应用服务列表")
    @PreAuthorize("@ss.hasPermi('isc:appservice:export')")
    @Log(title = "应用服务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated IscAppServiceBo bo, HttpServletResponse response) {
        List<IscAppServiceVo> list = iIscAppServiceService.queryList(bo);
        ExcelUtil.exportExcel(list, "应用服务", IscAppServiceVo.class, response);
    }

    /**
     * 获取应用服务详细信息
     */
    @ApiOperation("获取应用服务详细信息")
    @PreAuthorize("@ss.hasPermi('isc:appservice:query')")
    @GetMapping("/{serviceAppId}")
    public AjaxResult<IscAppServiceVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("serviceAppId") Long serviceAppId) {
        return AjaxResult.success(iIscAppServiceService.queryById(serviceAppId));
    }

    /**
     * 新增应用服务
     */
    @ApiOperation("新增应用服务")
    @PreAuthorize("@ss.hasPermi('isc:appservice:add')")
    @Log(title = "应用服务", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody IscAppServiceBo bo) {
        return toAjax(iIscAppServiceService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改应用服务
     */
    @ApiOperation("修改应用服务")
    @PreAuthorize("@ss.hasPermi('isc:appservice:edit')")
    @Log(title = "应用服务", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody IscAppServiceBo bo) {
        return toAjax(iIscAppServiceService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除应用服务
     */
    @ApiOperation("删除应用服务")
    @PreAuthorize("@ss.hasPermi('isc:appservice:remove')")
    @Log(title = "应用服务" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{serviceAppIds}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] serviceAppIds) {
        return toAjax(iIscAppServiceService.deleteWithValidByIds(Arrays.asList(serviceAppIds), true) ? 1 : 0);
    }
}
