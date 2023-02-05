package com.ruoyi.system.controller.system;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.idempotent.annotation.RepeatSubmit;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.web.core.BaseController;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.excel.utils.ExcelUtil;
import com.ruoyi.system.domain.vo.SysTenantVo;
import com.ruoyi.system.domain.bo.SysTenantBo;
import com.ruoyi.system.service.ISysTenantService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

/**
 * 租户管理
 *
 * @author Michelle.Chung
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/tenant")
public class SysTenantController extends BaseController {

    private final ISysTenantService iSysTenantService;

    /**
     * 查询租户列表
     */
    @SaCheckPermission("system:tenant:list")
    @GetMapping("/list")
    public TableDataInfo<SysTenantVo> list(SysTenantBo bo, PageQuery pageQuery) {
        return iSysTenantService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出租户列表
     */
    @SaCheckPermission("system:tenant:export")
    @Log(title = "租户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysTenantBo bo, HttpServletResponse response) {
        List<SysTenantVo> list = iSysTenantService.queryList(bo);
        ExcelUtil.exportExcel(list, "租户", SysTenantVo.class, response);
    }

    /**
     * 获取租户详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:tenant:query")
    @GetMapping("/{id}")
    public R<SysTenantVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iSysTenantService.queryById(id));
    }

    /**
     * 新增租户
     */
    @SaCheckPermission("system:tenant:add")
    @Log(title = "租户", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysTenantBo bo) {
        return toAjax(iSysTenantService.insertByBo(bo));
    }

    /**
     * 修改租户
     */
    @SaCheckPermission("system:tenant:edit")
    @Log(title = "租户", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysTenantBo bo) {
        return toAjax(iSysTenantService.updateByBo(bo));
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("system:tenantPackage:edit")
    @Log(title = "租户套餐", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody SysTenantBo bo) {
        return toAjax(iSysTenantService.updateTenantStatus(bo));
    }

    /**
     * 删除租户
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:tenant:remove")
    @Log(title = "租户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iSysTenantService.deleteWithValidByIds(List.of(ids), true));
    }
}
