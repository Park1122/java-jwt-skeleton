package com.ldcc.javajwtskeleton.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@ToString
public class MemberDto {
    private String memberId;
    private String name;
    private String email;
    private String role;

    public static MemberDto withRole(Member member) {
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .role(member.getRole().getRoleType().name())
                .build();
    }

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
