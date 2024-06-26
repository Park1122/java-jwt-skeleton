package com.ldcc.javajwtskeleton.domain.member.service;

import com.ldcc.javajwtskeleton.domain.member.dto.JwtToken;
import com.ldcc.javajwtskeleton.domain.member.dto.SignUpUserRequest;
import com.ldcc.javajwtskeleton.domain.member.entity.Member;

public interface MemberService {
    Member saveMember(SignUpUserRequest dto);

    JwtToken signIn(String username, String password);
}
