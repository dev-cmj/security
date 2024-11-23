package com.cmj.app.global.auth.dto;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.member.entity.MemberRole;
import com.cmj.app.global.encode.PasswordCryptService;
import com.cmj.app.global.encode.RSATextCryptService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
@Builder
public record SignUpRequest(
        @NotEmpty(message = "아이디는 필수 입력 값입니다.")
        @Length(min = 5, max = 20, message = "아이디는 5자 이상, 20자 이하여야 합니다.")
        @Pattern(
                regexp = "^(?!\\d)[a-zA-Z0-9._]{5,20}$",
                message = "아이디는 영문, 숫자, '.', '_'만 포함할 수 있으며 숫자로 시작할 수 없습니다."
        )
        String username,

        @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
        String password,

        @NotEmpty(message = "이름은 필수 입력 값입니다.")
        @Pattern(
                regexp = "^[a-zA-Z가-힣\\s]{2,30}$",
                message = "이름은 한글, 영문, 공백만 포함할 수 있으며 2자 이상 30자 이하여야 합니다."
        )
        String name,

        @NotEmpty(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "유효한 이메일 주소를 입력해주세요. (예: example@email.com)"
        )
        String email
) {
    // 회원가입 요청 Dto를 회원 엔티티로 변환
    public static Member toEntity(SignUpRequest signUpRequest, PasswordCryptService passwordCryptService, RSATextCryptService rsaTextCryptService) {
        String decrypt = rsaTextCryptService.decrypt(signUpRequest.password());
        if (!passwordCryptService.isValidPassword(decrypt)) {
            throw new IllegalArgumentException("비밀번호는 8자 이상, 영문 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.");
        }

        return Member.builder()
                .username(signUpRequest.username())
                .password(passwordCryptService.encrypt(decrypt))
                .name(signUpRequest.name())
                .email(signUpRequest.email())
                .role(MemberRole.USER)
                .build();
    }
}