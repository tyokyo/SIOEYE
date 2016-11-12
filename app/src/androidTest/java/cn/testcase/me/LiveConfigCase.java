package cn.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import com.squareup.spoon.Spoon;
import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.util.List;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.MeAction;
import cn.page.App;
import cn.page.MePage;

/**
 * Created by elon on 2016/10/27.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class LiveConfigCase extends VP2{
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageNameInLogin(App.SIOEYE_PACKAGE_NAME_EN);
        AccountAction.inLogin();
    }

    //直播标题内容设置-长度-3
    @Test
    public void test_default_video_title_3() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(3);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(MePage.SAMPLE_CONTENT).getText();
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //直播标题内容设置-长度-69
    @Test
    public void test_default_video_title_69() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(69);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(MePage.SAMPLE_CONTENT).getText();
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //直播标题内容设置-长度-120
    @Test
    public void test_default_video_title_120() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(120);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(MePage.SAMPLE_CONTENT).getText();
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //直播标题内容设置-长度->170(最多允许设置120)
    @Test
    public void test_default_video_title_170() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(170);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(MePage.SAMPLE_CONTENT).getText();
        logger.info("length:"+active.length());
        expect=expect.substring(0,120);
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //privacy_settings to public
    @Test
    public void test_privacy_settings_public() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        MeAction.setToPublic();
        clickById(MePage.LIVE_CONFIGURATION_DONE_PRIVACY);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);

        String permission=MeAction.getPermissionToView();
        Asst.assertEquals("set Privacy Setting:Public","public",permission);
        Spoon.screenshot("Public","Permission_Public");
    }
    //privacy_settings to private
    @Test
    public void test_privacy_settings_private() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        MeAction.setToPrivate();
        clickById(MePage.LIVE_CONFIGURATION_DONE_PRIVACY);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        String permission=MeAction.getPermissionToView();
        Asst.assertEquals("set Privacy Setting:Public","private",permission);
        Spoon.screenshot("Public","Privacy_Settings_Private");
    }
    @Test
    public void test_privacy_settings_personal() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        MeAction.setToPersonal();

        clickById(MePage.LIVE_CONFIGURATION_DONE_PRIVACY);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        String permission=MeAction.getPermissionToView();
        Asst.assertEquals("set Privacy Setting:Public","private",permission);
        Spoon.screenshot("Public","Privacy_Settings_Private");
    }
}
