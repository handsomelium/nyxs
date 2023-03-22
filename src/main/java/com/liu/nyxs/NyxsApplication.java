package com.liu.nyxs;

import com.liu.nyxs.netty.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NyxsApplication implements CommandLineRunner {

    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(NyxsApplication.class, args);
    }


    @Override
    public void run(String... args) {
        System.out.println("-----------程序启动成功------------");
    }
}
