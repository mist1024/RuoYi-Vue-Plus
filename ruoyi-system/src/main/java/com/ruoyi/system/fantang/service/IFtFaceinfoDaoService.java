package com.ruoyi.system.fantang.service;

import com.ruoyi.system.fantang.domain.FtFaceinfoDao;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 人脸信息Service接口
 *
 * @author ryo
 * @date 2021-01-11
 */
public interface IFtFaceinfoDaoService extends IService<FtFaceinfoDao> {

    /**
     * 查询列表
     */
    List<FtFaceinfoDao> queryList(FtFaceinfoDao ftFaceinfoDao);
}
