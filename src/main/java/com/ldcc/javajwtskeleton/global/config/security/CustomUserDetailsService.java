package com.ldcc.javajwtskeleton.global.config.security;

import com.ldcc.javajwtskeleton.domain.member.entity.Member;
import com.ldcc.javajwtskeleton.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new CustomUserDetails(this.memberRepository.findByMemberId(username).orElseThrow(NoSuchElementException::new));
//    }

    /*
    loadUserByUsername, createUserDetails : TEST용 메소드
    DB에 비밀번호를 인코딩하지 않고 테스트
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = this.memberRepository.findByMemberId(username).orElseThrow(NoSuchElementException::new);
        log.info("Member ID: {}", member.getMemberId());
        log.info("Member Password: {}", member.getPassword());
        log.info("Member Role: {}", member.getRole().toString());

        UserDetails u = this.createUserDetails(member);
        log.info("u name: {}", u.getUsername());
        log.info("u pw: {}", u.getPassword());

        return u;
    }

    private UserDetails createUserDetails(Member member) {
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        return User.builder()
                .username(member.getMemberId())
                .password(this.passwordEncoder.encode(member.getPassword()))
                .roles(member.getRole().toString())
                .build();
    }

}
