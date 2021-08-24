package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.TestNodeBo;
import com.ruoyi.system.domain.vo.TestNodeVo;
import com.ruoyi.system.domain.TestNode;
import com.ruoyi.system.mapper.TestNodeMapper;
import com.ruoyi.system.service.ITestNodeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 节点维护Service业务层处理
 *
 * @author qianlan
 * @date 2021-08-08
 */
@Service
public class TestNodeServiceImpl extends ServicePlusImpl<TestNodeMapper, TestNode, TestNodeVo> implements ITestNodeService {

	@Autowired
	private TestNodeMapper testNodeMapper;

	@Override
	public TestNodeVo queryById(Long id) {
		return getVoById(id);
	}


	@Override
	public List<TestNodeVo> queryList(TestNodeBo bo) {
		return listVo(buildQueryWrapper(bo));
	}

	private LambdaQueryWrapper<TestNode> buildQueryWrapper(TestNodeBo bo) {
		Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<TestNode> lqw = Wrappers.lambdaQuery();
		lqw.like(StrUtil.isNotBlank(bo.getName()), TestNode::getName, bo.getName());
		lqw.eq(StrUtil.isNotBlank(bo.getCategary()), TestNode::getCategary, bo.getCategary());
		lqw.eq(bo.getPid() != null, TestNode::getPid, bo.getPid());
		return lqw;
	}

	@Override
	public Boolean insertByBo(TestNodeBo bo) {
		TestNode add = BeanUtil.toBean(bo, TestNode.class);
		validEntityBeforeSave(add);
		return save(add);
	}

	@Override
	public Boolean updateByBo(TestNodeBo bo) {
		TestNode update = BeanUtil.toBean(bo, TestNode.class);
		validEntityBeforeSave(update);
		return updateById(update);
	}

	/**
	 * 保存前的数据校验
	 *
	 * @param entity 实体类数据
	 */
	private void validEntityBeforeSave(TestNode entity) {
		//TODO 做一些数据校验,如唯一约束
	}

	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if (isValid) {
			//TODO 做一些业务上的校验,判断是否需要校验
		}
		return removeByIds(ids);
	}

	/**
	 * 查询节点以children的方式展示
	 *
	 * @param nodeName 前端传来的节点名
	 * @return 带children的节点list
	 */
	@Override
	public List<TestNode> queryListWithChildren(String nodeName) {
		List<TestNode> nodeList = testNodeMapper.selectList(new QueryWrapper<TestNode>().like("name", nodeName));
		if (nodeList.size() == 0) {
			return null;
		}
		Long id = nodeList.get(0).getId();
		//获得根节点下的所有子节点
		List<TestNode> testNodeList = testNodeMapper.queryListWithChildren(id);
		ArrayList<TestNode> nodeArrayList = new ArrayList<>();
		nodeList.get(0).setChildren(testNodeList);
		nodeArrayList.add(nodeList.get(0));
		return nodeArrayList;
	}

	/**
	 * 获取根节点的名字用于前端页面展示
	 *
	 * @return
	 */
	@Override
	public List getTags() {
		List<TestNode> nodeList = testNodeMapper.selectList(new QueryWrapper<TestNode>().eq("pid", 0));
		ArrayList<String> arrayList = new ArrayList();
		for (TestNode testNode : nodeList) {
			arrayList.add(testNode.getName());
		}
		return arrayList;
	}

	/**
	 * 添加多个节点
	 *
	 * @param map
	 * @return
	 */
	@Override
	public AjaxResult<Void> addMultiple(Map map) {
		int insert = 0;
		int insert1 = 0;
		String name = (String) map.get("name");
		String categary = (String) map.get("categary");

		Long pid = ((Integer) map.get("pid")).longValue();
		String[] nameArray = name.split("#");
		if (StrUtil.isNotBlank(categary)) {
			String[] categaryArray = categary.split("#");
			if (nameArray.length != categaryArray.length) {
				return AjaxResult.error("输入数量不匹配，请重新提交");
			} else {
				for (int i = 0; i < nameArray.length; i++) {
					TestNode testNode = new TestNode();
					testNode.setName(nameArray[i]);
					testNode.setCategary(categaryArray[i]);
					testNode.setPid(pid);
					if (categaryArray[i].trim().isEmpty()) {
						testNode.setCategary(null);
					}
					testNodeMapper.insert(testNode);
					insert++;
				}
			}
		} else {
			for (int i = 0; i < nameArray.length; i++) {
				TestNode testNode = new TestNode();
				testNode.setName(nameArray[i]);
				testNode.setPid(pid);
				testNodeMapper.insert(testNode);
				insert1++;
			}
		}
		if (nameArray.length == insert || nameArray.length == insert1) {
			return AjaxResult.success("添加成功");
		} else {
			return AjaxResult.error("添加失败");
		}
	}
}
