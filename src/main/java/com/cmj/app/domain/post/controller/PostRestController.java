package com.cmj.app.domain.post.controller;

import com.cmj.app.domain.post.dto.PostSearchCondition;
import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.post.entity.PostProjection;
import com.cmj.app.domain.post.service.PostService;
import com.cmj.app.domain.post.service.ViewCountService;
import com.cmj.app.global.domain.PaginationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;
    private final ViewCountService viewCountService;

    @GetMapping("/search")
    public ResponseEntity<?> findPosts(PostSearchCondition condition) {
        if (condition.paginationType() == PaginationType.SLICE) {
            Slice<PostProjection> posts = postService.findPostsSlice(condition);
            return ResponseEntity.ok(posts);
        } else {
            Page<PostProjection> posts = postService.findPostsPage(condition);
            return ResponseEntity.ok(posts);
        }
    }

    @GetMapping("{postId}")
    public ResponseEntity<?> findPostById(@PathVariable Long postId, String username) {
        Optional<Post> post = postService.findPostWithMemberAndBoardById(postId);
        viewCountService.increaseViewCount(post.orElseThrow(), username);
        return ResponseEntity.ok(post);
    }
}
