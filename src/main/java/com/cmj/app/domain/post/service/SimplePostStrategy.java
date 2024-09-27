package com.cmj.app.domain.post.service;

import com.cmj.app.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimplePostStrategy implements PostStrategy{

    @Override
    public void increaseViewCount(Post post, String username) {
        // 조회수 증가 로직 DB 쿼리로 대체
    }

    @Override
    public Long getViewCount(Long postId) {
        // 조회수 가져오기 로직 DB 쿼리로 대체
        return 0L;
    }
}
