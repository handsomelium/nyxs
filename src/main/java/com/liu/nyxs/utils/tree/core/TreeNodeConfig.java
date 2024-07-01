package com.liu.nyxs.utils.tree.core;

import lombok.Data;

/**
 * @author : haoshen
 * @date :2023-12-15
 * @description :树节点配置，该类用来封装{@link com.liu.nyxs.utils.tree.annotation.Tree}注解中的配置信息
 */
@Data
public class TreeNodeConfig {
    //节点id
    private String nodeId;
    //上级节点id
    private String parentId;
    //节点名字
    private String nodeName;
    //自定义标识
    private String key;
    //上级节点key（混合节点使用）
    private String parentKey;
    //排序
    private String sort;
    //是否自动生成节点id（虚拟节点）
    private boolean autoGenerateNodeId;
}
