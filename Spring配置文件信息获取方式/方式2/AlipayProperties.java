package cn.healthmall.sail.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

//获取配置文件中的信息类
@ConfigurationProperties(prefix = AlipayProperties.ALIPAY_PREFIX)
public class AlipayProperties {
    public static final String ALIPAY_PREFIX = "pay.alipay";

    private String serverUrl;
    private String appId;
    private String appPrivateKey;
    private String alipayPublicKey;
    private String notifyUrl;

    private String returnUrl520;

    private String returnUrl;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public void setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl520() {
        return returnUrl520;
    }

    public void setReturnUrl520(String returnUrl520) {
        this.returnUrl520 = returnUrl520;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
