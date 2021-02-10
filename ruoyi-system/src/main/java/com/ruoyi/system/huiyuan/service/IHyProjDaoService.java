package com.ruoyi.system.huiyuan.service;

import com.ruoyi.system.huiyuan.domain.HyProjDao;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 项目信息Service接口
 *
 * @author ryo
 * @date 2021-01-09
 */
public interface IHyProjDaoService extends IService<HyProjDao> {

    /**
     * 查询列表
     */
    List<HyProjDao> queryList(HyProjDao hyProjDao);
}
