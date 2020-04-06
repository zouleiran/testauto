package cn.boss.platform.bll.batchCaseManager;

import cn.boss.platform.bean.batchCaseManager.BatchCaseStepBean;
import cn.boss.platform.dao.batchCaseManager.BatchCaseStepMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2018/9/7.
 */
@Component
public class BatchCaseStepBll {

    @Autowired
    private BatchCaseStepMapper batchCaseStepMapper;

    /**
     * 添加用例步骤
     * @param batchCaseStepBean
     */
    public void addBatchCase(BatchCaseStepBean batchCaseStepBean) {
        batchCaseStepMapper.addBatchCaseStep(batchCaseStepBean);
    }

    /**
     * 分页查询用例步骤
     * @param batchId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<BatchCaseStepBean> getBatchStepBycaseId(int batchId, int pageIndex, int pageSize) {
        int skip = (pageIndex - 1) * pageSize;
        return batchCaseStepMapper.getBatchStepBycaseId(batchId, skip, pageSize);
    }

    /**
     * 获取集成执行的步骤
     * @param batchId
     * @return
     */
    public List<BatchCaseStepBean> getBatchStepIntegradeBycaseId(int batchId,int[] batchCaseId) {
        return batchCaseStepMapper.getBatchStepIntegradeBycaseId(batchId,batchCaseId);
    }
    /**
     * 获取单条用例步骤
     * @param id
     * @return
     */
    public BatchCaseStepBean getBatchCaseById(Integer id){
        return batchCaseStepMapper.getBatchCaseById(id);
    }


    /**
     * 删除用例步骤
     * @param id
     */
    public void deleteBatchCaseStep(Integer id){
        batchCaseStepMapper.deleteBatchCaseStep(id);
    }

    /**
     * 更新用例步骤
     * @param batchCaseStepBean
     */
    public void updateBatchCaseStep(BatchCaseStepBean batchCaseStepBean) {
        batchCaseStepMapper.updateBatchCaseStep(batchCaseStepBean);
    }

    /**
     * 切换用例
     * @return
     */
    public boolean switchBatchCase(Integer frontId, Integer afterId){
        try{
            batchCaseStepMapper.switchBatchCase(frontId,afterId);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
