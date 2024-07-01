package com.liu.nyxs.utils.tree.core;

import com.liu.nyxs.utils.tree.base.BaseTree;
import com.liu.nyxs.utils.tree.base.BaseTreeNode;
import com.liu.nyxs.utils.tree.exception.TreeException;
import com.liu.nyxs.utils.tree.utils.TreeUtils;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author ：haoshen
 * @date ：2023-12-15
 * @description : 树核心工具类，仅内部调用，如需要调用树相关工具方法，请看{@link TreeUtils}
 */
public abstract class TreeTools {

    /**
     * 将树转成数组
     *
     * @param tree 树
     * @return 按照深度优先遍历顺序组成数组树
     */
    public static List<TreeNode> toArrayTree(TreeNode tree) {
        List<TreeNode> arrayTree = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(tree);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode curNode = queue.poll();
                assert curNode != null;
                arrayTree.add(curNode);
                List<? extends TreeNode> children = curNode.getChildren();
                if (!CollectionUtils.isEmpty(children)) {
                    queue.addAll(children);
                }
                size--;
            }
        }
        return arrayTree;
    }

    /**
     * 根据节点获取所有下级节点（按照层级关系）
     * 不包含自己！
     */
    public static List<? extends TreeNode> getDescendant(TreeNode treeNode) {
        Assert.notNull(treeNode, "treeNode should not be null");
        List<TreeNode> arrayTree = toArrayTree(treeNode);
        arrayTree.remove(0);
        return arrayTree;
    }

    /**
     * 根据节点获取所有父节点（按照层级关系）
     * 不包含自己！
     */
    public static List<? extends TreeNode> getAncestor(Tree tree, Long nodeId) {
        if (tree == null || nodeId == null) {
            throw new TreeException("tree and the nodeId should not be null");
        }
        TreeNode treeNode = tree.getTreeNodeById(nodeId);
        if (treeNode == null) {
            return new ArrayList<>(0);
        }
        List<TreeNode> ancestors = new ArrayList<>();
        TreeNode parentNode = tree.getTreeNodeById(treeNode.getParentId());
        while (parentNode != null && parentNode != treeNode) {
            ancestors.add(parentNode);
            parentNode = tree.getTreeNodeById(parentNode.getParentId());
        }
        return ancestors;
    }

    /**
     * 判断是否子节点，同样的节点返回false
     *
     * @param childId  要判断的子节点id
     * @param parentId 要判断的父节点id
     */
    public static boolean isDescendant(Tree tree, Long childId, Long parentId) {
        if (childId == null || parentId == null || tree == null) {
            throw new TreeException("tree, childId and parentId should not be null");
        }
        if (childId.equals(parentId)) {
            return false;
        }
        List<? extends TreeNode> ancestors = getAncestor(tree, childId);
        return ancestors.stream().map(TreeNode::getNodeId).findFirst().isPresent();
    }

    public static boolean isAncestor(Tree tree, Long parentId, Long childId) {
        return isDescendant(tree, childId, parentId);
    }

    public static <T> boolean isArrayEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * JSON树反序列化为树对象
     * @param <T> BaseTree的泛型信息
     */
    public static <T> BaseTree<T> parseBaseJsonTree(JSONObject treeJSON, Class<T> dataType) {
        Assert.notNull(treeJSON, "treeJSON should not be null");

        List<BaseTreeNode<T>> baseTreeNodes = new ArrayList<>();
        Queue<JSONObject> queue = new LinkedList<>();
        queue.add(treeJSON);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                JSONObject node = queue.poll();
                if (node == null) {
                    throw new TreeException("TreeJSON parse error, each node should not be null");
                }
                //节点必要信息
                Long nodeId = node.getLong("nodeId");
                Long parentId = node.getLong("parentId");
                String nodeName = node.getString("nodeName");
                //节点泛型信息
                T dataObject = null;
                JSONObject data = node.getJSONObject("data");
                if (data != null && dataType != null) {
                    String dataString = data.toJSONString();
                    dataObject = JSONObject.parseObject(dataString, dataType);
                }
                BaseTreeNode<T> treeNode = new BaseTreeNode<>(nodeId, parentId, nodeName, dataObject);
                //节点其余信息
                String sort = node.getString("sort");
                String key = node.getString("key");
                treeNode.setSort(sort);
                treeNode.setKey(key);

                baseTreeNodes.add(treeNode);
                //children
                JSONArray children = node.getJSONArray("children");
                if (children != null && children.size() > 0) {
                    for (int i = 0; i < children.size(); i++) {
                        JSONObject jsonObject = children.getJSONObject(i);
                        queue.add(jsonObject);
                    }
                }
                size--;
            }
        }
        return new BaseTree<T>(baseTreeNodes);
    }
}
