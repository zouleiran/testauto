package cn.boss.platform.bll.InterfaceManager;

import cn.boss.platform.bean.interfaceManager.TaskBean;
import cn.boss.platform.dao.interfaceManager.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2018/8/21.
 */
@Component
public class TaskBll {

    @Autowired
    private TaskMapper taskMapper;

    /**
     * 添加任务
     * @param bean
     * @return
     */
    public Integer add(TaskBean bean) {
        return taskMapper.insertTask(bean);
    }

    /**
     * 删除任务
     * @param taskId
     */
    public void delete(int taskId) {
        taskMapper.deleteTask(taskId);
    }

    /**
     * 查询任务
     * @param taskId
     * @param taskName
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<TaskBean> search(Integer taskId, Integer projectId, String taskName, String type, int pageIndex, int pageSize) {
        int skip = (pageIndex - 1) * pageSize;
        return taskMapper.searchTask(taskId,projectId,taskName,type, skip, pageSize);
    }

    /**
     * 查询单个任务
     * @param taskId
     * @return
     */
    public List<TaskBean> searchTaskById(Integer taskId) {
        return taskMapper.searchTaskById(taskId);
    }

    /**
     * 查询条件下任务总数
     * @param taskId
     * @param taskName
     * @return
     */
    public int searchCount(Integer projectId,Integer taskId,String taskName) {
        return taskMapper.searchCountTask(projectId,taskId,taskName);
    }



    /**
     * 更新任务
     * @param bean
     */
    public void update(TaskBean bean) {
        taskMapper.updateTask(bean);
    }

    public int getLastId() {
        return taskMapper.getLastId();
    }

    public String searchjobstatusById(Integer id) {
        return taskMapper.searchjobstatusById(id);
    }
}
