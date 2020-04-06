package cn.boss.platform.service.interfaceManager;

import cn.boss.platform.bean.interfaceManager.CaseBean;
import cn.boss.platform.bean.interfaceManager.CaseExecuteLogBean;
import cn.boss.platform.bean.interfaceManager.ParameterBean;
import cn.boss.platform.bean.interfaceManager.ProjectBean;
import cn.boss.platform.bll.InterfaceManager.*;
import cn.boss.platform.bll.kanzhun.KanzhunUtil;
import cn.boss.platform.bll.util.DealStrSub;
import cn.boss.platform.bll.util.MD5Util;
import cn.boss.platform.bll.util.SignUtil;
import cn.boss.platform.service.parameterManager.ParameterService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.ConnectionConfig;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.config.HttpClientConfig;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2018/8/10.
 */
@Service
public class ExecuteService {

    private static final Logger logger = LoggerFactory.getLogger(ExecuteService.class);


    @Autowired
    private ParameterBll parameterBll;
    @Autowired
    private CaseExecuteLogBll caseExecuteLogBll;
    @Autowired
    private EnvBll envBll;
    @Autowired
    private TaskBll taskBll;
    @Autowired
    private ProjectBll projectBll;
    @Autowired
    private ParameterService parameterService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private RestAssertService restAssertService;


    public void todo(List<CaseBean> list, Integer serialId , Integer taskId, String author,String serverIp) {
        logger.info("开始执行测试用例!!");
        //更新任务中用例条数
//        taskBll.updateStatus(taskId,list.size(),new Date());
        for (CaseBean caseBean : list) {
            doEachCase(caseBean, serialId, taskId,null, author,serverIp);
        }
        logger.info("结束执行测试用例!!");
    }

    /**
     * 执行单条用例
     * @param caseBean
     * @param serialId
     * @param taskId
     * @param author
     * @return
     */
    public boolean doEachCase(CaseBean caseBean, Integer serialId,Integer taskId,Integer batchId,String author,String serverIp) {
        logger.debug("执行测试用例，CaseBean:{},taskCode:{}", caseBean.toString(), serialId);
        long sTime = System.currentTimeMillis();
        //设置预期结果和状态和用例编号
        CaseExecuteLogBean caseExecuteLogBean = new CaseExecuteLogBean(){{
            setTaskId(taskId);
            setSerialId(serialId);
            setBatchId(batchId);
            setAuthor(author);
            setProjectId(caseBean.getProjectId());
            setGroupId(caseBean.getGroupId());
            setEnvId(caseBean.getEnvId());
            setInterfaceId(caseBean.getInterfaceId());
            setCaseId(caseBean.getId());
            setExpectedResult(caseBean.getExpectedResult());

            setExpectedStatus(caseBean.getExpectedStatus());
            setCreateTime(new Date());
        }};
        try{
            //前置处理动作
            preProcessing(caseBean.getPreProcessing(),serialId,taskId);
            //兼容线上接口测试
            if(StringUtils.isBlank(serverIp)){
                //获取接口路径
                serverIp = envBll.getIpById(caseBean.getEnvId());
            }
            String url = caseBean.getInterfaceBean().getHttp() + "://" + serverIp + caseBean.getInterfaceBean().getPath();
            url = parameterService.evalParameter(url);
            URL uri = new URL(url);
            //获取头文件-替换
            String header = parameterService.evalParameter(caseBean.getHeaders());
            Map headerMap = JSON.parseObject(header);
            Map paramerMap = null;
            String params = "";
            Response responseResult = null;
            String sendUrl = "";

            ProjectBean projectBean = projectBll.getProjectByid((caseBean.getProjectId()==null) ? 1:caseBean.getProjectId());
            //判断接口类型，走不同签名方式
            int item = projectBean.getProjectTeam();
            if(item == 3 && caseBean.getProjectId() == 13){
                params = caseBean.getParameters() == null ? "" : caseBean.getParameters();
                params = params == null ? "" : params.replace("}{", ",");
                params = parameterService.evalParameter(params);
                paramerMap = JSON.parseObject(params) ;
                paramerMap = KanzhunUtil.kanzhunSign(caseBean.getInterfaceBean().getPath(), paramerMap, null);
                String kanzhunUrl = caseBean.getInterfaceBean().getHttp() + "://" + serverIp  + "/api";
                //发送请求
                responseResult = send(kanzhunUrl, paramerMap, headerMap, caseBean.getInterfaceBean().getMethod());
                sendUrl = kanzhunUrl + "?" + SignUtil.signStringAllParams(paramerMap, "&");
                //在boss_web下面也会执行api的接口（比如登录），所以加上了
            }else if(((item == 1) && (caseBean.getProjectId() ==2 || caseBean.getProjectId() ==4) || caseBean.getProjectId() ==22  || serverIp.equals("boss-api-qa.weizhipin.com")) || item == 2){
                //自动向接口添加头文件zp-sig，zhipin新接口需要
                if(headerMap != null && headerMap.containsKey("zp-user-id")){
                    String zpUserId = headerMap.get("zp-user-id") + "";
                    String zpSig = MD5Util.md5(zpUserId + "_" + uri.getHost());
                    headerMap.put("zp-sig",zpSig);
                }
                //client_info参数
                String clientInfo = requestService.getClientInfo(item);
                //获取参数-包括公共参数+请求参数+t-替换
                params = (caseBean.getParameters()==null?"":caseBean.getParameters()) + (caseBean.getPublicParameters()==null?"":caseBean.getPublicParameters()) + (StringUtils.isBlank(caseBean.getT())?"":"{\"t\":" + "\""+ caseBean.getT()+"\"}" + (StringUtils.isBlank(clientInfo)?"":"{\"client_info\":" + clientInfo+"}"));
                params = params==null?"":params.replace("}{",",");
                params = parameterService.evalParameter(params);
                //获取签名-替换
                String secretKey = caseBean.getSecretKey();
                secretKey = StringUtils.isBlank(secretKey)? secretKey:parameterService.evalParameter(secretKey);
                //签名后sig值，boss和店长签名不一致
                String sig ;
                if(item == 2){
                    sig = requestService.getDianZhangSig(url, params, secretKey);
                }else{
                    sig = requestService.getSig(url, params, secretKey);
                }
                paramerMap = JSON.parseObject(params) ;
                if(paramerMap !=null){
                    paramerMap.put("sig",sig);
                }
                //发送请求
                responseResult = send(url,paramerMap, headerMap, caseBean.getInterfaceBean().getMethod());
                //签名后地址
                sendUrl = requestService.getSigUrl(url, params, secretKey,item);
            }else{
                params = caseBean.getParameters() == null ? "" : caseBean.getParameters();
                params = params == null ? "" : params.replace("}{", ",");
                params = parameterService.evalParameter(params);
                paramerMap = JSON.parseObject(params) ;
                responseResult = send(url,paramerMap,headerMap,caseBean.getInterfaceBean().getMethod());
                //地址链接
                sendUrl = url +"?"+(paramerMap==null?"":SignUtil.signStringAllParams(paramerMap,"&"));
            }

            //设置参数和链接地址
            caseExecuteLogBean.setParameters(params);
            caseExecuteLogBean.setUrl(sendUrl);
            //执行时间
            caseExecuteLogBean.setExecTime(System.currentTimeMillis() - sTime);
            if (responseResult ==null ||responseResult.asString() == null) {
                //返回结果为空
                caseExecuteLogBean.setResult(false);
                caseExecuteLogBll.addLog(caseExecuteLogBean);
                return false;
            }
            //获取结果状态
            int statusCode = responseResult.getStatusCode();
            //获取结果内容和头文件
            String resultContent = responseResult.asString();
            String headers = responseResult.headers().toString();
            logger.info("返回结果："+resultContent);
            logger.info("返回头文件："+headers);
            //设置状态和结果
            caseExecuteLogBean.setResponseStatus(statusCode);
            caseExecuteLogBean.setResponseResult(resultContent);
            if(statusCode != caseBean.getExpectedStatus()){
                //如果状态不相等
                caseExecuteLogBean.setResult(false);
                caseExecuteLogBll.addLog(caseExecuteLogBean);
                return false;
            }else if(StringUtils.isBlank(caseBean.getExpectedResult())) {
                //如果状态相等，预期结果为空
                caseExecuteLogBean.setResult(true);
                caseExecuteLogBll.addLog(caseExecuteLogBean);
                //后置处理动作-成功后才会后置处理
                postProcessing(caseBean.getPostProcessing(),headers+resultContent,serialId,taskId);
                return true;
            }else{
                String expected = caseBean.getExpectedResult();
                expected = StringUtils.isEmpty(expected)?"":parameterService.evalParameter(expected);
                caseExecuteLogBean.setExpectedResult(expected);
                //断言
                restAssertService.assertRest(responseResult,expected);
                caseExecuteLogBean.setResult(true);
                caseExecuteLogBll.addLog(caseExecuteLogBean);
                //后置处理动作-成功后才会后置处理
                postProcessing(caseBean.getPostProcessing(),headers+resultContent,serialId,taskId);
                return true;
            }
        }catch(AssertionError e) {
            logger.error("doEachCase执行用例断言失败,预期结果和实际结果不匹配,AssertionError:{}", e);
            caseExecuteLogBean.setResult(false);
            caseExecuteLogBll.addLog(caseExecuteLogBean);
            return false;
        } catch(IllegalArgumentException e) {
            logger.error("doEachCase执行用例断言异常,json-key值不存在,IllegalArgumentException:{}", e);
            caseExecuteLogBean.setResult(false);
            caseExecuteLogBll.addLog(caseExecuteLogBean);
            return false;
        }catch (Exception e) {
            logger.error("doEachCase执行用例异常,Exception:{}", e);
            caseExecuteLogBean.setResult(false);
            caseExecuteLogBll.addLog(caseExecuteLogBean);
            return false;
        }
//        finally {
//            RestAssured.reset();
//        }
    };


    /**
     * 发送请求
     * @param url
     * @param params
     * @param headers
     * @param method
     * @return
     */
    public Response sendinfo(@Nonnull String url, Map<String, Object> params, Map<String, Object> headers, @Nonnull String method) {
        try {
            RequestSpecification requestSpecification = RestAssured.given();
            //以json格式接收数据
            requestSpecification.headers("Accept", "application/JSON");
            if (headers != null) {
                requestSpecification.headers(headers);
            }
            if (params != null) {
                requestSpecification.formParams(params);
            }
            switch (method.toUpperCase()) {
                case "GET":
                    return requestSpecification.get(url);
                case "POST":
                    return requestSpecification.post(url);
                case "PUT":
                    return requestSpecification.put(url);
                case "DELETE":
                    return requestSpecification.delete(url);
                default:
                    return null;
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}", e);
            return null;
        }
    }

    /**
     * 发送http请求-boss直聘-店长直聘使用
     * @param url
     * @param method
     * @return
     */
    public Response sendForBoss(@Nonnull String url, Map<String, Object> params, Map<String, Object> headers, @Nonnull String method) {
        try {
            RestAssured.registerParser("text/plain", Parser.JSON);
            RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("utf-8"));
            RequestSpecification requestSpecification = RestAssured.given();
            //以json格式接收数据
            requestSpecification.headers("Accept", "application/JSON");
            if (headers != null) {
                requestSpecification.headers(headers);
            }
            if (params != null) {
                requestSpecification.formParams(params);
            }
            switch (method.toUpperCase()) {
                case "GET":
                    return requestSpecification.get(url);
                case "POST":
                    return requestSpecification.post(url);
                case "PUT":
                    return requestSpecification.put(url);
                case "DELETE":
                    return requestSpecification.delete(url);
                default:
                    return null;
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}", e);
            return null;
        }
    }

    /**
     * 发送http请求-kanzhuan项目使用
     * @param url
     * @param method
     * @return
     */
    public Response send(@Nonnull String url, Map<String, Object> params, Map<String, Object> headers, @Nonnull String method) {
        try {
            RestAssured.registerParser("text/plain", Parser.JSON);
            //kanzhun接口使用
            RestAssured.registerParser("application/json-gz",Parser.JSON);
            //请求配置（编码格式和超时时间）
            RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("utf-8"))
                    .httpClient(HttpClientConfig.httpClientConfig()
                            .setParam("http.connection.timeout", 3000)
                            .setParam("http.socket.timeout", 3000)
                            .setParam("http.connection.request.timeout",3000));

            RequestSpecification requestSpecification = RestAssured.given();
            //以json格式接收数据
            requestSpecification.headers("Accept", "application/JSON");
            if (headers != null) {
                requestSpecification.headers(headers);
            }
            if (params != null) {
                requestSpecification.formParams(params);
            }
            switch (method.toUpperCase()) {
                case "GET":
                    return requestSpecification.get(url);
                case "POST":
                    return requestSpecification.post(url);
                case "PUT":
                    return requestSpecification.put(url);
                case "DELETE":
                    return requestSpecification.delete(url);
                default:
                    return null;
            }
        } catch (Exception e) {
            logger.error("调用接口失败: {}", e);
            return null;
        }
    }

    /**
     * //前置处理动作：包括参数替换，执行用例(多用例执行)和左右正则表达式获取值
     * @param preProcessing
     * @return
     */
    public void preProcessing(String preProcessing,int serialId, int taskId) {
        try {
            //先参数替换
            preProcessing = parameterService.evalParameter(preProcessing);
            if(!StringUtils.isBlank(preProcessing)){
                //有执行用例的执行用例
                Map<String, String> mapProcessing = JSON.parseObject(preProcessing,new TypeReference<LinkedHashMap<String, String>>(){});
                for (String key : mapProcessing.keySet()) {
                    if (key.contains("case")) {
                        //正则表达式提取器
                        String caseValue = mapProcessing.get(key);
                        String[] split = caseValue.split(",");
                        for( int i = 0 ; i < split.length ; i++) {
                            parameterService.getCaseResult(Integer.parseInt(split[i]),serialId,taskId);
                        }

                    }
                }
            }
        }catch (Exception e) {
            logger.error("获取密码成功：{}", e);
        }
    }


    /**
     * 后置处理动作：包括参数替换，执行用例(多用例执行)和左右正则表达式获取值
     * @param postProcessing
     * @param result
     */
    public void postProcessing(String postProcessing,String result, int serialId, int taskId){
        try{
            if(!StringUtils.isBlank(postProcessing)) {
                //先参数替换
                postProcessing = parameterService.evalParameter(postProcessing);
                LinkedHashMap<String, String> mapProcessing = JSON.parseObject(postProcessing,new TypeReference<LinkedHashMap<String, String>>(){});
                for(String key : mapProcessing.keySet()){
                    //正则表达式提取器
                    String regular = mapProcessing.get(key)+"";
                    //执行用例后置处理
                    Pattern pattern = Pattern.compile("^\\d+$");
                    Matcher isNum = pattern.matcher(regular);
                    //key为case且value为数字
                    if (key.equals("case") && isNum.matches()) {
                        parameterService.getCaseResult(Integer.parseInt(regular),serialId,taskId);
                    } else{
                        //正则获取预期结果
                        String actualRegular = DealStrSub.getSubUtilSimple(result, regular);
                        ParameterBean parameterBean = parameterBll.selectByName(key);
//                        if(!StringUtils.isBlank(actualRegular) && parameterBean != null){
//                            parameterBean.setValue(actualRegular);
//                            parameterBean.setUpdateTime(new Date());
//                            //更新数据
//                            parameterBll.updateParameter(parameterBean);
//                        }else if(StringUtils.isBlank(actualRegular) && parameterBean != null){
//                            parameterBean.setValue(regular);
//                            parameterBean.setUpdateTime(new Date());
//                            //更新数据
//                            parameterBll.updateParameter(parameterBean);
//                        }
                        if (parameterBean != null) {
                            parameterBean.setValue(actualRegular);
                            parameterBean.setUpdateTime(new Date());
                            //更新数据
                            parameterBll.updateParameter(parameterBean);
                        }
                    }
                }
            }
        }catch (Exception e) {
            logger.error("后置处理失败：{}", e);
        }
    }

    public static void main(String[] args) {


//        1、单个url接口请求平均时间计算
        String url = "http://apm.weizhipin.com/api/app/monitor/result/detail/list?appKey=n9mObpaxi2d2AFbV&action=action_sys_url_monitor&pageNo=1&pageSize=20000&version=&types=&before=0&userId=&keyword=Batch|BossF1GetList";
        Response result = RestAssured.given().
                headers("Accept", "application/JSON").
                headers("Cookie", "t_datamonitor=NGbbU87ajm2Vjs; lastCity=101010100; toUrl=http%3A%2F%2Fboss-m-qa.weizhipin.com%2F; td_cookie=2602468123").
                when().
                get(url);
        Map paramerMap = new HashMap<String, String>();
        paramerMap = JSON.parseObject(result.asString());
        List<Object> test = (List<Object>) JSON.parseObject(paramerMap.get("zpData").toString()).get("results");
        int requestTime = 0;
        int time = 0;
        int traffic = 0;
        for (Object c : test) {
            long timeTemp = Long.parseLong(JSON.parseObject(JSON.parseObject(JSON.parseObject(c.toString()).get("detail").toString()).get("actionp2").toString()).get("time").toString());
            String faile = (JSON.parseObject(JSON.parseObject(JSON.parseObject(c.toString()).get("detail").toString()).get("actionp2").toString()).get("fail").toString());
//            System.out.println(faile);

            if(faile == "true"){
                System.out.println(c.toString());
            }

//            if (timeTemp > 1572999300000L && timeTemp < 1572999600000L) {
//                int requestTimeOne = Integer.parseInt(JSON.parseObject(JSON.parseObject(JSON.parseObject(c.toString()).get("detail").toString()).get("actionp2").toString()).get("requestTime").toString());
//                if(requestTimeOne > 1000){
//                    System.out.println(requestTime);
//                    System.out.println(c.toString());
//                }
//                String faile = (JSON.parseObject(JSON.parseObject(JSON.parseObject(c.toString()).get("detail").toString()).get("actionp2").toString()).get("fail").toString());
//                requestTime = requestTime + requestTimeOne;
//                time++;
////                System.out.println(faile);
////                System.out.println(requestTime);
////                System.out.println(time);
//            }
        }
    }


        //2、所有url接口请求平均时间计算
//        String url = "http://apm.weizhipin.com/api/app/monitor/result/detail/list?appKey=n9mObpaxi2d2AFbV&action=action_sys_net_monitor&pageNo=1&pageSize=50000&version=&types=&before=0&userId=&keyword=";
//        Response result = RestAssured.given().
//                headers("Accept", "application/JSON").
//                headers("Cookie", "t_datamonitor=NGbbU87ajm2Vjs; lastCity=101010100; toUrl=http%3A%2F%2Fboss-m-qa.weizhipin.com%2F; td_cookie=2283139158").
//                when().
//                get(url);
//        Map paramerMap = new HashMap<String, String>();
//        paramerMap = JSON.parseObject(result.asString());
//        List<Object> test = (List<Object>) JSON.parseObject(paramerMap.get("zpData").toString()).get("results");
//        int requestTime = 0;
//        int time = 0;
//        int traffic = 0;
//        for (Object c : test) {
//            long timeTemp = Long.parseLong(JSON.parseObject(JSON.parseObject(JSON.parseObject(c.toString()).get("detail").toString()).get("actionp2").toString()).get("time").toString());
//            if (timeTemp > 1572901800000L && timeTemp < 1572902100000L) {
//                int requestTimeOne = Integer.parseInt(JSON.parseObject(JSON.parseObject(JSON.parseObject(c.toString()).get("detail").toString()).get("actionp2").toString()).get("requestTime").toString());
//                Long requestTimeTwo = Long.parseLong(JSON.parseObject(JSON.parseObject(JSON.parseObject(c.toString()).get("detail").toString()).get("actionp").toString()).get("time").toString());
//                Long failCount = Long.parseLong(JSON.parseObject(JSON.parseObject(JSON.parseObject(c.toString()).get("detail").toString()).get("actionp2").toString()).get("failCount").toString());
//                int timeOne = Integer.parseInt(JSON.parseObject(JSON.parseObject(JSON.parseObject(c.toString()).get("detail").toString()).get("actionp2").toString()).get("count").toString());
//                requestTime = requestTime + requestTimeOne;
//                time = time + timeOne;
//                if(requestTimeOne > 10000){
//                    System.out.println("请求时长");
//                    System.out.println(requestTimeTwo);
//                }
//                if(failCount >= 1L){
//                    System.out.println("失败次数");
//                    System.out.println(failCount);
//                    System.out.println(requestTimeTwo);
//                }
//                System.out.println(requestTimeOne);
//                System.out.println(timeOne);
//            }
//        }
//        System.out.println(requestTime);
//        System.out.println(time);
//        System.out.println(requestTime / time);
//    }

        //2、单个url接口请求平均时间计算
//        String url = "http://apm.weizhipin.com/api/app/monitor/result/detail/list?appKey=n9mObpaxi2d2AFbV&action=action_sys_url_monitor&pageNo=1&pageSize=100000&version=&types=&before=0&userId=&keyword=Batch|BossF1GetList";
//        Response result = RestAssured.given().
//                headers("Accept", "application/JSON").
//                headers("Cookie", "t_datamonitor=NGbbU87ajm2Vjs; lastCity=101010100; toUrl=http%3A%2F%2Fboss-m-qa.weizhipin.com%2F; td_cookie=2283139158").
//                when().
//                get(url);
//        Map paramerMap = new HashMap<String, String>();
//        paramerMap = JSON.parseObject(result.asString());
//        List<Object> test = (List<Object>) JSON.parseObject(paramerMap.get("zpData").toString()).get("results");
//        long requestTime = 0;
//        int time = 0;
//        int traffic = 0;
//        for (Object c : test) {
//            long requestTimeOne = Long.parseLong(JSON.parseObject(JSON.parseObject(JSON.parseObject(c.toString()).get("detail").toString()).get("actionp2").toString()).get("requestTime").toString());
//            time ++ ;
//            requestTime = requestTime + requestTimeOne;
//        }
//        System.out.println(requestTime);
//        System.out.println(time);
//        System.out.println(requestTime/time);
//    }



}
