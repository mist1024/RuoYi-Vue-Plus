package com.ruoyi.winery.service;

import com.ruoyi.winery.domain.appdiscuss.AppDiscuss;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * app评论Service接口
 *
 * @author ruoyi
 * @date 2021-01-08
 */
public interface IAppDiscussService extends IService<AppDiscuss> {


    //获取到所有一级和二级的评论
    List<AppDiscuss> getAllReply(String prentId);

}
