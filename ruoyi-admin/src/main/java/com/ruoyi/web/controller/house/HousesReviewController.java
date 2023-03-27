package com.ruoyi.web.controller.house;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.excel.ExcelResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.BuyHouses;
import com.ruoyi.system.domain.HousesReview;
import com.ruoyi.system.domain.bo.HousesReviewBo;
import com.ruoyi.system.domain.vo.HousesReviewVo;
import com.ruoyi.system.mapper.BuyHousesMapper;
import com.ruoyi.system.service.IHousesReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 购房复审登记
 *
 * @author ruoyi
 * @date 2023-03-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/house")
public class HousesReviewController extends BaseController {

    private final IHousesReviewService iHousesReviewService;
    private final BuyHousesMapper buyHousesMapper;


    /**
     * 购房登记录入
     * @param bo
     * @param pageQuery
     * @return
     */
    @SaCheckPermission("system:review:reviewList")
    @GetMapping("/review/list")
    public TableDataInfo<HousesReview> reviewList(HousesReviewBo bo, PageQuery pageQuery) {
        return iHousesReviewService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询购房复审登记列表
     */
    @SaCheckPermission("system:review:list")
    @GetMapping("/registrationManagement/list")
    public TableDataInfo<HousesReview> list(HousesReviewBo bo, PageQuery pageQuery) {
        return iHousesReviewService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出购房复审登记列表
     */
    @SaCheckPermission("system:review:export")
    @Log(title = "购房复审登记", businessType = BusinessType.EXPORT)
    @PostMapping("/registrationManagement/export")
    public void export(HousesReviewBo bo, HttpServletResponse response) {
        List<HousesReviewVo> list = iHousesReviewService.queryList(bo);
        ExcelUtil.exportExcel(list, "购房复审登记", HousesReviewVo.class, response);
    }

    /**
     * 获取购房复审登记详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:review:query")
    @GetMapping("/registrationManagement/{id}")
    public R<HousesReviewVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iHousesReviewService.queryById(id));
    }

    /**
     * 新增购房复审登记
     */
    @SaCheckPermission("system:review:add")
    @Log(title = "购房复审登记", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/registrationManagement")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody HousesReviewBo bo) {
        return toAjax(iHousesReviewService.insertByBo(bo));
    }

    /**
     * 修改购房复审登记
     */
    @SaCheckPermission("system:review:edit")
    @Log(title = "购房复审登记", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/registrationManagement")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody HousesReviewBo bo) {
        return toAjax(iHousesReviewService.updateByBo(bo));
    }

    /**
     * 删除购房复审登记
     * @param ids 主键串
     */
    @SaCheckPermission("system:review:remove")
    @Log(title = "购房复审登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/registrationManagement/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iHousesReviewService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 导入
     * @param file
     * @return
     * @throws Exception
     */
    @Log(title = "购房复审导入", businessType = BusinessType.IMPORT)
    @SaCheckPermission("system:review:import")
    @PostMapping(value = "/review/importData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<Void> importData(@RequestPart("file") MultipartFile file) throws Exception {
        ExcelResult<HousesReviewVo> result = ExcelUtil.importExcel(file.getInputStream(), HousesReviewVo.class, true);
        List<HousesReviewVo> volist = result.getList();
        List<HousesReview> list = BeanUtil.copyToList(volist, HousesReview.class);
        //先获取到导入数据的身份证去购房一期数据库去查
        //过滤出导入表中的身份证号码
        List<String> collect = list.stream().filter( e ->ObjectUtil.isNotEmpty(e.getCard())).map(HousesReview::getCard).collect(Collectors.toList());
        if (collect.size()>0 && list.size()>0) {
            LambdaQueryWrapper<BuyHouses> queryWrapper = new LambdaQueryWrapper<BuyHouses>().in(BuyHouses::getCardId, collect);
            //查询出导入数据中的身份证有那些是属于区级人才的
            List<String> collect1 = buyHousesMapper.selectList(queryWrapper).stream().map(BuyHouses::getCardId).collect(Collectors.toList());
            for (HousesReview housesReview : list) {
                if ( collect1.size() > 0 &&  collect1.contains(housesReview.getCard()) ) {
                    housesReview.setSourceBy("1");
                } else {
                    housesReview.setSourceBy("2");
                }
            }
        }
        iHousesReviewService.saveBatch(list);
        return R.ok(result.getAnalysis());
    }

    /**
     * 获取导入模板
     */
    @Log(title = "获取导入模板", businessType = BusinessType.IMPORT)
    @PostMapping("/review/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil.exportExcel(new ArrayList<>(), "用户数据", HousesReviewVo.class, response);
    }

    /**
     * 获取申报材料
     */
    @Log(title = "获取申报材料",businessType = BusinessType.OTHER)
    @PostMapping("/review/material")
    public R<?> getMaterialInfo(@RequestBody HousesReviewBo bo){
        return iHousesReviewService.getMaterialInfo(bo);
    }

    /**
     * 获取审核材料
     */
    @Log(title = "审核时返回当前人材料",businessType = BusinessType.OTHER)
    @GetMapping("/getMaterial/{id}")
    public R<?> getMaterialByBusinessId(@PathVariable(value = "id") Long id){
        return iHousesReviewService.getMaterialByBusinessId(id);
    }



}
