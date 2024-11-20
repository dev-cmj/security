package com.cmj.app.domain.member.entity;

import com.cmj.app.global.domain.BaseEntity;
import com.cmj.app.global.encode.PasswordCryptService;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;


    public void update(Member member, PasswordCryptService passwordCryptService) {
        this.username = member.getUsername();
        this.password = passwordCryptService.encrypt(member.getPassword());
        this.email = member.getEmail();
        this.role = member.getRole();
    }
}
