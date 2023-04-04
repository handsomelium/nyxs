package com.liu.nyxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.nyxs.domain.entity.Meters;

/**
 * @Author lium
 * @Date 2023/4/4
 * @Description
 */
public interface IMeterService extends IService<Meters> {

    Integer totalCount();
}
