package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import com.squareup.spoon.Spoon;
import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.util.logging.Logger;
import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.action.SettingAction;
import iris4G.page.Iris4GPage;

/**
 * @Author elon
 * @Description 机身账号登录
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class AccountCase extends VP2{
    Logger logger = Logger.getLogger(AccountCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void testLogin() throws Exception {
        gDevice.pressHome();
        //清除app数据  包括登录的账号
        Iris4GAction.pmClear();
        //启动 camera
        Iris4GAction.startCamera();

        String useName= Constant.getUserName();
        String password=Constant.getPassword();
        //登录账号
        AccountAction.loginAccount(useName,password);
        //打开live&save 开关
        SettingAction.clickLiveAndSave();
    }
}
