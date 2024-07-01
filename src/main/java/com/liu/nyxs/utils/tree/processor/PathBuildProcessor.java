package com.liu.nyxs.utils.tree.processor;

import com.liu.nyxs.utils.tree.core.TreeBuildPostProcessor;
import com.liu.nyxs.utils.tree.core.TreeConfig;
import com.liu.nyxs.utils.tree.core.TreeDefinition;
import com.liu.nyxs.utils.tree.core.TreeNode;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author ：haoshen
 * @date ：2023-12-17
 * @description 构建树路径处理器
 */
public class PathBuildProcessor implements TreeBuildPostProcessor {

    @Override
    public void process(TreeDefinition treeDefinition, TreeConfig config) {
        Map<Long, TreeNode> treeNodeMap = treeDefinition.treeNodeMap();
        treeDefinition.treeNodeList().forEach(node -> buildPath(node, treeNodeMap));
    }

    private void buildPath(TreeNode treeNode, Map<Long, TreeNode> treeNodeMap) {
        StringBuilder pathNameBuilder = new StringBuilder();
        LinkedList<Long> parentIds = new LinkedList<>();
        TreeNode cursor = treeNodeMap.getOrDefault(treeNode.getParentId(), null);
        while (cursor != null) {
            //路径名
            pathNameBuilder.insert(0, "/");
            pathNameBuilder.insert(0, cursor.getNodeName());
            //上级ids
            parentIds.addFirst(cursor.getNodeId());
            cursor = treeNodeMap.getOrDefault(cursor.getParentId(), null);
        }
        pathNameBuilder.append(treeNode.getNodeName());
        treeNode.setPath(pathNameBuilder.toString());
        if (parentIds.size() > 0) {
            treeNode.setParentIds(parentIds);
        }
    }

}
