package com.ruoyi.system.fantang.service;

import com.ruoyi.system.fantang.domain.FtConfigDao;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 饭堂参数Service接口
 *
 * @author ft
 * @date 2020-12-07
 */
public interface IFtConfigDaoService extends IService<FtConfigDao> {

    Map<String, String> getDinnerTimeSetting();
}
