package com.liu.nyxs.utils.tree.example;

import com.liu.nyxs.utils.tree.annotation.Tree;
import com.liu.nyxs.utils.tree.base.BaseTreeNode;
import com.liu.nyxs.utils.tree.utils.TreeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ：haoshen
 * @date ：2023-12-18
 * @description 模拟设备树
 */
public class MeterTree {

    public static void main(String[] args) {
        BaseTreeNode<BaseMeterDO> meterTree = buildMeterTree();
        System.out.println();
    }

    public static BaseTreeNode<BaseMeterDO> buildMeterTree() {
        return TreeUtils.buildBaseTree(mockMeters()).getTree();
    }

    public static List<BaseMeterDO> mockMeters() {
        BaseMeterDO meter0 = new BaseMeterDO(1L, null, "meter0");
        BaseMeterDO meter1 = new BaseMeterDO(100L, 1L, "meter1");
        BaseMeterDO meter2 = new BaseMeterDO(101L, 1L, "meter2");
        return Stream.of(meter0, meter1, meter2).collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    @Accessors(chain = true)
    @Tree(nodeId = "nodeId", parentId = "areaId", nodeName = "meterName")
    public static class BaseMeterDO {
        private Long nodeId;
        private Long areaId;
        private String meterName;
    }
}
