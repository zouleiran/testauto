package cn.boss.platform.web.batchCaseManager;

import cn.boss.platform.bean.batchCaseManager.BatchCaseBean;
import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.CaseExecuteLogBean;
import cn.boss.platform.bll.batchCaseManager.BatchCaseBll;
import cn.boss.platform.bll.InterfaceManager.CaseExecuteLogBll;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.BatchCaseForm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/9/6.
 */
@Controller
@RequestMapping("/boss/batchCase")
public class BatchCaseController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(BatchCaseController.class);

    @Autowired
    private BatchCaseBll batchCaseBll;
    @Autowired
    private CaseExecuteLogBll caseExecuteLogBll;

    /**
     * 添加批用例
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> addBatchCase(@Valid BatchCaseForm form, BindingResult bindingResult) {
        logger.info("/batchCase/add POST 方法被调用!!"+form.toString());
        try{
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            BatchCaseBean bean = new BatchCaseBean();
            BeanUtils.copyProperties(form,bean);
            bean.setCreateTime(new Date());
            bean.setUpdateTime(new Date());
            batchCaseBll.addBatchCase(bean);
            return ResultBean.success("添加业务用例成功！");
        } catch (Exception e) {
            logger.error("添加业务用例异常：{}", e);
            return ResultBean.failed("添加用例失败!");
        }
    }

    /**
     * 查询业务用例
     * @param id
     * @param projectId
     * @param groupId
     * @param envId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam(value="id",required =false) Integer id,@RequestParam(value="projectId",required =false) Integer projectId,
                                   @RequestParam(value="groupId",required =false) Integer groupId,@RequestParam(value="envId",required =false) Integer envId,
                                   @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize){
        logger.info("/batchCase/list GET 方法被调用!!");
        try{
            List<BatchCaseBean> list = batchCaseBll.getBatchCase(projectId,groupId,envId,id,pageIndex,pageSize);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("batchCaseList", list);
            }};
            return ResultBean.success("查询业务用例成功!",result);
        } catch (Exception e) {
            logger.error("查询业务用例异常：{}", e);
            return ResultBean.failed("查询业务用例失败!");
        }
    }

    /**
     * 删除用例
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> delete(@RequestParam(value="id",required =true, defaultValue = "0") Integer id) {
        try{
            logger.info("/batchCase/delete GET 方法被调用!! code: "+id);
            batchCaseBll.deleteBatchCase(id);
            return ResultBean.success("删除业务用例成功！");

        } catch (Exception e) {
            logger.error("删除业务用例异常：{}", e);
            return ResultBean.failed("删除业务用例异常!");
        }
    }

    @GetMapping("/log/list")
    @ResponseBody
    public ResultBean<Object> logList(@RequestParam("batchId") Integer batchId, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        logger.info("开始获取单条case执行日志, param: " + batchId);
        logger.info("/log/list POST 方法被调用!! param:" + batchId, pageIndex, pageSize);
        try {
            List<CaseExecuteLogBean> list = caseExecuteLogBll.getBatchCaseExecuteLogs(batchId, pageIndex, pageSize);
            int count = caseExecuteLogBll.countBatchCaseExecuteLog(batchId);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("caseLogList", list);
                put("caseLogCount", count);
            }};
            return ResultBean.success("获取日志成功!", result);
        } catch (Exception e) {
            logger.error("获取单条case执行日志接口异常：{" + e + "}");
            return ResultBean.failed("获取用例日志异常!");
        }
    }




}
