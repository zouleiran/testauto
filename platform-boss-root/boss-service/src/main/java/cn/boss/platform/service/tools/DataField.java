package cn.boss.platform.service.tools;

/**
 * Created by admin on 2018/4/13.
 */
public class DataField {

    public static final String jdbc_driver="com.mysql.jdbc.Driver";
    //qa环境
    public static final String qa_url="jdbc:mysql://192.168.1.26:3306/${database}?useUnicode=true&amp;characterEncoding=utf-8";

    //qa2环境
    public static final String qa2_url="jdbc:mysql://192.168.1.159:3306/${database}?useUnicode=true&amp;characterEncoding=utf-8";

    //qa3环境
    public static final String qa3_url="jdbc:mysql://192.168.1.174:3306/${database}?useUnicode=true&amp;characterEncoding=utf-8";

    //线下测试
    public static final String test_url="jdbc:mysql://192.168.1.31:3306/${database}?useUnicode=true&amp;characterEncoding=utf-8";

    //jira环境
    public static final String jira_url="jdbc:mysql://192.168.1.242:3306/jira?useUnicode=true&amp;characterEncoding=utf-8";

    //TWL平台环境
    public static final String twl_url= "jdbc:mysql://192.168.1.26:3306/platform_db?useUnicode=true&amp;characterEncoding=utf-8";


    public static final String jiraUsername="bosstester";

    public static final String jiraPassword="boss@jPeG";

    public static final String username="boss";

    public static final String password="boss!@#boss";



}
