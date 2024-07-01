package com.liu.nyxs.utils.tree.example;

import com.liu.nyxs.utils.tree.annotation.Tree;
import com.liu.nyxs.utils.tree.base.BaseTreeIterator;
import com.liu.nyxs.utils.tree.base.BaseTreeNode;
import com.liu.nyxs.utils.tree.utils.TreeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ：haoshen
 * @date ：2023-12-18
 * @description 模拟区域树
 */
public class AreaTree {

    public static void main(String[] args) {
        BaseTreeNode<BaseAreaDO> tree = buildAreaTree();
        System.out.println();
        BaseTreeIterator<BaseAreaDO> iterator = new BaseTreeIterator<>(tree);
        while (iterator.hasNextLevel()) {
            List<BaseTreeNode<BaseAreaDO>> baseTreeNodes = iterator.nextLevel();
            System.out.println("----");
            baseTreeNodes.forEach(item -> System.out.println(item.getNodeName()));
        }
    }

    public static BaseTreeNode<BaseAreaDO> buildAreaTree() {
        return TreeUtils.buildBaseTree(mockAreas()).getTree();
    }

    public static List<BaseAreaDO> mockAreas() {
        BaseAreaDO root = new BaseAreaDO(1L, null, "root");
        BaseAreaDO node1 = new BaseAreaDO(2L, 1L, "root-node1");
        BaseAreaDO node2 = new BaseAreaDO(3L, 1L, "root-node2");
        BaseAreaDO node11 = new BaseAreaDO(4L, 2L, "root-node1-node1");
        BaseAreaDO node12 = new BaseAreaDO(5L, 2L, "root-node1-node2");
        return Stream.of(root, node1, node2, node11, node12).collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    @Tree(nodeId = "areaId", parentId = "areaPid", nodeName = "areaName")
    public static class BaseAreaDO {
        private Long areaId;
        private Long areaPid;
        private String areaName;
    }
}
