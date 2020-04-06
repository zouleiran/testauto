package cn.boss.platform.web.interfaceManager;

import cn.boss.platform.bean.common.ResultBean;
import cn.boss.platform.bean.interfaceManager.DailyCaseBean;
import cn.boss.platform.bean.interfaceManager.ProjectBean;
import cn.boss.platform.bll.InterfaceManager.CaseBll;
import cn.boss.platform.bll.InterfaceManager.CaseExecuteLogBll;
import cn.boss.platform.bll.InterfaceManager.InterfaceBll;
import cn.boss.platform.bll.InterfaceManager.ProjectBll;
import cn.boss.platform.web.api.AbstractBaseController;
import cn.boss.platform.web.model.DailyCaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by caosenquan on 2018/10/18.
 * 图标报告controller
 */
@Controller
@RequestMapping("/boss/stat")
public class StatController extends AbstractBaseController {

    @Autowired
    private ProjectBll projectBll;
    @Autowired
    private InterfaceBll interfaceBll;
    @Autowired
    private CaseBll caseBll;
    @Autowired
    private CaseExecuteLogBll caseExecuteLogBll;

    private static final Logger logger = LoggerFactory.getLogger(StatController.class);
    /**
     * 基本信息
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> baseInfo() {
        try{
            //项目数
            Integer projectCount = projectBll.getProjectCount();
            //接口数和用例数
            Integer interfaceCount = interfaceBll.searchInterfaceCount(null,"");
            //用例总数
            Integer caseCount = caseBll.getCaseCounts();
            //报告数
            Integer logCount = caseExecuteLogBll.executeLogCount(null,null ,null,null,null);
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("interfaceCount", interfaceCount);
                put("caseCount", caseCount);
                put("projectCount", projectCount);
                put("logCount", logCount);
            }};
            return ResultBean.success("获取基本信息成功！",result);
        }catch (Exception e) {
            logger.error("接口请求异常：{}", e);
            return ResultBean.failed("接口请求异常!");
        }
    }


    /**
     * 每日用例执行数查询接口
     * @return
     */
    @RequestMapping(value = "/daily/executeCase", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> dailyCase() {
        try {
            int[] data = {4,3,2,1,0};
            List<DailyCaseModel> dailyCaseModelList = new ArrayList();
            for (int j=0; j<data.length; j++) {
                Date daily = StatController.getDateBefore(new Date(), data[j]);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                List<DailyCaseBean> caseExecuteList = caseExecuteLogBll.getDailyExecuteCase(df.format(daily));
                DailyCaseModel DailyCaseModel = new DailyCaseModel();
                DailyCaseModel.setData(df.format(daily));
                List<String> teamList = new ArrayList<>();
                List<Integer> caseCountList = new ArrayList<>();
                teamList.add("1");
                teamList.add("2");
                teamList.add("3");
                int caseCountOne = 0;
                int caseCountTwo = 0;
                int caseCountThree = 0;
                if(caseExecuteList !=null){
                    for(int i=0; i<caseExecuteList.size(); i++){
                        ProjectBean projectBean = projectBll.getProjectByid(caseExecuteList.get(i).getProject_id());
                        caseExecuteList.get(i).setTeam(projectBean.getProjectTeam());
                        if(projectBean.getProjectTeam().equals(1)){
                            int count = caseExecuteList.get(i).getCaseExecuteCount();
                            caseCountOne = caseCountOne + count;
                        }else if(projectBean.getProjectTeam().equals(2)){
                            int count = caseExecuteList.get(i).getCaseExecuteCount();
                            caseCountTwo = caseCountTwo + count;
                        }else if(projectBean.getProjectTeam().equals(3)){
                            int count = caseExecuteList.get(i).getCaseExecuteCount();
                            caseCountThree = caseCountThree + count;
                        }
                    }
                    caseCountList.add(caseCountOne);
                    caseCountList.add(caseCountTwo);
                    caseCountList.add(caseCountThree);
                    DailyCaseModel.setTeam(teamList);
                    DailyCaseModel.setCaseCount(caseCountList);
                }
                dailyCaseModelList.add(DailyCaseModel);
            }
            return ResultBean.success("获取每日执行用例数成功！", dailyCaseModelList);
        } catch (Exception e) {
            logger.error("接口请求异常：{}", e);
            return ResultBean.failed("接口请求异常!");
        }
    }

    /**
     * 接口用例分布数查询接口
     * @return
     */
    @RequestMapping(value = "/interfaceAndCase/distribution", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Object> interfaceAndCaseDistribution() {
        try {
            List<String> projectList = new ArrayList<>();
            List<Integer> interfaceList =new ArrayList<>();
            List<Integer> caseList =new ArrayList<>();
            List<ProjectBean> list = projectBll.getProjectByProjectTeam();
            if(list !=null){
                for(int i=0; i < list.size(); i++){
                    projectList.add(list.get(i).getProjectName());
                    interfaceList.add(interfaceBll.getInterfaceCountByprojectId(list.get(i).getId()));
                    caseList.add(caseBll.getCaseCountByProjectId(list.get(i).getId()));
                }
            }
            Map<String, Object> result = new HashMap<String, Object>() {{
                put("interfaceCount", interfaceList);
                put("caseCount", caseList);
                put("projectName", projectList);
            }};
            return ResultBean.success("获取接口用例分布数成功！", result);
        } catch (Exception e) {
            logger.error("接口请求异常：{}", e);
            return ResultBean.failed("接口请求异常!");
        }

    }


    public static Date getDateBefore(Date d, int day) {
        Calendar no = Calendar.getInstance();
        no.setTime(d);
        no.set(Calendar.DATE, no.get(Calendar.DATE) - day);
        return no.getTime();
    }

}
