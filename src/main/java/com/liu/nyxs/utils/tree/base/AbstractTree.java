package com.liu.nyxs.utils.tree.base;

import com.liu.nyxs.utils.tree.core.*;
import com.liu.nyxs.utils.tree.exception.MergeTreeException;
import com.liu.nyxs.utils.tree.exception.TreeException;
import com.liu.nyxs.utils.tree.processor.LevelLimitProcessor;
import com.liu.nyxs.utils.tree.processor.PathBuildProcessor;
import com.liu.nyxs.utils.tree.processor.SortProcessor;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.liu.nyxs.utils.tree.core.TreeTools.toArrayTree;

/**
 * @author ：haoshen
 * @date ：2023-12-07
 * @description : 抽象树
 */
public abstract class AbstractTree implements Tree {
    private final TreeDefinition treeDefinition;
    private final TreeNode root;

    public AbstractTree(TreeDefinition treeDefinition) {
        this.treeDefinition = treeDefinition;
        this.root = this.treeDefinition.treeRoot();
    }

    public AbstractTree(List<? extends TreeNode> treeNodes) {
        TreeDefinitionBuilder builder = new BaseTreeDefinitionBuilder();
        //添加后处理支持
        builder.buildPostProcessor(new PathBuildProcessor());
        builder.buildPostProcessor(new SortProcessor());
        builder.buildPostProcessor(new LevelLimitProcessor());
        builder.buildNodes(treeNodes);
        this.treeDefinition = builder.build();
        this.root = this.treeDefinition.treeRoot();
    }

    @Override
    public TreeNode getTree() {
        return this.root;
    }

    @Override
    public TreeNode getTreeNodeById(Long nodeId) {
        return treeDefinition.treeNodeMap().getOrDefault(nodeId, null);
    }

    @Override
    public TreeNode getParentNodeById(Long nodeId) {
        if (Objects.equals(this.root.getNodeId(), nodeId)) {
            return null;
        }
        TreeNode node = this.getTreeNodeById(nodeId);
        if (node == null) {
            throw new TreeException("nodeId[" + nodeId + "] not exist");
        }
        return this.getTreeNodeById(node.getParentId());
    }

    @Override
    public int size() {
        return this.treeDefinition.size();
    }

    @Override
    public boolean isEmpty() {
        return this.treeDefinition.size() == 0;
    }

    @Override
    public boolean contains(Long nodeId) {
        return this.treeDefinition.treeNodeMap().containsKey(nodeId);
    }

    @Override
    public List<? extends TreeNode> getTreeNodeList() {
        return toArrayTree(getTree());
    }

    @Override
    public List<? extends TreeNode> getDescendent(Long nodeId, boolean containOwn) {
        TreeNode node = this.getTreeNodeById(nodeId);
        if (node == null) {
            throw new TreeException("node[nodeId:" + nodeId + "] not exist");
        }
        List<? extends TreeNode> descendant = TreeTools.getDescendant(node);
        List<TreeNode> result = new ArrayList<>(descendant);
        if (containOwn) {
            result.add(node);
        }
        return result;
    }

    @Override
    public List<? extends TreeNode> getAncestor(Long nodeId, boolean containOwn) {
        List<? extends TreeNode> ancestor = TreeTools.getAncestor(this, nodeId);
        List<TreeNode> result = new ArrayList<>(ancestor);
        if (containOwn) {
            TreeNode node = this.getTreeNodeById(nodeId);
            result.add(node);
        }
        return result;
    }

    @Override
    public void traverse(TreeVisitor treeVisitor) {
        if (treeVisitor == null) {
            return;
        }
        TreeIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            TreeNode node = iterator.next();
            treeVisitor.consume(node);
        }
    }

    @Override
    public TreeIterator iterator() {
        return new GenericTreeIterator(getTree());
    }

    @Override
    public List<? extends TreeNode> collect(TreeCollector treeCollector) {
        if (treeCollector == null) {
            return toArrayTree(getTree());
        }
        List<TreeNode> targetNodes = new ArrayList<>();
        TreeIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            TreeNode node = iterator.next();
            if (treeCollector.isCollect(node)) {
                targetNodes.add(node);
            }
        }
        return targetNodes;
    }

    @Override
    public boolean addNode(TreeNode node) {
        return this.treeDefinition.addNode(node);
    }

    @Override
    public int addNodeCascade(TreeNode node) {
        return 0;
    }

    @Override
    public boolean updateNode(TreeNode node) {
        return this.treeDefinition.updateNode(node);
    }

    @Override
    public int updateNodeCascade(TreeNode node) {
        return this.treeDefinition.updateNodeCascade(node);
    }

    @Override
    public int removeNode(Long nodeId) {
        return this.treeDefinition.removeNodes(new Long[]{nodeId});
    }

    @Override
    public int removeNode(Long nodeId, Predicate<TreeNode> nodeDelPredictor) {
        if (nodeId == null) {
            return 0;
        }
        if (nodeDelPredictor == null) {
            return removeNode(nodeId);
        }
        boolean isDel = true;
        TreeIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            TreeNode node = iterator.next();
            isDel = nodeDelPredictor.test(node);
            //一个不通过，则无法删除
            if (!isDel) {
                break;
            }
        }
        return isDel ? removeNode(nodeId) : 0;
    }

    @Override
    public Tree mergeTree(Tree tree) throws MergeTreeException {
        //TODO...
        return null;
    }

    @Override
    public Tree filter(TreeFilter treeFilter) {
        if (treeFilter == null) {
            return this;
        }
        TreeNode filterTreeRoot = doFilter(getTree(), treeFilter);
        if (filterTreeRoot == null) {
            return null;
        }
        List<TreeNode> treeNodes = toArrayTree(filterTreeRoot);
        TreeBuilder treeBuilder = new BaseTreeBuilder();
        return treeBuilder.buildTree(getTreeConfig(treeNodes));
    }

    @Override
    public Tree filterAnd(TreeFilter... filters) {
        if (TreeTools.isArrayEmpty(filters)) {
            return this;
        }
        return this.filter(treeNode -> {
            boolean flag = true;
            for (TreeFilter filter : filters) {
                if (!filter.contains(treeNode)) {
                    flag = false;
                    break;
                }
            }
            return flag;
        });
    }

    protected abstract TreeConfig getTreeConfig(List<TreeNode> treeNodes);

    private TreeNode doFilter(TreeNode treeNode, TreeFilter treeFilter) {
        if (treeNode == null) {
            return null;
        }
        TreeNode copyNode = null;
        List<? extends TreeNode> children = treeNode.getChildren();
        if (treeFilter.contains(treeNode)) {
            if (CollectionUtils.isEmpty(children)) {
                return treeNode.copy();
            } else {
                copyNode = treeNode.copy();
            }
        }
        if (children != null) {
            //防止并发异常
            List<TreeNode> filteredChildren = new ArrayList<>(treeNode.getChildren().size());
            for (TreeNode child : children) {
                //符合条件的节点
                TreeNode filteredChild = doFilter(child, treeFilter);
                if (filteredChild != null) {
                    filteredChildren.add(filteredChild);
                }
            }
            //子节点存在
            if (filteredChildren.size() > 0) {
                TreeNode filteredNode = treeNode.copy();
                filteredNode.setChildren(filteredChildren);
                return filteredNode;
            }
            //子节点不存在但自己本身是
            return copyNode;
        }
        return null;
    }


    @Override
    public <T extends TreeNode, R> Tree filterBy(Function<T, R> fieldExtractor, R targetValue) {
        Assert.notNull(fieldExtractor, "fieldExtractor should not be null");
        return this.filter(treeNode -> {
            R matchValue = fieldExtractor.apply((T) treeNode);
            if (matchValue instanceof String) {
                return ((String) matchValue).contains((CharSequence) targetValue);
            }
            return Objects.equals(matchValue, targetValue);
        });
    }

    @Override
    public Tree filterByName(String nodeName) {
        return this.filter(treeNode -> treeNode.getNodeName().contains(nodeName));
    }

    @Override
    public TreeNode getEmptyTree() {
        return this.root.copy();
    }
}
