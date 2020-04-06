package cn.boss.platform.web.dubboManager.controller;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.dubboManager.ZhipinDubboCaseExecuteLogBean;
import cn.boss.platform.bean.dubboManager.ZhipinEnvBean;
import cn.boss.platform.bll.dubboManager.ZhipinDubboCaseExecuteLogBll;
import cn.boss.platform.bll.dubboManager.ZhipinEnvBll;
import cn.boss.platform.web.api.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/12/26.
 */
@Controller
@RequestMapping("/boss/zhipin/log")
public class ZhipinDubboCaseExecuteLogController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(ZhipinDubboCaseExecuteLogController.class);

    @Autowired
    ZhipinDubboCaseExecuteLogBll zhipinDubboCaseExecuteLogBll;
    @Autowired
    private ZhipinEnvBll zhipinEnvBll;

    @GetMapping("/list")
    @ResponseBody
    public ResultBean<Object> logList(@RequestParam(value = "caseId",required =false) Integer caseId,@RequestParam(value = "interfaceId",required =false) Integer interfaceId,@RequestParam(value = "taskId",required =false) Integer taskId,
                                      @RequestParam(value = "serialId",required =false) Integer serialId, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        logger.info("/log/list POST 方法被调用!! param:" + caseId, pageIndex, pageSize);
        try {
            List<ZhipinDubboCaseExecuteLogBean> list = zhipinDubboCaseExecuteLogBll.getExecuteLog(interfaceId,caseId,taskId,serialId,pageIndex,pageSize);
            int count = zhipinDubboCaseExecuteLogBll.getCountCaseExecuteLog(interfaceId,caseId,taskId,serialId);
            if(list !=null && list.size()>0){
                for(int i=0; i < list.size(); i++){
                    ZhipinEnvBean zhipinEnvBean= zhipinEnvBll.getZhipinEnvById(list.get(i).getEnvId());
                    list.get(i).setZhipinEnvBean(zhipinEnvBean);
                }
            }
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("caseLogList", list);
                put("caseLogCount", count);
            }};
            return ResultBean.success("获取用例日志成功!", result);
        } catch (Exception e) {
            logger.error("获取case执行日志接口异常：{" + e + "}");
            return ResultBean.failed("获取用例日志异常!");
        }
    }
}



