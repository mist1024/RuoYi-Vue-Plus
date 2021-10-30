package com.ruoyi.isc.service;

import cn.hutool.core.lang.tree.Tree;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.isc.domain.IscService;
import com.ruoyi.isc.domain.IscServiceCate;
import com.ruoyi.isc.domain.bo.IscServiceCateBo;
import com.ruoyi.isc.domain.vo.IscServiceCateVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 服务分类Service接口
 *
 * @author Wenchao Gong
 * @date 2021-08-22
 */
public interface IIscServiceCateService extends IServicePlus<IscServiceCate, IscServiceCateVo> {
    /**
     * 查询单个
     * @return
     */
    IscServiceCateVo queryById(Long cateId);


    /**
     * 查询列表
     */
    List<IscServiceCateVo> queryList(IscServiceCateBo bo);

    /**
     * 根据新增业务对象插入服务分类
     * @param bo 服务分类新增业务对象
     * @return
     */
    Boolean insertByBo(IscServiceCateBo bo);

    /**
     * 根据编辑业务对象修改服务分类
     * @param bo 服务分类编辑业务对象
     * @return
     */
    Boolean updateByBo(IscServiceCateBo bo);

    /**
     * 校验并删除数据
     * @param ids 主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 获取分类信息
     * @return 分类列表
     */
    List<IscServiceCate> selectCateList();

    /**
     * 批量获取分类名称
     * @param fullPathList
     * @return
     */
    Map<String, String> batchCateName(Set<String> fullPathList);

    /**
     * 组装 服务分类 树结构
     * @param cates 所有分类
     * @return 树结构信息
     */
    List<Tree<Long>> genCateTree(List<IscServiceCate> cates);

    /**
     * 组装 服务分类 树结构
     * @param cates       所有分类
     * @param serviceList 可用服务列表
     * @param exitsIds    已存在服务ID集合
     * @return 树结构信息
     */
    List<Tree<Long>> genCateTree(List<IscServiceCate> cates, List<IscService> serviceList, Set<Long> exitsIds);
}
