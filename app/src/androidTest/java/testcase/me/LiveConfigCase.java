package testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.widget.TextView;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import action.MeAction;
import ckt.base.VP2;
import page.App;
import page.Me;

/**
 * Created by elon on 2016/10/27.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class LiveConfigCase extends VP2{
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }

    //video title set to length 3
    @Test
    public void test_default_video_title_3() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(Me.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(3);
        shellInputText(expect);
        clickById(Me.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(Me.SAMPLE_CONTENT).getText();
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //set video title to length 69
    @Test
    public void test_default_video_title_69() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(Me.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(69);
        shellInputText(expect);
        clickById(Me.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(Me.SAMPLE_CONTENT).getText();
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //set video title to length 70
    @Test
    public void test_default_video_title_70() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(Me.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(70);
        shellInputText(expect);
        clickById(Me.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(Me.SAMPLE_CONTENT).getText();
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //set video title to 100 （bigger than max 70）
    @Test
    public void test_default_video_title_100() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(Me.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(100);
        shellInputText(expect);
        clickById(Me.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(Me.SAMPLE_CONTENT).getText();
        expect=expect.substring(0,70);
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    public void setToPublic() throws UiObjectNotFoundException {
        List<UiObject2> cbx = gDevice.findObjects(By.res(Me.LIVE_CONFIGURATION_PRIVACY_SELECT));
        boolean is_checked=getObjectById(Me.LIVE_CONFIGURATION_PRIVACY_SELECT).isChecked();
        if (!is_checked){
            clickById(Me.LIVE_CONFIGURATION_PRIVACY_SELECT);
        }
    }
    public void setToPrivate() throws UiObjectNotFoundException {
        setToPublic();
        List<UiObject2> cbx = gDevice.findObjects(By.res(Me.LIVE_CONFIGURATION_PRIVACY_SELECT));
        for (UiObject2 box:cbx) {
            box.click();
        }
    }
    //privacy_settings to public
    @Test
    public void test_privacy_settings_public() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        setToPublic();
        clickById(Me.LIVE_CONFIGURATION_DONE_PRIVACY);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        boolean is_checked=getObjectById(Me.LIVE_CONFIGURATION_PRIVACY_SELECT).isChecked();
        Asst.assertEquals("set Privacy Setting:Public",true,is_checked);
        Spoon.screenshot("Public","Privacy_Settings_Public");
    }
    //privacy_settings to private
    @Test
    public void test_privacy_settings_private() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        setToPrivate();
        clickById(Me.LIVE_CONFIGURATION_DONE_PRIVACY);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        boolean is_checked=getObjectById(Me.LIVE_CONFIGURATION_PRIVACY_SELECT).isChecked();
        Asst.assertEquals("set Privacy Setting:Public",false,is_checked);
        Spoon.screenshot("Public","Privacy_Settings_Private");
    }
    //simulcast set to length 4
    @Test
    public void test_simulcast_4char() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_SLV_VIDEO);
        getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).clearTextField();
        String expect = getRandomString(4);
        shellInputText(expect);
        clickById(Me.LIVE_CONFIGURATION_DONE_SLV_VIDEO);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_SLV_VIDEO);
        String active = getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).getText();
        //expect=expect.substring(0,70);
        Asst.assertEquals("SLV:",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();
    }
    //设置最大字符长度70
    @Test
    public void test_simulcast_70char() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_SLV_VIDEO);
        getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).clearTextField();
        String expect = getRandomString(70);
        shellInputText(expect);
        clickById(Me.LIVE_CONFIGURATION_DONE_SLV_VIDEO);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_SLV_VIDEO);
        String active = getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).getText();
        //expect=expect.substring(0,70);
        Asst.assertEquals("SLV:",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();
    }
    //设置超过最大字符数限制
    @Test
    public void test_simulcast_120char() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_SLV_VIDEO);
        getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).clearTextField();
        String expect = getRandomString(120);
        shellInputText(expect);
        clickById(Me.LIVE_CONFIGURATION_DONE_SLV_VIDEO);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_SLV_VIDEO);
        String active = getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).getText();
        //expect=expect.substring(0,70);
        Asst.assertEquals("SLV:",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();
    }
    //设置超过最大字符数限制
    @Test
    public void test_simulcast_500char() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_SLV_VIDEO);
        getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).clearTextField();
        String expect = getRandomString(500);
        shellInputText(expect);
        clickById(Me.LIVE_CONFIGURATION_DONE_SLV_VIDEO);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_SLV_VIDEO);
        String active = getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).getText();
        expect=expect.substring(0,120);
        Asst.assertEquals("SLV:",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();
    }
    //设置public_viewing_link 10
    @Test
    public void test_public_viewing_link_10char() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_LINK);
        getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).clearTextField();
        String expect = getRandomString(10);
        shellInputText(expect);
        clickById(Me.LIVE_CONFIGURATION_DONE_SLV_VIDEO);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_LINK);
        String active = getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).getText();
        //expect=expect.substring(0,120);
        Asst.assertEquals("Publish viewing link::",expect,active);

        gDevice.pressBack();
        gDevice.pressBack();
    }
    //设置public_viewing_link 120
    @Test
    public void test_public_viewing_link_120char() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_LINK);
        getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).clearTextField();
        String expect = getRandomString(120);
        shellInputText(expect);
        clickById(Me.LIVE_CONFIGURATION_DONE_SLV_VIDEO);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_LINK);
        String active = getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).getText();
        //expect=expect.substring(0,120);
        Asst.assertEquals("Publish viewing link::",expect,active);
        Spoon.screenshot("link_120c");
        gDevice.pressBack();
        gDevice.pressBack();
    }
    //设置public_viewing_link 超过最大字符数限制
    @Test
    public void test_public_viewing_link_500char() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_LINK);
        getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).clearTextField();
        String expect = getRandomString(500);
        shellInputText(expect);
        clickById(Me.LIVE_CONFIGURATION_DONE_SLV_VIDEO);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToLiveConfiguration();
        clickById(Me.LIVE_CONFIGURATION_LINK);
        String active = getObjectById(Me.LIVE_CONFIGURATION_SLV_SHARE_CONTENT).getText();
        expect=expect.substring(0,120);
        Asst.assertEquals("Publish viewing link:link_500c:",expect,active);
        Spoon.screenshot("link_500c");
        gDevice.pressBack();
        gDevice.pressBack();
    }
}
