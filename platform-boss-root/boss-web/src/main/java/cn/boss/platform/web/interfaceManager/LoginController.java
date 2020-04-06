package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bll.InterfaceManager.PeopleruleBll;
import cn.boss.platform.bll.toolsManager.UserInfoBll;
import cn.boss.platform.bll.util.DealStrSub;
import cn.boss.platform.service.interfaceManager.LoginService;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.LoginForm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by admin on 2019/3/25.
 */
@Controller
@RequestMapping("/boss/login")
public class LoginController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(InterfaceController.class);

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserInfoBll userInfoBll;

    /**
     * 登陆post请求
     * @return
     */
    @Resource
    private PeopleruleBll peopleruleBll;
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> login(@RequestParam("password") String password, @RequestParam("email") String email) {
        logger.info("login POST 方法被调用, email:" + email + "  password:"+ password);
        LoginForm login = new LoginForm();
        try {
            if (StringUtils.isBlank(email)) {
                return ResultBean.failed("账号不能为空！");
            }
            if (StringUtils.isBlank(password)) {
                return ResultBean.failed("密码不能为空！");
            }

            LoginService loginService = new LoginService();
            loginService.LDAP_connect();
            boolean loginStatus = loginService.authenricate(email,password);
            if (loginStatus == false){
                return ResultBean.failed("账号和密码不匹配！");
            } else{
                String userInfo = loginService.getUserinfo(email);
//                session.setAttribute("email",email);
//                session.setMaxInactiveInterval(30*60);//以秒为单位，即在没有活动30分钟后，session将失效
                login.setEmail(email);
                //获取姓名
                String auth = peopleruleBll.searchweburlbyemail1(email);
                String rgex = "sn=sn: (.*?), loginshell";
                login.setName(DealStrSub.getSubUtilSimple(userInfo, rgex));
                login.setToken("admin");
                login.setauth(auth);
                String rgexNumber = "uidnumber=uidNumber: (.*?), gidnumber";
                login.setUid(DealStrSub.getSubUtilSimple(userInfo, rgexNumber));
                return ResultBean.success("登陆成功!",login);
            }
        } catch (Exception e) {
            logger.error("账号登陆异常：{}", e);
            return ResultBean.failed("账号登陆失败!");
        }
    }


}
