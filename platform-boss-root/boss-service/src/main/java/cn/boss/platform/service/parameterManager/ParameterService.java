package cn.boss.platform.service.parameterManager;

import cn.boss.platform.bean.interfaceManager.CaseBean;
import cn.boss.platform.bean.interfaceManager.ParameterBean;
import cn.boss.platform.bll.InterfaceManager.CaseBll;
import cn.boss.platform.bll.InterfaceManager.InterfaceBll;
import cn.boss.platform.bll.InterfaceManager.ParameterBll;
import cn.boss.platform.service.interfaceManager.ExecuteService;
import cn.boss.platform.service.tools.DatabaseService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by admin on 2018/8/10.
 */
@Service
public class ParameterService {

    private static final Logger logger = LoggerFactory.getLogger(ParameterService.class);
    @Autowired
    ParameterBll parameterBll;
    @Autowired
    CaseBll caseBll;
    @Autowired
    private InterfaceBll interfaceBll;
    @Autowired
    private ExecuteService executeService;

    /**
     * 参数替换
     * @parameters 参数
     * @return 返回
     */
    public  String evalParameter(String parameters) throws InterruptedException {
        if(StringUtils.isBlank(parameters)){
            return "";
        }else{
            Pattern pattern = Pattern.compile("\\$\\{.+?\\}");
            Matcher matcher = pattern.matcher(parameters);
            while (matcher.find()) {
                String group = matcher.group(0);
                String name = group.substring(2, group.length() - 1);
                ParameterBean parameterBean = parameterBll.selectByName(name);
                //不为空，能够做参数替换
                parameters = parameters.replace(group, evalParameterOne(name));
            }
        }
        return parameters;
    }

    /**
     * 单参数替换
     * @param name
     * @return
     */
    public  String evalParameterOne(String name) throws InterruptedException {
        ParameterBean parameterBean = parameterBll.selectByName(name);
        //数据库不为空，能够做参数替换，除非函数助手
        if(parameterBean != null){
            switch (parameterBean.getType()) {
                case "sql":
                    return getSqlResult(parameterBean);
                case "wait":
                    String value = parameterBean.getValue();
                    boolean isNum = value.matches("[0-9]+");
                    if (isNum) {
                        Thread.sleep(Integer.valueOf(value) * 1000);
                    }
                    return value;
                default:
                    return parameterBean.getValue();
            }
        }else{
            return EvalFunction(evalMethod(name))+"";
        }
    }


    /**
     *解析函数助手
     * @param parameters
     * @return
     */
    public  String[] evalMethod(String parameters) {
        if (StringUtils.isBlank(parameters)) {
            return null;
        } else {
            String spiltRules = "\\+|,|\\*|/|=|\\(|\\)";
            String[] array = parameters.split(spiltRules);
//            for (String s : array) {
//                System.out.println(s);
//            }
            return array;
        }
    }

    /**
     * 反射调用
     * @param parameter
     * @return
     */
    public Object EvalFunction(String[] parameter) {
        Object noReturnObject = null;
        try {
            //2、反射调用java方法--------
            //获取某个类的Class对象
            Class<?> cls=Class.forName("cn.boss.platform.bll.util.Function");
            //利用获取到的类的Class对象新建一个实例（相当于Reflect new了个对象）获取无参构造方法并创建对象
            Constructor con = cls.getConstructor();
            Object obj = con.newInstance();
            //获取Reflect的方法，第一个参数是方法名；第二个参数是参数的类型，注意是参数的类型！
            if(parameter.length-1 == 0){
                Method med = cls.getDeclaredMethod(parameter[0].substring(2));
                noReturnObject = med.invoke(obj);
            }else if(parameter.length-1 == 1){
                Method med = cls.getDeclaredMethod(parameter[0].substring(2),String.class);
                noReturnObject = med.invoke(obj,parameter[1]);
            }else if(parameter.length-1 == 2){
                Method med = cls.getDeclaredMethod(parameter[0].substring(2),String.class,String.class);
                noReturnObject = med.invoke(obj,parameter[1],parameter[2]);
            }
        }catch (Exception e) {
        }
        return noReturnObject;
    }

    /**
     * 通过查询数据库进行参数替换
     * @param bean 参数bean
     * @return 返回
     */
    public String getSqlResult(ParameterBean bean) {
        String sql = bean.getDbSql();
        String result = "";
        DatabaseService database = new DatabaseService(bean.getDbEnv(), bean.getDbName());
        if (sql.indexOf("select") == 0) {
            List<Map<String, String>> sqlResult = database.select(sql, 1, 1);
            if(sqlResult !=null){
                result = sqlResult.get(0).get(bean.getDbColumn());
            }
        } else if (sql.indexOf("update") == 0 || sql.indexOf("delete") == 0 || sql.indexOf("insert into") == 0 || sql.indexOf("create") == 0 || sql.indexOf("alter") == 0) {
            database.update(sql);
        }
        logger.debug("通过JDBC获取前置参数值{}", result);
        bean.setValue(result);
        bean.setUpdateTime(new Date());
        //更新数据
        parameterBll.updateParameter(bean);
        return result;
    }

    /**
     * 通过执行用例
     * @param caseId
     * @param currentTime
     * @param taskId
     */
    public void getCaseResult(int caseId,int currentTime,int taskId){
        //查询用例信息
        CaseBean caseBean = caseBll.getCaseById(caseId);
        if(caseBean !=null){
            //查询接口信息
            caseBean.setInterfaceBean(interfaceBll.getInterfaceById(caseBean.getInterfaceId()));
            executeService.doEachCase(caseBean,currentTime,taskId,null,"前后置执行的用例",null);
        }
    }


}
