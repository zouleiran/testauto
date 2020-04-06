package cn.boss.platform.web.autoManager;

import cn.boss.platform.bean.autoManager.AutoCaseBean;
import cn.boss.platform.bean.autoManager.AutoCaseStepBean;
import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bll.autoCaseManager.AutoCaseBll;
import cn.boss.platform.bll.autoCaseManager.AutoCaseStepBll;
import cn.boss.platform.doe.form.AutoCaseForm;

import cn.boss.platform.doe.util.DriverManage;
import cn.boss.platform.service.autoCaseManager.CaseStepService;
import cn.boss.platform.web.api.AbstractBaseController;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.validation.Valid;
import java.net.URL;
import java.util.*;

/**
 * Created by admin on 2019/11/8.
 */
@Controller
@RequestMapping("/boss/autoCase")
public class AutoCaseController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(AutoCaseController.class);

    @Autowired
    private AutoCaseBll autoCaseBll;
    @Autowired
    private AutoCaseStepBll autoCaseStepBll;

    @Autowired
    private CaseStepService caseStepService;

    //节点存储地址，用于判断driver是否需要重新new
    public Map<String, WebDriver> nodeUrl =  new HashMap<String, WebDriver>();



    /**
     * 添加UI用例
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> addAutoCase(@Valid AutoCaseForm form, BindingResult bindingResult) {
        logger.info("/batchCase/add POST 方法被调用!!"+form.toString());
        try{
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            AutoCaseBean bean = new AutoCaseBean();
            BeanUtils.copyProperties(form,bean);
            bean.setCreateTime(new Date());
            bean.setUpdateTime(new Date());
            autoCaseBll.addAutoCase(bean);
            return ResultBean.success("添加UI用例成功！");
        } catch (Exception e) {
            logger.error("添加UI用例异常：{}", e);
            return ResultBean.failed("添加UI用例失败!");
        }
    }

    /**
     * 查询UI用例
     * @param id
     * @param projectId
     * @param groupId
     * @param envId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam(value="id",required =false) Integer id, @RequestParam(value="projectId",required =false) Integer projectId,
                                   @RequestParam(value="groupId",required =false) Integer groupId, @RequestParam(value="envId",required =false) Integer envId,
                                   @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize){
        logger.info("/autoCase/list GET 方法被调用!!");
        try{
            List<AutoCaseBean> list = autoCaseBll.getAutoCase(projectId,groupId,envId,id,pageIndex,pageSize);

            //用例总数
            int caseCount = autoCaseBll.getAutoCaseCount(projectId,groupId,envId,id);
            //用例下步骤总数
            int autoCaseStepCount = 0;
            for(int i = 0 ; i < list.size() ; i++) {
                List<AutoCaseStepBean> stepList = autoCaseStepBll.getAutoCaseStep(null,null,list.get(i).getId(),null,1,5000);
                if(stepList != null){
                    list.get(i).setCaseStepCount(stepList.size());
                }
            }
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("autoCaseList", list);
            }};
            result.put("caseCount",caseCount);
            return ResultBean.success("查询UI用例成功!",result);
        } catch (Exception e) {
            logger.error("查询UI用例异常：{}", e);
            return ResultBean.failed("查询UI用例失败!");
        }
    }


    /**
     * 删除用例
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> delete(@RequestParam(value="id",required =true, defaultValue = "0") Integer id) {
        try{
            logger.info("/autoCase/delete GET 方法被调用!! code: "+id);
            autoCaseBll.deleteBatchCase(id);
            return ResultBean.success("删除UI用例成功！");

        } catch (Exception e) {
            logger.error("删除UI用例异常：{}", e);
            return ResultBean.failed("删除UI用例异常!");
        }
    }


    /**
     * 执行UI自动化用例
     * @return
     */
    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> executeCase( @RequestParam(value="projectId",required =false) Integer projectId, @RequestParam(value="groupId",required =false) String groupId,
                                           @RequestParam(value="caseId",required =false) String caseId, @RequestParam(value="id",required =false) String id,
                                           @RequestParam(value="restartBrowser",required =false) Integer restartBrowser,@RequestParam(value="hubUrl") String hubUrl,
                                           @RequestParam(value="taskId") Integer taskId,@RequestParam(value="author") String author,@RequestParam(value="serialId") String serialId) {
        try{
            int[] arrayCases = null;
            int[] arrayGroups = null;
            int[] arrayCaseSteps = null;
            if (!StringUtils.isBlank(groupId)) {
                String[] split = groupId.split(",");
                arrayGroups = Arrays.stream(split).mapToInt(Integer::valueOf).toArray();
            }
            if (!StringUtils.isBlank(caseId)) {
                String[] split = caseId.split(",");
                arrayCases = Arrays.stream(split).mapToInt(Integer::valueOf).toArray();
            }
            //需要重启的话直接重启且保存driver对象，如果不重启已经存在就哪之前的driver，还是没有的话就重启
            WebDriver webdriver = null;
            if(nodeUrl.size() > 0){
                String name = "";
                for(String key: nodeUrl.keySet()){
                    name = name + key;
                }
                if(name.contains(author) && restartBrowser.equals(0)){
                    webdriver = nodeUrl.get(author);
                }else{
                    webdriver = DriverManage.getDriver(3,restartBrowser,hubUrl);
                    nodeUrl.put(author,webdriver);
                }
            }else{
                webdriver = DriverManage.getDriver(3,restartBrowser,hubUrl);
                nodeUrl.put(author,webdriver);
            }
            logger.info("webdriver对象", nodeUrl);
            Integer serialIds = StringUtils.isBlank(serialId) ? 0:Integer.parseInt(serialId);
            //先查用例
            List<AutoCaseBean> autoCaselist = autoCaseBll.getAutoCases(projectId,arrayGroups,arrayCases,1,5000);
            for(AutoCaseBean autoCaseBean : autoCaselist){
                List<AutoCaseStepBean> autoCaseStepList = autoCaseStepBll.getAutoCaseStep(projectId,null,autoCaseBean.getId(),null,1,5000);
                for(AutoCaseStepBean c : autoCaseStepList){
                    boolean result = caseStepService.doEachCase(c,serialIds,taskId,author,webdriver);
                    if(result == false && c.getException().equals("1")){
                        logger.info("该条用例执行失败！，继续执行下一条！");
                        break;
                    }
                }
            }
            return ResultBean.success("执行完毕，总共执行 "+autoCaselist.size()+" 条用例！" );
        } catch (Exception e) {
            logger.error("执行用例异常：{}", e);
            return ResultBean.failed("执行用例失败!");
        }
    }


    /**
     * 查询分组下的用例
     * @param groupIds 分组编号
     * @return
     */
    @RequestMapping(value = "/list/byGroupId", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam(value="groupIds") String groupIds) {
        logger.info("/projectGroup/list GET 方法被调用!!");
        try {
            List<String> groupIdsList = new ArrayList<>();
            String[]  id = groupIds.split(",");
            for(String  materialId :id){
                groupIdsList.add(materialId);
            }
            List<AutoCaseBean>  list = autoCaseBll.getAutoCaseBygroupId(groupIdsList);
            return ResultBean.success("查询分组下的用例成功!",list);
        } catch (Exception e) {
            logger.error("查询分组下的用例异常：{}", e);
            return ResultBean.failed("查询分组下的用例失败!");
        }
    }

}