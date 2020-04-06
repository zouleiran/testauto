package cn.boss.platform.web.form;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

/**
 * Created by admin on 2018/8/13.
 */
public class ParameterForm {

    private Integer id;
    @Range(min = 1, max = 999999, message = "项目编号必须为整数!")
    private Integer projectId;
//    @Range(min = 1, max = 999999, message = "分组编号必须为整数!")
    private Integer groupId;
//    @Range(min = 1, max = 999999, message = "接口编号必须为整数!")
    private Integer interfaceId;
//    @Range(min = 1, max = 999999, message = "环境编号必须为整数!")
    private Integer envId;

    @NotBlank(message = "参数名不能为空！")
    private String name;
    private String value;
    @NotBlank(message = "是否是全局类型不能为空！")
    private String global;
    @NotBlank(message = "参数类型不能为空！")
    private String type;
    private int caseId;
    private String dbEnv;
    private String dbName;
    private String dbColumn;
    private String dbSql;
    private String message;
    @NotBlank(message = "添加人员不能为空！")
    private String author;

    private int pageIndex;
    private int pageSize;

    @Override
    public String toString() {
        return "ParameterForm{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", groupId=" + groupId +
                ", interfaceId=" + interfaceId +
                ", envId=" + envId +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", global='" + global + '\'' +
                ", type='" + type + '\'' +
                ", caseId=" + caseId +
                ", dbEnv='" + dbEnv + '\'' +
                ", dbName='" + dbName + '\'' +
                ", dbColumn='" + dbColumn + '\'' +
                ", dbSql='" + dbSql + '\'' +
                ", message='" + message + '\'' +
                ", author='" + author + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                '}';
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Integer interfaceId) {
        this.interfaceId = interfaceId;
    }

    public Integer getEnvId() {
        return envId;
    }

    public void setEnvId(Integer envId) {
        this.envId = envId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGlobal() {
        return global;
    }

    public void setGlobal(String global) {
        this.global = global;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getDbEnv() {
        return dbEnv;
    }

    public void setDbEnv(String dbEnv) {
        this.dbEnv = dbEnv;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbColumn() {
        return dbColumn;
    }

    public void setDbColumn(String dbColumn) {
        this.dbColumn = dbColumn;
    }

    public String getDbSql() {
        return dbSql;
    }

    public void setDbSql(String dbSql) {
        this.dbSql = dbSql;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
