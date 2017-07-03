package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import org.hamcrest.Asst;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;

/**
 * @Author yyun2016
 * @Description 机身账号登录
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class AccountCase extends VP2{
    Logger logger = Logger.getLogger(AccountCase.class.getName());
    @AfterClass
    public static void loginAccountWithDefault() throws Exception {
        initDevice();
        //清除app数据  包括登录的账号
        Iris4GAction.pmClear();
        //启动 camera
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        //登录账号
        AccountAction.loginAccount(useName, password);
    }

    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    /*
    case 1 ：使用SioeyeID登录
    读取本地config.properties文件中SioeyeID和sioeyeID_password来进行登录
    请提前将config.properties保存在本地根目录；格式：sioeye_id=xxx；sioeyeId_password=xxx
    检查是否成功登陆；并发起一个直播
     */
    public void testLoginBySioEyeID() throws Exception{
        if (AccountAction.isLogin()) {AccountAction.logOut();}
        String sioEyeID = Constant.getUserName("sioeye_id");
        String sioEyeIdPassword = Constant.getPassword("sioeye_password");
        CameraAction.navToAccount();
        AccountAction.loginAccount(sioEyeID,sioEyeIdPassword);
        waitTime(3);
        if (!AccountAction.isLogin()) {
            Assert.fail("Login Failed");}
        else {
            Iris4GAction.cameraKey();
            CameraAction.checkLiveStatus(1);
            if (!CameraAction.checkLiveSuccess()){
                Assert.fail("live failed");
            }
            CameraAction.cameraRecordTime();
            Iris4GAction.cameraKey();
        }
    }
    @Test
    /*
    case 2 ：使用phone_number登录
    读取本地config.properties文件中phone_number和phone_password来进行登录
    请提前将config.properties保存在本地根目录；格式：phone_number=xxx；phone_password=xxx
    检查是否成功登陆；并发起一个直播
     */
    public void testLoginByPhoneNumber() throws Exception{
        if (AccountAction.isLogin()) {AccountAction.logOut();}
        String userName = Constant.getUserName("phone_number");
        String password = Constant.getPassword("phone_password");
        CameraAction.navToAccount();
        AccountAction.loginAccount(userName,password);
        waitTime(2);
        if (!AccountAction.isLogin()) {
            Assert.fail("Login Failed");}
        else {
            Iris4GAction.cameraKey();
            CameraAction.checkLiveStatus(1);
            if (!CameraAction.checkLiveSuccess()){
                Assert.fail("live failed");
            }
            CameraAction.cameraRecordTime();
            Iris4GAction.cameraKey();
        }
    }
    @Test
    /*
    case 3 ：使用email登录
    读取本地config.properties文件中email和email_password来进行登录
    请提前将config.properties保存在本地根目录；格式：email=xxxxx@xx.xx；email_password=xxx
    检查是否成功登陆；并发起一个直播
     */
    public void testLoginByEmail() throws Exception {
        if (AccountAction.isLogin()) {AccountAction.logOut();}
        String userName = Constant.getUserName("email");
        String password = Constant.getPassword("email_password");
        CameraAction.navToAccount();
        AccountAction.loginAccount(userName,password);
        waitTime(2);
        if (!AccountAction.isLogin()) {
            Assert.fail("Login Failed");}
        else {
            Iris4GAction.cameraKey();
            CameraAction.checkLiveStatus(1);
            if (!CameraAction.checkLiveSuccess()){
                Assert.fail("live failed");
            }
            CameraAction.cameraRecordTime();
            Iris4GAction.cameraKey();
        }
    }
    @Test
    /*
    case4 ：错误的邮箱账号和密码登陆
    邮箱为无效邮箱
    密码通过随机字符串产生
    检查是否登录成功
    */
    public void testLoginByErrorEmailAndPassword() throws Exception {
        if (AccountAction.isLogin()) {AccountAction.logOut();}
        String userName=Constant.randomEmail(20);
        String userPassword=Constant.randomStringGenerator(20);
        CameraAction.navToAccount();
        AccountAction.loginAccount(userName,userPassword);
        waitTime(3);
        if (AccountAction.isLogin()) {
            Assert.fail("Error Email And Password success");
        }
    }
    @Test
    /*
    case5 ：正确的邮箱和错误的密码登陆
    邮箱读取本地config.properties文件中email
    密码为随机字符
    检查是否登录成功
    */
    public void testLoginByErrorPassword() throws Exception {
        if (AccountAction.isLogin()) {AccountAction.logOut();}
        String userName=Constant.getUserName("email");
        String userPassword=Constant.randomStringGenerator(20);
        CameraAction.navToAccount();
        AccountAction.loginAccount(userName,userPassword);
        waitTime(3);
        if (AccountAction.isLogin()) {
            Assert.fail("Error Password Login success");
        }
    }
    @Test
    /*
    case6：错误的邮箱和正确的密码登陆
    邮箱为错误邮箱
    密码为读取本地config.properties文件中email
    检查是否登录成功
    */
    public void testLoginByErrorEmailAndRightPassword() throws Exception {
        if (AccountAction.isLogin()) {AccountAction.logOut();}
        String userName=Constant.randomEmail(20);
        String userPassword=Constant.getPassword("email_password");
        CameraAction.navToAccount();
        AccountAction.loginAccount(userName,userPassword);
        waitTime(3);
        if (AccountAction.isLogin()) {
            Assert.fail("Error Email And Right Password Login success");
        }
    }
    @Test
    /*
    case7 ：错误的电话号码和错误的密码登陆
    电话号码为错误电话
    密码随机字符串
    检查是否登录成功
    */
    public void testLoginByErrorPhoneAndPassword() throws Exception {
        if (AccountAction.isLogin()) {AccountAction.logOut();}
        String userName=Constant.randomPhoneNumber();
        String userPassword=Constant.randomStringGenerator(20);
        CameraAction.navToAccount();
        AccountAction.loginAccount(userName,userPassword);
        waitTime(3);
        if (AccountAction.isLogin()) {
            Assert.fail("Error User Name Login success");
        }
    }
    @Test
    /*
    case8 ：正确的电话号码和错误的密码登陆
    电话号码为错误电话
    密码随机字符串
    检查是否登录成功
    */
    public void testLoginByRightPhoneNumberAndErrorPassword() throws Exception {
        if (AccountAction.isLogin()) {AccountAction.logOut();}
        String userName=Constant.getUserName("phone_number");
        String userPassword=Constant.randomStringGenerator(20);
        CameraAction.navToAccount();
        AccountAction.loginAccount(userName,userPassword);
        waitTime(3);
        if (AccountAction.isLogin()) {
            Assert.fail("Error Phone Number And Right Password Login success");
        }
    }
    @Test
    /**
     *  Case 9:在手动登录界面，按电源键灭屏及亮屏后再登录，按快门键发起直播
     *  能够正常登录且能发起直播
     */
    public void testloginByPowerOnOff() throws Exception {
        UiObject inputObject = getObjectById(Iris4GPage.login_id_input);
        UiObject passwordObject = getObjectById(Iris4GPage.login_password_input);
        String username= Constant.getUserName("email");
        String password= Constant.getPassword("email_password");
        //check Account 是否已登录
        if(AccountAction.isLogin()) {
            CameraAction.cameraSetting();
            Iris4GAction.ScrollViewByText("Account");
            clickByText("Account");
            clickById(Iris4GPage.logout_btn);
        }
            CameraAction.navToAccount();
            inputObject.setText(username);
            passwordObject.setText(password);
            Iris4GAction.powerKey();
            waitTime(5);
            Iris4GAction.powerKey();
            AccountAction.login_btn_login();
            Asst.assertEquals("login succeed",true,AccountAction.isLogin());
            waitTime(3);
            Iris4GAction.cameraKey();
            Asst.assertEquals("Live succeed",true,CameraAction.checkLiveSuccess());
            waitTime(35);
            Iris4GAction.cameraKey();
        }
    }
