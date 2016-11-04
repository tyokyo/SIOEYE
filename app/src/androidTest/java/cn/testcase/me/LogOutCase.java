package cn.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ckt.base.VP2;
import ckt.tools.Constant;
import usa.page.App;
import usa.page.Me;

/**
 * Created by elon on 2016/10/28.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class LogOutCase extends VP2 {
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    //注销Log out时->取消注销
    @Test
    public void testLogOut_Cancel(){
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.SETTINGS_USER_MAIN);
        clickById(Me.LOG_OUT);
        clickById(Me.LOG_OUT_CANCEL);
        boolean active = getObjectById(Me.LOG_OUT).exists();
        Asst.assertEquals("logout cancel",true,active);
    }
    //注销Log out
    @Test
    public void testLogOut_OK() throws UiObjectNotFoundException {
        //log out
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.SETTINGS_USER_MAIN);
        clickById(Me.LOG_OUT);
        clickById(Me.LOG_OUT_OK);
        //log in
        clickById(Me.ID_MAIN_TAB_ME);
        clickByText("Login");
        //input username
        getObjectById(Me.INPUT_USERNAME).clearTextField();
        getObjectById(Me.INPUT_USERNAME).setText(Constant.userNmae);
        //input password
        getObjectById(Me.INPUT_PASSWORD).clearTextField();
        getObjectById(Me.INPUT_PASSWORD).setText(Constant.passwd);
        clickById(Me.LOGIN_SIGN_UP);
        gDevice.wait(Until.findObject(By.res(Me.ID_MAIN_TAB_ME)),10000);
        boolean active = getObjectById(Me.ID_MAIN_TAB_ME).exists();
        Asst.assertEquals("Login success",true,active);
        Spoon.screenshot("Login_success");
    }
}
