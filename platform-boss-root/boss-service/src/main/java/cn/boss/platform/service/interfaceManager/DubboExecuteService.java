package cn.boss.platform.service.interfaceManager;

import cn.boss.platform.bean.dubboManager.ZhipinDubboCaseBean;
import cn.boss.platform.bean.dubboManager.ZhipinDubboCaseExecuteLogBean;
import cn.boss.platform.bean.dubboManager.ZhipinDubboInterfaceBean;
import cn.boss.platform.bean.dubboManager.ZhipinEnvBean;
import cn.boss.platform.bll.dubboManager.ZhipinDubboCaseExecuteLogBll;
import cn.boss.platform.bll.dubboManager.ZhipinDubboInterfaceBll;
import cn.boss.platform.bll.dubboManager.ZhipinEnvBll;
import dubbo.dto.ConnectDTO;
import dubbo.dto.ResultDTO;
import dubbo.service.TelnetService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2018/12/25.
 */
@Service
public class DubboExecuteService {

    @Autowired
    ZhipinDubboInterfaceBll zhipinDubboInterfaceBll;
    @Autowired
    ZhipinDubboCaseExecuteLogBll zhipinDubboCaseExecuteLogBll;
    @Autowired
    private ZhipinEnvBll zhipinEnvBll;
    @Autowired
    private TelnetService telnetService;
    @Autowired
    private JsonPathService jsonPathService;

    private static final Logger logger = LoggerFactory.getLogger(DubboExecuteService.class);

    public void todo(List<ZhipinDubboCaseBean> list, Integer serialId , Integer taskId, String author) {
        logger.info("开始执行测试用例!!");
        //更新任务中用例条数
//        taskBll.updateStatus(taskId,list.size(),new Date());
        for (ZhipinDubboCaseBean caseBean : list) {
            doEachCase(caseBean, serialId, taskId, author);
        }
        logger.info("结束执行测试用例!!");
    }

    public void doEachCase(ZhipinDubboCaseBean caseBean, Integer serialId, Integer taskId, String author) {
        long sTime = System.currentTimeMillis();
        //设置预期结果和状态和用例编号
        ZhipinDubboCaseExecuteLogBean caseExecuteLogBean = new ZhipinDubboCaseExecuteLogBean(){{
            setTaskId(taskId);
            setSerialId(serialId);
            setAuthor(author);
            setEnvId(caseBean.getEnvId());
            setInterfaceId(caseBean.getInterfaceId());
            setCaseId(caseBean.getId());
            setExpectedResult(caseBean.getExpectedResult());
            setParameters(caseBean.getParameters());
            setCreateTime(new Date());
        }};
        try{
            //获取环境信息
            ZhipinEnvBean zhipinEnvBean = zhipinEnvBll.getZhipinEnvById(caseBean.getEnvId());
            //获取接口信息
            ZhipinDubboInterfaceBean zhipinDubboInterfaceBean = zhipinDubboInterfaceBll.getDubboInterfaceById(caseBean.getInterfaceId());
            ConnectDTO dto = new ConnectDTO(){{
                setMethodName(zhipinDubboInterfaceBean.getMethodName());
                setServiceName(zhipinDubboInterfaceBean.getInterfaceName());
                setConn(zhipinEnvBean.getAddress());
                setJson(caseBean.getParameters());
            }};
            //执行dubbo接口
            ResultDTO<Object> resultDTO;
            try{
                resultDTO = telnetService.send(dto);
            }catch (Exception e){
                resultDTO = ResultDTO.createExceptionResult(e, Object.class);
            }
            //执行时间
            caseExecuteLogBean.setExecTime(System.currentTimeMillis() - sTime);
            caseExecuteLogBean.setResponseResult(resultDTO.getData().toString());
            //断言
            if(!StringUtils.isBlank(caseBean.getExpectedResult())){
                //预期结果非空
                jsonPathService.assertDubboRest(resultDTO.getData().toString(),caseBean.getExpectedResult());
            }
            caseExecuteLogBean.setResult(true);
            zhipinDubboCaseExecuteLogBll.addExecuteLog(caseExecuteLogBean);

        }catch (AssertionError e){
            logger.error("doEachCase执行用例断言失败,预期结果和实际结果不匹配,AssertionError:{}", e);
            caseExecuteLogBean.setResult(false);
            zhipinDubboCaseExecuteLogBll.addExecuteLog(caseExecuteLogBean);
        }
        catch (Exception e) {
            logger.error("doEachCase执行用例异常,Exception:{}", e);
            caseExecuteLogBean.setResult(false);
            zhipinDubboCaseExecuteLogBll.addExecuteLog(caseExecuteLogBean);
        }
    }
}
