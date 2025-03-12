package com.jxw.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig {

    // 通用异步任务线程池（CPU密集型）
    @Bean(name = "asyncTaskExecutor")
    public Executor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors()); // 核心线程数 = CPU核心数
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

    // IO密集型任务线程池（使用WorkStealingPool）
    @Bean(name = "ioExecutor")
    public Executor ioExecutor() {
        return Executors.newWorkStealingPool(50); // 并行度设置为50
    }
}
