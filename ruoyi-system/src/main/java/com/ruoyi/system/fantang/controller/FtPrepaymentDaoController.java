package com.ruoyi.system.fantang.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.fantang.domain.FtPrepaymentDao;
import com.ruoyi.system.fantang.domain.FtPrepaymentVo;
import com.ruoyi.system.fantang.service.IFtPrepaymentDaoService;
import com.ruoyi.system.fantang.utils.PdfUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.EDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 收费管理Controller
 *
 * @author ft
 * @date 2020-11-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/prepayment")
public class FtPrepaymentDaoController extends BaseController {

    private final IFtPrepaymentDaoService iFtPrepaymentDaoService;


    @GetMapping("/getCountById/{patientId}")
    public AjaxResult getCountById(@PathVariable("patientId") Long patientId) {
        FtPrepaymentVo dao = iFtPrepaymentDaoService.getCountById(patientId);
        if (dao == null)
            return AjaxResult.error("无该记录");
        return AjaxResult.success("操作成功", dao);
    }

    /**
     * 通过病人id查询收费管理详细
     * 作者：陈智兴
     * 日期：2020年12月2日
     * 功能： 前端查询预付款情况
     *
     * @param patientId
     * @return
     */
    @GetMapping("/getPrepaymentByPatientId/{patientId}")
    public AjaxResult getPrepaymentByPatientId(@PathVariable("patientId") Long patientId) {
//        FtPrepaymentVo dao = iFtPrepaymentDaoService.getCountById(patientId);
//        if (dao == null)
//            return AjaxResult.error("无该记录");
//        return AjaxResult.success("操作成功", dao);
        FtPrepaymentDao ftPrepaymentDao = iFtPrepaymentDaoService.getByPatientId(patientId);
        if (ftPrepaymentDao == null) {
            return AjaxResult.error("无该记录");
        }
        return AjaxResult.success("操作成功", ftPrepaymentDao);
    }

    // 查询所有待缴费列表
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:list')")
    @GetMapping("/listNoPrepay")
    public TableDataInfo listNoPrepay() {
        startPage();
        return getDataTable(iFtPrepaymentDaoService.listNoPrepay());
    }

    // 查询所有已缴费列表
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:list')")
    @GetMapping("/listPrepay")
    public AjaxResult listPrepay(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return AjaxResult.success(iFtPrepaymentDaoService.listPrepay(pageNum, pageSize));
    }

    // 查询所有已结算列表
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:list')")
    @GetMapping("/listAllPrepay")
    public TableDataInfo listAllPrepay() {
        List<FtPrepaymentVo> list = iFtPrepaymentDaoService.listAllPrepay();
        return getDataTable(list);
    }


    /**
     * 查询收费管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtPrepaymentDao ftPrepaymentDao) {
        startPage();
        LambdaQueryWrapper<FtPrepaymentDao> lqw = Wrappers.lambdaQuery(ftPrepaymentDao);
        if (ftPrepaymentDao.getCollectAt() != null) {
            lqw.eq(FtPrepaymentDao::getCollectAt, ftPrepaymentDao.getCollectAt());
        }
        if (ftPrepaymentDao.getSettlementAt() != null) {
            lqw.eq(FtPrepaymentDao::getSettlementAt, ftPrepaymentDao.getSettlementAt());
        }
        if (ftPrepaymentDao.getSettlementFlag() != null) {
            lqw.eq(FtPrepaymentDao::getSettlementFlag, ftPrepaymentDao.getSettlementFlag());
        }
        if (ftPrepaymentDao.getPrepaid() != null) {
            lqw.eq(FtPrepaymentDao::getPrepaid, ftPrepaymentDao.getPrepaid());
        }
        if (ftPrepaymentDao.getPrepaidAt() != null) {
            lqw.eq(FtPrepaymentDao::getPrepaidAt, ftPrepaymentDao.getPrepaidAt());
        }
        if (ftPrepaymentDao.getPrepaidAt() != null) {
            lqw.eq(FtPrepaymentDao::getPrepaidAt, ftPrepaymentDao.getPrepaidAt());
        }
        List<FtPrepaymentDao> list = iFtPrepaymentDaoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出收费管理列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:export')")
    @Log(title = "收费管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FtPrepaymentDao ftPrepaymentDao) {
        LambdaQueryWrapper<FtPrepaymentDao> lqw = new LambdaQueryWrapper<FtPrepaymentDao>(ftPrepaymentDao);
        List<FtPrepaymentDao> list = iFtPrepaymentDaoService.list(lqw);
        ExcelUtil<FtPrepaymentDao> util = new ExcelUtil<FtPrepaymentDao>(FtPrepaymentDao.class);
        return util.exportExcel(list, "prepayment");
    }

    /**
     * 获取收费管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:query')")
    @GetMapping(value = "/{prepaymentId}")
    public AjaxResult getInfo(@PathVariable("prepaymentId") Long prepaymentId) {
        return AjaxResult.success(iFtPrepaymentDaoService.getById(prepaymentId));
    }

    /**
     * 新增收费管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:add')")
    @Log(title = "收费管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtPrepaymentVo ftPrepaymentDao) {

        Long patientId = ftPrepaymentDao.getPatientId();
        QueryWrapper<FtPrepaymentVo> wrapper = new QueryWrapper<>();
        wrapper.eq("patient_id", patientId);

        ftPrepaymentDao.setCollectAt(new Date());
        ftPrepaymentDao.setSettlementFlag(0L);
        return toAjax(iFtPrepaymentDaoService.save(ftPrepaymentDao) ? 1 : 0);
    }

    /**
     * 修改收费管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:edit')")
    @Log(title = "收费管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtPrepaymentVo ftPrepaymentDao) {
        return toAjax(iFtPrepaymentDaoService.updateById(ftPrepaymentDao) ? 1 : 0);
    }

    /**
     * 删除收费管理
     */
    @PreAuthorize("@ss.hasPermi('fantang:prepayment:remove')")
    @Log(title = "收费管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{prepaymentIds}")
    public AjaxResult remove(@PathVariable Long[] prepaymentIds) {
        return toAjax(iFtPrepaymentDaoService.removeByIds(Arrays.asList(prepaymentIds)) ? 1 : 0);
    }

    @PostMapping("/generateReceiptPdf")
    public AjaxResult generateReceiptPdf(@RequestBody JSONObject params) {
//        Map<String, String> convert = NumberToList.convertTo(123.12F, "1");
//        System.out.println(convert);

        System.out.println(params);

        // 模板路径
        String templatePath = "F:\\pdfTemplate\\饭堂票据模板2.pdf";

        System.out.println(RuoYiConfig.getUploadPath());

        // 生成的新文件路径
        String outputPath = RuoYiConfig.getUploadPath() + "\\饭堂票据.pdf";

        PdfUtils.generatePrepaymentPdf(templatePath, outputPath, params);

        // 下载地址
        String downloadPath = "profile/upload/饭堂票据.pdf";

        return AjaxResult.success(downloadPath);

    }
}
