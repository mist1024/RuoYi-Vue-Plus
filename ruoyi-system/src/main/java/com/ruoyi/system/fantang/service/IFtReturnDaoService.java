package com.ruoyi.system.fantang.service;

import com.ruoyi.system.fantang.domain.FtReturnDao;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 回款登记Service接口
 *
 * @author ft
 * @date 2021-01-04
 */
public interface IFtReturnDaoService extends IService<FtReturnDao> {

    /**
     * 查询列表
     */
    List<FtReturnDao> queryList(FtReturnDao ftReturnDao);
}
