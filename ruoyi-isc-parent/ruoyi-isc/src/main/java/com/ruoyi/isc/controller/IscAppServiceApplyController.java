package com.ruoyi.isc.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.isc.domain.bo.IscAppServiceApplyBo;
import com.ruoyi.isc.domain.bo.IscAuditBo;
import com.ruoyi.isc.domain.vo.IscAppServiceApplyVo;
import com.ruoyi.isc.service.IIscAppServiceApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 应用服务申请信息Controller
 *
 * @author Wenchao Gong
 * @date 2021-09-09
 */
@Validated
@Api(value = "应用服务申请信息控制器", tags = {"应用服务申请信息管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/isc/serviceapply")
public class IscAppServiceApplyController extends BaseController {

    private final IIscAppServiceApplyService iIscAppServiceApplyService;

    /**
     * 查询应用服务申请信息列表
     */
    @ApiOperation("查询应用服务申请信息列表")
    @PreAuthorize("@ss.hasPermi('isc:serviceapply:list')")
    @GetMapping("/list")
    public TableDataInfo<IscAppServiceApplyVo> list(@Validated(QueryGroup.class) IscAppServiceApplyBo bo) {
        return iIscAppServiceApplyService.queryPageList(bo);
    }

    /**
     * 导出应用服务申请信息列表
     */
    @ApiOperation("导出应用服务申请信息列表")
    @PreAuthorize("@ss.hasPermi('isc:serviceapply:export')")
    @Log(title = "应用服务申请信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated IscAppServiceApplyBo bo, HttpServletResponse response) {
        List<IscAppServiceApplyVo> list = iIscAppServiceApplyService.queryList(bo);
        ExcelUtil.exportExcel(list, "应用服务申请信息", IscAppServiceApplyVo.class, response);
    }

    /**
     * 获取应用服务申请信息详细信息
     */
    @ApiOperation("获取应用服务申请信息详细信息")
    @PreAuthorize("@ss.hasPermi('isc:serviceapply:query')")
    @GetMapping("/{applyId}")
    public AjaxResult<IscAppServiceApplyVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("applyId") Long applyId) {
        return AjaxResult.success(iIscAppServiceApplyService.queryById(applyId));
    }

    /**
     * 应用服务申请信息审核
     */
    @ApiOperation("应用服务申请信息审核")
    @PreAuthorize("@ss.hasPermi('isc:serviceapply:audit')")
    @Log(title = "应用服务申请信息审核", businessType = BusinessType.AUDIT)
    @RepeatSubmit()
    @PutMapping("/audit")
    public AjaxResult<Void> audit(@Validated @RequestBody IscAuditBo bo) {
        return toAjax(iIscAppServiceApplyService.audit(bo) ? 1 : 0);
    }
}
