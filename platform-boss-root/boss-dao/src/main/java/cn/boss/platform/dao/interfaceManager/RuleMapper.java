package cn.boss.platform.dao.interfaceManager;

import cn.boss.platform.bean.interfaceManager.RuleBean;
import cn.boss.platform.bean.interfaceManager.WebBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RuleMapper {
    //分页查询环境
    List<RuleBean> queryrule(@Param("id") Integer id,@Param("ruledes") String ruledes,  @Param("startSize") int startSize, @Param("pageSize") int pageSize);

    void deleterule(Integer id);

    void addrule(RuleBean a);

    List<WebBean> searchweb();

    String searchwebbyid(Integer id);
    String searchweburlbywebid(int id);
    void update(@Param("id") int id,@Param("ruleid")  String ruleid);
}
