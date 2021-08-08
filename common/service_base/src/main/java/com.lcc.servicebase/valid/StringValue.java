package com.lcc.servicebase.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author Administrator
 * @Description 自定义的注解：字符换串输入的值校验
 * @Date 2021/6/6  18:23
 */
@Documented
@Constraint(validatedBy = {StringValueConstraintValidator.class}) //指定校验结果
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface StringValue {

    String message() default "{com.lcc.servicebase.valid.StringValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] values() default {};
}
