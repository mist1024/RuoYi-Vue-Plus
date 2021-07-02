package com.ruoyi.product.service;

import com.ruoyi.product.domain.ProItemPicture;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * proItemPictureService接口
 *
 * @author ruoyi
 * @date 2021-03-20
 */
public interface IProItemPictureService extends IService<ProItemPicture> {

    /**
     * 查询列表
     */
    List<ProItemPicture> queryList(ProItemPicture proItemPicture);
}