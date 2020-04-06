package cn.boss.platform.dao.interfaceManager;

import cn.boss.platform.bean.interfaceManager.InterfaceBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2018/7/26.
 */
public interface InterfaceMapper {

    //添加接口
    void addInterface(InterfaceBean inter);

    //查询接口
    List<InterfaceBean> getInterface(InterfaceBean inter);

    //查询接口
    InterfaceBean getInterfaceById(Integer id);

    //查询接口总数
    Integer getInterfaceCount(InterfaceBean inter);

    //查询项目下接口总数
    Integer getInterfaceCountByprojectId(@Param("projectId") Integer projectId);

    //查询分组下接口总数
    Integer getInterfaceCountBygroupId(@Param("groupId") Integer groupId);

    //通过项目和组名查询接口
    List<InterfaceBean> getInterfaceByProjectGroupId(@Param("projectId") Integer projectId,@Param("groupId") Integer groupId);

    //删除接口
    void deleteInterface(Integer id);

    //更新接口
    Integer updateInterface(InterfaceBean bean);

    //关键字搜索接口
    List<InterfaceBean>  searchInterface(@Param("projectId") Integer projectId,@Param("keyword") String keyword,@Param("startSize") int startSize,@Param("pageSize") int pageSize);

    //关键字搜索接口总数
    Integer searchInterfaceCount(@Param("projectId") Integer projectId,@Param("keyword") String keyword);

    //查询order_code为空的接口（刚添加接口，order_code都为空）、
    List<InterfaceBean> getInterfaaceByOrderCode();

    //通过用例id查询接口
    List<InterfaceBean> searchInterfaceBycaseId(@Param("caseId") Integer caseId);






}
