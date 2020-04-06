package cn.boss.platform.service.autoCaseManager;
import cn.boss.platform.bean.autoManager.AutoCaseExecuteLogBean;
import cn.boss.platform.bean.autoManager.AutoCaseStepBean;
import cn.boss.platform.bll.autoCaseManager.AutoCaseExecuteLogBll;
import cn.boss.platform.doe.util.SeleniumUtil;
import cn.boss.platform.service.parameterManager.ParameterService;
import com.alibaba.fastjson.JSON;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/11/8.
 */
@Service
public class CaseStepService {

    private static final Logger logger = LoggerFactory.getLogger(CaseStepService.class);

    @Autowired
    private AutoCaseExecuteLogBll autoCaseExecuteLogBll;
    @Autowired
    private ParameterService parameterService;


    public void todo(List<AutoCaseStepBean> list, Integer serialId , Integer taskId, String author,WebDriver webdriver) {
        logger.info("开始执行测试用例!!");
        //更新任务中用例条数
//        taskBll.updateStatus(taskId,list.size(),new Date());
        for (AutoCaseStepBean autoCaseStepBean : list) {
            doEachCase(autoCaseStepBean, serialId, taskId, author,webdriver);
        }
        logger.info("结束执行测试用例!!");
    }


    public boolean doEachCase(AutoCaseStepBean caseBean, Integer serialId,Integer taskId,String author,WebDriver webdriver) {
        logger.debug("执行测试用例步骤，CaseBean:{},taskCode:{}", caseBean + "", serialId);
        //设置预期结果和状态和用例编号
        long sTime = System.currentTimeMillis();
        AutoCaseExecuteLogBean autoCaseExecuteLogBean = new AutoCaseExecuteLogBean(){{
            setProjectId(caseBean.getProjectId());
            setTaskId(taskId);
            setSerialId(serialId);
            setCaseId(caseBean.getCaseId());
            setCaseStepId(caseBean.getId());
            setStepName(caseBean.getName());
            setParameter(caseBean.getParameter());
            setAuthor(author);
            setCreateTime(new Date());
        }};

        try{
            //参数替换
            String parameter = parameterService.evalParameter(caseBean.getParameter());
            WebElement element = null;
            SeleniumUtil SeleniumUtil = new SeleniumUtil(webdriver);
            String action = caseBean.getAction();
            Map parameterMap = JSON.parseObject(parameter);
            if(action.equals("click") || action.equals("sendkeys") || action.equals("switchToFrame")){
                logger.info(parameterMap.get("findBy")+"", parameterMap.get("value")+"");
                element =  SeleniumUtil.generateElement(parameterMap.get("findBy") + "",parameterMap.get("value") + "");
                if(element == null){
                    logger.debug("元素不存在！");
                    autoCaseExecuteLogBean.setResponseResult("元素不存在:" + parameter);
                    autoCaseExecuteLogBean.setSuccess(0);
                    autoCaseExecuteLogBean.setExecTime(System.currentTimeMillis() - sTime);
                    autoCaseExecuteLogBll.addAutoCaseStepLog(autoCaseExecuteLogBean);
                    return false;
                }
            }
            String result = null;
            switch (caseBean.getAction()) {
                case "get":
                    result = SeleniumUtil.get(parameterMap.get("url") + "",parameterMap.get("maximize")+"");
                    break;
                case "click":
                    result = SeleniumUtil.click(element);
                    break;
                case "sendkeys":
                    result = SeleniumUtil.sendKeys(element,parameterMap.get("content") + "");
                    break;
                case "clear":
                    result = SeleniumUtil.clear(element);
                case "setCookie":
                    result = SeleniumUtil.addCookie(parameterMap.get("name") + "",parameterMap.get("value") + "");
                    break;
                case "switchToFrame":
                    result = SeleniumUtil.switchToFrame(element);
                    break;
                case "moveByOffset":
                    result = SeleniumUtil.moveByOffset(element,Integer.parseInt(parameterMap.get("x")+""),Integer.parseInt(parameterMap.get("y")+""));
                case "sleep":
                    Thread.sleep( Long.valueOf(parameterMap.get("time")+""));
                    result = "强制等待："+ parameterMap.get("time") + "秒";
                    break;
            }
            autoCaseExecuteLogBean.setExecTime(System.currentTimeMillis() - sTime);
            autoCaseExecuteLogBean.setResponseResult(result);
            autoCaseExecuteLogBean.setSuccess(1);
            autoCaseExecuteLogBll.addAutoCaseStepLog(autoCaseExecuteLogBean);
            return true;
        }catch (Exception e){
            logger.debug("执行测试用例失败！" + e);
            autoCaseExecuteLogBean.setExecTime(System.currentTimeMillis() - sTime);
            autoCaseExecuteLogBean.setResponseResult("执行测试用例失败！" + e);
            autoCaseExecuteLogBean.setSuccess(0);
            autoCaseExecuteLogBll.addAutoCaseStepLog(autoCaseExecuteLogBean);
            return false;
        }

    }



}