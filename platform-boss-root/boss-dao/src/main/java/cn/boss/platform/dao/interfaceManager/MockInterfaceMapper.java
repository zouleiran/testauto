package cn.boss.platform.dao.interfaceManager;

import cn.boss.platform.bean.interfaceManager.MockInterfaceBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2019/3/21.
 */
public interface MockInterfaceMapper {

    //添加mock接口
    void addMockInterface(MockInterfaceBean bean);

    //查询mock接口
    List<MockInterfaceBean> getMockInterface(@Param("interfaceId") Integer interfaceId,@Param("id") Integer id);

    //删除mock接口
    void deleteMockInterface(Integer id);

    //更新mock接口
    Integer updateMockInterface(MockInterfaceBean bean);

    //查询mock接口
    List<MockInterfaceBean> getMockInterfaceByUrl(@Param("url") String url);
}

