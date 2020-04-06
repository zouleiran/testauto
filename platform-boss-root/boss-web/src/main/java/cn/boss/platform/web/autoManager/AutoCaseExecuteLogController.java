package cn.boss.platform.web.autoManager;

import cn.boss.platform.bean.autoManager.AutoCaseExecuteLogBean;
import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.CaseExecuteLogBean;
import cn.boss.platform.bean.interfaceManager.TaskBean;
import cn.boss.platform.bll.InterfaceManager.TaskBll;
import cn.boss.platform.bll.autoCaseManager.AutoCaseExecuteLogBll;
import cn.boss.platform.doe.model.AutoCaseExecuteLogViewModel;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.model.ExecuteLogViewModel;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/11/22.
 */
@Controller
@RequestMapping("/boss/autoCaseLog")
public class AutoCaseExecuteLogController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(AutoCaseExecuteLogController.class);

    @Autowired
    private AutoCaseExecuteLogBll autoCaseExecuteLogBll;
    @Autowired
    private TaskBll taskBll;

    /**
     * 查询用例执行日志，用例管理中使用
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public ResultBean<Object> getlogList(@RequestParam(value="projectId",required =false) Integer projectId, @RequestParam(value="taskId",required =false) Integer taskId,
                                         @RequestParam(value="serialId",required =false) Integer serialId,
                                         @RequestParam(value="caseId",required =false) Integer caseId,@RequestParam(value="caseStepId",required =false) Integer caseStepId,
                                         @RequestParam(value="startTime",required =false) String startTime,@RequestParam(value="endTime",required =false) String endTime,
                                         @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        logger.info("开始获用例执行日志!");
        try {
            List<AutoCaseExecuteLogBean> list = autoCaseExecuteLogBll.getAutoCaseStepLog(projectId, taskId,serialId, caseId, caseStepId, startTime, endTime, pageIndex, pageSize);

            int count = autoCaseExecuteLogBll.getAutoCaseStepLogCount(projectId, taskId,serialId, caseId, caseStepId, startTime, endTime);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("list", list);
                put("caseExCount", count);
                NumberFormat nt = NumberFormat.getPercentInstance();
                nt.setMinimumFractionDigits(0);
            }};
            return ResultBean.success("获取成功!", result);

        } catch (Exception e) {
            logger.error("获取用例执行日志接口异常：{" + e + "}");
            return ResultBean.failed("获取用例执行日志异常!");
        }
    }


    /**
     * 查询用例执行日志，日志管理中使用
     * @return
     */
    @GetMapping("/list/log")
    @ResponseBody
    public ResultBean<Object> getlogLists(@RequestParam(value="projectId",required =false) Integer projectId, @RequestParam(value="taskId",required =false) Integer taskId,
                                         @RequestParam(value="serialId",required =false) Integer serialId, @RequestParam(value="startTime",required =false) String startTime,
                                          @RequestParam(value="endTime",required =false) String endTime, @RequestParam("pageIndex") Integer pageIndex,
                                          @RequestParam("pageSize") Integer pageSize) {
        logger.info("开始获用例执行日志!");
        try {
            List<AutoCaseExecuteLogBean> list = autoCaseExecuteLogBll.executeLog(projectId, taskId,serialId, startTime, endTime, pageIndex, pageSize);

            int count = autoCaseExecuteLogBll.executeLogCount(projectId, taskId,serialId, startTime, endTime);

            List<AutoCaseExecuteLogViewModel> executeLogList = new ArrayList<>();
            if (!list.isEmpty()) {
                for (AutoCaseExecuteLogBean log : list) {
                    AutoCaseExecuteLogViewModel viewModel = new AutoCaseExecuteLogViewModel();
                    BeanUtils.copyProperties(viewModel, log);
                    //执行成功数
                    int successCaseCount = autoCaseExecuteLogBll.getExecuteSucessCount(log.getSerialId());
                    //总数
                    int countBySericalId = autoCaseExecuteLogBll.countSerialIdExecuteLog(log.getSerialId());
                    viewModel.setResultStatus(successCaseCount == countBySericalId ? "成功" : "失败");
                    viewModel.setScale(String.format("%s/%s", successCaseCount, countBySericalId));
                    //添加任务名
                    List<TaskBean> taskBeanList = taskBll.searchTaskById(viewModel.getTaskId());
                    if(taskBeanList !=null && taskBeanList.size() > 0){
                        viewModel.setTaskName(taskBeanList.get(0).getTaskName());
                    } else{
                        viewModel.setTaskName("任务已删除");
                    }
                    executeLogList.add(viewModel);
                }

            }
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("list", executeLogList);
                put("logCount", count);
            }};
            return ResultBean.success("获取日志成功!", result);

        } catch (Exception e) {
            logger.error("获取用例执行日志接口异常：{" + e + "}");
            return ResultBean.failed("获取用例执行日志异常!");
        }
    }
}