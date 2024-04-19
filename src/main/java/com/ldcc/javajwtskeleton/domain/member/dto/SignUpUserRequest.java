package com.ldcc.javajwtskeleton.domain.member.dto;

import com.ldcc.javajwtskeleton.domain.member.entity.Member;
import com.ldcc.javajwtskeleton.domain.member.entity.MemberRoleType;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@ToString
@Getter
public class SignUpUserRequest {

    private String memberId;
    private String name;
    private String password;
    private String email;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .memberId(this.memberId)
                .name(this.name)
                .password(passwordEncoder.encode(this.password))
                .email(this.email)
                .build();
    }
}
