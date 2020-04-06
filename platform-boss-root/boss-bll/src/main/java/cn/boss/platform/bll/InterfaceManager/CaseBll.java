package cn.boss.platform.bll.InterfaceManager;

import cn.boss.platform.bean.interfaceManager.CaseBean;
import cn.boss.platform.dao.interfaceManager.CaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by admin on 2018/8/11.
 */
@Component
public class CaseBll {

    @Autowired
    private CaseMapper caseMapper;

    /**
     * 添加用例
     * @param caseBean
     */
    public void addCase(CaseBean caseBean) {
        caseMapper.addCase(caseBean);
    }

    /**
     * 分页查询接口下用例
     * @param interfaceId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<CaseBean> selectByInterfaceIdPagination(int interfaceId, int pageIndex, int pageSize) {
        int skip = (pageIndex - 1) * pageSize;
        return caseMapper.selectByInterfaceIdPagination(interfaceId, skip, pageSize);
    }

    /**
     * 删除用例
     * @param id
     */
    public void deleteCase(Integer id){
        caseMapper.deleteCase(id);
    }

    /**
     * 更新用例
     * @param caseBean
     */
    public void updateCase(CaseBean caseBean) {
        caseMapper.updateCase(caseBean);
    }

    /**
     * 获取单条用例
     * @param id
     * @return
     */
    public CaseBean getCaseById(Integer id){
        return caseMapper.getCaseById(id);
    }

    /**
     * 查询接口下用例数
     * @param interfaceId
     * @return
     */
    public Integer getCaseCount(Integer interfaceId) {
        return caseMapper.countByInterfaceId(interfaceId);
    }

//    /**
//     * 查询项目下case总数
//     * @param projectId
//     * @return
//     */
//    public Integer getCaseCountByProjectId(Integer projectId) {
//        return caseMapper.getCaseCountByProjectId(projectId);
//    }


    /**
     * 查询执行用例
     * @param projectId
     * @param groupId
     * @param envtId
     * @param interfaceIds
     * @param caseIds
     * @param author
     * @return
     */
    public List<CaseBean> getExecute(Integer projectId,String groupId,Integer envtId,String version,int[] interfaceIds,int[] caseIds,String author) {
        Map<String, Object> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("interfaceIds", interfaceIds);
        params.put("author", author);
        params.put("caseIds", caseIds);
        params.put("groupId", groupId);
        params.put("envtId", envtId);
        params.put("version", version);
        return caseMapper.selectExecute(params);
    }

    /**
     * 项目下总用例数
     * @return
     */
    public int getCaseCountByProjectId(Integer projectId){
        return caseMapper.getCaseCount(projectId);
    }

    /**
     * 查询使用该环境的用例
     * @param envId
     * @return
     */
    public List<CaseBean> selectByEnvId(int envId) {
        return caseMapper.selectByEnvId(envId);
    }

    /**
     * 总用例数
     * @return
     */
    public Integer getCaseCounts(){
        return caseMapper.getCaseCounts();
    }

    /**
     * 查询用例中引用参数
     * @param relevantParam
     * @return
     */
    public List<CaseBean> relatedParameters(String relevantParam){
        return caseMapper.relatedParameters(relevantParam);
    }

    /**
     * 查询用例中参数来源用例
     * @param relevantParameter
     * @return
     */
    public List<CaseBean> sourceParameters(String relevantParameter){
        return caseMapper.sourceParameters(relevantParameter);
    };

}
