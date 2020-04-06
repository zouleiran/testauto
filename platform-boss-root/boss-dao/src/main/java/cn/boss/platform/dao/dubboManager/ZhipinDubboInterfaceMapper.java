package cn.boss.platform.dao.dubboManager;


import cn.boss.platform.bean.dubboManager.ZhipinDubboInterfaceBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2018/12/18.
 */
public interface ZhipinDubboInterfaceMapper {

    //分组查询服务名
    List<ZhipinDubboInterfaceBean> getServerName();

    //分组查询服务接口
    List<ZhipinDubboInterfaceBean> getServerInterface(@Param("serverName") String serverName);

    //分组查询服务接口
    List<ZhipinDubboInterfaceBean> getServerMethod(@Param("interfaceName") String interfaceName);

    //获取dubbo接口
    List<ZhipinDubboInterfaceBean> getDubboInterface(ZhipinDubboInterfaceBean bean);

    //获取dubbo接口个数
    int getDubboInterfaceCount(ZhipinDubboInterfaceBean bean);

    //查询单条接口
    ZhipinDubboInterfaceBean getDubboInterfaceById(@Param("id") int id);

    //添加接口
    void addDubboInterface(ZhipinDubboInterfaceBean inter);

    //更新接口
    Integer updateDubboInterface(ZhipinDubboInterfaceBean inter);

    //删除接口
    void deleteInterface(@Param("id") Integer id);

}
