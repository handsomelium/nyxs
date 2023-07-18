package com.liu.nyxs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class}, scanBasePackages = {"org.jeecg.modules.jmreport","com.liu"})
@MapperScan("com.liu.nyxs.mapper")
public class NyxsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NyxsApplication.class, args);
    }


    @Override
    public void run(String... args) {
        System.out.println("-----------程序启动成功------------");
    }
}
