package com.liu.nyxs.utils.tree.core;

import java.util.List;

/**
 * @author ：haoshen
 * @date ：2023-12-07
 * @description : 树构造器
 */
public interface TreeDefinitionBuilder {
    /**
     * 构建树节点
     */
    TreeDefinitionBuilder buildNodes(List<? extends TreeNode> metaNodes);
    /**
     * 构建树校验器
     */
    TreeDefinitionBuilder buildTreeNodeValidators(TreeNodeValidator... validators);
    /**
     * 构建树定义类型
     */
    //构建树定义
    TreeDefinitionBuilder buildTreeDefinition(Class<? extends TreeDefinition> definitionType, Object... args);
    /**
     * 构建树配置
     */
    TreeDefinitionBuilder buildTreeConfig(TreeConfig treeConfig);
    /**
     * 构建树后置处理器
     */
    TreeDefinitionBuilder buildPostProcessor(TreeBuildPostProcessor... postProcessors);
    /**
     * 构建树
     */
    TreeDefinition build();
}
