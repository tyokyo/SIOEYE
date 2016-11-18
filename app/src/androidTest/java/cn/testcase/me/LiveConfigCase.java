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
        openAppByPackageNameInLogin(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }

    //直播标题内容设置-长度-3
    @Test
    public void testTitle3c() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(3);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(MePage.SAMPLE_CONTENT).getText();
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //直播标题内容设置-长度-69
    @Test
    public void testTitle40c() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(40);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(MePage.SAMPLE_CONTENT).getText();
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //直播标题内容设置-长度-120
    @Test
    public void testTitle50c() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(50);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(MePage.SAMPLE_CONTENT).getText();
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //直播标题内容设置-长度->170(最多允许设置70)
    @Test
    public void testTitle170c() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(170);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(MePage.SAMPLE_CONTENT).getText();
        logger.info("length:"+active.length());
        expect=expect.substring(0,70);
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //privacy_settings to public
    @Test
    public void testSetPublic() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        MeAction.setToPublic();
        clickById(MePage.LIVE_CONFIGURATION_DONE_PRIVACY);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);

        String permission=MeAction.getPermissionToView();
        Asst.assertEquals("set Privacy Setting:Public","public",permission);
        Spoon.screenshot("Public","Permission_Public");
    }
    //privacy_settings to private
    @Test
    public void testSetPrivate() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        MeAction.setToPrivate();
        clickById(MePage.LIVE_CONFIGURATION_DONE_PRIVACY);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        String permission=MeAction.getPermissionToView();
        Asst.assertEquals("set Privacy Setting:private","private",permission);
        Spoon.screenshot("private","Privacy_Settings_Private");
    }
    @Test
    public void testSetPersonal() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        //谁可以看我的直播
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        //设置为部分可见
        MeAction.setToPersonal();
        //没有选择用户
        if (!id_exists(MePage.SELECT_PEOPLE_LIST_Horizontal)){
            logger.info("Not select user");
            getObject2ById(MePage.SELECT_PEOPLE_LIST_Vertical).findObjects(By.res(MePage.SELECT_PEOPLE)).get(0).click();
        }else{
            logger.info("select user");
        }
        clickById(MePage.PRIVACY_PERSONAL_DONE);
        clickById(MePage.PRIVACY_PERSONAL_RIGHT);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        //谁可以看我的直播
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        /*//设置为部分可见
        MeAction.setToPersonal();
        clickById(MePage.PRIVACY_PERSONAL_DONE);
        clickById(MePage.PRIVACY_PERSONAL_RIGHT);*/
        String permission=MeAction.getPermissionToView();
        Asst.assertEquals("set Privacy Setting:Public","personal",permission);
        Spoon.screenshot("personal");
        MeAction.setToPersonal();
        Spoon.screenshot("personal");
    }
}
