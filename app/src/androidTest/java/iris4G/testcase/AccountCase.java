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
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
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
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();

        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Live&Save");
        CameraAction.openCompoundButton("Live&Save");
        Iris4GAction.ScrollViewByText("Account");
        clickByText("Account");
        AccountAction.loginAccount("tyokyo@126.com", "123456789");
        boolean login = AccountAction.isLoginSuccess();
        if (login) {
            logger.info(" 账号登陆成功");
        }else {
            logger.info(" 账号登陆失败");
        }
    }
}
