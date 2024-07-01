package com.liu.nyxs.utils.tree.core;

import com.liu.nyxs.utils.tree.exception.TreeException;

import java.util.Iterator;
import java.util.List;

/**
 * @author ：haoshen
 * @date ：2023-12-13
 * @description : 树迭代器
 */
public interface TreeIterator extends Iterator<TreeNode> {
    @Override
    default void remove() {
        throw new TreeException("Remove is not support for the treeIterator");
    }

    /**
     * 查看是否有下一个层级
     */
    boolean hasNextLevel();

    /**
     * 取层级List
     * @return 对应层级的List
     */
    List<? extends TreeNode> nextLevel();
}
