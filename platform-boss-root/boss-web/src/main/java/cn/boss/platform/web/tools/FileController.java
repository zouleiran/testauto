package cn.boss.platform.web.tools;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.toolsManager.fileBean;
import cn.boss.platform.web.api.AbstractBaseController;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by admin on 2018/8/11.
 */
@Controller
@RequestMapping("/boss/file")
public class FileController extends AbstractBaseController {
    private static String contextpath = "/usr/tomcat/apache-tomcat-8.5.37/webapps/file/pro";
//    private static String contextpath = "/Users/zouleiran/Desktop/zlr/zlr/file/1";
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @PostMapping(value = "/add")
    @ResponseBody
    public ResultBean<Object> add(@RequestParam("file") MultipartFile a,@RequestParam("group") String group) throws IOException {
        logger.info("/boss/file/add POST 方法被调用!!");
        try{
            if(!a.isEmpty()){
                //在根目录下创建一个tempfileDir的临时文件夹request.getContextPath()+
//                String contextpath = "/Users/zouleiran/Desktop/platform-boss-root/boss-web/src/main/java/cn/boss/platform/web";
//                String contextpath = "/usr/tomcat/apache-tomcat-8.5.37/webapps/file/pro";
                System.out.println(group);
                File f = new File(contextpath);
                if(!f.exists()){
                    f.mkdirs();
                }
                if(group.length()>0)
                    group=group+'/';
                //在tempfileDir的临时文件夹下创建一个新的和上传文件名一样的文件
                String filename = a.getOriginalFilename();
                String filepath = contextpath+"/"+group+filename;
                File newFile = new File(filepath);
                //将MultipartFile文件转换，即写入File新文件中，返回File文件
                CommonsMultipartFile commonsmultipartfile = (CommonsMultipartFile) a;
                commonsmultipartfile.transferTo(newFile);
            }
            return ResultBean.success("上传成功!");
        } catch (Exception e) {
            logger.error("上传文件异常：{}", e);
            return ResultBean.failed("上传文件异常!");
        }
    }
    @RequestMapping(value = "/deletefile", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> deletefile(@RequestParam("filename") String filename,@RequestParam("group") String group) {
        try {
            delFile(contextpath+"/"+group,filename);
            return ResultBean.success("接口调用成功！");
        }catch (Exception e) {
            logger.error("请求失败：{}", e);
            return ResultBean.failed("请求失败!");
        }
    }
    @RequestMapping(value = "/filelist", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getfile(@RequestParam("group") String group,@RequestParam("pageIndex") String pageIndex,@RequestParam("pageSize") String pageSize) {
        try {
            System.out.println(group);
            System.out.println(pageIndex);
            System.out.println(pageSize);
            List b=new ArrayList();
            readfile(contextpath,b,group);
//            List finalB = b;
            List finalC = new ArrayList();
            for (Object x:b)
            {
//                http://qa.kanzhun-inc.com/file/pro/dianzhang/boss_7.170_beta2.apk
                String y="http://qa.kanzhun-inc.com/file/pro/"+group+"/"+x;
//                Map z=new HashMap();
//                z.put(x,y);
                fileBean file=new fileBean();
                String lujing=contextpath+'/'+group+'/'+x;
                String time=getFileTime(lujing);
                file.setfilename((String) x);
                file.setfiletime(time);
                file.setfileurl(y);
                finalC.add(file);
            }
            Map<String, Object> result = new HashMap<String, Object>() {
                {
                    put("result", finalC);
                }
            };
            return ResultBean.success("接口调用成功！", result);
        }catch (Exception e) {
            logger.error("请求失败：{}", e);
            return ResultBean.failed("请求失败!");
        }
    }
    public String getFileTime(String filepath){
        File f = new File(filepath);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        long time = f.lastModified();
        cal.setTimeInMillis(time);
        return formatter.format(cal.getTime());
    }

    @RequestMapping(value = "/updatefilename", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> updatefilename(@RequestParam("filename") String filename,@RequestParam("oldname") String oldname,@RequestParam("group") String group) {
        try {
            System.out.println(filename);
            System.out.println(oldname);
//            String[] s=oldname.split("/.");
//            filename+"."+s[s.length-1]
            System.out.println(filename+oldname.substring(oldname.lastIndexOf(".")));
            renameFile(contextpath+'/'+group,oldname,filename+oldname.substring(oldname.lastIndexOf(".")));
            return ResultBean.success("接口调用成功！");
        }catch (Exception e) {
            logger.error("请求失败：{}", e);
            return ResultBean.failed("请求失败!");
        }
    }
    public void renameFile(String path,String oldname,String newname){
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile=new File(path+"/"+oldname);
            File newfile=new File(path+"/"+newname);
            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newname+"已经存在！");
            else{
                oldfile.renameTo(newfile);
            }
        }
    }
    public void delFile(String path,String filename){
        File file=new File(path+"/"+filename);
        if(file.exists()&&file.isFile())
            file.delete();
    }
    @Test
    public void test() throws IOException {
        String x="123.1234.png";
//        String[] s=x.indexOf(".");
//        x.lastIndexOf()
        System.out.println(x.substring(x.lastIndexOf("."),x.length()));
//        List a=new ArrayList();
//        readfile("/Users/zouleiran/Desktop/zlr/zlr/file/1",a,"boss");
//        System.out.println(a.size());
    }
    public static void readfile(String filepath,List a,String group) throws FileNotFoundException, IOException {
        try {
            filepath=filepath+"/"+group;
            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                System.out.println("name=" + file.getName());
            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "//" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        System.out.println("path=" + readfile.getPath());
                        System.out.println("name=" + readfile.getName());
                        a.add(readfile.getName());
                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "//" + filelist[i],a,"");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
    }
}