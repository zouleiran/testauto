package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.CaseBean;
import cn.boss.platform.bean.interfaceManager.EnvBean;
import cn.boss.platform.bll.InterfaceManager.CaseBll;
import cn.boss.platform.bll.InterfaceManager.EnvBll;
import cn.boss.platform.bll.InterfaceManager.ProjectBll;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.EnvForm;
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
 * Created by admin on 2018/6/6.
 */
@Controller
@RequestMapping("/boss/env")
public class EnvController extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(EnvController.class);

    @Autowired
    private EnvBll envBll;
    @Autowired
    private ProjectBll projectBll;
    @Autowired
    private CaseBll caseBll;

    /**
     * 添加环境
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> add(@Valid EnvForm form, BindingResult bindingResult) {
        logger.info("/boss/env/add POST 方法被调用!!" + form.toString());
        try {
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            EnvBean envBean = new EnvBean();
            BeanUtils.copyProperties(form, envBean);
            envBean.setCreateTime(new Date());
            envBean.setUpdateTime(new Date());
            envBll.addEnv(envBean);
            return ResultBean.success("添加环境成功!");
        } catch (Exception e) {
            logger.error("添加环境异常：{}", e);
            return ResultBean.failed("添加环境失败!");
        }
    }

    /**
     * 删除环境
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> delete(@RequestParam(value="id") Integer id) {
        logger.info("/env/delete GET 方法被调用!!");
        try {
            List<CaseBean> beans = caseBll.selectByEnvId(id);
            if(beans !=null && beans.size() > 0){
                return ResultBean.success("该环境有用例使用，不能删除!");
            }else{
                envBll.deleteEnv(id);
                return ResultBean.success("删除环境成功!");
            }
        } catch (Exception e) {
            logger.error("删除环境ip异常：{" + e + "}");
            return ResultBean.failed("删除环境异常!");
        }
    }


    /**
     * 查询环境
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> list(@RequestParam(value="item",required=false) Integer item,@RequestParam(value="projectId") Integer projectId, @RequestParam(value="env",required=false) String env, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        try {
            logger.info("/env/list GET 方法被调用!! param: " +pageIndex,pageSize);
            List<EnvBean> list = envBll.queryEnv(item,projectId,env,pageIndex, pageSize);
            for (EnvBean c : list) {
                String name = projectBll.getProjectName(c.getProjectId());
                c.setProjectName(name);
            }
            int listCount = list ==null ? 0 : list.size();
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("envList", list);
                put("envCount", listCount);
            }};
            return ResultBean.success("查询环境成功!", result);
        } catch (Exception e) {
            logger.error("查询用例异常：{}", e);
            return ResultBean.failed("查询用例异常!");
        }
    }

    /**
     * 查询单个环境
     * @param id
     * @return
     */
    @RequestMapping(value = "/envById", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> searchEnvById(@RequestParam(value="id",required =true, defaultValue = "0") Integer id) {
        logger.info("/task/list GET 方法被调用!!");
        try {
            EnvBean bean = envBll.getEnvById(id);
            return ResultBean.success("查询环境成功!", bean);
        } catch (Exception e) {
            logger.error("查询环境异常：{" + e + "}");
            return ResultBean.failed("查询环境异常!");
        }
    }

    /**
     * 修改环境
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> update(@Valid EnvForm form, BindingResult bindingResult) {
        logger.info("/env/update POST 方法被调用!!"+form.toString());
        try{
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            EnvBean envBean = new EnvBean();
            BeanUtils.copyProperties(form,envBean);
            envBean.setUpdateTime(new Date());
            envBll.updateEnv(envBean);
            return ResultBean.success("更新环境成功！");
        } catch (Exception e) {
            logger.error("更新环境异常：{}", e);
            return ResultBean.failed("更新环境失败!");
        }
    }





}
