package cn.action;

import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import com.squareup.spoon.Spoon;
import org.junit.Assert;
import java.io.IOException;
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
        MainAction.clickMe();
        waitTime(1);
        if (id_exists(AccountPage.ACCOUNT_WEIXIN)){
            //当前账号已经处于logout状态
            logger.info("当前账号已经处于logout状态");
        }else{
            //clickById(MePage.SETTINGS_USER_MAIN);
            clickByText("Settings");
            clickByText("Account and Security");
            //clickById(AccountPage.LOG_OUT);
            clickByText("Log out");
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
        waitUntilFind(MePage.ID_MAIN_TAB_ME,10000);
        if(text_exists_contain("CLOSE")){
            clickTextContain("CLOSE");
        }
        if(text_exists_contain("Close")){
            clickTextContain("Close");
        }
        if (id_exists(MePage.ID_WELCOME_PAGE_VP)){
            getObject2ById(MePage.ID_WELCOME_PAGE_VP).swipe(Direction.LEFT,0.6f);
            waitTime(3);
            clickById(MePage.ID_WELCOME_PAGE_VP);
            waitTime(2);
        }
        if(text_exists_contain("OK")){
            clickTextContain("OK");
        }
        if(text_exists_contain("Ok")){
            clickTextContain("Ok");
        }

        MainAction.clickMe();
        if (id_exists(AccountPage.ACCOUNT_WEIXIN)){
            waitUntilFindText("Log in",10000);
            if (text_exists("Log in")){
                logger.info("===============================================================");
            }
            clickByText("Log in");
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
            waitUntilFind(MePage.ID_MAIN_TAB_ME,10000);
        }else{
            logger.info("处于登录状态，不需要重新登录账号");
        }
        Spoon.screenshot("inLogin");
    }
    //登录账号
    public static void logInAccount(String username,String password) throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MainAction.clickMe();
        if (id_exists(AccountPage.ACCOUNT_WEIXIN)){
            clickByText("Log in");
            getObjectById(AccountPage.LOGIN_ET_INPUT_USERNAME).setText(username);
            getObjectById(AccountPage.LOGIN_ET_INPUT_PASSWORD).setText(password);
            clickById(AccountPage.LOGIN_ET_SIGN_UP_BTN);
            waitUntilFind(MePage.ID_MAIN_TAB_ME,20);
        }
    }
    //进入登录界面
    public static void navToLogin() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MainAction.clickMe();
        clickByText("Log in");
    }
    //进入Sign Up界面-mobile
    public static void navToSignUp_ByMobile() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MainAction.clickMe();
        clickByText("Sign Up");
    }
    //进入Sign Up界面-Email
    public static void navToSignUp_ByEmail() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MainAction.clickMe();
        clickByText("Sign Up");
        clickByText("Register with email");
    }
    //进入注册输入密码界面by Email
    public static void navToInputPassword_ByEmailRegister() throws UiObjectNotFoundException, IOException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MainAction.clickMe();
        clickByText("Sign Up");
        clickByText("Register with email");
        String Email = Constant.randomEmail(18);
        getObjectById(AccountPage.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT).setText(Email);
        waitTime(3);
        clickById(AccountPage.SIGN_UP_CONTINUE);
        waitTime(1);
    }
    //进入注册输入密码
    public static void inputPassword(String password) throws UiObjectNotFoundException, IOException {
        getObjectById(AccountPage.SIGN_UP_ACCOUNT_PASSWORD_INPUT).setText(password);
        waitTime(1);
    }
    //进入注册输入SioEyeID
    public static void inputSioEyeId(String sioEye) throws UiObjectNotFoundException, IOException {
        getObjectById(AccountPage.SIGN_UP_ACCOUNT_SIOEYE_ID).setText(sioEye);
        waitTime(2);
    }
    public static void checkVisibleAndUnVisiblePassword() throws UiObjectNotFoundException, IOException {
        String randomPassword= Constant.randomStringGenerator(16);
        getObjectById(AccountPage.SIGN_UP_ACCOUNT_PASSWORD_INPUT).setText(randomPassword);
        waitTime(1);
        if (getUiObjectByText(randomPassword).exists())
        {
            clickById(AccountPage.ACCOUNT_PASSWORD_SHOW_BTN);
            clickById(AccountPage.ACCOUNT_PASSWORD_SHOW_BTN);
            waitTime(1);
            if (getUiObjectByText(randomPassword).exists())
            {
                Spoon.screenshot("SetPasswordUnVisibleFailed");
                Assert.fail("SetPasswordUnVisibleFailed");
            }
            else
            {
                clearText(AccountPage.SIGN_UP_ACCOUNT_PASSWORD_INPUT);
                randomPassword= Constant.randomStringGenerator(28);
                getObjectById(AccountPage.SIGN_UP_ACCOUNT_PASSWORD_INPUT).setText(randomPassword);
                waitTime(1);
                if (getUiObjectByText(randomPassword).exists())
                {
                    Spoon.screenshot("PasswordIsVisible");
                    Assert.fail("InputPasswordByUnVisibleButPasswordIsVisible");
                }
                else {
                    clickById(AccountPage.ACCOUNT_PASSWORD_SHOW_BTN);
                    waitTime(1);
                    if (!getUiObjectByText(randomPassword).exists())
                    {
                        Spoon.screenshot("PasswordIsUNVisible");
                        Assert.fail("PasswordIsVisible");
                    }
                }
            }
        }
        else
        {
            Spoon.screenshot("DefaultPasswordIsUnVisible");
            Assert.fail("DefaultPasswordIsUnVisible");
        }
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
        MainAction.clickMe();
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
