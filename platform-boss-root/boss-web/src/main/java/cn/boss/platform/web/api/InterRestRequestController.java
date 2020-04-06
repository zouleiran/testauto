
package cn.boss.platform.web.api;


import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.ProjectBean;
import cn.boss.platform.bll.InterfaceManager.ProjectBll;
import cn.boss.platform.bll.kanzhun.KanzhunUtil;
import cn.boss.platform.bll.util.MD5Util;
import cn.boss.platform.bll.util.SecurityUtils;
import cn.boss.platform.bll.util.SignUtil;
import cn.boss.platform.service.interfaceManager.ExecuteService;
import cn.boss.platform.service.parameterManager.ParameterService;
import cn.boss.platform.web.form.DebugForm;
import cn.boss.platform.web.form.RequestForm;
import com.alibaba.fastjson.JSON;
import com.jayway.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.boss.platform.service.interfaceManager.RequestService;

import javax.validation.Valid;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *创建时间：2017年7月24日 下午5:39:15 
 *类名：
 * @author caosenquan
 */
@Controller
@RequestMapping("/")
public class InterRestRequestController extends AbstractBaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(InterRestRequestController.class);

	@Autowired
	private ExecuteService executeService;
	@Autowired
	private RequestService requestService;
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private ProjectBll projectBll;

	
	/**
	 * 实现get和post请求接口（带有签名方法）适合自己公司
	 * 
	 * @return
	 */
	@RequestMapping(value = "/request/key", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> request(@Valid RequestForm form, BindingResult bindingResult ) {
		logger.info("/request/key方法被调用" + form);
		try {
			Response responseResult = null;
			String errorMsg = validateData(bindingResult);
			if (!StringUtils.isEmpty(errorMsg)) {
				logger.info("params error:{}", errorMsg);
				return ResultBean.failed(errorMsg);
			}
			//默认为2
			if(form.getProjectId() ==null){
				form.setProjectId(2);
			}
			ProjectBean projectBean = projectBll.getProjectByid((form.getProjectId()==null) ? 1:form.getProjectId());
			//获取接口地址和域名
			String url = form.getUrl();
			URL uri = new URL(url);
			String ip = uri.getHost();

			Map<String,Object> headerMap = new HashMap<>();
			Map<String,Object> cookieMap = new HashMap<>();
			String sendUrl = "";
			//获取cookie
			if(!StringUtils.isBlank(form.getCookie())){
				cookieMap = JSON.parseObject(form.getCookie());
			}
			//获取参数
			String params = form.getParameter();
			params = parameterService.evalParameter(params);
			//获取头文件
			if(!StringUtils.isBlank(form.getHeaders())){
				String header = parameterService.evalParameter(form.getHeaders());
				headerMap = JSON.parseObject(header);
			}
			//判断接口类型，走不同签名方式
			int item = projectBean.getProjectTeam();
			//看准网
			if(item == 3 && form.getProjectId() == 13){
				Map<String, Object> paramerMap = JSON.parseObject(params);
				paramerMap = KanzhunUtil.kanzhunSign(uri.getPath(),paramerMap,null);
				String kanzhunUrl = "http://"+ ip + "/api";
				//发送请求
				responseResult = executeService.send(kanzhunUrl,paramerMap, headerMap, form.getMethod());
				System.out.println(responseResult.asString());
				sendUrl = kanzhunUrl +"?"+SignUtil.signStringAllParams(paramerMap,"&");
				//boss直聘项目和店长直聘
			}else if(((item == 1) && (form.getProjectId() ==2 || form.getProjectId() ==4 || form.getProjectId() ==22) || ip.equals("boss-api-qa.weizhipin.com")) || item == 2){
				if(headerMap != null && headerMap.containsKey("zp-user-id")){
					String zpUserId = headerMap.get("zp-user-id") + "";
					String zpSig = MD5Util.md5(zpUserId + "_" + ip);
					headerMap.put("zp-sig",zpSig);
				}
				//client_info参数添加
				String clientInfo = requestService.getClientInfo(item);
				//登录接口不要client_info
				if(url.contains("user/login") || url.contains("user/codeLogin")){
					params = params;
				}else{
					params = params + (StringUtils.isBlank(clientInfo)?"":"{\"client_info\":" + clientInfo+"}");
				}
				params = params==null?"":params.replace("}{",",");
				//获取签名key
				String signKey = form.getSignKey();
				//签名后sig值，boss和店长签名不一致
				String sig ;
				if(item == 2){
					sig = requestService.getDianZhangSig(url, params, signKey);
				}else{
					sig = requestService.getSig(url, params, signKey);
				}
				Map<String, Object> mapTypes = JSON.parseObject(params);
				if(mapTypes !=null){
					mapTypes.put("sig", sig);
				}
				responseResult = executeService.send(url,mapTypes,headerMap,form.getMethod());
				//签名后地址
				sendUrl = requestService.getSigUrl(url, params, signKey,item);
			}else{
				Map<String, Object> paramerMapOther = JSON.parseObject(params);
				responseResult = executeService.send(url,paramerMapOther,headerMap,form.getMethod());
				//地址链接
				sendUrl = url +"?"+SignUtil.signStringAllParams(paramerMapOther,"&");
			}
			DebugForm debug = new DebugForm();
//			debug.setDebug(sendUrl,responseResult.getStatusCode(),form.getSignKey(),responseResult.asString(),"");
			debug.setAllDebug(sendUrl,responseResult.getStatusCode(),form.getSignKey(),responseResult.asString(),null,responseResult.getCookies(),"");
			return ResultBean.success("接口调用成功！",debug);
		} catch (Exception e) {
			logger.error("接口调用成功：{}", e);
		return ResultBean.failed("接口调用失败!");
	}
	}



	@RequestMapping(value = "request/rc4", method = RequestMethod.GET)
	@ResponseBody
	public  ResultBean<Object> rcSign(@RequestParam("data") String data,@RequestParam("key") String key) {
		logger.info("/request/rc4方法被调用");
		try{
			return ResultBean.success("加密成功！", SecurityUtils.rc4Encrypt(data,key));
		}catch (Exception e) {
			logger.error("加密失败：{}", e);
			return ResultBean.failed("加密失败!");
		}
	}

	/**
	 * 实现get和post请求接口（带有签名方法）最终公共版
	 *
	 * @return
	 */
	@RequestMapping(value = "/request/common", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Object> requestCommon(@Valid RequestForm form, BindingResult bindingResult) {
		logger.info("/request/key方法被调用" + form);
		try {
			String errorMsg = validateData(bindingResult);
			if (!StringUtils.isEmpty(errorMsg)) {
				logger.info("params error:{}", errorMsg);
				return ResultBean.failed(errorMsg);
			}
			//获取接口地址
			String url = form.getUrl();
			Map<String, Object> headerMap = new HashMap<>();
			Map<String, Object> cookieMap = new HashMap<>();
			//获取cookie
			if (!StringUtils.isBlank(form.getCookie())) {
				cookieMap = JSON.parseObject(form.getCookie());
			}
			//添加头文件
			if (!StringUtils.isBlank(form.getHeaders())) {
				headerMap = JSON.parseObject(form.getHeaders());
			}
			//获取参数
			String params = form.getParameter();
			Map<String, Object> mapTypes = JSON.parseObject(params);
			Response responseResult = executeService.sendinfo(url, mapTypes, headerMap, form.getMethod());
			DebugForm debug = new DebugForm();
			System.out.println(responseResult.asString());
			Map<String, String> cookies = responseResult.cookies();
			Map<String, String> responseMap = null;
			try{
				if(responseResult !=null){
					responseMap = responseResult.as(Map.class);
				}
				debug.setResponse(responseResult.asString());
				debug.setHeadersDebug(url, responseResult.getStatusCode(), responseMap, responseResult.headers().toString(), cookies, "执行成功！");
			} catch (Exception e) {
				//非json下只返回字符串
				debug.setDebug(url, responseResult.getStatusCode(), "",responseResult.asString(),cookies, "执行成功！");
			}

			return ResultBean.success("执行成功！", debug);
		} catch (Exception e) {
			logger.error("执行异常：{}", e);
			return ResultBean.failed("执行失败!");
		}
	};



}
