package cn.boss.platform.dao.interfaceManager;

import cn.boss.platform.bean.interfaceManager.CaseExecuteLogBean;
import cn.boss.platform.bean.interfaceManager.DailyCaseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2018/8/15.
 */
public interface CaseExecuteLogMapper {

    //添加用例执行日志
    void addLog(CaseExecuteLogBean bean);

    //通过用例编号分页查询单条用例执行日志
    List<CaseExecuteLogBean> selectLogByCaseId(@Param("caseId") int caseId,
                                                      @Param("skip") int skip, @Param("take") int take);

    //通过batch用例比啊你好分页查询单条用例执行日志
    List<CaseExecuteLogBean> selectLogByBatchId(@Param("batchId") int batchId,
                                               @Param("skip") int skip, @Param("take") int take);

    //通过用例编号查询单条用例执行日志总数
    int selectLogCountByCaseId(@Param("caseId") int caseId);

    //通过batch用例编号查询单条用例执行日志总数
    int selectLogCountByBatchCaseId(@Param("batchId") int batchId);

    //按条件查询每组第一条数据
    List<CaseExecuteLogBean> executeLog(@Param("projectId") Integer projectId,
                                        @Param("taskId") Integer taskId,
                                        @Param("serialId") Integer serialId,
                                        @Param("startTime") String startTime,
                                        @Param("endTime") String endTime,
                                        @Param("startSize") int startSize,
                                        @Param("pageSize") int pageSize );

    //按条件查询所有日志总数
    int executeLogCount(@Param("projectId") Integer projectId,
                        @Param("taskId") Integer taskId,
                        @Param("serialId") Integer serialId,
                        @Param("startTime") String startTime,
                        @Param("endTime") String endTime);

    //使用流水号查询所有日志成功总数
    int getExecuteSucessCount(@Param("serialId") Integer serialId);

    //使用流水号查询所有日志总数
    int countBySerialId(@Param("serialId") Integer serialId);

    //使用流水号查询所有日志信息
    List<CaseExecuteLogBean> getDetailExecuteLog(@Param("serialId") int serialId,@Param("startSize") int startSize, @Param("pageSize") int pageSize);

    //获取每日某项目所有用例执行数
    List<DailyCaseBean> getDailyExecuteCases(@Param("daily") String daily);


}
