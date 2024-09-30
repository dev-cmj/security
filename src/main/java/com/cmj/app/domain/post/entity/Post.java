package com.cmj.app.domain.post.entity;

import com.cmj.app.domain.board.entity.Board;
import com.cmj.app.domain.comment.entity.Comment;
import com.cmj.app.domain.like.entity.Like;
import com.cmj.app.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Setter
    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;  // 해당 게시글이 속한 게시판

    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();  // 게시글에 달린 댓글 목록

    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<Like> likes = new ArrayList<>();  // 게시글에 달린 좋아요 목록

    public void update(Post post) {
        this.title = post.title;
        this.content = post.content;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

}