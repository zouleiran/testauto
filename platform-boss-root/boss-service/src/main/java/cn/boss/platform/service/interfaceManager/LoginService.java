package cn.boss.platform.service.interfaceManager;

import cn.boss.platform.bll.util.DealStrSub;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Hashtable;

import javax.naming.*;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;


@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private final String URL = "ldap://192.168.1.15/";
    private final String BASEDN = "ou=people,dc=kanzhun,dc=org";
    private final String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private final String ADMIN = "uid=techwolfadmin";
    private final String PWD = "Ph@xW18dYr";
    private LdapContext ctx = null;
    private final Control[] connCtls = null;


    public void LDAP_connect() {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY);
        env.put(Context.PROVIDER_URL, URL + BASEDN);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        env.put(Context.SECURITY_PRINCIPAL, ADMIN +  "," + BASEDN);
        env.put(Context.SECURITY_CREDENTIALS,  PWD);
        // 此处若不指定用户名和密码,则自动转换为匿名登录
        try {
            ctx = new InitialLdapContext(env, connCtls);
        } catch (AuthenticationException e) {
            logger.error("验证失败：" + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUserDN(String uid) {
        String userDN = "";
        LDAP_connect();
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> en = ctx.search("", "uid=" + uid, constraints);
            if (en == null || !en.hasMoreElements()) {
                logger.info("未找到该用户");
                return null;
            }
            while (en != null && en.hasMoreElements()) {
                Object obj = en.nextElement();
                if (obj instanceof SearchResult) {
                    SearchResult si = (SearchResult) obj;
                    System.out.println(si.getAttributes());
                    userDN += si.getName();
                    userDN += "," + BASEDN;
                } else {
                    logger.info(obj+"");
                }
            }
        } catch (Exception e) {
            logger.info("查找用户时产生异常。");
            e.printStackTrace();
        }
        return userDN;
    }

    public String getUserinfo(String uid) {
        String usrInfo = "";
        LDAP_connect();
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> en = ctx.search("", "uid=" + uid, constraints);
            if (en == null || !en.hasMoreElements()) {
                logger.info("未找到该用户");
                return null;
            }
            while (en != null && en.hasMoreElements()) {
                Object obj = en.nextElement();
                if (obj instanceof SearchResult) {
                    SearchResult si = (SearchResult) obj;
                    usrInfo = si.getAttributes().toString();
                } else {
                    logger.info(obj+"");
                }
            }
        } catch (Exception e) {
            logger.info("查找用户时产生异常。");
            e.printStackTrace();
        }
        return usrInfo;
    }

    public boolean authenricate(String UID, String password) {
        boolean valide = false;
        String userDN = getUserDN(UID);
        try {
            if(StringUtils.isBlank(userDN)){
                valide = false;
            } else {
                ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, userDN);
                ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
                ctx.reconnect(connCtls);
                System.out.println(userDN + " 验证通过");
                valide = true;
            }

        } catch (AuthenticationException e) {
            System.out.println(userDN + " 验证失败");
            System.out.println(e.toString());
            valide = false;
        } catch (NamingException e) {
            System.out.println(userDN + " 验证失败");
            valide = false;
        }

        return valide;
    }


    public static void main(String[] args) throws ParseException {
        LoginService login = new LoginService();
        login.LDAP_connect();
        System.out.println(login.authenricate("caosenquan@kanzhun.com","Cao13583192122"));
        String rgex = "sn=sn: (.*?), loginshell";
        System.out.println(DealStrSub.getSubUtilSimple(login.getUserinfo("caosenquan@kanzhun.com"), rgex));





    }
}