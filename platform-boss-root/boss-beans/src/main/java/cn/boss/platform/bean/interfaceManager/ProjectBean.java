package cn.boss.platform.bean.interfaceManager;

import java.util.Date;

/**
 * Created by caosenquan on 2018/7/23.
 */
public class ProjectBean {

    private Integer id;
    private Integer projectTeam;
    private String projectName;
    private String projectType;
    private String projectDesc;
    private String projectVersion;
    private Integer signType;
    private Integer used;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "ProjectBean{" +
                "id=" + id +
                ", projectTeam=" + projectTeam +
                ", projectName='" + projectName + '\'' +
                ", projectType='" + projectType + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                ", projectVersion='" + projectVersion + '\'' +
                ", signType=" + signType +
                ", used=" + used +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
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
