package cn.boss.platform.web.batchCaseManager;


import cn.boss.platform.bean.batchCaseManager.BatchCaseStepBean;
import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bll.batchCaseManager.BatchCaseStepBll;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.BatchCaseStepForm;
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
 * Created by admin on 2018/9/7.
 */
@Controller
@RequestMapping("/boss/batchCaseStep")
public class BatchCaseStepController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(BatchCaseStepController.class);

    @Autowired
    private BatchCaseStepBll batchCaseStepBll;

    /**
     * 添加批用例
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> addBatchCaseStep(@Valid BatchCaseStepForm form, BindingResult bindingResult) {
        logger.info("/batchCaseStep/add POST 方法被调用!!"+form.toString());
        try{
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            BatchCaseStepBean bean = new BatchCaseStepBean();
            BeanUtils.copyProperties(form,bean);
            bean.setCreateTime(new Date());
            bean.setUpdateTime(new Date());
            batchCaseStepBll.addBatchCase(bean);
            return ResultBean.success("添加业务用例成功！");
        } catch (Exception e) {
            logger.error("添加业务用例异常：{}", e);
            return ResultBean.failed("添加用例失败!");
        }
    }

    /**
     * 分页查询用例步骤
     * @param batchId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam("batchId") Integer batchId, @RequestParam("pageIndex") Integer pageIndex,
                                   @RequestParam("pageSize") Integer pageSize) {
        try {
            logger.info("/batchCaseStep/list GET 方法被调用!! param: " + batchId,pageIndex,pageSize);
            List<BatchCaseStepBean> list = batchCaseStepBll.getBatchStepBycaseId(batchId, pageIndex, pageSize);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("batchCaseStepList", list);
//                put("caseCount", count);
            }};
            return ResultBean.success("查询用例成功!", result);
        } catch (Exception e) {
            logger.error("查询用例异常：{}", e);
            return ResultBean.failed("查询用例异常!");
        }
    }

    /**
     * 获取单条用例步骤
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getByCode(@RequestParam(value="id",required =true, defaultValue = "0") Integer id) {
        try {
            logger.info("/batchCaseStep/getCase GET 方法被调用!! , param: " + id);
            return ResultBean.success("查询用例步骤成功!",batchCaseStepBll.getBatchCaseById(id));
        } catch (Exception e) {
            logger.error("查询用例异常：{}", e);
            return ResultBean.failed("查询用例步骤异常!");
        }
    }


    /**
     * 删除用例步骤
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> delete(@RequestParam(value="id",required =true, defaultValue = "0") Integer id) {
        try{
            logger.info("/batchCaseStep/delete GET 方法被调用!! code: "+id);
            batchCaseStepBll.deleteBatchCaseStep(id);
            return ResultBean.success("删除用例步骤成功！");

        } catch (Exception e) {
            logger.error("删除用例步骤异常：{}", e);
            return ResultBean.failed("删除用例步骤异常!");
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
    public ResultBean<Object> update(@Valid BatchCaseStepForm form, BindingResult bindingResult) {
        try{
            logger.info("/batchCaseStep/update GET 方法被调用!! , " + form.toString());
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            BatchCaseStepBean bean = new BatchCaseStepBean();
            BeanUtils.copyProperties(form,bean);
            bean.setUpdateTime(new Date());
            batchCaseStepBll.updateBatchCaseStep(bean);
            return ResultBean.success("更新batch用例步骤成功!!");
        }catch (Exception e) {
            logger.error("更新batch用例步骤异常：{" + e + "}");
            return ResultBean.failed("更新batch用例步骤异常!");
        }
    }

    @PostMapping("/switch")
    @ResponseBody
    public ResultBean<Object> ReplacementSteps(@RequestParam("frontId") Integer frontId, @RequestParam("afterId") Integer afterId){
        try {
            logger.info("/batchCaseStep/switch POST 方法被调用!!");
            if (null != frontId && null != afterId) {
                boolean result = batchCaseStepBll.switchBatchCase(frontId,99999);
                if(result ==true){
                    boolean resultOne = batchCaseStepBll.switchBatchCase(afterId,frontId);
                    if(resultOne ==true){
                        boolean resultTwo = batchCaseStepBll.switchBatchCase(99999,afterId);
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
