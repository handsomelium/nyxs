package com.liu.nyxs.utils.tree.base;

import com.liu.nyxs.utils.MyReflectUtils;
import com.liu.nyxs.utils.tree.core.*;
import com.liu.nyxs.utils.tree.exception.TreeInitException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author ：haoshen
 * @date ：2023-12-07
 * @description : 默认树定义构建者
 */
public class BaseTreeDefinitionBuilder implements TreeDefinitionBuilder {
    private final Set<Long> nodeIdValidator = new HashSet<>();
    private List<TreeNodeValidator> treeNodeValidators;
    private List<TreeNode> treeNodes;
    private TreeConfig treeConfig;
    private List<TreeBuildPostProcessor> postProcessors;
    private Class<? extends TreeDefinition> definitionType = BaseTreeDefinition.class;
    private TreeNode root = null;

    @Override
    public TreeDefinitionBuilder buildNodes(List<? extends TreeNode> metaNodes) {
        if (CollectionUtils.isEmpty(metaNodes)) {
            throw new IllegalArgumentException("The nodes of tree should not be empty");
        }
        for (TreeNode metaNode : metaNodes) {
            validNodeId(metaNode);
        }
        //找不到根节点
        if (this.root == null) {
            throw new IllegalArgumentException("Unable to locate the root node of tree. Please ensure that there is a" +
                    " " +
                    "node with null `parentId`");
        }
        this.treeNodes = new LinkedList<>(metaNodes);
        return this;
    }

    @Override
    public TreeDefinitionBuilder buildTreeNodeValidators(TreeNodeValidator... validators) {
        if (validators != null && validators.length > 0) {
            this.treeNodeValidators = Arrays.asList(validators);
        }
        return this;
    }

    @Override
    public TreeDefinitionBuilder buildTreeDefinition(Class<? extends TreeDefinition> definitionType, Object... args) {
        if (definitionType != null) {
            this.definitionType = definitionType;
        }
        return this;
    }

    @Override
    public TreeDefinitionBuilder buildTreeConfig(TreeConfig treeConfig) {
        this.treeConfig = treeConfig;
        return this;
    }

    @Override
    public TreeDefinitionBuilder buildPostProcessor(TreeBuildPostProcessor... postProcessors) {
        if (postProcessors == null || postProcessors.length == 0) {
            return this;
        }
        this.postProcessors = Arrays.asList(postProcessors);
        return this;
    }

    @Override
    public TreeDefinition build() {
        TreeDefinition treeDefinition;
        //自定义校验
        customValid();
        //构建树定义
        if (this.definitionType == BaseTreeDefinition.class) {
            treeDefinition = new BaseTreeDefinition(root, treeNodes);
        } else {
            treeDefinition = MyReflectUtils.createObject(this.definitionType);
        }
        if (treeDefinition == null) {
            throw new TreeInitException("Tree init fail...");
        }
        //树配置
        treeDefinition.setTreeConfig(treeConfig);
        //后置处理器
        treeDefinition.setPostProcessors(this.postProcessors);
        return treeDefinition;
    }

    private void validNodeId(TreeNode node) {
        Assert.notNull(node, "Each node of tree should not be null");
        Long nodeId = node.getNodeId();
        if (nodeId == null) {
            throw new IllegalArgumentException("Each node's `nodeId` should not be null");
        }
        if (this.nodeIdValidator.contains(nodeId)) {
            String nodeMsg = "nodeId:" + node.getNodeId() + " / nodeName:" + node.getNodeName();
            throw new IllegalArgumentException("The node[" + nodeMsg + "] is duplicated");
        }
        Long parentId = node.getParentId();
        //校验是否有父节点id及是否存在重复根节点
        if (parentId == null && root == null) {
            this.root = node;
        } else if (parentId == null) {
            throw new IllegalArgumentException("Each node's `parentId` should not be null, except for the root");
        }
        this.nodeIdValidator.add(nodeId);
    }

    private void customValid() {
        if (this.treeNodeValidators == null) {
            return;
        }
        this.treeNodes.forEach(node -> {
            this.treeNodeValidators.forEach(validator -> {
                String msg = validator.valid(node);
                if (StringUtils.isNotBlank(msg)) {
                    throw new IllegalArgumentException("Node[" + node.getNodeId() + "] custom validate error => " + msg);
                }
            });
        });
    }


}
