package com.liu.nyxs.service.impl;

import com.liu.nyxs.domain.entity.TestDO;
import com.liu.nyxs.mapper.mysql.TestMapper;
import com.liu.nyxs.service.ITestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试用 服务实现类
 * </p>
 *
 * @author lium
 * @since 2023-03-30
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, TestDO> implements ITestService {

}
