package com.ruoyi.system.fantang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.uuid.IdUtils;
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
    public List<FtStaffInfoDao> selectStaffInfoWithDepart(FtStaffInfoDao ftStaffInfoDao) {

        return this.baseMapper.selectStaffInfoWithDepart(ftStaffInfoDao);
    }

    @Override
    public AjaxResult login(String tel, String password) {
        // 查询是否有该员工
        QueryWrapper<FtStaffInfoDao> wrapper = new QueryWrapper<>();
        wrapper.eq("tel", tel);
        FtStaffInfoDao dao = this.baseMapper.selectOne(wrapper);
        if (dao == null) {
            return AjaxResult.error(-2, "无该员工信息");
        } else {
            wrapper.eq("password", password);
            dao = this.baseMapper.selectOne(wrapper);
            if (dao == null)
                return AjaxResult.error(-1, "密码错误");
            dao.setLoginFlag(true);
            dao.setToken(IdUtils.fastUUID());
            this.baseMapper.updateById(dao);
            return AjaxResult.success(dao);
        }
    }

    @Override
    public AjaxResult logout(Long staffId) {
        FtStaffInfoDao dao = new FtStaffInfoDao();
        dao.setStaffId(staffId);
        dao.setLoginFlag(false);
        dao.setToken("");
        int ret = this.baseMapper.updateById(dao);
        if (ret == 0)
            return AjaxResult.error("更新退出状态失败");
        return AjaxResult.success(dao);
    }

    @Override
    public FtStaffInfoDao getDepartInfo(Long staffId) {
        return this.baseMapper.getDepartInfo(staffId);
    }
}
