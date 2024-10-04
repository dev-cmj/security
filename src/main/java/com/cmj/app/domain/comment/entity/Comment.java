package com.cmj.app.domain.comment.entity;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 댓글 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;  // 해당 댓글이 달린 게시글

    // 부모 댓글 설정
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;  // 부모 댓글 (대댓글의 부모)

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> replies = new ArrayList<>();  // 자식 댓글 (대댓글 목록)

    // 댓글 내용 업데이트
    public void updateContent(String content) {
        this.content = content;
    }

    // 댓글 작성자 확인
    public boolean isOwner(String username) {
        return member.getUsername().equals(username);
    }

    // 자식 댓글 추가
    public void addReply(Comment reply) {
        replies.add(reply);
        reply.setParent(this);
    }

}
