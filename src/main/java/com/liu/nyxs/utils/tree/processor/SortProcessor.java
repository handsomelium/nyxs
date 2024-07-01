package com.liu.nyxs.utils.tree.processor;

import com.liu.nyxs.utils.tree.base.GenericTreeIterator;
import com.liu.nyxs.utils.tree.core.*;

import java.util.Comparator;
import java.util.List;

/**
 * @author ：haoshen
 * @date ：2023-12-26
 * @description : 排序处理器
 */
public class SortProcessor implements TreeBuildPostProcessor {
    @Override
    public void process(TreeDefinition treeDefinition, TreeConfig treeConfig) {
        if (!treeConfig.enableSort()) {
            return;
        }
        TreeIterator iterator = new GenericTreeIterator(treeDefinition.treeRoot());
        while (iterator.hasNext()) {
            TreeNode node = iterator.next();
            List<? extends TreeNode> children = node.getChildren();
            if (children != null && children.size() > 0) {
                children.sort(Comparator.comparing(TreeNode::getSort));
            }
        }
    }
}
