package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.fantang.mapper.FtStaffDemandDaoMapper;
import com.ruoyi.system.fantang.domain.FtStaffDemandDao;
import com.ruoyi.system.fantang.service.IFtStaffDemandDaoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        FtStaffDemandDao dao = this.baseMapper.selectById(id);
        dao.setDemandMode(demandMode);

        int ret = this.baseMapper.updateById(dao);
        if (ret == 0)
            return AjaxResult.error("更新订餐状态失败");
        return AjaxResult.success();
    }

    @Override
    public AjaxResult initDemandMode(Long staffId) {
        // 先删除该员工的配置信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("staff_id", staffId);
        int ret = this.baseMapper.deleteByMap(map);
        // 初始化三条订餐配置信息
        this.baseMapper.initDemandMode(staffId);

        // 重新检索返回给前端
        QueryWrapper<FtStaffDemandDao> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id", staffId);
        return AjaxResult.success(this.baseMapper.selectList(wrapper));
    }

}
