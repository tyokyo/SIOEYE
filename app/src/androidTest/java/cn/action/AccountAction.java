package cn.action;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import java.util.Random;
import ckt.base.VP2;
import cn.page.Account;
import cn.page.App;
import cn.page.Me;


/**
 * Created by admin on 2016/11/2.
 **/
public class AccountAction extends VP2{
    /**
     *
     *启动app,判断是否登录,已经登录返回TRUE，并返回到初始化设备状态
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
     *login and back to desktop
     *
     *
     * */
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
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
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
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.SETTINGS_USER_MAIN);
        if (getUiObjectByText("Login").exists()){

        }else{
            clickById(Account.LOG_OUT);
            clickById(Account.LOG_OUT_OK);
            //wait logout

        }
    }

}
