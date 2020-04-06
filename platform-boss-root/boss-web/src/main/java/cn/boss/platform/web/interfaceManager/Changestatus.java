package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.dao.toolsManager.ChangestatusMapper;
import cn.boss.platform.service.tools.RedisService;
import cn.boss.platform.web.api.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by admin on 2018/8/11.
 */
@Controller
@RequestMapping("/tools/changestatus")
public class Changestatus extends AbstractBaseController {
//    private static String contextpath = "/usr/tomcat/apache-tomcat-8.5.37/webapps/file/pro";
//    private static String contextpath = "/Users/zouleiran/Desktop/zlr/zlr/file/1";
    private static final Logger logger = LoggerFactory.getLogger(Changestatus.class);
    @Resource
    RedisService redisService;
    @Resource
    ChangestatusMapper changestatusMapper;
    @GetMapping(value = "/boss")
    @ResponseBody
    public ResultBean<Object> add(@RequestParam("bossid") String bossid,@RequestParam("status") String status,@RequestParam("env") String env) throws IOException {
        logger.info("/tools/changestatus/boss POST 方法被调用!!");
        try{
            if(env.equals("QA"))
                //先修改数据库
                changestatusMapper.changeboss(bossid,status);
                //再清除缓存
            redisService.delkey(env,"zb_qa:b_extra_"+bossid);
            return ResultBean.success("执行成功!");
        } catch (Exception e) {
            logger.error("执行失败：{}", e);
            return ResultBean.failed("执行失败!");
        }
    }
    @GetMapping(value = "/geek")
    @ResponseBody
    public ResultBean<Object> deletefile(@RequestParam("geekid") String geekid,@RequestParam("status") String status,@RequestParam("env") String env) {
        logger.info("/tools/changestatus/geek POST 方法被调用!!");
        try{
            if(env.equals("QA"))
                //先修改数据库
                changestatusMapper.changegeek(geekid,status);
            //再清除缓存6461346 get zp_geek_qa:GEXTK_6461346
            redisService.delkey(env,"zp_geek_qa:GEXTK_"+geekid);
            return ResultBean.success("执行成功!");
        } catch (Exception e) {
            logger.error("执行失败：{}", e);
            return ResultBean.failed("执行失败!");
        }
    }
}