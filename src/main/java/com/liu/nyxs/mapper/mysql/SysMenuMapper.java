package com.liu.nyxs.mapper.mysql;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.nyxs.domain.entity.SysMenu;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author lium
 * @since 2023-04-27
 */
@DS("mysql-service")
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}
