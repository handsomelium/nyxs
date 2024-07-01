package com.liu.nyxs.utils.tree.base;


import com.liu.nyxs.utils.tree.core.TreeConfig;
import com.liu.nyxs.utils.tree.core.TreeDefinition;
import com.liu.nyxs.utils.tree.core.TreeNode;

import java.util.List;

/**
 * @author ：haoshen
 * @date ：2023-12-07
 * @description : 通用树，基类
 */
public class GenericTree extends AbstractTree {

    //for inner use now
    public GenericTree(TreeDefinition treeDefinition) {
        super(treeDefinition);
    }

    public GenericTree(List<? extends TreeNode> metaNodes) {
        super(metaNodes);
    }

    @Override
    protected TreeConfig getTreeConfig(List<TreeNode> treeNodes) {
        return BaseTreeConfig.builder()
                .treeType(GenericTree.class)
                .sourceTreeNodes(treeNodes)
                .build();
    }

}
