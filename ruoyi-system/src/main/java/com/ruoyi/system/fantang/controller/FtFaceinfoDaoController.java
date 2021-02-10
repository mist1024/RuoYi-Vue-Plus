package com.ruoyi.system.fantang.controller;

import java.util.List;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.fantang.domain.FtFaceinfoDao;
import com.ruoyi.system.fantang.service.IFtFaceinfoDaoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 人脸信息Controller
 * 
 * @author ryo
 * @date 2021-01-11
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/fantang/faceInfo" )
public class FtFaceinfoDaoController extends BaseController {

    private final IFtFaceinfoDaoService iFtFaceinfoDaoService;

    /**
     * 查询人脸信息列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:faceInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(FtFaceinfoDao ftFaceinfoDao) {
        startPage();
        List<FtFaceinfoDao> list = iFtFaceinfoDaoService.queryList(ftFaceinfoDao);
        return getDataTable(list);
    }

    /**
     * 导出人脸信息列表
     */
    @PreAuthorize("@ss.hasPermi('fantang:faceInfo:export')" )
    @Log(title = "人脸信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(FtFaceinfoDao ftFaceinfoDao) {
        List<FtFaceinfoDao> list = iFtFaceinfoDaoService.queryList(ftFaceinfoDao);
        ExcelUtil<FtFaceinfoDao> util = new ExcelUtil<FtFaceinfoDao>(FtFaceinfoDao.class);
        return util.exportExcel(list, "faceInfo" );
    }

    /**
     * 获取人脸信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:faceInfo:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iFtFaceinfoDaoService.getById(id));
    }

    /**
     * 新增人脸信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:faceInfo:add')" )
    @Log(title = "人脸信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FtFaceinfoDao ftFaceinfoDao) {
        return toAjax(iFtFaceinfoDaoService.save(ftFaceinfoDao) ? 1 : 0);
    }

    /**
     * 修改人脸信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:faceInfo:edit')" )
    @Log(title = "人脸信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FtFaceinfoDao ftFaceinfoDao) {
        return toAjax(iFtFaceinfoDaoService.updateById(ftFaceinfoDao) ? 1 : 0);
    }

    /**
     * 删除人脸信息
     */
    @PreAuthorize("@ss.hasPermi('fantang:faceInfo:remove')" )
    @Log(title = "人脸信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iFtFaceinfoDaoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
