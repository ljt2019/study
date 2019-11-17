package com.tiger.enums;

import java.util.Objects;

/**
 * XWPF 表格边框样式
 *
 * @author: tiger
 * @create: 2019-11-02 12:20
 */
public enum XWPFBorderStyleEnum {
    //细实线
    single(0, "single"),
    //厚实线
    thick(1, "thick"),
    //虚线
    dashed(2, "dashed"),
    //细虚线
    dotted(3, "dotted"),
    //双线
    double1(4, "double"),
    //波动线
    wave(5, "wave"),
    //无线
    center(6, "center"),

    ;

    private Integer code;
    private String  msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private XWPFBorderStyleEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static XWPFBorderStyleEnum getByCode(Integer code) {
        if (Objects.nonNull(code)) {
            for (XWPFBorderStyleEnum value : XWPFBorderStyleEnum.values()) {
                if (value.code.equals(code)) {
                    return value;
                }
            }
        }
        return null;
    }
}
