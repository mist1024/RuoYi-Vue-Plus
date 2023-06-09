package com.ruoyi.web.controller.material;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.bo.MaterialTalentsBo;
import com.ruoyi.system.domain.vo.MaterialTalentsVo;
import com.ruoyi.system.service.IMaterialTalentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 材料关系
 *
 * @author ruoyi
 * @date 2023-03-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/material/talents")
public class MaterialTalentsController extends BaseController {

    private final IMaterialTalentsService iMaterialTalentsService;

    /**
     * 查询材料关系列表
     */
    @SaCheckPermission("material:talents:list")
    @GetMapping("/list")
    public R<List<MaterialTalentsVo>> list(MaterialTalentsBo bo) {
        List<MaterialTalentsVo> list = iMaterialTalentsService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 导出材料关系列表
     */
    @SaCheckPermission("material:talents:export")
    @Log(title = "材料关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MaterialTalentsBo bo, HttpServletResponse response) {
        List<MaterialTalentsVo> list = iMaterialTalentsService.queryList(bo);
        ExcelUtil.exportExcel(list, "材料关系", MaterialTalentsVo.class, response);
    }

    /**
     * 获取材料关系详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("material:talents:query")
    @GetMapping
    public R<MaterialTalentsVo> getInfo(Long id) {
        return R.ok(iMaterialTalentsService.queryById(id));
    }

    /**
     * 新增材料关系
     */
    @SaCheckPermission("material:talents:add")
    @Log(title = "材料关系", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MaterialTalentsBo bo) {
        return toAjax(iMaterialTalentsService.insertByBo(bo));
    }

    /**
     * 修改材料关系
     */
    @SaCheckPermission("material:talents:edit")
    @Log(title = "材料关系", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MaterialTalentsBo bo) {
        return toAjax(iMaterialTalentsService.updateByBo(bo));
    }

    /**
     * 删除材料关系
     *
     * @param ids 主键串
     */
    @SaCheckPermission("material:talents:remove")
    @Log(title = "材料关系", businessType = BusinessType.DELETE)
    @DeleteMapping
    public R<Void> remove(Long[] ids) {
        return toAjax(iMaterialTalentsService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
