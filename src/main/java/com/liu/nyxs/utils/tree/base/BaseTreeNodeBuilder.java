package com.liu.nyxs.utils.tree.base;

import com.liu.nyxs.utils.tree.annotation.Tree;
import com.liu.nyxs.utils.tree.core.TreeNodeBuilder;
import com.liu.nyxs.utils.tree.core.TreeNodeConfig;
import com.liu.nyxs.utils.tree.exception.TreeException;
import com.liu.nyxs.utils.tree.exception.TreeInitException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : haoshen
 * @date :2023-12-15
 * @description :基本树节点构造器
 */
public class BaseTreeNodeBuilder implements TreeNodeBuilder {
    private TreeNodeConfig config;

    @Override
    public <T> List<BaseTreeNode<T>> buildTreeNodes(List<T> nodeSource) {
        if (CollectionUtils.isEmpty(nodeSource)) {
            throw new TreeException("The nodeSource should not be empty");
        }
        parseTreeNode(nodeSource);
        return generateTreeNodes(nodeSource);
    }

    private <T> List<BaseTreeNode<T>> generateTreeNodes(List<T> nodeSource) {
        List<BaseTreeNode<T>> treeNodes = new ArrayList<>(nodeSource.size());
        for (T t : nodeSource) {
            Long nodeId = getNodeId(config.getNodeId(), t);
            Long parentId = getNodeId(config.getParentId(), t);
            String nodeName = getNodeName(config.getNodeName(), t);
            String key = config.getKey();
            String sort = getSort(config.getSort(), t);
            BaseTreeNode<T> treeNode = new BaseTreeNode<>(nodeId, parentId, nodeName, t);
            treeNode.setKey(key);
            treeNode.setSort(sort);
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }

    private <T> void parseTreeNode(List<T> nodeSource) {
        //得到泛型类型
        Class<?> clazz = getNodeType(nodeSource);
        if (clazz == null) {
            throw new TreeInitException("A generic type should be specified for the node source");
        }
        Tree annotation = clazz.getAnnotation(Tree.class);
        if (annotation == null) {
            throw new TreeInitException("The type of node source should be annotated with Tree");
        }
        config = buildConfig(annotation);
    }

}
