package com.ruoyi.isc.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.isc.domain.bo.IscApplicationBo;
import com.ruoyi.isc.domain.vo.IscApplicationVo;
import com.ruoyi.isc.service.IIscApplicationService;
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
 * 应用信息Controller
 *
 * @author Wenchao Gong
 * @date 2021-09-08
 */
@Validated
@Api(value = "应用信息控制器", tags = {"应用信息管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/isc/application")
public class IscApplicationController extends BaseController {

    private final IIscApplicationService iIscApplicationService;

    /**
     * 查询应用信息列表
     */
    @ApiOperation("查询应用信息列表")
    @PreAuthorize("@ss.hasPermi('isc:application:list')")
    @GetMapping("/list")
    public TableDataInfo<IscApplicationVo> list(@Validated(QueryGroup.class) IscApplicationBo bo) {
        return iIscApplicationService.queryPageList(bo);
    }

    /**
     * 导出应用信息列表
     */
    @ApiOperation("导出应用信息列表")
    @PreAuthorize("@ss.hasPermi('isc:application:export')")
    @Log(title = "应用信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated IscApplicationBo bo, HttpServletResponse response) {
        List<IscApplicationVo> list = iIscApplicationService.queryList(bo);
        ExcelUtil.exportExcel(list, "应用信息", IscApplicationVo.class, response);
    }

    /**
     * 获取应用信息详细信息
     */
    @ApiOperation("获取应用信息详细信息")
    @PreAuthorize("@ss.hasPermi('isc:application:query')")
    @GetMapping("/{applicationId}")
    public AjaxResult<IscApplicationVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("applicationId") Long applicationId) {
        return AjaxResult.success(iIscApplicationService.queryById(applicationId));
    }

    /**
     * 新增应用信息
     */
    @ApiOperation("新增应用信息")
    @PreAuthorize("@ss.hasPermi('isc:application:add')")
    @Log(title = "应用信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody IscApplicationBo bo) {
        return toAjax(iIscApplicationService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改应用信息
     */
    @ApiOperation("修改应用信息")
    @PreAuthorize("@ss.hasPermi('isc:application:edit')")
    @Log(title = "应用信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody IscApplicationBo bo) {
        return toAjax(iIscApplicationService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除应用信息
     */
    @ApiOperation("删除应用信息")
    @PreAuthorize("@ss.hasPermi('isc:application:remove')")
    @Log(title = "应用信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{applicationIds}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] applicationIds) {
        return toAjax(iIscApplicationService.deleteWithValidByIds(Arrays.asList(applicationIds), true) ? 1 : 0);
    }
}
