package cn.boss.platform.dao.toolsManager;

import org.apache.ibatis.annotations.Param;

/**
 * Created by admin on 2019/2/26.
 */
public interface ChangestatusMapper {

    void changeboss(@Param("bossid") String bossid, @Param("status") String status);
    void changegeek(@Param("geekid") String geekid, @Param("status") String status);
}
