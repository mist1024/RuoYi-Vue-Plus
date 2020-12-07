package com.ruoyi.system.fantang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.fantang.domain.FtCateringDao;

import java.util.List;

/**
 * 配餐功能Service接口
 *
 * @author ft
 * @date 2020-12-07
 */
public interface IFtCateringDaoService extends IService<FtCateringDao> {

    List<FtCateringDao> listNewFormatter(FtCateringDao ftCateringDao);
}
