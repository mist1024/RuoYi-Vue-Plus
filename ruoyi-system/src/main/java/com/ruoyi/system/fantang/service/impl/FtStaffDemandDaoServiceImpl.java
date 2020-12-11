package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.mapper.FtStaffDemandDaoMapper;
import com.ruoyi.system.fantang.domain.FtStaffDemandDao;
import com.ruoyi.system.fantang.service.IFtStaffDemandDaoService;

/**
 * 员工报餐Service业务层处理
 *
 * @author ft
 * @date 2020-12-07
 */
@Service
public class FtStaffDemandDaoServiceImpl extends ServiceImpl<FtStaffDemandDaoMapper, FtStaffDemandDao> implements IFtStaffDemandDaoService {

    @Override
    public AjaxResult getConfiguration(Long staffId) {
        QueryWrapper<FtStaffDemandDao> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);
        FtStaffDemandDao dao = this.baseMapper.selectOne(wrapper);
        if (dao == null)
            return AjaxResult.error("获取个人配置信息错误");
        return AjaxResult.success(dao);
    }

    @Override
    public AjaxResult setDemandMode(Long id, Boolean demandMode) {
        FtStaffDemandDao dao = new FtStaffDemandDao();
        dao.setId(id);
        dao.setDemandMode(demandMode);
        int ret = this.baseMapper.updateById(dao);
        if (ret == 0)
            return AjaxResult.error("更新订餐状态失败");
        return AjaxResult.success();
    }

}
