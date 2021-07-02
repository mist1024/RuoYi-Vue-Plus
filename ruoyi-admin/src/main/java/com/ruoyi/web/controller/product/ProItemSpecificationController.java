/*
package com.ruoyi.web.controller.product;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.product.bo.ProItemSpecificationAddBo;
import com.ruoyi.product.bo.ProItemSpecificationEditBo;
import com.ruoyi.product.bo.ProItemSpecificationQueryBo;
import com.ruoyi.product.service.IProItemSpecificationService;
import com.ruoyi.product.vo.ProItemSpecificationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

*/
/**
 * 商品规格Controller
 *
 * @author ruoyi
 * @date 2021-04-06
 *//*

@Api(value = "商品规格控制器", tags = {"商品规格管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/product/proItemSpecification")
public class ProItemSpecificationController extends BaseController {

    private final IProItemSpecificationService iProItemSpecificationService;

    */
/**
     * 查询商品规格列表
     *//*

    @ApiOperation("查询商品规格列表")
    @PreAuthorize("@ss.hasPermi('product:proItemSpecification:list')")
    @GetMapping("/list")
    public TableDataInfo<ProItemSpecificationVo> list(ProItemSpecificationQueryBo bo) {
        startPage();
        List<ProItemSpecificationVo> list = iProItemSpecificationService.queryList(bo);
        return getDataTable(list);
    }

    */
/**
     * 导出商品规格列表
     *//*

    @ApiOperation("导出商品规格列表")
    @PreAuthorize("@ss.hasPermi('product:proItemSpecification:export')")
    @Log(title = "商品规格", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult<ProItemSpecificationVo> export(ProItemSpecificationQueryBo bo) {
        List<ProItemSpecificationVo> list = iProItemSpecificationService.queryList(bo);
        ExcelUtil<ProItemSpecificationVo> util = new ExcelUtil<>(ProItemSpecificationVo.class);
        return util.exportExcel(list, "proItemSpecification");
    }

    */
/**
     * 获取商品规格详细信息
     *//*

    @ApiOperation("获取商品规格详细信息")
    @PreAuthorize("@ss.hasPermi('product:proItemSpecification:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult<ProItemSpecificationVo> getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iProItemSpecificationService.queryById(id));
    }

    */
/**
     * 新增商品规格
     *//*

    @ApiOperation("新增商品规格")
    @PreAuthorize("@ss.hasPermi('product:proItemSpecification:add')")
    @Log(title = "商品规格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult<Void> add(@RequestBody ProItemSpecificationAddBo bo) {
        return toAjax(iProItemSpecificationService.insertByAddBo(bo) ? 1 : 0);
    }

    */
/**
     * 修改商品规格
     *//*

    @ApiOperation("修改商品规格")
    @PreAuthorize("@ss.hasPermi('product:proItemSpecification:edit')")
    @Log(title = "商品规格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult<Void> edit(@RequestBody ProItemSpecificationEditBo bo) {
        return toAjax(iProItemSpecificationService.updateByEditBo(bo) ? 1 : 0);
    }

    */
/**
     * 删除商品规格
     *//*

    @ApiOperation("删除商品规格")
    @PreAuthorize("@ss.hasPermi('product:proItemSpecification:remove')")
    @Log(title = "商品规格", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@PathVariable Long ids) {
        return toAjax(iProItemSpecificationService.deleteWithValidByIds(Collections.singletonList(ids), true) ? 1 : 0);
    }
}
*/
