package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.batchCaseManager.BatchCaseBean;
import cn.boss.platform.bean.batchCaseManager.BatchCaseStepBean;
import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.CaseBean;
import cn.boss.platform.bean.interfaceManager.CaseExecuteLogBean;
import cn.boss.platform.bean.interfaceManager.InterfaceBean;
import cn.boss.platform.bll.batchCaseManager.BatchCaseBll;
import cn.boss.platform.bll.batchCaseManager.BatchCaseStepBll;
import cn.boss.platform.bll.InterfaceManager.CaseBll;
import cn.boss.platform.bll.InterfaceManager.CaseExecuteLogBll;
import cn.boss.platform.bll.InterfaceManager.EnvBll;
import cn.boss.platform.bll.InterfaceManager.InterfaceBll;
import cn.boss.platform.bll.util.MD5Util;
import cn.boss.platform.service.interfaceManager.ExecuteService;
import cn.boss.platform.service.interfaceManager.RequestService;
import cn.boss.platform.service.interfaceManager.RestAssertService;
import cn.boss.platform.service.parameterManager.ParameterService;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.CaseForm;
import cn.boss.platform.web.form.DebugForm;
import cn.boss.platform.web.form.ExecuteParam;
import com.alibaba.fastjson.JSON;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URL;
import java.util.*;

/**
 * Created by admin on 2018/8/11.
 */
@Controller
@RequestMapping("/boss/case")
public class CaseController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(CaseController.class);

    @Autowired
    private CaseBll caseBll;
    @Autowired
    private InterfaceBll interfaceBll;
    @Autowired
    private CaseExecuteLogBll caseExecuteLogBll;
    @Autowired
    private EnvBll envBll;
    @Autowired
    private BatchCaseBll batchCaseBll;
    @Autowired
    private BatchCaseStepBll batchCaseStepBll;
    @Autowired
    private RequestService requestService;
    @Autowired
    private ExecuteService executeService;
    @Autowired
    private RestAssertService restAssertService;
    @Autowired
    private ParameterService parameterService;


    /**
     * 添加用例
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> addCase(@Valid CaseForm form, BindingResult bindingResult) {
        logger.info("/case/add POST 方法被调用!!"+form.toString());
        try{
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            CaseBean bean = new CaseBean();
            BeanUtils.copyProperties(form,bean);
            bean.setCreateTime(new Date());
            bean.setUpdateTime(new Date());
            caseBll.addCase(bean);
            return ResultBean.success("添加用例成功！");
        } catch (Exception e) {
            logger.error("添加用例异常：{}", e);
            return ResultBean.failed("添加用例失败!");
        }
    }

    /**
     * 获取接口用例列表
     * @param interfaceId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam("interfaceId") Integer interfaceId, @RequestParam("pageIndex") Integer pageIndex,
                                   @RequestParam("pageSize") Integer pageSize) {
        try {
            logger.info("/case/list GET 方法被调用!! param: " + interfaceId,pageIndex,pageSize);
            List<CaseBean> list = caseBll.selectByInterfaceIdPagination(interfaceId, pageIndex, pageSize);
//            for (CaseBean c : list) {
//                c.setInterfaceBean(interfaceBll.get(c.getInterfaceId()));
//            }
//            int count = caseBll.count(interfaceId);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("caseList", list);
//                put("caseCount", count);
            }};
            return ResultBean.success("查询用例成功!", result);
        } catch (Exception e) {
            logger.error("查询用例异常：{}", e);
            return ResultBean.failed("查询用例异常!");
        }
    }

    /**
     * 删除用例
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> deleteCase(@RequestParam(value="id",required =true, defaultValue = "0") Integer id) {
        try{
            logger.info("/case/delete GET 方法被调用!! code: "+id);
            caseBll.deleteCase(id);
            return ResultBean.success("删除用例成功！");

        } catch (Exception e) {
            logger.error("删除用例异常：{}", e);
            return ResultBean.failed("删除用例异常!");
        }
    }

    /**
     * 更新用例
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public ResultBean<Object> update(@Valid CaseForm form, BindingResult bindingResult) {
        try{
            logger.info("/case/update GET 方法被调用!! , " + form.toString());
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            CaseBean caseBean = new CaseBean();
            BeanUtils.copyProperties(form,caseBean);
            caseBean.setUpdateTime(new Date());
            caseBll.updateCase(caseBean);
            return ResultBean.success("更新用例成功!!");
        }catch (Exception e) {
            logger.error("更新用例异常：{" + e + "}");
            return ResultBean.failed("更新用例异常!");
        }

    }

    /**
     * 获取单条用例
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getByCode(@RequestParam(value="id",required =true, defaultValue = "0") Integer id) {
        try {
            logger.info("/case/getCase GET 方法被调用!! , param: " + id);
            return ResultBean.success("查询单条用例成功!",caseBll.getCaseById(id));
        } catch (Exception e) {
            logger.error("查询用例异常：{}", e);
            return ResultBean.failed("查询单条用例异常!");
        }
    }

    /**
     * 批量执行单接口用例
     * @param execute
     * @return
     */
    @PostMapping("/execute")
    @ResponseBody
    public ResultBean<Object> execute(ExecuteParam execute) {
        try{
            logger.info("/case/execute GET 方法被调用!! ,", execute.toString());
            int[] arrayCase = null;
            int[] arrayInter = null;
            if (!StringUtils.isBlank(execute.getCaseIds())) {
                String[] split = execute.getCaseIds().split(",");
                arrayCase = Arrays.stream(split).mapToInt(Integer::valueOf).toArray();
            }
            if (!StringUtils.isBlank(execute.getInterfaceIds())) {
                String[] split = execute.getInterfaceIds().split(",");
                arrayInter = Arrays.stream(split).mapToInt(Integer::valueOf).toArray();
            }
            List<CaseBean> list = caseBll.getExecute(execute.getProjectId(),execute.getGroupId(),execute.getEnvId(),execute.getVersion(),arrayInter,arrayCase,execute.getAuthor());
            if (list.isEmpty()) {
                return ResultBean.failed("无可执行的用例！");
            }
            for (CaseBean c : list) {
                c.setInterfaceBean(interfaceBll.getInterfaceById(c.getInterfaceId()));
            }
            Integer serialId = StringUtils.isBlank(execute.getSerialId()) ? 0:Integer.parseInt(execute.getSerialId());
//            Integer serialId = Integer.parseInt((new Date().getTime() +"").substring(5));
            //执行用例
            executeService.todo(list, serialId, Integer.parseInt(execute.getTaskId()),execute.getAuthor(),null);
            logger.info("任务执行流水号：", serialId);
            logger.info("执行完毕，总共{"+list.size()+"}用例" );
            return ResultBean.success("执行完毕，总共执行 "+list.size()+" 条用例！" );
        } catch (Exception e) {
            logger.error("用例执行异常：{}", e);
            return ResultBean.failed("用例执行异常!");
        }
    }


    /**
     * 线上用例执行，给运维提供接口
     * @param ip
     * @param port
     * @param service
     * @return
     */
    @GetMapping("/execute/online")
    @ResponseBody
    public ResultBean<Object> executeOnline(@RequestParam(value = "ip",required=false) String ip,@RequestParam(value = "port", required=false) String port,
                                            @RequestParam(value = "service", required=false) String service) {
        try{
            if(StringUtils.isBlank(ip) || StringUtils.isBlank(port) || StringUtils.isBlank(service) ){
                return ResultBean.failed("参数不能为空！");
            }
            List<CaseBean> list = null;
            Integer taskId = null;
            if(service.equals("zhipin-geek")){
                list = caseBll.getExecute(22,"174",null,null,null,null,null);
                taskId = 73;

            }else if(service.equals("zhipin-boss")){
                list = caseBll.getExecute(22,"175",null,null,null,null,null);
                taskId = 74;
            }
            if (list.isEmpty()) {
                return ResultBean.failed("无可执行的用例！");
            }
            for (CaseBean c : list) {
                c.setInterfaceBean(interfaceBll.getInterfaceById(c.getInterfaceId()));
            }
            Integer serialId = Integer.parseInt((new Date().getTime() +"").substring(5));
            //执行用例
            executeService.todo(list, serialId, taskId,service +"部署",ip+":"+port);
            logger.info("任务执行流水号：", serialId);
            logger.info("执行完毕，总共{"+list.size()+"}用例" );
            //执行成功数
            int count = caseExecuteLogBll.countSerialIdExecuteLog(serialId);
            int successCaseCount = caseExecuteLogBll.getExecuteSucessCount(serialId);
            if(successCaseCount == count){
                return ResultBean.success("执行完毕，总共执行 "+count+" 条用例，成功 " +  successCaseCount + " 条！");
            }else{
                return ResultBean.failed("执行完毕，总共执行 "+count+" 条用例，成功 " +  successCaseCount + " 条！" );
            }

        }catch (Exception e) {
            logger.error("用例执行异常：{}", e);
            return ResultBean.failed("用例执行异常！");
        }

    }


    /**
     * 批量执行batch用例
     * @param execute
     * @return
     */
    @PostMapping("batchExecute")
    @ResponseBody
    public ResultBean<Object> batchExecute(ExecuteParam execute) {
        logger.info("/batchExecute/execute GET 方法被调用!! ,", execute.toString());
        CaseBean caseBean = new CaseBean();
        int[] arrayCase = null;
        if (!StringUtils.isBlank(execute.getCaseIds())) {
            String[] split = execute.getCaseIds().split(",");
            arrayCase = Arrays.stream(split).mapToInt(Integer::valueOf).toArray();
        }
        try {
            List<BatchCaseBean> batchCaselist = batchCaseBll.getBatchCaseIntegrade(execute.getProjectId(),execute.getGroupId(),execute.getEnvId(),execute.getBatchId());
            if (batchCaselist.isEmpty()) {
                return ResultBean.failed("无可执行的batch用例！");
            }
            for (BatchCaseBean c : batchCaselist) {
                List<BatchCaseStepBean> batchCaseSteplist = batchCaseStepBll.getBatchStepIntegradeBycaseId(c.getId(),arrayCase);
                Integer serialId = StringUtils.isBlank(execute.getSerialId()) ? 0:Integer.parseInt(execute.getSerialId());
                Integer taskId = StringUtils.isBlank(execute.getTaskId()) ? 0:Integer.parseInt(execute.getTaskId());
                for (BatchCaseStepBean b : batchCaseSteplist) {
                    BeanUtils.copyProperties(b,caseBean);
                    caseBean.setInterfaceBean(interfaceBll.getInterfaceById(b.getInterfaceId()));
                    //执行用例
                    logger.error("执行用例：",caseBean.toString());
                    executeService.doEachCase(caseBean, serialId, taskId,execute.getBatchId(),execute.getAuthor(),null);
                }
            }
            return ResultBean.success("执行完毕，总共执行 "+batchCaselist.size()+" 条batch用例！" );
        }catch (Exception e) {
                logger.error("用例执行异常：{}", e);
                return ResultBean.failed("用例执行异常!");
            }
    }




    /**
     * 调试单接口
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/debug/executed", method = RequestMethod.POST)
    @ResponseBody
    public Object debugExecute(@Valid CaseForm form, BindingResult bindingResult) {
        logger.info("/debug/executed POST 方法被调用!!"+form.toString());
        DebugForm debug = new DebugForm();
        try{
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            InterfaceBean interfaceBean = interfaceBll.getInterfaceById(form.getInterfaceId());
            //前置处理动作及参数替换
            long currentTime = System.currentTimeMillis() / 1000;
            executeService.preProcessing(form.getPreProcessing(),(int)currentTime,0);
            //获取接口地址-根据传入的domain参数确定执行环境和参数替换
            String url = interfaceBean.getHttp()+"://"+ envBll.getIpById(form.getEnvId()) + interfaceBean.getPath();
            url = parameterService.evalParameter(url);
            //头文件处理及参数替换
            String header = parameterService.evalParameter(form.getHeaders());
            Map headerMap = JSON.parseObject(header);
            //自动向接口添加头文件zp-sig，zhipin新接口需要
            URL uri = new URL(url);
            if(headerMap != null && headerMap.containsKey("zp-user-id")){
                String zpUserId = headerMap.get("zp-user-id") + "";
                String zpSig = MD5Util.md5(zpUserId + "_" + uri.getHost() );
                headerMap.put("zp-sig",zpSig);
            }

            //获取参数-包括公共参数和请求参数-参数替换-解码
            String params = (form.getParameters()==null?"":form.getParameters()) + (form.getPublicParameters()==null?"":form.getPublicParameters()) + (StringUtils.isBlank(form.getT())?"":"{\"t\":" + "\""+ form.getT()+"\"}");
            params = params==null?"":params.replace("}{",",");
            params = parameterService.evalParameter(params);
            //获取签名-替换
            String secretKey = form.getSecretKey();
            secretKey = StringUtils.isBlank(secretKey)? secretKey:parameterService.evalParameter(secretKey);
            //签名后sig值
            String sig = requestService.getSig(url, params, secretKey);
            Map paramerMap = JSON.parseObject(params) ;
            if(paramerMap !=null){
                paramerMap.put("sig",sig);
            }
            //发送请求
            Response responseResult = executeService.send(url,paramerMap, headerMap, interfaceBean.getMethod());
            //签名后地址
            String sendUrl = requestService.getSigUrl(url, params, secretKey,1);
            String content = null;
            String headers = null;
            int status = 0;
            if (responseResult !=null ||responseResult.asString() != null) {
                content = responseResult.asString();
                headers = responseResult.getHeaders().toString();
                status = responseResult.getStatusCode();
                logger.info("接口返回结果："+content);
            }
            //断言预期结果和实际结果
            debug.setDebug(sendUrl,status,secretKey,content,null,"");
            if(status != form.getExpectedStatus()) {
                debug.setMessage("请求状态不匹配！");
            }else if(StringUtils.isBlank(form.getExpectedResult())){
                debug.setMessage("预期结果为空时调试成功！");
                //后置处理动作-成功后才会后置处理
                executeService.postProcessing(form.getPostProcessing(),headers+content,1,1);
            }else{
                try {
                    //String expected = paramService.evalProcessing(form.getExpectedResult());
                    String expected = form.getExpectedResult();
                    restAssertService.assertRest(responseResult,expected);
                    debug.setMessage("调试成功！");
                    //后置处理动作-成功后才会后置处理
                    executeService.postProcessing(form.getPostProcessing(),headers+content,1,1);
                }catch(AssertionError e) {
                    debug.setMessage("预期结果和实际结果不匹配！"+ "\n"+e.getMessage());
                }catch (IllegalArgumentException e){
                    debug.setMessage("预期结果中json-key值不存在！"+"\n" + e.getMessage());
                }catch (Exception e) {
                    debug.setMessage("接口断言异常！"+ "\n"+e.getMessage());
                }
            }
        } catch (Exception e) {
            debug.setMessage("接口请求异常！"+ "\n"+ e.getMessage());
        }
        finally {
            RestAssured.reset();
        }
        return debug;
    }

    @GetMapping("/log/list")
    @ResponseBody
    public ResultBean<Object> logList(@RequestParam("caseId") Integer caseId, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        logger.info("开始获取单条case执行日志, param: " + caseId);
        logger.info("/log/list POST 方法被调用!! param:" + caseId, pageIndex, pageSize);
        try {
            List<CaseExecuteLogBean> list = caseExecuteLogBll.getCaseExecuteLogs(caseId, pageIndex, pageSize);
            int count = caseExecuteLogBll.countCaseExecuteLog(caseId);
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

    public static void main(String[] args) {
        System.out.println((new Date().getTime()+""));
        System.out.println((new Date().getTime()+"").substring(5));
    }

}
