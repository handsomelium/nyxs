package com.liu.nyxs.utils.tree.core;

import java.util.List;
import java.util.Map;

/**
 * @author ：haoshen
 * @date ：2023-12-07
 * @description : 树定义元信息接口
 */
public interface TreeDefinition {
    //元节点
    List<TreeNode> treeNodeList();

    //节点缓存
    Map<Long, TreeNode> treeNodeMap();

    //树根节点
    TreeNode treeRoot();

    //节点数量
    int size();

    //层级
    int level();

    //设置树配置
    void setTreeConfig(TreeConfig treeConfig);

    //添加后置处理器
    void setPostProcessors(List<TreeBuildPostProcessor> postProcessors);

    //增加节点
    boolean addNode(TreeNode treeNode);

    //级联新增节点
    int addNodeCascade(TreeNode treeNode);

    //更新节点，找不到节点返回false
    boolean updateNode(TreeNode treeNode);

    //级联更新节点
    int updateNodeCascade(TreeNode treeNode);

    //删除节点（会级联删除）
    int removeNodes(Long[] nodeIds);


    //拷贝，不传参则拷贝一份原本的TreeDefinition，传参则会过滤
    TreeDefinition copy();
}
