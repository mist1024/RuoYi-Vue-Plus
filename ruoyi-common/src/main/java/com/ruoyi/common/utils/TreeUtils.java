package com.ruoyi.common.utils;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;

import java.util.List;

/**
 * 树结构工具类
 *
 * @author Wenchao Gong
 * @date 2021-09-08
 */
public class TreeUtils {

    public static final TreeNodeConfig DEFAULT_TREE_NODE_CONFIG = new TreeNodeConfig();

    static
    {
        DEFAULT_TREE_NODE_CONFIG.setNameKey("label");
    }

    public static <T> List<Tree<Long>> build(List<T> list, NodeParser<T, Long> nodeParser)
    {
        Long parentId = 0L;
        return TreeUtil.build(list, parentId, DEFAULT_TREE_NODE_CONFIG, nodeParser);
    }
}
