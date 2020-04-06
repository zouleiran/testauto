package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.ProjectBean;
import cn.boss.platform.bll.InterfaceManager.CaseBll;
import cn.boss.platform.bll.InterfaceManager.InterfaceBll;
import cn.boss.platform.bll.InterfaceManager.ProjectBll;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.ProjectForm;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/7/23.
 */
@Controller
@RequestMapping("/boss/project")
public class ProjectController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectBll projectBll;
    @Autowired
    private InterfaceBll interfaceBll;
    @Autowired
    private CaseBll caseBll;

    /**
     * 添加项目
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> add(@Valid ProjectForm form, BindingResult bindingResult) {
        logger.info("/boss/project/add POST 方法被调用!!" + form.toString());
        try {
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            ProjectBean projectBean = new ProjectBean();
            BeanUtils.copyProperties(form, projectBean);
            projectBean.setCreateTime(new Date());
            projectBean.setUpdateTime(new Date());
            projectBll.addProject(projectBean);
            return ResultBean.success("添加项目成功!");
        } catch (Exception e) {
            logger.error("添加项目异常：{}", e);
            return ResultBean.failed("添加项目失败!");
        }
    }



    /**
     * 查询满足条件的项目
     * project  项目编号，非必输
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam(value="item",required =false, defaultValue = "0") Integer item,@RequestParam(value="projectId",required =false, defaultValue = "0") Integer projectId) {
        logger.info("/project/list GET 方法被调用!!");
        try {
            List<ProjectBean> list = projectBll.getProject(item,projectId);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("project", list);
            }};
            return ResultBean.success("查询项目成功!",result);
        } catch (Exception e) {
            logger.error("查询项目异常：{}", e);
            return ResultBean.failed("查询项目失败!");
        }

    }

    /**
     * 查询满足条件的项目
     * project  项目编号，非必输
     * @return
     */
    @RequestMapping(value = "/desc", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> projectDesc(@RequestParam(value="projectId", defaultValue = "0") Integer projectId) {
        logger.info("/project/desc GET 方法被调用!!");
        try {
            Integer interfaceCount = interfaceBll.getInterfaceCountByprojectId(projectId);
            Integer caseCount = caseBll.getCaseCountByProjectId(projectId);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("interfaceCount", interfaceCount);
                put("caseCount", caseCount);
            }};
            return ResultBean.success("查询项目描述成功!",result);
        } catch (Exception e) {
            logger.error("查询项目描述异常：{}", e);
            return ResultBean.failed("项目描述失败!");
        }

    }




}
