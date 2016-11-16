package cn.testcase.setting;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.logging.Logger;

import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.SettingAction;
import cn.page.App;
import cn.page.MePage;

/**
 * Created by elon on 2016/11/16.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class SettingCase extends VP2 {
    Logger logger = Logger.getLogger(SettingCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        AccountAction.inLogin();
    }
    //check for updates
    @Test
    public void testCheckForUpdates() throws UiObjectNotFoundException, IOException {
        SettingAction.navToUpdate();
        Spoon.screenshot("HELP_VERSION_CHECK");
        //clickById(Me.HELP_BACK);
        boolean feedback_status=getObjectById(MePage.SETTINGS_CB_DIRECTOR).exists();
        Asst.assertEquals("back logo success",true,feedback_status);
        gDevice.pressBack();
    }
    @Test
    public void testAboutSioEye() throws UiObjectNotFoundException, IOException {
        SettingAction.navToAboutSioEye();
        Spoon.screenshot("HELP_ABOUT");
        clickById(MePage.HELP_BACK);
        boolean feedback_status=getObjectById(MePage.SETTINGS_CB_DIRECTOR).exists();
        Asst.assertEquals("HELP_ABOUT",true,feedback_status);
        gDevice.pressBack();
    }
}
