package com.liu.nyxs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.nyxs.domain.entity.Meters;

import com.liu.nyxs.domain.entity.TestDO;
import com.liu.nyxs.mapper.tdengine.MeterMapper;
import com.liu.nyxs.service.IMeterService;
import com.liu.nyxs.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lium
 * @Date 2023/4/4
 * @Description
 */
@Service
public class MeterServiceImpl extends ServiceImpl<MeterMapper, Meters> implements IMeterService {

    @Autowired
    private MeterMapper meterMapper;

    @Autowired
    private ITestService testService;


    @Override
    public Integer totalCount() {
        return meterMapper.totalCount();
    }


    @Override
    public List<Meters> testTdengineData() {
        return meterMapper.list();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer testTransaction() {
        TestDO testDO = new TestDO();
        testDO.setUserName("张三");
        testDO.setAge(18);
        testDO.setWeight(BigDecimal.valueOf(70));
        testService.save(testDO);
        testTransaction2();
        return null;
    }


    public void testTransaction2(){
        TestDO testDO = new TestDO();
        testDO.setUserName("李四");
        testDO.setAge(19);
        testDO.setWeight(BigDecimal.valueOf(80));
        testService.save(testDO);
        int i = 10 / 0;

    }


}
