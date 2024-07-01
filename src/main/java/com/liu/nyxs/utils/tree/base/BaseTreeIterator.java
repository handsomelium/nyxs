package com.liu.nyxs.utils.tree.base;

import java.util.List;

/**
 * @author ：haoshen
 * @date ：2023-12-15
 * @description : 带泛型的树迭代器
 */
@SuppressWarnings({"unchecked"})
public class BaseTreeIterator<T> extends GenericTreeIterator {
    private final BaseTreeNode<T> treeNode;
    public BaseTreeIterator(BaseTreeNode<T> treeNode) {
        super(treeNode);
        this.treeNode = treeNode;
    }

    @Override
    public BaseTreeNode<T> next() {
        return (BaseTreeNode<T>) super.next();
    }

    @Override
    public List<BaseTreeNode<T>> nextLevel() {
        return (List<BaseTreeNode<T>>) super.nextLevel();
    }

    public BaseTreeNode<T> getTree() {
        return this.treeNode;
    }

}
