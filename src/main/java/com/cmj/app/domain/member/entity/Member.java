package com.cmj.app.domain.member.entity;

import com.cmj.app.domain.token.entity.RefreshToken;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RefreshToken> refreshTokens = new ArrayList<>();

    public void addRefreshToken(RefreshToken refreshToken) {
        refreshTokens.add(refreshToken);
    }

    public void removeRefreshToken(RefreshToken refreshToken) {
        refreshTokens.remove(refreshToken);
    }

    public void update(Member member, PasswordEncoder passwordEncoder) {
        this.username = member.getUsername();
        this.password = passwordEncoder.encode(member.getPassword());
        this.email = member.getEmail();
        this.role = member.getRole();
    }
}
