package cn.boss.platform.bll.dubboManager;

import cn.boss.platform.bean.dubboManager.ZhipinDubboInterfaceBean;
import cn.boss.platform.dao.dubboManager.ZhipinDubboInterfaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2018/12/18.
 */
@Component
public class ZhipinDubboInterfaceBll {

    @Autowired
    private ZhipinDubboInterfaceMapper zhipinDubboInterfaceMapper;


    /**
     * 查询服务名
     * @return
     */
    public List<ZhipinDubboInterfaceBean> getServerName(){
        return zhipinDubboInterfaceMapper.getServerName();
    }

    /**
     * f查询接口名
     * @param serverName
     * @return
     */
    public List<ZhipinDubboInterfaceBean> getServerInterface(String serverName){
        return zhipinDubboInterfaceMapper.getServerInterface(serverName);
    }

    /**
     * 查询方法名
     * @param interfaceName
     * @return
     */
    public List<ZhipinDubboInterfaceBean> getServerMethod(String interfaceName){
        return zhipinDubboInterfaceMapper.getServerMethod(interfaceName);
    }

    /**
     * 查询dubbo接口
     * @param zhipinDubboInterfaceBean
     * @return
     */
    public List<ZhipinDubboInterfaceBean> getDubboInterface(ZhipinDubboInterfaceBean zhipinDubboInterfaceBean){
        return zhipinDubboInterfaceMapper.getDubboInterface(zhipinDubboInterfaceBean);
    }

    /**
     * 查询dubbo接口个数
     * @param zhipinDubboInterfaceBean
     * @return
     */
    public int getDubboInterfaceCount(ZhipinDubboInterfaceBean zhipinDubboInterfaceBean){
        return zhipinDubboInterfaceMapper.getDubboInterfaceCount(zhipinDubboInterfaceBean);
    }

    /**
     * 查询单条接口
     * @param id
     * @return
     */
    public ZhipinDubboInterfaceBean getDubboInterfaceById(int id){
        return zhipinDubboInterfaceMapper.getDubboInterfaceById(id);
    }

    /**
     * 添加接口
     * @param zhipinDubboInterfaceBean
     */
    public void addDubboInterface(ZhipinDubboInterfaceBean zhipinDubboInterfaceBean){
        zhipinDubboInterfaceMapper.addDubboInterface(zhipinDubboInterfaceBean);
    }

    /**
     * 更新接口
     * @param zhipinDubboInterfaceBean
     */
    public boolean updateDubboInterface(ZhipinDubboInterfaceBean zhipinDubboInterfaceBean){
        return zhipinDubboInterfaceMapper.updateDubboInterface(zhipinDubboInterfaceBean) > 0;
    }

    /**
     * 删除接口
     * @param id
     */
    public void deleteInterface(Integer id){
        zhipinDubboInterfaceMapper.deleteInterface(id);
    }


}
