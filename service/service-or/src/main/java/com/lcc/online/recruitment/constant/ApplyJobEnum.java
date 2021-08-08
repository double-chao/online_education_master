package com.lcc.online.recruitment.constant;


/**
 * @Author Administrator
 * @Description 应聘岗位枚举
 * @Date 2021/5/23  13:03
 */
public enum ApplyJobEnum {
    JAVA(NumberConstant.ONE, "java工程师"),
    ANDROID(NumberConstant.TWO, "安卓工程师");

    private final Byte code;

    private final String name;

    ApplyJobEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public Byte getCode() {
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
    public static String getNameByCode(Byte code) {
        for (ApplyJobEnum sexEnum : values()) {
            if (sexEnum.getCode().equals(code)) {
                return sexEnum.getName();
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
    public static Byte getCodeByName(String name) {
        for (ApplyJobEnum sexEnum : values()) {
            if (sexEnum.getName().equals(name)) {
                return sexEnum.getCode();
            }
        }
        return null;
    }

}
