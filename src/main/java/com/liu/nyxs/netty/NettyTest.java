package com.liu.nyxs.netty;

import com.liu.nyxs.NyxsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author lium
 * @Date 2023/3/22
 * @Description
 */
@SpringBootTest(classes = NyxsApplication.class)
public class NettyTest {
    @Autowired
    private NettyServer nettyServer;

    @Test
    public void test1(){
        nettyServer.start();
    }
}
