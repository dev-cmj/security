package com.cmj.app.domain.post.scheduler;

import com.cmj.app.domain.post.service.PostService;
import com.cmj.app.domain.post.service.ViewCountService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ViewCountScheduler {

    private final ViewCountService viewCountService;
    private final RedisTemplate<String, String> redisTemplate;

    // 1분마다 실행
    @Scheduled(cron = "0 0/1 * * * *")
    @CircuitBreaker(name = "updateViewCount", fallbackMethod = "updateViewCountFallback")
    public void updateViewCount() {
        viewCountService.saveViewCountFromRedisToDB();
    }

    // Fallback 메서드
    public void updateViewCountFallback(Throwable throwable) {
        log.error("레디스 서버가 정상적으로 작동하지 않습니다. DB 조회수 업데이트 실패", throwable);
    }

    @PostConstruct
    public void init() {
        log.info("ViewCountScheduler 초기화");
        redisTemplate.delete("post:viewcount:*");
        for (int i=1; i<=10; i++) {
            redisTemplate.opsForValue().set("post:viewcount:" + i, String.valueOf(i));
        }
    }
}
