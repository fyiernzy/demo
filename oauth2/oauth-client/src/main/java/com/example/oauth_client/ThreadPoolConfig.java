package com.example.oauth_client;

import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class ThreadPoolConfig {

    public static final String BATCH_THREADS = "batchThreads";

    @Bean(BATCH_THREADS)
    public ThreadPoolTaskExecutor batchWorkers(ThreadPoolTaskExecutorBuilder builder) {
        return builder
            .threadNamePrefix(prefix(BATCH_THREADS))
            .corePoolSize(10).build();
    }

    private String prefix(String threadPoolName) {
        return threadPoolName + "-";
    }
}
