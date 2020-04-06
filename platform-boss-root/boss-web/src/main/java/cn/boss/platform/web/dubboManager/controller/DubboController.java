package cn.boss.platform.web.dubboManager.controller;


import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.dubboManager.ZhipinEnvBean;
import cn.boss.platform.bll.dubboManager.ZhipinEnvBll;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.dubboManager.form.ConnectForm;
import com.alibaba.fastjson.JSON;
import dubbo.dto.ConnectDTO;
import dubbo.dto.ResultDTO;
import dubbo.model.PointModel;
import dubbo.service.TelnetService;
import dubbo.service.impl.TelnetBaseService;
import dubbo.util.ParamUtil;
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

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/12/6.
 */
@Controller
@RequestMapping("/boss/dubbo")
public class DubboController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(DubboController.class);

    @Autowired
    private TelnetService telnetService;
    @Autowired
    private ZhipinEnvBll zhipinEnvBll;


    /**
     * 简单模式调用
     * @param dto
     * @return
     */
    @RequestMapping(value = "/doSendWithTelnet", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO<Object> doSendWithTelnet(ConnectDTO dto) {
        logger.info("DubboController.doSendWithTelnet({})", JSON.toJSONString(dto));
        ResultDTO<Object> resultDTO;
        try {
            resultDTO = telnetService.send(dto);
        } catch (Exception e) {
            resultDTO = ResultDTO.createExceptionResult(e, Object.class);
        }
        return resultDTO;
    }


    /**
     *
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/doListTelnet", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> doListService(@Valid ConnectForm form, BindingResult bindingResult){
        logger.info("DubboController.doListService({})", JSON.toJSONString(form));
        try{
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            PointModel model = ParamUtil.parsePointModel(form.getConn());
            int timeout = form.getTimeout() <= 0 ? 10000 : form.getTimeout();// socket延迟时间：5000ms
            TelnetBaseService ws = new TelnetBaseService(model.getIp(), model.getPort(),timeout);

            String str = ws.sendCommand(form.getTelnetKey());
            str = new String(str.getBytes("ISO-8859-1"), "GBK");
            String []strList = str.split("\r\n");
            ws.disconnect();
            return ResultBean.success("获取成功！",strList);

        }catch (Exception e) {
            return ResultBean.failed("获取失败!");
        }
    }



    @RequestMapping(value = "/zhipin/address", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getZhipinEnvlist(@RequestParam(value="zhipinName",required =false) String zhipinName, @RequestParam(value="env",required =false) String env){
        logger.info("/batchCase/list GET 方法被调用!!");
        try{
            List<ZhipinEnvBean> list = zhipinEnvBll.getZhipinEnv(zhipinName,env);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("zhipinEnvlist", list);
            }};
            return ResultBean.success("查询zhipin服务地址成功!",result);
        } catch (Exception e) {
            logger.error("查询zhipin服务地址异常：{}", e);
            return ResultBean.failed("查询zhipin服务地址失败!");
        }
    }





}
