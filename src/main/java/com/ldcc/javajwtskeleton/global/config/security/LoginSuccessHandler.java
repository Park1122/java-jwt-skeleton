package com.ldcc.javajwtskeleton.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldcc.javajwtskeleton.domain.member.dto.Token;
import com.ldcc.javajwtskeleton.domain.member.dto.auth.LoginSuccessResponse;
import com.ldcc.javajwtskeleton.domain.member.dto.auth.CustomUserDetails;
import com.ldcc.javajwtskeleton.global.config.security.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(readOnly = true)
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Token token = jwtTokenProvider.createToken(authentication);
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        LoginSuccessResponse loginSuccessResponse = LoginSuccessResponse.of(token, userDetails.getMember());

        String res = objectMapper.writeValueAsString(loginSuccessResponse);

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(res);
    }
}
