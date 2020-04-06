package cn.boss.platform.dao.interfaceManager;

import cn.boss.platform.bean.interfaceManager.ProjectGroupBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by caosenquan on 2018/7/24.
 */
public interface ProjectGroupMapper {

    //添加项目分组
    void addProjectGoup(ProjectGroupBean project);

    //查询项目下的所有分组
    public List<ProjectGroupBean> getProjectGroup(@Param("projectId") int projectId, @Param("type") int type);

    //删除分组
    void deleteProjectGroup(Integer id);

    //查询单项目分组
    ProjectGroupBean getProjectGroupById(Integer id);

}
