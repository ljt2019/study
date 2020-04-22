package com.tiger.starter;

import com.tiger.starter.configuration.TigerProperties;
import com.tiger.starter.format.FormatProcessor;

/**
 * 对外提供的格式模板类
 * 格式化模板，根据条件选择对应的模板
 */
public class TigerFormatTemplate {
    //格式处理器
    private FormatProcessor formatProcessor;
    //配置文件属性
    private TigerProperties tigerProperties;

    public TigerFormatTemplate(TigerProperties tigerProperties, FormatProcessor formatProcessor) {
        this.tigerProperties = tigerProperties;
        this.formatProcessor = formatProcessor;
    }

    /**
     * 根据传入的类型，自动对其格式化
     *
     * @param obj
     * @param <T>
     * @return
     */
    public <T> String doFormat(T obj) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("====== 开始格式化 ======").append("<br/>");
        stringBuilder.append("====== 读取配置文件信息 ======info:【").append(formatProcessor.format(tigerProperties.getInfo())).append("】");
        stringBuilder.append("--name:【").append(formatProcessor.format(tigerProperties.getName())).append("】<br/>");
        stringBuilder.append("====== 格式结果【").append(formatProcessor.format(obj)).append("】<br/>");
        return stringBuilder.toString();

    }
}
