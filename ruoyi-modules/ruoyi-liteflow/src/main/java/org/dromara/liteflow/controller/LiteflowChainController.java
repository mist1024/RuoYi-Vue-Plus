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
import org.dromara.liteflow.domain.bo.LiteflowChainBo;
import org.dromara.liteflow.domain.vo.LiteflowChainVo;
import org.dromara.liteflow.service.ILiteflowChainService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 编排规则
 *
 * @author AprilWind
 * @date 2024-06-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/liteflow/chain")
public class LiteflowChainController extends BaseController {

    private final ILiteflowChainService liteflowChainService;

    /**
     * 查询编排规则列表
     */
    @SaCheckPermission("liteflow:chain:list")
    @GetMapping("/list")
    public TableDataInfo<LiteflowChainVo> list(LiteflowChainBo bo, PageQuery pageQuery) {
        return liteflowChainService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出编排规则列表
     */
    @SaCheckPermission("liteflow:chain:export")
    @Log(title = "编排规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(LiteflowChainBo bo, HttpServletResponse response) {
        List<LiteflowChainVo> list = liteflowChainService.queryList(bo);
        ExcelUtil.exportExcel(list, "编排规则", LiteflowChainVo.class, response);
    }

    /**
     * 获取编排规则详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("liteflow:chain:query")
    @GetMapping("/{id}")
    public R<LiteflowChainVo> getInfo(@NotNull(message = "主键不能为空")
                                      @PathVariable Long id) {
        return R.ok(liteflowChainService.queryById(id));
    }

    /**
     * 新增编排规则
     */
    @SaCheckPermission("liteflow:chain:add")
    @Log(title = "编排规则", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LiteflowChainBo bo) {
        if (!liteflowChainService.checkChainNameUnique(bo)) {
            return R.fail("新增编排规则'" + bo.getChainName() + "'失败，规则名称已存在");
        }
        return toAjax(liteflowChainService.insertByBo(bo));
    }

    /**
     * 修改编排规则
     */
    @SaCheckPermission("liteflow:chain:edit")
    @Log(title = "编排规则", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LiteflowChainBo bo) {
        return toAjax(liteflowChainService.updateByBo(bo));
    }

    /**
     * 删除编排规则
     *
     * @param ids 主键串
     */
    @SaCheckPermission("liteflow:chain:remove")
    @Log(title = "编排规则", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(liteflowChainService.deleteWithValidByIds(List.of(ids), true));
    }
}
