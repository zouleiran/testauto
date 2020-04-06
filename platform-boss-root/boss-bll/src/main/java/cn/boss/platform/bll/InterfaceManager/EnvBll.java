package cn.boss.platform.bll.InterfaceManager;


import cn.boss.platform.bean.interfaceManager.EnvBean;
import cn.boss.platform.dao.interfaceManager.EnvMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2018/6/6.
 */
@Component
public class EnvBll {

    @Autowired
    private EnvMapper envMapper;

    /**
     * 添加环境ip
     * @param envBean
     */
    public void addEnv(EnvBean envBean) {
        envMapper.addEnv(envBean);
    }

    public List<EnvBean> getEnv(int pageIndex, int pageSize) {
        int skip = (pageIndex - 1) * pageSize;
        return envMapper.selectEnv(skip, pageSize);
    }

    /**
     * 删除环境
     * @param id
     */
    public void deleteEnv(Integer id){
        envMapper.deleteEnv(id);
    }


    /**
     * 查询环境
     * @param env
     * @return
     */
    public List<EnvBean> getEnv(EnvBean env){
        return envMapper.getEnv(env);
    }


    /**
     * 通过id获取ip
     * @param id
     * @return
     */
    public String getIpById(Integer id){
        return envMapper.getIpByid(id);
    }

    /**
     * 条件查询环境
     * @param projectId
     * @param env
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public  List<EnvBean> queryEnv(Integer item,Integer projectId,String env,int pageIndex, int pageSize){
        int skip = (pageIndex - 1) * pageSize;
        return envMapper.queryEnv(item,projectId,env,skip, pageSize);
    }

    /**
     * 通过id查询环境
     * @param id
     * @return
     */
    public EnvBean getEnvById(Integer id){
        return envMapper.getEnvById(id);
    }

    /**
     * 更新环境
     * @param envBean
     */
    public void updateEnv(EnvBean envBean){
        envMapper.updateEnv(envBean);
    }

    /**
     * 通过ip获取boean
     * @param ip
     * @return
     */
    public EnvBean getEnvBeanByip(String ip,Integer id,Integer projectId){
        return envMapper.getEnvBeanByip(ip,id,projectId);
    }

}
