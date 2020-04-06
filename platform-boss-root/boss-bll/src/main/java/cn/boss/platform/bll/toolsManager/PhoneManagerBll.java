package cn.boss.platform.bll.toolsManager;

import cn.boss.platform.bean.toolsManager.PhoneManagerBean;
import cn.boss.platform.dao.toolsManager.PhoneManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2019/9/4.
 */
@Component
public class PhoneManagerBll {

    @Autowired
    PhoneManagerMapper phoneManagerMapper;

    /**
     * 搜索手机设备信息
     * @param keyword
     * @return
     */
    public List<PhoneManagerBean> searchPhoneInfo(String keyword){
        return phoneManagerMapper.searchPhoneInfo(keyword);

    }

    /**
     * 查询单个手机信息
     * @return
     */
    public PhoneManagerBean getPhoneById(Integer id){
        return phoneManagerMapper.getPhoneById(id);
    }

    /**
     * 删除信息
     * @param id
     */
    public void delete(Integer id){
        phoneManagerMapper.deletePhoneInfo(id);
    }

    /**
     * 更新信息
     * @param bean
     * @return
     */
    public boolean update(PhoneManagerBean bean){
        return phoneManagerMapper.updatePhoneInfo(bean) > 0;
    }

    /**
     * 添加信息
     * @param bean
     */
    public void add(PhoneManagerBean bean) {
        phoneManagerMapper.addPhoneInfo(bean);
    }

}