package cn.boss.platform.bll.dubboManager;

import cn.boss.platform.bean.dubboManager.ZhipinDubboCaseBean;
import cn.boss.platform.dao.dubboManager.ZhipinDubboCaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2018/12/20.
 */
@Component
public class ZhipinDubboCaseBll {

    @Autowired
    private ZhipinDubboCaseMapper zhipinDubboCaseMapper;

    /**
     * 添加dubbo用例
     * @param zhipinDubboCaseBean
     */
    public void addDubboCase(ZhipinDubboCaseBean zhipinDubboCaseBean){

        zhipinDubboCaseMapper.addDubboCase(zhipinDubboCaseBean);
    }

    /**
     * 删除接口用例
     * @param id
     */
    public void deleteDubboCase(Integer id){
        zhipinDubboCaseMapper.deleteDubboCase(id);
    }

    /**
     * 查询接口用例
     * @param interfaceId
     * @param caseId
     * @param envId
     * @return
     */
    public List<ZhipinDubboCaseBean> getDubboCases(Integer interfaceId, Integer caseId, Integer envId){
        return zhipinDubboCaseMapper.getDubboCases(interfaceId,caseId,envId);

    }

    /**
     * 更新dubbo用例
     * @param zhipinDubboCaseBean
     */
    public void updateDubboCase(ZhipinDubboCaseBean zhipinDubboCaseBean){
        zhipinDubboCaseMapper.updateDubboCase(zhipinDubboCaseBean);
    }

    /**
     * 查询需要执行的用例
     * @param interfaceId
     * @param envId
     * @param caseId
     * @param author
     * @return
     */
    public List<ZhipinDubboCaseBean> getExecute(Integer interfaceId,Integer envId,Integer caseId,String author){
        return zhipinDubboCaseMapper.getExecute(interfaceId,envId,caseId,author);
    }

}
