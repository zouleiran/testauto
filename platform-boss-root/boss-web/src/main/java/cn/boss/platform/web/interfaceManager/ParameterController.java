package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.CaseBean;
import cn.boss.platform.bean.interfaceManager.ParameterBean;
import cn.boss.platform.bll.InterfaceManager.CaseBll;
import cn.boss.platform.bll.InterfaceManager.ParameterBll;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.ParameterForm;
import org.apache.commons.lang3.StringUtils;
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


import javax.validation.Valid;
import java.util.*;

/**
 * Created by admin on 2018/8/13.
 */
@Controller
@RequestMapping("/boss/parameter")
public class ParameterController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(ParameterController.class);
    @Autowired
    ParameterBll parameterBll;
    @Autowired
    private CaseBll caseBll;

    /**
     * 添加参数
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> add(@Valid ParameterForm form, BindingResult bindingResult) {
        logger.info("/param/add POST 方法被调用!!"+form.toString());
        try {
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            ParameterBean parameterBean = new ParameterBean();
            BeanUtils.copyProperties(form, parameterBean);
            parameterBean.setCreateTime(new Date());
            parameterBean.setUpdateTime(new Date());
            boolean result = parameterBll.addParameter(parameterBean);
            return ResultBean.success(result ? "添加参数成功!":"添加参数名重复！");
        } catch (Exception e) {
            logger.error("添加参数异常：{}", e);
            return ResultBean.failed("添加参数失败!");
        }
    }

    /**
     * 查询参数
     * @param form
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(ParameterForm form) {
        logger.info("/parameter/list GET 方法被调用!!"+form.toString());
        try {
            ParameterBean parameterBean = new ParameterBean();
            BeanUtils.copyProperties(form, parameterBean);
            List<ParameterBean> list = parameterBll.getParameter(parameterBean);
            //查询参数相关用例
            for( int i = 0 ; i < list.size() ; i++) {
                String paramName = list.get(i).getName();
                List<CaseBean> caseBeans= caseBll.relatedParameters(paramName);
                //使用改该参数的用例集和接口集
                Map<Integer, Integer> relatedCaseParametersMap = new HashMap<Integer, Integer>();
                for( int j = 0 ; j < caseBeans.size() ; j++){
                    relatedCaseParametersMap.put(caseBeans.get(j).getId(),caseBeans.get(j).getInterfaceId());
                }
                list.get(i).setRelevantCases(relatedCaseParametersMap);

                //参数来源的用例集和接口集
                List<CaseBean> sourceCaseBeans= caseBll.sourceParameters(paramName);
                Map<Integer, Integer> sourceCaseParametersMap = new HashMap<Integer, Integer>();
                for( int j = 0 ; j < sourceCaseBeans.size() ; j++){
                    sourceCaseParametersMap.put(sourceCaseBeans.get(j).getId(),sourceCaseBeans.get(j).getInterfaceId());
                }
                list.get(i).setSourceCases(sourceCaseParametersMap);

            }
            int listCount = parameterBll.getParameterCount(parameterBean);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("parameterList", list);
                put("parameterCount", listCount);
            }};
            return ResultBean.success("查询参数成功!",result);
        } catch (Exception e) {
            logger.error("查询项目异常：{}", e);
            return ResultBean.failed("查询项目失败!");
        }
    }

    /**
     * 删除参数
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> delete(@RequestParam(value="id",required =true, defaultValue = "0") Integer id) {
        logger.info("/parameter/delete GET 方法被调用!!");
        try {
            boolean result = parameterBll.deleteParameter(id);
            return ResultBean.success(result ? "删除参数成功！":"参数已被使用不能删除！");
        } catch (Exception e) {
            logger.error("删除参数异常：{" + e + "}");
            return ResultBean.failed("删除参数异常!");
        }
    }

    /**
     * 更新参数
     * @param form
     * @param br
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> update(@Valid ParameterForm form, BindingResult br) {
        logger.info("/interface/update POST 方法被调用!!", form);
        try{
            String errorMsg = validateData(br);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            ParameterBean bean = new ParameterBean();
            BeanUtils.copyProperties(form,bean);
            bean.setUpdateTime(new Date());
            if(parameterBll.updateParameter(bean)){
                return ResultBean.success("更新参数成功!!");
            }
            return ResultBean.failed("更新参数失败!!");
        } catch (Exception e) {
            logger.error("更新参数异常：{" + e + "}");
            return ResultBean.failed("更新参数异常!");
        }
    }

}
