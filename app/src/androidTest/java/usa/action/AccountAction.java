package usa.action;

import android.os.Environment;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.squareup.spoon.Spoon;

import org.junit.Assert;

import java.io.File;
import java.util.Random;
import java.util.zip.Deflater;

import ckt.base.VP2;
import ckt.tools.Property;
import usa.page.Account;
import usa.page.App;
import usa.page.Constant;
import usa.page.Me;

/**
 * Created by admin on 2016/11/2.
 **/
public class AccountAction extends VP2{
    /**
     * 启动app,判断是否登录,已经登录返回TRUE，并返回到初始化设备状态
     */
    public static boolean judgelogin() throws UiObjectNotFoundException {
        initDevice();
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        clickByText("Me");
        //判断是否登录
        if (gDevice.findObject(new UiSelector().text("Login")).exists()) {
            System.out.println("you haven't login");
            initDevice();
            return false;
        } else {
            System.out.println("you have logined");
            clickByText("Discover");
            initDevice();
            return true;
        }
    }

    /**
     *产生length个随机数（数字和字母),用于随机搜索关键字
     */
    public  static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * login and back to desktop
     */
    public static void login(String username, String password) throws UiObjectNotFoundException {
        initDevice();
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        clickByText("Me");
        clickByText("Login");
        UiObject us = gDevice.findObject(new UiSelector().resourceId("com.sioeye.sioeyeapp:id/et_input_username"));
        UiObject ps = gDevice.findObject(new UiSelector().resourceId("com.sioeye.sioeyeapp:id/et_input_password"));
        us.clearTextField();
        us.setText(username);
        ps.setText(password);
        clickByText("Login");
        waitTime(4);
        if (gDevice.findObject(new UiSelector().text("Login")).exists()) {
            System.out.println("log in failed");
            initDevice();
        } else {
            System.out.println("log in succeed");
            clickByText("Discover");
            initDevice();
        }
    }

    /**
     * logout and back to desktop
     *
     * */
    public static void logout() throws UiObjectNotFoundException{
        initDevice();
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        clickByText("Me");
        clickByText("Settings");
        clickById("com.sioeye.sioeyeapp:id/tv_logout");
        clickById("com.sioeye.sioeyeapp:id/delete_ok");
        if(getUiObjectByText("Change password").exists()){
            System.out.println("Log Out Failed,Please check it");
        }else {
            System.out.println("you have log out successfully");
        }
        initDevice();
    }
    //注销账号
    /*如果当前处于登录状态，注销账号
    如果当前已处于注销状态，退出
    * */
    public static  void logOutAccount(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        clickById(Me.ID_MAIN_TAB_ME);
        waitTime(2);
        if (getUiObjectByText("Login").exists()){

        }else{
            clickById(Me.SETTINGS_USER_MAIN);
            clickById(Account.LOG_OUT);
            clickById(Account.LOG_OUT_OK);
            //wait logout
            waitTime(2);
            waitUntilGone(Account.ACCOUNT_LOGOUT,30);
            Spoon.screenshot("log_out");
        }
    }
    //登录账号
    public static void logInAccount(String username,String password) throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        clickById(Me.ID_MAIN_TAB_ME);
        if (text_exists("Login")){
            clickByText("Login");
            getObjectById(Account.LOGIN_ET_INPUT_USERNAME).setText(username);
            getObjectById(Account.LOGIN_ET_INPUT_PASSWORD).setText(password);
            clickById(Account.LOGIN_ET_SIGN_UP_BTN);
            waitUntilFind(Me.ID_MAIN_TAB_ME,20);
        }
    }
    //进入登录界面
    public static void navToLogin() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        clickById(Me.ID_MAIN_TAB_ME);
        clickByText("Login");
    }
    //进入Sign Up界面-mobile
    public static void navToSignUp_ByMobile() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        clickById(Me.ID_MAIN_TAB_ME);
        clickByText("Sign Up");
    }
    //进入Sign Up界面-mobile
    public static void navToSignUp_ByEmail() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        clickById(Me.ID_MAIN_TAB_ME);
        clickByText("Sign Up");
        clickByText("Use Your Email Address");
    }
    //判断是否登录成功
    public static void inLogin() throws UiObjectNotFoundException {
        boolean login = true;
        clickById(Me.ID_MAIN_TAB_ME);
        if (text_exists("Login")){
            clickByText("Login");
            getObjectById(Account.LOGIN_ET_INPUT_USERNAME).setText(Constant.userName);
            getObjectById(Account.LOGIN_ET_INPUT_PASSWORD).setText(Constant.passwd);
            clickById(Account.LOGIN_ET_SIGN_UP_BTN);
            waitUntilFind(Me.ID_MAIN_TAB_ME,20);
        }
    }
    //仅仅一个登陆的动作
    public static void justLogIn(String username,String password) throws UiObjectNotFoundException {
        getObjectById(Account.LOGIN_ET_INPUT_USERNAME).setText(username);
        waitTime(1);
        Spoon.screenshot("inputusername", "输入账号");
        getObjectById(Account.LOGIN_ET_INPUT_PASSWORD).setText(password);
        waitTime(1);
        Spoon.screenshot("inputpassword", "输入密码");
        clickById(Account.LOGIN_ET_SIGN_UP_BTN);
    }
    public static String getUserName(int options){
        String returnstring=null;
        String config= Environment.getExternalStorageDirectory()+ File.separator+"config.properties";
        switch (options){
            case 1:{
                logger.info("switch_case1");
                String userName= Property.getValueByKey(config,"user_name");
                returnstring =userName;}
                break;
            case 2:{
                String phoneNumber= Property.getValueByKey(config,"phone_number");
                returnstring =phoneNumber;}
                break;
            case 3:{
                String sioeyeID= Property.getValueByKey(config,"sioeye_id");
                returnstring =sioeyeID;}
                break;
            default:logger.info("getUserName方法传参错误，1代表邮箱名、2代表电话号码、3代表sioeyeID");
                returnstring="error";
                break;
            }
        return returnstring;
    }
    public static String getPassword(int options) {
        String returnstring=null;
        String config = Environment.getExternalStorageDirectory() + File.separator + "config.properties";
        switch (options) {
            case 1: {
                String password = Property.getValueByKey(config, "user_password");
                returnstring = password;
            }
            break;
            case 2: {
                String phonepassword = Property.getValueByKey(config, "phone_password");
                returnstring = phonepassword;
            }
            break;
            case 3: {
                String sioeyepassword = Property.getValueByKey(config, "sioeye_password");
                returnstring = sioeyepassword;
            }
            break;
            default:
                logger.info("getPassword方法传参错误，1代表邮箱密码、2代表电话号码密码、3代表sioeyeID密码");
                returnstring = "error";
                break;
        }
        return returnstring;
    }
    public static void judgeLoginAction() throws UiObjectNotFoundException{
        if (getUiObjectByText("Login").exists()) {
            Spoon.screenshot("LoginFaied", "登陆失败");
            Assert.fail("登陆失败");
        } else {
                logger.info("登陆成功");
        }
    }
}
