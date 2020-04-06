package cn.boss.platform.web.toolsManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.service.tools.MailUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/4/18.
 */
@Controller
@RequestMapping("/boss/mail")
public class SendMailController {

    private static final Logger logger = LoggerFactory.getLogger(SendMailController.class);

    @Autowired
    private MailUtil mailUtil;

    /**
     *
     * 发送邮件
     * @param mail
     * @param url
     * @return
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> sendMail(@RequestParam("mail") String mail, @RequestParam("url") String url)  {
        try {
            logger.info("/mail/send POST 方法被调用!!");
            String content = "接口测试报告："+url ;
            List<String> recipients=new ArrayList<String>();
            String[] strarray=mail.split(";");
            for(int i = 0; i < strarray.length; i++){
                if(!StringUtils.isBlank(strarray[i])){
                    recipients.add(strarray[i]);
                }
            }
            mailUtil.send(recipients, "接口测试报告", content);
            return ResultBean.success("发送报告成功！", "");
        }catch (Exception e) {
            logger.error("报告发送异常：{}", e);
            return ResultBean.failed("发送接口报告失败！");
        }
    }
}
