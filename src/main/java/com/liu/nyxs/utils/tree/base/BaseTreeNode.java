package com.liu.nyxs.utils.tree.base;

import lombok.Getter;

import java.util.List;

/**
 * @author ：haoshen
 * @date ：2023-12-15
 * @description 带泛型参数的树节点
 */
@Getter
@SuppressWarnings("unchecked")
public class BaseTreeNode<T> extends AbstractTreeNode {
    private T data;
    private String key;

    public BaseTreeNode(Long nodeId, Long parentId, String nodeName, T data) {
        super(nodeId, parentId, nodeName);
        this.data = data;
    }

    @Override
    public List<? extends BaseTreeNode<T>> getChildren() {
        return (List<BaseTreeNode<T>>) super.getChildren();
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
