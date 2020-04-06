package cn.boss.platform.dao.autoCaseManager;

import cn.boss.platform.bean.autoManager.AutoCaseBean;
import cn.boss.platform.bean.autoManager.AutoCaseStepBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/11/18.
 */
public interface AutoCaseStepMapper {


    //添加用例步骤
    void addAutoStepCase(AutoCaseStepBean autoCaseStepBean);

    //查询批用例
    List<AutoCaseStepBean> getAutoCaseStep(@Param("projectId") Integer projectId, @Param("groupId") Integer groupId, @Param("caseId") Integer caseId, @Param("id") Integer id, @Param("startSize") int startSize, @Param("pageSize") int pageSize);

    //查询批用例
    List<AutoCaseStepBean> getAutoCaseSteps(Map<Object, Object> executeParam);

    //删除用例步骤
    void deleteAutoCaseStep(Integer id);

    //更新用例步骤
    Integer updateAutoCaseStep(AutoCaseStepBean bean);

    //获取单条步骤
    AutoCaseStepBean getAutoCaseStepById(@Param("id") Integer id);

    //用例步骤编号互换
    void switchAutoCaseStep(@Param("frontId") int frontId,@Param("afterId") int afterId);




}