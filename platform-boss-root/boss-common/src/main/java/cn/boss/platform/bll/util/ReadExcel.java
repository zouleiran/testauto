package cn.boss.platform.bll.util;



import cn.boss.platform.bean.toolsManager.PhoneManagerBean;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2019/10/19.
 */
public class ReadExcel {

    public void testLoad() throws Exception{
        FileInputStream in = new FileInputStream("C:\\Users\\admin\\Desktop\\20191009.xlsx");
        Workbook wk = StreamingReader.builder()
                .rowCacheSize(100)  //缓存到内存中的行数，默认是10
                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                .open(in);  //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
        Sheet sheet = wk.getSheetAt(0);
        //遍历所有的行
        for (Row row : sheet) {
            if (row.getRowNum() != 0 && row.getRowNum() <= 96){
                List<String> list = new ArrayList<>();
                System.out.println("开始遍历第" + row.getRowNum() + "行数据：");
                //遍历所有的列
                PhoneManagerBean bean = new PhoneManagerBean();
                for (Cell cell : row) {
//                    System.out.print(cell.getStringCellValue() + " ");
                    list.add(cell.getStringCellValue());
                }
                //循环list往bean中插入数据
                for(int i = 0 ; i < list.size() ; i++) {
                    System.out.println(list.get(i));
                }
                System.out.println(list);
//                System.out.println(" ");
            }

        }
    }

    public static void main(String[] args) throws Exception {
        ReadExcel t = new ReadExcel();
        t.testLoad();
    }
}