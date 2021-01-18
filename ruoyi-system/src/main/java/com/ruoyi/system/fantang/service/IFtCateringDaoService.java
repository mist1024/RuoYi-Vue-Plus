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

    FtCateringDao getByIdNewFormatter(Long id);

    List<FtCateringDao> addNutritionCatering(FtCateringDao ftCateringDao);

    Integer deleteByPatientId(Long[] ids);

    Integer cancelByPatientId(Long[] ids);

    List<Long> notInTable(FtCateringDao ftCateringDao);

    Integer copyAndAdd(Long patientId, List<FtCateringDao> ftCateringDao);
}
