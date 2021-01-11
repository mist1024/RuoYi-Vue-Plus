package com.ruoyi.winery.controller.appdiscuss;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.List;
import java.util.Arrays;

import com.ruoyi.common.utils.StringUtils;
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
import com.ruoyi.winery.domain.appdiscuss.AppDiscuss;
import com.ruoyi.winery.service.IAppDiscussService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * app评论Controller
 * 
 * @author ruoyi
 * @date 2021-01-08
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/app/discuss" )
public class AppDiscussController extends BaseController {

    private final IAppDiscussService iAppDiscussService;

    /**
     * 查询app评论列表
     */
    @PreAuthorize("@ss.hasPermi('app:discuss:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppDiscuss appDiscuss)
    {
        startPage();
        LambdaQueryWrapper<AppDiscuss> lqw = Wrappers.lambdaQuery(appDiscuss);
        if (appDiscuss.getDeptId() != null){
            lqw.eq(AppDiscuss::getDeptId ,appDiscuss.getDeptId());
        }
        if (appDiscuss.getState() != null){
            lqw.eq(AppDiscuss::getState ,appDiscuss.getState());
        }
        if (StringUtils.isNotBlank(appDiscuss.getCreateUser())){
            lqw.eq(AppDiscuss::getCreateUser ,appDiscuss.getCreateUser());
        }
        if (StringUtils.isNotBlank(appDiscuss.getUpdateUser())){
            lqw.eq(AppDiscuss::getUpdateUser ,appDiscuss.getUpdateUser());
        }
        if (StringUtils.isNotBlank(appDiscuss.getAppDiscussReplyId())){
            lqw.eq(AppDiscuss::getAppDiscussReplyId ,appDiscuss.getAppDiscussReplyId());
        }
        if (StringUtils.isNotBlank(appDiscuss.getAppDiscussText())){
            lqw.eq(AppDiscuss::getAppDiscussText ,appDiscuss.getAppDiscussText());
        }
        if (StringUtils.isNotBlank(appDiscuss.getAppAssociationId())){
            lqw.eq(AppDiscuss::getAppAssociationId ,appDiscuss.getAppAssociationId());
        }
        if (StringUtils.isNotBlank(appDiscuss.getAppDiscussImage())){
            lqw.eq(AppDiscuss::getAppDiscussImage ,appDiscuss.getAppDiscussImage());
        }
//        if (StringUtils.isNotBlank(appDiscuss.getAppDiscussVideo())){
//            lqw.eq(AppDiscuss::getAppDiscussVideo ,appDiscuss.getAppDiscussVideo());
//        }
        if (StringUtils.isNotBlank(appDiscuss.getAppDiscussExtraUsername())){
            lqw.like(AppDiscuss::getAppDiscussExtraUsername ,appDiscuss.getAppDiscussExtraUsername());
        }
        if (StringUtils.isNotBlank(appDiscuss.getAppDiscussExtraMobile())){
            lqw.eq(AppDiscuss::getAppDiscussExtraMobile ,appDiscuss.getAppDiscussExtraMobile());
        }
        if (appDiscuss.getAppDiscussRecommend() != null){
            lqw.eq(AppDiscuss::getAppDiscussRecommend ,appDiscuss.getAppDiscussRecommend());
        }
        if (appDiscuss.getAppDiscussType() != null){
            lqw.eq(AppDiscuss::getAppDiscussType ,appDiscuss.getAppDiscussType());
        }
        if (StringUtils.isNotBlank(appDiscuss.getAppDiscussReplyUser())){
            lqw.eq(AppDiscuss::getAppDiscussReplyUser ,appDiscuss.getAppDiscussReplyUser());
        }
        List<AppDiscuss> list = iAppDiscussService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出app评论列表
     */
    @PreAuthorize("@ss.hasPermi('app:discuss:export')" )
    @Log(title = "app评论" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(AppDiscuss appDiscuss) {
        LambdaQueryWrapper<AppDiscuss> lqw = new LambdaQueryWrapper<AppDiscuss>(appDiscuss);
        List<AppDiscuss> list = iAppDiscussService.list(lqw);
        ExcelUtil<AppDiscuss> util = new ExcelUtil<AppDiscuss>(AppDiscuss. class);
        return util.exportExcel(list, "discuss" );
    }

    /**
     * 获取app评论详细信息
     */
    @PreAuthorize("@ss.hasPermi('app:discuss:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) String id) {
        return AjaxResult.success(iAppDiscussService.getById(id));
    }

    /**
     * 新增app评论
     */
    @PreAuthorize("@ss.hasPermi('app:discuss:add')" )
    @Log(title = "app评论" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppDiscuss appDiscuss) {
        return toAjax(iAppDiscussService.save(appDiscuss) ? 1 : 0);
    }

    /**
     * 修改app评论
     */
    @PreAuthorize("@ss.hasPermi('app:discuss:edit')" )
    @Log(title = "app评论" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppDiscuss appDiscuss) {
        return toAjax(iAppDiscussService.updateById(appDiscuss) ? 1 : 0);
    }

    /**
     * 删除app评论
     */
    @PreAuthorize("@ss.hasPermi('app:discuss:remove')" )
    @Log(title = "app评论" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(iAppDiscussService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
