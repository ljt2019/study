package com.tiger.starter.format;

import com.alibaba.fastjson.JSON;

/**
 * 编写 json 格式处理器
 */
public class JsonFormatProcessor implements FormatProcessor {

    @Override
    public <T> String format(T obj) {
        return "JsonFormatProcessor:" + JSON.toJSONString(obj);
    }
}
