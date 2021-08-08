package com.lcc.online.recruitment.constant;


/**
 * @Author Administrator
 * @Description 应聘岗位职级枚举
 * @Date 2021/5/23  13:03
 */
public enum ApplyLevelEnum {
    P1(NumberConstant.ONE, "初级"),
    P2(NumberConstant.TWO, "中级"),
    P3(NumberConstant.THREE, "高级"),
    P4(NumberConstant.FOUR, "资深"),
    P5(NumberConstant.FIVE, "专家");

    private final Byte code;

    private final String name;

    ApplyLevelEnum(Byte code, String name) {
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
        for (ApplyLevelEnum sexEnum : values()) {
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
        for (ApplyLevelEnum sexEnum : values()) {
            if (sexEnum.getName().equals(name)) {
                return sexEnum.getCode();
            }
        }
        return null;
    }

}
