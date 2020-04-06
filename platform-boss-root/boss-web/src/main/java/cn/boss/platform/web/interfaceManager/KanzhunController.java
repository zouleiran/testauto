package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.InterfaceBean;
import cn.boss.platform.bean.interfaceManager.ProjectBean;
import cn.boss.platform.bll.InterfaceManager.*;
import cn.boss.platform.bll.kanzhun.KanzhunUtil;
import cn.boss.platform.bll.util.MD5Util;
import cn.boss.platform.bll.util.SignUtil;
import cn.boss.platform.service.interfaceManager.ExecuteService;
import cn.boss.platform.service.interfaceManager.RequestService;
import cn.boss.platform.service.interfaceManager.RestAssertService;
import cn.boss.platform.service.parameterManager.ParameterService;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.CaseForm;
import cn.boss.platform.web.form.DebugForm;
import com.alibaba.fastjson.JSON;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URL;
import java.util.Map;

/**
 * Created by admin on 2018/10/18.
 */
@Controller
@RequestMapping("/kanzhun/case")
public class KanzhunController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(KanzhunController.class);

    @Autowired
    private ProjectBll projectBll;
    @Autowired
    private InterfaceBll interfaceBll;
    @Autowired
    private EnvBll envBll;
    @Autowired
    private RequestService requestService;
    @Autowired
    private ExecuteService executeService;
    @Autowired
    private RestAssertService restAssertService;
    @Autowired
    private ParameterService parameterService;


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
            ProjectBean projectBean = projectBll.getProjectByid((form.getProjectId()==null) ? 1:form.getProjectId());
            InterfaceBean interfaceBean = interfaceBll.getInterfaceById(form.getInterfaceId());

            //前置处理动作及参数替换
            long currentTime = System.currentTimeMillis() / 1000;
            executeService.preProcessing(form.getPreProcessing(),(int)currentTime,0);
            //获取接口地址-根据传入的domain参数确定执行环境和参数替换
            String ip = envBll.getIpById(form.getEnvId());
            String url = interfaceBean.getHttp()+"://"+ ip + interfaceBean.getPath();
            url = parameterService.evalParameter(url);
            //头文件处理及参数替换
            String header = parameterService.evalParameter(form.getHeaders());
            Map headerMap = JSON.parseObject(header);

            Map paramerMap = null;
            String params = "";
            String sendUrl = "";
            Response responseResult = null;

            //判断接口类型，走不同签名方式
            int item = projectBean.getProjectTeam();
            if(item == 3 && form.getProjectId() == 13) {
                params = form.getParameters() == null ? "" : form.getParameters();
                params = params == null ? "" : params.replace("}{", ",");
                params = parameterService.evalParameter(params);
                paramerMap = JSON.parseObject(params);
                paramerMap = KanzhunUtil.kanzhunSign(interfaceBean.getPath(), paramerMap, null);
                String kanzhunUrl = interfaceBean.getHttp() + "://" + envBll.getIpById(form.getEnvId()) + "/api";
                //发送请求
                responseResult = executeService.send(kanzhunUrl, paramerMap, headerMap, interfaceBean.getMethod());
                sendUrl = kanzhunUrl + "?" + SignUtil.signStringAllParams(paramerMap, "&");
            }else if(((item == 1) && (form.getProjectId() ==2 || form.getProjectId() ==4 || form.getProjectId() ==22 || ip.equals("boss-api-qa.weizhipin.com"))) || item == 2){
                //默认走boss接口签名方式
                //自动向接口添加头文件zp-sig，zhipin新接口需要
                URL uri = new URL(url);
                if(headerMap != null && headerMap.containsKey("zp-user-id")){
                    String zpUserId = headerMap.get("zp-user-id") + "";
                    String zpSig = MD5Util.md5(zpUserId + "_" + uri.getHost() );
                    headerMap.put("zp-sig",zpSig);
                }
                //client_info参数
                String clientInfo = requestService.getClientInfo(item);
                //获取参数-包括公共参数和请求参数-参数替换-解码
                params = (form.getParameters()==null?"":form.getParameters()) + (form.getPublicParameters()==null?"":form.getPublicParameters()) + (StringUtils.isBlank(form.getT())?"":"{\"t\":" + "\""+ form.getT()+"\"}" +(StringUtils.isBlank(clientInfo)?"":"{\"client_info\":" + clientInfo+"}"));
                params = params==null?"":params.replace("}{",",");
                params = parameterService.evalParameter(params);
                //获取签名-替换
                String secretKey = form.getSecretKey();
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
                //签名后地址
                sendUrl = requestService.getSigUrl(url, params, secretKey,item);
                //发送请求
                responseResult = executeService.sendForBoss(url,paramerMap, headerMap, interfaceBean.getMethod());
            }else{
                params = form.getParameters() == null ? "" : form.getParameters();
                params = params == null ? "" : params.replace("}{", ",");
                params = parameterService.evalParameter(params);
                Map<String, Object> paramerMapOther = JSON.parseObject(params);
                responseResult = executeService.sendForBoss(url,paramerMapOther,headerMap,interfaceBean.getMethod());
                //地址链接
                sendUrl = url +"?"+(paramerMapOther== null?"":SignUtil.signStringAllParams(paramerMapOther,"&"));
            }
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
//            debug.setDebug(sendUrl,status,form.getSecretKey(),content,"");
            debug.setAllDebug(sendUrl,status,form.getSecretKey(),content,null,responseResult.getCookies(),"");
            if(status != form.getExpectedStatus()) {
                debug.setMessage("请求状态不匹配！");
            }else if(StringUtils.isBlank(form.getExpectedResult())){
                debug.setMessage("预期结果为空时调试成功！");
                //后置处理动作-成功后才会后置处理
                executeService.postProcessing(form.getPostProcessing(),headers+content,1,1);
            }else{
                try {
//                    String expected = form.getExpectedResult();
                    String expected =parameterService.evalParameter(form.getExpectedResult());
                    restAssertService.assertRest(responseResult,expected);
                    //后置处理动作-成功后才会后置处理
                    executeService.postProcessing(form.getPostProcessing(),headers+content,1,1);
                    debug.setMessage("调试成功！");
                }catch(AssertionError e) {
                    debug.setMessage("预期结果和实际结果不匹配！"+ "\n"+e);
                }catch (IllegalArgumentException e){
                    debug.setMessage("预期结果中json-key值不存在！"+"\n" + e);
                }catch (Exception e) {
                    debug.setMessage("接口断言异常！"+ "\n"+e);
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



}


