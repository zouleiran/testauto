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


import dubbo.model.MethodModel;

public class MethodModelDTO {

    /**
     * the name of interface which the method belong to.
     */
    private String interfaceName;
    /**
     * the cache key.
     */
    private String methodKey;
    /**
     * just only the method name.
     */
    private String methodName;
    /**
     * show on the web.
     */
    private String methodText;

    public MethodModelDTO() {

    }

    public MethodModelDTO(MethodModel model) {

        this.methodKey = model.getKey();
        this.methodName = model.getMethod().getName();
        this.methodText = model.getMethodText();
    }


    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
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

    public String getMethodText() {
        return methodText;
    }

    public void setMethodText(String methodText) {
        this.methodText = methodText;
    }
}
