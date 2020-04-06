package cn.boss.platform.dao.interfaceManager;

import cn.boss.platform.bean.interfaceManager.JenkinsBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JenkinsMapper {
    JenkinsBean getlastjenkins();

    void insertjenkins(JenkinsBean b);


//    List<JenkinsBean> selectJenkins2(JenkinsBean b);

    void setCookie(String cookie);

    List<JenkinsBean> selectJenkins2(@Param("testenv") String testenv,
                                     @Param("service") String service,
                                     @Param("begin") int begin,
                                     @Param("size") int size,
                                     @Param("source") String source);

    List<JenkinsBean> getlastJenkins(@Param("testenv") String testenv,
                                     @Param("source") String source);

    int getlastjenkinsadmin(@Param("testenv") String testenv,@Param("service") String service);

    int getlastjenkinsyufa();

    JenkinsBean getlastjenkinspay();

    JenkinsBean getlastjenkinsdianzhangadmin();

    JenkinsBean getlastjenkinsdianzhangweifuwu();
}
