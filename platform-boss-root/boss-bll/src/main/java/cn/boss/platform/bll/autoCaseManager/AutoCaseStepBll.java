package cn.boss.platform.bll.autoCaseManager;

import cn.boss.platform.bean.autoManager.AutoCaseBean;
import cn.boss.platform.bean.autoManager.AutoCaseStepBean;
import cn.boss.platform.dao.autoCaseManager.AutoCaseStepMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/11/18.
 */
@Component
public class AutoCaseStepBll {

    @Autowired
    private AutoCaseStepMapper autoCaseStepMapper;

    /**
     * 添加用例步骤
     * @param
     */
    public void addAutoStepCase(AutoCaseStepBean autoCaseStepBean) {
        autoCaseStepMapper.addAutoStepCase(autoCaseStepBean);
    }

    /**
     * 查询用例步骤
     * @param projectId
     * @param groupId
     * @param caseId
     * @param id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<AutoCaseStepBean> getAutoCaseStep(Integer projectId, Integer groupId, Integer caseId, Integer id, int pageIndex, int pageSize){
        int skip = (pageIndex - 1) * pageSize;
        return autoCaseStepMapper.getAutoCaseStep(projectId, groupId,caseId,id,skip,pageSize);
    }

    /**
     * 查询用例
     * @param projectId
     * @param groupId
     * @param caseId
     * @param id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<AutoCaseStepBean> getAutoCaseSteps(Integer projectId, int[] groupId, int[] caseId, int[] id, int pageIndex, int pageSize){
        int startSize = (pageIndex - 1) * pageSize;
        Map<Object, Object> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("groupId", groupId);
        params.put("caseId", caseId);
        params.put("id", id);
        params.put("startSize", startSize);
        params.put("pageSize", pageSize);
        return autoCaseStepMapper.getAutoCaseSteps(params);
    }


    /**
     * 删除用例步骤
     * @param id
     */
    public void deleteBatchCaseStep(Integer id){
        autoCaseStepMapper.deleteAutoCaseStep(id);
    }

    /**
     * 更新用例步骤
     * @param bean
     * @return
     */
    public boolean update(AutoCaseStepBean bean){
        return autoCaseStepMapper.updateAutoCaseStep(bean) > 0;
    }

    /**
     * 查询单用例步骤
     * @param id
     * @return
     */
    public AutoCaseStepBean getAutoCaseStepById(Integer id){
        return autoCaseStepMapper.getAutoCaseStepById(id);
    }

    /**
     * 切换用例
     * @return
     */
    public boolean switchAutoCaseStep(Integer frontId, Integer afterId){
        try{
            autoCaseStepMapper.switchAutoCaseStep(frontId,afterId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}