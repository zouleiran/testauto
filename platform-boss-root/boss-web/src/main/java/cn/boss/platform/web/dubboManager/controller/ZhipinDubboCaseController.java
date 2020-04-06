package cn.boss.platform.web.dubboManager.controller;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.dubboManager.ZhipinDubboCaseBean;
import cn.boss.platform.bll.dubboManager.ZhipinDubboCaseBll;
import cn.boss.platform.service.interfaceManager.DubboExecuteService;
import cn.boss.platform.service.interfaceManager.JsonPathService;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.dubboManager.form.ZhipinDubboCaseForm;
import cn.boss.platform.web.dubboManager.form.ZhipinExecuteForm;
import dubbo.dto.ConnectDTO;
import dubbo.dto.ResultDTO;
import dubbo.service.TelnetService;
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
 * Created by admin on 2018/12/20.
 */
@Controller
@RequestMapping("/boss/zhipin/case")
public class ZhipinDubboCaseController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(ZhipinDubboCaseController.class);

    @Autowired
    private ZhipinDubboCaseBll zhipinDubboCaseBll;
    @Autowired
    private DubboExecuteService dubboExecuteService;
    @Autowired
    private TelnetService telnetService;
    @Autowired
    private JsonPathService jsonPathService;

    /**
     * 添加用例
     *
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> addCase(@Valid ZhipinDubboCaseForm form, BindingResult bindingResult) {
        logger.info("/boss/zhipin/case/add POST 方法被调用!!" + form.toString());
        try {
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            ZhipinDubboCaseBean bean = new ZhipinDubboCaseBean();
            BeanUtils.copyProperties(form, bean);
            bean.setCreateTime(new Date());
            bean.setUpdateTime(new Date());
            zhipinDubboCaseBll.addDubboCase(bean);
            return ResultBean.success("添加dubbo用例成功！");
        } catch (Exception e) {
            logger.error("添加dubbo用例异常：{}", e);
            return ResultBean.failed("添加dubbo用例失败!");
        }
    }

    /**
     * 删除用例
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> deleteCase(@RequestParam(value = "id", required = true, defaultValue = "0") Integer id) {
        try {
            logger.info("/boss/zhipin/case/delete GET 方法被调用!! code: " + id);
            zhipinDubboCaseBll.deleteDubboCase(id);
            return ResultBean.success("删除dubbo用例成功！");

        } catch (Exception e) {
            logger.error("删除dubbo用例异常：{}", e);
            return ResultBean.failed("删除dubbo用例异常!");
        }
    }

    /**
     * 获取用例列表
     *
     * @param interfaceId
     * @param caseId
     * @param envId
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam(value = "interfaceId", required = false) Integer interfaceId, @RequestParam(value = "caseId", required = false) Integer caseId,
                                   @RequestParam(value = "envId", required = false) Integer envId) {
        try {
            logger.info("/boss/zhipin/case/list GET 方法被调用!! param: " + interfaceId, caseId, envId);
            List<ZhipinDubboCaseBean> list = zhipinDubboCaseBll.getDubboCases(interfaceId, caseId, envId);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("dubboCaseList", list);
//                put("caseCount", count);
            }};
            return ResultBean.success("查询dubbo用例成功!", result);
        } catch (Exception e) {
            logger.error("查询dubbo用例异常：{}", e);
            return ResultBean.failed("查询dubbo用例异常!");
        }
    }

    /**
     * 更新用例
     *
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public ResultBean<Object> update(@Valid ZhipinDubboCaseForm form, BindingResult bindingResult) {
        try {
            logger.info("/case/update GET 方法被调用!! , " + form.toString());
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            ZhipinDubboCaseBean zhipinDubboCaseBean = new ZhipinDubboCaseBean();
            BeanUtils.copyProperties(form, zhipinDubboCaseBean);
            zhipinDubboCaseBean.setUpdateTime(new Date());
            zhipinDubboCaseBll.updateDubboCase(zhipinDubboCaseBean);
            return ResultBean.success("更新用例成功!!");
        } catch (Exception e) {
            logger.error("更新用例异常：{" + e + "}");
            return ResultBean.failed("更新用例异常!");
        }
    }


    /**
     * 批量执行用例
     * @param execute
     * @return
     */
    @PostMapping("/execute")
    @ResponseBody
    public ResultBean<Object> execute(ZhipinExecuteForm execute) {
        try {
            List<ZhipinDubboCaseBean> list = zhipinDubboCaseBll.getExecute(execute.getInterfaceId(), execute.getEnvId(), execute.getCaseId(), execute.getAuthor());
            System.out.println(list.isEmpty());
            if (list.isEmpty()) {
                return ResultBean.failed("无可执行的用例！");
            }
            Integer serialId = StringUtils.isBlank(execute.getSerialId()) ? 0 : Integer.parseInt(execute.getSerialId());
            //执行用例
            dubboExecuteService.todo(list, serialId, Integer.parseInt(execute.getTaskId()), execute.getAuthor());
            logger.info("任务执行流水号：", serialId);
            logger.info("执行完毕，总共{" + list.size() + "}用例");
            return ResultBean.success("执行完毕，总共执行 " + list.size() + " 条用例！");
        }catch (Exception e) {
                logger.error("执行用例异常：{" + e + "}");
                return ResultBean.failed("执行用例异常!");
            }
    }

    @PostMapping("/debug/execute")
    @ResponseBody
    public ResultBean<Object> debugExecute(ConnectDTO dto, BindingResult bindingResult) {
        //执行dubbo接口
        ResultDTO<Object> resultDTO=null;
        try{
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            resultDTO = telnetService.send(dto);
            if(resultDTO.isSuccess() ==false){
                return ResultBean.failed("接口请求异常！",resultDTO);
            }
            //断言
            if(!StringUtils.isBlank(dto.getExpectedResult())){
                //预期结果非空
                jsonPathService.assertDubboRest(resultDTO.getData().toString(),dto.getExpectedResult());
                return ResultBean.success("断言成功！",resultDTO);
            }else{
                return ResultBean.success("预期结果为空时断言成功！",resultDTO);
            }
        }catch (AssertionError e){
            return ResultBean.success("预期结果和实际结果不匹配！",resultDTO);
        }catch (Exception e) {
            logger.error("执行用例异常：{" + e + "}");
            return ResultBean.failed("执行异常！",resultDTO);
        }
    }

}
