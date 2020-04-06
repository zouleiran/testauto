package cn.boss.platform.bean.autoManager;


import java.util.List;

/**
 * Created by admin on 2019/11/26.
 */
public class AutoProjectGroupBean {

    private Integer id;
    private String label;
    private List<AutoCaseBean> children;

    @Override
    public String toString() {
        return "AutoProjectGroupBean{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
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

    public List<AutoCaseBean> getChildren() {
        return children;
    }

    public void setChildren(List<AutoCaseBean> children) {
        for (int i=0;i<children.size();i++){
            children.get(i).setLabel(children.get(i).getName());
        }
        this.children = children;
    }
}