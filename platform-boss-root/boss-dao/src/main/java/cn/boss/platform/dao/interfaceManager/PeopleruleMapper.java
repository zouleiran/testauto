package cn.boss.platform.dao.interfaceManager;

import cn.boss.platform.bean.interfaceManager.PeopleruleBean;
import cn.boss.platform.bean.interfaceManager.RuleBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PeopleruleMapper {
    List<PeopleruleBean> querypeoplerule(@Param("id") Integer id,
                                         @Param("email") String email,
                                         @Param("username") String username,
                                         @Param("startSize") int startSize, @Param("pageSize") int pageSize);

    void deletepeoplerule(Integer id);

    void addpeoplerule(PeopleruleBean a);

    String searchrulebypeopleid(Integer id);

    List<RuleBean> searchrule();

    void updatepeoplerule(PeopleruleBean PeopleruleBean);

    String searchrulebyemail(String email);

}
