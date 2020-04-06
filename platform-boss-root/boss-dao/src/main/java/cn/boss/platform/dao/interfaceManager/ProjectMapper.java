package cn.boss.platform.dao.interfaceManager;

import cn.boss.platform.bean.interfaceManager.ProjectBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by caosenquan on 2018/7/23.
 */
public interface ProjectMapper {

    //添加项目
    void addProject(ProjectBean project);

    //查询所有使用中的项目
    List<ProjectBean> getProject(@Param("item") int item, @Param("projectId") int projectId);

    //通过id查询项目名
    String queryProjectNameById(@Param("projectId") int projectId);

    //项目个数
    Integer getProjectCount();

    //查询单项目
    ProjectBean getProjectByid(@Param("projectId") int projectId);

//
//    //通过组查项目
//    List<ProjectBean> getProjectByItem(@Param("item") int item);

    //按照team分组查询项目
    List<ProjectBean> getProjectByProjectTeam();


}
