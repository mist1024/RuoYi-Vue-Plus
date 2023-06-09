package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.RsaSecurityVo;
import com.ruoyi.system.domain.bo.RsaSecurityBo;
import com.ruoyi.system.service.IRsaSecurityService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 请求RSA数据加解密
 *
 * @author ruoyi
 * @date 2023-05-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/security")
public class RsaSecurityController extends BaseController {

    private final IRsaSecurityService iRsaSecurityService;

    /**
     * 查询请求RSA数据加解密列表
     */
    @SaCheckPermission("system:security:list")
    @GetMapping("/list")
    public TableDataInfo<RsaSecurityVo> list(RsaSecurityBo bo, PageQuery pageQuery) {
        return iRsaSecurityService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出请求RSA数据加解密列表
     */
    @SaCheckPermission("system:security:export")
    @Log(title = "请求RSA数据加解密", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(RsaSecurityBo bo, HttpServletResponse response) {
        List<RsaSecurityVo> list = iRsaSecurityService.queryList(bo);
        ExcelUtil.exportExcel(list, "请求RSA数据加解密", RsaSecurityVo.class, response);
    }

    /**
     * 获取请求RSA数据加解密详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:security:query")
    @GetMapping
    public R<RsaSecurityVo> getInfo(Long id) {
        return R.ok(iRsaSecurityService.queryById(id));
    }

    /**
     * 新增请求RSA数据加解密
     */
    @SaCheckPermission("system:security:add")
    @Log(title = "请求RSA数据加解密", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody RsaSecurityBo bo) {
        iRsaSecurityService.insertByBo(bo);
        return toAjax(1);
    }

    /**
     * 修改请求RSA数据加解密
     */
    @SaCheckPermission("system:security:edit")
    @Log(title = "请求RSA数据加解密", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody RsaSecurityBo bo) {
        iRsaSecurityService.updateByBo(bo);
        return toAjax(1);
    }

    /**
     * 删除请求RSA数据加解密
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:security:remove")
    @Log(title = "请求RSA数据加解密", businessType = BusinessType.DELETE)
    @DeleteMapping
    public R<Void> remove(Long[] ids) {
        return toAjax(iRsaSecurityService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
