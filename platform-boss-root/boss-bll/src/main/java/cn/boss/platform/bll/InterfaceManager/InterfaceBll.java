package cn.boss.platform.bll.InterfaceManager;

import cn.boss.platform.bean.interfaceManager.InterfaceBean;
import cn.boss.platform.dao.interfaceManager.InterfaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admin on 2018/7/26.
 */
@Component
public class InterfaceBll {

    @Autowired
    private InterfaceMapper interfaceMapper;

    /**
     * 添加接口
     * @param interfaceBean
     */
    public void addInterface(InterfaceBean interfaceBean) {
        interfaceMapper.addInterface(interfaceBean);
    }

    /**
     * 查询接口
     * @param inter
     * @return
     */
    public List<InterfaceBean> getInterface(InterfaceBean inter){
        inter.setPage(inter.getPageIndex(),inter.getPageSize());
        return interfaceMapper.getInterface(inter);
    }

    /**
     * 查询单条接口
     * @param id
     * @return
     */
    public InterfaceBean getInterfaceById(Integer id){
        return interfaceMapper.getInterfaceById(id);
    }

    /**
     * 查询接口总数
     * @param inter
     * @return
     */
    public Integer getInterfaceCount(InterfaceBean inter){
        return interfaceMapper.getInterfaceCount(inter);
    }

    /**
     * 查询项目下接口总数
     * @param projectId
     * @return
     */
    public Integer getInterfaceCountByprojectId(Integer projectId){
        return interfaceMapper.getInterfaceCountByprojectId(projectId);
    }

    /**
     * 查询分组下接口总数
     * @param groupId
     * @return
     */
    public Integer getInterfaceCountBygroupId(Integer groupId){
        return interfaceMapper.getInterfaceCountBygroupId(groupId);
    }


    /**
     * 通过项目id和组id查询接口
     * @param projectId
     * @param groupId
     * @return
     */
    public List<InterfaceBean> getInterface(Integer projectId, Integer groupId){
        return interfaceMapper.getInterfaceByProjectGroupId(projectId, groupId);
    }

    /**
     * 删除接口
     * @param id
     */
    public void delete(Integer id){
        interfaceMapper.deleteInterface(id);
    }

    /**
     * 更新接口
     * @param bean
     * @return
     */
    public boolean update(InterfaceBean bean){
        return interfaceMapper.updateInterface(bean) > 0;
    }

    /**
     * 关键字搜索接口
     * @param groupId
     * @param keyword
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<InterfaceBean> searchInterface(Integer groupId,String keyword,int pageIndex, int pageSize){
        int skip = (pageIndex - 1) * pageSize;
        return interfaceMapper.searchInterface(groupId,keyword,skip, pageSize);
    }

    /**
     * 关键字搜索接口总数
     * @param groupId
     * @param keyword
     * @return
     */
    public Integer searchInterfaceCount(Integer groupId,String keyword){
        return interfaceMapper.searchInterfaceCount(groupId,keyword);
    }

    /**
     * 获取order_code为空的接口
     * @return
     */
    public List<InterfaceBean> getInterfaaceByOrderCode(){
        return interfaceMapper.getInterfaaceByOrderCode();
    }

    /**
     * 通过用例id查询接口
     * @param caseId
     * @return
     */
    public List<InterfaceBean> searchInterfaceBycaseId(Integer caseId){
        return interfaceMapper.searchInterfaceBycaseId(caseId);

    }
}
