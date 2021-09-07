package com.ruoyi.common.utils;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;

import java.util.List;

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
