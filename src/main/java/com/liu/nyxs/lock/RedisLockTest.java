package com.liu.nyxs.lock;

import com.liu.nyxs.NyxsApplication;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.concurrent.*;


/**
 * @Author lium
 * @Date 2023/3/22
 * @Description
 */

@SpringBootTest(classes = NyxsApplication.class)
public class RedisLockTest {

    @Resource
    private RedissonClient redissonClient;

    static int ticket = 100;


    private static final ExecutorService POOL = new ThreadPoolExecutor(10,
            20, 6, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2)
            , Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    /**
     * redis分布式锁简单示例
     */
    @Test
    // @Transactional(rollbackFor = Exception.class)
    // @Rollback(false)
    // @SneakyThrows(Exception.class)
    public void testDistributedLock() {

        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i< 5; i++){
            POOL.execute(() -> {
                while (true){
                    if (ticket <= 0){
                        return;
                    }
                    // 加锁
                    RLock lock = redissonClient.getLock("test:Lock");

                    // 尝试加锁，最多等待5秒，上锁以后60秒自动解锁
                    boolean isLock;
                    try {
                        isLock = lock.tryLock(5, 60, TimeUnit.SECONDS);
                        if (!isLock){
                            return;
                        }
                        if (ticket <= 0){
                            return;
                        }
                        // 处理业务
                        ticket--;
                        System.out.println(Thread.currentThread().getName() + "抢到票了，还剩__" + ticket);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        // 释放锁
                        lock.unlock();
                    }
                }

            });
        }

        POOL.shutdown();

        while (true){
            if (POOL.isTerminated()){
                System.out.println("用时============》" + (System.currentTimeMillis() - startTime));
                return;
            }
        }

    }





    @Test
    public void testSynLock(){
        long startTime = System.currentTimeMillis();

        Object o = new Object();

        for (int i = 0; i < 5; i++){
            POOL.execute(() -> {
                while (true){
                    if (ticket > 0){
                        synchronized (o){
                            if (ticket > 0){
                                ticket--;
                                System.out.println(Thread.currentThread().getName() + "抢到票了，还剩__" + ticket);
                            }else {
                                return;
                            }
                        }
                    }else {
                        return;
                    }
                }
            });
        }
        POOL.shutdown();

        while (true){
            if (POOL.isTerminated()){
                System.out.println("用时============》" + (System.currentTimeMillis() - startTime));
                return;
            }
        }
    }

}
