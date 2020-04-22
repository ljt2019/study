package com.tiger.starter.format;

/**
 * 格式化接口规范
 */
public interface FormatProcessor {

    //定义一个格式化的方法
    <T> String format(T obj);
}
