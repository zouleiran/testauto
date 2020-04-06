package cn.boss.platform.bll.InterfaceManager;

import cn.boss.platform.bean.interfaceManager.ProjectGroupBean;
import cn.boss.platform.dao.interfaceManager.ProjectGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by caosenquan on 2018/7/24.
 */
@Component
public class ProjectGroupBll {

    @Autowired
    private ProjectGroupMapper projectGroupMapper;

    /**
     * 添加分组
     * @param projectGroupBean
     */
    public void addProjectGroup(ProjectGroupBean projectGroupBean) {
        projectGroupMapper.addProjectGoup(projectGroupBean);
    }

    /**
     * 获取项目分组
     * @param projectId
     * @return
     */
    public List<ProjectGroupBean> getProjectGroup(Integer projectId,Integer type){
        return projectGroupMapper.getProjectGroup(projectId,type);
    }

    /**
     * 删除分组
     * @param id
     */
    public void delete(Integer id){
        projectGroupMapper.deleteProjectGroup(id);
    }


    /**
     * 查询单个分组
     * @param id
     * @return
     */
    public ProjectGroupBean getProjectGroupById(Integer id){
        return projectGroupMapper.getProjectGroupById(id);
    }
}
