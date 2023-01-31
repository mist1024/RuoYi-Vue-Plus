package com.ruoyi.system.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ArrayUtil;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.web.core.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.domain.bo.SysDeptBo;
import com.ruoyi.system.domain.vo.SysDeptVo;
import com.ruoyi.system.service.ISysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门信息
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {

    private final ISysDeptService deptService;

    /**
     * 获取部门列表
     */
    @SaCheckPermission("system:dept:list")
    @GetMapping("/list")
    public R<List<SysDeptVo>> list(SysDeptBo bo) {
        List<SysDeptVo> depts = deptService.selectDeptList(bo);
        return R.ok(depts);
    }

    /**
     * 查询部门列表（排除节点）
     *
     * @param deptId 部门ID
     */
    @SaCheckPermission("system:dept:list")
    @GetMapping("/list/exclude/{deptId}")
    public R<List<SysDeptVo>> excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        List<SysDeptVo> depts = deptService.selectDeptList(new SysDeptBo());
        depts.removeIf(d -> d.getDeptId().equals(deptId)
            || ArrayUtil.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return R.ok(depts);
    }

    /**
     * 根据部门编号获取详细信息
     *
     * @param deptId 部门ID
     */
    @SaCheckPermission("system:dept:query")
    @GetMapping(value = "/{deptId}")
    public R<SysDeptVo> getInfo(@PathVariable Long deptId) {
        deptService.checkDeptDataScope(deptId);
        return R.ok(deptService.selectDeptById(deptId));
    }

    /**
     * 新增部门
     */
    @SaCheckPermission("system:dept:add")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysDeptBo bo) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(bo))) {
            return R.fail("新增部门'" + bo.getDeptName() + "'失败，部门名称已存在");
        }
        return toAjax(deptService.insertDept(bo));
    }

    /**
     * 修改部门
     */
    @SaCheckPermission("system:dept:edit")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody SysDeptBo bo) {
        Long deptId = bo.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(bo))) {
            return R.fail("修改部门'" + bo.getDeptName() + "'失败，部门名称已存在");
        } else if (bo.getParentId().equals(deptId)) {
            return R.fail("修改部门'" + bo.getDeptName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, bo.getStatus())
            && deptService.selectNormalChildrenDeptById(deptId) > 0) {
            return R.fail("该部门包含未停用的子部门！");
        }
        return toAjax(deptService.updateDept(bo));
    }

    /**
     * 删除部门
     *
     * @param deptId 部门ID
     */
    @SaCheckPermission("system:dept:remove")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public R<Void> remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return R.warn("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return R.warn("部门存在用户,不允许删除");
        }
        deptService.checkDeptDataScope(deptId);
        return toAjax(deptService.deleteDeptById(deptId));
    }
}
