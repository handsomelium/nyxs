package com.liu.nyxs.utils.tree.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : haoshen
 * @date :2023-12-15
 * @description :自定义树注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Tree {
    //树节点id对应的字段
    String nodeId() default "nodeId";
    //树节点上级id对应的字段
    String parentId() default "parentId";
    //树节点名称对应的字段
    String nodeName() default "nodeName";
    //是否自动生成nodeId（虚拟节点）
    boolean autoGenerateNodeId() default false;
    //自定义树节点标识对应的字段
    String key() default "";
    //上级节点key（构建混合节点树使用）
    String parentKey() default "";
    //排序
    String sort() default "";
}
