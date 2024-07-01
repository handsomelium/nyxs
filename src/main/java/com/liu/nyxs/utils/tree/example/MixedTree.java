package com.liu.nyxs.utils.tree.example;

import com.liu.nyxs.utils.tree.base.MixedTreeNode;
import com.liu.nyxs.utils.tree.utils.TreeUtils;

import java.util.List;

/**
 * @author ：haoshen
 * @date ：2023-12-18
 * @description 模拟混合树
 */
public class MixedTree {

    public static void main(String[] args) {
        MixedTreeNode tree = buildMixedTree();
        System.out.println();
    }

    public static MixedTreeNode buildMixedTree() {
        return TreeUtils.buildMixedTree(mockData()).getTree();
    }

    public static List<?>[] mockData() {
        List<AreaTree.BaseAreaDO> areas = AreaTree.mockAreas();
        List<MeterTree.BaseMeterDO> meters = MeterTree.mockMeters();
        //更改设备上级节点
        meters.get(0).setNodeId(100L).setAreaId(2L);
        meters.get(1).setNodeId(101L).setAreaId(3L);
        meters.get(2).setNodeId(102L).setAreaId(4L);
        return new List[]{areas, meters};
    }
}
