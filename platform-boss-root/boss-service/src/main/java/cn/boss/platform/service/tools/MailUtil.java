package cn.boss.platform.service.tools;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2018/4/18.
 */
@Service
public class MailUtil {

    @Autowired
    @Qualifier("mailSender")
    private MailSender mailSender;

    @Autowired
    @Qualifier("simpleMailMessage")
    private SimpleMailMessage simpleMailMessage;

    /**
     * 单发
     * @param recipient 收件人
     * @param subject 主题
     * @param content 内容
     */
    public void send(String recipient,String subject,String content){
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        mailSender.send(simpleMailMessage);
    }

    /**
     * 群发
     * @param recipients 收件人
     * @param subject 主题
     * @param content 内容
     */
    public void send(List<String> recipients,String subject,String content){
        simpleMailMessage.setTo(recipients.toArray(new String[recipients.size()]));
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        mailSender.send(simpleMailMessage);
    }

    public static String getVerifyReqCountDate10Key(String phone) {
        return String.format("vcc_%s_%s", phone, DateFormatUtils.format(new Date(), "yyyyMMdd"));
    }

}