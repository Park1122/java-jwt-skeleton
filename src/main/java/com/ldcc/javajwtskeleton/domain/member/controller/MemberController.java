package com.ldcc.javajwtskeleton.domain.member.controller;

import com.ldcc.javajwtskeleton.domain.member.dto.JwtToken;
import com.ldcc.javajwtskeleton.domain.member.dto.SignInDto;
import com.ldcc.javajwtskeleton.domain.member.dto.SignUpUserRequest;
import com.ldcc.javajwtskeleton.domain.member.dto.SignUpUserResponse;
import com.ldcc.javajwtskeleton.domain.member.entity.Member;
import com.ldcc.javajwtskeleton.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member Controller", description = "사용자 관련 API")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpUserResponse> signup(@RequestBody SignUpUserRequest dto) {
        Member member = this.memberService.saveMember(dto);
        return ResponseEntity.ok(SignUpUserResponse.builder()
                .memberId(member.getMemberId())
                .message("SignUp Success")
                .build());
    }

    @Operation(summary = "로그인", description = "로그인 시도후, 성공시 JWT 토큰 반환")
    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignInDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        return this.memberService.signIn(username, password);
    }

    @GetMapping("/api/v1/user/test")
    public String test() {
        return "test";
    }
}
