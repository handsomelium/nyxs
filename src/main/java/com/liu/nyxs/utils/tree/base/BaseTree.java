package com.liu.nyxs.utils.tree.base;

import com.liu.nyxs.utils.tree.core.TreeConfig;
import com.liu.nyxs.utils.tree.core.TreeDefinition;
import com.liu.nyxs.utils.tree.core.TreeNode;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author ：haoshen
 * @date ：2023-12-15
 * @description : 基础树，用来快速构建
 */
@SuppressWarnings({"unchecked"})
public class BaseTree<T> extends GenericTree {

    public BaseTree(TreeDefinition treeDefinition) {
        super(treeDefinition);
    }

    public BaseTree(List<? extends BaseTreeNode<T>> metaNodes) {
        super(metaNodes);
    }

    @Override
    protected TreeConfig getTreeConfig(List<TreeNode> treeNodes) {
        return BaseTreeConfig.builder()
                .treeType(BaseTree.class)
                .sourceTreeNodes(treeNodes)
                .build();
    }

    @Override
    public BaseTreeNode<T> getTree() {
        return (BaseTreeNode<T>) super.getTree();
    }

    @Override
    public BaseTreeNode<T> getTreeNodeById(Long nodeId) {
        return (BaseTreeNode<T>) super.getTreeNodeById(nodeId);
    }

    @Override
    public BaseTreeNode<T> getParentNodeById(Long nodeId) {
        return (BaseTreeNode<T>) super.getParentNodeById(nodeId);
    }

    @Override
    public List<BaseTreeNode<T>> getTreeNodeList() {
        return (List<BaseTreeNode<T>>) super.getTreeNodeList();
    }

    @Override
    public List<BaseTreeNode<T>> getDescendent(Long nodeId, boolean containOwn) {
        return (List<BaseTreeNode<T>>) super.getDescendent(nodeId, containOwn);
    }

    @Override
    public BaseTreeIterator<T> iterator() {
        return new BaseTreeIterator<>(this.getTree());
    }

    @Override
    public List<BaseTreeNode<T>> collect(TreeCollector treeCollector) {
        return (List<BaseTreeNode<T>>) super.collect(treeCollector);
    }

    @Override
    public BaseTree<T> filter(TreeFilter treeFilter) {
        return (BaseTree<T>) super.filter(treeFilter);
    }

    @Override
    public <U extends TreeNode, R> BaseTree<T> filterBy(Function<U, R> fieldExtractor, R targetValue) {
        return (BaseTree<T>) super.filterBy(fieldExtractor, targetValue);
    }

    @Override
    public BaseTree<T> filterByName(String nodeName) {
        return (BaseTree<T>) super.filterByName(nodeName);
    }

    public <R> BaseTree<T> filterByGeneric(Function<T, R> genericExtractor, R targetValue) {
        Assert.notNull(genericExtractor, "genericExtractor should not be null");
        return this.filter(treeNode -> {
            if (treeNode instanceof BaseTreeNode) {
                BaseTreeNode<T> baseTreeNode = (BaseTreeNode<T>) treeNode;
                R matchValue = genericExtractor.apply(baseTreeNode.getData());
                return Objects.equals(matchValue, targetValue);
            }
            return false;
        });
    }
}
