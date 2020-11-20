package com.lcc.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

@Data
@AllArgsConstructor  //生成有参数构造方法
@NoArgsConstructor   //生成无参数构造
public class BadException extends RuntimeException {
    private Integer code;//状态码
    private String msg;//异常信息

    private Integer status = BAD_REQUEST.value();

    public BadException(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public BadException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }
}
