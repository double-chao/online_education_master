package com.lcc.servicebase.exceptionhandler;

import com.lcc.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 统一异常处理
 * </p>
 *
 * @author chaochao
 * @since 2020-05-25
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail().message("出现异常了");
    }

    //自定义异常执行这个方法
    @ExceptionHandler(BadException.class)
    @ResponseBody //为了返回数据
    public Result error(BadException e) {
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }

}
