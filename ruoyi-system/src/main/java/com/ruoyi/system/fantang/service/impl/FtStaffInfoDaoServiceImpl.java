package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.fantang.domain.FtStaffInfoDao;
import com.ruoyi.system.fantang.mapper.FtStaffInfoDaoMapper;
import com.ruoyi.system.fantang.service.IFtStaffInfoDaoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工管理Service业务层处理
 *
 * @author ft
 * @date 2020-11-19
 */
@Service
public class FtStaffInfoDaoServiceImpl extends ServiceImpl<FtStaffInfoDaoMapper, FtStaffInfoDao> implements IFtStaffInfoDaoService {

    @Override
    public List<FtStaffInfoDao> selectStaffInfoWithDepart() {

        return this.baseMapper.selectStaffInfoWithDepart();
    }

    @Override
    public AjaxResult login(String tel, String password) {
        QueryWrapper<FtStaffInfoDao> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tel", tel);
        queryWrapper.eq("password", password);
        FtStaffInfoDao dao = this.baseMapper.selectOne(queryWrapper);
        if (dao == null)
            return AjaxResult.error(-1, "查无记录");
        return AjaxResult.success(dao);
    }
}
