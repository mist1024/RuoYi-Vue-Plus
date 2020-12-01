package com.ruoyi.system.fantang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.fantang.domain.FtPrepaymentVo;

import java.util.List;

/**
 * 收费管理Service接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface IFtPrepaymentDaoService extends IService<FtPrepaymentVo> {

    List<com.ruoyi.system.fantang.vo.FtPrepaymentVo> listNoPrepay();

    List<com.ruoyi.system.fantang.vo.FtPrepaymentVo> listPrepay();

    List<com.ruoyi.system.fantang.vo.FtPrepaymentVo> listAllPrepay();

    int getCountById(Long patiendId);
}
