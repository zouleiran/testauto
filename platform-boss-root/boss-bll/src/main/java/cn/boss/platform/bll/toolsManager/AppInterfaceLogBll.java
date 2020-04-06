package cn.boss.platform.bll.toolsManager;

import cn.boss.platform.bean.toolsManager.AppInterfaceLogBean;
import cn.boss.platform.dao.toolsManager.AppInterfaceLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2019/12/19.
 */
@Component
public class AppInterfaceLogBll {

    @Autowired
    AppInterfaceLogMapper appInterfaceLogMapper;

    /**
     *添加用例执行日志
     * @param bean
     */
    public void addInterfaceLog(AppInterfaceLogBean bean){
        appInterfaceLogMapper.addInterfaceLog(bean);
    }

    /**
     * 分页查询日志
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<AppInterfaceLogBean> getLog(Integer userId, String phone, int pageIndex, int pageSize) {
        int skip = (pageIndex - 1) * pageSize;
        return appInterfaceLogMapper.getInterfaceLog(userId,phone,skip,pageSize);
    }

    /**
     * 分页查询日志-最新几条
     * @return
     */
    public  List<AppInterfaceLogBean> getLogNew(List<Object> userId, String phone,Integer limit) {
        return appInterfaceLogMapper.getInterfaceLogNew(userId,phone,limit);
    }

    /**
     * 删除日志
     * @param userId
     */
    public void deleteInterfaceLog(Integer userId){
        appInterfaceLogMapper.deleteInterfaceLog(userId);
    }


    /**
     * 查询日志个数
     * @param userId
     * @return
     */
    public int getInterfaceLogCountByUserIds(List<Object> userId,String phone){
        return appInterfaceLogMapper.getInterfaceLogCount(userId,phone);
    }

    /**
     * 获取错误日志
     * @return
     */
    public List<AppInterfaceLogBean> getErrorInterfaceLog(String startTime,String endTime,String env,int pageIndex, int pageSize){
        int skip = (pageIndex - 1) * pageSize;
        return appInterfaceLogMapper.getErrorInterfaceLog(startTime,endTime,env,skip, pageSize);
    }
}
