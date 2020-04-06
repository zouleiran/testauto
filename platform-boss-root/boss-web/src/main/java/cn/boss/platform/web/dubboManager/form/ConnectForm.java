package cn.boss.platform.web.dubboManager.form;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by admin on 2018/12/7.
 */
public class ConnectForm {
    /**
     * ip and port.
     */
    @NotBlank(message = "连接地址不能为空！")
    private String conn;
    /**
     * interface name;
     */
    private String serviceName;
    /**
     * the provider cache key.
     */
    private String providerKey;
    /**
     * method key.
     */
    private String methodKey;
    /**
     * method name.
     */
    private String methodName;
    /**
     * method params.
     */
    private String json;
    /**
     * timeout of waiting for result.
     */
    private int timeout;
    @NotBlank(message = "telent命令不能为空！")
    private String telnetKey;

    public String getTelnetKey() {
        return telnetKey;
    }

    public void setTelnetKey(String telnetKey) {
        this.telnetKey = telnetKey;
    }

    public String getConn() {
        return conn;
    }

    public void setConn(String conn) {
        this.conn = conn;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getProviderKey() {
        return providerKey;
    }

    public void setProviderKey(String providerKey) {
        this.providerKey = providerKey;
    }

    public String getMethodKey() {
        return methodKey;
    }

    public void setMethodKey(String methodKey) {
        this.methodKey = methodKey;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
