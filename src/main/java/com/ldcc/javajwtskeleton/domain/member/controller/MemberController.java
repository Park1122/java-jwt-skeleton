package com.ldcc.javajwtskeleton.domain.member.controller;

import com.ldcc.javajwtskeleton.domain.member.dto.SignUpUserRequest;
import com.ldcc.javajwtskeleton.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member Controller", description = "사용자 관련 API")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/v1/auth/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public void signup(@RequestBody @Valid SignUpUserRequest dto) {
        this.memberService.create(dto);
    }

}
