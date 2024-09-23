package com.cmj.app.domain.channel.entity;

import com.cmj.app.domain.board.entity.Board;
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

    @OneToMany(mappedBy = "channel")
    private List<Board> boards;  // 채널에 속한 게시판 목록

    // 채널마다 별도의 설정을 둘 수 있음
    private boolean isPrivate;  // 비공개 채널 여부
    private int maxBoards;  // 해당 채널에서 생성 가능한 게시판 수
}