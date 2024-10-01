package com.cmj.app.domain.post.controller;

import com.cmj.app.domain.post.dto.PostSearchCondition;
import com.cmj.app.domain.post.entity.PostProjection;
import com.cmj.app.domain.post.service.PostService;
import com.cmj.app.global.domain.PaginationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;

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

    @GetMapping("/{postId}")
    public ResponseEntity<?> findPostWithMemberAndBoardById(@PathVariable Long postId, UserPrincipal userPrincipal) {
        return ResponseEntity.ok(postService.increaseAndGetViewCountAndFindPostWithMemberAndBoardById(postId, userPrincipal.getName()));
    }


}
