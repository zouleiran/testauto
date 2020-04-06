package cn.boss.platform.dao.dubboManager;


import cn.boss.platform.bean.dubboManager.ZhipinEnvBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2018/12/10.
 */
public interface ZhipinEnvMapper {

    //查询服务化环境地址
    List<ZhipinEnvBean> getZhipinEnv(@Param("zhipinName") String zhipinName, @Param("env") String env);

    //通过id查询环境地址
    ZhipinEnvBean getZhipinEnvById(@Param("id") Integer id);
}
