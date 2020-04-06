package cn.boss.platform.dao.autoCaseManager;

import cn.boss.platform.bean.autoManager.AutoCaseBean;
import cn.boss.platform.bean.autoManager.AutoCaseStepBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/11/15.
 */
public interface AutoCaseMapper {

    //添加批用例
    void addAutoCase(AutoCaseBean autoCaseBean);

    //查询批用例
    List<AutoCaseBean> getAutoCase(@Param("projectId") Integer projectId, @Param("groupId") Integer groupId, @Param("envId") Integer envId,
                                     @Param("id") Integer id, @Param("startSize") int startSize, @Param("pageSize") int pageSize);

    //查询批用例
    List<AutoCaseBean> getAutoCases(Map<Object, Object> executeParam);

    //查询用例总数
    Integer getAutoCaseCount(@Param("projectId") Integer projectId, @Param("groupId") Integer groupId, @Param("envId") Integer envId,
                                   @Param("id") Integer id);


    //通过groupId查询用例
    List<AutoCaseBean> getAutoCaseBygroupId(List list);

    //删除用例
    void deleteAutoCase(Integer id);

    //通过项目和组名查询接口
    List<AutoCaseBean> getAutoCaseByProjectGroupId(@Param("projectId") Integer projectId, @Param("groupId") Integer groupId);

}