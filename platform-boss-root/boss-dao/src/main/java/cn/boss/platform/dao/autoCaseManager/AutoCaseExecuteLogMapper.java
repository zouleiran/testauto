package cn.boss.platform.dao.autoCaseManager;

import cn.boss.platform.bean.autoManager.AutoCaseExecuteLogBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2019/11/21.
 */
public interface AutoCaseExecuteLogMapper {

    //添加用例执行日志
    void addAutoCaseStepLog(AutoCaseExecuteLogBean bean);

    //按条件查询所有日志信息
    List<AutoCaseExecuteLogBean> getAutoCaseStepLog(@Param("projectId") Integer projectId,
                                        @Param("taskId") Integer taskId,
                                        @Param("serialId") Integer serialId,
                                        @Param("caseId") Integer caseId,
                                        @Param("caseStepId") Integer caseStepId,
                                        @Param("startTime") String startTime,
                                        @Param("endTime") String endTime,
                                        @Param("startSize") int startSize,
                                        @Param("pageSize") int pageSize );

    //按条件查询所有日志信息总数
    Integer getAutoCaseStepLogCount(@Param("projectId") Integer projectId,
                                                @Param("taskId") Integer taskId,
                                                @Param("serialId") Integer serialId,
                                                @Param("caseId") Integer caseId,
                                                @Param("caseStepId") Integer caseStepId,
                                                @Param("startTime") String startTime,
                                                @Param("endTime") String endTime);


    //按条件查询每组第一条数据
    List<AutoCaseExecuteLogBean> executeLog(@Param("projectId") Integer projectId,
                                        @Param("taskId") Integer taskId,
                                        @Param("serialId") Integer serialId,
                                        @Param("startTime") String startTime,
                                        @Param("endTime") String endTime,
                                        @Param("startSize") int startSize,
                                        @Param("pageSize") int pageSize );

    //按条件查询每组第一条数据
    Integer executeLogCount(@Param("projectId") Integer projectId,
                                            @Param("taskId") Integer taskId,
                                            @Param("serialId") Integer serialId,
                                            @Param("startTime") String startTime,
                                            @Param("endTime") String endTime);

    //使用流水号查询流水下日志成功总数
    int getExecuteSucessCount(@Param("serialId") Integer serialId);

    //使用流水号查询所有日志总数
    int countBySerialId(@Param("serialId") Integer serialId);



}