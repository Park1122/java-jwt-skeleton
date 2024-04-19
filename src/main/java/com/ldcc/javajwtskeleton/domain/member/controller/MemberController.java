package com.ldcc.javajwtskeleton.domain.member.controller;

import com.ldcc.javajwtskeleton.domain.member.dto.SignUpUserRequest;
import com.ldcc.javajwtskeleton.domain.member.dto.SignUpUserResponse;
import com.ldcc.javajwtskeleton.domain.member.entity.Member;
import com.ldcc.javajwtskeleton.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpUserResponse> signup(@RequestBody SignUpUserRequest dto) {
        Member member = this.memberService.saveMember(dto);
        return ResponseEntity.ok(SignUpUserResponse.builder()
                .memberId(member.getMemberId())
                .message("SignUp Success")
                .build());
    }
}
