package com.cmj.app.domain.post.scheduler;

import com.cmj.app.domain.post.service.PostService;
import com.cmj.app.domain.post.service.ViewCountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ViewCountScheduler {

    private final ViewCountService viewCountService;

    // 1분마다 실행
    @Scheduled(cron = "0 0/1 * * * *")
    public void updateViewCount() {
        log.info("updateViewCount() 실행");
        viewCountService.saveViewCountFromRedisToDB();
    }
}
