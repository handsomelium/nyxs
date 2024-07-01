package com.liu.nyxs.utils.tree.core;

import cn.hutool.core.util.ReflectUtil;
import com.liu.nyxs.utils.MyReflectUtils;
import com.liu.nyxs.utils.tree.annotation.Tree;
import com.liu.nyxs.utils.tree.exception.TreeException;
import com.liu.nyxs.utils.tree.exception.TreeInitException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author : haoshen
 * @date :2023-12-15
 * @description :树节点构建器
 */
public interface TreeNodeBuilder {

    <T> List<? extends TreeNode> buildTreeNodes(List<T> nodeSource);

    default Class<?> getNodeType(List<?> nodeSources) {
        if (CollectionUtils.isEmpty(nodeSources)) {
            return null;
        }
        for (Object nodeSource : nodeSources) {
            if (nodeSource != null) {
                return nodeSource.getClass();
            }
        }
        return null;
    }

    default TreeNodeConfig buildConfig(Tree annotation) {
        if (annotation == null) {
            return null;
        }
        TreeNodeConfig config = new TreeNodeConfig();
        config.setKey(annotation.key());
        config.setParentKey(annotation.parentKey());
        config.setNodeName(annotation.nodeName());
        config.setNodeId(annotation.nodeId());
        config.setSort(annotation.sort());
        config.setParentId(annotation.parentId());
        config.setAutoGenerateNodeId(annotation.autoGenerateNodeId());
        return config;
    }

    default Long getNodeId(String nodeIdFieldName, Object obj) {
        if (StringUtils.isBlank(nodeIdFieldName)) {
            throw new TreeInitException("The field name of nodeId should not be blank");
        }
        Class<?> type = obj.getClass();
        try {
            Field field = MyReflectUtils.getPrivateField(type, nodeIdFieldName);
            if (field == null) {
                return null;
            }
            Object nodeId = field.get(obj);
            if (nodeId == null) {
                return null;
            }
            if (nodeId instanceof Long) {
                return (Long) nodeId;
            }
            return convert(nodeId);
        } catch (Exception e) {
            throw new TreeException("NodeId should be able to convert to Long");
        }
    }

    default String getNodeName(String nodeNameFieldName, Object obj) {
        if (StringUtils.isBlank(nodeNameFieldName)) {
            throw new TreeInitException("The field name of nodeName should not be blank");
        }
        Class<?> type = obj.getClass();
        try {
            // Field field = type.getDeclaredField(nodeNameFieldName);
            Field field = ReflectUtil.getField(type, nodeNameFieldName);
            field.setAccessible(true);
            Object nodeName = field.get(obj);
            if (nodeName == null) {
                return null;
            }
            if (nodeName instanceof String) {
                return (String) nodeName;
            }
            return nodeName.toString();
//        } catch (NoSuchFieldException e) {
//            throw new TreeException("NoSuchField[" + nodeNameFieldName + "]");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default String getSort(String sortFieldName, Object obj) {
        if (StringUtils.isBlank(sortFieldName)) {
            return "";
        }
        Class<?> type = obj.getClass();
        try {
            Field field = type.getDeclaredField(sortFieldName);
            field.setAccessible(true);
            Object sortValue = field.get(obj);
            if (sortValue == null) {
                return "";
            }
            if (sortValue instanceof String) {
                return (String) sortValue;
            } else if (sortValue instanceof Number) {
                return sortValue.toString();
            } else {
                return "";
            }
        } catch (NoSuchFieldException e) {
            throw new TreeException("No such field[" + sortFieldName + "]");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default Long convert(Object nodeId) {
        if (nodeId instanceof String) {
            return Long.getLong((String) nodeId);
        } else if (nodeId instanceof Integer) {
            return Integer.toUnsignedLong((Integer) nodeId);
        }
        throw new RuntimeException();
    }

}

