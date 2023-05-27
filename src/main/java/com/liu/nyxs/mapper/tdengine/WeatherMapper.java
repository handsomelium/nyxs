package com.liu.nyxs.mapper.tdengine;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.liu.nyxs.domain.entity.Weather;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author lium
 * @Date 2023/4/6
 * @Description
 */

@DS("tdengine-service")
@Mapper
public interface WeatherMapper {

    void createDB();

    void createSuperTable();

    int insertTdengine(@Param("list") List<Weather> list);

    void createTable(Weather weather);

    int insertSubTdengine(Weather weather);

    Integer getWeatherCount();

    List<Weather> avg();
}
