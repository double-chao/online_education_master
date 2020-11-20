package com.lcc.servicebase.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: 一十六
 * @Description: 校验输出的值是否合理
 * @Date: 2020/6/13 12:23
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {

    private Set<Integer> set = new HashSet<>();

    /**
     * 把指定的值放入到一个set容器中
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] values = constraintAnnotation.values();
        if (values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                set.add(values[i]);
            }
        }
    }

    /**
     * @param value   需要校验的值，是否在指定的值之中
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);
    }
}
