package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.fantang.domain.FtOrderDao;
import com.ruoyi.system.fantang.domain.FtPrepaymentDao;
import com.ruoyi.system.fantang.domain.FtPrepaymentVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 收费管理Mapper接口
 *
 * @author ft
 * @date 2020-11-19
 */
public interface FtPrepaymentDaoMapper extends BaseMapper<FtPrepaymentDao> {

    List<FtPrepaymentVo> listNoPrepay(FtPrepaymentVo params);

    List<FtPrepaymentVo>  listPrepay(FtPrepaymentVo params);

    List<FtPrepaymentVo> listAllPrepay(FtPrepaymentVo params);

}
