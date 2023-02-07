package com.ruoyi.system.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.bo.SysUserBo;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.SysTenantBo;
import com.ruoyi.system.domain.vo.SysTenantVo;
import com.ruoyi.system.service.ISysTenantService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 租户Service业务层处理
 *
 * @author Michelle.Chung
 */
@RequiredArgsConstructor
@Service
public class SysTenantServiceImpl implements ISysTenantService {

    private final ISysUserService iSysUserService;
    private final SysTenantMapper baseMapper;
    private final SysTenantPackageMapper sysTenantPackageMapper;
    private final SysUserMapper sysUserMapper;
    private final SysDeptMapper sysDeptMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysRoleDeptMapper sysRoleDeptMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    /**
     * 查询租户
     */
    @Override
    public SysTenantVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询租户列表
     */
    @Override
    public TableDataInfo<SysTenantVo> queryPageList(SysTenantBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysTenant> lqw = buildQueryWrapper(bo);
        Page<SysTenantVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询租户列表
     */
    @Override
    public List<SysTenantVo> queryList(SysTenantBo bo) {
        LambdaQueryWrapper<SysTenant> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysTenant> buildQueryWrapper(SysTenantBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysTenant> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), SysTenant::getTenantId, bo.getTenantId());
        lqw.like(StringUtils.isNotBlank(bo.getContactUserName()), SysTenant::getContactUserName, bo.getContactUserName());
        lqw.eq(StringUtils.isNotBlank(bo.getContactPhone()), SysTenant::getContactPhone, bo.getContactPhone());
        lqw.like(StringUtils.isNotBlank(bo.getCompanyName()), SysTenant::getCompanyName, bo.getCompanyName());
        lqw.eq(StringUtils.isNotBlank(bo.getLicenseNumber()), SysTenant::getLicenseNumber, bo.getLicenseNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getAddress()), SysTenant::getAddress, bo.getAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getIntro()), SysTenant::getIntro, bo.getIntro());
        lqw.eq(bo.getPackageId() != null, SysTenant::getPackageId, bo.getPackageId());
        lqw.eq(bo.getExpireTime() != null, SysTenant::getExpireTime, bo.getExpireTime());
        lqw.eq(bo.getAccountCount() != null, SysTenant::getAccountCount, bo.getAccountCount());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysTenant::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增租户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(SysTenantBo bo) {
        SysTenant add = BeanUtil.toBean(bo, SysTenant.class);

        SysUserBo userBo = new SysUserBo();
        userBo.setUserName(bo.getUsername());
        // 判断用户名是否重复
        if (UserConstants.NOT_UNIQUE.equals(iSysUserService.checkUserNameUnique(userBo))) {
            throw new ServiceException("新增用户'" + bo.getUsername() + "'失败，登录账号已存在");
        }

        // 获取所有租户编号
        List<String> tenantIds = baseMapper.selectObjs(
            new LambdaQueryWrapper<SysTenant>().select(SysTenant::getTenantId), Convert::toStr);
        String tenantId = generateTenantId(tenantIds);
        add.setTenantId(tenantId);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }

        // 根据套餐创建角色
        Long roleId = createTenantRole(tenantId, bo.getPackageId());

        // 创建部门: 公司名是部门名称
        SysDept dept = new SysDept();
        dept.setTenantId(tenantId);
        dept.setDeptName(bo.getCompanyName());
        dept.setAncestors(Constants.TOP_PARENT_ID.toString());
        sysDeptMapper.insert(dept);
        Long deptId = dept.getDeptId();

        // 角色和部门关联表
        SysRoleDept roleDept = new SysRoleDept();
        roleDept.setRoleId(roleId);
        roleDept.setDeptId(deptId);
        sysRoleDeptMapper.insert(roleDept);

        // 创建系统用户
        SysUser user = new SysUser();
        user.setTenantId(tenantId);
        user.setUserName(bo.getUsername());
        user.setNickName(bo.getUsername());
        user.setPassword(BCrypt.hashpw(bo.getPassword()));
        user.setDeptId(deptId);
        sysUserMapper.insert(user);

        // 用户和角色关联表
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(roleId);
        sysUserRoleMapper.insert(userRole);

        return flag;
    }

    /**
     * 生成租户id
     *
     * @param tenantIds 已有租户id列表
     * @return 租户id
     */
    private String generateTenantId(List<String> tenantIds) {
        // 随机生成6位
        String numbers = RandomUtil.randomNumbers(6);
        // 判断是否存在，如果存在则重新生成
        if (tenantIds.contains(numbers)) {
            generateTenantId(tenantIds);
        }
        return numbers;
    }

    /**
     * 根据租户菜单创建租户角色
     *
     * @param tenantId  租户编号
     * @param packageId 租户套餐id
     * @return 角色id
     */
    @Transactional(rollbackFor = Exception.class)
    public Long createTenantRole(String tenantId, Long packageId) {
        // 获取租户套餐
        SysTenantPackage tenantPackage = sysTenantPackageMapper.selectById(packageId);
        if (ObjectUtil.isNull(tenantPackage)) {
            throw new ServiceException("套餐不存在");
        }
        // 获取套餐菜单id
        List<Long> menuIds = StringUtils.splitTo(tenantPackage.getMenuIds(), Convert::toLong);

        // 创建角色
        SysRole role = new SysRole();
        role.setTenantId(tenantId);
        role.setRoleName(UserConstants.ADMIN_ROLE_NAME);
        role.setRoleKey(UserConstants.ADMIN_ROLE_KEY);
        role.setRoleSort(UserConstants.ADMIN_ROLE_SORT);
        role.setStatus(UserConstants.NORMAL);
        sysRoleMapper.insert(role);
        Long roleId = role.getRoleId();

        // 创建角色菜单
        List<SysRoleMenu> roleMenus = new ArrayList<>(menuIds.size());
        menuIds.forEach(menuId -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        });
        sysRoleMenuMapper.insertBatch(roleMenus);

        return roleId;
    }

    /**
     * 修改租户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(SysTenantBo bo) {
        SysTenant update = BeanUtil.toBean(bo, SysTenant.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 修改租户状态
     *
     * @param bo 租户信息
     * @return 结果
     */
    @Override
    public int updateTenantStatus(SysTenantBo bo) {
        SysTenant tenant = BeanUtil.toBean(bo, SysTenant.class);
        return baseMapper.updateById(tenant);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysTenant entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除租户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
