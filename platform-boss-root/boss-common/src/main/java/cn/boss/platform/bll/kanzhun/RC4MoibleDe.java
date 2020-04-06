package cn.boss.platform.bll.kanzhun;


/**
 * RC4加密解密
 * Descriptions of the class DefaultMoibleDe.java's implementation：TODO described the implementation of class
 * @date 2016年4月6日
 * @author liyouting
 */
public class RC4MoibleDe implements MobileDe {

    @Override
    public String decode(String s, String key) {
        return SecurityUtils.rc4Decrypt(s, key);
    }

    @Override
    public String encode(String s, String key) {
        return SecurityUtils.rc4Encrypt(s, key);
    }

}
