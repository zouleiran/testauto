package cn.boss.platform.dao.toolsManager;

import cn.boss.platform.bean.toolsManager.AppInterfaceLogBean;
import com.sun.imageio.plugins.common.I18N;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2019/12/19.
 */
public interface AppInterfaceLogMapper {

    //添加接口日志
    void addInterfaceLog(AppInterfaceLogBean appInterfaceLogBean);

    //获取接口日志
    List<AppInterfaceLogBean> getInterfaceLog(@Param("userId") Integer userId, @Param("phone") String phone, @Param("skip") int skip, @Param("take") int take);

    //获取接口最新日志
    List<AppInterfaceLogBean> getInterfaceLogNew(@Param("userId") List<Object> userId, @Param("phone") String phone, @Param("limit") Integer limit);

    //获取接口日志个数通过多个用户
    int getInterfaceLogCount(@Param("userId") List<Object> userId, @Param("phone") String phone);

    //删除用户日志
    void deleteInterfaceLog(@Param("userId") Integer userId);

    //获取错误日志
    List<AppInterfaceLogBean> getErrorInterfaceLog(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("env") String env,
                                                   @Param("skip") int skip, @Param("take") int take);
}