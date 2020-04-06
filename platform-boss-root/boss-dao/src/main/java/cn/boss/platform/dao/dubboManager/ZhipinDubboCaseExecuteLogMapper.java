package cn.boss.platform.dao.dubboManager;

import cn.boss.platform.bean.dubboManager.ZhipinDubboCaseExecuteLogBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2018/12/25.
 */
public interface ZhipinDubboCaseExecuteLogMapper {

    //添加记录
    void addExecuteLog(ZhipinDubboCaseExecuteLogBean bean);

    //获取执行记录
    List<ZhipinDubboCaseExecuteLogBean> getExecuteLog(@Param("interfaceId") Integer interfaceId, @Param("caseId") Integer caseId, @Param("taskId") Integer taskId,
                                                      @Param("serialId") Integer serialId,@Param("skip") int skip, @Param("pageSize") int pageSize);

    //获取用例日志
    int countCaseExecuteLog(@Param("interfaceId") Integer interfaceId, @Param("caseId") Integer caseId, @Param("taskId") Integer taskId,
                            @Param("serialId") Integer serialId);


}

