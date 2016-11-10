package cn.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import com.squareup.spoon.Spoon;
import java.util.Random;
import ckt.base.VP2;
import cn.page.AccountPage;
import cn.page.App;
import cn.page.Constant;
import cn.page.MePage;

/**
 * Created by admin on 2016/11/2.
 **/
public class AccountAction extends VP2{
    //注销账号
    /*如果当前处于登录状态，注销账号
    如果当前已处于注销状态，退出
    * */
    public static  void logOutAccount() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        if (id_exists(AccountPage.ACCOUNT_WEIXIN)){
            //当前账号已经处于logout状态
        }else{
            clickById(AccountPage.LOG_OUT);
            clickById(AccountPage.LOG_OUT_OK);
            //wait logout
        }
    }
    //判断是否处于登录状态
    public static void inLogin() throws UiObjectNotFoundException {
        boolean login = true;
        if(id_exists(MePage.ID_TV_OK)){
            clickById(MePage.ID_TV_OK);
            waitHasObject(MePage.ID_MAIN_TAB_ME,10000);
        }
        clickById(MePage.ID_MAIN_TAB_ME);
        if (id_exists(AccountPage.ACCOUNT_WEIXIN)){
            clickByText("登录");
            //input username
            //getObjectById(AccountPage.LOGIN_ET_INPUT_USERNAME).click();
            getObjectById(AccountPage.LOGIN_ET_INPUT_USERNAME).setText(Constant.userName);
            //input  password
            //getObjectById(AccountPage.LOGIN_ET_INPUT_PASSWORD).click();
            getObjectById(AccountPage.LOGIN_ET_INPUT_PASSWORD).setText(Constant.passwd);
            //login
            clickById(AccountPage.LOGIN_ET_SIGN_UP_BTN);
            waitUntilFind(MePage.ID_MAIN_TAB_ME,20);
        }
        Spoon.screenshot("inLogin");
    }
    //登录账号
    public static void logInAccount(String username,String password) throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
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
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        clickById(MePage.ID_MAIN_TAB_ME);
        clickByText("登录");
    }
    //进入Sign Up界面-mobile
    public static void navToSignUp_ByMobile() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        clickById(MePage.ID_MAIN_TAB_ME);
        clickByText("注册");
    }
    //进入Sign Up界面-mobile
    public static void navToSignUp_ByEmail() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        clickById(MePage.ID_MAIN_TAB_ME);
        clickByText("注册");
        clickByText("邮箱注册");
    }
}
