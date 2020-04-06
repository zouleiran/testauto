package cn.boss.platform.web.dubboManager.form;

/**
 * Created by admin on 2018/12/18.
 */
public class ZhipinDubboGroupForm<T> {

    private String id;
    private String label;
    private int interfaceId;
    private T children;

    @Override
    public String toString() {
        return "ZhipinDubboGroupForm{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", interfaceId=" + interfaceId +
                ", children=" + children +
                '}';
    }

    public int getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(int interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public T getChildren() {
        return children;
    }

    public void setChildren(T children) {
        this.children = children;
    }
}
