package com.cmj.app.domain.comment.controller;

import com.cmj.app.domain.comment.dto.CommentRequest;
import com.cmj.app.domain.comment.dto.CommentSearchCondition;
import com.cmj.app.domain.comment.entity.CommentProjection;
import com.cmj.app.domain.comment.service.CommentService;
import com.cmj.app.global.domain.PaginationType;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    //crud operations
    @PostMapping
    public ResponseEntity<?> saveComment(CommentRequest commentRequest, UserPrincipal userPrincipal) {
        commentService.saveComment(commentRequest, userPrincipal.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> findComments(CommentSearchCondition condition) {
        if (condition.paginationType() == PaginationType.SLICE) {
            Slice<CommentProjection> comments = commentService.findCommentsSlice(condition);
            return ResponseEntity.ok(comments);
        } else {
            Page<CommentProjection> comments = commentService.findCommentsPage(condition);
            return ResponseEntity.ok(comments);
        }
    }

    //댓글 단일 조회는 api 미제공

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, CommentRequest commentRequest, UserPrincipal userPrincipal) {
        commentService.updateCommentById(commentId, commentRequest, userPrincipal.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, UserPrincipal userPrincipal) {
        commentService.deleteCommentById(commentId, userPrincipal.getName());
        return ResponseEntity.ok().build();
    }


}
