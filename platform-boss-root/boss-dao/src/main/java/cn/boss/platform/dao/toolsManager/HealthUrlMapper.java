package cn.boss.platform.dao.toolsManager;

import cn.boss.platform.bean.toolsManager.HealthUrlBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2019/2/26.
 */
public interface HealthUrlMapper {

    //添加日志
    void addLog(HealthUrlBean healthUrlBean);

    //获取日志
    List<HealthUrlBean> getLog(@Param("env") String env, @Param("zhipinName") String zhipinName,@Param("skip") int skip, @Param("take") int take);


}
