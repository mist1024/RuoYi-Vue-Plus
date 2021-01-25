package com.ruoyi.winery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.winery.service.IAppDiscussService;


import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.winery.mapper.AppDiscussMapper;
import com.ruoyi.winery.domain.appdiscuss.AppDiscuss;

import java.util.ArrayList;
import java.util.List;


/**
 * app评论Service业务层处理
 *
 * @author ruoyi
 * @date 2021-01-08
 */
@Service
public class AppDiscussServiceImpl extends ServiceImpl<AppDiscussMapper, AppDiscuss> implements IAppDiscussService {

    @Override
    public List<AppDiscuss> getAllReply(String prentId) {
        //查询所有的一级回复
        LambdaQueryWrapper<AppDiscuss> wrapperOne = Wrappers.lambdaQuery();
        wrapperOne.eq(AppDiscuss::getAppDiscussReplyId, prentId);
        //根据wrapperOne的条件查询全部记录
        List<AppDiscuss> oneReplyList = baseMapper.selectList(wrapperOne);
            return oneReplyList;
//        //查询所有的二级回复
//        LambdaQueryWrapper<AppDiscuss> wrapperTwo = Wrappers.lambdaQuery();
//        wrapperTwo.ne(AppDiscuss::getAppDiscussReplyId, 0);
//        List<AppDiscuss> TwoReplyList = baseMapper.selectList(wrapperTwo);
//
//        //设置一个总集合，把刚刚查到的分层数据全部放到finalReplyList
//        List<AppDiscuss> finalReplyList = new ArrayList<>();
//
//        //把一级回复装到集合里面
//        for (int i = 0; i < oneReplyList.size(); i++) {
//            AppDiscuss appDiscuss = oneReplyList.get(i);
//
//            AppDiscuss reply = new AppDiscuss();
//            //把查询到的数据拷贝到oneDiscuss
//            BeanUtils.copyProperties(appDiscuss, reply);
//            //最后将数据添加到finalReplyList，最后返回它就好了
//            finalReplyList.add(reply);
//
//            List<AppDiscuss> finalTwoReplyList = new ArrayList<>();
//            //把二级回复装到集合里面
//            for (int m = 0; m < TwoReplyList.size(); m++) {
//                AppDiscuss appDiscuss1 = TwoReplyList.get(m);
//                //如果二级目录的getAppDiscussExtraUsername和一级目录的getAppDiscussReplyUser内容相同，则为回复它的评论
//                if (appDiscuss1.getAppDiscussExtraUsername().equals(appDiscuss.getAppDiscussReplyUser())){
//
//                        AppDiscuss oneReply = new AppDiscuss();
//
//                        BeanUtils.copyProperties(appDiscuss1, oneReply);
//
//                        finalTwoReplyList.add(oneReply);
//                    }
//                }
//
//            //把数据最后都放到finalTwoReplyList
//            reply.setChildren(finalTwoReplyList);
//        }
//        return finalReplyList;
    }


}