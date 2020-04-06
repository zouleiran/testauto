package cn.boss.platform.doe.util;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2019/11/8.
 */
public class SeleniumUtil {

    private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

    public WebDriver driver = null;

    /**
     * 初始化
     */
    public SeleniumUtil(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * get方法
     */
    public String get(String url,String maximize) {
        if(!StringUtils.isEmpty(maximize) && maximize.equals(1)){
            driver.manage().window().maximize();
        }
        driver.navigate().to(url);
        logger.info("get:[" + url + "]");
        return "get:[" + url + "]";
    }

    /**
     * close方法
     */
    public void close() {
        driver.close();
        logger.info("close url");
    }

    /**
     * 刷新方法
     */
    public void refresh() {
        driver.navigate().refresh();
        logger.info("refresh url");
    }



    /**
     * 后退方法
     */
    public void back() {
        driver.navigate().back();
        logger.info("后退成功！");
    }

    /**
     * 前进方法
     */
    public void forward() {
        driver.navigate().forward();
        logger.info("前进成功！");
    }

    /**
     * 添加cookie
     */
    public String  addCookie(String name,String value){
        //注入cookie时候，需要先删除才能注入成功，不然会失败的
        driver.manage().deleteCookieNamed(name);
        Cookie cookie = new Cookie(name,value,"","/",null);
        driver.manage().addCookie(cookie);
        logger.info("addCookie:  [ "+name+": "+value+" ]"+"getCookies：[ " + driver.manage().getCookies() + "]" );
        return "addCookie:  [ "+name+": "+value+" ]"+"getCookies：[ " + driver.manage().getCookies() + "]";
    }

    /**
     * iframe切换
     * @param element
     */
    public String switchToFrame(WebElement element){
        driver.switchTo().frame(element);
        logger.info("switchToFrame：[ " + switchtoString(element)+ "]" );
        return "switchToFrame：[ " + switchtoString(element)+ "]";
    }


    /**
     * pageLoadTimeout。页面加载时的超时时间。因为webdriver会等页面加载完毕在进行后面的操作，
     * 所以如果页面在这个超时时间内没有加载完成，那么webdriver就会抛出异常
     */
    public void waitForPageLoading(int pageLoadTime) {
        driver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);
    }

    /**
     * 包装清除操作
     * */
    public String clear(WebElement element) {
        try {
            element.clear();
            return ("clear [" + switchtoString(element)  + "]");
        } catch (Exception e) {
            return ("clear [" + switchtoString(element) + "]");
        }

    }



    /**
     * 包装点击操作，element为参数
     */
    public String click(WebElement element) {
        try {
            if (WaitForElement(element,3)) {
                clickTheClickable(element,System.currentTimeMillis(),3);
                logger.error("click [" + switchtoString(element) + "]");
                return "click [" + switchtoString(element) + "]";
            }else{
                logger.error("[" + switchtoString(element) + "] is no longer exist!");
                return "[" + switchtoString(element) + "] is no longer exist!";
            }
        } catch (Exception e) {
            logger.error("click  [" + switchtoString(element) + "]");
            return "click [" + switchtoString(element) + "]";
        }

    }

    /**
     *  不能点击时候重试点击操作 ，element为参数
     * @param element
     * @param startTime
     * @param timeOut
     */
    public void clickTheClickable(WebElement element, long startTime, int timeOut) {
        try {
            element.click();
        } catch (Exception e) {
            if (System.currentTimeMillis() - startTime > timeOut) {
                logger.warn("click ["+ switchtoString(element) + "]"+" failed！");
            } else {
                logger.warn("click ["+ switchtoString(element) + "]"+ " failed！");
                clickTheClickable(element, startTime, timeOut);
            }
        }
    }

    /**
     * 输入操作封装
     * @param element
     * @param key
     */

    public String sendKeys(WebElement element, String key){
        try {
            if(WaitForElement(element,3)){
                element.clear();
                element.sendKeys(key);
                logger.warn("sendKeys ["+key+"] to ["+ switchtoString(element) + "]");
                return "sendKeys ["+key+"] to ["+ switchtoString(element) + "]";
            }else{
                logger.warn("sendKeys ["+key+"] to "+switchtoString(element)+"success");
                return "sendKeys ["+key+"] to "+switchtoString(element)+"failed";
            }
        } catch (Exception e) {
            logger.warn("元素不存在["+ switchtoString(element) + "]");
            return "元素不存在["+ switchtoString(element) + "]";
        }
    }

    /**
     * 点击元素把元素拖到执行位置释放
     * @param element
     * @param x
     * @param y
     * @return
     */
    public String moveByOffset(WebElement element, int x,int y){
        try{
            Actions builder = new Actions(driver);
            builder.clickAndHold(element).perform();
            builder.moveByOffset(x,y);
            builder.release().perform();
            logger.warn("moveByOffset ["+ switchtoString(element) + "] to ["+x+","+y + "] success");
            return "moveByOffset ["+ switchtoString(element) + "] to ["+x+","+y + "] success";
        } catch (Exception e) {
            logger.warn("moveByOffset ["+ switchtoString(element) + "]  to ["+x+","+y + "] failed");
            return "moveByOffset ["+ switchtoString(element) + "]  to ["+x+","+y + "] failed";
        }

    }



    /**
     * 在给定的时间内去查找元素，如果没找到则超时，抛出异常,以element为参数
     */
    public boolean WaitForElement(final WebElement element, int timeOut) {
        try {
            new WebDriverWait(driver, timeOut,200L).until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }


    /**
     * 将element对象转换成string
     * @param element
     */
    public String switchtoString(WebElement element){
        String Character=null;
        String moduleName=null;
        try{
            if(element != null){
                Character=element.toString();
            }
            moduleName = Character.substring(Character.lastIndexOf(">")+1, Character.lastIndexOf("]"));
        }catch(Exception e){
            logger.error("element对象转换成String对象：失败！！[" +element +"]"+e);
        }
        return moduleName;
    }

    public WebElement generateElement(String type,String value){
        WebElement element = null;
        try{
            if(type.equals("id")){
                element = new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(By.id(value)));
            }else if(type.equals("name")){
                element = new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(By.name(value)));
            }else if(type.equals("css")){
                element = new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(value)));
            }else if(type.equals("xpath")){
                element = new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(By.xpath(value)));
            }
            return element;
        }catch(Exception e){
            return null;
        }

    }










}