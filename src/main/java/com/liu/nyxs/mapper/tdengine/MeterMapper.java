package com.liu.nyxs.mapper.tdengine;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.nyxs.domain.entity.Meters;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lium
 * @Date 2023/4/4
 * @Description
 */
@DS("tdengine-service")
@Mapper
public interface MeterMapper extends BaseMapper<Meters> {

    Integer totalCount();
}
