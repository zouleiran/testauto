package cn.boss.platform.bll.InterfaceManager;



import cn.boss.platform.bean.interfaceManager.ProjectBean;
import cn.boss.platform.dao.interfaceManager.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Created by caosenquan on 2018/7/23.
 */
@Component
public class ProjectBll {

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 添加项目
     * @param projectBean
     */
    public void addProject(ProjectBean projectBean) {
        projectMapper.addProject(projectBean);
    }

    /**
     * 获取项目
     * @param item
     * @param projectId
     * @return
     */
    public List<ProjectBean> getProject(Integer item,Integer projectId){
        return projectMapper.getProject(item, projectId);
    }

    /**
     * 获取项目名
     * @param projectId
     * @return
     */
    public String getProjectName(Integer projectId){
        return projectMapper.queryProjectNameById(projectId);
    }


    /**
     * 获取项目总数
     * @return
     */
    public Integer getProjectCount(){
        return projectMapper.getProjectCount();
    }

    /**
     * 查询单项目
     * @param projectId
     * @return
     */
    public ProjectBean getProjectByid(Integer projectId){
        return projectMapper.getProjectByid(projectId);
    }


    /**
     * 按照team分组查询项目
     * @return
     */
    public List<ProjectBean> getProjectByProjectTeam(){
        return projectMapper.getProjectByProjectTeam();
    }
}
