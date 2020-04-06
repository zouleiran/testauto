package cn.boss.platform.bll.dubboManager;


import cn.boss.platform.bean.dubboManager.ZhipinEnvBean;
import cn.boss.platform.dao.dubboManager.ZhipinEnvMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2018/12/10.
 */
@Component
public class ZhipinEnvBll {

    @Autowired
    private ZhipinEnvMapper zhipinEnvMapper;


    /**
     * 通过名和域名查询环境
     * @param zhipinName
     * @param env
     * @return
     */
    public List<ZhipinEnvBean> getZhipinEnv(String zhipinName, String env) {

        return zhipinEnvMapper.getZhipinEnv(zhipinName,env);
    }


    /**
     * 通过id查询环境
     * @param id
     * @return
     */
    public ZhipinEnvBean getZhipinEnvById(Integer id){
        return zhipinEnvMapper.getZhipinEnvById(id);
    }
}
