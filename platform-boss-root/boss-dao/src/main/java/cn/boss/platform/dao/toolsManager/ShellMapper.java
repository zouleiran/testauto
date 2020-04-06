package cn.boss.platform.dao.toolsManager;

import cn.boss.platform.bean.interfaceManager.ShellBean;
import org.apache.ibatis.annotations.Param;

/**
 * Created by admin on 2019/2/26.
 */
public interface ShellMapper {

    //添加日志
    ShellBean getshell(@Param("servername") String servername,@Param("env") String env);

    //获取日志


}
