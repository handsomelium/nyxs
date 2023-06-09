package com.liu.nyxs.mapper.mysql;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.nyxs.domain.entity.TestDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 测试用 Mapper 接口
 * </p>
 *
 * @author lium
 * @since 2023-03-30
 */
@DS("mysql-service")
@Mapper
public interface TestMapper extends BaseMapper<TestDO> {

    List<TestDO> getAll();
}
