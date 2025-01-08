package com.ldcc.javajwtskeleton.domain.member.controller.common;

import com.ldcc.javajwtskeleton.global.exceptions.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage handleException(Exception ex) {
        log.debug("[handleException] ex = ", ex);
        return new ExceptionMessage(ex.getMessage());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, EntityNotFoundException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleIllegalArgumentException(IllegalArgumentException ex) {
        log.debug("[handleIllegalArgumentException] ex = ", ex);
        return new ExceptionMessage(ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionMessage handleAuthenticationException(AuthenticationException ex) {
        log.debug("[handleAuthenticationException] ex = ", ex);
        return new ExceptionMessage(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.debug("[MethodArgumentNotValidException] ex = ", ex);

        ObjectError error = ex.getBindingResult().getAllErrors().stream().findFirst().get();
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();

        return ResponseEntity.badRequest().body(new ExceptionMessage(String.format("%s 은(는) %s", fieldName, errorMessage)));
    }

}
