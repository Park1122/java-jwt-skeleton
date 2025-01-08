package com.ldcc.javajwtskeleton.domain.member.dto;

import com.ldcc.javajwtskeleton.domain.member.entity.Member;
import com.ldcc.javajwtskeleton.domain.member.entity.MemberRoleType;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SignUpUserRequest {

    @NotBlank
    private String memberId;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
