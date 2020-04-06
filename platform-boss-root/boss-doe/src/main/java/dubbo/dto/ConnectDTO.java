/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package dubbo.dto;


import javax.validation.constraints.NotBlank;

public class ConnectDTO extends BaseDTO {

    /**
     * ip and port.
     */
    @NotBlank(message = "服务地址 不能为空！")
    private String conn;
    /**
     * interface name;
     */
    @NotBlank(message = "接口名 不能为空！")
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
    @NotBlank(message = "方法名 不能为空！")
    private String methodName;
    /**
     * method params.
     */
    private String json;
    /**
     * timeout of waiting for result.
     */
    private int timeout;
    /**
     * interface version number, eg: 1.0.0
     */
    private String version;
    /**
     * the group of interface, eg: mmcgroup
     */
    private String group;
    private String expectedResult;

    @Override
    public String toString() {
        return "ConnectDTO{" +
                "conn='" + conn + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", providerKey='" + providerKey + '\'' +
                ", methodKey='" + methodKey + '\'' +
                ", methodName='" + methodName + '\'' +
                ", json='" + json + '\'' +
                ", timeout=" + timeout +
                ", version='" + version + '\'' +
                ", group='" + group + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                '}';
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
