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
import com.ruoyi.system.domain.vo.SubscribeExportVo;
import com.ruoyi.system.domain.bo.SubscribeExportBo;
import com.ruoyi.system.service.ISubscribeExportService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 预约导出
 *
 * @author ruoyi
 * @date 2023-04-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/export")
public class SubscribeExportController extends BaseController {

    private final ISubscribeExportService iSubscribeExportService;

    /**
     * 查询预约导出列表
     */
    @SaCheckPermission("system:export:list")
    @GetMapping("/list")
    public TableDataInfo<SubscribeExportVo> list(SubscribeExportBo bo, PageQuery pageQuery) {
        return iSubscribeExportService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出预约导出列表
     */
    @SaCheckPermission("system:export:export")
    @Log(title = "预约导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SubscribeExportBo bo, HttpServletResponse response) {
        List<SubscribeExportVo> list = iSubscribeExportService.queryList(bo);
        ExcelUtil.exportExcel(list, "预约导出", SubscribeExportVo.class, response);
    }

    /**
     * 获取预约导出详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:export:query")
    @GetMapping
    public R<SubscribeExportVo> getInfo(Long id) {
        return R.ok(iSubscribeExportService.queryById(id));
    }

    /**
     * 新增预约导出
     */
    @SaCheckPermission("system:export:add")
    @Log(title = "预约导出", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SubscribeExportBo bo) {
        return toAjax(iSubscribeExportService.insertByBo(bo));
    }

    /**
     * 修改预约导出
     */
    @SaCheckPermission("system:export:edit")
    @Log(title = "预约导出", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SubscribeExportBo bo) {
        return toAjax(iSubscribeExportService.updateByBo(bo));
    }

    /**
     * 删除预约导出
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:export:remove")
    @Log(title = "预约导出", businessType = BusinessType.DELETE)
    @DeleteMapping
    public R<Void> remove(Long[] ids) {
        return toAjax(iSubscribeExportService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
