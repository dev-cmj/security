package com.cmj.app.domain.post.controller;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.member.service.MemberService;
import com.cmj.app.domain.post.dto.PostRequest;
import com.cmj.app.domain.post.dto.PostSearchCondition;
import com.cmj.app.domain.post.entity.PostProjection;
import com.cmj.app.domain.post.service.PostService;
import com.cmj.app.global.domain.PaginationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostRestController {

    private final MemberService memberService;
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

    /*
     * 게시글 조회 후 조회수 증가 로직
     */
    @GetMapping("/{postId}")
    public ResponseEntity<?> findPostWithMemberAndBoardById(@PathVariable Long postId, UserPrincipal userPrincipal) {
        return ResponseEntity.ok(postService.increaseAndGetViewCountAndFindPostWithMemberAndBoardById(postId, userPrincipal.getName()));
    }

    @PostMapping
    public ResponseEntity<?> savePost(@RequestBody PostRequest request, UserPrincipal userPrincipal) {
        Member member = memberService.findByUsername(userPrincipal.getName());
        Long postId = postService.savePost(request.toEntity(member));
        return ResponseEntity.ok(postId);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostRequest request, UserPrincipal userPrincipal) {
        Member member = memberService.findByUsername(userPrincipal.getName());
        postService.validatePostOwner(postId, member.getUsername());
        postService.updatePostById(postId, request.toEntity(member));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, UserPrincipal userPrincipal) {
        Member member = memberService.findByUsername(userPrincipal.getName());
        postService.validatePostOwner(postId, member.getUsername());
        postService.deletePostById(postId);
        return ResponseEntity.ok().build();
    }


}
