package com.cmj.app.domain.category.entity;

import com.cmj.app.domain.channel.entity.Channel;
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
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // 카테고리 이름

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<Channel> channels = new ArrayList<>();  // 카테고리에 속한 채널 목록
}