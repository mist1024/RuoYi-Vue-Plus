package com.ruoyi.winery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.List;
import java.util.Arrays;

import com.ruoyi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import com.ruoyi.winery.domain.NewsContent;
import com.ruoyi.winery.service.INewsContentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新闻资讯Controller
 * 
 * @author ruoyi
 * @date 2020-12-31
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/news/news_content" )
public class NewsContentController extends BaseController {

    private final INewsContentService iNewsContentService;

    /**
     * 查询新闻资讯列表
     */
    @PreAuthorize("@ss.hasPermi('news:news_content:list')")
    @GetMapping("/list")
    public TableDataInfo list(NewsContent newsContent)
    {
        startPage();
        LambdaQueryWrapper<NewsContent> lqw = Wrappers.lambdaQuery(newsContent);
        if (newsContent.getDeptId() != null){
            lqw.eq(NewsContent::getDeptId ,newsContent.getDeptId());
        }
        if (StringUtils.isNotBlank(newsContent.getNewsTitle())){
            lqw.eq(NewsContent::getNewsTitle ,newsContent.getNewsTitle());
        }
        if (StringUtils.isNotBlank(newsContent.getNewsBody())){
            lqw.eq(NewsContent::getNewsBody ,newsContent.getNewsBody());
        }
        if (StringUtils.isNotBlank(newsContent.getNewsImage())){
            lqw.eq(NewsContent::getNewsImage ,newsContent.getNewsImage());
        }
        if (newsContent.getNewsType() != null){
            lqw.eq(NewsContent::getNewsType ,newsContent.getNewsType());
        }
        if (newsContent.getState() != null){
            lqw.eq(NewsContent::getState ,newsContent.getState());
        }
        List<NewsContent> list = iNewsContentService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出新闻资讯列表
     */
    @PreAuthorize("@ss.hasPermi('news:news_content:export')" )
    @Log(title = "新闻资讯" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(NewsContent newsContent) {
        LambdaQueryWrapper<NewsContent> lqw = new LambdaQueryWrapper<NewsContent>(newsContent);
        List<NewsContent> list = iNewsContentService.list(lqw);
        ExcelUtil<NewsContent> util = new ExcelUtil<NewsContent>(NewsContent. class);
        return util.exportExcel(list, "news_content" );
    }

    /**
     * 获取新闻资讯详细信息
     */
    @PreAuthorize("@ss.hasPermi('news:news_content:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) String id) {
        return AjaxResult.success(iNewsContentService.getById(id));
    }

    /**
     * 新增新闻资讯
     */
    @PreAuthorize("@ss.hasPermi('news:news_content:add')" )
    @Log(title = "新闻资讯" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(UsernamePasswordAuthenticationToken token, @RequestBody NewsContent newsContent) {
        newsContent.setDeptId(getDeptId(token));
        return toAjax(iNewsContentService.save(newsContent) ? 1 : 0);
    }

    /**
     * 修改新闻资讯
     */
    @PreAuthorize("@ss.hasPermi('news:news_content:edit')" )
    @Log(title = "新闻资讯" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewsContent newsContent) {
        return toAjax(iNewsContentService.updateById(newsContent) ? 1 : 0);
    }

    /**
     * 删除新闻资讯
     */
    @PreAuthorize("@ss.hasPermi('news:news_content:remove')" )
    @Log(title = "新闻资讯" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(iNewsContentService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
