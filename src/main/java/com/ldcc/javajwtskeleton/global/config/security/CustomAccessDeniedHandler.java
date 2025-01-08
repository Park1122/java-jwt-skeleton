package com.ldcc.javajwtskeleton.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldcc.javajwtskeleton.global.exceptions.ExceptionMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.debug("[CustomAccessDeniedHandler] ", accessDeniedException);
        String res = objectMapper.writeValueAsString(new ExceptionMessage("인가 과정에서 문제가 발생했습니다. 권한이 있는지 확인해주세요"));

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(res);
    }
}
