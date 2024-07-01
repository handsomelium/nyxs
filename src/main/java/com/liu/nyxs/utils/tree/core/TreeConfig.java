package com.liu.nyxs.utils.tree.core;

import com.liu.nyxs.utils.tree.base.AbstractTree;

import java.util.List;
import java.util.Map;

/**
 * @author ：haoshen
 * @date ：2023-12-26
 * @description : 树配置接口
 */
public interface TreeConfig {
    //最大层数
    int getDeep();
    //是否开启排序（从小到大排序）
    boolean enableSort();
    //树类型
    Class<? extends AbstractTree> getTreeType();
    //树源节点（数据库中）
    List<? extends TreeNode> getSourceTreeNodes();
    //树后置处理器
    List<TreeBuildPostProcessor> getPostProcessors();
    //其余参数
    Map<String, Object> getParameters();
}
