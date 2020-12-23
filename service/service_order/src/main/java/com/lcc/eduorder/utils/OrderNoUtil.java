package com.lcc.eduorder.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 订单号工具类
 *
 * @author chaochao
 * @since 1.0
 */
public class OrderNoUtil {
    /**
     * 生成随机订单号
     *
     * @return
     */
    public static String getOrderNo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss"); // 线程安全，代替 SimpleDateFormat
        LocalDateTime localDateTime = LocalDateTime.now(); // 使用Instant 代替 Date，LocalDateTime 代替 Calendar
        String newDate = formatter.format(localDateTime);
        StringBuilder stringBuilder = new StringBuilder();
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        for (int i = 0; i < 3; i++) {
            stringBuilder.append(threadLocalRandom.nextInt(10));
        }
        return newDate + stringBuilder.toString();
    }
}
