package cn.boss.platform.bll.util;

import cn.boss.platform.bll.boss.Base24;
import cn.boss.platform.bll.boss.Base64;
import cn.boss.platform.bll.boss.RC4;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.UnsupportedEncodingException;

/**
 * 鍔犲瘑宸ュ叿绫�
 *
 * @author wwj <wangweijie@techwolf.cn>
 * @ClassName: SecurityUtils
 * @date 2014骞�2鏈�10鏃� 涓嬪崍2:59:26
 */
public class SecurityUtils {
    private final static Log logger = LogFactory.getLog(SecurityUtils.class);
    private static final String DEFAULT_CHARSET = "UTF-8";
    //娉ㄦ剰锛氳涓嶈闅忔剰淇敼褰撳墠Key锛孖OS鍚屾牱渚濊禆褰撳墠key杩涜瑙ｅ瘑
    private static final String RC4_DEFAULT_PASSWORD = "3984aF333#@213";





    /**
     * 鍔犲瘑
     * 鍔犲瘑瀛楃涓插拰鍘熷瓧绗︿覆闀垮害鍩烘湰鐩稿悓
     * 涓昏閽堝椤甸潰ID鐨勫姞瀵�
     *
     * @param source
     * @return
     */
    public static String rc4Encrypt(Object source) {
        return rc4Encrypt(source, RC4_DEFAULT_PASSWORD);
    }

    public static String rc4Encrypt(Object source, String rc4Password) {
        String encrypt = "";
        try {
            encrypt = Base64.encode(RC4.RC4encrypt(rc4Password.getBytes(DEFAULT_CHARSET),
                    String.valueOf(source).getBytes(DEFAULT_CHARSET)));
        } catch (UnsupportedEncodingException e) {
            logger.error("SecurityUtils.rc4Encrypt(" + source + ", " + rc4Password + ")", e);
        }
        return encrypt.replaceAll("/", "_").replaceAll("\\+", "-").replaceAll("=", "~");
    }

    /**
     * 瑙ｅ瘑
     * 鍔犲瘑瀛楃涓插拰鍘熷瓧绗︿覆闀垮害鍩烘湰鐩稿悓
     * 涓昏閽堝椤甸潰ID鐨勫姞瀵�
     *
     * @return
     */
    public static String rc4Decrypt(String encryptStr) {
        return rc4Decrypt(encryptStr, RC4_DEFAULT_PASSWORD);
    }

    public static String rc4Decrypt(String encryptStr, String rc4Password) {
        String decrypt = "";
        try {
            encryptStr = encryptStr.replaceAll("_", "/").replaceAll("-", "+").replaceAll("\\~", "=");
            System.out.println("结果:"+encryptStr);
            byte[] keyBytes = rc4Password.getBytes(DEFAULT_CHARSET);
            byte[] plainDataBytes = Base64.decode(encryptStr);
            if (plainDataBytes == null) {
                return decrypt;
            }

            byte[] bytes = RC4.RC4encrypt(keyBytes, plainDataBytes);
            decrypt = new String(bytes, DEFAULT_CHARSET);
            System.out.println(decrypt);
        } catch (UnsupportedEncodingException e) {
            logger.error("SecurityUtils.rc4Decrypt(" + encryptStr + ", " + rc4Password + ")", e);
        }
        return decrypt;
    }

    /**
     * public static void main(String[] args) {
     * String plainText = "54";
     * String s = encryptRC4(plainText);
     * System.out.println("Bing Go: " + encryptRC4(plainText));
     * System.out.println("---Bing Go: " + decryptRC4(s));
     * }
     */

    public static String rc4EncryptUseBase24(String source, String rc4Password) {
        return Base24.encode(rc4Encrypt(source, rc4Password));
    }

    public static String rc4DecryptUseBase24(String encryptStr, String rc4Password) {
        String decodeStr = Base24.decode(encryptStr);
        if (StringUtils.isNotEmpty(decodeStr)) {
            return rc4Decrypt(decodeStr, rc4Password);
        }
        return "";
    }

    /**
     * boss端id加密
     * @param plainText
     * @return
     */
    public static String encryptString(String plainText) {
        try {
            String encryptString = SecurityUtils.rc4Encrypt(plainText);
            encryptString = StringUtils.substring(DigestUtils.md5Hex(encryptString), 8, 24) + encryptString;
            return encryptString;
        } catch (Throwable var2) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(SecurityUtils.rc4Encrypt("123456c", "9f738a3934abf88b1dca8e8092043fbd"));
    }

}
