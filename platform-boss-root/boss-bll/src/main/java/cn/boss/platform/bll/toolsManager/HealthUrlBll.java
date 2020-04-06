package cn.boss.platform.bll.toolsManager;

import cn.boss.platform.bean.toolsManager.HealthUrlBean;
import cn.boss.platform.dao.toolsManager.HealthUrlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2019/2/26.
 */
@Component
public class HealthUrlBll {

    @Autowired
    HealthUrlMapper healthUrlMapper;

    /**
     *添加用例执行日志
     * @param bean
     */
    public void addLog(HealthUrlBean bean){
        healthUrlMapper.addLog(bean);
    }

    /**
     * 分页查询日志
     * @param env
     * @param zhipinName
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<HealthUrlBean> getLog(String env,String zhipinName, int pageIndex, int pageSize) {
        int skip = (pageIndex - 1) * pageSize;
        return healthUrlMapper.getLog(env,zhipinName, skip, pageSize);
    }
}
