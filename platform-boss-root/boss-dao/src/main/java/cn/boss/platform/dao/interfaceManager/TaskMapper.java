package cn.boss.platform.dao.interfaceManager;

import cn.boss.platform.bean.interfaceManager.TaskBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2018/8/21.
 */
public interface TaskMapper {

    //添加任务
    Integer insertTask(TaskBean bean);

    //删除任务
    void deleteTask(@Param("taskId") int taskId);

    //更新任务
    void updateTask(TaskBean bean);

    //查询任务
    List<TaskBean> searchTask(@Param("taskId") Integer taskId,@Param("projectId") Integer projectId, @Param("taskName") String taskName, @Param("type") String type, @Param("startSize") int startSize, @Param("pageSize") int pageSize);

    //查询单个任务
    List<TaskBean>  searchTaskById(@Param("taskId") Integer taskId);

    //查询任务总数
    public int searchCountTask(@Param("projectId") Integer projectId,@Param("taskId") Integer taskId, @Param("taskName") String taskName);

    List<TaskBean> selectTimerTask();

    int getLastId();

    String searchjobstatusById(@Param("id") Integer id);
}
