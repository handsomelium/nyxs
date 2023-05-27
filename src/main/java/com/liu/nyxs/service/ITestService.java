package com.liu.nyxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.nyxs.domain.entity.TestDO;

import java.util.List;

/**
 * <p>
 * 测试用 服务类
 * </p>
 *
 * @author lium
 * @since 2023-03-30
 */
public interface ITestService extends IService<TestDO> {

    List<TestDO> getAll();
}
