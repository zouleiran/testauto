package cn.boss.platform.web.toolsManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.toolsManager.PhoneManagerBean;
import cn.boss.platform.bll.toolsManager.PhoneManagerBll;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.form.PhoneManagerForm;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by admin on 2019/9/4.
 */
@Controller
@RequestMapping("/boss/phone")
public class PhoneManagerControler  extends AbstractBaseController {

    private static final Logger logger = LoggerFactory.getLogger(PhoneManagerControler.class);

    @Autowired
    private PhoneManagerBll phoneManagerBll;

    /**
     * 搜索手机
     *
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> getPhoneInfo(@RequestParam("keyword") String keyword) {
        logger.info("/boss/phone/get GET 方法被调用!!");
        try {
            List<PhoneManagerBean> phoneList = phoneManagerBll.searchPhoneInfo(keyword);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("phoneList", phoneList);
                put("phoneListCounts", phoneList.size());
            }};
            return ResultBean.success("获取成功！", result);
        } catch (Exception e) {
            logger.error("添加用例异常：{}", e);
            return ResultBean.failed("执行失败!");
        }
    }

    /**
     * 查询单个手机信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/phoenById", method = RequestMethod.GET)

    @ResponseBody
    public ResultBean<Object> searchEnvById(@RequestParam(value = "id") Integer id) {
        logger.info("/task/list GET 方法被调用!!");
        try {
            PhoneManagerBean bean = phoneManagerBll.getPhoneById(id);
            return ResultBean.success("查询手机成功!", bean);
        } catch (Exception e) {
            logger.error("查询手机异常：{" + e + "}");
            return ResultBean.failed("查询手机异常!");
        }
    }


    /**
     * 删除信息
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<String> delete(@RequestParam(value = "id") Integer id) {
        logger.info("/phone/delete GET 方法被调用!!");
        try {
            phoneManagerBll.delete(id);
            return ResultBean.success("删除成功!");
        } catch (Exception e) {
            logger.error("删除接口异常：{" + e + "}");
            return ResultBean.failed("删除接口异常!");
        }
    }

    /**
     * 添加手机信息
     *
     * @param form
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> add(PhoneManagerForm form) {
        logger.info("/phone/add POST 方法被调用!!" + form.toString());
        try {
            PhoneManagerBean bean = new PhoneManagerBean();
            BeanUtils.copyProperties(form, bean);
            bean.setUpdateTime(new Date());
            phoneManagerBll.add(bean);
            return ResultBean.success("添加手机成功!");
        } catch (Exception e) {
            logger.error("添加异常：{}", e);
            return ResultBean.failed("添加失败!");
        }
    }

    /**
     * 修改手机信息
     *
     * @param form
     * @param
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Object> update(PhoneManagerForm form) {
        logger.info("/phone/update POST 方法被调用!!" + form.toString());
        try {
            PhoneManagerBean bean = new PhoneManagerBean();
            BeanUtils.copyProperties(form, bean);
            bean.setUpdateTime(new Date());
            phoneManagerBll.update(bean);
            return ResultBean.success("更新手机成功！");
        } catch (Exception e) {
            logger.error("更新异常：{}", e);
            return ResultBean.failed("更新失败!");
        }
    }

    /**
     * 导入手机信息
     * @return
     * @throws FileNotFoundException
     */
    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> excelToDatabase() throws FileNotFoundException {
        try{
            FileInputStream in = new FileInputStream("C:\\Users\\admin\\Desktop\\20191009.xlsx");
            Workbook wk = StreamingReader.builder()
                    .rowCacheSize(100)  //缓存到内存中的行数，默认是10
                    .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                    .open(in);  //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
            Sheet sheet = wk.getSheetAt(0);
            //遍历所有的行
            for (Row row : sheet) {
                if (row.getRowNum() != 0 && row.getRowNum() <= 96) {
                    List<String> list = new ArrayList<>();
                    System.out.println("开始遍历第" + row.getRowNum() + "行数据：");
                    //遍历所有的列
                    PhoneManagerBean bean = new PhoneManagerBean();
                    for (Cell cell : row) {
                        list.add(cell.getStringCellValue());
                    }
                    //循环list往bean中插入数据
                    for (int i = 0; i < list.size(); i++) {
                        if(i == 0){
                            bean.setNumber(list.get(i));
                        }else if(i == 1){
                            bean.setPhone(list.get(i));
                        }else if(i == 2){
                            bean.setType(list.get(i));
                        }else if(i == 3){
                            bean.setOs(list.get(i));
                        }else if(i == 4){
                            bean.setCrack(list.get(i));
                        }else if(i == 5){
                            bean.setResolution(list.get(i));
                        }else if(i == 6){
                            bean.setSize(list.get(i));
                        }else if(i == 7){
                            bean.setNetworkModel(list.get(i));
                        }else if(i == 8){
                            bean.setReceiver(list.get(i));
                        }else if(i == 9){
                            bean.setImei(list.get(i));
                        }else if(i == 10){
                            bean.setUser(list.get(i));
                        }else if(i == 11){
                            bean.setDepartment(list.get(i));
                        }else if(i == 12){
                            bean.setRemarks(list.get(i));
                        }
                    }
                    bean.setUpdateTime(new Date());
                    phoneManagerBll.add(bean);
                }
            }
            return ResultBean.success("更新手机成功！");
        } catch (Exception e) {
            logger.error("更新异常：{}", e);
            return ResultBean.failed("更新失败!");
        }


    }
}