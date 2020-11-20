package com.lcc.excel;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author Administrator
 * @Description 测试线程池
 * @Date 2020/9/15  10:26
 */
public class TestThreadPool {
    /**
     * 当线程池中发生了异常，会发生啥
     * 1、执行execute()方法，会打印异常信息
     * 执行submit()方法，不会,但是把它封装在Future<?>中，调用get方法，能打印
     * 2、不会影响其他线程执行
     * 3、线程池会删除其发生异常的线程，创建新的线程放入线程池中
     */
    public static void main(String[] args) {
        ThreadPoolTaskExecutor executorService = buildThreadPoolTaskExecutor();

        executorService.execute(() -> {
            for (int i = 0; i < 100; i++) {
                sayHi("execute");
            }
        }); //会抛出异常信息

//        Future<?> submit = executorService.submit(() -> sayHi("submit"));//不会抛出异常信息
//        try {
//            submit.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }


//        System.out.println("-------start--------");
//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> { // 无返回值的
//            sayHi("异步编排无返回值的");
//        }, executorService);
//        System.out.println("-------end--------");


//        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> { // 有返回值的
//            sayHi("异步编排有返回值的");
//            return "这是返回的结果";
//        }, executorService);
//        String s = null;
//        try {
//            s = supplyAsync.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//        System.out.println("返回的结果值为："+s);

    }

    private static void sayHi(String name) {
        String printStr = "【thread-name:" + Thread.currentThread().getName() + ",执行方式:" + name + "】";
        System.out.println(printStr);
//        throw new RuntimeException(printStr + ",我异常啦!哈哈哈!");
    }

    private static ThreadPoolTaskExecutor buildThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executorService = new ThreadPoolTaskExecutor();
        executorService.setThreadNamePrefix("(别问啊)-");
        executorService.setCorePoolSize(40); //核心线程数
        executorService.setQueueCapacity(1000); //阻塞队列
        executorService.setMaxPoolSize(200); //最大线程数
        executorService.setKeepAliveSeconds(30);
        executorService.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executorService.initialize();
        return executorService;
    }
}
