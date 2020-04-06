package cn.boss.platform.dao.batchCaseManager;

import cn.boss.platform.bean.batchCaseManager.BatchCaseStepBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2018/9/7.
 */
public interface BatchCaseStepMapper {

    //添加用例步骤
    void addBatchCaseStep(BatchCaseStepBean step);

    //分页查询用例执行步骤
    List<BatchCaseStepBean> getBatchStepBycaseId(@Param("batchId") int batchId,
                                                 @Param("skip") int skip, @Param("take") int take);

    //分页查询用例执行步骤
    List<BatchCaseStepBean> getBatchStepIntegradeBycaseId(@Param("batchId") int batchId,@Param("batchCaseId") int[] batchCaseId);

    //获取单条步骤
    BatchCaseStepBean getBatchCaseById(@Param("id") int id);

    //删除用例步骤
    void deleteBatchCaseStep(Integer id);

    //更新用例步骤
    void updateBatchCaseStep(BatchCaseStepBean caseBean);

    //用例编号互换
    void switchBatchCase(@Param("frontId") int frontId,@Param("afterId") int afterId);



}
