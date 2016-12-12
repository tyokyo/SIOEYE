package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.uiautomator.UiObject;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;
import iris4G.page.NavPage;
import usa.page.Account;

/**
 * Created by jiali.liu on 2016/12/10.
 */
@RunWith(JUnit4.class)
@SdkSuppress(minSdkVersion = 16)

public class OpenLocationCase extends VP2{
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    /**打开直播Location功能*/
    @Test
    public void Test() throws Exception {
     //   CameraAction.navToLocation();
        CameraAction.openCompoundButton("Live&Location");
        //PS：需要添加location开关状态验证方法
    }
    /*开启location后，进行直播*/
    @Test
    public void TestLiveAndLocation() throws Exception {
      //  CameraAction.navToLocation();
        CameraAction.openCompoundButton("Live&Location");
        if (AccountAction.isLogin()){
            Iris4GAction.cameraKey();
        }else {
            CameraAction.navToAccount();
            String username= Constant.getUserName("email");
            String password= Constant.getPassword("email_password");
            AccountAction.loginAccount(username,password);
            gDevice.pressBack();
            Iris4GAction.cameraKey();
        }
    }
}
