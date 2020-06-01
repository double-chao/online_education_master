package com.lcc.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
