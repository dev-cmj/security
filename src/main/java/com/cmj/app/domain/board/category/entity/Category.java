package com.cmj.app.domain.board.category.entity;

import com.cmj.app.domain.board.channel.entity.Channel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // 카테고리 이름

    @OneToMany(mappedBy = "category")
    private List<Channel> channels;  // 카테고리에 속한 채널 목록
}