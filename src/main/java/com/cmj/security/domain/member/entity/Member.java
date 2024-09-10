package com.cmj.security.domain.member.entity;

import com.cmj.security.global.common.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseAuditableEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String nickname;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Column
    private String oauthIdentifier;

    @Enumerated(EnumType.STRING)
    private OauthType oauthType;

    @Column
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;
}
