package com.ruoyi.web.controller.house;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.ruoyi.system.domain.BuyHouses;
import com.ruoyi.system.domain.bo.HousesReviewBo;
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
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.BuyHousesVo;
import com.ruoyi.system.domain.bo.BuyHousesBo;
import com.ruoyi.system.service.IBuyHousesService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】
 *
 * @author ruoyi
 * @date 2023-03-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/house")
public class BuyHousesController extends BaseController {

    private final IBuyHousesService iBuyHousesService;

    /**
     * 查询【请填写功能名称】列表
     */
    @SaCheckPermission("system:houses:list")
    @GetMapping("/list")
    public TableDataInfo<BuyHousesVo> list(BuyHousesBo bo, PageQuery pageQuery) {
        return iBuyHousesService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @SaCheckPermission("system:houses:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BuyHousesBo bo, HttpServletResponse response) {
        List<BuyHousesVo> list = iBuyHousesService.queryList(bo);
        ExcelUtil.exportExcel(list, "【请填写功能名称】", BuyHousesVo.class, response);
    }

    /**
     * 获取【请填写功能名称】详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:houses:query")
    @GetMapping("/{id}")
    public R<BuyHousesVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iBuyHousesService.queryById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    /*@SaCheckPermission("system:houses:add")
    @Log(title = "【购房申请添加】", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BuyHousesBo bo) {
        return toAjax(iBuyHousesService.insertByBo(bo));
    }*/

    /**
     * 修改【请填写功能名称】
     */
  /*  @SaCheckPermission("system:houses:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BuyHousesBo bo) {
        return toAjax(iBuyHousesService.updateByBo(bo));
    }*/

    /**
     * 删除【请填写功能名称】
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:houses:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iBuyHousesService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 获取申报材料
     */
    @Log(title = "获取申报材料",businessType = BusinessType.OTHER)
    @PostMapping("/review/material")
    public R<?> getMaterialInfo(@RequestBody BuyHousesBo bo){
        return iBuyHousesService.getMaterialInfo(bo);
    }



}
