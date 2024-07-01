package com.liu.nyxs.utils.tree.core;

/**
 * @author ：haoshen
 * @date ：2023-12-17
 * @description 树构建后置处理器，用于扩展树初始化功能，当树构建完后就会执行，由于可直接修改树节点，请谨慎实现！
 */
public interface TreeBuildPostProcessor {

    //处理节点
    void process(TreeDefinition treeDefinition, TreeConfig treeConfig);
}
