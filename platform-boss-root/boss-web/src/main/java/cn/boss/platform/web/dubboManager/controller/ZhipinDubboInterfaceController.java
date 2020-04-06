package cn.boss.platform.web.dubboManager.controller;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.dubboManager.ZhipinDubboCaseBean;
import cn.boss.platform.bean.dubboManager.ZhipinDubboInterfaceBean;
import cn.boss.platform.bll.dubboManager.ZhipinDubboCaseBll;
import cn.boss.platform.bll.dubboManager.ZhipinDubboInterfaceBll;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.dubboManager.form.ZhipinDubboGroupForm;
import cn.boss.platform.web.dubboManager.form.ZhipinDubboInterfaceForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by admin on 2018/12/18.
 */
@Controller
@RequestMapping("/boss/zhipin/interface")
public class ZhipinDubboInterfaceController extends AbstractBaseController  {

    private static final Logger logger = LoggerFactory.getLogger(ZhipinDubboInterfaceController.class);

    @Autowired
    private ZhipinDubboInterfaceBll zhipinDubboInterfaceBll;
    @Autowired
    private ZhipinDubboCaseBll zhipinDubboCaseBll;


    /**
     * 添加接口
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> add(@Valid ZhipinDubboInterfaceForm form, BindingResult bindingResult) {
        logger.info("/boss/interface/add POST 方法被调用!!" + form.toString());
        try {
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            ZhipinDubboInterfaceBean interfaceBean = new ZhipinDubboInterfaceBean();
            BeanUtils.copyProperties(form, interfaceBean);
            interfaceBean.setCreateTime(new Date());
            interfaceBean.setUpdateTime(new Date());
            zhipinDubboInterfaceBll.addDubboInterface(interfaceBean);
            return ResultBean.success("添加dubbo接口成功!");
        } catch (Exception e) {
            logger.error("添加dubbo接口异常：{}", e);
            return ResultBean.failed("添加dubbo接口失败!");
        }
    }

    /**
     * 更新接口
     * @param form
     * @param br
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> update(@Valid ZhipinDubboInterfaceForm form, BindingResult br) {
        logger.info("/interface/update POST 方法被调用!!", form);
        try{
            String errorMsg = validateData(br);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            ZhipinDubboInterfaceBean bean = new ZhipinDubboInterfaceBean();
            BeanUtils.copyProperties(form,bean);
            bean.setUpdateTime(new Date());
            if(zhipinDubboInterfaceBll.updateDubboInterface(bean)){
                return ResultBean.success("更新dubbo接口成功!!");
            }
            return ResultBean.failed("更新dubbo接口失败!!");
        } catch (Exception e) {
            logger.error("更新dubbo接口异常：{" + e + "}");
            return ResultBean.failed("更新dubbo接口异常!");
        }
    }

    /**
     * 删除接口，有用例的接口不能删除
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> delete(@RequestParam(value="id",required =true, defaultValue = "0") Integer id) {
        logger.info("/interface/delete GET 方法被调用!!");
        try {
            zhipinDubboInterfaceBll.deleteInterface(id);
            return ResultBean.success("删除接口成功!");
//            Integer count = caseBll.getCaseCount(id);
//            if(count > 0){
//                return ResultBean.failed("接口下有用例，不能删除!");
//            }else{
//                interfaceBll.delete(id);
//                return ResultBean.success("删除接口成功!");
//            }
        } catch (Exception e) {
            logger.error("删除接口异常：{" + e + "}");
            return ResultBean.failed("删除接口异常!");
        }
    }




    /**
     * 获取dubbo接口分组
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<java.lang.Object> getDubboInterface() {
        try {
            logger.info("/boss/zhipin/interface GET 方法被调用!! ");
            List<ZhipinDubboInterfaceBean> serverName= zhipinDubboInterfaceBll.getServerName();
            logger.info(serverName+"");
            List<ZhipinDubboGroupForm> result = new ArrayList<ZhipinDubboGroupForm>();
            for (int i = 0; i < serverName.size(); i++) {
                ZhipinDubboGroupForm form = new ZhipinDubboGroupForm();
                form.setLabel(serverName.get(i).getServerName());
                form.setId(serverName.get(i).getId()+"");

                //取children
                List<ZhipinDubboInterfaceBean> serverInterface= zhipinDubboInterfaceBll.getServerInterface(serverName.get(i).getServerName());
                //非空情况下
                if(serverInterface !=null && serverInterface.size() > 0){
                    List<ZhipinDubboGroupForm> resultInterface = new ArrayList<>();
                    for (int j = 0; j < serverInterface.size(); j++) {
                        ZhipinDubboGroupForm formInterface = new ZhipinDubboGroupForm();
                        formInterface.setId(new Date().getTime() +"");
                        formInterface.setLabel(serverInterface.get(j).getInterfaceName());

                        List<ZhipinDubboInterfaceBean> serverMethod= zhipinDubboInterfaceBll.getServerMethod(serverInterface.get(j).getInterfaceName());
                        if(serverMethod !=null && serverMethod.size() > 0){
                            List<ZhipinDubboGroupForm> resultMethod = new ArrayList<>();
                            for(int n = 0; n < serverMethod.size(); n++){
                                ZhipinDubboGroupForm formMethod = new ZhipinDubboGroupForm();
                                formMethod.setId(new Date().getTime() +"");
                                formMethod.setLabel(serverMethod.get(n).getMethodName());
                                formMethod.setInterfaceId(serverMethod.get(n).getId());
                                resultMethod.add(formMethod);
                            }
                            formInterface.setChildren(resultMethod);
                        }

                        resultInterface.add(formInterface);
                    }
                    form.setChildren(resultInterface);
                }
                result.add(form);
            }
            return ResultBean.success("获取成功！",result);
        } catch (Exception e) {
            logger.error("查询dubbo接口：{}", e);
            return ResultBean.failed("查询dubbo接口失败!");
        }
    }

    /**
     * 查询满足条件的接口
     * @param form
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(ZhipinDubboInterfaceForm form) {
        logger.info("/boss/zhipin/interface/list GET 方法被调用!!"+form.toString());
        try {
            ZhipinDubboInterfaceBean zhipinDubboInterfaceBean = new ZhipinDubboInterfaceBean();
            BeanUtils.copyProperties(form, zhipinDubboInterfaceBean);
            List<ZhipinDubboInterfaceBean> list = zhipinDubboInterfaceBll.getDubboInterface(zhipinDubboInterfaceBean);
            for (ZhipinDubboInterfaceBean c : list) {
                List<ZhipinDubboCaseBean> listCases= zhipinDubboCaseBll.getDubboCases(c.getId(),null,null);
                if (listCases != null){
                    c.setCaseCount(listCases.size());
                }else{
                    c.setCaseCount(0);
                }
            }
            Integer count = zhipinDubboInterfaceBll.getDubboInterfaceCount(zhipinDubboInterfaceBean);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("interfaceList", list);
                put("interfaceCount", count);
            }};
            return ResultBean.success("查询接口成功!",result);
        } catch (Exception e) {
            logger.error("查询接口异常：{}", e);
            return ResultBean.failed("查询接口失败!");
        }
    }


}
