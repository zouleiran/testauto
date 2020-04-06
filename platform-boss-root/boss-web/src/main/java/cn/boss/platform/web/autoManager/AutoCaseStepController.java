package cn.boss.platform.web.autoManager;

import cn.boss.platform.bean.autoManager.AutoCaseStepBean;
import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bll.autoCaseManager.AutoCaseStepBll;
import cn.boss.platform.doe.form.AutoCaseStepForm;
import cn.boss.platform.web.api.AbstractBaseController;
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
 * Created by admin on 2019/11/18.
 */
@Controller
@RequestMapping("/boss/autoCaseStep")
public class AutoCaseStepController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(AutoCaseController.class);

    @Autowired
    private AutoCaseStepBll autoCaseStepBll;

    /**
     * 添加UI用例步骤
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> addAutoCase(@Valid AutoCaseStepForm form, BindingResult bindingResult) {
        logger.info("/autoCaseStep/add POST 方法被调用!!"+form.toString());
        try{
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            AutoCaseStepBean bean = new AutoCaseStepBean();
            BeanUtils.copyProperties(form,bean);
            bean.setCreateTime(new Date());
            bean.setUpdateTime(new Date());
            autoCaseStepBll.addAutoStepCase(bean);
            return ResultBean.success("添加UI用例成功！");
        } catch (Exception e) {
            logger.error("添加UI用例异常：{}", e);
            return ResultBean.failed("添加UI用例失败!");
        }
    }


    /**
     * 查询UI用例步骤
     * @param id
     * @param projectId
     * @param groupId
     * @param caseId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam(value="id",required =false) Integer id, @RequestParam(value="projectId",required =false) Integer projectId,
                                   @RequestParam(value="groupId",required =false) Integer groupId, @RequestParam(value="caseId",required =false) Integer caseId,
                                   @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize){
        logger.info("/autoCase/list GET 方法被调用!!");
        try{
            List<AutoCaseStepBean> list = autoCaseStepBll.getAutoCaseStep(projectId,groupId,caseId,id,pageIndex,pageSize);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("autoCaseStepList", list);
            }};
            return ResultBean.success("查询UI用例步骤成功!",result);
        } catch (Exception e) {
            logger.error("查询UI用例步骤异常：{}", e);
            return ResultBean.failed("查询UI用例步骤失败!");
        }
    }


    /**
     * 删除接口，有用例的接口不能删除
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> delete(@RequestParam(value="id") Integer id) {
        logger.info("/autoCase/delete GET 方法被调用!!");
        try {
            autoCaseStepBll.deleteBatchCaseStep(id);
            return ResultBean.success("删除UI用例步骤成功!");
        } catch (Exception e) {
            logger.error("删除UI用例步骤异常：{" + e + "}");
            return ResultBean.failed("删除UI用例步骤异常!");
        }
    }

    /**
     * 更新用例步骤
     * @param form
     * @param br
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> update(@Valid AutoCaseStepForm form, BindingResult br) {
        logger.info("/autoCase/update POST 方法被调用!!", form);
        try{
            String errorMsg = validateData(br);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            AutoCaseStepBean bean = new AutoCaseStepBean();
            BeanUtils.copyProperties(form,bean);
            bean.setUpdateTime(new Date());
            if(autoCaseStepBll.update(bean)){
                return ResultBean.success("更新UI用例步骤成功!!");
            }
            return ResultBean.failed("更新UI用例步骤失败!!");
        } catch (Exception e) {
            logger.error("更新UI用例步骤异常：{" + e + "}");
            return ResultBean.failed("更新UI用例步骤异常!");
        }
    }


    @PostMapping("/switch")
    @ResponseBody
    public ResultBean<Object> ReplacementSteps(@RequestParam("frontId") Integer frontId, @RequestParam("afterId") Integer afterId){
        try {
            logger.info("/autoCaseStep/switch POST 方法被调用!!");
            if (null != frontId && null != afterId) {
                boolean result = autoCaseStepBll.switchAutoCaseStep(frontId,99999);
                if(result ==true){
                    boolean resultOne = autoCaseStepBll.switchAutoCaseStep(afterId,frontId);
                    if(resultOne ==true){
                        boolean resultTwo = autoCaseStepBll.switchAutoCaseStep(99999,afterId);
                        if(resultTwo ==true){
                            return ResultBean.success("切换用例顺序成功!");
                        }else{
                            return ResultBean.failed("切换用例顺序失败!");
                        }
                    }else{
                        return ResultBean.failed("切换用例顺序失败!");
                    }
                }else{
                    return ResultBean.failed("切换用例顺序失败!");
                }
            } else {
                return ResultBean.success("frontId 或 afterId不能为空！");
            }
        } catch (Exception e) {
            logger.error("切换用例步骤异常：{" + e + "}");
            return ResultBean.failed("切换用例步骤异常!");
        }
    }




}