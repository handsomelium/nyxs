package com.liu.nyxs.service;

import com.liu.nyxs.domain.entity.Weather;

import java.util.List;
import java.util.Map;

/**
 * @Author lium
 * @Date 2023/4/6
 * @Description
 */
public interface ITDengineService {


    void createDb();

    void createSuperTable();

    void insertTdengine(List<Weather> weather);

    int  createTable();

    Integer getWeatherCount();

    List<Weather> avg();


}
