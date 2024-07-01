package com.liu.nyxs.utils.tree.core;

import java.util.List;

/**
 * @author ：haoshen
 * @date ：2023-12-07
 * @description : 树节点
 */
public interface TreeNode {
    //节点Id，保证唯一
    Long getNodeId();

    //节点父Id，保证唯一
    Long getParentId();

    //节点名称
    String getNodeName();

    //节点层级
    int getLevel();

    //节点路径
    String getPath();

    //节点上级ids
    List<Long> getParentIds();

    //排序
    String getSort();

    //子节点
    List<? extends TreeNode> getChildren();

    void setNodeId(Long nodeId);

    void setParentId(Long parentId);

    void setNodeName(String nodeName);

    void setLevel(int level);

    void setPath(String path);

    void setParentIds(List<Long> parentIds);

    void setSort(String sort);

    void setChildren(List<? extends TreeNode> children);

    void addChildren(TreeNode... children);

    //浅克隆
    TreeNode clone();

    //复制，会清空children
    TreeNode copy();
}
