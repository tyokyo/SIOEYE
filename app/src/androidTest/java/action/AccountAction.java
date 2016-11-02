package action;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import java.util.Random;

import ckt.base.VP2;
import page.App;

/**
 * Created by admin on 2016/11/2.
 */
public class AccountAction extends VP2{
    /**
     *
     *启动app,判断是否登录,已经登录返回TRUE，并返回到初始化设备状态
     */
    public static boolean judgelogin() throws UiObjectNotFoundException {
        initDevice();
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        clickByText("我");
        //判断是否登录
        if (gDevice.findObject(new UiSelector().text("登录")).exists()) {
            System.out.println("you haven't login");
            initDevice();
            return false;
        } else {
            System.out.println("you have logined");
            clickByText("发现");
            initDevice();
            return true;
        }
    }

    /**
     *产生length个随机数（数字和字母),用于随机搜索关键字
     */
    public String getStringRandom(int length) {

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
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        clickByText("我");
        clickByText("登录");
        UiObject us = gDevice.findObject(new UiSelector().resourceId("cn.sioeye.sioeyeapp:id/et_input_username"));
        UiObject ps = gDevice.findObject(new UiSelector().resourceId("cn.sioeye.sioeyeapp:id/et_input_password"));
        us.clearTextField();
        us.setText(username);
        ps.setText(password);
        clickByText("登录");
        waitTime(4);
        if (gDevice.findObject(new UiSelector().text("登录")).exists()) {
            System.out.println("log in failed");
            initDevice();
        } else {
            System.out.println("log in succeed");
            clickByText("发现");
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
        clickByText("我");
        clickByText("设置");
        clickById("cn.sioeye.sioeyeapp:id/tv_logout");
        clickById("cn.sioeye.sioeyeapp:id/delete_ok");
        if(getUiObjectByText("修改密码").exists()){
            System.out.println("Log Out Failed,Please check it");
        }else {
            System.out.println("you have log out successfully");
        }
        initDevice();
    }
}
