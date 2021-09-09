package com.ruoyi.isc.service;

import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.isc.domain.IscApplication;
import com.ruoyi.isc.domain.bo.IscApplicationBo;
import com.ruoyi.isc.domain.vo.IscApplicationVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 应用信息Service接口
 *
 * @author Wenchao Gong
 * @date 2021-09-08
 */
public interface IIscApplicationService extends IServicePlus<IscApplication, IscApplicationVo> {
    /**
     * 查询单个
     * @return
     */
    IscApplicationVo queryById(Long applicationId);

    /**
     * 查询列表
     */
    TableDataInfo<IscApplicationVo> queryPageList(IscApplicationBo bo);

    /**
     * 查询列表
     */
    List<IscApplicationVo> queryList(IscApplicationBo bo);

    /**
     * 根据新增业务对象插入应用信息
     * @param bo 应用信息新增业务对象
     * @return
     */
    Boolean insertByBo(IscApplicationBo bo);

    /**
     * 根据编辑业务对象修改应用信息
     * @param bo 应用信息编辑业务对象
     * @return
     */
    Boolean updateByBo(IscApplicationBo bo);

    /**
     * 校验并删除数据
     * @param ids 主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 通过应用ID集合批量查询 应用名称
     * @param applicationIds 应用ID集合
     * @return 应用名称Map
     */
    Map<Long, String> getNameMap(Collection<Long> applicationIds);
}
