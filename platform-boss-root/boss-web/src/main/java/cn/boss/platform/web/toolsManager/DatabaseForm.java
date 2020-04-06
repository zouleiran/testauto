package cn.boss.platform.web.toolsManager;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * Created by admin on 2018/4/13.
 */
public class DatabaseForm {


    @NotBlank(message = "数据库环境不能为空！")
    private String env;
    @NotBlank(message = "数据库不能为空！")
    private String db;
    @NotBlank(message = "sql语句不能为空！")
    private String sql;
    @Range(min=1,max=999999,message = "pageSize必须为整数!")
    private Integer pageSize;
    @Range(min=1,max=999999,message = "pageIndex必须为整数!")
    private Integer pageIndex;

    @Override
    public String toString() {
        return "DatabaseForm{" +
                "env='" + env + '\'' +
                ", db='" + db + '\'' +
                ", sql='" + sql + '\'' +
                ", pageSize=" + pageSize +
                ", pageIndex=" + pageIndex +
                '}';
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}
