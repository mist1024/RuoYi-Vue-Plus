package com.ruoyi.system.huiyuan.service;

import com.ruoyi.system.huiyuan.domain.HyProjTalentDao;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 优才项目Service接口
 *
 * @author ryo
 * @date 2021-01-09
 */
public interface IHyProjTalentDaoService extends IService<HyProjTalentDao> {

    /**
     * 查询列表
     */
    List<HyProjTalentDao> queryList(HyProjTalentDao hyProjTalentDao);
}
