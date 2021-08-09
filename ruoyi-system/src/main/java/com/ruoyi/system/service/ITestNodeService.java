package com.ruoyi.system.service;

import com.ruoyi.system.domain.TestNode;
import com.ruoyi.system.domain.vo.TestNodeVo;
import com.ruoyi.system.domain.bo.TestNodeBo;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;

import java.util.Collection;
import java.util.List;

/**
 * 节点维护Service接口
 *
 * @author qianlan
 * @date 2021-08-08
 */
public interface ITestNodeService extends IServicePlus<TestNode, TestNodeVo> {
	/**
	 * 查询单个
	 * @return
	 */
	TestNodeVo queryById(Long id);


	/**
	 * 查询列表
	 */
	List<TestNodeVo> queryList(TestNodeBo bo);

	/**
	 * 根据新增业务对象插入节点维护
	 * @param bo 节点维护新增业务对象
	 * @return
	 */
	Boolean insertByBo(TestNodeBo bo);

	/**
	 * 根据编辑业务对象修改节点维护
	 * @param bo 节点维护编辑业务对象
	 * @return
	 */
	Boolean updateByBo(TestNodeBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
