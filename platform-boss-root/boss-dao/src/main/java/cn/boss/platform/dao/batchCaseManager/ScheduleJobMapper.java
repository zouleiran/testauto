package cn.boss.platform.dao.batchCaseManager;

import cn.boss.platform.bean.batchCaseManager.BatchCaseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2018/9/6.
 */
public interface ScheduleJobMapper {

    //添加批用例
    void addBatchCase(BatchCaseBean batchCaseBean);

    //查询批用例
    List<BatchCaseBean> getBatchCase(@Param("projectId") Integer projectId, @Param("groupId") Integer groupId, @Param("envId") Integer envId,
                                            @Param("id") Integer id, @Param("startSize") int startSize, @Param("pageSize") int pageSize);


    //查询批用例-用于集成执行
    List<BatchCaseBean> getBatchCaseIntegrade(@Param("projectId") Integer projectId, @Param("groupId") Integer groupId, @Param("envId") Integer envId,
                                     @Param("batchId") Integer batchId);
    //通过groupId查询批用例
    List<BatchCaseBean> getBatchCaseBygroupId( @Param("groupId") Integer groupId);

    //删除用例
    void deleteBatchCase(Integer id);


}
