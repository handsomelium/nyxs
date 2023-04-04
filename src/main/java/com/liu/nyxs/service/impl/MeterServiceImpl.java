package com.liu.nyxs.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.nyxs.domain.entity.Meters;

import com.liu.nyxs.mapper.tdengine.MeterMapper;
import com.liu.nyxs.service.IMeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author lium
 * @Date 2023/4/4
 * @Description
 */
@Service
public class MeterServiceImpl extends ServiceImpl<MeterMapper, Meters> implements IMeterService {

    @Autowired
    private MeterMapper meterMapper;

    @Override
    public Integer totalCount() {
        return meterMapper.totalCount();
    }
}
