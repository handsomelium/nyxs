package com.liu.nyxs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.nyxs.domain.entity.TestDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 测试用 Mapper 接口
 * </p>
 *
 * @author lium
 * @since 2023-03-30
 */
@Mapper
public interface TestMapper extends BaseMapper<TestDO> {

}
