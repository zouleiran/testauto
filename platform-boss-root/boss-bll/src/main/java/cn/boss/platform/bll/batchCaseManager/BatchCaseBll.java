package cn.boss.platform.bll.batchCaseManager;

import cn.boss.platform.bean.batchCaseManager.BatchCaseBean;
import cn.boss.platform.dao.batchCaseManager.BatchCaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2018/9/6.
 */
@Component
public class BatchCaseBll {

    @Autowired
    private BatchCaseMapper batchCaseMapper;

    /**
     * 添加用例
     * @param batchCaseBean
     */
    public void addBatchCase(BatchCaseBean batchCaseBean) {
        batchCaseMapper.addBatchCase(batchCaseBean);
    }

    /**
     * 查询用例
     * @param projectId
     * @param groupId
     * @param envId
     * @param id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<BatchCaseBean> getBatchCase(Integer projectId, Integer groupId, Integer envId, Integer id,int pageIndex, int pageSize){
        int skip = (pageIndex - 1) * pageSize;
        return batchCaseMapper.getBatchCase(projectId, groupId,envId,id,skip,pageSize);
    }

    /**
     * 查询用例
     * @param groupId
     * @return
     */
    public List<BatchCaseBean> getBatchCaseBygroupId(Integer groupId){
        return batchCaseMapper.getBatchCaseBygroupId(groupId);
    }

    /**
     * 删除用例
     * @param id
     */
    public void deleteBatchCase(Integer id){
        batchCaseMapper.deleteBatchCase(id);
    }


    /**
     * 查询批量执行用例
     * @return
     */
    public List<BatchCaseBean> getBatchCaseIntegrade(Integer projectId, String groupId, Integer envId, Integer batchId){
        return batchCaseMapper.getBatchCaseIntegrade(projectId, groupId,envId,batchId);
    }

}
