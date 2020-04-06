package cn.boss.platform.bean.batchCaseManager;

import java.util.List;

/**
 * Created by admin on 2018/9/11.
 */
public class BatchProjectGroupTempBean {

    private Integer id;
    private String label;
    private List<BatchCaseBean> children;

    @Override
    public String toString() {
        return "BatchProjectGroupTempBean{" +
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


    public List<BatchCaseBean> getChildren() {
        return children;
    }

    public void setChildren(List<BatchCaseBean> children) {
        for (int i=0;i<children.size();i++){
            children.get(i).setLabel(children.get(i).getName());
        }
        this.children = children;
    }

}
