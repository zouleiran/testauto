package cn.boss.platform.dao.interfaceManager;

import cn.boss.platform.bean.interfaceManager.CaseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/8/11.
 */
public interface CaseMapper {

    //添加用例
    void addCase(CaseBean cases);

    //分页查询接口下的用例
    List<CaseBean> selectByInterfaceIdPagination(@Param("interfaceId") int interfaceId,
                                                 @Param("skip") int skip, @Param("take") int take);
    //删除用例
    void deleteCase(Integer id);

    //更新接口用例
    void updateCase(CaseBean caseBean);

    //获取单条用例
    CaseBean getCaseById(@Param("id") int id);

    //查询接口下的总用例数
    int countByInterfaceId(@Param("interfaceId") Integer interfaceId);

//    //查询项目下的总用例数
//    int getCaseCountByProjectId(@Param("projectId") int projectId);

    //查询执行的用例
    List<CaseBean> selectExecute(Map<String, Object> executeParams);

    //项目下接口总用例数
    Integer getCaseCount(@Param("projectId") int projectId);

    //通过envid查询是否有使用该环境的用例
    List<CaseBean> selectByEnvId(@Param("envId") Integer envId);

    //查询总用例数
    Integer getCaseCounts();

    //通过参数名查询相关用例
    List<CaseBean> relatedParameters(@Param("relevantParam") String relevantParam);

    //通过参数名查询参数来源用例
    List<CaseBean> sourceParameters(@Param("sourceParameter") String sourceParameter);


}
