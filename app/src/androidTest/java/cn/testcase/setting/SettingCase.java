package cn.testcase.setting;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
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
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    //check for updates
    @Test
    @SanityTest
    @PerformanceTest
    public void testCheckForUpdates() throws UiObjectNotFoundException, IOException {
        SettingAction.navToUpdate();
        Spoon.screenshot("HELP_VERSION_CHECK");
        //clickById(Me.HELP_BACK);
        boolean feedback_status=getObjectById(MePage.SETTINGS_3G_NETWORK).exists();
        Asst.assertEquals("back logo success",true,feedback_status);
        gDevice.pressBack();
    }
    /**
     *测试3G网络下可以观看
     * @author elon
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testEnable() throws UiObjectNotFoundException {
        SettingAction.navToSetting();
        boolean isChecked=getObject2ById(MePage.SETTINGS_3G_NETWORK).isChecked();
        logger.info(""+isChecked);
        if (isChecked==false){
            clickById(MePage.SETTINGS_3G_NETWORK);
            waitTime(2);
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    public void testDisable() throws UiObjectNotFoundException {
        SettingAction.navToSetting();
        boolean isChecked=getObject2ById(MePage.SETTINGS_3G_NETWORK).isChecked();
        logger.info(""+isChecked);
        if (isChecked==true){
            clickById(MePage.SETTINGS_3G_NETWORK);
            waitTime(2);
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    public void testAboutSioEye() throws UiObjectNotFoundException, IOException {
        SettingAction.navToAboutSioEye();
        Spoon.screenshot("HELP_ABOUT");
        clickById(MePage.HELP_BACK);
        waitUntilFind(MePage.SETTINGS_3G_NETWORK,10000);
        boolean feedback_status=getObjectById(MePage.SETTINGS_3G_NETWORK).exists();
        Asst.assertEquals("HELP_ABOUT",true,feedback_status);
        gDevice.pressBack();
    }
    /**
     * 导播控制测试
     *测试开关导播
     * @author elon
     */
    public void tesDirectorEnable() throws UiObjectNotFoundException {
        SettingAction.navToSetting();
        boolean isChecked=getObject2ById(MePage.SETTINGS_CB_DIRECTOR).isChecked();
        logger.info(""+isChecked);
        if (isChecked==false){
            clickById(MePage.SETTINGS_CB_DIRECTOR);
            waitTime(2);
            String active=getTex(MePage.SETTINGS_CB_DIRECTOR_TEXT);
            String expect="Disable the direct control, the changes will take effect on the new live stream";
            Asst.assertEquals("打开直播",expect,active);
        }
    }
    /**
     * 流量控制测试
     *测试流量开关
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testThreeDNetwork() throws UiObjectNotFoundException {
        SettingAction.navToSetting();
        boolean isChecked=getObject2ById(MePage.SETTINGS_3G_NETWORK).isChecked();
        logger.info(""+isChecked);
        if (isChecked==false){
            clickById(MePage.SETTINGS_3G_NETWORK);
            waitTime(2);
            boolean avtiveisChecked=getObject2ById(MePage.SETTINGS_3G_NETWORK).isChecked();
            boolean expect;
            expect=true;
            Asst.assertEquals("打开流量",expect,avtiveisChecked);
            Spoon.screenshot("testThreeDNetwork");
        }if (isChecked==true){
            clickById(MePage.SETTINGS_3G_NETWORK);
            waitTime(2);
            boolean avtiveisChecked=getObject2ById(MePage.SETTINGS_3G_NETWORK).isChecked();
            boolean expect;
            expect=false;
            Asst.assertEquals("关闭流量",expect,avtiveisChecked);
            Spoon.screenshot("testThreeDNetwork");
        }
    }
    /**
     * 直播推送测试
     *直播推送开关
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testLiveNotifications() throws UiObjectNotFoundException {
        SettingAction.navToMessageNotification();
        boolean isChecked=getObject2ById(MePage.SETTINGS_PUSH_TYPE_LIVE).isChecked();
        logger.info(""+isChecked);
        if (isChecked==false){
            clickById(MePage.SETTINGS_PUSH_TYPE_LIVE);
            waitTime(2);
            boolean avtiveisChecked=getObject2ById(MePage.SETTINGS_PUSH_TYPE_LIVE).isChecked();
            boolean expect;
            expect=true;
            Asst.assertEquals("打开直播推送",expect,avtiveisChecked);
            Spoon.screenshot("testLiveNotifications");
        }if (isChecked==true){
            clickById(MePage.SETTINGS_PUSH_TYPE_LIVE);
            waitTime(2);
            boolean avtiveisChecked=getObject2ById(MePage.SETTINGS_PUSH_TYPE_LIVE).isChecked();
            boolean expect;
            expect=false;
            Asst.assertEquals("关闭直播推送",expect,avtiveisChecked);
            Spoon.screenshot("testLiveNotifications");
        }
    }
    /**
     * @ME测试
     *@ME开关
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testPushTypeAt() throws UiObjectNotFoundException {
        SettingAction.navToMessageNotification();
        boolean isChecked=getObject2ById(MePage.SETTINGS_PUSH_TYPE_AT).isChecked();
        logger.info(""+isChecked);
        if (isChecked==false){
            clickById(MePage.SETTINGS_PUSH_TYPE_AT);
            waitTime(2);
            boolean avtiveisChecked=getObject2ById(MePage.SETTINGS_PUSH_TYPE_AT).isChecked();
            boolean expect;
            expect=true;
            Asst.assertEquals("打开@ME推送",expect,avtiveisChecked);
            Spoon.screenshot("testPushTypeAt");
        }if (isChecked==true){
            clickById(MePage.SETTINGS_PUSH_TYPE_AT);
            waitTime(2);
            boolean avtiveisChecked=getObject2ById(MePage.SETTINGS_PUSH_TYPE_AT).isChecked();
            boolean expect;
            expect=false;
            Asst.assertEquals("关闭@ME推送",expect,avtiveisChecked);
            Spoon.screenshot("testPushTypeAt");
        }
    }
    /**
     * 评论推送测试
     *评论推送开关
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testCommentsNotify() throws UiObjectNotFoundException {
        SettingAction.navToMessageNotification();
        boolean isChecked=getObject2ById(MePage.SETTINGS_PUSH_TYPE_COMMENT).isChecked();
        logger.info(""+isChecked);
        if (isChecked==false){
            clickById(MePage.SETTINGS_PUSH_TYPE_COMMENT);
            waitTime(2);
            boolean avtiveisChecked=getObject2ById(MePage.SETTINGS_PUSH_TYPE_COMMENT).isChecked();
            boolean expect;
            expect=true;
            Asst.assertEquals("打开评论推送",expect,avtiveisChecked);
            Spoon.screenshot("testCommentsNotify");
        }if (isChecked==true){
            clickById(MePage.SETTINGS_PUSH_TYPE_COMMENT);
            waitTime(2);
            boolean avtiveisChecked=getObject2ById(MePage.SETTINGS_PUSH_TYPE_COMMENT).isChecked();
            boolean expect;
            expect=false;
            Asst.assertEquals("关闭评论推送",expect,avtiveisChecked);
            Spoon.screenshot("testCommentsNotify");
        }
    }

    public void testDirectorDisable() throws UiObjectNotFoundException {
        SettingAction.navToSetting();
        boolean isChecked=getObject2ById(MePage.SETTINGS_CB_DIRECTOR).isChecked();
        logger.info(""+isChecked);
        if (isChecked==true){
            clickById(MePage.SETTINGS_CB_DIRECTOR);
            waitTime(2);
            String active=getTex(MePage.SETTINGS_CB_DIRECTOR_TEXT);
            String expect="Enable the direct control, you can switch the scenes ";
            Asst.assertEquals("关闭直播",expect,active);
        }
    }
}
