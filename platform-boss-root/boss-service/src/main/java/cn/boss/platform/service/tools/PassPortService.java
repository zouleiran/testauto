package cn.boss.platform.service.tools;

import cn.techwolf.boss.utils.SecurityUtils;
import org.springframework.stereotype.Service;


/**
 * 手机号加密和解密
 * Created by admin on 2018/8/23.
 */
@Service
public class PassPortService {

    private final String ENCRYPTED_KEY = "rTu]BA+UE+I4!a[f";

    /**
     * 账号加密
     *
     * @param account
     * @return
     */
    public String encrypt(String account) {
        return SecurityUtils.rc4Encrypt(account, ENCRYPTED_KEY);
    }

    /**
     * 账号解密
     *
     * @param encryptAccount
     * @return
     */
    public String decrypt(String encryptAccount) {
        return SecurityUtils.rc4Decrypt(encryptAccount, ENCRYPTED_KEY);
    }

}



