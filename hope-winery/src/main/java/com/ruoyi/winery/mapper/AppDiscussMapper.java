package com.ruoyi.winery.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.winery.domain.appdiscuss.AppDiscuss;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * app评论Mapper接口
 *
 * @author ruoyi
 * @date 2021-01-08
 */
public interface AppDiscussMapper extends BaseMapper<AppDiscuss> {


    Integer replyNum(String num);

    Integer select(LambdaQueryWrapper<AppDiscuss> wrapper);

    void update(AppDiscuss appDiscuss);
}
