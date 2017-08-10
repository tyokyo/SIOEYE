package cn.testcase.other;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.page.AccountPage;
import cn.page.App;
import cn.page.Constant;

/**
 *
 * change by yun.yang on 2016/12/25.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class LoginCase extends VP2 {
    Logger logger = Logger.getLogger(LoginCase.class.getName());

    @AfterClass
    public static void loginAccountWithDefault() throws Exception {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        initDevice();
        AccountAction.inLogin();
    }

    @Before
    public void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.logOutAccount();
        AccountAction.navToLogin();
    }

    @Test
    @SanityTest
    @PerformanceTest
    /*
    case 1：login界面元素检查
    1.检查返回按钮(点击返回键是否起作用）
    2.login，Forgot password
    3.输入框提示文字“Mobile Number or Email”“Password”其中“Password”无法查看
     */
    public void testCheckLoginInterface() throws UiObjectNotFoundException{
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        waitTime(2);
        if (getUiObjectByText("Email, phone number, Sioeye ID").exists()){
            logger.info("输入手机号或者邮箱存在");
            if (getUiObjectByText("Log in").exists()){
                logger.info("Login Exist");
                if (getUiObjectByText("Forgot your password?").exists()){
                    logger.info("Forgot password Exist");
                    clickByClass("android.widget.ImageView", 0);
                    //点击返回键；检查返回键功能是否正常
                    if (getUiObjectByText("Log in").exists()) {
                        logger.info("back icon is ok");
                        clickByText("Log in");
                    } else {
                        Spoon.screenshot("LoginInterface", "返回键无功能");
                        Assert.fail("Back icon can't back");}
                }
                else {Spoon.screenshot("LoginInterface","Forgot password不存在");
                    Assert.fail("Forgot password ");}
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
    @SanityTest
    @PerformanceTest
    /*
    case 2：未输入账号和密码；点击login
    无账号无密码
    无页面变化
     */
    public void testLoginByLoseUseNameAndPassword() throws UiObjectNotFoundException {
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        AccountAction.justLogIn("", "");
        if (!getUiObjectByText("Log in").exists()) {
            Assert.fail("无账号和密码点击登陆后页面变化");
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    /*
    case 3：输入正确的账号，未输入密码；点击login
    账号通过读取本地config.properties文件中email；
    无页面变化
     */
    public void testLoginByLosePassword() throws UiObjectNotFoundException {
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        String userName = Constant.getUserName("email");
        AccountAction.justLogIn(userName, "");
        if (!getUiObjectByText("Log in").exists()) {
            Assert.fail("无密码点击登陆后页面变化");
        }
    }
    @Test
    /*
    case 4：未输入账号和正确的密码；点击login
    无页面变化
     */
    public void testLoseUseName() throws UiObjectNotFoundException{
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        String userPassword = Constant.getUserName("email_password");
        AccountAction.justLogIn("",userPassword);
        if (!getUiObjectByText("Log in").exists()){
            Assert.fail("无账号点击登陆后页面变化");
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    /*
    case 5：密码可见和不可见
    1.在密码不可见情况下输入密码
    2.打开可见并检查
    3.再重新输入密码并检查是否为可见
    4.关闭可见并检查
     */
    public void testVisibleAndUnVisibleLoginPassword() throws UiObjectNotFoundException {
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        String userPassword=Constant.randomStringGenerator(20);
        getObjectById(AccountPage.LOGIN_ET_INPUT_PASSWORD).setText(userPassword);
        clickById(AccountPage.ACCOUNT_PASSWORD_SHOW_BTN);
        if (!getUiObjectByText(userPassword).exists()){
            Spoon.screenshot("FailedOpenPasswordVisible","开启密码可见失败");
            Assert.fail("FailedOpenPasswordVisible");
        }
        userPassword=Constant.randomStringGenerator(22);
        getObjectById(AccountPage.LOGIN_ET_INPUT_PASSWORD).setText(userPassword);
        if (!getUiObjectByText(userPassword).exists()){
            Spoon.screenshot("Password_Visible_Fail","开启密码可见后再输入可见失效");
            Assert.fail("开启密码可见后再输入可见失效");
        }
        clickById(AccountPage.ACCOUNT_PASSWORD_SHOW_BTN);
        if (getUiObjectByText(userPassword).exists()){
            Spoon.screenshot("Password_Unvisible_Fail","关闭密码可见失败");
            Assert.fail("FailedClosePasswordVisible");
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    /*
    case 6：输入不存在的邮箱账号和密码后点击登录
    1.输入不存在的账号和密码
    2.点击login
    无页面变化
     */
    public void testLoginByErrorEmailAndPassword() throws UiObjectNotFoundException{
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        String userName=Constant.randomEmail(10);
        String userPassword=Constant.randomStringGenerator(20);
        AccountAction.justLogIn(userName,userPassword);
        waitTime(2);
        if (!getUiObjectByText("Log in").exists()) {
            Assert.fail("输入不存在的邮箱账号和密码点击登陆后页面有变化");}
    }
    @Test
    /*
    case 7：输入不存在的电话号码账号和密码后点击登录
    1.输入不存在的账号和密码
    2.点击login
    无页面变化
     */
    public void tesLoginBytErrorPhoneNumberAndPassword() throws UiObjectNotFoundException{
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        String userName=Constant.randomPhoneNumber();
        String userPassword=Constant.randomStringGenerator(20);
        AccountAction.justLogIn(userName,userPassword);
        waitTime(2);
        if (!getUiObjectByText("Log in").exists()) {
            Assert.fail("使用不存在的电话号码账号和密码点击登陆后页面变化");}
    }
    @Test
    @SanityTest
    @PerformanceTest
    /*
    case 8：输入正确的邮箱账号和错误的密码后点击登录
    1.输入正确的账号和错误的密码密码
    2.账号通过读取本地config.properties文件中email；错误密码通过随机字符串产生
    3.点击login
    无页面变化
     */
    public void testLoginByEmailAndErrorPassword() throws UiObjectNotFoundException {
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        String userName = Constant.getUserName("email");
        String userPassword = Constant.randomStringGenerator(20);
        AccountAction.justLogIn(userName, userPassword);
        waitTime(2);
        if (!getUiObjectByText("Log in").exists()) {
            Assert.fail("错误的密码点击登陆后页面变化");
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    /*
    case 9：输入正确的电话号码账号和错误的密码后点击登录
    1.输入正确的账号和错误的密码密码
    2.账号通过读取本地config.properties文件中email；错误密码通过随机字符串产生
    3.点击login
    无页面变化
     */
    public void testLoginByPhoneNumberAndErrorPassword() throws UiObjectNotFoundException {
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        String userName = Constant.getUserName("phone_number");
        String userPassword = Constant.randomStringGenerator(20);
        AccountAction.justLogIn(userName, userPassword);
        waitTime(2);
        if (!getUiObjectByText("Log in").exists()) {
            Assert.fail("错误密码点击登陆后页面变化");
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    /*
    case 10：使用email登录
    读取本地config.properties文件中email和email_password来进行登录
    请提前将config.properties保存在本地根目录；格式：email=xxxxx@xx.xx；email_password=xxx
    登录成功
     */
    public void testLoginByEmail() throws UiObjectNotFoundException {
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        String userName = Constant.getUserName("email");
        String userPassword = Constant.getUserName("email_password");
        AccountAction.justLogIn(userName,userPassword );
        waitTime(3);
        if (getUiObjectByText("Log in").exists()) {
            Assert.fail("LoginFailByEmail");
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    /*
    case 11：使用phone登录
    读取本地config.properties文件中phone_number和phone_password来进行登录
    请提前将config.properties保存在本地根目录；格式：phone_number=1xxx；phone_password=xxx
    登录成功
     */
    public void testLoginByPhoneNumber() throws Exception {
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        String userName = Constant.getUserName("phone_number");
        String userPassword = Constant.getUserName("phone_password");
        AccountAction.justLogIn(userName,userPassword );
        waitTime(3);
        if (getUiObjectByText("Log in").exists()) {
            Assert.fail("LoginFailByPhoneNumber");
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    /*
    case 12：使用sioEye登录
    读取本地config.properties文件中phone_number和phone_password来进行登录
    请提前将config.properties保存在本地根目录；格式：sioeye_id=xxx；sioeye_password=xxx
    登录成功
     */
    public void testLoginBySioEeyID() throws UiObjectNotFoundException {
        clearText(AccountPage.LOGIN_ET_INPUT_USERNAME);
        String userName = Constant.getUserName("sioeye_id");
        String userPassword = Constant.getUserName("sioeye_password");
        AccountAction.justLogIn(userName,userPassword );
        waitTime(3);
        if (getUiObjectByText("Log in").exists()) {
            Assert.fail("LoginFailBySioEeyId");
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    /*
    case 13：使用邮箱找回密码
    1.读取本地config.properties文件中email
    请提前将config.properties保存在本地根目录
    结果：提示重置密码链接已经发送到注册邮箱中
    2.为注册过的邮箱
    随机产生一个邮箱
    结果：无“提示重置密码链接已经发送到注册邮箱中”
     */
    public void testForgotPasswordByEmailNotExist() throws UiObjectNotFoundException {
        getObjectByTextContains("Forgot your password?").click();
        String email=Constant.randomEmail(26);
        getObjectById(AccountPage.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT).setText(email);
        waitTime(1);
        Spoon.screenshot("resetPassword");
        getObjectById(AccountPage.SIGN_UP_CONTINUE).click();
        //无重置密码的链接已经发送到你注册的邮箱
        if (getObjectByTextContains("The reset password email has been sent").exists()){
            Spoon.screenshot("invalidEmailCanSentForgotPasswordEmail");
            Assert.fail("invalidEmailCanSentForgotPasswordEmail");
        }else {

        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    public void testForgotPasswordByEmailExist() throws UiObjectNotFoundException {
        getObjectByTextContains("Forgot your password?").click();
        String email=Constant.getUserName("email");
        Spoon.screenshot("resetPassword");
        getObjectById(AccountPage.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT).setText(email);
        waitTime(1);
        Spoon.screenshot("inputPassword",email);
        getObjectById(AccountPage.SIGN_UP_CONTINUE).click();
        waitUntilFindTextContains("Reset your password",10000);
        Spoon.screenshot("resetPassword");
        //无重置密码的链接已经发送到你注册的邮箱
        if (getObjectByTextContains("The reset password email has been sent").exists()){
            clickById(AccountPage.LOG_OUT_I_KNOW);
        }else {
            Assert.fail("reset");
        }
    }
}
