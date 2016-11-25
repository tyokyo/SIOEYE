package cn.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import com.squareup.spoon.Spoon;
import java.util.Random;
import java.util.logging.Logger;
import ckt.base.VP2;
import cn.page.AccountPage;
import cn.page.App;
import cn.page.Constant;
import cn.page.MePage;
/**
 * Created by admin on 2016/11/2.
 **/
public class AccountAction extends VP2{
    private static Logger logger=Logger.getLogger(AccountAction.class.getName());
    //注销账号
    /*如果当前处于登录状态，注销账号
    如果当前已处于注销状态，退出
    * */
    public static  void logOutAccount() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        clickById(MePage.ID_MAIN_TAB_ME);
        if (id_exists(AccountPage.ACCOUNT_WEIXIN)){
            //当前账号已经处于logout状态
            logger.info("当前账号已经处于logout状态");
        }else{
            //clickById(MePage.SETTINGS_USER_MAIN);
            clickByText("设置");
            clickByText("账号与安全");
            //clickById(AccountPage.LOG_OUT);
            clickByText("退出登录");
            clickById(AccountPage.LOG_OUT_OK);
            //wait logout
            waitUntilFind(MePage.ID_MAIN_TAB_ME,60000);
            logger.info("LogOut success");
            Spoon.screenshot("logOut");
        }
    }
    /*
    * 判断是否处于登录状态,
    * 未登录，将会登录账号
    * */
    public static void inLogin() throws UiObjectNotFoundException {
        boolean login = true;
        if(id_exists(MePage.ID_TV_OK)){
            clickById(MePage.ID_TV_OK);
            waitHasObject(MePage.ID_MAIN_TAB_ME,10000);
        }
        clickById(MePage.ID_MAIN_TAB_ME);
        if (id_exists(AccountPage.ACCOUNT_WEIXIN)){
            clickByText("登录");
            /*
            如果手机sdcard存在config.properties
            并且内容为
            user_name    =***********
            user_password=***********
            第一优先级采用这个账号登录app
            如果没有配置:
            采用cn.page.Constant.java 文件中的Constant.userName Constant.passwd 登录app
            * */
            String useName=Constant.getUserName();
            String password=Constant.getPassword();
            if (useName!=null&&password!=null){
                logger.info(String.format("use config.properties userName:%s password:%s to login app",useName,password));
            }else{
                useName=Constant.userName;
                password=Constant.passwd;
                logger.info(String.format("use local userName:%s password:%s to login app",Constant.userName,Constant.passwd));
            }
            //input username
            //getObjectById(AccountPage.LOGIN_ET_INPUT_USERNAME).click();
            getObjectById(AccountPage.LOGIN_ET_INPUT_USERNAME).setText(useName);
            //input  password
            //getObjectById(AccountPage.LOGIN_ET_INPUT_PASSWORD).click();
            getObjectById(AccountPage.LOGIN_ET_INPUT_PASSWORD).setText(password);
            //login
            clickById(AccountPage.LOGIN_ET_SIGN_UP_BTN);
            waitUntilFind(MePage.ID_MAIN_TAB_ME,20);
        }else{
            logger.info("处于登录状态，不需要重新登录账号");
        }
        Spoon.screenshot("inLogin");
    }
    //登录账号
    public static void logInAccount(String username,String password) throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        clickById(MePage.ID_MAIN_TAB_ME);
        if (id_exists(AccountPage.ACCOUNT_WEIXIN)){
            clickByText("登录");
            getObjectById(AccountPage.LOGIN_ET_INPUT_USERNAME).setText(username);
            getObjectById(AccountPage.LOGIN_ET_INPUT_PASSWORD).setText(password);
            clickById(AccountPage.LOGIN_ET_SIGN_UP_BTN);
            waitUntilFind(MePage.ID_MAIN_TAB_ME,20);
        }
    }
    //进入登录界面
    public static void navToLogin() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        clickById(MePage.ID_MAIN_TAB_ME);
        clickByText("登录");
    }
    //进入Sign Up界面-mobile
    public static void navToSignUp_ByMobile() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        clickById(MePage.ID_MAIN_TAB_ME);
        clickByText("注册");
    }
    //进入Sign Up界面-mobile
    public static void navToSignUp_ByEmail() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        clickById(MePage.ID_MAIN_TAB_ME);
        clickByText("注册");
        clickByText("邮箱注册");
    }
    //仅仅一个登陆的动作
    public static void justLogIn(String username,String password) throws UiObjectNotFoundException {
        getObjectById(AccountPage.LOGIN_ET_INPUT_USERNAME).setText(username);
        getObjectById(AccountPage.LOGIN_ET_INPUT_PASSWORD).setText(password);
        clickById(AccountPage.LOGIN_ET_SIGN_UP_BTN);
    }
    /**
     *
     *启动app,判断是否登录,已经登录返回TRUE，并返回到初始化设备状态
     */
    public static boolean isLogin() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        clickById(MePage.ID_MAIN_TAB_ME);
        //判断是否登录
        if (gDevice.findObject(new UiSelector().text("Login")).exists()) {
            System.out.println("you haven't login");
            return false;
        } else {
            System.out.println("you have logined");
            clickById(MePage.ID_MAIN_TAB_DISCOVER);
            return true;
        }
    }
}
