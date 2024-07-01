package com.liu.nyxs.utils.tree.base;

import com.liu.nyxs.utils.tree.core.TreeNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：haoshen
 * @date ：2023-12-08
 * @description : 默认树节点
 */
public abstract class AbstractTreeNode implements TreeNode, Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private Long nodeId;
    private Long parentId;
    private String nodeName;
    private Integer level;
    private List<Long> parentIds;
    private String path;
    private String sort;
    private List<TreeNode> children;

    public AbstractTreeNode(Long nodeId, Long parentId, String nodeName) {
        this.nodeId = nodeId;
        this.parentId = parentId;
        this.nodeName = nodeName;
    }

    @Override
    public Long getNodeId() {
        return this.nodeId;
    }

    @Override
    public Long getParentId() {
        return this.parentId;
    }

    @Override
    public String getNodeName() {
        return this.nodeName;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public List<Long> getParentIds() {
        return parentIds;
    }

    @Override
    public String getSort() {
        return sort;
    }

    @Override
    public List<? extends TreeNode> getChildren() {
        return this.children;
    }

    @Override
    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public void setChildren(List<? extends TreeNode> children) {
        if (this.children != null) {
            this.children.clear();
        }
        if (children != null) {
            children.forEach(this::addChildren);
        }
    }

    @Override
    public void addChildren(TreeNode... children) {
        if (children == null || children.length == 0) {
            return;
        }
        synchronized (this) {
            if (this.children == null) {
                this.children = new ArrayList<>();
            }
            this.children.addAll(Arrays.asList(children));
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public void setParentIds(List<Long> parentIds) {
        this.parentIds = parentIds;
    }

    @Override
    public TreeNode clone() {
        TreeNode treeNode;
        try {
            treeNode = (TreeNode) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return treeNode;
    }

    @Override
    public TreeNode copy() {
        TreeNode copyNode = this.clone();
        copyNode.setChildren(null);
        return copyNode;
    }
}
