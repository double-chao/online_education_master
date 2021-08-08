package com.lcc.online.recruitment.constant;


/**
 * @Author Administrator
 * @Description 性别枚举
 * @Date 2021/5/23  13:03
 */
public enum SexEnum {
    MAN(NumberConstant.ZERO, "男"),
    WOMEN(NumberConstant.ONE, "女"),
    UNKNOWN(NumberConstant.TWO, "保密");

    private final Byte code;

    private final String name;

    SexEnum(Byte code, String name) {
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
        for (SexEnum sexEnum : values()) {
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
        for (SexEnum sexEnum : values()) {
            if (sexEnum.getName().equals(name)) {
                return sexEnum.getCode();
            }
        }
        return null;
    }

}
