package com.lcc.servicebase.valid;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author Administrator
 * @Description 字符串自定义注解
 * @Date 2021/6/6  18:21
 */
public class StringValueConstraintValidator implements ConstraintValidator<StringValue, String> {

    private final Set<String> set = new HashSet<>();

    /**
     * 校验输入的值是否在values数组中
     *
     * @param value   输入的参数值
     * @param context 上下文
     * @return 包含返回true 不包含返回false
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) { // 当输入的值为空是，返回true
            return true;
        } else {
            return set.contains(value);
        }
    }

    /**
     * 把指定的值放入到一个set容器中,初始化自定义注解中的值
     *
     * @param constraintAnnotation 注解中values传入的值
     */
    @Override
    public void initialize(StringValue constraintAnnotation) {
        String[] values = constraintAnnotation.values();
        if (!ObjectUtils.isEmpty(values)) {
            set.addAll(Arrays.asList(values));
        }
    }
}
