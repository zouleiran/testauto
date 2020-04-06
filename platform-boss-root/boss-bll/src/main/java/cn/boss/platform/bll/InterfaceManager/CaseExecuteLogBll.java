package cn.boss.platform.bll.InterfaceManager;

import cn.boss.platform.bean.interfaceManager.CaseExecuteLogBean;
import cn.boss.platform.bean.interfaceManager.DailyCaseBean;
import cn.boss.platform.bean.interfaceManager.ReportBean;
import cn.boss.platform.dao.interfaceManager.CaseExecuteLogMapper;
import cn.boss.platform.dao.interfaceManager.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2018/8/15.
 */
@Component
public class CaseExecuteLogBll {

    @Autowired
    private CaseExecuteLogMapper caseExecuteLogMapper;
    @Autowired
    private ReportMapper reportMapper;

    /**
     *添加用例执行日志
     * @param bean
     */
    public void addLog(CaseExecuteLogBean bean){
        caseExecuteLogMapper.addLog(bean);
    }

    /**
     * 分页查询单条用例执行日志
     * @param caseId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<CaseExecuteLogBean> getCaseExecuteLogs(int caseId, int pageIndex, int pageSize) {
        int skip = (pageIndex - 1) * pageSize;
        return caseExecuteLogMapper.selectLogByCaseId(caseId, skip, pageSize);
    }

    /**
     * 分页查询单条batch用例执行日志
     * @param batchId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<CaseExecuteLogBean> getBatchCaseExecuteLogs(int batchId, int pageIndex, int pageSize) {
        int skip = (pageIndex - 1) * pageSize;
        return caseExecuteLogMapper.selectLogByBatchId(batchId, skip, pageSize);
    }

    /**
     * 查询单条用例执行日志总数
     * @param caseId
     * @return
     */
    public int countCaseExecuteLog(int caseId) {
        return caseExecuteLogMapper.selectLogCountByCaseId(caseId);
    }

    /**
     * 查询单条用例执行日志总数
     * @param batchId
     * @return
     */
    public int countBatchCaseExecuteLog(int batchId) {
        return caseExecuteLogMapper.selectLogCountByBatchCaseId(batchId);
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
    public List<CaseExecuteLogBean> executeLog(Integer projectId,Integer taskId,Integer serialId,String startTime,String endTime, int pageIndex, int pageSize){
        int startSize = (pageIndex - 1) * pageSize;
        return caseExecuteLogMapper.executeLog(projectId,taskId,serialId,startTime, endTime,startSize,pageSize);
    }

    /**
     * 按serial_id分组查询每组的第一条数据
     * @param projectId
     * @param taskId
     * @param serialId
     * @param startTime
     * @param endTime
     * @return
     */
    public int executeLogCount(Integer projectId,Integer taskId,Integer serialId,String startTime,String endTime){
        return caseExecuteLogMapper.executeLogCount(projectId,taskId,serialId,startTime, endTime);
    }

    /**
     * 查询流水号执行日志成功总数
     * @param serialId
     * @return
     */
    public int getExecuteSucessCount(Integer serialId) {
        return caseExecuteLogMapper.getExecuteSucessCount(serialId);
    }

    /**
     * 查询流水号所有日志总数
     * @param serialId
     * @return
     */
    public int countSerialIdExecuteLog(Integer serialId) {
        return caseExecuteLogMapper.countBySerialId(serialId);
    }

    /**
     * 查询流水号执行日志
     * @param serialId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<CaseExecuteLogBean> getDetailExecuteLog(int serialId, int pageIndex, int pageSize) {
        int startSize = (pageIndex - 1) * pageSize;
        return caseExecuteLogMapper.getDetailExecuteLog(serialId,startSize,pageSize);
    }

    public List<ReportBean> getReport(String serialId){
        return reportMapper.selectReportBySerialId(serialId);
    }


    /**
     * 每日项目下执行用例数
     * @param daily
     * @return
     */
    public List<DailyCaseBean> getDailyExecuteCase(String daily){
        return caseExecuteLogMapper.getDailyExecuteCases(daily);

    }




}
