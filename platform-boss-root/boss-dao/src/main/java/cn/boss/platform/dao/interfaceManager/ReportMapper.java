package cn.boss.platform.dao.interfaceManager;

import cn.boss.platform.bean.interfaceManager.ReportBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by Asen on 2018/11/29.
 */
public interface ReportMapper {

    List<ReportBean> selectReportBySerialId(@Param("serialId") String serialId);

}
