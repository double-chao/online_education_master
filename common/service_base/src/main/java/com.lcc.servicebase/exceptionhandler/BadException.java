package com.lcc.servicebase.exceptionhandler;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * <p>
 * 自定义异常
 * </p>
 *
 * @author chaochao
 * @since 2020-05-25
 */
public class BadException extends RuntimeException {

    public BadException() {}

    private static CodeEnum codeEnum;
    private Integer status = BAD_REQUEST.value();
    private int code;
    private String msg;

    public BadException(CodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }

    public BadException(HttpStatus status, String msg) {
        super(msg);
        this.status = status.value();
    }

    public BadException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public static CodeEnum getCodeEnum() {
        return codeEnum;
    }
}
