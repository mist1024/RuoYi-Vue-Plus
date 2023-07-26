package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.UserBo;
import com.ruoyi.system.domain.vo.UserVo;
import com.ruoyi.system.domain.User;
import com.ruoyi.system.mapper.UserMapper;
import com.ruoyi.system.service.IUserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-03
 */
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {

    private final UserMapper baseMapper;

    /**
     * 查询【请填写功能名称】
     */
    @Override
    public UserVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public TableDataInfo<UserVo> queryPageList(UserBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<User> lqw = buildQueryWrapper(bo);
        Page<UserVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public List<UserVo> queryList(UserBo bo) {
        LambdaQueryWrapper<User> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<User> buildQueryWrapper(UserBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<User> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getStatus() != null, User::getStatus, bo.getStatus());
        lqw.like(StringUtils.isNotBlank(bo.getLoginName()), User::getLoginName, bo.getLoginName());
        lqw.eq(StringUtils.isNotBlank(bo.getPassword()), User::getPassword, bo.getPassword());
        lqw.eq(StringUtils.isNotBlank(bo.getCardNumber()), User::getCardNumber, bo.getCardNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), User::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getValidityDate()), User::getValidityDate, bo.getValidityDate());
        lqw.eq(StringUtils.isNotBlank(bo.getWxToken()), User::getWxToken, bo.getWxToken());
        lqw.eq(bo.getEnterNumber() != null, User::getEnterNumber, bo.getEnterNumber());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), User::getUserName, bo.getUserName());
        lqw.eq(bo.getLastTime() != null, User::getLastTime, bo.getLastTime());
        lqw.eq(bo.getDirection() != null, User::getDirection, bo.getDirection());
        lqw.eq(bo.getJurisdiction() != null, User::getJurisdiction, bo.getJurisdiction());
        lqw.eq(StringUtils.isNotBlank(bo.getJobWanted()), User::getJobWanted, bo.getJobWanted());
        lqw.eq(bo.getRegisterTime() != null, User::getRegisterTime, bo.getRegisterTime());
        lqw.eq(StringUtils.isNotBlank(bo.getWxToken1()), User::getWxToken1, bo.getWxToken1());
        lqw.eq(StringUtils.isNotBlank(bo.getCardId()), User::getCardId, bo.getCardId());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     */
    @Override
    public Boolean insertByBo(UserBo bo) {
        User add = BeanUtil.toBean(bo, User.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改【请填写功能名称】
     */
    @Override
    public Boolean updateByBo(UserBo bo) {
        User update = BeanUtil.toBean(bo, User.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(User entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除【请填写功能名称】
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return baseMapper.update(null,
            new LambdaUpdateWrapper<User>()
                .set(User::getPassword, password)
                .eq(User::getLoginName, userName));
    }
}
