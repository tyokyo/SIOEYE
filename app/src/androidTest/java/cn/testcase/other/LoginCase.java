package cn.testcase.other;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;
import com.squareup.spoon.Spoon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.logging.Logger;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.page.AccountPage;
import cn.page.App;
import cn.page.Constant;
import cn.page.MePage;

/**
 * Created by user on 2016/11/05   .
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class LoginCase extends VP2 {
    Logger logger = Logger.getLogger(LoginCase.class.getName());

    @Before
    public void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    @Test
    /*
    case 1：login界面元素检查
    1.检查返回按钮(点击返回键是否起作用）
    2.login，Forgot password
    3.输入框提示文字“Mobile Number or Email”“Password”其中“Password”无法查看
     */
    public void testCheckLoginInterface() throws UiObjectNotFoundException{
        AccountAction.logOutAccount();
        AccountAction.navToLogin();
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        if (getUiObjectByText("邮箱、手机号、sioeye id").exists()){
            logger.info("输入手机号或者邮箱存在");
            if (getUiObjectByText("登录").exists()){
                logger.info("Login存在");
                if (getUiObjectByText("忘记密码？").exists()){
                    logger.info("Forgot password存在");
                        clickByClass("android.widget.ImageView", 0);
                        //点击返回键；检查返回键功能是否正常
                        if (getUiObjectByText("登录").exists()) {
                            logger.info("返回键正常");
                            clickByText("登录");
                        } else {
                            Spoon.screenshot("LoginInterface", "返回键无功能");
                            Assert.fail("返回键无功能");}
                }
                else {Spoon.screenshot("LoginInterface","Forgot password不存在");
                    Assert.fail("Forgot password不存在");}
            }
            else {Spoon.screenshot("LoginInterface","Login不存在");
                Assert.fail("Login不存在");}
        }
        else {
            Spoon.screenshot("LoginInterface","Mobile Number or Email不存在");
            Assert.fail("Mobile Number or Email不存在");
        }
    }
    @Test
    /*
    case 2：只输入账号，或者输入密码；点击login
    1.有账号 无密码
    2.无账号无密码
    3.无账号 有密码
    无页面变化
     */
    public void testLoseUseNameOrPassword() throws UiObjectNotFoundException{
        AccountAction.logOutAccount();
        AccountAction.navToLogin();
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        AccountAction.justLogIn("yyun@123.com","");
        if (!getUiObjectByText("登录").exists()){
            Assert.fail("无密码点击登陆后页面变化");
        }
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        AccountAction.justLogIn("","");
        if (!getUiObjectByText("登录").exists()) {
            Assert.fail("无账号密码点击登陆后页面变化");
        }
        AccountAction.justLogIn("","eye1321");
        if (!getUiObjectByText("登录").exists()) {
            Assert.fail("无账号点击登陆后页面变化");
        }
        AccountAction.justLogIn(Constant.getUserName(),Constant.getPassword());
        waitTime(5);
        if (!getObjectById(MePage.ID_MAIN_TAB_ME).exists()){
            Assert.fail("登陆失败");}
    }
    @Test
    /*
    case 3：输入错误的账号或者密码后点击登录
    1.输入错误的密码，点击login
    2.错误的账号和密码。点击login
    无页面变化
     */
    public void testErrorUseNameOrPassword() throws UiObjectNotFoundException{
        AccountAction.logOutAccount();
        AccountAction.navToLogin();
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        AccountAction.justLogIn("eye@163.com","12012012012012558");
        waitTime(2);
        if (!getUiObjectByText("登录").exists()) {
            Assert.fail("错误密码点击登陆后页面变化");
        }
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        AccountAction.justLogIn("e11111111ye163.com","1212558");
        waitTime(2);
        if (!getUiObjectByText("登录").exists()) {
            Assert.fail("错误账号点击登陆后页面变化");}
    }
    @Test
    /*
    case 4：密码可见和不可见
    1.在密码不可见情况下输入密码
    2.打开可见并检查
    3.再重新输入密码并检查是否为可见
    4.关闭可见并检查
     */
    public void testVisibleAndUnVisiblePassword() throws UiObjectNotFoundException {
        AccountAction.logOutAccount();
        AccountAction.navToLogin();
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        getObjectById(AccountPage.LOGIN_ET_INPUT_PASSWORD).setText("123abcDEF!@");
        clickById(AccountPage.ACCOUNT_PASSWORD_SHOW_BTN);
        if (!getUiObjectByText("123abcDEF!@").exists()){
            Spoon.screenshot("Password_Visible_Fail","开启密码可见失败");
            Assert.fail("开启密码可见失败");
        }
        getObjectById(AccountPage.LOGIN_ET_INPUT_PASSWORD).setText("#*MOpl0oF!@");
        if (!getUiObjectByText("#*MOpl0oF!@").exists()){
            Spoon.screenshot("Password_Visible_Fail","开启密码可见后再输入可见失效");
            Assert.fail("开启密码可见后再输入可见失效");
        }
        clickById(AccountPage.ACCOUNT_PASSWORD_SHOW_BTN);
        if (getUiObjectByText("#*MOpl0oF!@").exists()){
            Spoon.screenshot("Password_Unvisible_Fail","关闭密码可见失败");
            Assert.fail("关闭密码可见失败");
        }
    }
}


