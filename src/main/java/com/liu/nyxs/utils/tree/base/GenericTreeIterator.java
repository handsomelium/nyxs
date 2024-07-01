package com.liu.nyxs.utils.tree.base;

import com.liu.nyxs.utils.tree.core.TreeIterator;
import com.liu.nyxs.utils.tree.core.TreeNode;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.liu.nyxs.utils.tree.core.TreeTools.toArrayTree;


/**
 * @author ：haoshen
 * @date ：2023-12-13
 * @description : 默认树迭代器
 */
public class GenericTreeIterator implements TreeIterator {
    private final List<TreeNode> arrayTree;
    private int cursor;
    private int levelCursor;

    public GenericTreeIterator(TreeNode treeNode) {
        Assert.notNull(treeNode, "TreeNode should not be null");
        this.arrayTree = toArrayTree(treeNode);
    }


    @Override
    public boolean hasNext() {
        if (CollectionUtils.isEmpty(arrayTree)) {
            return false;
        }
        return cursor <= arrayTree.size() - 1;
    }

    @Override
    public TreeNode next() {
        if (hasNext()) {
            return this.arrayTree.get(cursor++);
        }
        return null;
    }

    @Override
    public boolean hasNextLevel() {
        if (CollectionUtils.isEmpty(arrayTree)) {
            return false;
        }
        return levelCursor <= arrayTree.size() - 1;
    }

    @Override
    public List<? extends TreeNode> nextLevel() {
        if (hasNextLevel()) {
            int level = this.arrayTree.get(levelCursor).getLevel();
            List<TreeNode> resList = new ArrayList<>();
            for (int i = levelCursor; i < arrayTree.size(); i++) {
                if (arrayTree.get(i).getLevel() == level) {
                    resList.add(arrayTree.get(i));
                    levelCursor++;
                } else {
                    break;
                }

            }
            return resList;
        }
        return null;
    }


}
