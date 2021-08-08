package com.lcc.online.recruitment.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 简历编号工具类
 *
 * @author chaochao
 * @since 1.0
 */
public class ResumeCodeUtil {
    private static final String PREFIX = "OR";
    /**
     * 生成随机简历编号
     *
     * @return 简历编号
     */
    public static String getResumeCode() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss"); // 线程安全，代替 SimpleDateFormat
        LocalDateTime localDateTime = LocalDateTime.now(); // 使用Instant 代替 Date，LocalDateTime 代替 Calendar
        String newDate = formatter.format(localDateTime);
        StringBuilder stringBuilder = new StringBuilder();
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        for (int i = 0; i < 3; i++) {
            stringBuilder.append(threadLocalRandom.nextInt(10));
        }
        return PREFIX + newDate + stringBuilder.toString();
    }
}
