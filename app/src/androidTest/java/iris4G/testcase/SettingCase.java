package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.Iris4GAction;
import iris4G.action.SettingAction;

/**
 * @Author yun.yang
 * @Description
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class SettingCase extends VP2 {
    Logger logger = Logger.getLogger(SettingCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void test() throws Exception {

        Iris4GAction.startSettings();
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
        UiObject passwd = getObjectById("com.android.settings:id/password");
        if (passwd!=null&&passwd.exists()) {
            passwd.setText("ck88888!");
        }
        if (text_exists("Connect")) {
            clickByText("Connect");
        }
        Iris4GAction.stopSettings();
    }
    @Test
    /**case 1
     * yun.yang
     * 录制一段视频检查设置存储是否更新
     */
    public void testStorageUpdate()throws Exception{
        Iris4GAction.startSettings();
        SettingAction.navToStorage();
        float originalUsed=SettingAction.getUesd();
        float originalFree=SettingAction.getFree();
        int makeVideoTime=90;
        Iris4GAction.markVideoSomeTime(makeVideoTime);
        waitTime(1);
        gDevice.pressMenu();
        SettingAction.navToStorage();
        float updateUsed=SettingAction.getUesd();
        float updateFree=SettingAction.getFree();
        float result=SettingAction.floatAbs(updateUsed,originalUsed,updateFree,originalFree);
        if (originalUsed==updateUsed||originalFree==updateFree||result>=0.02){
            Assert.fail("storageUsedOrFreeNotUpdate");
        }else
            logger.info("录制"+makeVideoTime+"秒视频后相机存储更新正确");
    }
}
