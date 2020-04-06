package cn.boss.platform.bean.interfaceManager;

/**
 * 参数管理bean
 */

import java.util.Date;
import java.util.Map;

public class ParameterBean extends PageBean{

    private Integer id;
    private Integer projectId;
    private Integer groupId;
    private Integer interfaceId;
    private Integer envId;

    private String name;
    private String value;
    private String global;
    private String type;
    private Integer caseId;
    private String dbEnv;
    private String dbName;
    private String dbColumn;
    private String dbSql;
    private String message;
    private String author;
    //使用该参数/参数来源的用例和接口
    private Map<Integer,Integer> relevantCases;
    private Map<Integer,Integer> sourceCases;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "ParameterBean{" +
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
                ", relevantCases=" + relevantCases +
                ", sourceCases=" + sourceCases +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Map<Integer, Integer> getSourceCases() {
        return sourceCases;
    }

    public void setSourceCases(Map<Integer, Integer> sourceCases) {
        this.sourceCases = sourceCases;
    }

    public Map<Integer, Integer> getRelevantCases() {
        return relevantCases;
    }

    public void setRelevantCases(Map<Integer, Integer> relevantCases) {
        this.relevantCases = relevantCases;
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

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
