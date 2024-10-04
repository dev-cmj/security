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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
