package com.liu.nyxs.utils.tree.base;

import com.liu.nyxs.utils.tree.core.*;
import com.liu.nyxs.utils.tree.exception.TreeException;
import com.liu.nyxs.utils.tree.exception.TreeInitException;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：haoshen
 * @date ：2023-12-07
 * @description : 默认树定义
 */
public class BaseTreeDefinition implements TreeDefinition {
    private final List<TreeNode> treeNodes;
    private final Map<Long, TreeNode> treeNodeMap;
    private final TreeNode root;
    private TreeConfig treeConfig;
    private List<TreeBuildPostProcessor> postProcessors;
    private int size;
    private int level;

    public BaseTreeDefinition(TreeNode root, List<TreeNode> nodes) {
        this.treeNodes = nodes;
        this.root = root;
        this.size = treeNodes.size();
        this.treeNodeMap = new HashMap<>();
        buildTree();
    }

    private void buildTree() {
        //加载进缓存
        loadNodeCache();
        //生成树
        generateTree();
        //树初始化
        initTree();
    }

    private void loadNodeCache() {
        this.treeNodes.forEach(node -> {
            List<? extends TreeNode> children = node.getChildren();
            if (children != null) {
                children.clear();
            }
            this.treeNodeMap.put(node.getNodeId(), node);
        });
    }

    private void generateTree() {
        for (TreeNode treeNode : this.treeNodes) {
            if (treeNode == root) {
                continue;
            }
            Long parentId = treeNode.getParentId();
            TreeNode parent = treeNodeMap.getOrDefault(parentId, null);
            Assert.notNull(parent, "node[" + treeNode.getNodeId() + "] 's parent not found, please check");
            parent.addChildren(treeNode);
        }
    }

    private void initTree() {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode curNode = queue.poll();
                Assert.notNull(curNode, "Any node of tree should not be null");
                //校验是否循环引用
                validCircularRefer(curNode);
                //设置节点层级
                curNode.setLevel(level);
                //遍历子节点
                List<? extends TreeNode> children = curNode.getChildren();
                if (children != null) {
                    queue.addAll(children);
                }
                size--;
            }
            level++;
        }
        this.level = level;
    }

    @Override
    public List<TreeNode> treeNodeList() {
        return this.treeNodes;
    }

    @Override
    public Map<Long, TreeNode> treeNodeMap() {
        return treeNodeMap;
    }

    @Override
    public TreeNode treeRoot() {
        return root;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int level() {
        return this.level;
    }

    @Override
    public void setTreeConfig(TreeConfig treeConfig) {
        this.treeConfig = treeConfig;
    }

    @Override
    public synchronized void setPostProcessors(List<TreeBuildPostProcessor> postProcessors) {
        //Only load once
        if (this.postProcessors != null) {
            throw new TreeInitException("Tree post processors only load once");
        }
        if (postProcessors == null || postProcessors.size() == 0) {
            return;
        }
        this.postProcessors = postProcessors;
        //后置处理
        if (this.treeConfig != null) {
            postProcessors.forEach(processor -> processor.process(this, this.treeConfig));
        }
    }

    @Override
    public boolean addNode(TreeNode treeNode) {
        Assert.notNull(treeNode, "insert node should not be null");
        Assert.notNull(treeNode.getParentId(), "node's parentId should not be null");
        TreeNode parentNode = this.treeNodeMap.get(treeNode.getParentId());
        if (parentNode == null) {
            return false;
        }
        //校验新增节点
        validAddNode(treeNode);
        //拷贝节点
        TreeNode copyNode = treeNode.clone();
        if (copyNode == null) {
            throw new TreeException("Insert error, please make sure the method of clone has been overrided");
        }
        copyNode.setChildren(null);
        //设置层级
        copyNode.setLevel(parentNode.getLevel() + 1);
        //增加节点
        this.treeNodes.add(copyNode);
        this.treeNodeMap.put(copyNode.getNodeId(), copyNode);
        // 刷新大小
        this.size++;
        // 重新计算树的层级
        rebuildLevel();
        return true;
    }

    /**
     * 更新树的level
     */
    private void rebuildLevel() {
        if (root == null) {
            this.level = 0;
            return;
        }
        int level = 0;
        TreeIterator iterator = new GenericTreeIterator(root);
        while (iterator.hasNextLevel()) {
            iterator.nextLevel();
            level ++;
        }
        this.level = level;
    }

    @Override
    public int addNodeCascade(TreeNode treeNode) {
        return 0;
    }


    @Override
    public boolean updateNode(TreeNode updateNode) {
        Assert.notNull(updateNode, "UpdateNode should not be null");
        Assert.notNull(updateNode.getNodeId(), "UpdateNode's id should not be null");
        Long nodeId = updateNode.getNodeId();
        TreeNode targetNode = this.treeNodeMap.getOrDefault(nodeId, null);
        if (targetNode == null) {
            return false;
        }
        TreeNode copyNode = updateNode.clone();
        if (copyNode == null) {
            throw new TreeException("Update error, please make sure the method of clone has been override");
        }
        copyNode.setParentId(targetNode.getParentId());
        copyNode.setChildren(targetNode.getChildren());
        //更新
        this.replaceListNode(targetNode, copyNode);
        this.treeNodeMap.put(nodeId, copyNode);
        return true;
    }

    @Override
    public int updateNodeCascade(TreeNode updateNode) {
        Assert.notNull(updateNode, "Update node should not be null");
        Long nodeId = updateNode.getNodeId();
        TreeNode targetNode = this.treeNodeMap.getOrDefault(nodeId, null);
        if (targetNode == null) {
            return 0;
        }
        if (targetNode == root) {
            updateRoot(targetNode, updateNode);
        }
        TreeNode newParentNode = this.treeNodeMap.get(updateNode.getParentId());
        Assert.notNull(newParentNode, "The parent of update node is not exist");
        TreeNode originParentNode = this.treeNodeMap.get(targetNode.getParentId());
        //修改了上级节点
        if (newParentNode != originParentNode) {
            originParentNode.getChildren().remove(targetNode);
            newParentNode.addChildren(updateNode);
        }
        //设置层级
        updateNode.setLevel(newParentNode.getLevel() + 1);
        //不修改子节点
        updateNode.setChildren(targetNode.getChildren());
        this.treeNodes.add(updateNode);
        this.treeNodes.remove(targetNode);
        this.treeNodeMap.put(nodeId, updateNode);
        return -1;
    }

    //TODO 待完善
    private void updateRoot(TreeNode originRoot, TreeNode updateRoot) {
        //检查父节点是否为空
        Assert.isNull(updateRoot.getParentId(), "Root's parent id should be null, update fail");

    }

    @Override
    public int removeNodes(Long[] nodeIds) {
        if (TreeTools.isArrayEmpty(nodeIds)) {
            return 0;
        }
        Set<Long> removeNodeIds = new HashSet<>();
        for (Long nodeId : nodeIds) {
            TreeNode node = this.treeNodeMap.getOrDefault(nodeId, null);
            if (node == null) {
                continue;
            }
            if (this.root == node) {
                throw new TreeException("The removeNodes should not contain the root");
            }
            if (removeNodeIds.contains(nodeId)) {
                continue;
            }
            //添加自己
            removeNodeIds.add(nodeId);
            //添加子节点
            List<? extends TreeNode> descendant = TreeTools.getDescendant(node);
            descendant.forEach(i -> removeNodeIds.add(i.getNodeId()));
        }
        //批量删除
        this.remove(removeNodeIds);
        return removeNodeIds.size();
    }

    private void remove(Collection<Long> removeNodeIds) {
        if (removeNodeIds == null || removeNodeIds.size() == 0) {
            return;
        }
        int size = 0;
        for (Long removeNodeId : removeNodeIds) {
            TreeNode node = this.treeNodeMap.getOrDefault(removeNodeId, null);
            if (node != null) {
                this.treeNodes.remove(node);
                this.treeNodeMap.remove(removeNodeId);
                size--;
            }
        }
        this.size = size;
        //重新计算层级
        this.rebuildLevel();
    }

    private TreeNode getNode(Long nodeId) {
        if (nodeId == null) {
            return null;
        }
        return this.treeNodeMap.get(nodeId);
    }

    private void validAddNode(TreeNode addNode) {
        if (this.treeNodeMap.containsKey(addNode.getNodeId())) {
            throw new TreeException("node's id conflict");
        }
        //校验是否循环引用
        validCircularRefer(addNode);
    }


    //校验是否循环引用，即子节点的父节点是自己
    private void validCircularRefer(TreeNode curNode) {
        if (root == curNode) {
            return;
        }
        Long parentId = curNode.getParentId();
        TreeNode parentNode = this.treeNodeMap.get(parentId);
        while (parentNode != null) {
            if (parentNode == curNode) {
                throw new RuntimeException("Tree node[" + curNode.getNodeId() + "] has parent[" + parentId + "] which" +
                        " was itself, circular references error");
            }
            parentNode = this.treeNodeMap.get(parentNode.getParentId());
        }
    }

    private void replaceListNode(TreeNode originNode, TreeNode replaceNode) {
        if (originNode == null || replaceNode == null) {
            return;
        }
        this.treeNodes.remove(originNode);
        this.treeNodes.add(replaceNode);
    }

    @Override
    public TreeDefinition copy() {
        TreeNode copyRoot = getCopyRoot();
        List<TreeNode> copyNodes = getCopyNodes();
        copyNodes.add(copyRoot);
        return new BaseTreeDefinition(copyRoot, copyNodes);
    }

    private TreeNode getCopyRoot() {
        TreeNode copyRoot = this.root.clone();
        copyRoot.setChildren(null);
        return copyRoot;
    }

    private List<TreeNode> getCopyNodes() {
        List<Long> copyNodeIds = this.treeNodes.stream().map(TreeNode::getNodeId).collect(Collectors.toList());
        List<TreeNode> copyNodes = new ArrayList<>(copyNodeIds.size());
        for (Long copyNodeId : copyNodeIds) {
            TreeNode originNode = this.treeNodeMap.getOrDefault(copyNodeId, null);
            if (originNode == null || originNode.getNodeId().equals(root.getNodeId())) {
                continue;
            }
            TreeNode copyNode = originNode.clone();
            copyNode.setChildren(null);
            copyNodes.add(copyNode);
        }
        return copyNodes;
    }
}
