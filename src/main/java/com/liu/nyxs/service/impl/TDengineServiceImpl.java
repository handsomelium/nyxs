package com.liu.nyxs.service.impl;

import com.liu.nyxs.domain.entity.Weather;
import com.liu.nyxs.mapper.tdengine.WeatherMapper;
import com.liu.nyxs.service.ITDengineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Author lium
 * @Date 2023/4/6
 * @Description
 */

@Service
public class TDengineServiceImpl implements ITDengineService {

    @Autowired
    private WeatherMapper weatherMapper;




    @Override
    public void createDb() {
        weatherMapper.createDB();
    }


    @Override
    public void createSuperTable() {
        weatherMapper.createSuperTable();
    }


    @Override
    public int insertTdengine(List<Weather> weather) {
        return weatherMapper.insertTdengine(weather);
    }


    @Override
    public int createTable() {
        String[] locations = {"北京", "上海", "广州", "深圳", "天津"};
        Random random = new Random(System.currentTimeMillis());
        long ts = System.currentTimeMillis();
        long thirtySec = 1000 * 30;
        int count = 0;
        for (int i = 0; i < 20; i++) {
            Weather weather = new Weather(new Timestamp(ts + (thirtySec * i)), 30 * random.nextFloat(), random.nextInt(100));
            weather.setLocation(locations[random.nextInt(locations.length)]);
            weather.setGroupId(i % locations.length);
            weatherMapper.createTable(weather);
            count += weatherMapper.insertSubTdengine(weather);
        }
        return count;
    }

    @Override
    public Integer getWeatherCount() {
        return weatherMapper.getWeatherCount();
    }

    @Override
    public List<Weather> avg() {
        return weatherMapper.avg();
    }



}
