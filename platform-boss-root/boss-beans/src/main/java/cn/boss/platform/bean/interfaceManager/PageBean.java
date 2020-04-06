package cn.boss.platform.bean.interfaceManager;

/**
 * Created by admin on 2018/8/23.
 */
public class PageBean {

    private int pageIndex;//分页的索引
    private int pageSize;//分页的大小
    private int startSize;//分页起始行

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartSize() {
        return startSize;
    }

    public void setStartSize(int startSize) {
        this.startSize = startSize;
    }

    public void setPage(int pageIndex, int pageSize){
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.startSize = (pageIndex - 1) * pageSize;
    }
}
