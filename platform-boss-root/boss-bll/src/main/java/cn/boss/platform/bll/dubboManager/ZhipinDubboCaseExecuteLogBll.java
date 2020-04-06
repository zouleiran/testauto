package cn.boss.platform.bll.dubboManager;

import cn.boss.platform.bean.dubboManager.ZhipinDubboCaseExecuteLogBean;
import cn.boss.platform.dao.dubboManager.ZhipinDubboCaseExecuteLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2018/12/25.
 */
@Component
public class ZhipinDubboCaseExecuteLogBll {

    @Autowired
    private ZhipinDubboCaseExecuteLogMapper zhipinDubboCaseExecuteLogMapper;

    /**
     * 添加日志
     * @param bean
     */
    public void addExecuteLog(ZhipinDubboCaseExecuteLogBean bean){
        zhipinDubboCaseExecuteLogMapper.addExecuteLog(bean);
    }

    /**
     * 获取日志
     * @param interfaceId
     * @param caseId
     * @param taskId
     * @param serialId
     * @return
     */
    public List<ZhipinDubboCaseExecuteLogBean> getExecuteLog(Integer interfaceId, Integer caseId, Integer taskId, Integer serialId, int pageIndex, int pageSize){
        int skip = (pageIndex - 1) * pageSize;
        return zhipinDubboCaseExecuteLogMapper.getExecuteLog(interfaceId,caseId,taskId,serialId, skip, pageSize);
    }

    /**
     * 获取用例日志个数
     * @param interfaceId
     * @param caseId
     * @param taskId
     * @param serialId
     * @return
     */
    public int getCountCaseExecuteLog(Integer interfaceId, Integer caseId, Integer taskId, Integer serialId){
        return zhipinDubboCaseExecuteLogMapper.countCaseExecuteLog(interfaceId,caseId,taskId,serialId);
    }
}
