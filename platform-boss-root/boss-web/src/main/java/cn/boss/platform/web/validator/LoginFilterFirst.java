package cn.boss.platform.web.validator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆过滤器
 * Created by caosenquan on 2018/3/5.
 */
public class LoginFilterFirst implements Filter {

    private  FilterConfig config;

    public void destroy() {
        // TODO Auto-generated method stub
        System.out.println("destroy");
    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain arg2) throws IOException, ServletException {
        // TODO Auto-generated method stub
        System.out.println("doFilter");
        HttpServletRequest request=(HttpServletRequest)arg0;
        HttpServletResponse response=(HttpServletResponse)arg1;
        //获取初始化参数
        String para=config.getInitParameter("nofilterpath");
        System.out.println(para);
        if(request.getRequestURI().indexOf("login") != -1) {
            arg2.doFilter(arg0, arg1);
            return;
        }
        if(request.getSession().getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath()+"/login");
        }
        else {
            arg2.doFilter(arg0, arg1);
        }
    }
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        System.out.println("init");
        config=arg0;

    }

}