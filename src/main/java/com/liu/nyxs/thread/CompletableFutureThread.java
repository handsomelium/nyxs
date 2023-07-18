package com.liu.nyxs.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author lium
 * @Date 2023/6/29
 * @Description
 */
public class CompletableFutureThread {

    public static void main(String[] args) {
        // 1. 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Integer> list = Arrays.asList(1, 2, 3);
        for (Integer key : list) {
            // 2. 提交任务
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                // 睡眠一秒，模仿处理过程
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.getMessage();
                }
                return "结果" + key;
            }, executorService);
            completableFuture.thenAccept(System.out::println);

        }
        executorService.shutdown();

    }


}
