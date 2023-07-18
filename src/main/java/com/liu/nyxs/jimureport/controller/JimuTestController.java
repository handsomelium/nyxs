package com.liu.nyxs.jimureport.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liu.nyxs.domain.JimuResponse;
import com.liu.nyxs.domain.entity.SysMenu;
import com.liu.nyxs.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lium
 * @Date 2023/7/18
 * @Description
 */
@RestController
@RequestMapping("/jimuTest")
public class JimuTestController {

    @Autowired
    private ISysMenuService menuService;

    //http://127.0.0.1:19999/nyxsservice/jimuTest/getSysMenuData
    @GetMapping("/getSysMenuData")
    public JimuResponse<?> getSysMenuData(){

        QueryWrapper<SysMenu> param = new QueryWrapper<>();
        param.eq("parent_id", 0L);
        List<SysMenu> paranMenuList = menuService.list(param);
        return getJimuResponse(paranMenuList);

    }

    private JimuResponse<?> getJimuResponse(List<SysMenu> paranMenuList) {
        JimuResponse<SysMenu> response = new JimuResponse<>();
        response.setData(paranMenuList);
        response.setCount(paranMenuList.size());
        response.setTotal(1);
        return response;

    }
}
