package com.liu.nyxs.utils.tree.processor;

import com.liu.nyxs.utils.tree.core.TreeBuildPostProcessor;
import com.liu.nyxs.utils.tree.core.TreeConfig;
import com.liu.nyxs.utils.tree.core.TreeDefinition;
import com.liu.nyxs.utils.tree.core.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：haoshen
 * @date ：2023-12-22
 * @description : 层级限制处理器
 */
public class LevelLimitProcessor implements TreeBuildPostProcessor {
    @Override
    public void process(TreeDefinition treeDefinition, TreeConfig config) {
        if (config.getDeep() == -1) {
            return;
        }
        List<Long> deleteNodeIds = new ArrayList<>();
        for (TreeNode treeNode : treeDefinition.treeNodeList()) {
            if (treeNode.getLevel() >= config.getDeep()) {
                deleteNodeIds.add(treeNode.getNodeId());
            }
        }
        //删除多余节点
        treeDefinition.removeNodes(deleteNodeIds.toArray(new Long[0]));
    }
}
