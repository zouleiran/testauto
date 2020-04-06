package cn.boss.platform.service.tools;

import cn.boss.platform.bll.toolsManager.UserInfoBll;
import cn.boss.platform.service.interfaceManager.ExecuteService;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.boss.platform.bean.toolsManager.UserInfoBean;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2019/9/23.
 */
@Service
public class UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ExecuteService.class);

    @Autowired
    private UserInfoBll userInfoBll;
    @Autowired
    private ExecuteService executeService;


    /**
     * 循环执行后台登录生成awt值
     */
    public void adminLogin(){
        List<UserInfoBean> beans = userInfoBll.getUserInfo();
        String url = "";
        for(UserInfoBean c : beans){
            String env = c.getEnv();
            if(env.equals("1")){
                url = "http://192.168.1.22:8888/manager/login";
            }else if(env.equals("2")){
                url = "http://192.168.1.153:8888/manager/login";
            }else if(env.equals("3")){
                url = "http://192.168.1.168:8888/manager/login";
            }else if(env.equals("4")){
                url = "https://admin.bosszhipin.com/manager/login";
            }else if(env.equals("5")){
                url = "http://192.168.1.24:8888/manager/login";
            }

            Response responseResult = RestAssured.given().
                    redirects().follow(false).
                    param("account", c.getPhone()).
                    param("password", c.getPassword()).
                    headers("Accept", "application/JSON").
                    when().
                    post(url);

            c.setAwt(responseResult.getCookies().get("awt"));
            c.setUpdateTime(new Date());
            userInfoBll.updateUserInfo(c);
        }
    }

    /**
     * 登录指定环境下老后台
     * @param env
     * @return
     */
    public boolean adminLoginByenv(String env,String type){
        try{
            UserInfoBean bean = userInfoBll.getUserInfoBytype("",env,type);
            String url;
            if(env.equals("4")){
                url = "https://" + bean.getDomain() + "/manager/login";
            }else{
                url = "http://" + bean.getDomain() + "/manager/login";
            }

            Response responseResult = RestAssured.given().
                    redirects().follow(false).
                    param("account", bean.getPhone()).
                    param("password", bean.getPassword()).
                    headers("Accept", "application/JSON").
                    when().
                    post(url);
            bean.setAwt(responseResult.getCookies().get("awt"));
            bean.setUpdateTime(new Date());
            userInfoBll.updateUserInfo(bean);
            return true;
        }catch (Exception e){
            return false;
        }

        }

    /**
     * 登录指定环境新后台
     * @param env
     * @return
     */
    public boolean adminLoginNewByEnv(String env,String type){
            try{
                UserInfoBean bean = userInfoBll.getUserInfoBytype("",env,type);
                String url;
                if(env.equals("4")){
                    url = "https://" + bean.getDomain().trim() + "/user/login.json";
                }else{
                    url = "http://" + bean.getDomain().trim() + "/user/login.json";
                }
                Response responseResult = RestAssured.given().
                        redirects().follow(false).
                        param("phone", bean.getPhone()).
                        param("password", bean.getPassword()).
                        headers("Accept", "application/JSON").
                        when().
                        post(url);
                logger.info(responseResult.asString());
                logger.info(responseResult.getCookies().get("awt"));
                bean.setAwt(responseResult.getCookies().get("awt"));
                bean.setUpdateTime(new Date());
                userInfoBll.updateUserInfo(bean);
                return true;
            }catch (Exception e){
                return false;
            }
        }
    }
