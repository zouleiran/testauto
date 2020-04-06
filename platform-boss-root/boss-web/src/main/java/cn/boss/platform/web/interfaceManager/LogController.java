package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.*;
import cn.boss.platform.bean.toolsManager.AppInterfaceLogBean;
import cn.boss.platform.bll.InterfaceManager.CaseExecuteLogBll;
import cn.boss.platform.bll.InterfaceManager.InterfaceBll;
import cn.boss.platform.bll.InterfaceManager.TaskBll;
import cn.boss.platform.bll.toolsManager.AppInterfaceLogBll;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.CaseForm;
import cn.boss.platform.web.form.LogForm;
import cn.boss.platform.web.model.ExecuteLogViewModel;
import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by admin on 2018/8/17.
 */
@Controller
@RequestMapping("/boss/log")
public class LogController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private CaseExecuteLogBll caseExecuteLogBll;
    @Autowired
    private InterfaceBll interfaceBll;
    @Autowired
    private TaskBll taskBll;
    @Autowired
    private AppInterfaceLogBll appInterfaceLogBll;

    /**
     * 按流水号分组查询且获取每组第一条执行日志
     *
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> logList(@Valid LogForm form, BindingResult bindingResult) {
        logger.info("开始获用例执行日志!");
        try {
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            //日志
            List<CaseExecuteLogBean> list = caseExecuteLogBll.executeLog(form.getProjectId(),form.getTaskId(), form.getSerialId() ,form.getStartTime(), form.getEndTime(), form.getPageIndex(), form.getPageSize());
            //日志总数
            int logCount = caseExecuteLogBll.executeLogCount(form.getProjectId(),form.getTaskId(),form.getSerialId() ,form.getStartTime(), form.getEndTime());
            List<ExecuteLogViewModel> executeLogList = new ArrayList<>();
            if (!list.isEmpty()) {
                for (CaseExecuteLogBean log : list) {
                    ExecuteLogViewModel viewModel = new ExecuteLogViewModel();
                    BeanUtils.copyProperties(viewModel, log);
                    //执行成功数
                    int successCaseCount = caseExecuteLogBll.getExecuteSucessCount(log.getSerialId());
                    //总数
                    int count = caseExecuteLogBll.countSerialIdExecuteLog(log.getSerialId());
                    viewModel.setResultStatus(successCaseCount == count ? "成功" : "失败");
                    viewModel.setScale(String.format("%s/%s", successCaseCount, count));
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
                put("logCount", logCount);
            }};
            return ResultBean.success("查询用例执行日志成功!", result);
        } catch (Exception e) {
            logger.error("获用例执行日志接口异常：{" + e + "}");
            return ResultBean.failed("获用例执行日志异常!");
        }
    }


    /**
     * 查询日志详细信息
     * @return
     */
    @GetMapping("/detail/list")
    @ResponseBody
    public ResultBean<Object> getlogList(@Valid LogForm form, BindingResult bindingResult) {
        logger.info("开始获用例执行日志!");
        String errorMsg = validateData(bindingResult);
        if (!StringUtils.isEmpty(errorMsg)) {
            logger.info("params error:{}", errorMsg);
            return ResultBean.failed(errorMsg);
        }
        try {
            List<CaseExecuteLogBean> list = caseExecuteLogBll.getDetailExecuteLog(form.getSerialId(), form.getPageIndex(), form.getPageSize());
            for (CaseExecuteLogBean c : list) {
                if(!StringUtils.isBlank(c.getUrl())){
                    URL uri = new URL(c.getUrl());
                    String path = uri.getPath();
                    c.setPath(path);
                }
            }
            int count = caseExecuteLogBll.countSerialIdExecuteLog(form.getSerialId());
            //执行成功数
            int successCaseCount = caseExecuteLogBll.getExecuteSucessCount(form.getSerialId());
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("list", list);
                put("caseExCount", count);
                put("successCaseCount", successCaseCount);
                //成功率
                NumberFormat nt = NumberFormat.getPercentInstance();
                nt.setMinimumFractionDigits(0);
                float baifen = (float)successCaseCount/(float)count;
                put("successRate", nt.format(baifen));
            }};
            return ResultBean.success("获取成功!", result);

        } catch (Exception e) {
            logger.error("获用例执行日志接口异常：{" + e + "}");
            return ResultBean.failed("获用例执行日志异常!");
        }
    }

    /**
     * 查询日志简易信息
     * @return
     */
    @GetMapping("/detail/simpleList")
    @ResponseBody
    public ResultBean<Object> getlogListSimple(@Valid LogForm form, BindingResult bindingResult) {
        logger.info("开始获用例执行日志!");
        String errorMsg = validateData(bindingResult);
        if (!StringUtils.isEmpty(errorMsg)) {
            logger.info("params error:{}", errorMsg);
            return ResultBean.failed(errorMsg);
        }
        try {
            List<CaseExecuteLogBean> list = caseExecuteLogBll.getDetailExecuteLog(form.getSerialId(), form.getPageIndex(), form.getPageSize());
            int count = caseExecuteLogBll.countSerialIdExecuteLog(form.getSerialId());
            //执行成功数
            int successCaseCount = caseExecuteLogBll.getExecuteSucessCount(form.getSerialId());
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("list", list.get(0));
                put("caseExCount", count);
                put("successCaseCount", successCaseCount);
                //成功率
                NumberFormat nt = NumberFormat.getPercentInstance();
                nt.setMinimumFractionDigits(0);
                float baifen = (float)successCaseCount/(float)count;
                put("successRate", nt.format(baifen));
            }};
            return ResultBean.success("获取成功!", result);

        } catch (Exception e) {
            logger.error("获用例执行日志接口异常：{" + e + "}");
            return ResultBean.failed("获用例执行日志异常!");
        }
    }

    @GetMapping("/report")
    @ResponseBody
    public ResultBean<Object> report(@RequestParam("serialId") String serialId) {
        try {
            logger.info("获取日志报告接口,serialId:{}", serialId);
            List<ReportBean> reportBeans = caseExecuteLogBll.getReport(serialId);
            if (!reportBeans.isEmpty()) {
                for (ReportBean report : reportBeans) {
                    int success = 0;
                    List<CaseLogResultBean> caseLogResultBean = report.getExecuteLogs();
                    //接口总用例数
                    int caseCount = caseLogResultBean.size();
                    for (CaseLogResultBean caseExecuteLog : caseLogResultBean){
                        CaseExecuteLogBean bean = caseExecuteLog.getBean();
                        //返回数据太多时，页面会卡，所以预期和实际结果返回为空
                        bean.setExpectedResult("");
                        bean.setResponseResult("");
                        bean.setParameters("");
                        bean.setUrl("");
                        if(bean.isResult() == true){
                            success++;
                        }
                    }
                    report.setInterCaseSuccessCount(success);
                    report.setInterCaseCount(caseCount);
                    report.setInterCasefailCount(caseCount - success);
                    //成功率
                    NumberFormat nt = NumberFormat.getPercentInstance();
                    nt.setMinimumFractionDigits(0);
                    float baifen = (float)success/(float)caseCount;
                    report.setSuccessRate(nt.format(baifen));
                }
            }
            return ResultBean.success("获取成功!", reportBeans);
        } catch (Exception e) {
            logger.error("获取日志报告接口异常：{" + e + "}");
            return ResultBean.failed("获取日志报告接口异常!");
        }
    }

    @GetMapping("/report/simple")
    @ResponseBody
    public ResultBean<Object> reportSimple(@RequestParam("serialId") String serialId) {
        try {
            logger.info("获取日志报告接口,serialId:{}", serialId);
            List<ReportBean> reportBeans = caseExecuteLogBll.getReport(serialId);
            if (!reportBeans.isEmpty()) {
                for (ReportBean report : reportBeans) {
                    int success = 0;
                    long executeTime = 0;
                    List<CaseLogResultBean> caseLogResultBean = report.getExecuteLogs();
                    InterfaceBean interfaceBean = interfaceBll.getInterfaceById(report.getInterfaceId());
                    //接口总用例数
                    int caseCount = caseLogResultBean.size();
                    for (CaseLogResultBean caseExecuteLog : caseLogResultBean){
                        CaseExecuteLogBean bean = caseExecuteLog.getBean();
                        if(bean.isResult() == true){
                            success++;
                        }
                        //计算接口下用例总时间
                        executeTime += bean.getExecTime();
                        //返回数据太多时，页面会卡
                        caseExecuteLog.setBean(null);
                    }
                    report.setPath(interfaceBean.getPath());
                    report.setExecuteTime(executeTime);
                    report.setInterCaseSuccessCount(success);
                    report.setInterCaseCount(caseCount);
                    report.setInterCasefailCount(caseCount - success);
                    //成功率
                    NumberFormat nt = NumberFormat.getPercentInstance();
                    nt.setMinimumFractionDigits(0);
                    float baifen = (float)success/(float)caseCount;
                    report.setSuccessRate(nt.format(baifen));
                }
            }
            return ResultBean.success("获取成功!", reportBeans);
        } catch (Exception e) {
            logger.error("获取日志报告接口异常：{" + e + "}");
            return ResultBean.failed("获取日志报告接口异常!");
        }
    }


    /**
     * 抓包日志接口
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> addAppInterfaceLog(@RequestParam(value="url",required =false) String url,
                                                 @RequestParam(value="userId",required =false) String userId,
                                                 @RequestParam(value="parameter",required =false) String parameter,
                                                 @RequestParam(value="method",required =false) String method,
                                                 @RequestParam(value="result",required =false) String result,
                                                 @RequestParam(value="status",required =false) String status) {
        try{
            AppInterfaceLogBean bean = new AppInterfaceLogBean();
            try{
                if(!StringUtils.isEmpty(result) ){
                    Map headerMap = JSON.parseObject(result);
                    int code = (int) headerMap.get("code");
                    if(code != 0 || result.contains("系统服务错误") || result.contains("服务器超时")){
                        bean.setBusinessStatus("error");
                    }
                }
            }catch (Exception e) {
                logger.error(e.getMessage());
            }
            bean.setMethod(method);
            bean.setUrl(url);
            bean.setParameter(parameter);
            bean.setResult(result);
            bean.setStatus(status);
            bean.setUserId(userId);
            bean.setCreateTime(new Date());
            appInterfaceLogBll.addInterfaceLog(bean);
            return ResultBean.success("添加接口成功！");
        } catch (Exception e) {
            logger.error("添加接口异常：{}", e);
            return ResultBean.failed("添加接口失败!");
        }
    }

    /**
     * 删除用户日志
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> delete(@RequestParam(value="userId",required =true, defaultValue = "0") Integer userId) {
        logger.info("/interface/delete GET 方法被调用!!");
        try {
            appInterfaceLogBll.deleteInterfaceLog(userId);
            return ResultBean.success("删除日志成功!");
        } catch (Exception e) {
            logger.error("删除日志异常：{" + e + "}");
            return ResultBean.failed("删除日志异常!");
        }
    }


    /**
     * 获取错误日志
     * @return
     */
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getErrorLog(@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime,
                                          @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("env") String env) {
        logger.info("/interface/delete GET 方法被调用!!");
        try {
            List<AppInterfaceLogBean> list = appInterfaceLogBll.getErrorInterfaceLog(startTime,endTime,env,pageIndex,pageSize);
            return ResultBean.success("获取异常日志成功！",list);
        } catch (Exception e) {
            logger.error("获取异常日志失败：{" + e + "}");
            return ResultBean.failed("获取异常日志失败!");
        }
    }




}
