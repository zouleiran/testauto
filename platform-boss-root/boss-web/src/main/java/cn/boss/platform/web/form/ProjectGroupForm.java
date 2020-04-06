package cn.boss.platform.web.form;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

/**
 * Created by caosenquan on 2018/7/24.
 */
public class ProjectGroupForm {

    private Integer id;
    @Range(min=1,max=999999,message = "项目编号必须为整数!")
    private Integer projectId;
    @NotBlank(message = "组名不能为空！")
    private String groupName;
    private Integer type;
    private String groupDesc;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "ProjectGroupForm{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", groupName='" + groupName + '\'' +
                ", type=" + type +
                ", groupDesc='" + groupDesc + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
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
