package com.ruoyi.isc.service;

import cn.hutool.core.lang.tree.Tree;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.isc.domain.IscAppService;
import com.ruoyi.isc.domain.bo.IscAppServiceBo;
import com.ruoyi.isc.domain.vo.IscAppServiceVo;

import java.util.Collection;
import java.util.List;

/**
 * 应用服务Service接口
 *
 * @author Wenchao Gong
 * @date 2021-09-08
 */
public interface IIscAppServiceService extends IServicePlus<IscAppService, IscAppServiceVo> {
    /**
     * 查询单个
     * @return
     */
    IscAppServiceVo queryById(Long appServiceId);

    /**
     * 查询列表
     */
    TableDataInfo<IscAppServiceVo> queryPageList(IscAppServiceBo bo);

    /**
     * 查询列表
     */
    List<IscAppServiceVo> queryList(IscAppServiceBo bo);

    /**
     * 根据新增业务对象插入应用服务
     * @param bo 应用服务新增业务对象
     * @return
     */
    Boolean insertByBo(IscAppServiceBo bo);

    /**
     * 根据编辑业务对象修改应用服务
     * @param bo 应用服务编辑业务对象
     * @return
     */
    Boolean updateByBo(IscAppServiceBo bo);

    /**
     * 校验并删除数据
     * @param ids 主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 构造 应用服务树结构
     * @param applicationId 应用ID
     * @return 应用服务树结构 （标识服务是否存在）
     */
    List<Tree<Long>> genAppServiceTree(Long applicationId);

    /**
     * 通过 Id集合 返回列表
     * @param ids 应用服务ID
     * @return 应用服务列表
     */
    List<IscAppService> getAppServiceListByIds(Collection<Long> ids);

    /**
     * 刷新规则信息（服务对应AK）
     */
    void refreshRules();
}
