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

import java.util.logging.Logger;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.MeAction;
import cn.page.AccountPage;
import cn.page.App;
import cn.page.Constant;
import cn.page.MePage;

/**
 * Created by elon on 2016/10/28.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class LogOutCase extends VP2 {
    private Logger logger = Logger.getLogger(LogOutCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    //注销Log out时->取消注销
    @Test
    @SanityTest
    @PerformanceTest
    public void testLogOut_Cancel() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("Account and Security");
        //clickById(AccountPage.LOG_OUT);
        clickByText("Log out");
        clickById(AccountPage.LOG_OUT_CANCEL);
        waitTime(2);
        boolean active = text_exists("Log out");
        Asst.assertEquals("testLogOut_Cancel",true,active);
        Spoon.screenshot("testLogOut_Cancel");
    }
    //注销Log out
    @Test
    @SanityTest
    @PerformanceTest
    public void testLogOut_OK() throws UiObjectNotFoundException {
        //log out
        MeAction.navToSettings();
        clickByText("Account and Security");
        //clickById(AccountPage.LOG_OUT);
        clickByText("Log out");
        clickById(AccountPage.LOG_OUT_OK);
        //log in
        clickById(MePage.ID_MAIN_TAB_ME);
        clickByText("Log in");
        //input username
        getObjectById(MePage.INPUT_USERNAME).clearTextField();
        getObjectById(MePage.INPUT_USERNAME).setText(Constant.userName);
        //input password
        getObjectById(MePage.INPUT_PASSWORD).clearTextField();
        getObjectById(MePage.INPUT_PASSWORD).setText(Constant.passwd);
        clickById(MePage.LOGIN_SIGN_UP);
        gDevice.wait(Until.findObject(By.res(MePage.ID_MAIN_TAB_ME)),10000);
        boolean active = getObjectById(MePage.ID_MAIN_TAB_ME).exists();
        Asst.assertEquals("Login success",true,active);
        Spoon.screenshot("testLogOut_OK");
    }
}
