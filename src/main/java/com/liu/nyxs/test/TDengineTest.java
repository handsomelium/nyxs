package com.liu.nyxs.test;

/**
 * @Author lium
 * @Date 2023/4/6
 * @Description
 */

import com.liu.nyxs.NyxsApplication;
import com.liu.nyxs.domain.entity.Weather;
import com.liu.nyxs.service.ITDengineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = NyxsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TDengineTest {

    @Autowired
    private ITDengineService dengineService;


    @Test
    public void createDb(){
        dengineService.createDb();
    }


    @Test
    public void createSuperTable(){
        dengineService.createSuperTable();
    }

    @Test
    public void createTable(){
        System.out.println(dengineService.createTable());
    }


    @Test
    public void insertTdengine(){
        List<Weather> list = new ArrayList<>();
        for (int i=0; i<100; i++){
            Weather weather = new Weather();
            weather.setTs(new Timestamp(System.currentTimeMillis() + i));
            weather.setHumidity((float) i);
            weather.setTemperature((float) (i+1));
            list.add(weather);
        }
        dengineService.insertTdengine(list);
    }


    @Test
    public void avg(){
        List<Weather> list = dengineService.avg();
        System.out.println(list);
    }



    @Test
    public void count(){
        Integer count = dengineService.getWeatherCount();
        System.out.println(count);
    }

}
