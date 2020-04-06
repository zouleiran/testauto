package cn.boss.platform.web.form;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Created by caosenquan on 2017/12/12.
 */
public class ProjectForm {

    private Integer id;
    private Integer projectTeam;
    @NotBlank(message = "项目不能为空！")
    private String projectName;
    private String projectType;
    private String projectDesc;
    private String projectVersion;
    private Integer used;
    private Integer signType;
    private Date updateTime;
    private Date create_time;

    @Override
    public String toString() {
        return "ProjectForm{" +
                "id=" + id +
                ", projectTeam=" + projectTeam +
                ", projectName='" + projectName + '\'' +
                ", projectType='" + projectType + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                ", projectVersion='" + projectVersion + '\'' +
                ", used=" + used +
                ", signType=" + signType +
                ", updateTime=" + updateTime +
                ", create_time=" + create_time +
                '}';
    }

    public Integer getSignType() {
        return signType;
    }

    public void setSignType(Integer signType) {
        this.signType = signType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(Integer projectTeam) {
        this.projectTeam = projectTeam;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
