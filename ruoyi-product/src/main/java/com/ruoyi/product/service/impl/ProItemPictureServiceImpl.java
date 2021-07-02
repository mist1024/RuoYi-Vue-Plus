package com.ruoyi.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.product.domain.ProItemPicture;
import com.ruoyi.product.mapper.ProItemPictureMapper;
import com.ruoyi.product.service.IProItemPictureService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * proItemPictureService业务层处理
 *
 * @author ruoyi
 * @date 2021-03-20
 */
@Service
public class ProItemPictureServiceImpl extends ServiceImpl<ProItemPictureMapper, ProItemPicture> implements IProItemPictureService {

    @Override
    public List<ProItemPicture> queryList(ProItemPicture proItemPicture) {
        LambdaQueryWrapper<ProItemPicture> lqw = Wrappers.lambdaQuery();
        return this.list(lqw);
    }
}