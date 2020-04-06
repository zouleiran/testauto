package cn.boss.platform.bll.InterfaceManager;

import cn.boss.platform.bean.interfaceManager.ParameterBean;
import cn.boss.platform.dao.interfaceManager.ParameterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2018/8/13.
 */
@Component
public class ParameterBll {

    @Autowired
    private ParameterMapper parameterMapper;

    /**
     * 添加参数，参数名已存在时添加失败
     * @param parameterBean
     * @return
     */
    public boolean addParameter(ParameterBean parameterBean) {
        if(parameterMapper.selectByName(parameterBean.getName()) !=null){
            return false;
        }else{
            parameterMapper.addParameter(parameterBean);
            return true;
        }
    }

    /**
     * 通过名查询参数
     * @param name
     * @return
     */
    public ParameterBean selectByName(String name) {
        return parameterMapper.selectByName(name);
    }
    /**
     * 查询参数
     * @param parameterBean
     * @return
     */
    public List<ParameterBean> getParameter(ParameterBean parameterBean){
        parameterBean.setPage(parameterBean.getPageIndex(),parameterBean.getPageSize());
        return parameterMapper.getParameter(parameterBean);
    }

    public Integer getParameterCount(ParameterBean parameterBean){
        parameterBean.setPage(parameterBean.getPageIndex(),parameterBean.getPageSize());
        return parameterMapper.getParameterCount(parameterBean);
    }

    /**
     * 更新参数
     * @param bean
     * @return
     */
    public boolean updateParameter(ParameterBean bean){
        return parameterMapper.updateParameter(bean) > 0;
    }

    /**
     * 删除参数
     * @param id
     */
    public boolean deleteParameter(Integer id) {
        ParameterBean paramBean = parameterMapper.selectParameterByid(id);
        if (parameterMapper.isInUse(paramBean.getName())) {
            return false;
        }
        parameterMapper.deleteParameter(id);
        return true;
    }



}
