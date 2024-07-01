package com.liu.nyxs.utils.tree.base;

import com.liu.nyxs.utils.tree.constantts.Constants;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * @author ：haoshen
 * @date ：2023-12-17
 * @description 混合树节点
 */
@Getter
public class MixedTreeNode extends BaseTreeNode<Object> {
    //节点类型
    private final Class<?> type;
    //上级节点标识
    private String parentKey = "";

    public MixedTreeNode(Long nodeId, Long parentId, String nodeName, Object data) {
        super(nodeId, parentId, nodeName, data);
        this.type = Objects.isNull(data) ? Constants.NONE.getClass() : data.getClass();
    }

    @Override
    public List<MixedTreeNode> getChildren() {
        return (List<MixedTreeNode>) super.getChildren();
    }

    protected void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

}
