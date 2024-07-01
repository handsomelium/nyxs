package com.liu.nyxs.utils.tree.base;

import com.liu.nyxs.utils.tree.core.*;
import com.liu.nyxs.utils.tree.exception.TreeBuildException;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;

/**
 * @author ：haoshen
 * @date ：2023-12-21
 * @description : 通用树构造器
 */
public class BaseTreeBuilder implements TreeBuilder {
    @Override
    public Tree buildTree(TreeConfig treeConfig) {
        Assert.notNull(treeConfig, "tree build fail, treeConfig should not be null");
        //构建TreeDefinition
        TreeDefinitionBuilder definitionBuilder = new BaseTreeDefinitionBuilder();
        TreeDefinition treeDefinition = definitionBuilder
                .buildNodes(treeConfig.getSourceTreeNodes())
                .buildTreeConfig(treeConfig)
                .buildPostProcessor(treeConfig.getPostProcessors().toArray(new TreeBuildPostProcessor[0]))
                .build();
        //构建树
        Class<? extends AbstractTree> treeType = treeConfig.getTreeType();
        treeType = treeType == null ? BaseTree.class : treeType;
        try {
            Constructor<? extends AbstractTree> constructor = treeType.getDeclaredConstructor(TreeDefinition.class);
            constructor.setAccessible(true);
            return constructor.newInstance(treeDefinition);
        } catch (NoSuchMethodException e) {
            throw new TreeBuildException("The tree build failed cause there should be a constructor that " +
                    "includes the `TreeDefinition` parameter");
        } catch (Exception e) {
            throw new TreeBuildException(e.getMessage());
        }
    }
}
