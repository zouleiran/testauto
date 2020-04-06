package cn.boss.platform.bll.autoCaseManager;

import cn.boss.platform.bean.autoManager.AutoCaseExecuteLogBean;
import cn.boss.platform.bean.interfaceManager.CaseExecuteLogBean;
import cn.boss.platform.dao.autoCaseManager.AutoCaseExecuteLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2019/11/21.
 */
@Component
public class AutoCaseExecuteLogBll {

    @Autowired
    private AutoCaseExecuteLogMapper autoCaseExecuteLogMapper;

    /**
     *添加用例执行日志
     * @param bean
     */
    public void addAutoCaseStepLog(AutoCaseExecuteLogBean bean){
        autoCaseExecuteLogMapper.addAutoCaseStepLog(bean);
    }

    /**
     * 按照条件查询用例
     * @param projectId
     * @param taskId
     * @param caseId
     * @param caseStepId
     * @param startTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<AutoCaseExecuteLogBean> getAutoCaseStepLog(Integer projectId, Integer taskId,Integer serialId, Integer caseId, Integer caseStepId,String startTime,
                                                           String endTime, int pageIndex, int pageSize){
        int skip = (pageIndex - 1) * pageSize;
        return autoCaseExecuteLogMapper.getAutoCaseStepLog(projectId, taskId,serialId, caseId, caseStepId, startTime, endTime, skip, pageSize);
    }

    /**
     * 按照条件查询用例总数
     * @param projectId
     * @param taskId
     * @param caseId
     * @param caseStepId
     * @param startTime
     * @param endTime
     * @return
     */
    public Integer getAutoCaseStepLogCount(Integer projectId, Integer taskId,Integer serialId, Integer caseId, Integer caseStepId,
                                                           String startTime, String endTime){
        return autoCaseExecuteLogMapper.getAutoCaseStepLogCount(projectId, taskId,serialId, caseId, caseStepId, startTime, endTime);
    }

    /**
     * 按serial_id分组查询每组的第一条数据
     * @param projectId
     * @param taskId
     * @param serialId
     * @param startTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<AutoCaseExecuteLogBean> executeLog(Integer projectId, Integer taskId, Integer serialId, String startTime, String endTime, int pageIndex, int pageSize){
        int startSize = (pageIndex - 1) * pageSize;
        return autoCaseExecuteLogMapper.executeLog(projectId,taskId,serialId,startTime, endTime,startSize,pageSize);
    }

    /**
     * 按serial_id分组查询每组的第一条数据的总数
     * @param projectId
     * @param taskId
     * @param serialId
     * @param startTime
     * @param endTime
     * @return
     */
    public Integer executeLogCount(Integer projectId, Integer taskId, Integer serialId, String startTime, String endTime){
        return autoCaseExecuteLogMapper.executeLogCount(projectId,taskId,serialId,startTime, endTime);
    }

    /**
     * 查询流水号所有日志成功总数
     * @param serialId
     * @return
     */
    public int getExecuteSucessCount(Integer serialId) {
        return autoCaseExecuteLogMapper.getExecuteSucessCount(serialId);
    }

    /**
     * 查询流水号所有日志总数
     * @param serialId
     * @return
     */
    public int countSerialIdExecuteLog(Integer serialId) {
        return autoCaseExecuteLogMapper.countBySerialId(serialId);
    }
}