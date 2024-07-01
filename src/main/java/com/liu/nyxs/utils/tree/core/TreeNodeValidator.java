package com.liu.nyxs.utils.tree.core;

/**
 * @author ：haoshen
 * @date ：2023-12-07
 * @description : 树节点校验器
 */
@FunctionalInterface
public interface TreeNodeValidator {
    /**
     * 校验方法
     * @param node 节点
     * @return 校验通过则返回null或空串，否则会抛出异常信息
     */
    String valid(TreeNode node);
}

