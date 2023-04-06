package com.liu.nyxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.nyxs.domain.entity.Meters;
import com.liu.nyxs.domain.entity.Weather;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lium
 * @Date 2023/4/4
 * @Description
 */
public interface IMeterService extends IService<Meters> {

    Integer totalCount();

    List<Meters> testTdengineData();


}
