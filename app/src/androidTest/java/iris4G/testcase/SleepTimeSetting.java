package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.Iris4GAction;

/**
 * @Author caiBin
 * @Description
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class SleepTimeSetting extends VP2{
    Logger logger = Logger.getLogger(SleepTimeSetting.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void test() throws Exception {
        gDevice.pressHome();
        gDevice.pressMenu();

        gDevice.waitForWindowUpdate("com.android.settings", 5000);
        clickByText("Device");
        Iris4GAction.ScrollViewByText("Display");
        clickByText("Display");
        clickByText("Sleep");
        Iris4GAction.ScrollViewByText("Never");
        clickByText("Never");

        gDevice.pressHome();
        gDevice.pressMenu();
        clickByText("Connection");
        clickByText("Wi-Fi");
        waitTime(3);
        UiObject addNewNetWork = getObjectByTextContains("Add new network...");
        if (addNewNetWork.exists()) {
            clickById("android:id/switchWidget");
            addNewNetWork.waitUntilGone(10000);
        }
        Iris4GAction.ScrollViewByText("CKT");
        clickByText("CKT");
        UiObject password = getObjectById("com.android.settings:id/password");
        if (password!=null&&password.exists()) {
            password.setText("ck88888!");
        }
        if (text_exists("Connect")) {
            clearText("Connect");
        }
    }
}
