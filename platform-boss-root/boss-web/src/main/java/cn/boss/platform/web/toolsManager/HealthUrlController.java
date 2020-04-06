package cn.boss.platform.web.toolsManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.dubboManager.ZhipinEnvBean;
import cn.boss.platform.bean.toolsManager.HealthUrlBean;
import cn.boss.platform.bll.dubboManager.ZhipinEnvBll;
import cn.boss.platform.bll.toolsManager.HealthUrlBll;
import cn.boss.platform.service.interfaceManager.ExecuteService;
import com.jayway.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.ConnectException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/2/26.
 */
@Controller
@RequestMapping("/boss/enspect")
public class HealthUrlController {

    private static final Logger logger = LoggerFactory.getLogger(HealthUrlController.class);

    @Autowired
    private HealthUrlBll healthUrlBll;
    @Autowired
    private ZhipinEnvBll zhipinEnvBll;
    @Autowired
    private ExecuteService executeService;

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> execute(@RequestParam("env") String env, @RequestParam(value="zhipinName",required =false, defaultValue = "") String zhipinName) {
        try {
            Integer serialId = Integer.parseInt(new Date().getTime() / 1000+"");
            List<ZhipinEnvBean> list = zhipinEnvBll.getZhipinEnv(zhipinName, env);
            Response responseResult ;
            for (ZhipinEnvBean c : list) {
                HealthUrlBean bean = new HealthUrlBean();
                bean.setSerialId(serialId);
                bean.setEnv(env);
                bean.setPerson(c.getPerson());
                bean.setZhipinName(c.getZhipinName());
                bean.setCreateTime(new Date());
                String url = "http://" + c.getHealthUrl() + c.getHealthAddress();
                bean.setUrl(url);
                bean.setDomain(c.getHealthUrl());
                try {
                    //发送请求
                    responseResult = executeService.send(url, null, null, "get");
                    bean.setStatus(responseResult.getStatusCode());
                    bean.setResponseResult(responseResult.asString());
                } catch (Exception e) {
                    bean.setStatus(604);
                    bean.setResponseResult(e.getMessage());
                }
                healthUrlBll.addLog(bean);
            }
            return ResultBean.success("监测成功！");
        } catch (Exception e) {
            logger.error("获取监测结果失败：{}", e);
            return ResultBean.failed("获取监测结果失败！");
        }
    }

    /**
     *
     * @param env
     * @param zhipinName
     * @param pageSize
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam(value="env",required =false, defaultValue = "") String env,@RequestParam(value="zhipinName",required =false, defaultValue = "") String zhipinName,
                                   @RequestParam("pageSize") Integer pageSize,@RequestParam("pageIndex") Integer pageIndex) {
        try {
            List<HealthUrlBean> list = healthUrlBll.getLog(env,zhipinName,pageIndex,pageSize);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("List", list);
            }};
            return ResultBean.success("查询日志成功!",result);
        } catch (Exception e) {
            logger.error("查询日志异常：{}", e);
            return ResultBean.failed("查询日志失败!");
        }
    }



}
