package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TestNode;
import com.ruoyi.common.core.mybatisplus.core.BaseMapperPlus;

import java.util.List;

/**
 * 节点维护Mapper接口
 *
 * @author qianlan
 * @date 2021-08-08
 */
public interface TestNodeMapper extends BaseMapperPlus<TestNode> {

	/**
	 * 查询节点以children的方式展示
	 * @param nodeId 前端传来的节点名
	 * @return 带children的节点list
	 */
    List<TestNode> queryListWithChildren(Long nodeId);
}
