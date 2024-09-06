package com.cmj.security.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private String name;

    @Column(unique = true)
    private String email;
    private String phone;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

    public Set<GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getRoleName()));
    }

    public void updatePassword(String password) {
        this.password = StringUtils.isEmpty(password) ? this.password : password;
    }

    public void updateEmail(String email) {
        this.email = StringUtils.isEmpty(email) ? this.email : email;
    }

    public void update(String name, String phone, String address) {
        this.name = StringUtils.isEmpty(name) ? this.name : name;
        this.phone = StringUtils.isEmpty(phone) ? this.phone : phone;
        this.address = StringUtils.isEmpty(address) ? this.address : address;
    }


}
