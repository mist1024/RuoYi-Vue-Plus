package com.ruoyi.isc.service;

import com.ruoyi.isc.domain.IscService;
import com.ruoyi.isc.domain.vo.IscServiceVo;
import com.ruoyi.isc.domain.bo.IscServiceBo;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 服务信息Service接口
 *
 * @author ruoyi
 * @date 2021-08-22
 */
public interface IIscServiceService extends IServicePlus<IscService, IscServiceVo> {
    /**
     * 查询单个
     * @return
     */
    IscServiceVo queryById(Long serviceId);

    /**
     * 查询列表
     */
    TableDataInfo<IscServiceVo> queryPageList(IscServiceBo bo);

    /**
     * 查询列表
     */
    List<IscServiceVo> queryList(IscServiceBo bo);

    /**
     * 根据新增业务对象插入服务信息
     * @param bo 服务信息新增业务对象
     * @return
     */
    Boolean insertByBo(IscServiceBo bo);

    /**
     * 根据编辑业务对象修改服务信息
     * @param bo 服务信息编辑业务对象
     * @return
     */
    Boolean updateByBo(IscServiceBo bo);

    /**
     * 校验并删除数据
     * @param ids 主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
