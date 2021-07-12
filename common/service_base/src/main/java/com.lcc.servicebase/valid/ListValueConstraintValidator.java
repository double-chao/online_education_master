package com.lcc.servicebase.valid;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author Administrator
 * @Description 自定义校验器：输入的数字是否合理
 * @Date 2021/6/6  18:21
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {

    private final Set<Integer> set = new HashSet<>();

    /**
     * 把指定的值放入到一个set容器中
     *
     * @param constraintAnnotation 校验的内容
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] values = constraintAnnotation.values();
        if (values.length > 0) {
            for (int value : values) {
                set.add(value);
            }
        }
    }

    /**
     * @param value   需要校验的值，是否在指定的值之中
     * @param context 上下文
     * @return boolean
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (ObjectUtils.isEmpty(value)) {
            return true;
        }
        return set.contains(value);
    }
}
