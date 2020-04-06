package cn.boss.platform.bll.util;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/4/13.
 */
public class ConnectionDB {

    //数据库驱动
    private String dbDriver;
    //数据库链接
    private String dbUrl;
    //连接数据库名和密码
    private String dbUser;
    private String dbPass;

    public ConnectionDB(String DBDriver, String DBUrl, String DBUser, String DBPass){
        this.dbDriver = DBDriver;
        this.dbUrl = DBUrl;
        this.dbUser = DBUser;
        this.dbPass = DBPass;
    }


    /**
     * 连接mysql数据库
     * @return
     */
    private Connection getConnction(){
        //数据库连接
        Connection connection = null;
        try {
            //加载数据库驱动
            Class.forName(dbDriver);
            //通过驱动管理类获取数据库链接,用户名及密码
            connection = DriverManager.getConnection(dbUrl,dbUser,dbPass);
        } catch (SQLException e) {
            throw new RuntimeException("数据库访问出现异常", e);
        }catch (ClassNotFoundException e) {
            throw new RuntimeException("Drive.Class文件出现异常", e);
        }
        return connection;
    }

    /**
     * 查询操作,查询结果生成 List<Map<String,String>>对象
     * @param sql sql语句
     * @return 返回list对象
     */
    public List<Map<String,String>> select(String sql){
        Connection conn = getConnction();//此处为通过自己写的方法getConn()获得连接
        Statement stmt = null;
        ResultSet rs;
        List<Map<String,String>> list= new ArrayList<>();
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                Map<String,String> map= new  LinkedHashMap();
                //列的个数
                ResultSetMetaData rsmd = rs.getMetaData();
                for(int i=1;i<=rsmd.getColumnCount();i++){
                    map.put(rsmd.getColumnName(i), rs.getObject(i)+"");
                }
                list.add(map);
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Drive.Class文件出现异常", e);
        }finally{
            //6.关闭数据库相应的资源
            try {
                conn.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Drive.Class文件出现异常", e);
            }

        }
        return list;
    }

    /**
     * 插入、更新数据
     * @param sql
     */
    public boolean update(String sql) {
        //取操作SQL语句的Statement对象：
        Statement statement = null;
        //数据库链接
        Connection connection = getConnction();
        try {
            statement = connection.createStatement();
            //调用Statement对象的executeUpdate(sql)
            statement.executeUpdate(sql);
            //  	 System.out.println("插入/更新数据成功！！");
        } catch (SQLException e) {
            throw new RuntimeException("Drive.Class文件出现异常", e);
        } catch (Exception e) {
            throw new RuntimeException("执行异常", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
                return true;
            } catch (SQLException e) {
                throw new RuntimeException("关闭异常", e);
            } catch (Exception e) {
                throw new RuntimeException("执行异常", e);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String path = System.getProperty("user.dir");
        String sql = "ALTER TABLE test\n" +
                "ADD Birthday name ";
    }
}
