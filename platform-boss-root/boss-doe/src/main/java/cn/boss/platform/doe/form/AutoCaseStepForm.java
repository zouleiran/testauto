package cn.boss.platform.doe.form;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * Created by admin on 2019/11/18.
 */
public class AutoCaseStepForm {

    private Integer id;
    private Integer index;
    @Range(min=1,max=999999,message = "项目编号 必须为整数!")
    private Integer projectId;
    @Range(min=1,max=999999,message = "分组编号 必须为整数!")
    private Integer groupId;
    @Range(min=1,max=999999,message = "用例编号 必须为整数!")
    private Integer caseId;
    @NotBlank(message = "用例步骤 不能为空！")
    private String name;
    private String action;
    private String exception;
    private String parameter;
    private String value;
    private String message;


    @Override
    public String toString() {
        return "AutoCaseStepForm{" +
                "id=" + id +
                ", index=" + index +
                ", projectId=" + projectId +
                ", groupId=" + groupId +
                ", caseId=" + caseId +
                ", name='" + name + '\'' +
                ", action='" + action + '\'' +
                ", exception='" + exception + '\'' +
                ", parameter='" + parameter + '\'' +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
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

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}