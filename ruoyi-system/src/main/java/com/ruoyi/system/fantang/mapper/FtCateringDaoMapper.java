package com.ruoyi.system.fantang.mapper;

import com.ruoyi.system.fantang.domain.FtCateringDao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 配餐功能Mapper接口
 *
 * @author ft
 * @date 2020-12-07
 */
public interface FtCateringDaoMapper extends BaseMapper<FtCateringDao> {

    List<FtCateringDao> listNewFormatter(FtCateringDao ftCateringDao);

    FtCateringDao getByIdNewFormatter(Long id);
}
