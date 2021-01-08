package com.lcc.nomal;

import java.time.LocalDate;

/**
 * @Author Administrator
 * @Description 开发测试
 * @Date 2020/12/11  17:22
 */
public class DevloperTest {

    public static void main(String[] args) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate today = LocalDate.now();
//        String yesterday = formatter.format(today.plusDays(-1));
        String yesterday = LocalDate.now().plusDays(-1).toString();
        System.out.println(yesterday);
    }

}
