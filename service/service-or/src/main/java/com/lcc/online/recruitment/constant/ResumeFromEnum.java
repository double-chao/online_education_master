package com.lcc.online.recruitment.constant;


/**
 * @Author Administrator
 * @Description 简历来源枚举
 * @Date 2021/5/23  13:03
 */
public enum ResumeFromEnum {
    NET_WORK(NumberConstant.ONE, "网上招聘"),
    SUPPLIER(NumberConstant.TWO, "供应商"),
    PUSH(NumberConstant.THREE, "内推");

    private final Byte code;

    private final String name;

    ResumeFromEnum(Byte code, String name) {
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
        for (ResumeFromEnum sexEnum : values()) {
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
        for (ResumeFromEnum sexEnum : values()) {
            if (sexEnum.getName().equals(name)) {
                return sexEnum.getCode();
            }
        }
        return null;
    }

}
