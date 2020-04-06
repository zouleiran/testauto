package cn.boss.platform.bean.toolsManager;

public class fileBean {
    String filename;
    String fileurl;
    String filetime;
        @Override
        public String toString() {
            return "fileBean{" +
                    "filename=" + filename +
                    ", fileurl=" + fileurl +
                    '}';
        }
    public String getfiletime() {
        return filetime;
    }

    public void setfiletime(String filetime) {
        this.filetime = filetime;
    }
        public String getfilename() {
            return filename;
        }

        public void setfilename(String filename) {
            this.filename = filename;
        }

        public String getfileurl() {
            return fileurl;
        }

        public void setfileurl(String fileurl) {
            this.fileurl = fileurl;
        }
    }
