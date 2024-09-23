package com.cmj.app.domain.comment.entity;

import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;  // 댓글 작성자

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;  // 해당 댓글이 달린 게시글

    // Getter, Setter
}