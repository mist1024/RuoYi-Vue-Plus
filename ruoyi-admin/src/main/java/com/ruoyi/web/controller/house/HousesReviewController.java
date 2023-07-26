package com.ruoyi.web.controller.house;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.excel.ExcelResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.BuyHouses;
import com.ruoyi.system.domain.HousesReview;
import com.ruoyi.system.domain.bo.HousesReviewBo;
import com.ruoyi.system.domain.dto.HousesReviewEvent;
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
import java.io.IOException;
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
     * 购房登记录入列表
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
     * 购房登记录入管理列表
     * @param bo
     * @param pageQuery
     * @return
     */
    @SaCheckPermission("system:manager:reviewList")
    @GetMapping("/manager/list")
    public TableDataInfo<HousesReview> managerReviewList(HousesReviewBo bo, PageQuery pageQuery) {
        return iHousesReviewService.managerQueryPageList(bo, pageQuery);
    }

    /**
     * 预约导出
     * @param bo
     * @return
     * @throws IOException
     */
    @SaCheckPermission("system:houses:subscribeExport")
    @PostMapping("/subscribeExport")
    public R subscribeExport(@RequestBody HousesReviewEvent bo) throws IOException {
        return iHousesReviewService.subscribeExport(bo);
    }

    /**
     * 导出excel
     */
    @SaCheckPermission("system:houses:exportExcel")
    @PostMapping("/exportExcel")
    public void exportExcel(HousesReviewBo bo,HttpServletResponse response){
        iHousesReviewService.exportExcel(bo,response);
    }

    /**
     * 查询购房复审登记列表
     */
    @SaCheckPermission("system:review:list")
    @GetMapping("/registrationManagement/list")
    public TableDataInfo<HousesReview> list(HousesReviewBo bo, PageQuery pageQuery) {
        boolean admin = LoginHelper.isAdmin();
        if (!admin){
            bo.setProjectName(LoginHelper.getLoginUser().getProperties());
        }
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
    @SaCheckPermission("system:review:edit")
    @GetMapping("/registrationManagement")
    public R<HousesReviewVo> getInfo(Long id) {
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
    @DeleteMapping("/review")
    public R<Void> remove(Long[] ids) {
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
        ExcelResult<HousesReviewVo> result = ExcelUtil.importExcel(file.getInputStream(), HousesReviewVo.class, true,3);
        List<HousesReviewVo> volist = result.getList();
        //判断时间格式是否正确
        volist.stream().forEach(v ->{
            if (!DateUtils.checkDate(v.getQualificationConfirmTime(),DateUtils.YYYY_MM_DD_HH_MM_SS)){
                throw new ServiceException("资格确认时间格式不正确,格式为:"+DateUtils.YYYY_MM_DD_HH_MM_SS);
            }
            if (!DateUtils.checkDate(v.getAuditTime(),DateUtils.YYYY_MM_DD)){
                throw new ServiceException("审核时间格式不正确,格式为:"+DateUtils.YYYY_MM_DD);
            }
            if (!DateUtils.checkDate(v.getRegisterFailureTime(),DateUtils.YYYY_MM_DD)){
                throw new ServiceException("登记失效时间格式不正确,格式为:"+DateUtils.YYYY_MM_DD);
            }
        });

        List<HousesReview> list = BeanUtil.copyToList(volist, HousesReview.class);
        //先获取到导入数据的身份证去购房一期数据库去查
        //过滤出导入表中的身份证号码
        List<String> collect = list.stream()
            .filter( e ->ObjectUtil.isNotEmpty(e.getCard()))
            .map(HousesReview::getCard)
            .collect(Collectors.toList());
        if (collect.size()>0 && list.size()>0) {
            LambdaQueryWrapper<BuyHouses> queryWrapper = new LambdaQueryWrapper<BuyHouses>()
                .in(BuyHouses::getCardId, collect)
                .eq(BuyHouses::getProcessStatus,Constants.SUCCEED);
            //查询出导入数据中的身份证有那些是属于区级人才的
            List<String> collect1 = buyHousesMapper.selectList(queryWrapper)
                .stream()
                .map(BuyHouses::getCardId)
                .collect(Collectors.toList());
            for (HousesReview housesReview : list) {
                housesReview.setProcessKey("house_review");
                housesReview.setProcessStatus(Constants.SUBMIT);
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
    @GetMapping("/getMaterial")
    public R<?> getMaterialByBusinessId(Long id){
        return iHousesReviewService.getMaterialByBusinessId(id);
    }
}
