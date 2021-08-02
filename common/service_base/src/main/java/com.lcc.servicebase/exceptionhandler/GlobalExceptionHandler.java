package com.lcc.servicebase.exceptionhandler;

import com.lcc.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 统一异常处理
 * </p>
 *
 * @author chaochao
 * @since 2020-05-25
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.lcc")
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result error(MethodArgumentNotValidException e) {
        log.error("数据校验出现异常" + e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors().forEach((items) -> {
            map.put(items.getField(), items.getDefaultMessage());
        });
        return Result.fail().code(CodeEnum.DATA_CHECK_EXCEPTION.getCode()).data("data", map);
    }

    //自定义异常执行这个方法
    @ExceptionHandler(BadException.class)
    public Result error(BadException e) {
        CodeEnum codeEnum = BadException.getCodeEnum();
        e.printStackTrace();
        return Result.fail().code(codeEnum.getCode()).message(codeEnum.getMsg());
    }

}
