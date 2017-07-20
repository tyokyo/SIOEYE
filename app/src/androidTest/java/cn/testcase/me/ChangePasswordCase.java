package cn.testcase.me;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import com.squareup.spoon.Spoon;
import org.hamcrest.Asst;
import org.junit.Assert;
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
 * Created by chendaofa on 2017/7/19.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class ChangePasswordCase extends VP2{
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    /**
     * 修改密码时输入与旧密码一样的新密码
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testChangeCroectPassword() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("Account and Security");
        clickByText("Change your password");
        getUiObject2ByText("Current password").setText("123456");
        getUiObject2ByText("New password").setText("123456");
        getUiObject2ByText("Re-type password").setText("123456");
        clickById(MePage.ACCOUNT_AND_SECURITY_RIGHT);
        Spoon.screenshot("testChangeCroectPassword");
        waitTime(3);
        String active_title=getObjectById(MePage.ACCOUNT_AND_SECURITY_TITLE).getText();
        String expect_title="Change your password";

        Asst.assertEquals("testLogOut_Cancel",expect_title,active_title);
        Spoon.screenshot("testChangeCroectPassword");
    }
    /**
     * 1.在密码不可见情况下输入密码
     2.打开可见并检查
     3.再重新输入密码并检查是否为可见
     4.关闭可见并检查
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testChangePassword() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("Account and Security");
        clickByText("Change your password");
        clearText(MePage.ACCOUNT_AND_PASSWORD);
        String userPassword=Constant.randomStringGenerator(10);
        getObjectById(MePage.ACCOUNT_AND_PASSWORD).setText(userPassword);
        clickById(MePage.ACCOUNT_AND_PASSWORD_VISIBLE);
        if (!getUiObjectByText(userPassword).exists()){
            Spoon.screenshot("FailedOpenPasswordVisible","开启密码可见失败");
            Assert.fail("FailedOpenPasswordVisible");
        }
        userPassword=Constant.randomStringGenerator(12);
        getObjectById(MePage.ACCOUNT_AND_PASSWORD).setText(userPassword);
        if (!getUiObjectByText(userPassword).exists()){
            Spoon.screenshot("Password_Visible_Fail","开启密码可见后再输入可见失效");
            Assert.fail("开启密码可见后再输入可见失效");
        }
        clickById(MePage.ACCOUNT_AND_PASSWORD_VISIBLE);
        if (getUiObjectByText(userPassword).exists()){
            Spoon.screenshot("Password_Unvisible_Fail","关闭密码可见失败");
            Assert.fail("FailedClosePasswordVisible");
        }

    }
    /**
     * 绑定手机号的时候输入错误密码
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testChangePhoneNumber() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("Account and Security");
        clickByText("Your Phone Number");
        Spoon.screenshot("testChangePhoneNumber");
        clickById(MePage.ACCOUNT_AND_TV_TOBING_PHONE);
        String userPassword=Constant.randomStringGenerator(20);
        getObjectById(MePage.ACCOUNT_AND_TV_TOBING_PHONE_PASSWORD).setText(userPassword);
        clickById(MePage.ACCOUNT_AND_TV_TOBING_PHONE_NEXT);
        waitTime(3);

        String active_title=getObjectById(MePage.ACCOUNT_AND_TV_TOBING_PHONE_NEXT).getText();
        String expect_title="Next";

        Asst.assertEquals("testLogOut_Cancel",expect_title,active_title);
        Spoon.screenshot("testChangePhoneNumber");
    }
    /**
     * 绑定邮箱时候入错误密码
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testChangeEmail() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("Account and Security");
        clickByText("Email");
        Spoon.screenshot("testChangePhoneNumber");
        clickById(MePage.ACCOUNT_AND_TV_TOBING_EMAIL);
        String userPassword=Constant.randomStringGenerator(20);
        getObjectById(MePage.ACCOUNT_AND_TV_TOBING_EMAIL_PASSWORD).setText(userPassword);
        clickById(MePage.ACCOUNT_AND_TV_TOBING_EMAIL_NEXT);
        waitTime(3);

        String active_title=getObjectById(MePage.ACCOUNT_AND_TV_TOBING_EMAIL_NEXT).getText();
        String expect_title="Next";

        Asst.assertEquals("testLogOut_Cancel",expect_title,active_title);
        Spoon.screenshot("testChangePhoneNumber");
    }
}
