package com.lcc.eduservice.constant;


/**
 * @Author Administrator
 * @Description 讲师头衔枚举
 * @Date 2021/5/23  13:03
 */
public enum TeacherLevelEnum {
    SENIOR_TEACHER(NumberConstant.ONE, "高级讲师"),
    CHIEF_TEACHER(NumberConstant.TWO, "首席讲师");

    private final Integer code;

    private final String name;

    TeacherLevelEnum(Integer code, String name) {
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
        for (TeacherLevelEnum teacherEnum : values()) {
            if (teacherEnum.getCode().equals(code)) {
                return teacherEnum.getName();
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
        for (TeacherLevelEnum teacherEnum : values()) {
            if (teacherEnum.getName().equals(name)) {
                return teacherEnum.getCode();
            }
        }
        return null;
    }

}
