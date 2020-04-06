package cn.boss.platform.web.form;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

/**
 * Created by admin on 2018/9/6.
 */
public class BatchCaseForm {

    private Integer id;
    @Range(min=1,max=999999,message = "项目编号 必须为整数!")
    private Integer projectId;
    @Range(min=1,max=999999,message = "分组编号 必须为整数!")
    private Integer groupId;
    @Range(min=1,max=999999,message = "环境编号 必须为整数!")
    private Integer envId;

    @NotBlank(message = "用例名 不能为空！")
    private String name;
    private String message;
    @NotBlank(message = "添加人员 不能为空！")
    private String author;
//    private Date createTime;
//    private Date updateTime;

    @Override
    public String toString() {
        return "BatchCaseForm{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", groupId=" + groupId +
                ", envId=" + envId +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
