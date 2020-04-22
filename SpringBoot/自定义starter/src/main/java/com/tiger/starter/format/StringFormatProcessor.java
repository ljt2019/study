package com.tiger.starter.format;

import java.util.Objects;

/**
 * 编写 String 格式处理器
 */
public class StringFormatProcessor implements FormatProcessor {

    @Override
    public <T> String format(T obj) {
        return "StringFormatProcessor:" + Objects.toString(obj);
    }
}
