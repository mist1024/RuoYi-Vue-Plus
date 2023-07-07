package com.ruoyi.web.controller.house;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Arrays;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.system.domain.BuyHouses;
import com.ruoyi.system.domain.dto.BuyHousesEvent;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.BuyHousesVo;
import com.ruoyi.system.domain.bo.BuyHousesBo;
import com.ruoyi.system.service.IBuyHousesService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 一期后台接口
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
     * 一期后台接口查询列表
     */
    @SaCheckPermission("system:houses:list")
    @GetMapping("/list")
    public TableDataInfo<BuyHousesVo> list(BuyHousesBo bo, PageQuery pageQuery) {
        return iBuyHousesService.queryPageList(bo, pageQuery);
    }

    /**
     * 预约导出
     * @param bo
     * @return
     * @throws IOException
     */
    @SaCheckPermission("system:houses:subscribeExport")
    @PostMapping("/subscribeExport")
    public R subscribeExport(@RequestBody BuyHousesEvent bo) throws IOException {
        return iBuyHousesService.subscribeExport(bo);
    }
    /**
     * 导出excel
     */
    @SaCheckPermission("system:houses:exportExcel")
    @PostMapping("/exportExcel")
    public void exportExcel(BuyHousesBo bo,HttpServletResponse response){
        iBuyHousesService.exportExcel(bo,response);
    }

    /**
     * 一期导出列表
     */
    @SaCheckPermission("system:houses:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("一期导出列表")
    public void export(BuyHousesBo bo, HttpServletResponse response) {
        List<BuyHousesVo> list = iBuyHousesService.queryList(bo);
        ExcelUtil.exportExcel(list, "一期导出列表", BuyHousesVo.class, response);
    }

    /**
     * 获取详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:houses:query")
    @GetMapping
    public R<BuyHousesVo> getInfo(Long id) {
        return R.ok(iBuyHousesService.queryById(id));
    }


    /**
     * 删除【请填写功能名称】
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:houses:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping
    public R<Void> remove(@NotEmpty(message = "主键不能为空")Long[] ids) {
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


    /**
     * 取消资格
     * @param buyHouses
     * @return
     */
    @SaCheckPermission("system:houses:edit")
    @PutMapping
    public R<?>updateBuyHouses(@RequestBody BuyHouses buyHouses){
        return iBuyHousesService.updateBuyHouses(buyHouses);
    }

    /**
     * 首页购房申请表展示
     */
//    @SaCheckPermission("system:houses:indexType")
    @GetMapping("/indexType")
    public R<?>getIndexType(){
        return iBuyHousesService.getIndexType();
    }

    /**
     * 首页企业所在地展示
     */
//    @SaCheckPermission("system:houses:companyDistrict")
    @GetMapping("/companyDistrict")
    public R<?>getCompanyDistrict(){
        return iBuyHousesService.getCompanyDistrict();
    }
    /**
     * 首页国籍和婚姻状态展示
     */
//    @SaCheckPermission("system:houses:nationalityAndMarital")
    @GetMapping("/nationalityAndMarital")
    public R<?>getNationalityAndMarital(){
        return iBuyHousesService.getNationalityAndMarital();
    }

    /**
     * 首页第一排基础数据
     */
//    @SaCheckPermission("system:houses:basicData")
    @GetMapping("/basicData")
    public R getBasicData(){
        return iBuyHousesService.getBasicData();
    }

    /**
     * 复审柱状图
     */
//    @SaCheckPermission("system:houses:histogram")
    @GetMapping("/histogram")
    public R getHistogram(String date){
        return iBuyHousesService.getHistogram(date);
    }

    /**
     * 对市局系统单独推送
     */
    @GetMapping("/push")
    public R push(String id) throws ParseException {
        return iBuyHousesService.push(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/out")
    public R logout(String id){
        return iBuyHousesService.logout(id);
    }
}
