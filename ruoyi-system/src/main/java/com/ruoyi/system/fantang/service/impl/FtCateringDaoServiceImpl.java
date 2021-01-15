package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.domain.FtCateringDao;
import com.ruoyi.system.fantang.mapper.FtCateringDaoMapper;
import com.ruoyi.system.fantang.service.IFtCateringDaoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 配餐功能Service业务层处理
 *
 * @author ft
 * @date 2020-12-07
 */
@Service
public class FtCateringDaoServiceImpl extends ServiceImpl<FtCateringDaoMapper, FtCateringDao> implements IFtCateringDaoService {

    @Override
    public List<FtCateringDao> listNewFormatter(FtCateringDao ftCateringDao) {
        return this.baseMapper.listNewFormatter(ftCateringDao);
    }

    @Override
    public FtCateringDao getByIdNewFormatter(Long id) {
        return this.baseMapper.getByIdNewFormatter(id);
    }

    @Override
    public List<FtCateringDao> addNutritionCatering(FtCateringDao ftCateringDao) {

        List<FtCateringDao> list = new ArrayList<>();

        List<Integer> types = ftCateringDao.getTypes();

            for (int i = 1; i < 5; i++) {
                FtCateringDao cateringDao = new FtCateringDao();
                cateringDao.setPatientId(ftCateringDao.getPatientId());
                cateringDao.setNumber(ftCateringDao.getNumber());
                cateringDao.setFrequency(ftCateringDao.getFrequency());
                cateringDao.setCateringUsage(ftCateringDao.getCateringUsage());
                cateringDao.setCreateAt(new Date());
                cateringDao.setType(i);

                for (Integer type : types) {
                    if (i == type) {
                        cateringDao.setFlag(true);
                        break;
                    } else {
                        cateringDao.setFlag(false);
                    }
                }

                this.baseMapper.insert(cateringDao);
                list.add(cateringDao);
            }

        return list;
    }

    @Override
    public Integer deleteByPatientId(Long[] ids) {

        int rows = 0;

        for (Long id : ids) {
            QueryWrapper<FtCateringDao> wrapper = new QueryWrapper<>();
            wrapper.eq("patient_id", id);
            rows += this.baseMapper.delete(wrapper);
        }

        return rows;
    }

    @Override
    public Integer cancelByPatientId(Long[] ids) {

        int rows = 0;

        FtCateringDao ftCateringDao = new FtCateringDao();
        ftCateringDao.setFlag(false);
        ftCateringDao.setUpdateAt(new Date());

        for (Long id : ids) {
            UpdateWrapper<FtCateringDao> wrapper = new UpdateWrapper<>();
            wrapper.eq("patient_id", id);
            rows += this.baseMapper.update(ftCateringDao, wrapper);
        }

        return rows;
    }

    @Override
    public List<Long> notInTable(FtCateringDao ftCateringDao) {

        List<Long> patientIds = ftCateringDao.getPatientIds();

        List<Long> patientNotInTable = new ArrayList<>();

        for (Long patientId : patientIds) {
            QueryWrapper<FtCateringDao> wrapper = new QueryWrapper<>();
            wrapper.eq("patient_id", patientId);
            List<FtCateringDao> ftCateringDaoList = this.baseMapper.selectList(wrapper);

            if (ftCateringDaoList.size() == 0) {
                patientNotInTable.add(patientId);
            }

        }

        return patientNotInTable;
    }

    @Override
    public List<FtCateringDao> copyAndAdd(List<Long> patientIds, FtCateringDao ftCateringDao) {

        List<FtCateringDao> list = new ArrayList<>();

        List<Integer> types = ftCateringDao.getTypes();

        for (Long patientId : patientIds) {
            for (int i = 1; i < 5; i++) {
                FtCateringDao cateringDao = new FtCateringDao();
                cateringDao.setPatientId(patientId);
                cateringDao.setNumber(ftCateringDao.getNumber());
                cateringDao.setFrequency(ftCateringDao.getFrequency());
                cateringDao.setCateringUsage(ftCateringDao.getCateringUsage());
                cateringDao.setCreateAt(new Date());
                cateringDao.setType(i);

                for (Integer type : types) {
                    if (i == type) {
                        cateringDao.setFlag(true);
                        break;
                    } else {
                        cateringDao.setFlag(false);
                    }
                }

                this.baseMapper.insert(cateringDao);
                list.add(cateringDao);
            }
        }

        return list;
    }
}
