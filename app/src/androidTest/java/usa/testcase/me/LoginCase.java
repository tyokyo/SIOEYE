package usa.testcase.me;

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
import usa.action.AccountAction;
import usa.page.Account;
import usa.page.App;


/**
 * Created by user on 2016/11/05   .
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class LoginCase extends VP2 {
    Logger logger = Logger.getLogger(LoginCase.class.getName());
    @Before
    public void setup() {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);}
    @Test
    /*
    case 1：login界面元素检查
    1.检查返回按钮(点击返回键是否起作用）
    2.login，Forgot password
    3.输入框提示文字“Mobile Number or Email”“Password”其中“Password”无法查看
     */
    public void testCheckLoginInterface() throws UiObjectNotFoundException{
        if (AccountAction.judgelogin()){AccountAction.logout();}
        AccountAction.navToLogin();
        clearText(Account.LOGIN_ET_INPUT_USERNAME);
        if (getUiObjectByText("Mobile Number or Email").exists()){
            logger.info("Mobile Number or Email存在");
            if (getUiObjectByText("Login").exists()){
                logger.info("Login存在");
                if (getUiObjectByText("Forgot password?").exists()){
                    logger.info("Forgot password存在");
                        clickByClass("android.widget.ImageView", 0);
                        //点击返回键；检查返回键功能是否正常
                        if (getUiObjectByText("Sign Up").exists()) {
                            logger.info("返回键正常");
                            clickByText("Login");
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
        if (AccountAction.judgelogin()){AccountAction.logout();}
        AccountAction.navToLogin();
        clearText(Account.LOGIN_ET_INPUT_USERNAME);
        AccountAction.justLogIn("yyun@123.com","");
        if (!getUiObjectByText("Login").exists()){
            Assert.fail("无密码点击登陆后页面变化");
        }
       clearText(Account.LOGIN_ET_INPUT_USERNAME);
        AccountAction.justLogIn("","");
        if (!getUiObjectByText("Login").exists()) {
            Assert.fail("无账号密码点击登陆后页面变化");
        }
        AccountAction.justLogIn("","eye1321");
        if (!getUiObjectByText("Login").exists()) {
            Assert.fail("无账号点击登陆后页面变化");
        }
    }
    @Test
    /*
    case 3：输入错误的账号或者密码后点击登录
    1.输入错误的密码，点击login
    2.错误的账号和密码。点击login
    无页面变化
     */
    public void testErrorUseNameOrPassword() throws UiObjectNotFoundException{
        if (AccountAction.judgelogin()){AccountAction.logout();}
        AccountAction.navToLogin();
        clearText(Account.LOGIN_ET_INPUT_USERNAME);
        AccountAction.justLogIn("eye@163.com","12012012012012558");
        waitTime(2);
        if (!getUiObjectByText("Login").exists()) {
            Assert.fail("错误密码点击登陆后页面变化");
        }
        clearText(Account.LOGIN_ET_INPUT_USERNAME);
        AccountAction.justLogIn("e11111111ye163.com","1212558");
        waitTime(2);
        if (!getUiObjectByText("Login").exists()) {
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
    public void testVisibleAndUnvisiblePassword() throws UiObjectNotFoundException {
        if (AccountAction.judgelogin()) {AccountAction.logout();}
        AccountAction.navToLogin();
        clearText(Account.LOGIN_ET_INPUT_USERNAME);
        getObjectById(Account.LOGIN_ET_INPUT_PASSWORD).setText("123abcDEF!@");
        clickById(Account.VISIBLE_AND_UNVISIBLE_PASSWORD);
        if (!getUiObjectByText("123abcDEF!@").exists()){
            Spoon.screenshot("Password_Visible_Fail","开启密码可见失败");
            Assert.fail("开启密码可见失败");
        }
        getObjectById(Account.LOGIN_ET_INPUT_PASSWORD).setText("#*MOpl0oF!@");
        if (!getUiObjectByText("#*MOpl0oF!@").exists()){
            Spoon.screenshot("Password_Visible_Fail","开启密码可见后再输入可见失效");
            Assert.fail("开启密码可见后再输入可见失效");
        }
        clickById(Account.VISIBLE_AND_UNVISIBLE_PASSWORD);
        if (getUiObjectByText("#*MOpl0oF!@").exists()){
            Spoon.screenshot("Password_Unvisible_Fail","关闭密码可见失败");
            Assert.fail("关闭密码可见失败");
        }
    }
    @Test
    /*
    case 5:输入超出的账号和密码
    1.输入120个字符账号
    2.输入120个密码
    3.输入超过120个字符的账号和密码
    4.
     */
    public void testLongUseNameAndPassword() throws UiObjectNotFoundException{
        if (AccountAction.judgelogin()) {AccountAction.logout();}
        AccountAction.navToLogin();
        clearText(Account.LOGIN_ET_INPUT_USERNAME);
        clearText(Account.LOGIN_ET_INPUT_PASSWORD);
        String usename120=AccountAction.getRandomEmail(110,111);
        logger.info(usename120);
        String password120=AccountAction.getStringRandom(120);
        logger.info(usename120);
        AccountAction.justLogIn(usename120,password120);
        if (!getUiObjectByText("Login").exists()) {
            Assert.fail("错误账号点击登陆后页面变化");}
    }
    @Test
    /*
    case 6:账号密码超过120个字符
    1.尝试账号输入130个字符（实际是无法输入进去的）
    2.尝试密码输入130个字符（实际是无法输入进去的）
    3.点击登录
    4.check无页面异常
     */
    public void testSupLongUseNameAndPassword() throws UiObjectNotFoundException{
        if (AccountAction.judgelogin()) {AccountAction.logout();}
        AccountAction.navToLogin();
        clearText(Account.LOGIN_ET_INPUT_USERNAME);
        clearText(Account.LOGIN_ET_INPUT_PASSWORD);
        String useName130=AccountAction.getRandomEmail(120,130);
        String password130=AccountAction.getStringRandom(130);
        AccountAction.justLogIn(useName130,password130);
        if (!getUiObjectByText("Login").exists()) {
            Assert.fail("错误账号点击登陆后页面变化");}
    }
    @Test
    /*
    case 7:使用邮箱登陆
    使用邮箱账户登录
    需要提前将邮箱账号及密码保存在根目录下config.properties文件中,对应user_name，user_password
    即：    user_name=XXXX@xxx.com
            user_password=xxxx
    检查是否登录成功
     */
    public void testEmailLogin() throws UiObjectNotFoundException{
        if (AccountAction.judgelogin()) {AccountAction.logout();}
        String usename=null;
        String password=null;
        usename=AccountAction.getUserName(1);
        if (!usename.equals("error")&&usename.length()>0&&usename!=null) {
            logger.info(usename);
            password = AccountAction.getPassword(1);
            if (!password.equals("error")&&password.length()>0&&password!=null){
                logger.info(password);
                AccountAction.navToLogin();
                AccountAction.justLogIn(usename, password);
                AccountAction.judgeLoginAction();
            }
            else
            {Assert.fail("本地配置文件中未找到user_password，或者getPassword方法传参非1-3，请检查");}
        }
        else
            {Assert.fail("本地配置文件中未找到user_name，或者getUserName方法传参非1-3，请检查");}
    }
    @Test
    /*
    case 8 :sioeye id 登陆
    sioeye id 登陆
    需要提前将sioeye id及密码保存在根目录下config.properties文件中,对应sioeye_id，sioeye_password
    即：    sioeye_id=xxxxx
            sioeye_password=xxxxx
    检查是否登录成功
     */
    public void testSioeyeIDLogin() throws UiObjectNotFoundException{
        if (AccountAction.judgelogin()) {AccountAction.logout();}
        String usename=null;
        String password=null;
        usename=AccountAction.getUserName(3);
        if (!usename.equals("error")&&usename.length()>0&&usename!=null) {
            logger.info(usename);
            password = AccountAction.getPassword(3);
            if (!password.equals("error")&&password.length()>0&&password!=null){
                logger.info(password);
                AccountAction.navToLogin();
                AccountAction.justLogIn(usename, password);
                AccountAction.judgeLoginAction();
            }
            else
            {Assert.fail("本地配置文件中未找到sioeye_password，或者getPassword方法传参非1-3，请检查");}
        }
        else
            {Assert.fail("本地配置文件中未找到sioeye_id，或者getUserName方法传参非1-3，请检查");}
    }
    @Test
    /*
    case 9:电话号码登陆
    使用电话号码登录
    需要提前将电话号码及密码保存在根目录下config.properties文件中,对应phone_number，phone_password
    即：    phone_number=xxxxx
            phone_password=xxxxx
    检查是否登录成功
     */
    public void testPhoneNumberLogin() throws UiObjectNotFoundException{
        if (AccountAction.judgelogin()) {AccountAction.logout();}
        String usename=null;
        String password=null;
        usename=AccountAction.getUserName(2);
        if (!usename.equals("error")&&usename.length()>0&&usename!=null) {
            logger.info(usename);
            password = AccountAction.getPassword(2);
            if (!password.equals("error")&&password.length()>0&&password!=null){
                logger.info(password);
                AccountAction.navToLogin();
                AccountAction.justLogIn(usename, password);
                AccountAction.judgeLoginAction();
            }
            else
            {Assert.fail("本地配置文件中未找phone_password，或者getPassword方法传参非1-3，请检查");}
        }
        else
        {Assert.fail("本地配置文件未找到phone_number，或者getUserName方法传参非1-3，请检查");}
    }
//    @Test
//    /*
//    case 10 Facebook三方登陆
//    Facebook三方登陆
//    检查是否登录成功
//     */
//    public void testFacebookLogin() throws UiObjectNotFoundException{
//
//    }
//    @Test
//    /*
//    case 11 Twitter三方登陆
//    Twitter三方登陆
//    检查是否登录成功
//     */
//    public void testTwitterLogin() throws UiObjectNotFoundException{
//
//    }
//    @Test
//    /*
//    case 12 Googgle三方登陆
//    Googgle三方登陆
//    检查是否登录成功
//     */
//    public void testGooggleLogin() throws UiObjectNotFoundException{
//
//    }
}


