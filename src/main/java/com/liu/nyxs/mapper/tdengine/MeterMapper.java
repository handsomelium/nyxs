package com.liu.nyxs.mapper.tdengine;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.nyxs.domain.entity.Meters;
import com.liu.nyxs.domain.entity.Weather;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author lium
 * @Date 2023/4/4
 * @Description
 */
@DS("tdengine-service")
@Mapper
public interface MeterMapper extends BaseMapper<Meters> {

    List<Meters> list();

    Integer totalCount();

}
