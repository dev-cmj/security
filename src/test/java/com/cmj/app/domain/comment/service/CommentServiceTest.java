package com.cmj.app.domain.comment.service;

import com.cmj.app.domain.comment.dto.CommentRequest;
import com.cmj.app.domain.comment.dto.CommentSearchCondition;
import com.cmj.app.domain.comment.entity.CommentProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class CommentServiceTest {

    private final CommentService commentService;


    @Test
    void findCommentsPage() {
        // Given (테스트 데이터 1번 게시물에 대한 댓글 검색 조건)
        CommentSearchCondition condition = CommentSearchCondition.builder()
                .postId(1L)
                .build();

        // When
        Page<CommentProjection> commentsSlice = commentService.findCommentsPage(condition);

        // Then
        Assertions.assertThat(commentsSlice).isNotNull();
        Assertions.assertThat(commentsSlice.getContent()).isNotEmpty();
    }

    @Test
    void findCommentsSlice() {
        // Given (테스트 데이터 1번 게시물에 대한 댓글 검색 조건)
        CommentSearchCondition condition = CommentSearchCondition.builder()
                .postId(1L)
                .build();

        // When
        Slice<CommentProjection> commentsSlice = commentService.findCommentsSlice(condition);

        // Then
        Assertions.assertThat(commentsSlice).isNotNull();
        Assertions.assertThat(commentsSlice.getContent()).isNotEmpty();
    }

    //parentId가 1인 댓글을 조회
    @Test
    void findCommentsByParentId() {
        // Given (테스트 데이터 1번 댓글에 대한 대댓글 검색 조건)
        CommentSearchCondition condition = CommentSearchCondition.builder()
                .postId(1L)
                .parentId(1L)
                .build();

        // When
        Slice<CommentProjection> commentsSlice = commentService.findCommentsSlice(condition);

        // Then
        Assertions.assertThat(commentsSlice).isNotNull();
        Assertions.assertThat(commentsSlice.getContent()).isNotEmpty();

        log.info(commentsSlice.getContent().toString());
    }




}