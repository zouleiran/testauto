package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.TaskBean;
import cn.boss.platform.bll.InterfaceManager.TaskBll;
import cn.boss.platform.service.interfaceManager.QuartzJobFactory;
import cn.boss.platform.service.interfaceManager.QuartzManager;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.TaskForm;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
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
 * Created by admin on 2018/8/21.
 */
@Controller
@RequestMapping("/boss/task")
public class TaskController extends AbstractBaseController {
    private Scheduler scheduler;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private TaskBll taskBll;
    @Autowired
    private QuartzManager quartzManager;
    /**
     * 添加任务
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> addTask(@Valid TaskForm form, BindingResult bindingResult) {
        logger.info("/task/add POST 方法被调用!!"+form.toString());
        try{
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            TaskBean task = new TaskBean();
            BeanUtils.copyProperties(form,task);
            task.setCreateTime(new Date());
            task.setUpdateTime(new Date());
            if(task.getJobstatus().equals("1")&&task.getCron()==null)
                return ResultBean.failed("添加任务失败!,在需要定时任务时候请填写时间描述");
            if (task.getJobstatus().equals("1")&&CronExpression.isValidExpression(task.getCron())!=true)
                return ResultBean.failed("添加任务失败!,请填写正确时间描述");
            taskBll.add(task);
            if (task.getJobstatus().equals("1"))
            {
                CronExpression.isValidExpression(task.getCron());
                String taskID=taskBll.getLastId()+"";
                Class clazz = QuartzJobFactory.class;
                quartzManager.addJob(taskID, taskID, taskID, taskID, clazz, task.getCron());
            }
            else {
                task.setJobstatus("");
                task.setCron("");
            }
            return ResultBean.success("添加任务成功！");
        } catch (Exception e) {
            logger.error("添加任务异常：{}", e);
            return ResultBean.failed("添加任务失败!");
        }
    }

    /**
     * 删除任务
     * taskId 任务编号
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> deleteTask(@RequestParam(value="id",required =true, defaultValue = "0") Integer id) {
        logger.info("/task/delete POST 方法被调用!!"+id);
        try{
            if(id.equals(54) || id.equals(55) || id.equals(73) || id.equals(74) || id.equals(75) || id.equals(76) ){
                return ResultBean.failed("特殊任务，不能删除！");
            }
            taskBll.delete(id);
            String ID=id+"";
            quartzManager.removeJob(ID, ID, ID, ID);
            return ResultBean.success("删除任务成功！");
        } catch (Exception e) {
            logger.error("删除任务异常：{}", e);
            return ResultBean.failed("删除任务失败!");
        }
    }

    /**
     * 查询任务
     * @param taskId
     * @param taskName
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> searchTask(@RequestParam(value="taskId",required =true) Integer taskId,
                                         @RequestParam(value="projectId",required =true) Integer projectId,
                                         @RequestParam("taskName") String taskName,
                                         @RequestParam("type") String type,
                                         @RequestParam("pageIndex") Integer pageIndex,
                                         @RequestParam("pageSize") Integer pageSize) {
        logger.info("/task/list GET 方法被调用!!");
        try {
            List<TaskBean> list = taskBll.search(taskId,projectId,taskName,type,pageIndex,pageSize);
            int count = taskBll.searchCount(projectId,taskId,taskName);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("list", list);
                put("taskCount", count);
            }};
            return ResultBean.success("查询任务成功!", result);
        } catch (Exception e) {
            logger.error("查询任务异常：{" + e + "}");
            return ResultBean.failed("查询任务异常!");
        }

    }


    /**
     * 查询单个任务
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/listById", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> searchTaskById(@RequestParam(value="taskId",required =true, defaultValue = "0") Integer taskId) {
        logger.info("/task/list GET 方法被调用!!");
        try {
            List<TaskBean> list = taskBll.searchTaskById(taskId);
            return ResultBean.success("查询任务成功!", list);
        } catch (Exception e) {
            logger.error("查询任务异常：{" + e + "}");
            return ResultBean.failed("查询任务异常!");
        }

    }
    /**
     * 修改任务
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> updateTask(@Valid TaskForm form, BindingResult bindingResult) {
        logger.info("/task/add POST 方法被调用!!"+form.toString());
        try{
            if(form.getjobstatus().equals("1")&&form.getcron()==null)
                return ResultBean.failed("添加任务失败!,在需要定时任务时候请填写正确时间描述");
            if (form.getjobstatus().equals("1")&&CronExpression.isValidExpression(form.getcron())!=true)
                return ResultBean.failed("添加任务失败!,请填写正确时间描述");
            String errorMsg = validateData(bindingResult);
            if (!StringUtils.isEmpty(errorMsg)) {
                logger.info("params error:{}", errorMsg);
                return ResultBean.failed(errorMsg);
            }
            String ID=form.getId()+"";
            String flag=taskBll.searchjobstatusById(form.getId());//1代表之前有定时任务，0代表之前没有
            TaskBean task = new TaskBean();
            BeanUtils.copyProperties(form,task);
            task.setUpdateTime(new Date());
            taskBll.update(task);
            if (task.getJobstatus().equals("1")&&flag.equals("0"))//之前没开启了定时，现在改为开启，使用add
            {
                Class clazz = QuartzJobFactory.class;
                quartzManager.addJob(ID, ID, ID, ID, clazz, task.getCron());
            }
            else if (task.getJobstatus().equals("0")&&flag.equals("1"))//之前开启了定时，现在改为没开启，使用关闭
            {
                quartzManager.removeJob(ID, ID, ID, ID);
            }
            else if (task.getJobstatus().equals("1")&&flag.equals("1"))//之前开启了定时，现在改为开启只改时间，使用修改
            {
                quartzManager.modifyJobTime(ID, ID, ID, ID, task.getCron());
            }
            //之前关闭了定时，现在改为关闭不需要修改
            return ResultBean.success("更新任务成功！");
        } catch (Exception e) {
            logger.error("更新任务异常：{}", e);
            return ResultBean.failed("更新任务失败!");
        }
    }



}
