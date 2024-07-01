package com.liu.nyxs.utils.tree.base;

import com.liu.nyxs.utils.tree.constantts.Constants;
import com.liu.nyxs.utils.tree.core.TreeConfig;
import com.liu.nyxs.utils.tree.core.TreeDefinition;
import com.liu.nyxs.utils.tree.core.TreeNode;
import org.springframework.util.Assert;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ：haoshen
 * @date ：2023-12-18
 * @description 组合树
 */
@SuppressWarnings({"unchecked"})
public class MixedTree extends BaseTree<Object> {
    private final Map<Type, List<MixedTreeNode>> nodeTypeMap = new HashMap<>();

    private MixedTree(TreeDefinition treeDefinition) {
        super(treeDefinition);
    }

    public MixedTree(List<MixedTreeNode> metaNodes) {
        super(metaNodes);
        initNodeTypeMap(metaNodes);
    }

    @Override
    protected TreeConfig getTreeConfig(List<TreeNode> treeNodes) {
        return BaseTreeConfig.builder()
                .treeType(MixedTree.class)
                .sourceTreeNodes(treeNodes)
                .build();
    }

    @Override
    public MixedTreeNode getTree() {
        return (MixedTreeNode) super.getTree();
    }

    private void initNodeTypeMap(List<MixedTreeNode> metaNodes) {
        Map<? extends Class<?>, List<MixedTreeNode>> nodeGroupByType = metaNodes.stream()
                .collect(Collectors.groupingBy(MixedTreeNode::getType));
        this.nodeTypeMap.putAll(nodeGroupByType);
    }

    @Override
    public MixedTreeNode getTreeNodeById(Long nodeId) {
        return (MixedTreeNode) super.getTreeNodeById(nodeId);
    }

    public List<MixedTreeNode> getNodesByType(Class<?> type) {
        if (type == null) {
            return this.nodeTypeMap.get(Constants.NONE.getClass());
        }
        return this.nodeTypeMap.get(type);
    }

    @Override
    public MixedTree filter(TreeFilter treeFilter) {
        return (MixedTree) super.filter(treeFilter);
    }

    @Override
    public <U extends TreeNode, R> MixedTree filterBy(Function<U, R> fieldExtractor, R targetValue) {
        return (MixedTree) super.filterBy(fieldExtractor, targetValue);
    }

    @Override
    public MixedTree filterByName(String nodeName) {
        return (MixedTree) super.filterByName(nodeName);
    }

    @Override
    public <R> MixedTree filterByGeneric(Function<Object, R> genericExtractor, R targetValue) {
        return (MixedTree) super.filterByGeneric(genericExtractor, targetValue);
    }

    public <U, R> MixedTree filterByGeneric(Class<U> type, Function<U, R> genericExtractor, R targetValue) {
        Assert.notNull(type, "type should not be null");
        Assert.notNull(genericExtractor, "genericExtractor should not be null");

        return this.filter(treeNode -> {
            if (treeNode instanceof MixedTreeNode) {
                MixedTreeNode mixedTreeNode = (MixedTreeNode) treeNode;
                Class<?> nodeType = mixedTreeNode.getType();
                if (nodeType != null && nodeType == type) {
                    R matchValue = genericExtractor.apply((U) mixedTreeNode.getData());
                    return Objects.equals(matchValue, targetValue);
                }
            }
            return false;
        });
    }
}
