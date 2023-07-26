package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.User;
import com.ruoyi.system.domain.vo.UserVo;
import com.ruoyi.system.domain.bo.UserBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2023-04-03
 */
public interface IUserService {

    /**
     * 查询【请填写功能名称】
     */
    UserVo queryById(Long id);

    /**
     * 查询【请填写功能名称】列表
     */
    TableDataInfo<UserVo> queryPageList(UserBo bo, PageQuery pageQuery);

    /**
     * 查询【请填写功能名称】列表
     */
    List<UserVo> queryList(UserBo bo);

    /**
     * 新增【请填写功能名称】
     */
    Boolean insertByBo(UserBo bo);

    /**
     * 修改【请填写功能名称】
     */
    Boolean updateByBo(UserBo bo);

    /**
     * 校验并批量删除【请填写功能名称】信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    int resetUserPwd(String userName, String password);

}
