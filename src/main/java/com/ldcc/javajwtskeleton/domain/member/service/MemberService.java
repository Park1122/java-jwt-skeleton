package com.ldcc.javajwtskeleton.domain.member.service;

import com.ldcc.javajwtskeleton.domain.member.dto.SignUpUserRequest;
import com.ldcc.javajwtskeleton.domain.member.entity.Member;
import com.ldcc.javajwtskeleton.domain.member.entity.Role;
import com.ldcc.javajwtskeleton.domain.member.entity.MemberRoleType;
import com.ldcc.javajwtskeleton.domain.member.repository.MemberRepository;
import com.ldcc.javajwtskeleton.domain.member.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(SignUpUserRequest request) {
        if (memberRepository.existsByMemberId(request.getMemberId())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

//        if(memberRepository.existsByEmail(request.getEmail())) {
//            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
//        }

        // TODO 이메일 인증코드 전송

        Role role = roleRepository.findByRoleType(MemberRoleType.USER).orElseThrow(() -> new EntityNotFoundException("역할이 존재하지 않습니다."));
        Member member = Member.builder()
                .memberId(request.getMemberId())
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        memberRepository.save(member);
    }
}
