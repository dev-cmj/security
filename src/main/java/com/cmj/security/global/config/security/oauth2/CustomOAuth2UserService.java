package com.cmj.security.global.config.security.oauth2;

import com.cmj.security.global.config.security.CachedUserDetailsService;
import com.cmj.security.domain.member.entity.Member;
import com.cmj.security.domain.entity.Roles;
import com.cmj.security.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final CachedUserDetailsService cachedUserDetailsService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());

        if (userInfo.getEmail() == null || userInfo.getEmail().isEmpty()) {
            throw new OAuth2AuthenticationException("이메일 정보가 제공되지 않았습니다.");
        }

        Optional<Member> existingMember = memberRepository.findByEmail(userInfo.getEmail());

        Member member = existingMember.map(value -> updateExistingMember(value, userInfo))
                .orElseGet(() -> registerNewMember(userInfo));

        UserDetails userDetails = cachedUserDetailsService.loadUserByUsername(member.getUsername());

        return new DefaultOAuth2User(userDetails.getAuthorities(), oAuth2User.getAttributes(), userNameAttributeName);
    }

    private Member updateExistingMember(Member existingMember, OAuth2UserInfo userInfo) {
        existingMember.update(userInfo.getName(), null, null);
        return memberRepository.save(existingMember);
    }

    // 새로운 사용자를 등록하는 메소드
    private Member registerNewMember(OAuth2UserInfo userInfo) {
        Member member = Member.builder()
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .role(Role.builder().roleName(Roles.ROLE_USER.name()).build())// 기본 사용자 권한 부여
                .build();

        return memberRepository.save(member);
    }
}