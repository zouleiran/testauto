package cn.boss.platform.web.toolsManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.service.tools.PassPortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.techwolf.boss.utils.security.AccessGeekInfoUtils;
/**
 * Created by admin on 2018/8/23.
 */
@Controller
@RequestMapping("/boss/pass")
public class PassPortController {

    private static final Logger logger = LoggerFactory.getLogger(PassPortController.class);

    @Autowired
    private PassPortService passPortService;

    @RequestMapping(value = "/encrypt", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> encrypt(@RequestParam("phone") String phone)  {
        try {
            return ResultBean.success("手机号加密成功！", passPortService.encrypt(phone));
        }catch (Exception e) {
            logger.error("手机号加密异常：{}", e);
            return ResultBean.failed("手机号加密失败！");
        }
    }

    @RequestMapping(value = "/decrypt", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> decrypt(@RequestParam("phone") String phone)  {
        try {
            return ResultBean.success("手机号解密成功！", passPortService.decrypt(phone));
        }catch (Exception e) {
            logger.error("手机号解密异常：{}", e);
            return ResultBean.failed("手机号解密失败！");
        }
    }

    /**
     * boss开聊时securityId参数生成
     * @param geekId
     * @param expectId
     * @param jobId
     * @return
     */
    @RequestMapping(value = "/getSecurityId", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getSecurityId(@RequestParam("geekId") Long geekId,@RequestParam("expectId") Long expectId,@RequestParam("jobId") Long jobId)  {
        try {
            String securityId = AccessGeekInfoUtils.getSecurityId(geekId,expectId,1,"0",true,true,jobId);
            return ResultBean.success("获取成功！", securityId);
        }catch (Exception e) {
            logger.error("获取失败：{}", e);
            return ResultBean.failed("获取失败！");
        }
    }

    public static void main(String[] args) {
        String securityId = AccessGeekInfoUtils.getSecurityId(6457734,92006948,1,"0",true,true,1041094763);
        System.out.println(securityId);
    }

}
