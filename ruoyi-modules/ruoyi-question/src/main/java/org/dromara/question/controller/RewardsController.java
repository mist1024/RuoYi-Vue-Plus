package org.dromara.question.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
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
import org.dromara.question.domain.vo.RewardsVo;
import org.dromara.question.domain.bo.RewardsBo;
import org.dromara.question.service.IRewardsService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 奖品管理
 *
 * @author lvxudong
 * @date 2023-11-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/question/rewards")
public class RewardsController extends BaseController {

    private final IRewardsService rewardsService;

    /**
     * 查询奖品管理列表
     */
    @SaCheckPermission("question:rewards:list")
    @GetMapping("/list")
    public TableDataInfo<RewardsVo> list(RewardsBo bo, PageQuery pageQuery) {
        return rewardsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出奖品管理列表
     */
    @SaCheckPermission("question:rewards:export")
    @Log(title = "奖品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(RewardsBo bo, HttpServletResponse response) {
        List<RewardsVo> list = rewardsService.queryList(bo);
        ExcelUtil.exportExcel(list, "奖品管理", RewardsVo.class, response);
    }

    /**
     * 获取奖品管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("question:rewards:query")
    @GetMapping("/{id}")
    public R<RewardsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(rewardsService.queryById(id));
    }

    /**
     * 新增奖品管理
     */
    @SaCheckPermission("question:rewards:add")
    @Log(title = "奖品管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody RewardsBo bo) {
        return toAjax(rewardsService.insertByBo(bo));
    }

    /**
     * 修改奖品管理
     */
    @SaCheckPermission("question:rewards:edit")
    @Log(title = "奖品管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody RewardsBo bo) {
        return toAjax(rewardsService.updateByBo(bo));
    }

    /**
     * 删除奖品管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("question:rewards:remove")
    @Log(title = "奖品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(rewardsService.deleteWithValidByIds(List.of(ids), true));
    }
}
