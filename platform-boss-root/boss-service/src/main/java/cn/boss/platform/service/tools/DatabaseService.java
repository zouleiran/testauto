package cn.boss.platform.service.tools;

import cn.boss.platform.bll.util.ConnectionDB;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2018/4/13.
 */
public class DatabaseService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    //数据库驱动
    private String dbDriver;
    //数据库链接
    private String dbUrl;
    //连接数据库名和密码
    private String dbUser;
    private String dbPass;


    public DatabaseService(String env, String db) {
        this.dbDriver = DataField.jdbc_driver;
        if(env.equals("qa")){
            this.dbUrl = DataField.qa_url;
        }else if(env.equals("qa2")){
            this.dbUrl = DataField.qa2_url;
        }else if(env.equals("qa3")){
            this.dbUrl = DataField.qa3_url;
        }else if(env.equals("线下测试")){
            this.dbUrl = DataField.test_url;
        }
        this.dbUser = DataField.username;
        this.dbPass = DataField.password;
        if(!StringUtils.isEmpty(dbUrl)){
            Pattern pattern = Pattern.compile("\\$\\{.+?\\}");
            Matcher matcher = pattern.matcher(dbUrl);
            while (matcher.find()) {
                String group = matcher.group(0);
                this.dbUrl = dbUrl.replace(group, db);
            }
        }

    }


    public List<Map<String,String>> select(String sql,Integer pageIndex,Integer pageSize){
        try{
            int skip = (pageIndex - 1) * pageSize;
            ConnectionDB db = new ConnectionDB(this.dbDriver,this.dbUrl,this.dbUser,this.dbPass);
            String SQL = sql + " limit " + skip +"," +pageSize;
            System.out.println(SQL);
            return db.select(SQL);
        }catch (Exception e){
            logger.error("执行查询数据异常,Exception:{}", e);
            return null;
        }

    }

    public boolean update(String sql){
        try{
            ConnectionDB db = new ConnectionDB(this.dbDriver,this.dbUrl,this.dbUser,this.dbPass);
            return db.update(sql);
        }catch (Exception e){
            logger.error("执行更新数据异常,Exception:{}", e);
            return false;
        }

    }

    public static void main(String[] args) {
        DatabaseService test = new DatabaseService("qa","boss");
        String sqlTwo = "select f.* from should_audit_factory e , (select d.* from (select b.* from brand a,brand_com_relation b where b.brand_id = a.id and a.certificate = 1 and b.count>0) c ,company_full_info d where c.com_id = d.id GROUP BY d.id) f where e.target_id = f.id and f.status = 1 and f.certification = 1 " ;
        List<Map<String,String>> result = test.select(sqlTwo,1,15);
        System.out.println(result);




    }
}
