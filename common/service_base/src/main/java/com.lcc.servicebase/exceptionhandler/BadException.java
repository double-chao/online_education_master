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

    public BadException(){}

    private CodeEnum codeEnum;
    private Integer status = BAD_REQUEST.value();

    public BadException(CodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }

    public BadException(HttpStatus status, String msg) {
        super(msg);
        this.status = status.value();
    }
}
