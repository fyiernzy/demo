package com.example.oauth_client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Async(ThreadPoolConfig.BATCH_THREADS)
@RestController
@RequestMapping("/api/batch")
public class CallableController {

    @Lazy CallableController self;

    @GetMapping
    public CompletableFuture<List<String>> getBatchResults() {
        log.info("The curren thread is: {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture(self.doSomeWork());
    }

    @Transactional
    public List<String> doSomeWork() {
        return List.of("Zhiyang", "is", "handsome");
    }
}
