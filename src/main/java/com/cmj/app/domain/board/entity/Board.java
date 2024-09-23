package com.cmj.app.domain.board.entity;

import com.cmj.app.domain.channel.entity.Channel;
import com.cmj.app.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // 게시판 이름
    private String description;  // 게시판 설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;  // 게시판이 속한 채널

    @OneToMany(mappedBy = "board")
    private List<Post> posts;  // 게시판에 속한 게시글 목록

    // 게시판마다 별도의 설정을 둘 수 있음
    private boolean isAnonymous;  // 익명 게시판 여부
    private boolean isModerated;  // 관리자 승인 여부가 필요한 게시판
}