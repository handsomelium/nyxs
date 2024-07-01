package com.liu.nyxs.utils.tree.core;

import com.liu.nyxs.utils.tree.exception.MergeTreeException;
import com.liu.nyxs.utils.tree.exception.TreeNodeConflictException;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author ：haoshen
 * @date ：2023-12-07
 * @description : 树抽象接口
 */
public interface Tree {
    /**
     * 获取树
     */
    TreeNode getTree();

    /**
     * 获取树中的某个节点
     * @param nodeId 节点id
     * @return 找不到会返回null
     */
    TreeNode getTreeNodeById(Long nodeId);

    /**
     * 获取某个节点的上级节点
     */
    TreeNode getParentNodeById(Long nodeId);

    /**
     * 获取树节点数量
     */
    int size();

    /**
     * 树是否为空
     */
    boolean isEmpty();

    /**
     * 是否包含节点
     */
    boolean contains(Long nodeId);

    /**
     * 会将树转成数组，且按照树遍历的顺序返回
     */
    List<? extends TreeNode> getTreeNodeList();

    /**
     * 根据节点id获取其所有子节点
     *
     * @param nodeId     节点Id
     * @param containOwn 是否包含自己
     * @return 所有子节点
     */
    List<? extends TreeNode> getDescendent(Long nodeId, boolean containOwn);

    /**
     * 根据节点id获取其所有父节点
     * @param nodeId 节点id
     * @param containOwn 是否包含自己
     * @return 所有父节点
     */
    List<? extends TreeNode> getAncestor(Long nodeId, boolean containOwn);

    /**
     * 遍历树
     */
    void traverse(TreeVisitor treeVisitor);

    /**
     * 树迭代器{@link TreeIterator}
     */
    TreeIterator iterator();

    /**
     * 收集树节点
     *
     * @param treeCollector 收集器
     * @return 节点列表
     */
    List<? extends TreeNode> collect(TreeCollector treeCollector);

    /**
     * 增加节点，根据节点父id来新增，该方法只会新增当个节点，如果该节点有子节点会忽略，
     * 需要级联新增请用{@link Tree#addNodeCascade(TreeNode)}
     * @return true表示新增成功，false表示新增失败，一般是由于找不到父节点
     */
    boolean addNode(TreeNode node);

    /**
     * 级联新增节点，根据节点父id来新增，如该节点有子节点，也会进行级联新增
     * @return 新增节点的个数
     * @throws TreeNodeConflictException 如果新增节点中存在子节点，且和原树中的节点id一致，会导致冲突
     */
    int addNodeCascade(TreeNode node) throws TreeNodeConflictException;

    /**
     * 仅更新节点信息，不会更新其父节点和子节点，
     * 如需要级联更新节点请用{@link Tree#updateNodeCascade(TreeNode)}方法
     * @param node 节点
     * @return true表示更新成功，false表示更新失败
     */
    boolean updateNode(TreeNode node);

    /**
     * 级联更新节点，除了会更新节点信息，还会更新它的父节点和子节点
     * @return 更新节点数量
     * @throws TreeNodeConflictException 如果更新的节点中存在子节点，且子节点和原树中的节点有冲突，报该异常
     */
    int updateNodeCascade(TreeNode node) throws TreeNodeConflictException;

    /**
     * 删除节点，会级联删除
     * @param nodeId 节点Id
     * @return 删除节点个数
     */
    int removeNode(Long nodeId);

    /**
     * 删除节点，会级联删除
     * @param nodeId            删除节点id
     * @param nodeDelPredictor 删除节点校验器，实现其{@link Predicate#test(Object)}方法，返回true表示该节点能删，返回false则表示
     *                          该节点不能删
     * @return 删除节点的个数
     */
    int removeNode(Long nodeId, Predicate<TreeNode> nodeDelPredictor);

    /**
     * 合并树，会先根据该树的根节点，找到对应的节点来合并，找不到则不合并
     *
     * @param tree 树
     * @throws MergeTreeException 合并树异常
     */
    Tree mergeTree(Tree tree) throws MergeTreeException;

    /**
     * 过滤树节点
     * @param treeFilter 自定义树过滤器
     * @return 返回一棵新树，如果一个节点都没有，则只会包含根节点
     */
    Tree filter(TreeFilter treeFilter);

    Tree filterAnd(TreeFilter... filters);

    /**
     * 通过某个字段来过滤
     * @param fieldExtractor 自定义过滤条件
     * @param targetValue 目标值
     * @return 返回一棵新树，如果一个节点都没有，则只会包含根节点
     * @description 以下展示一段代码示例，通过树id来过滤
     * <code>
     *      pulic Tree testFilterByTreeId() {
     *          //忽略生成树的过程...
     *          Tree filteredTree = tree.filterBy(TreeNode::getNodeId, 2L);
     *      }
     * </code>
     * 注意：如果是String字段，则会按照contains()来判断，其它则会按照equals()方法来判断是否相等
     * 如果需要自定义判断规则，请使用{@link Tree#filter(TreeFilter)}方法
     */
    <T extends TreeNode, R> Tree filterBy(Function<T, R> fieldExtractor, R targetValue);

    /**
     * 用名字筛选树，采用模糊匹配机制
     */
    Tree filterByName(String nodeName);

    /**
     * 会返回一个新的根节点，且不带任何children
     */
    TreeNode getEmptyTree();

    //树访问器
    interface TreeVisitor {
        void consume(TreeNode treeNode);
    }

    //树节点收集器
    interface TreeCollector {
        //true表示要收集这个节点，false则不收集
        boolean isCollect(TreeNode treeNode);
    }

    //树过滤器
    interface TreeFilter {
        //true表示该节点保留，false表示不保留
        boolean contains(TreeNode treeNode);
        //仅用来调试标识
        default String getRemark(String remark) {
            return remark == null ? "" : remark;
        }
    }
}
