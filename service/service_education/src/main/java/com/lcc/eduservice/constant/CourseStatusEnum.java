package com.lcc.eduservice.constant;


/**
 * @Author Administrator
 * @Description 课程状态枚举
 * @Date 2021/5/23  13:03
 */
public enum CourseStatusEnum {
    NOT_RELEASE(NumberConstant.ZERO, "未发布"),
    PUBLISHED(NumberConstant.ONE, "已发布");

    private final Integer code;

    private final String name;

    CourseStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据code获取name值
     *
     * @param code code
     * @return name
     */
    public static String getNameByCode(Integer code) {
        for (CourseStatusEnum courseEnum : values()) {
            if (courseEnum.getCode().equals(code)) {
                return courseEnum.getName();
            }
        }
        return null;
    }

    /**
     * 根据name值获取code
     *
     * @param name name
     * @return code
     */
    public static Integer getCodeByName(String name) {
        for (CourseStatusEnum courseEnum : values()) {
            if (courseEnum.getName().equals(name)) {
                return courseEnum.getCode();
            }
        }
        return null;
    }

}
