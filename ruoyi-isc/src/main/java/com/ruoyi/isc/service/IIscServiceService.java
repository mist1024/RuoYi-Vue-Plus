package com.ruoyi.isc.service;

import cn.hutool.core.lang.tree.Tree;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.isc.domain.IscService;
import com.ruoyi.isc.domain.bo.IscAuditBo;
import com.ruoyi.isc.domain.bo.IscServiceBo;
import com.ruoyi.isc.domain.vo.IscServiceVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 服务信息Service接口
 *
 * @author Wenchao Gong
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

    /**
     * 通过服务ID集合 查询服务名称map
     * @param serviceIds 服务ID集合
     * @return 服务名称map
     */
    Map<Long, String> getNameMap(Collection<Long> serviceIds);

    /**
     * 组装 服务树结构
     * @return 服务树结构
     */
    List<Tree<Long>> genServiceTree();

    /**
     * 组装 服务树结构
     * @param exitsIds 已存在服务ID集合
     * @return 服务树结构
     */
    List<Tree<Long>> genServiceTree(Set<Long> exitsIds);

    /**
     * 服务信息审核
     * @param bo 审核信息
     * @return 操作结果
     */
    Boolean audit(IscAuditBo bo);

    /**
     * 校验 审核参数
     * @param bo 审核业务对象
     */
    void checkAuditBO(IscAuditBo bo);
}
