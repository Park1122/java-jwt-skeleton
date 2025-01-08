package com.ldcc.javajwtskeleton.domain.member.dto.auth;

import com.ldcc.javajwtskeleton.domain.member.dto.MemberDto;
import com.ldcc.javajwtskeleton.domain.member.dto.Token;
import com.ldcc.javajwtskeleton.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class LoginSuccessResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private MemberDto member;

    public static LoginSuccessResponse of(Token token, Member member) {
        return LoginSuccessResponse.builder()
                .grantType(token.getGrantType())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .member(MemberDto.withRole(member))
                .build();
    }
}
