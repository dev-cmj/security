package com.cmj.app.domain.post.entity;

import com.cmj.app.domain.board.entity.Board;
import com.cmj.app.domain.comment.entity.Comment;
import com.cmj.app.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "memeber_id")
    private Member member;  // 작성자

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;  // 해당 게시글이 속한 게시판

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;  // 게시글에 달린 댓글 목록

    // Getter, Setter
}