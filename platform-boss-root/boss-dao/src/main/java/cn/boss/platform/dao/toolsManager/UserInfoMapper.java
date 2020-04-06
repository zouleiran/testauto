package cn.boss.platform.dao.toolsManager;

import cn.boss.platform.bean.toolsManager.UserInfoBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by admin on 2019/4/10.
 */
public interface UserInfoMapper {

    //添加用户信息
    void addUerInfo(UserInfoBean bean);

    //获取信息
    UserInfoBean getUserInfo(@Param("phone") String phone,@Param("env") String env);

    //获取信息
    UserInfoBean getUserInfoBytype(@Param("phone") String phone,@Param("env") String env,@Param("type") String type);

    //获取所有信息
    List<UserInfoBean> getUserInfos();

    //更新信息
    void updateUserInfo(UserInfoBean bean);


}
