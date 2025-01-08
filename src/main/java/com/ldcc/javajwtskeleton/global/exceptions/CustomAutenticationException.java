package com.ldcc.javajwtskeleton.global.exceptions;

public class CustomAutenticationException extends RuntimeException {
    public CustomAutenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CustomAutenticationException(String msg) {
        super(msg);
    }
}
