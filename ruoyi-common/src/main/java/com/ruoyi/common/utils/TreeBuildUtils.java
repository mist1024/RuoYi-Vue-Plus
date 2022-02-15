package com.ruoyi.common.utils;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import com.ruoyi.common.constant.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 扩展 hutool TreeUtil 封装系统树构建
 *
 * @author Lion Li
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeBuildUtils extends TreeUtil {

    /**
     * 根据前端定制差异化字段
     */
    public static final TreeNodeConfig DEFAULT_CONFIG = TreeNodeConfig.DEFAULT_CONFIG.setNameKey("label");

    /**
     * 构建整型类型的树型结构
     * @param list          数据列表
     * @param parentId      父级ID
     * @param nodeParser    节点解析
     * @param <T>            实体
     * @return
     */
    public static <T> List<Tree<Long>> build(List<T> list, Long parentId, NodeParser<T, Long> nodeParser) {
        // 默认设为根节点
        if (Validator.isNull(parentId)) {
            parentId = Constants.LongRootId;
        }

        return TreeUtil.build(list, parentId, DEFAULT_CONFIG, nodeParser);
    }

    /**
     * 构建字符串类型的树型结构
     * @param list          数据列表
     * @param parentId      父级ID
     * @param nodeParser    节点解析
     * @param <T>           实体
     * @return
     */
    public static <T> List<Tree<String>> buildStrTree(List<T> list, String parentId, NodeParser<T, String> nodeParser) {
        // 默认设为根节点
        if (Validator.isEmpty(parentId)) {
            parentId = Constants.StringRootId;
        }

        return TreeUtil.build(list, parentId, DEFAULT_CONFIG, nodeParser);
    }

}
