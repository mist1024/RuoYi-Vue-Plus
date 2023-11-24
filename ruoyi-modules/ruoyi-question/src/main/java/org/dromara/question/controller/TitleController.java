package org.dromara.question.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.question.domain.bo.TitleReq;
import org.dromara.question.domain.bo.TitleResp;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.question.domain.vo.TitleVo;
import org.dromara.question.domain.bo.TitleBo;
import org.dromara.question.service.ITitleService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 题目
 *
 * @author Lion Li
 * @date 2023-11-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/question/title")
public class TitleController extends BaseController {

    private final ITitleService titleService;

    /**
     * 查询题目列表
     */
    @SaCheckPermission("question:title:list")
    @GetMapping("/list")
    public TableDataInfo<TitleResp> list(TitleBo bo, PageQuery pageQuery) {
        return titleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出题目列表
     */
    @SaCheckPermission("question:title:export")
    @Log(title = "题目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TitleBo bo, HttpServletResponse response) {
        List<TitleVo> list = titleService.queryList(bo);
        ExcelUtil.exportExcel(list, "题目", TitleVo.class, response);
    }

    /**
     * 获取题目详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("question:title:query")
    @GetMapping("/{id}")
    public R<TitleResp> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(titleService.queryById(id));
    }

    /**
     * 新增题目
     */
    @SaCheckPermission("question:title:add")
    @Log(title = "题目", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TitleReq bo) {
        return toAjax(titleService.insertByBo(bo));
    }

    /**
     * 修改题目
     */
    @SaCheckPermission("question:title:edit")
    @Log(title = "题目", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TitleReq bo) {
        return toAjax(titleService.updateByBo(bo));
    }

    /**
     * 删除题目
     *
     * @param ids 主键串
     */
    @SaCheckPermission("question:title:remove")
    @Log(title = "题目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(titleService.deleteWithValidByIds(List.of(ids), true));
    }
}
