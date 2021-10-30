package com.ruoyi.isc.service;

import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.isc.domain.IscAppServiceApply;
import com.ruoyi.isc.domain.bo.IscAppServiceApplyBo;
import com.ruoyi.isc.domain.bo.IscAuditBo;
import com.ruoyi.isc.domain.vo.IscAppServiceApplyVo;

import java.util.Collection;
import java.util.List;

/**
 * 应用服务申请信息Service接口
 *
 * @author Wenchao Gong
 * @date 2021-09-09
 */
public interface IIscAppServiceApplyService extends IServicePlus<IscAppServiceApply, IscAppServiceApplyVo> {
    /**
     * 查询单个
     * @return
     */
    IscAppServiceApplyVo queryById(Long applyId);

    /**
     * 查询列表
     */
    TableDataInfo<IscAppServiceApplyVo> queryPageList(IscAppServiceApplyBo bo);

    /**
     * 查询列表
     */
    List<IscAppServiceApplyVo> queryList(IscAppServiceApplyBo bo);

    /**
     * 根据新增业务对象插入应用服务申请信息
     * @param bo 应用服务申请信息新增业务对象
     * @return
     */
    Boolean insertByBo(IscAppServiceApplyBo bo);

    /**
     * 根据编辑业务对象修改应用服务申请信息
     * @param bo 应用服务申请信息编辑业务对象
     * @return
     */
    Boolean updateByBo(IscAppServiceApplyBo bo);

    /**
     * 校验并删除数据
     * @param ids 主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 应用服务审核
     * @param bo 审核业务数据
     * @return 是否审核成功
     */
    Boolean audit(IscAuditBo bo);
}
