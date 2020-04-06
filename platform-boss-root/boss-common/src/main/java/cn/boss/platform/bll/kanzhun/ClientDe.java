/*
 * Copyright 2016 kanzhun.com All right reserved. This software is the
 * confidential and proprietary information of kanzhun.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kanzhun.com.
 */
package cn.boss.platform.bll.kanzhun;

import java.util.HashMap;
import java.util.Map;

/**
 * @date 2016年3月31日
 * @author daibiao
 */
public enum ClientDe {
    ANDROID("kanzhun-android", "6000ndy", new RC4MoibleDe()), // android-0.9
    IOS("kanzhun-ios", "5039bdo", new RC4MoibleDe()),
    KZ_WALL("kanzhun-ios", "5039bdo", new RC4MoibleDe()),
    // iso-0.9
    ;

    private static Map<String, ClientDe> map = new HashMap<String, ClientDe>();
    private String identify;
    private String ck;
    private MobileDe mobileDe;
    private boolean isDebug;

    private ClientDe(String identify, String ck, MobileDe mobileDe) {
        this.identify = identify;
        this.ck = ck;
        this.mobileDe = mobileDe;
        this.isDebug = false;
    }

    private ClientDe(String identify, String ck, MobileDe mobileDe, boolean isDebug) {
        this.identify = identify;
        this.ck = ck;
        this.mobileDe = mobileDe;
        this.isDebug = isDebug;
    }

    static {
        for (ClientDe de : ClientDe.values()) {
            map.put(de.getIdentify(), de);
        }
    }

    public String decode(String s, String key) {
        return mobileDe.decode(s, key);
    }

    public String encode(String s, String key) {
        return mobileDe.encode(s, key);
    }

    public static ClientDe getDe(String id) {
        return map.get(id);
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getCk() {
        return ck;
    }

    public void setCk(String ck) {
        this.ck = ck;
    }

    public MobileDe getMobileDe() {
        return mobileDe;
    }

    public void setMobileDe(MobileDe mobileDe) {
        this.mobileDe = mobileDe;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }
}
