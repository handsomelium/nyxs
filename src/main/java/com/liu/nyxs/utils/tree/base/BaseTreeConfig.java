package com.liu.nyxs.utils.tree.base;

import com.liu.nyxs.utils.tree.core.TreeBuildPostProcessor;
import com.liu.nyxs.utils.tree.core.TreeConfig;
import com.liu.nyxs.utils.tree.core.TreeNode;
import com.liu.nyxs.utils.tree.processor.LevelLimitProcessor;
import com.liu.nyxs.utils.tree.processor.PathBuildProcessor;
import com.liu.nyxs.utils.tree.processor.SortProcessor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ：haoshen
 * @date ：2023-12-21
 * @description : 树配置
 */
public class BaseTreeConfig implements TreeConfig {
    private int deep;
    private boolean enableSort;
    private Class<? extends AbstractTree> treeType;
    private List<? extends TreeNode> sourceTreeNodes;
    private List<TreeBuildPostProcessor> postProcessors;
    private Map<String, Object> parameters;

    private BaseTreeConfig() {

    }

    //默认配置
    public static BaseTreeConfig from(List<? extends TreeNode> sourceTreeNodes) {
        return BaseTreeConfig.builder()
                .deep(-1)
                .enableSort(true)
                .treeType(BaseTree.class)
                .sourceTreeNodes(sourceTreeNodes)
                .postProcessors()
                .build();
    }


    public void put(String key, Object value) {
        if (this.parameters == null) {
            this.parameters = new HashMap<>();
        }
        this.parameters.put(key, value);
    }

    @Override
    public int getDeep() {
        return this.deep;
    }

    @Override
    public boolean enableSort() {
        return this.enableSort;
    }

    @Override
    public Class<? extends AbstractTree> getTreeType() {
        return this.treeType;
    }

    @Override
    public List<? extends TreeNode> getSourceTreeNodes() {
        return this.sourceTreeNodes;
    }

    @Override
    public List<TreeBuildPostProcessor> getPostProcessors() {
        return this.postProcessors;
    }

    @Override
    public Map<String, Object> getParameters() {
        if (this.parameters == null) {
            this.parameters = new HashMap<>();
        }
        return this.parameters;
    }

    private void setDeep(int deep) {
        this.deep = deep;
    }

    private void setEnableSort(boolean enableSort) {
        this.enableSort = enableSort;
    }
    private void setTreeType(Class<? extends AbstractTree> treeType) {
        this.treeType = treeType == null ? BaseTree.class : treeType;
    }

    private void setSourceTreeNodes(List<? extends TreeNode> treeNodes) {
        this.sourceTreeNodes = treeNodes;
    }

    private void setPostProcessors(List<TreeBuildPostProcessor> postProcessors) {
        this.postProcessors = postProcessors;
    }

    private void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public static BaseTreeConfigBuilder builder() {
        return new BaseTreeConfigBuilder();
    }

    public static class BaseTreeConfigBuilder {
        private int deep = -1;
        private boolean enableSort = true;
        private Class<? extends AbstractTree> treeType = BaseTree.class;
        private List<? extends TreeNode> sourceTreeNodes;
        private List<TreeBuildPostProcessor> postProcessors;
        private Map<String, Object> parameters;

        public BaseTreeConfigBuilder deep(Integer depth) {
            if (depth == null || depth < 0) {
                this.deep = -1;
            }
            return this;
        }

        public BaseTreeConfigBuilder enableSort(Boolean enable) {
            this.enableSort = (enable == null) || enable;
            return this;
        }

        public BaseTreeConfigBuilder treeType(Class<? extends GenericTree> treeType) {
            this.treeType = treeType == null ? BaseTree.class : treeType;
            return this;
        }

        public BaseTreeConfigBuilder sourceTreeNodes(List<? extends TreeNode> treeNodes) {
            this.sourceTreeNodes = treeNodes == null ? new ArrayList<>(1) : treeNodes;
            return this;
        }

        public BaseTreeConfigBuilder postProcessors(TreeBuildPostProcessor... postProcessors) {
            if (postProcessors == null || postProcessors.length == 0) {
                this.postProcessors = new ArrayList<>();
                this.postProcessors.addAll(Stream.of(new PathBuildProcessor(), new LevelLimitProcessor(),
                        new SortProcessor()).collect(Collectors.toList()));
            } else {
                this.postProcessors = Arrays.asList(postProcessors);
            }
            return this;
        }

        public BaseTreeConfigBuilder parameters(Map<String, Object> parameters) {
            this.parameters = parameters == null ? new HashMap<>(1) : parameters;
            return this;
        }

        public BaseTreeConfig build() {
            BaseTreeConfig baseTreeConfig = new BaseTreeConfig();
            baseTreeConfig.setDeep(deep);
            baseTreeConfig.setEnableSort(enableSort);
            baseTreeConfig.setTreeType(treeType);
            baseTreeConfig.setSourceTreeNodes(sourceTreeNodes);
            baseTreeConfig.setPostProcessors(postProcessors);
            baseTreeConfig.setParameters(parameters);
            return baseTreeConfig;
        }
    }
}
