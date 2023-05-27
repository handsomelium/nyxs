package com.liu.nyxs.controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.liu.nyxs.domain.entity.SysMenu;
import com.liu.nyxs.service.IMeterService;
import com.liu.nyxs.service.ISysMenuService;
import com.liu.nyxs.service.ITDengineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lium
 * @Date 2023/3/16
 * @Description 测试
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private IMeterService meterService;

    @Autowired
    private ITDengineService dengineService;

    @Autowired
    private ISysMenuService sysMenuService;


    @GetMapping("/testTransaction")
    public Integer testTransaction(){
        return meterService.testTransaction();
    }


    @GetMapping("/testTree")
    public R<List<Tree<Long>>> testTree(){
        List<SysMenu> sysMenuList = sysMenuService.list();
        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey("menuId");
        config.setParentIdKey("parentId");
        config.setNameKey("menuName");
        config.setDeep(4);
        config.setWeightKey("orderNum");

        // 转树
        List<Tree<Long>> treeList = TreeUtil.build(sysMenuList, 0L, config, ((object, treeNode) -> {
            treeNode.setId(object.getMenuId());
            treeNode.setParentId(object.getParentId());
            treeNode.setName(object.getMenuName());
            treeNode.putExtra("component", object.getComponent());
            //扩展属性...
        }));
        return R.ok(treeList);

    }




}
