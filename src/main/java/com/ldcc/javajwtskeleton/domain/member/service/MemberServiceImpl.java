package com.ldcc.javajwtskeleton.domain.member.service;

import com.ldcc.javajwtskeleton.domain.member.dto.JwtToken;
import com.ldcc.javajwtskeleton.domain.member.dto.SignUpUserRequest;
import com.ldcc.javajwtskeleton.domain.member.entity.Member;
import com.ldcc.javajwtskeleton.domain.member.repository.MemberRepository;
import com.ldcc.javajwtskeleton.global.config.security.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public Member saveMember(SignUpUserRequest dto) {
        return null;
    }

    @Transactional
    @Override
    public JwtToken signIn(String username, String password) {
        // username + password 기반 authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        // 실제 검증 -> authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication auth = this.authenticationManagerBuilder.getObject().authenticate(authToken);

        JwtToken jwtToken = jwtTokenProvider.createToken(auth);

        return jwtToken;
    }
}
