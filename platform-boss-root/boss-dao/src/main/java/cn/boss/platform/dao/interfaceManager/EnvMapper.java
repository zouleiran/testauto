package cn.boss.platform.dao.interfaceManager;

import cn.boss.platform.bean.interfaceManager.EnvBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2018/6/6.
 */
public interface EnvMapper {


    //添加环境
    void addEnv(EnvBean env);

    //删除环境
    void deleteEnv(Integer id);

    //通过ip获取id
    String getIpByid(@Param("id") int id);

    //分页查询环境
    List<EnvBean> selectEnv(@Param("startSize") int startSize, @Param("pageSize") int pageSize);

    //分页查询环境
    List<EnvBean> queryEnv(@Param("item") Integer item,@Param("projectId") Integer projectId,@Param("env") String env,@Param("startSize") int startSize, @Param("pageSize") int pageSize);

    //条件查询环境
    public List<EnvBean> getEnv(EnvBean env);

    //查询环境
    public EnvBean getEnvById(Integer id);

    //更新环境
    void updateEnv(EnvBean env);

    //通过ip获取bean
    EnvBean getEnvBeanByip(@Param("ip") String ip,@Param("id") Integer id,@Param("projectId") Integer projectId);


}
