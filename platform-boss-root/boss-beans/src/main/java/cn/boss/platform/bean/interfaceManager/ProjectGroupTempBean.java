package cn.boss.platform.bean.interfaceManager;

import java.util.List;

/**
 * Created by admin on 2018/7/25.
 */
public class ProjectGroupTempBean {

    private Integer id;
    private String label;
    private List<InterfaceBean> children;

    @Override
    public String toString() {
        return "ProjectGroupTempBean{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }

    public List<InterfaceBean> getChildren() {
        return children;
    }

    public void setChildren(List<InterfaceBean> children) {
        for (int i=0;i<children.size();i++){
            children.get(i).setLabel(children.get(i).getName());
            //参数公共参数详情数据太大，删除不需要
            children.get(i).setParams("");
            children.get(i).setCommonParams("");
            children.get(i).setResponse("");
        }
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
