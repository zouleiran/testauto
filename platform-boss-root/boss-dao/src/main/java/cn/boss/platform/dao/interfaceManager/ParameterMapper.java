package cn.boss.platform.dao.interfaceManager;

import cn.boss.platform.bean.interfaceManager.ParameterBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2018/8/13.
 */
public interface ParameterMapper {

    //添加参数
    void addParameter(ParameterBean paramBean);

    //查询参数名是否存在
    ParameterBean selectByName(@Param("name") String name);

    //查询参数
    List<ParameterBean> getParameter(ParameterBean parameter);

    //查询参数个数
    Integer getParameterCount(ParameterBean parameter);

    //通过id查询参数
    ParameterBean selectParameterByid(@Param("id") Integer id);

    //删除参数
    void deleteParameter(@Param("id") Integer id);

    //更新参数
    Integer updateParameter(ParameterBean bean);

    //参数是否被使用
    boolean isInUse(@Param("parameterName") String parameterName);





}
