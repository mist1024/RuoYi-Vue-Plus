package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.mapper.FtStaffDemandDaoMapper;
import com.ruoyi.system.fantang.domain.FtStaffDemandDao;
import com.ruoyi.system.fantang.service.IFtStaffDemandDaoService;

import java.util.List;

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
        List<FtStaffDemandDao> daos = this.baseMapper.selectList(wrapper);
        if (daos.size() == 0 )
            return AjaxResult.error("获取个人配置信息错误");
        return AjaxResult.success(daos);
    }

    @Override
    public AjaxResult setDemandMode(Long id, Integer type, Boolean demandMode) {
        FtStaffDemandDao dao = new FtStaffDemandDao();
        dao.setId(id);
        dao.setDemandMode(demandMode);
        QueryWrapper<FtStaffDemandDao> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        int ret = this.baseMapper.update(dao, wrapper);
        if (ret == 0)
            return AjaxResult.error("更新订餐状态失败");
        return AjaxResult.success();
    }

}
