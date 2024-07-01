package com.liu.nyxs.utils.tree.base;

import cn.hutool.core.lang.Assert;
import com.liu.nyxs.utils.tree.annotation.Tree;
import com.liu.nyxs.utils.tree.core.TreeNode;
import com.liu.nyxs.utils.tree.core.TreeNodeBuilder;
import com.liu.nyxs.utils.tree.core.TreeNodeConfig;
import com.liu.nyxs.utils.tree.exception.TreeInitException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ：haoshen
 * @date ：2023-12-18
 * @description : 混合树节点建造者
 */
public class MixedTreeNodeBuilder implements TreeNodeBuilder {
    private final AtomicLong curMaxNodeId = new AtomicLong(0L);
    private final Map<String, MixedTreeNode> treeNodeRegistry = new HashMap<>();
    private final Map<Type, TreeNodeConfig> configMap = new LinkedHashMap<>();
    private final Set<String> keySet = new HashSet<>();
    private Map<String, String> parentKeys;
    private int size;

    @Override
    public <T> List<? extends TreeNode> buildTreeNodes(List<T> nodeSource) {
        throw new TreeInitException("Not Support For MixedTreeNodeBuilder");
    }

    public List<MixedTreeNode> buildTreeNodes(List<?>... nodeSources) {
        if (nodeSources == null || nodeSources.length == 0) {
            throw new TreeInitException("The nodeSources should not be empty");
        }
        parseConfig(nodeSources);
        return generateTreeNodes(nodeSources);
    }

    public void setParentKey(String key, String parentKey) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(parentKey)) {
            return;
        }
        if (this.parentKeys == null) {
            this.parentKeys = new HashMap<>();
        }
        this.parentKeys.put(key, parentKey);
    }

    private List<MixedTreeNode> generateTreeNodes(List<?>[] nodeSources) {
        List<MixedTreeNode> treeNodes = new ArrayList<>(size);
        for (List<?> nodeSource : nodeSources) {
            if (CollectionUtils.isEmpty(nodeSource)) {
                continue;
            }
            for (Object node : nodeSource) {
                TreeNodeConfig config = this.configMap.get(node.getClass());
                Long nodeId = generateNodeId(config, node);
                Long parentId = getNodeId(config.getParentId(), node);
                String nodeName = getNodeName(config.getNodeName(), node);
                String key = config.getKey();
                String parentKey = config.getParentKey();
                String sort = getSort(config.getSort(), node);

                MixedTreeNode treeNode = new MixedTreeNode(nodeId, parentId, nodeName, node);
                treeNode.setKey(key);
                treeNode.setSort(sort);
                treeNode.setParentKey(parentKey);

                final String nodeKey = key + "/" + nodeId;
                if (this.treeNodeRegistry.containsKey(nodeKey)) {
                    throw new TreeInitException("MixedTreeNode has a duplicated id node[" + nodeKey + "]");
                }
                treeNodes.add(treeNode);
                this.treeNodeRegistry.put(nodeKey, treeNode);
            }
        }
        //重建nodeId（虚拟节点）
        rebuildNodeId(treeNodes);
        return treeNodes;
    }

    private void rebuildNodeId(List<MixedTreeNode> treeNodes) {
        //记录更改了nodeId的节点
        Map<String, TreeNode> changeNodes = new HashMap<>();

        for (MixedTreeNode treeNode : treeNodes) {
            Class<?> type = treeNode.getType();
            if (type == null) {
                continue;
            }
            TreeNodeConfig config = this.configMap.getOrDefault(treeNode.getType(), null);
            if (config == null || !config.isAutoGenerateNodeId()) {
                continue;
            }
            //记录需要更改nodeId的节点
            changeNodes.put(treeNode.getKey() + "/" + treeNode.getNodeId(), treeNode);
            //更改nodeId
            treeNode.setNodeId(this.curMaxNodeId.incrementAndGet());
        }
        //重新绑定子节点的parentNodeId
        this.rebindParentId(treeNodes, changeNodes);
    }

    private void rebindParentId(List<MixedTreeNode> treeNodes, Map<String, TreeNode> changeNodes) {
        for (MixedTreeNode treeNode : treeNodes) {
            String key = treeNode.getParentKey() + "/" + treeNode.getParentId();
            Optional.ofNullable(changeNodes.getOrDefault(key, null))
                    .ifPresent(node -> treeNode.setParentId(node.getParentId()));
        }
    }

    private Long generateNodeId(TreeNodeConfig config, Object node) {
        Long nodeId = getNodeId(config.getNodeId(), node);
        //记录第一个非空的nodeId，用来生成虚拟节点id用
        if (nodeId != null && nodeId > this.curMaxNodeId.get()) {
            this.curMaxNodeId.set(nodeId);
        }
        return nodeId;
    }


    private void parseConfig(List<?>[] nodeSources) {
        for (List<?> nodeSource : nodeSources) {
            if (CollectionUtils.isEmpty(nodeSource)) {
                continue;
            }
            Class<?> nodeType = getNodeType(nodeSource);
            if (nodeType == null) {
                throw new TreeInitException("A generic type should be specified for each node source");
            }
            if (this.configMap.containsKey(nodeType)) {
                continue;
            }
            Tree annotation = nodeType.getAnnotation(Tree.class);
            if (annotation == null) {
                throw new TreeInitException("The type of node source should be annotated with Tree");
            }
            TreeNodeConfig config = buildConfig(annotation);
            this.configMap.put(nodeType, config);
            this.size += nodeSource.size();
        }
        this.parseKey();
        this.parseParentKey();
    }

    private void parseKey() {
        for (Map.Entry<Type, TreeNodeConfig> entry : this.configMap.entrySet()) {
            TreeNodeConfig nodeConfig = entry.getValue();

            String key = nodeConfig.getKey();
            Assert.notBlank(key, "MixedTreeNode should have a key");
            if (this.keySet.contains(key)) {
                throw new TreeInitException("MixedTreeNode's key should not be duplicated");
            }
            this.keySet.add(key);
        }
    }

    private void parseParentKey() {
        int index = 0;
        for (Map.Entry<Type, TreeNodeConfig> entry : this.configMap.entrySet()) {
            TreeNodeConfig nodeConfig = entry.getValue();
            String parentKey = nodeConfig.getParentKey();
            if (this.parentKeys != null) {
                String key = nodeConfig.getKey();
                parentKey = StringUtils.isBlank(parentKeys.get(key)) ? parentKey : parentKeys.get(key);
            }
            if (index > 0) {
                Assert.notBlank(parentKey, "MixedTreeNode should have a parentKey");
                if (!this.keySet.contains(parentKey)) {
                    throw new TreeInitException("TreeConfig locate the parentKey[" + parentKey + "] fail");
                }
            }
            index++;
        }
    }

}
