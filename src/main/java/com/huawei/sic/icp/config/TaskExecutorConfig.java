package com.huawei.sic.icp.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 线程池配置类
 *
 * @since 2022-08-20
 */
@Configuration
@ComponentScan("com.huawei.sic.icp")
public class TaskExecutorConfig implements AsyncConfigurer {



    /**
     * 通过getAsyncExecutor方法配置ThreadPoolTaskExecutor,获得一个基于线程池TaskExecutor
     *
     * @return 线程池
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5); // 核心线程数
        pool.setMaxPoolSize(10); // 最大线程数
        pool.setQueueCapacity(25); // 线程队列
        pool.initialize(); // 线程初始化
        return pool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
