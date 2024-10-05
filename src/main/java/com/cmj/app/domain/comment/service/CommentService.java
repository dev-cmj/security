package com.cmj.app.domain.comment.service;

import com.cmj.app.domain.comment.dto.CommentRequest;
import com.cmj.app.domain.comment.dto.CommentSearchCondition;
import com.cmj.app.domain.comment.entity.Comment;
import com.cmj.app.domain.comment.entity.CommentProjection;
import com.cmj.app.domain.comment.repository.CommentRepository;
import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.member.service.MemberService;
import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final PostService postService;
    private final MemberService memberService;
    private final CommentRepository commentRepository;

    //crud operations
    @Transactional
    public void saveComment(CommentRequest commentRequest, String username) {
        Post post = postService.findPostById(commentRequest.getPostId());
        Member member = memberService.findByUsername(username);

        // 부모 댓글이 있는지 확인 (대댓글 여부 판단)
        Comment parentComment = null;
        if (commentRequest.getParentId() != null) {
            parentComment = commentRepository.findById(commentRequest.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent comment not found with id: " + commentRequest.getParentId()));
        }

        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .content(commentRequest.getContent())
                .parent(parentComment)  // 부모 댓글 설정 (대댓글인 경우)
                .build();

        commentRepository.save(comment);

        // 부모 댓글이 있으면 대댓글로 처리
        if (parentComment != null) {
            parentComment.addReply(comment);
            commentRepository.save(parentComment);
        }
    }

    public void validateCommentOwner(Long commentId, String username) {
        commentRepository.findById(commentId)
                .filter(comment -> comment.isOwner(username))
                .orElseThrow(() -> new IllegalArgumentException("댓글 작성자만 수정, 삭제 가능합니다."));
    }

    @Transactional
    public void updateCommentById(Long commentId, CommentRequest commentRequest, String username) {
        validateCommentOwner(commentId, username);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
        comment.updateContent(commentRequest.getContent());
    }

    @Transactional
    public void deleteCommentById(Long commentId, String username) {
        validateCommentOwner(commentId, username);
        commentRepository.deleteById(commentId);
    }

    public Page<CommentProjection> findCommentsPage(CommentSearchCondition condition) {
        return commentRepository.findCommentsPage(condition);
    }

    public Slice<CommentProjection> findCommentsSlice(CommentSearchCondition condition) {
        return commentRepository.findCommentsSlice(condition);
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
    }





}
