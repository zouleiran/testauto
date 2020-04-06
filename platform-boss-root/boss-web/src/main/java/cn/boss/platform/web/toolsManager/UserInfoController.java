package cn.boss.platform.web.toolsManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.toolsManager.UserInfoBean;
import cn.boss.platform.bll.toolsManager.AppInterfaceLogBll;
import cn.boss.platform.bll.toolsManager.UserInfoBll;
import cn.boss.platform.service.interfaceManager.ExecuteService;
import cn.boss.platform.service.tools.UserInfoService;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.DebugForm;
import com.alibaba.fastjson.JSON;
import com.jayway.restassured.response.Response;
import com.zhipin.passport.api.LoginInfoApi;
import com.zhipin.passport.model.LoginInfoBO;
import com.zhipin.service.common.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2018/7/16.
 */
@Controller
@RequestMapping("/boss")
public class UserInfoController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private LoginInfoApi loginInfoApi;
    @Autowired
    private LoginInfoApi loginInfoApiQa2;
    @Autowired
    private LoginInfoApi loginInfoApiQa3;
    @Autowired
    private LoginInfoApi loginInfoApiUnderLine;
    @Autowired
    private UserInfoBll userInfoBll;
    @Autowired
    private ExecuteService executeService;
    @Autowired
    private UserInfoService userInfoService;



    @RequestMapping(value = "/getByUserId", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getByUserId(@RequestParam("env") Integer env, @RequestParam("userId") long userId) {
        logger.info("/admin/getByUserId GET 方法被调用!!");
        try {
            Result<LoginInfoBO> result = null;
            if (env.equals(1)) {
                result = loginInfoApi.getByUserId(userId);
            } else if (env.equals(2)) {
                result = loginInfoApiQa2.getByUserId(userId);
            } else if (env.equals(3)) {
                result = loginInfoApiQa3.getByUserId(userId);
            } else if(env.equals(5)){
                result = loginInfoApiUnderLine.getByUserId(userId);
            }
            return ResultBean.success("获取成功！", result);
        } catch (Exception e) {
            logger.error("添加用例异常：{}", e);
            return ResultBean.failed("执行失败!");
        }
    }


    @RequestMapping(value = "/getByAccount", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getByAccount(@RequestParam("env") Integer env, @RequestParam("account") String account) {
        logger.info("/admin/getByAccount GET 方法被调用!!");
        try {
            Result<LoginInfoBO> result = null;
            if (env.equals(1)) {
                result = loginInfoApi.getByAccount(account);
            } else if (env.equals(2)) {
                result = loginInfoApiQa2.getByAccount(account);
            } else if (env.equals(3)) {
                result = loginInfoApiQa3.getByAccount(account);
            } else if(env.equals(5)){
                result = loginInfoApiUnderLine.getByAccount(account);
            }
            return ResultBean.success("获取成功！", result);
        } catch (Exception e) {
            logger.error("添加用例异常：{}", e);
            return ResultBean.failed("执行失败!");
        }
    }


    /**
     * 后台登陆，保存atw到数据库
     * @param phone
     * @param password
     * @param env
     * @param ip
     * @return
     */
    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> addUserInfo(@RequestParam("phone") String phone,@RequestParam("password") String password,@RequestParam("env") String env,@RequestParam("ip") String ip) {
        DebugForm debug = new DebugForm();
        try{
            String url = "http://" + ip + "/user/login.json";
            Map<String, Object> params = new HashMap<String, Object>() ;
            params.put("phone",phone);
            params.put("password",password);
            Response responseResult = executeService.send(url,params,null,"POST");
            logger.info(responseResult.getCookies().get("awt")+"");
            debug.setAllDebug(url,responseResult.getStatusCode(),"",responseResult.asString(),null,responseResult.getCookies(),"");
            UserInfoBean userInfoBean = userInfoBll.getUserInfo(phone,env);
            if(responseResult.statusCode() == 200){
                if(userInfoBean != null){
                    userInfoBean.setAwt(responseResult.getCookies().get("awt"));
                    userInfoBean.setUpdateTime(new Date());
                    userInfoBll.updateUserInfo(userInfoBean);
                    return ResultBean.success("更新成功！",debug);
                }else{
                    UserInfoBean bean = new UserInfoBean();
                    bean.setEnv(env);
                    bean.setPhone(phone);
                    bean.setPassword(password);
                    bean.setCreateTime(new Date());
                    bean.setUpdateTime(new Date());
                    bean.setAwt(responseResult.getCookies().get("awt"));
                    userInfoBll.addUerInfo(bean);
                    return ResultBean.success("添加成功！",debug);
                }
            }else{
                return ResultBean.failed("登录失败！",debug);
            }
        } catch (Exception e) {
            logger.error("登录失败：{}", e);
            return ResultBean.failed("登录失败!",debug);
        }
    }

    /**
     * 循环老接口登录
     * @return
     */
    @RequestMapping(value = "/oldadmin/update", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> updateUserInfo(){
        try{
            userInfoService.adminLogin();
            return ResultBean.success("更新成功！");
        }catch (Exception e) {
            logger.error("登录失败：{}", e);
            return ResultBean.failed("更新失败!");
        }

    }

    /**
     * 循环登录新接口登录
     * @return
     */
    @RequestMapping(value = "/newadmin/update", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> updateUserInfoByNewAdmin(){
        try{
            userInfoService.adminLoginNewByEnv("","new");
            return ResultBean.success("更新成功！");
        }catch (Exception e) {
            logger.error("登录失败：{}", e);
            return ResultBean.failed("更新失败!");
        }
    }


    /**
     * 模拟老后台请求
     * @param url
     * @param parameter
     * @param env
     * @param method
     * @return
     */
    @RequestMapping(value = "/admin/common", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> executeAdminInter(@RequestParam("url") String url,@RequestParam("parameter") String parameter,@RequestParam("env") String env,@RequestParam("method") String method) {
        try {

            String headers = "";
            Response responseResult = null;
            UserInfoBean userInfoBean = userInfoBll.getUserInfoBytype("", env,"old");
            if (userInfoBean == null) {
                return ResultBean.failed("请用户重新操作!");
            }
            Map<String, Object> paramerMap = JSON.parseObject(parameter);
            Map<String, Object> headerMap = new HashMap<>();
            //添加cookie
            headerMap.put("Cookie", "awt=" + userInfoBean.getAwt());
            responseResult = executeService.send(url, paramerMap, headerMap, method);
            if(responseResult.asString().contains("Boss直聘碉堡了")){
                //先登录
                boolean loginType = userInfoService.adminLoginByenv(env,"old");
                if (loginType == true) {
                    headerMap.put("Cookie", "awt=" + userInfoBean.getAwt());
                    responseResult = executeService.send(url, paramerMap, headerMap, method);
                    DebugForm debug = new DebugForm();
                    debug.setDebug(url, responseResult.getStatusCode(), responseResult.asString());
                    return ResultBean.success("接口调用成功！", debug);
                }else{
                    return ResultBean.failed("请求失败，请重新操作!");
                }
            }else{
                logger.info(responseResult.asString());
                DebugForm debug = new DebugForm();
                debug.setDebug(url, responseResult.getStatusCode(), responseResult.asString());
                return ResultBean.success("接口调用成功！", debug);
            }
        }catch (Exception e) {
            logger.error("请求失败：{}", e);
            return ResultBean.failed("请求失败!");
        }
    }


    /**
     * 模拟新后台请求
     * @param url
     * @param parameter
     * @param env
     * @param method
     * @return
     */
    @RequestMapping(value = "/new/common", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> executeNewAdminInter(@RequestParam("url") String url,@RequestParam("parameter") String parameter,@RequestParam("env") String env,@RequestParam("method") String method) {
        try {
            //先直接请求，如果失败了，再次登录后重新请求
            Response responseResult = null;
            UserInfoBean userInfoBean = userInfoBll.getUserInfoBytype("", env,"new");
            Map<String, Object> paramerMap = JSON.parseObject(parameter);
            Map<String, Object> headerMap = new HashMap<>();
            //添加cookie
            headerMap.put("Cookie", "awt=" + userInfoBean.getAwt());
            responseResult = executeService.send(url, paramerMap, headerMap, method);
            if(responseResult.asString().contains("登录运营管理平台")){
                //先登录
                boolean loginType = userInfoService.adminLoginNewByEnv(env,"new");
                if (loginType == true) {
                    //添加cookie
                    headerMap.put("Cookie", "awt=" + userInfoBean.getAwt());
                    responseResult = executeService.send(url, paramerMap, headerMap, method);
                    logger.info(responseResult.asString());
                    DebugForm debug = new DebugForm();
                    debug.setDebug(url, responseResult.getStatusCode(), responseResult.asString());
                    return ResultBean.success("接口调用成功！", debug);
                } else {
                    return ResultBean.failed("请求失败，请重新操作!");
                }
            }else {
                logger.info(responseResult.asString());
                DebugForm debug = new DebugForm();
                debug.setDebug(url, responseResult.getStatusCode(), responseResult.asString());
                return ResultBean.success("接口调用成功！", debug);
            }
        }catch (Exception e) {
            logger.error("请求超时或异常：{}", e);
            return ResultBean.failed("请求超时或异常!");
        }
    }







    public boolean checkIsTestPhone(String phone,String userId){
        if(!StringUtils.isBlank(phone)){
                String sub = phone.substring(0,3);
                if(sub.equals("140")){
                    return true;
                }else{
                    return false;
                }
        }else{
            if(!StringUtils.isBlank(userId)){
                String url = "https://admin.bosszhipin.com/user/boss?uid="+userId+"&name=&phone=&weiXin=&certEmail=&company=&companyName=&email=&simpleQuery=1";
//                UserInfoBean userInfoBean = userInfoBll.getUserInfo("16619813861","4");
//                if(userInfoBean == null){
//                    return false;
//                }
                Map<String, Object> headerMap = new HashMap<>();
                //添加cookie
                headerMap.put("Cookie","awt=BAmZcTQczBz1VYlA2DjoDZQAxV2FTZVU9DW1Ya1ViVjU.");
                ExecuteService executeService = new ExecuteService();
                Response responseResult = executeService.send(url,null,headerMap,"GET");
//                System.out.println(responseResult.asString());

                //正则表达式提取器
                String regular = "secretKey:(.*?),";
                //执行用例后置处理
                Pattern pattern = Pattern.compile("^\\d+$");
                Matcher isNum = pattern.matcher(regular);
                System.out.println("111111111");
                System.out.println(isNum.matches());
//                String actualRegular = DealStrSub.getSubUtilSimple(responseResult.asString(), regular);
//                System.out.println(actualRegular);
            }else{

            }
        }
        return true;

    }

    public static void main(String[] args) {
        UserInfoController test = new UserInfoController();
        System.out.println(test.checkIsTestPhone("","37151624"));
    }


}
