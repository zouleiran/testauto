package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.InterfaceBean;
import cn.boss.platform.bll.InterfaceManager.CaseBll;
import cn.boss.platform.bll.InterfaceManager.InterfaceBll;
import cn.boss.platform.bll.InterfaceManager.ProjectGroupBll;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.InterfaceForm;
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
 * Created by admin on 2018/7/26.
 */
@Controller
@RequestMapping("/boss/interface")
public class InterfaceController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(InterfaceController.class);

    @Autowired
    InterfaceBll interfaceBll;
    @Autowired
    private CaseBll caseBll;
    @Autowired
    ProjectGroupBll projectGroupBll;

    /**
     * 添加接口
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> add(@Valid InterfaceForm form, BindingResult bindingResult) {
        logger.info("/boss/interface/add POST 方法被调用!!" + form.toString());
        try {
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            InterfaceBean interfaceBean = new InterfaceBean();
            BeanUtils.copyProperties(form, interfaceBean);
            interfaceBean.setCreateTime(new Date());
            interfaceBean.setUpdateTime(new Date());
            interfaceBll.addInterface(interfaceBean);

            List<InterfaceBean> list = interfaceBll.getInterfaaceByOrderCode();
            //遍历list，更新order_code
            for(int i = 0 ; i < list.size() ; i++) {
                Integer interfaceId = list.get(i).getId();
                list.get(i).setOrderCode(interfaceId);
                interfaceBll.update(list.get(i));
            }
            return ResultBean.success("添加接口成功!");
        } catch (Exception e) {
            logger.error("添加接口异常：{}", e);
            return ResultBean.failed("添加接口失败!");
        }
    }


    /**
     * 查询满足条件的接口
     * project  项目编号，非必输
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(InterfaceForm form) {
        logger.info("/interface/list GET 方法被调用!!"+form.toString());
        try {
            InterfaceBean interfaceBean = new InterfaceBean();
            BeanUtils.copyProperties(form, interfaceBean);
            List<InterfaceBean> list = interfaceBll.getInterface(interfaceBean);

            int caseCounts = caseBll.getCaseCountByProjectId(form.getProjectId());
            //遍历list，获取每条接口的用例数
            for(int i = 0 ; i < list.size() ; i++) {
                int caseCount = caseBll.getCaseCount(list.get(i).getId());
                list.get(i).setCaseCount(caseCount);
            }
            Integer count = interfaceBll.getInterfaceCount(interfaceBean);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("interfaceList", list);
                put("interfaceCount", count);
                put("caseCounts", caseCounts);
            }};
            return ResultBean.success("查询接口成功!",result);
        } catch (Exception e) {
            logger.error("查询接口异常：{}", e);
            return ResultBean.failed("查询接口失败!");
        }
    }

    /**
     * 简易查询满足条件的接口，返回结果中删除response部分
     * project  项目编号，非必输
     * @return
     */
    @RequestMapping(value = "/list/sample", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> listSample(InterfaceForm form) {
        logger.info("/interface/list GET 方法被调用!!"+form.toString());
        try {
            InterfaceBean interfaceBean = new InterfaceBean();
            BeanUtils.copyProperties(form, interfaceBean);
            List<InterfaceBean> list = interfaceBll.getInterface(interfaceBean);

            int caseCounts = caseBll.getCaseCountByProjectId(form.getProjectId());
            //遍历list，获取每条接口的用例数
            for(int i = 0 ; i < list.size() ; i++) {
                //把分组名也带上
                String groupName = projectGroupBll.getProjectGroupById(list.get(i).getGroupId()).getGroupName();
                list.get(i).setGroupName(groupName);
                //response数据太大，删除
                list.get(i).setResponse("");
                list.get(i).setParams("");
                list.get(i).setCommonParams("");
                int caseCount = caseBll.getCaseCount(list.get(i).getId());
                list.get(i).setCaseCount(caseCount);
            }
            Integer count = interfaceBll.getInterfaceCount(interfaceBean);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("interfaceList", list);
                put("interfaceCount", count);
                put("caseCounts", caseCounts);
            }};
            return ResultBean.success("查询接口成功!",result);
        } catch (Exception e) {
            logger.error("查询接口异常：{}", e);
            return ResultBean.failed("查询接口失败!");
        }
    }

    /**
     * 查询单个接口
     * @param interfaceId
     * @return
     */
    @RequestMapping(value = "/listById", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> searchTaskById(@RequestParam(value="interfaceId",required =true, defaultValue = "0") Integer interfaceId) {
        logger.info("/task/list GET 方法被调用!!");
        try {
            InterfaceBean bean = interfaceBll.getInterfaceById(interfaceId);
            return ResultBean.success("查询任务成功!", bean);
        } catch (Exception e) {
            logger.error("查询任务异常：{" + e + "}");
            return ResultBean.failed("查询任务异常!");
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
            Integer count = caseBll.getCaseCount(id);
            if(count > 0){
                return ResultBean.failed("接口下有用例，不能删除!");
            }else{
                interfaceBll.delete(id);
                return ResultBean.success("删除接口成功!");
            }
        } catch (Exception e) {
            logger.error("删除接口异常：{" + e + "}");
            return ResultBean.failed("删除接口异常!");
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
    public ResultBean<String> update(@Valid InterfaceForm form, BindingResult br) {
        logger.info("/interface/update POST 方法被调用!!", form);
        try{
            String errorMsg = validateData(br);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            InterfaceBean bean = new InterfaceBean();
            BeanUtils.copyProperties(form,bean);
            bean.setUpdateTime(new Date());
            if(interfaceBll.update(bean)){
                return ResultBean.success("更新接口成功!!");
            }
            return ResultBean.failed("更新接口失败!!");
        } catch (Exception e) {
            logger.error("更新接口异常：{" + e + "}");
            return ResultBean.failed("更新接口异常!");
        }
    }

    /**
     * 搜索满足条件的接口
     * project  项目编号，非必输
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> search(@RequestParam("projectId") Integer projectId,
                                   @RequestParam("keyword") String keyword,
                                   @RequestParam("pageIndex") Integer pageIndex,
                                   @RequestParam("pageSize") Integer pageSize) {
        try {
            List<InterfaceBean> list = interfaceBll.searchInterface(projectId,keyword,pageIndex,pageSize);
//            int caseCounts = caseBll.getCaseCountByProjectId(projectId);
            //遍历list，获取每条接口的用例数
            for(int i = 0 ; i < list.size() ; i++) {
                //把分组名也带上
                String groupName = projectGroupBll.getProjectGroupById(list.get(i).getGroupId()).getGroupName();
                list.get(i).setGroupName(groupName);
                int caseCount = caseBll.getCaseCount(list.get(i).getId());
                list.get(i).setCaseCount(caseCount);
            }
            Integer count = interfaceBll.searchInterfaceCount(projectId,keyword);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("interfaceList", list);
                put("interfaceCount", count);
//                put("caseCounts", caseCounts);
            }};
            return ResultBean.success("查询接口成功!",result);
        } catch (Exception e) {
            logger.error("查询接口异常：{}", e);
            return ResultBean.failed("查询接口失败!");
        }
    }


    @PostMapping("/move")
    @ResponseBody
    public ResultBean<Object> ReplacementSteps(@RequestParam("frontId") Integer frontId, @RequestParam("afterId") Integer afterId){
        try {
            logger.info("/batchCaseStep/switch POST 方法被调用!!");
            if (null != frontId && null != afterId) {
                InterfaceBean frontBean = interfaceBll.getInterfaceById(frontId);
                InterfaceBean afterIdBean = interfaceBll.getInterfaceById(afterId);
                if(frontBean != null && afterIdBean != null){
                    Integer id = frontBean.getOrderCode();
                    frontBean.setOrderCode(afterIdBean.getOrderCode());
                    afterIdBean.setOrderCode(id);
                    if(interfaceBll.update(frontBean) && interfaceBll.update(afterIdBean)){
                        return ResultBean.success("移动接口成功!!");
                    }
                }else{
                    return ResultBean.success("移动的接口不存在！");
                }
            }
            else {
                return ResultBean.success("移动的接口编号不能为空！");
            }
            return null;
        } catch (Exception e) {
            logger.error("切换用例步骤异常：{" + e + "}");
            return ResultBean.failed("切换用例步骤异常!");
        }
    }


    @GetMapping("/searchInterfaceBycaseId")
    @ResponseBody
    public ResultBean<Object> searchInterfaceBycaseId(@RequestParam("caseId") Integer caseId){
        logger.info("/searchInterfaceBycaseId POST 方法被调用!! param:" + caseId);
        try{
            List<InterfaceBean> bean = interfaceBll.searchInterfaceBycaseId(caseId);
            return ResultBean.success("查询成功！",bean);
        }catch (Exception e) {
            logger.error("查询接口失败：{" + e + "}");
            return ResultBean.failed("查询接口异常!");
        }
    }



}
