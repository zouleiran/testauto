package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.autoManager.AutoCaseBean;
import cn.boss.platform.bean.autoManager.AutoProjectGroupBean;
import cn.boss.platform.bean.batchCaseManager.BatchCaseBean;
import cn.boss.platform.bean.batchCaseManager.BatchProjectGroupTempBean;
import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.InterfaceBean;
import cn.boss.platform.bean.interfaceManager.ProjectGroupBean;
import cn.boss.platform.bean.interfaceManager.ProjectGroupTempBean;
import cn.boss.platform.bll.autoCaseManager.AutoCaseBll;
import cn.boss.platform.bll.batchCaseManager.BatchCaseBll;
import cn.boss.platform.bll.InterfaceManager.InterfaceBll;
import cn.boss.platform.bll.InterfaceManager.ProjectGroupBll;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.ProjectGroupForm;
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
 * Created by admin on 2018/7/24.
 */
@Controller
@RequestMapping("/boss/projectGroup")
public class ProjectGroupController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectGroupController.class);

    @Autowired
    ProjectGroupBll projectGroupBll;
    @Autowired
    InterfaceBll interfaceBll;
    @Autowired
    private BatchCaseBll batchCaseBll;
    @Autowired
    private AutoCaseBll autoCaseBll;


    /**
     * 添加项目分组
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> add(@Valid ProjectGroupForm form, BindingResult bindingResult) {
        logger.info("/boss/projectGroup/add POST 方法被调用!!" + form.toString());
        try {
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            ProjectGroupBean projectGroupBean = new ProjectGroupBean();
            BeanUtils.copyProperties(form, projectGroupBean);
            projectGroupBean.setCreateTime(new Date());
            projectGroupBean.setUpdateTime(new Date());
            projectGroupBll.addProjectGroup(projectGroupBean);
            return ResultBean.success("添加分组成功!");
        } catch (Exception e) {
            logger.error("添加分组异常：{}", e);
            return ResultBean.failed("添加分组失败!");
        }
    }


    /**
     * 查询项目下的分组,针对接口
     * @param projectId 目编号
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam(value="projectId") Integer projectId,@RequestParam(value="type") Integer type) {
        logger.info("/projectGroup/list GET 方法被调用!!");
        try {
            List<ProjectGroupBean> list = projectGroupBll.getProjectGroup(projectId,type);
            Map<String, Object> result = new HashMap<String, Object>();
            //UI自动化用例
            if(type.equals(2)){
                List<AutoProjectGroupBean> listTmp = new ArrayList<>();
                for(int i=0; i<list.size(); i++){
                    AutoProjectGroupBean autoProjectGroupBean = new AutoProjectGroupBean();
                    autoProjectGroupBean.setId(list.get(i).getId());
                    autoProjectGroupBean.setLabel(list.get(i).getGroupName());
                    List<AutoCaseBean> autoCaseList = autoCaseBll.getAutoCaseByProjectGroupId(projectId, list.get(i).getId());
                    autoProjectGroupBean.setChildren(autoCaseList);
                    listTmp.add(autoProjectGroupBean);
                }
                result.put("projectGroup", listTmp);

            }else{
                //接口自动化接口
                List<ProjectGroupTempBean> listTmp = new ArrayList<>();
                for (int i=0;i<list.size();i++){
                    ProjectGroupTempBean projectGroupTempBean = new ProjectGroupTempBean();
                    projectGroupTempBean.setId(list.get(i).getId());
                    List<InterfaceBean> interfaceList = interfaceBll.getInterface(projectId, list.get(i).getId());
                    projectGroupTempBean.setChildren(interfaceList);
                    projectGroupTempBean.setLabel(list.get(i).getGroupName());
                    listTmp.add(projectGroupTempBean);
                }
                result.put("projectGroup", listTmp);
            }

//            Map<String, Object> result = new HashMap<String, Object>() {{
//                put("projectGroup", listTmp);
//            }};
            return ResultBean.success("查询项目分组成功!",result);
        } catch (Exception e) {
            logger.error("查询项目分组异常：{}", e);
            return ResultBean.failed("查询项目分组失败!");
        }
    }


    /**
     * 查询项目下的分组-batchCase使用
     * @param projectId 目编号
     * @return
     */
    @RequestMapping(value = "/batchList", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> listBatch(@RequestParam(value="projectId") Integer projectId,@RequestParam(value="type") Integer type) {
        logger.info("/projectGroup/list GET 方法被调用!!");
        try {
            List<ProjectGroupBean> list = projectGroupBll.getProjectGroup(projectId,type);
            List<BatchProjectGroupTempBean> listTmp = new ArrayList<>();
            for (int i=0;i<list.size();i++){
                BatchProjectGroupTempBean projectGroupTempBean = new BatchProjectGroupTempBean();
                projectGroupTempBean.setId(list.get(i).getId());
                List<BatchCaseBean> interfaceList = batchCaseBll.getBatchCaseBygroupId(list.get(i).getId());
                projectGroupTempBean.setChildren(interfaceList);
                projectGroupTempBean.setLabel(list.get(i).getGroupName());
                listTmp.add(projectGroupTempBean);
            }
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("projectGroup", listTmp);
            }};
            return ResultBean.success("查询项目分组成功!",result);
        } catch (Exception e) {
            logger.error("查询项目分组异常：{}", e);
            return ResultBean.failed("查询项目分组失败!");
        }
    }


    /**
     * 删除项目分组
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> delete(@RequestParam(value="id") Integer id) {
        logger.info("/projectGroup/delete GET 方法被调用!!");
        try {
            int count = interfaceBll.getInterfaceCountBygroupId(id);
            if(count > 0){
                return ResultBean.failed("分组下面有接口，不能删除!");
            }else{
                projectGroupBll.delete(id);
                return ResultBean.success("删除分组成功!");
            }
        } catch (Exception e) {
            logger.error("删除分组异常：{" + e + "}");
            return ResultBean.failed("删除分组异常!");
        }
    }
}
