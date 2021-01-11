package com.ruoyi.system.fantang.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.ruoyi.system.fantang.mapper.FtFaceinfoDaoMapper;
import com.ruoyi.system.fantang.domain.FtFaceinfoDao;
import com.ruoyi.system.fantang.service.IFtFaceinfoDaoService;

import java.util.List;
import java.util.Map;

/**
 * 人脸信息Service业务层处理
 *
 * @author ryo
 * @date 2021-01-11
 */
@Service
public class FtFaceinfoDaoServiceImpl extends ServiceImpl<FtFaceinfoDaoMapper, FtFaceinfoDao> implements IFtFaceinfoDaoService {

    @Override
    public List<FtFaceinfoDao> queryList(FtFaceinfoDao ftFaceinfoDao) {
        LambdaQueryWrapper<FtFaceinfoDao> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(ftFaceinfoDao.getDeviceId())){
            lqw.eq(FtFaceinfoDao::getDeviceId ,ftFaceinfoDao.getDeviceId());
        }
        if (ftFaceinfoDao.getPersonId() != null){
            lqw.eq(FtFaceinfoDao::getPersonId ,ftFaceinfoDao.getPersonId());
        }
        if (StringUtils.isNotBlank(ftFaceinfoDao.getPhone())){
            lqw.eq(FtFaceinfoDao::getPhone ,ftFaceinfoDao.getPhone());
        }
        return this.list(lqw);
    }
}
