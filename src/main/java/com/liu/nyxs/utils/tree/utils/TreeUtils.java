package com.liu.nyxs.utils.tree.utils;

import com.liu.nyxs.utils.tree.base.*;
import com.liu.nyxs.utils.tree.core.*;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author ：haoshen
 * @date ：2023-12-13
 * @description : 树工具类
 * <p>
 * 关于下面方法的一些公共说明
 * <ul>
 * <li>
 * 下面的参数{List<T> treeSources}对应的是从数据库中查询出来的数据，数据库的一条记录对应一个节点，下面不再赘述
 * 但是需要注意，该类必须使用{@link com.liu.nyxs.utils.tree.annotation.Tree}注解进行标注，且配置好相应属性，否则会解析失败!
 * </li>
 * <li>泛型T代表任意类型，比如BaseAreaDO</li>
 * <li>方法返回的是实现了{@link Tree}接口的类，该接口提供了很多对树的操作，如果想获取根节点，只需要调用{@link Tree#getTree()}方法</li>
 * </ul>
 * </p>
 * <p>
 *     如，当需要构建一棵区域树，且区域树对应的实体类为BaseAreaDO，那么应该如下配置
 * <code>
 * //示例代码
 * {@literal @}Tree(nodeId = "areaId", parentId = "areaPid", nodeName = "areaName")
 *          public class BaseAreaDO {
 *              private Long areaId;
 *              private Long parentId;
 *              //注意上面两个字段请保证为Long，如果不是也至少保证能转成Long
 *              private String areaName;
 *           }
 * </code>
 * </p>
 */
@SuppressWarnings({"unchecked"})
public abstract class TreeUtils {

    /**
     * 构建树
     * @return {@link BaseTree} 返回树结构
     * @param <T> 自定义数据泛型，可以是任何类型
     */
    public static <T> BaseTree<T> buildBaseTree(List<T> treeSources) {
        return buildBaseTree(treeSources, null);
    }

    /**
     * @param treeConfig 树配置，可自定义配置，默认会使用{@link BaseTreeConfig#from(List)}作为配置
     */
    public static <T> BaseTree<T> buildBaseTree(List<T> treeSources, TreeConfig treeConfig) {
        return (BaseTree<T>) generateBaseTree(treeSources, treeConfig);
    }

    /**
     * 构建混合节点树
     * @param treeSources 树节点来源
     * <p>
     * 该方法主要来用于构建关联不同对象的树，如构建一棵业主树，其由两部分组成，分别是：BaseAreaDO和BaseUserDO，那么需要将这两个类都
     * 使用{@literal @}Tree注解进行标注和配置，然后从数据库中查出数据，并设置好[nodeId],[parentId],[nodeName]这三个属性后（注意这些节点id
     * 必须遵循树的结构），作为参数传入该方法即可
     * </p>
     * @return 混合树根节点，由于不同类型不确定的缘故，因此关联的数据只能是Object类型，但为了进行区分，可以在构建树之前设置好
     * {@link BaseTreeNode#getKey()}属性，也可以通过{@link MixedTreeNode#getType()}来获取其真实类型
     */
    public static MixedTree buildMixedTree(List<?>... treeSources) {
        return buildMixedTree(null, treeSources);
    }

    public static MixedTree buildMixedTree(TreeConfig treeConfig, List<?>... treeSources) {
        if (treeSources == null || treeSources.length == 0) {
            return null;
        }
        return (MixedTree) generateMixedTree(treeConfig, treeSources);
    }

    public static <T> BaseTree<T> parseJsonTree(JSONObject treeJSON, Class<T> dataType) {
        return TreeTools.parseBaseJsonTree(treeJSON, dataType);
    }

    private static <T> Tree generateBaseTree(List<T> treeSources, TreeConfig treeConfig) {
        Assert.notEmpty(treeSources, "treeSources should not be empty");
        BaseTreeNodeBuilder treeNodeBuilder = new BaseTreeNodeBuilder();
        List<BaseTreeNode<T>> treeNodes = treeNodeBuilder.buildTreeNodes(treeSources);
        return buildTree(treeNodes, treeConfig);
    }

    private static Tree generateMixedTree(TreeConfig treeConfig, List<?>[] treeSources) {
        MixedTreeNodeBuilder treeNodeBuilder = new MixedTreeNodeBuilder();
        List<MixedTreeNode> treeNodes = treeNodeBuilder.buildTreeNodes(treeSources);
        return buildTree(treeNodes, treeConfig);
    }

    private static Tree buildTree(List<? extends TreeNode> treeNodes, TreeConfig treeConfig) {
        TreeBuilder treeBuilder = new BaseTreeBuilder();
        treeConfig = treeConfig == null ? BaseTreeConfig.from(treeNodes) : treeConfig;
        return treeBuilder.buildTree(treeConfig);
    }
}
