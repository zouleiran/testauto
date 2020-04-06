/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package dubbo.model;



public class RegistryModel {

    private String registryKey;
    private String registryDesc;

    public String getRegistryKey() {
        return registryKey;
    }

    public void setRegistryKey(String registryKey) {
        this.registryKey = registryKey;
    }

    public String getRegistryDesc() {
        return registryDesc;
    }

    public void setRegistryDesc(String registryDesc) {
        this.registryDesc = registryDesc;
    }
}
