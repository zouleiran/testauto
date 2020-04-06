package cn.boss.platform.web.api;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.EnvBean;
import cn.boss.platform.bll.InterfaceManager.EnvBll;
import cn.boss.platform.service.tools.DatabaseService;
import cn.boss.platform.service.tools.PassPortService;
import com.zhipin.passport.api.PassportApi;
import com.zhipin.passport.model.PassportBO;
import com.zhipin.service.common.Result;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/11/16.
 */
@Controller
@RequestMapping("business")
public class BusinessController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private EnvBll envBll;
    @Autowired
    private PassPortService passPortService;
    @Autowired
    private PassportApi passportApi;
    @Autowired
    private PassportApi passportApiQa2;
    @Autowired
    private PassportApi passportApiQa3;
    @Autowired
    private PassportApi passportApiUnderLine;

    /**
     * 添加直豆功能（老接口）
     * @param env
     * @param db
     * @param userId
     * @param count
     * @param type
     * @return
     */
    @RequestMapping(value = "/add/beanBag", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> beanBag(@RequestParam("env") String env,@RequestParam("db") String db,@RequestParam("userId") String userId, @RequestParam("count") Integer count,@RequestParam("type") Integer type) {
        logger.info("/add/beanBag方法被调用");
        try{
            DatabaseService database = new DatabaseService(env,db);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            //添加老直豆
            if(type == 0){
                String sqlOne = "update user_bean_bag set bean_count=" + count + " where user_id="+ userId;
                database.update(sqlOne);
                String sqlTwo = "select amount from user_fund where user_id=" + userId;
                List<Map<String,String>> result = database.select(sqlTwo,1,15);
                int amount;
                if(result !=null && result.size()>0){
                    amount = (count*100+Integer.parseInt(result.get(0).get("amount"))*100);
                }else{
                    amount = count*100;
                }
                String sqlThree = "update user_bean set amount=" +amount + ",bean_amount="+count*100+" where user_id="+ userId;
                database.update(sqlThree);
                //添加赠送表记录
                String sqlFour = "select * from user_gift_fund where user_id=" + userId;
                List<Map<String,String>> resultFour = database.select(sqlFour,1,15);
                if(resultFour != null && resultFour.size() == 0){
                    String sqlFive = "insert into user_gift_fund (user_id,amount,add_time,update_time) VALUE ("+userId+",0,'"+df.format(new Date())+"','"+df.format(new Date())+"') ";
                    database.update(sqlFive);
                }
                return ResultBean.success("充值成功！");
                //添加新零钱
            }else if(type == 1){
                String sqlTwo = "delete from user_fund where user_id=" + userId;
                database.update(sqlTwo);
                String sqlThree = "insert into user_fund (user_id,amount,add_time,update_time) VALUE ("+userId+","+count*100+",'"+df.format(new Date())+"','"+df.format(new Date())+"') ";
                database.update(sqlThree);
                String sqlFour = "select bean_count from user_bean_bag where user_id=" + userId;
                List<Map<String,String>> result = database.select(sqlFour,1,15);
                int new_amount;
                int bean_amount;
                if(result !=null && result.size()>0){
                    new_amount = count*100+Integer.parseInt(result.get(0).get("bean_count"))*100;
                    bean_amount = Integer.parseInt(result.get(0).get("bean_count"))*100;
                }else{
                    new_amount = count*100;
                    bean_amount = 0;
                }
                String sqlFive = "update user_bean set amount=" +new_amount + ",bean_amount="+ bean_amount +" where user_id="+ userId;
                database.update(sqlFive);
                //添加赠送表记录
                String sqlSix = "select * from user_gift_fund where user_id=" + userId;
                List<Map<String,String>> resultFour = database.select(sqlSix,1,15);
                if(resultFour != null && resultFour.size() == 0){
                    String sqlSeven = "insert into user_gift_fund (user_id,amount,add_time,update_time) VALUE ("+userId+",0,'"+df.format(new Date())+"','"+df.format(new Date())+"') ";
                    database.update(sqlSeven);
                }
                return ResultBean.success("充值成功！");
            }else{
                return ResultBean.failed("充值类型错误！");
            }
        } catch (Exception e) {
            logger.error("接口调用成功：{}", e);
            return ResultBean.failed("充值失败!");
        }
    }


    /**
     * 清除直豆功能（老接口）
     * @param env
     * @param db
     * @param userId
     * @return
     */
    @RequestMapping(value = "/delete/beanBag", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> beanBagDelete(@RequestParam("env") String env,@RequestParam("db") String db,@RequestParam("userId") String userId) {

        logger.info("/add/beanBag方法被调用");
        try{
            DatabaseService database = new DatabaseService(env,db);
            String sqlOne = "update user_bean_bag  set  bean_count = '0' where user_id=" + userId;
            String sqlTwo = "update user_gift_fund set  amount = '0'   where user_id=" + userId;
            String sqlThree = "update user_fund  set  amount = '0'  where user_id= " + userId;
            String sqlFour = "update user_bean  set  amount = '0' , bean_amount = '0' where user_id=" + userId;
            database.update(sqlOne);
            database.update(sqlTwo);
            database.update(sqlThree);
            database.update(sqlFour);
            return ResultBean.success("清除成功！");
        } catch (Exception e) {
            logger.error("接口调用成功：{}", e);
            return ResultBean.failed("清除失败!");
        }
    }

    /**
     * 添加直豆功能
     * @param env
     * @param db
     * @param userId
     * @param count
     * @param type
     * @return
     */
    @RequestMapping(value = "/add/beanBag/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> beanBagV2(@RequestParam("env") String env,@RequestParam("db") String db,@RequestParam("userId") String userId, @RequestParam("count") Integer count,@RequestParam("type") Integer type) {
        logger.info("/add/beanBag方法被调用");
        try{
            if (type != 0 && type != 1) {
                return ResultBean.failed("充值类型错误！");
            }
            DatabaseService database = new DatabaseService(env,db);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            if (type == 0) {
                //添加老直豆
                String sqlOne = String.format("insert into user_bean_bag(user_id,bean_count,status,add_time) values(%s,%s,0,now()) on duplicate key update bean_count = bean_count + %s", userId, count, count);
                database.update(sqlOne);
            } else {
                //添加新钱
                String sqlOne = String.format("insert into user_fund(user_id,amount,add_time) values(%s,%s,now()) on duplicate key update amount = amount + %s", userId, count * 100, count * 100);
                database.update(sqlOne);
            }
            // 计算总直豆数
            String beanCountSql = "select bean_count from user_bean_bag where user_id=" + userId;
            List<Map<String, String>> result = database.select(beanCountSql, 1, 1);
            int beanCount = 0;
            if(result !=null && result.size()>0){
                beanCount = Integer.parseInt(result.get(0).get("bean_count"));
            }

            String fundAmountSql = "select amount from user_fund where user_id=" + userId;
            result = database.select(fundAmountSql, 1, 1);
            int fundAmount = 0;
            if(result !=null && result.size()>0){
                fundAmount = Integer.parseInt(result.get(0).get("amount"));
            }

            String giftAmountSql = "select amount from user_gift_fund where user_id=" + userId;
            result = database.select(giftAmountSql, 1, 1);
            int giftAmount = 0;
            if(result !=null && result.size()>0){
                giftAmount = Integer.parseInt(result.get(0).get("amount"));
            }
            int amount = beanCount * 100 + fundAmount + giftAmount;
            String sqlOne = String.format("insert into user_bean(user_id,amount,bean_amount,add_time) values(%s,%s,%s,now()) on duplicate key update amount = %s, bean_amount = %s", userId, amount, beanCount * 100, amount, beanCount * 100);
            database.update(sqlOne);
            return ResultBean.success("充值成功！");
        } catch (Exception e) {
            logger.error("接口调用成功：{}", userId, e);
            return ResultBean.failed("充值失败!");
        }
    }



    /**
     * 获取登录信息（secretKey、t、userid）
     * @param envIp
     * @param envId
     * @param projectId
     * @param db
     * @param phone
     * @return
     */
    @RequestMapping(value = "/getSecretKey", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getSecretKey(@RequestParam(value="envIp",required =false) String envIp,@RequestParam(value = "envId",required =false) Integer envId,
                                           @RequestParam("projectId") Integer projectId,@RequestParam("db") String db,@RequestParam("phone") String phone) {
        logger.info("/getSecretKey方法被调用");
        try{
            EnvBean envBean = envBll.getEnvBeanByip(envIp,envId,projectId);
            String env = envBean.getEnv();
            if(env.equals("线下环境")){
                env = "线下测试";
            }
            if(StringUtils.isBlank(phone)){
                return ResultBean.failed("手机号不能为空！");
            }
            if(envBean == null){
                return ResultBean.failed("查询环境有误！");
            }else if(env.equals("qa") || env.equals("qa2")  || env.equals("qa3") || env.equals("线下测试" ) || env.equals("线下环境")){
                DatabaseService database = new DatabaseService(env,db);
                String phoneEncryption = passPortService.encrypt(phone);
                String sqlOne = "select * from login_info where 1=1  and account='" + phoneEncryption +"' or account='" + phone + "'";
                List<Map<String,String>> result = database.select(sqlOne,1,15);
                if(result !=null && result.size()>0){
                    String userId = result.get(0).get("user_id");
                    String sqlTwo = "select * from passport where 1=1 and user_id=" +userId;
                    List<Map<String,String>> resultTwo = database.select(sqlTwo,1,15);
                    Map<String, Object> resultMap = new HashMap<String, Object>() {{
                        put("secretKey", resultTwo.get(0).get("user_secret_key"));
                        put("t", resultTwo.get(0).get("ticket"));
                        put("wt", resultTwo.get(0).get("web_ticket"));
                        put("userId", userId);
                    }};
                    return ResultBean.success("获取"+envBean.getEnv()+"环境ticket成功!",resultMap);
                }else{
                    return ResultBean.failed("手机号不存在或未注册！");
                }
            } else{
                return ResultBean.failed("不能查询该环境下的ticket!");
            }

        }catch (Exception e) {
            logger.error("接口调用成功：{}", e);
            return ResultBean.failed("获取失败!");
        }
    }


    @RequestMapping(value = "/getSecretKeyV2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getSecretKeyV2(@RequestParam(value="envIp",required =false) String envIp,@RequestParam(value = "envId",required =false) Integer envId,
                                           @RequestParam("projectId") Integer projectId,@RequestParam("db") String db,@RequestParam("phone") String phone) {
        logger.info("/getSecretKey方法被调用");
        try{
            EnvBean envBean = envBll.getEnvBeanByip(envIp,envId,projectId);
            String env = envBean.getEnv();
            if(env.equals("线下环境")){
                env = "线下测试";
            }
            if(StringUtils.isBlank(phone)){
                return ResultBean.failed("手机号不能为空！");
            }
            if(envBean == null){
                return ResultBean.failed("查询环境有误！");
            }
            else if(env.equals("qa") || env.equals("qa2")  || env.equals("qa3") || env.equals("线下测试" ) || env.equals("线下环境")){
                DatabaseService database = new DatabaseService(env,db);
                String phoneEncryption = passPortService.encrypt(phone);
                String sqlOne = "select * from login_info where 1=1  and account='" + phoneEncryption +"' or account='" + phone + "'";
                List<Map<String,String>> result = database.select(sqlOne,1,15);
                Result<PassportBO> passPort = null;
                if(result !=null && result.size()>0){
                    String userId = result.get(0).get("user_id");
                    if(env.equals("qa")){
                        passPort= passportApi.getPassportByUserId(Long.parseLong(userId));
                    }else if(env.equals("qa2")){
                        passPort= passportApiQa2.getPassportByUserId(Long.parseLong(userId));
                    }else if(env.equals("qa3")){
                        passPort= passportApiQa2.getPassportByUserId(Long.parseLong(userId));
                    }else if(env.equals("线下测试" ) || env.equals("线下环境")){
                        passPort= passportApiUnderLine.getPassportByUserId(Long.parseLong(userId));
                    }
                    Result<PassportBO> finalPassPort = passPort;
                    if(finalPassPort.getData() == null){
                        return ResultBean.failed("手机号未登录！");
                    }else{
                        Map<String, Object> resultMap = new HashMap<String, Object>() {{
                            put("secretKey", finalPassPort.getData().getUserSecretKey());
                            put("t", finalPassPort.getData().getTicket());
                            put("wt", finalPassPort.getData().getWebTicket());
                            put("userId", userId);
                        }};
                        return ResultBean.success("获取"+envBean.getEnv()+"环境ticket成功!",resultMap);
                    }

                }else{
                    return ResultBean.failed("手机号不存在或未注册！");
                }
            } else{
                return ResultBean.failed("不能查询该环境下的ticket!");
            }

        }catch (Exception e) {
            logger.error("接口调用成功：{}", e);
            return ResultBean.failed("获取失败!");
        }
    }


    @RequestMapping(value = "/getSecretKeyV3", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getSecretKeyV3(@RequestParam(value = "env") String env,@RequestParam("db") String db,@RequestParam("phone") String phone) {
        logger.info("/getSecretKey方法被调用");
        try{
            if(StringUtils.isBlank(phone)){
                return ResultBean.failed("手机号不能为空！");
            }
            DatabaseService database = new DatabaseService(env,db);
            String phoneEncryption = passPortService.encrypt(phone);
            String sqlOne = "select * from login_info where 1=1  and account='" + phoneEncryption +"' or account='" + phone + "'";
            List<Map<String,String>> result = database.select(sqlOne,1,15);
            Result<PassportBO> passPort = null;
            if(result !=null && result.size()>0){
                String userId = result.get(0).get("user_id");
                if(env.equals("qa")){
                    passPort= passportApi.getPassportByUserId(Long.parseLong(userId));
                }else if(env.equals("qa2")){
                    passPort= passportApiQa2.getPassportByUserId(Long.parseLong(userId));
                }else if(env.equals("qa3")){
                    passPort= passportApiQa2.getPassportByUserId(Long.parseLong(userId));
                }else if(env.equals("线下测试" ) || env.equals("线下环境")){
                    passPort= passportApiUnderLine.getPassportByUserId(Long.parseLong(userId));
                }
                Result<PassportBO> finalPassPort = passPort;
                if(finalPassPort.getData() == null){
                    return ResultBean.failed("手机号未登录！");
                }else{
                    Map<String, Object> resultMap = new HashMap<String, Object>() {{
                        put("secretKey", finalPassPort.getData().getUserSecretKey());
                        put("t", finalPassPort.getData().getTicket());
                        put("wt", finalPassPort.getData().getWebTicket());
                        put("userId", userId);
                    }};
                    return ResultBean.success("获取"+env+"环境ticket成功!",resultMap);
                }
            }else{
                return ResultBean.failed("手机号不存在或未注册！");
            }


        }catch (Exception e) {
            logger.error("接口调用成功：{}", e);
            return ResultBean.failed("获取失败!");
        }
    }
}
