package com.cmj.app.domain.post.controller;

import com.cmj.app.domain.post.dto.PostSearchCondition;
import com.cmj.app.domain.post.repository.PostProjection;
import com.cmj.app.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;


    @RequestMapping("/page")
    public Page<PostProjection> findPostsPage(PostSearchCondition condition) {
        return postService.findPostsPage(condition);
    }
}
