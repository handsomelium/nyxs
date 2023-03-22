package com.liu.nyxs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.Redisson;

@Configuration
public class RedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(){
        //配置类
        Config config = new Config();
        // 添加redis地址，这里添加了单点的地址，也可以使用config.useClusterServers()添加集群地址
        config.useSingleServer().setAddress("redis://150.158.50.73:6379").setPassword("123456");
        // 创建客户端
        return Redisson.create(config);
    }
}