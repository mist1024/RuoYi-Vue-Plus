package com.ruoyi.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 树结构工具类
 *
 * @author Wenchao Gong
 * @date 2021-09-08
 */
public class TreeUtils
{

    public static final TreeNodeConfig DEFAULT_TREE_NODE_CONFIG = new TreeNodeConfig();
    public static final long DEFAULT_PARENT_ID = 0L;
    public static final String FIELD_NODE_EXIST = "exist";

    static {
        DEFAULT_TREE_NODE_CONFIG.setNameKey("label");
    }

    /**
     * 设置默认 parentId
     *
     * @param list
     * @param nodeParser
     * @param <T>
     * @return
     */
    public static <T> List<Tree<Long>> build(List<T> list, NodeParser<T, Long> nodeParser)
    {
        return TreeUtil.build(list, DEFAULT_PARENT_ID, DEFAULT_TREE_NODE_CONFIG, nodeParser);
    }


    /**
     * 树构建
     *
     * @param <T>            转换的实体 为数据源里的对象类型
     * @param <E>            ID类型
     * @param list           源数据集合
     * @param parentId       最顶层父id值 一般为 0 之类
     * @param nodeParser     转换器
     * @param needExistField 是否需要 已存在字段
     * @return List
     */
    public static <T, E> List<Tree<E>> build(List<T> list, E parentId, NodeParser<T, E> nodeParser,
                                             boolean needExistField)
    {
        final List<Tree<E>> treeList = CollUtil.newArrayList();
        final TreeNodeConfig treeNodeConfig = DEFAULT_TREE_NODE_CONFIG;
        Tree<E> tree;
        for (T obj : list) {
            tree = new Tree<>(treeNodeConfig);
            nodeParser.parse(obj, tree);
            treeList.add(tree);
        }

        List<Tree<E>> finalTreeList = CollUtil.newArrayList();
        for (Tree<E> node : treeList) {
            if (ObjectUtil.equals(parentId, node.getParentId())) {
                finalTreeList.add(node);
                innerBuild(treeList, node, 0, treeNodeConfig.getDeep(), needExistField);
            }
        }
        // 内存每层已经排过了 这是最外层排序
        finalTreeList = finalTreeList.stream().sorted().collect(Collectors.toList());
        return finalTreeList;
    }

    /**
     * 递归处理
     *
     * @param treeNodes      数据集合
     * @param parentNode     当前节点
     * @param deep           已递归深度
     * @param maxDeep        最大递归深度 可能为null即不限制
     * @param needExistField 是否需要 已存在字段
     */
    private static <T> void innerBuild(List<Tree<T>> treeNodes, Tree<T> parentNode, int deep, Integer maxDeep,
                                       boolean needExistField)
    {
        if (CollUtil.isEmpty(treeNodes)) {
            return;
        }
        //maxDeep 可能为空
        if (maxDeep != null && deep >= maxDeep) {
            return;
        }
        // 每层排序 TreeNodeMap 实现了Comparable接口
        treeNodes = treeNodes.stream().sorted().collect(Collectors.toList());
        for (Tree<T> childNode : treeNodes) {
            if (parentNode.getId().equals(childNode.getParentId())) {
                List<Tree<T>> children = parentNode.getChildren();
                if (children == null) {
                    children = CollUtil.newArrayList();
                    parentNode.setChildren(children);
                }
                children.add(childNode);
                childNode.setParent(parentNode);
                if (needExistField) {
                    Object pobj = parentNode.get(FIELD_NODE_EXIST);
                    boolean exist = pobj == null ? true : (boolean) pobj;
                    if (exist && !CollectionUtils.isEmpty(children)) {
                        long count = children.stream().filter(t -> {
                            Object obj = t.get(FIELD_NODE_EXIST);
                            if (obj != null) {
                                return !(boolean) obj;
                            } else {
                                return false;
                            }
                        }).count();
                        if (count > 0) {
                            exist = false;
                        }
                    }
                    parentNode.put(FIELD_NODE_EXIST, exist);
                }
                innerBuild(treeNodes, childNode, deep + 1, maxDeep, needExistField);
            }
        }
    }
}
