package cn.boss.platform.dao.dubboManager;

import cn.boss.platform.bean.dubboManager.ZhipinDubboCaseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2018/12/20.
 */
public interface ZhipinDubboCaseMapper {

    //添加dubbo用例
    void addDubboCase(ZhipinDubboCaseBean zhipinDubboCaseBean);

    //删除dubbo用例
    void deleteDubboCase(Integer id);

    //查询dubbo用例
    List<ZhipinDubboCaseBean> getDubboCases(@Param("interfaceId") Integer interfaceId,@Param("caseId") Integer caseId,@Param("envId") Integer envId);


    //更新dubbo用例
    void updateDubboCase(ZhipinDubboCaseBean zhipinDubboCaseBean);

    //需要执行的用例
    List<ZhipinDubboCaseBean> getExecute(@Param("interfaceId") Integer interfaceId,@Param("envId") Integer envId,@Param("caseId") Integer caseId,@Param("author") String author);
}
