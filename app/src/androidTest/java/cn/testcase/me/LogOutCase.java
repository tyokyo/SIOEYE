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
import cn.action.AccountAction;
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
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        AccountAction.inLogin();
    }
    //注销Log out时->取消注销
    @Test
    public void testLogOut_Cancel(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        clickByText("账号与安全");
        clickById(AccountPage.LOG_OUT);
        clickById(AccountPage.LOG_OUT_CANCEL);
        boolean active = getObjectById(AccountPage.LOG_OUT).exists();
        Asst.assertEquals("testLogOut_Cancel",true,active);
        Spoon.screenshot("testLogOut_Cancel");
    }
    //注销Log out
    @Test
    public void testLogOut_OK() throws UiObjectNotFoundException {
        //log out
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        clickByText("账号与安全");
        clickById(AccountPage.LOG_OUT);
        clickById(AccountPage.LOG_OUT_OK);
        //log in
        clickById(MePage.ID_MAIN_TAB_ME);
        clickByText("登录");
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
