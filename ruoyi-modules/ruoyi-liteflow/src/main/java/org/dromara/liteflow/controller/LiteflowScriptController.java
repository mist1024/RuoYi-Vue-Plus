package org.dromara.liteflow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.liteflow.domain.bo.LiteflowScriptBo;
import org.dromara.liteflow.domain.vo.LiteflowScriptVo;
import org.dromara.liteflow.service.ILiteflowScriptService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 脚本
 *
 * @author AprilWind
 * @date 2024-06-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/liteflow/script")
public class LiteflowScriptController extends BaseController {

    private final ILiteflowScriptService liteflowScriptService;

    /**
     * 查询脚本列表
     */
    @SaCheckPermission("liteflow:script:list")
    @GetMapping("/list")
    public TableDataInfo<LiteflowScriptVo> list(LiteflowScriptBo bo, PageQuery pageQuery) {
        return liteflowScriptService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出脚本列表
     */
    @SaCheckPermission("liteflow:script:export")
    @Log(title = "脚本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(LiteflowScriptBo bo, HttpServletResponse response) {
        List<LiteflowScriptVo> list = liteflowScriptService.queryList(bo);
        ExcelUtil.exportExcel(list, "脚本", LiteflowScriptVo.class, response);
    }

    /**
     * 获取脚本详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("liteflow:script:query")
    @GetMapping("/{id}")
    public R<LiteflowScriptVo> getInfo(@NotNull(message = "主键不能为空")
                                       @PathVariable Long id) {
        return R.ok(liteflowScriptService.queryById(id));
    }

    /**
     * 新增脚本
     */
    @SaCheckPermission("liteflow:script:add")
    @Log(title = "脚本", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LiteflowScriptBo bo) {
        if (!liteflowScriptService.checkScriptIdUnique(bo)) {
            return R.fail("新增脚本'" + bo.getScriptId() + "'失败，脚本ID已存在");
        }
        return toAjax(liteflowScriptService.insertByBo(bo));
    }

    /**
     * 修改脚本
     */
    @SaCheckPermission("liteflow:script:edit")
    @Log(title = "脚本", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LiteflowScriptBo bo) {
        return toAjax(liteflowScriptService.updateByBo(bo));
    }

    /**
     * 删除脚本
     *
     * @param ids 主键串
     */
    @SaCheckPermission("liteflow:script:remove")
    @Log(title = "脚本", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(liteflowScriptService.deleteWithValidByIds(List.of(ids), true));
    }
}
