package com.ruoyi.system.huiyuan.service;

import com.ruoyi.system.huiyuan.domain.HyMemberDao;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 会员Service接口
 *
 * @author ryo
 * @date 2021-01-09
 */
public interface IHyMemberDaoService extends IService<HyMemberDao> {

    /**
     * 查询列表
     */
    List<HyMemberDao> queryList(HyMemberDao hyMemberDao);
}
