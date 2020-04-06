package cn.boss.platform.doe.util;

import org.omg.PortableInterceptor.INACTIVE;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


/**
 * Created by admin on 2019/11/7.
 */
public class DriverManage {

    private static final Logger logger = LoggerFactory.getLogger(DriverManage.class);
    private static WebDriver driver = null;

    //构造方法
    private DriverManage() {

    }

    //启动浏览器
    public static WebDriver getDriver(int type, Integer restartBrowser,String hubUrl) throws MalformedURLException {
        if (restartBrowser == 1) {
            //手动加载日志文件
            //CommonMethord.getRealath()获取工程目录绝对路径

            DesiredCapabilities caps = null;
            switch (type) {
                case 1:
                    System.setProperty("webdriver.firefox.bin", "d:\\Program Files\\Mozilla Firefox\\firefox.exe");
                    driver = new FirefoxDriver();
                    logger.info("runDriver is ff......");
                    break;
                case 2:
                    System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/res/IEDriverServer_32.exe");
                    caps = DesiredCapabilities.internetExplorer();
                    caps.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
                    caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    //IE默认开启保护模式，要么手动关闭，要么使用这句
                    caps.setCapability("ignoreProtectedModeSettings", true);
                    caps.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, "-private");
                    caps.setCapability("ignoreZoomSetting", true);
                    //new IE对象
                    driver = new InternetExplorerDriver(caps);
                    logger.info("runDriver is ie......");
                    break;
                case 3:
//                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/boss-doe/res/chromedriver.exe");
//                    driver = new ChromeDriver();
                    driver = new RemoteWebDriver(new URL(hubUrl), DesiredCapabilities.chrome());
                    logger.info("runDriver is chrome......");
                    break;
            }
        } else {
            logger.info("实力已经创建！！");
        }
        return driver;
    }

    public static void main(String[] args) throws MalformedURLException {
        String url =  "http://172.16.24.181:4992/wd/hub";

        WebDriver driver = new RemoteWebDriver(new URL(url), DesiredCapabilities.chrome());
        driver.get("https://www.baidu.com/");
        String test = null;
        System.out.println(test + "");
    }
}