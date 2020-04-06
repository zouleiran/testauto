package cn.boss.platform.bll.toolsManager;

import cn.boss.platform.bean.toolsManager.UserInfoBean;
import cn.boss.platform.dao.toolsManager.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2019/4/10.
 */
@Component
public class UserInfoBll {

    @Autowired
    UserInfoMapper userInfoMapper;


    /**
     * 添加用户基本信息
     * @param bean
     */
    public void addUerInfo(UserInfoBean bean){
        userInfoMapper.addUerInfo(bean);

    }

    /**
     * 获取用户信息
     * @param phone
     * @param env
     * @return
     */
    public UserInfoBean getUserInfo(String phone,String env){
         return userInfoMapper.getUserInfo(phone,env);
    }

    /**
     * 获取用户信息
     * @param phone
     * @param env
     * @return
     */
    public UserInfoBean getUserInfoBytype(String phone,String env,String type){
        return userInfoMapper.getUserInfoBytype(phone,env,type);
    }

    /**
     * 获取所有信息
     * @return
     */
    public List<UserInfoBean> getUserInfo(){
        return userInfoMapper.getUserInfos();
    }




    /**
     * 更新用户基本信息
     * @param bean
     */
    public void updateUserInfo(UserInfoBean bean){
        userInfoMapper.updateUserInfo(bean);
    }
}
