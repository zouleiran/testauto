package cn.boss.platform.dao.toolsManager;

import cn.boss.platform.bean.toolsManager.PhoneManagerBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2019/9/4.
 */
public interface PhoneManagerMapper {

    //搜索手机信息
    List<PhoneManagerBean> searchPhoneInfo(@Param("keyword") String keyword);

    //获取手机信息
    PhoneManagerBean getPhoneById(@Param("id") Integer id);

    //添加用户信息
    void addPhoneInfo(PhoneManagerBean bean);

    //更新信息
    Integer updatePhoneInfo(PhoneManagerBean bean);

    //删除信息
    void deletePhoneInfo(Integer id);
}