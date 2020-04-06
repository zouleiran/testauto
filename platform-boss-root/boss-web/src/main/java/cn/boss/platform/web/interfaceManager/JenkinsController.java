package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.service.interfaceManager.JenkinsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Jenkins")
public class JenkinsController {
    private static final Logger logger = LoggerFactory.getLogger(JenkinsController.class);
    @Resource
    JenkinsServer jenkinsServer;
//    @RequestMapping(value = "/gettestenv", method = RequestMethod.GET)
//    @ResponseBody
//    public Object gettestenv(@RequestParam(value="testenv",required = false) String testenv,
//                               @RequestParam(value="service",required = false) String service
//    ) {
//        try {
//            logger.info("/boss/Jenkins/gettestenv GET 方法被调用!! param: " +testenv);
//            List result=jenkinsServer.dojenkins(testenv,service);
//            Map<String, Object> result1 = new HashMap<String, Object>() {{
//                put("jenkinsBean", result);
//            }};
//            return ResultBean.success("查询成功!", result1);
//        } catch (Exception e) {
//            logger.error("查询失败：{}", e);
//            return ResultBean.failed("查询失败!");
//        }
//    }
//    gettestenv2
    @RequestMapping(value = "/gettestenv2", method = RequestMethod.GET)
    @ResponseBody
    public Object gettestenv2(@RequestParam(value="testenv",required = false) String testenv,
                               @RequestParam(value="service",required = false) String service,
                              @RequestParam(value="zuzhi",required = false) String source,
                              @RequestParam(value="pageIndex",required = false) int pageIndex
    ) {
        try {
            int begin=(pageIndex-1)*30;
            int size=30;
            logger.info("/boss/Jenkins/gettestenv2 GET 方法被调用!! param: " +testenv);
            List result=jenkinsServer.dojenkins2(testenv,service,begin,size,source);
            Map<String, Object> result1 = new HashMap<String, Object>() {{
                put("jenkinsBean", result);
            }};
            return ResultBean.success("查询成功!", result1);
        } catch (Exception e) {
            logger.error("查询失败：{}", e);
            return ResultBean.failed("查询失败!");
        }
    }
    @RequestMapping(value = "/getlastJenkins", method = RequestMethod.GET)
    @ResponseBody
    public Object gettestenv2(@RequestParam(value="testenv",required = false) String testenv,
                              @RequestParam(value="zuzhi",required = false) String source
    ) {
        try {
            logger.info("/boss/Jenkins/getlastJenkins GET 方法被调用!! param: " +testenv);
            List result=jenkinsServer.getlastJenkins(testenv,source);
            Map<String, Object> result1 = new HashMap<String, Object>() {{
                put("jenkinsBean2", result);
            }};
            return ResultBean.success("查询成功!", result1);
        } catch (Exception e) {
            logger.error("查询失败：{}", e);
            return ResultBean.failed("查询失败!");
        }
    }
    @RequestMapping(value = "/setCookie", method = RequestMethod.GET)
    @ResponseBody
    public Object debugExecute(@RequestParam(value="Cookie") String Cookie
    ) {
        try {
            logger.info("/boss/Jenkins/setCookie GET 方法被调用!! param: " +Cookie);
            jenkinsServer.setCookie(Cookie);
            return ResultBean.success("修改成功!");
        } catch (Exception e) {
            logger.error("修改失败：{}", e);
            return ResultBean.failed("修改失败!");
        }
    }

}
