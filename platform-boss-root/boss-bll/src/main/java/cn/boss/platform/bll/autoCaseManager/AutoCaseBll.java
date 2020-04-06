package cn.boss.platform.bll.autoCaseManager;

import cn.boss.platform.bean.autoManager.AutoCaseBean;
import cn.boss.platform.dao.autoCaseManager.AutoCaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/11/15.
 */
@Component
public class AutoCaseBll {


    @Autowired
    private AutoCaseMapper autoCaseMapper;

    /**
     * 添加用例
     * @param autoCaseBean
     */
    public void addAutoCase(AutoCaseBean autoCaseBean) {
        autoCaseMapper.addAutoCase(autoCaseBean);
    }

    /**
     * 查询用例
     * @param projectId
     * @param groupId
     * @param envId
     * @param id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<AutoCaseBean> getAutoCase(Integer projectId, Integer groupId, Integer envId, Integer id, int pageIndex, int pageSize){
        int skip = (pageIndex - 1) * pageSize;
        return autoCaseMapper.getAutoCase(projectId, groupId,envId,id,skip,pageSize);
    }

    public List<AutoCaseBean> getAutoCases(Integer projectId, int[] groupId, int[] caseId, int pageIndex, int pageSize){
        int startSize = (pageIndex - 1) * pageSize;
        Map<Object, Object> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("groupId", groupId);
        params.put("caseId", caseId);
        params.put("startSize", startSize);
        params.put("pageSize", pageSize);
        return autoCaseMapper.getAutoCases(params);
    }


    /**
     * 查询用例总数
     * @param projectId
     * @param groupId
     * @param envId
     * @param id
     * @return
     */
    public Integer getAutoCaseCount(Integer projectId, Integer groupId, Integer envId, Integer id){
        return autoCaseMapper.getAutoCaseCount(projectId, groupId,envId,id);
    }

    /**
     * 删除用例
     * @param id
     */
    public void deleteBatchCase(Integer id){
        autoCaseMapper.deleteAutoCase(id);
    }


    /**
     * 查询分组下的用例
     * @param groupId
     * @return
     */
    public List<AutoCaseBean> getAutoCaseBygroupId(List groupId){
        return autoCaseMapper.getAutoCaseBygroupId(groupId);
    }

    /**
     * 通过项目和分组查用例
     * @param projectId
     * @param groupId
     * @return
     */
    public List<AutoCaseBean> getAutoCaseByProjectGroupId(Integer projectId, Integer groupId){
        return autoCaseMapper.getAutoCaseByProjectGroupId(projectId, groupId);
    }



}