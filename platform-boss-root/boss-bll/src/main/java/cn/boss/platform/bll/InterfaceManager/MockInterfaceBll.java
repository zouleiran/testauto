package cn.boss.platform.bll.InterfaceManager;

import cn.boss.platform.bean.interfaceManager.MockInterfaceBean;
import cn.boss.platform.dao.interfaceManager.MockInterfaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2019/3/21.
 */
@Component
public class MockInterfaceBll {

    @Autowired
    private MockInterfaceMapper mockInterfaceMapper;

    /**
     * 添加mock接口
     * @param mockInterfaceBean
     */
    public void addMockInterface(MockInterfaceBean mockInterfaceBean) {
        mockInterfaceMapper.addMockInterface(mockInterfaceBean);
    }

    /**
     * 查询接口下的mock接口
     * @param interfaceId
     * @param id
     * @return
     */
    public List<MockInterfaceBean> getMockInterface(Integer interfaceId,Integer id) {
        return mockInterfaceMapper.getMockInterface(interfaceId,id);
    }

    /**
     * 删除mock接口

     * @param id
     */
    public void deleteMockInterface(Integer id){
        mockInterfaceMapper.deleteMockInterface(id);
    }

    /**
     * 更新mock接口
     * @param bean
     * @return
     */
    public boolean updateMockInterface(MockInterfaceBean bean){
        return mockInterfaceMapper.updateMockInterface(bean) > 0;
    }

    /**
     * 查询mock接口
     * @param url
     * @return
     */
    public List<MockInterfaceBean> getMockInterfaceByUrl(String url){
        return  mockInterfaceMapper.getMockInterfaceByUrl(url);
    }
}
