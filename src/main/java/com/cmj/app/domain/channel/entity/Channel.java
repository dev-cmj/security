package com.cmj.app.domain.channel.entity;

import com.cmj.app.domain.board.entity.Board;
import com.cmj.app.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // 채널 이름
    private String description;  // 채널 설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;  // 채널이 속한 카테고리

    @OneToMany(mappedBy = "channel")
    private List<Board> boards;  // 채널에 속한 게시판 목록

    // 채널마다 별도의 설정을 둘 수 있음
    @Builder.Default
    private boolean isPrivate = false;  // 비공개 채널 여부
    private int maxBoards;  // 해당 채널에서 생성 가능한 게시판 수
}