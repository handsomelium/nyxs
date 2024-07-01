package com.liu.nyxs.utils.tree.example;

import com.liu.nyxs.utils.tree.base.BaseTreeBuilder;
import com.liu.nyxs.utils.tree.base.BaseTreeConfig;
import com.liu.nyxs.utils.tree.base.BaseTreeNode;
import com.liu.nyxs.utils.tree.base.BaseTreeNodeBuilder;
import com.liu.nyxs.utils.tree.core.Tree;
import com.liu.nyxs.utils.tree.core.TreeNode;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.liu.nyxs.utils.tree.example.AreaTree.BaseAreaDO;

/**
 * @author ：haoshen
 * @date ：2024-02-29
 * @description : 示例
 */
public class Sample {

    public static void main(String[] args) {
        List<BaseAreaDO> baseAreaDOS = generateArea();
        BaseTreeNodeBuilder baseTreeNodeBuilder = new BaseTreeNodeBuilder();
        List<BaseTreeNode<BaseAreaDO>> baseTreeNodes = baseTreeNodeBuilder.buildTreeNodes(baseAreaDOS);
        BaseTreeBuilder baseTreeBuilder = new BaseTreeBuilder();
        Tree tree = baseTreeBuilder.buildTree(BaseTreeConfig.from(baseTreeNodes));

        Tree filterTree = tree.filterBy(TreeNode::getNodeId, 3L);
        System.out.println(filterTree);
    }

    public static List<BaseAreaDO> generateArea() {
        BaseAreaDO area1 = new BaseAreaDO(1L, null, "area1");
        BaseAreaDO area2 = new BaseAreaDO(2L, 1L, "area2");
        BaseAreaDO area3 = new BaseAreaDO(3L, 2L, "area3");
        BaseAreaDO area4 = new BaseAreaDO(4L, 3L, "area4");
        return Stream.of(area1, area2, area3, area4).collect(Collectors.toList());
    }
}
