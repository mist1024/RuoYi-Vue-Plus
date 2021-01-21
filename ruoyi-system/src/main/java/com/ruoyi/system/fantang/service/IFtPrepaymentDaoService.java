package com.ruoyi.system.fantang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.fantang.domain.FtPrepaymentDao;
import com.ruoyi.system.fantang.domain.FtPrepaymentVo;

import java.util.List;

/**
 * 收费管理Service接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface IFtPrepaymentDaoService extends IService<FtPrepaymentDao> {

    List<FtPrepaymentVo> listNoPrepay();

    IPage<FtPrepaymentVo> listPrepay(Integer pageNum, Integer pageSize);

    List<FtPrepaymentVo> listAllPrepay();

    FtPrepaymentVo getCountById(Long patiendId);

    FtPrepaymentDao getByPatientId(Long patientId);
}
