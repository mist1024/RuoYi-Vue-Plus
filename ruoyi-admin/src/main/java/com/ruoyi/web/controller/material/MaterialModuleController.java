package com.ruoyi.web.controller.material;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.bo.MaterialModuleBo;
import com.ruoyi.system.domain.vo.MaterialModuleVo;
import com.ruoyi.system.service.IMaterialModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 材料模块
 *
 * @author ruoyi
 * @date 2023-03-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/material/module")
public class MaterialModuleController extends BaseController {

    private final IMaterialModuleService iMaterialModuleService;

    /**
     * 查询材料模块列表
     */
    @SaCheckPermission("material:module:list")
    @GetMapping("/list")
    public TableDataInfo<MaterialModuleVo> list(MaterialModuleBo bo, PageQuery pageQuery) {
        return iMaterialModuleService.queryPageList(bo, pageQuery);
    }

    @SaCheckPermission("material:materialList:list")
    @GetMapping("/materialList")
    public R<?> materialList(MaterialModuleBo bo) {
        return iMaterialModuleService.selectMaterialList(bo);
    }

    /**
     * 导出材料模块列表
     */
    @SaCheckPermission("material:module:export")
    @Log(title = "材料模块", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MaterialModuleBo bo, HttpServletResponse response) {
        List<MaterialModuleVo> list = iMaterialModuleService.queryList(bo);
        ExcelUtil.exportExcel(list, "材料模块", MaterialModuleVo.class, response);
    }

    /**
     * 获取材料模块详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("material:module:query")
    @GetMapping
    public R<MaterialModuleVo> getInfo(Long id) {
        return R.ok(iMaterialModuleService.queryById(id));
    }

    /**
     * 新增材料模块
     */
    @SaCheckPermission("material:module:add")
    @Log(title = "材料模块", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MaterialModuleBo bo) {
        return toAjax(iMaterialModuleService.insertByBo(bo));
    }

    /**
     * 修改材料模块
     */
    @SaCheckPermission("material:module:edit")
    @Log(title = "材料模块", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MaterialModuleBo bo) {
        return toAjax(iMaterialModuleService.updateByBo(bo));
    }

    /**
     * 删除材料模块
     *
     * @param ids 主键串
     */
    @SaCheckPermission("material:module:remove")
    @Log(title = "材料模块", businessType = BusinessType.DELETE)
    @DeleteMapping
    public R<Void> remove(Long[] ids) {
        return toAjax(iMaterialModuleService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
