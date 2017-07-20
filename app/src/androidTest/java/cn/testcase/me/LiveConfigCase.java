package cn.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.widget.CheckBox;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.MeAction;
import cn.page.App;
import cn.page.MePage;

/**
 * Created by chendaofa on 2016/10/27.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
/*直播默认标题
*30个汉字或者70个字符
 *  */
public class LiveConfigCase extends VP2{
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageNameInLogin(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }

    //直播标题内容设置-长度-3
    @Test
    @SanityTest
    @PerformanceTest
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
    //直播标题内容设置-长度-35
    @Test
    @SanityTest
    @PerformanceTest
    public void testTitle40c() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(35);
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
    @SanityTest
    @PerformanceTest
    public void testTitle20c() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(20);
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
    //直播标题内容设置-长度->170(最多允许设置35)
    @Test
    @SanityTest
    @PerformanceTest
    public void testTitle50c() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.SAMPLE_CONTENT).clearTextField();
        String expect = getRandomString(100);
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
    @SanityTest
    @PerformanceTest
    public void testSetPublic() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        MeAction.setToPublic();
        clickById(MePage.LIVE_CONFIGURATION_DONE_PRIVACY);
        waitUntilGone(MePage.LIVE_CONFIGURATION_DONE_PRIVACY,10000);
        waitTime(3);
        Spoon.screenshot("public");
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        waitUntilFind(MePage.WHO_CAN_VIEW_MY_BROADCAST_PUBLIC,10000);
        String permission=MeAction.getPermissionToView();
        Asst.assertEquals("set Privacy Setting:Public","public",permission);
        Spoon.screenshot("Public","Permission_Public");
    }
    //privacy_settings to private
    @Test
    @SanityTest
    @PerformanceTest
    public void testSetPrivate() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        MeAction.setToPrivate();
        clickById(MePage.LIVE_CONFIGURATION_DONE_PRIVACY);
        waitUntilGone(MePage.LIVE_CONFIGURATION_DONE_PRIVACY,10000);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        waitUntilFind(MePage.WHO_CAN_VIEW_MY_BROADCAST_PRIVATE,10000);
        String permission=MeAction.getPermissionToView();
        Asst.assertEquals("set Privacy Setting:private","private",permission);
        Spoon.screenshot("private","Privacy_Settings_Private");
    }
    @Test
    @SanityTest
    @PerformanceTest
    public void testSetPersonal() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        //谁可以看我的直播
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        //设置为部分可见
        MeAction.setToPersonal();
        //如果没有选择用户
        if (!id_exists(MePage.SELECT_PEOPLE_LIST_Horizontal)){
            logger.info("Not select user");
            //选择第一个用户
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
    //点击直播封面检查弹出框-陈道发
    @Test
    @SanityTest
    @PerformanceTest
    public void testSetCoverPlot() throws UiObjectNotFoundException {
        MeAction.navToCoverplot();
        String expect_name1="Camera";
        String active_name1=getObjectById(MePage.COVER_PLOT_BTN_DIALOG_PHOTO).getText();
        String expect_name2="Album";
        String active_name2=getObjectById(MePage.COVER_PLOT_BTN_DIALOG_ALBUM).getText();
        String expect_name3="Cancel";
        String active_name3=getObjectById(MePage.COVER_PLOT_BTN_DIALOG_CANCEL).getText();
        Asst.assertEquals("点击的信息是否一致", expect_name1+expect_name2+expect_name3, active_name1+active_name2+active_name3);
        clickById(MePage.COVER_PLOT_BTN_DIALOG_CANCEL);
        Spoon.screenshot("testSetCoverPlot");

    }
    /**
     * 固定拉流地址跳转到会员权益
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testSelectPullingSource() throws UiObjectNotFoundException {
        MeAction.navToPullingSource();


        String active_name1=getObjectById(MePage.SELECT_PULLINS_TV_MEMBER_TEXT).getText();
        String expect_name1="You have not signed up for Sioeye VIP";
        if (active_name1.equals(expect_name1)){
            clickById(MePage.SELECT_PULLINS_TV_OPEN);
            waitTime(5);
            String active_name2=getObjectById(MePage.VIP_RIGHT_TITLE).getText();
            String expect_name2="VIP Rights";
            Asst.assertEquals("是否跳转到会员权益界面", expect_name2, active_name2);
        }

        Spoon.screenshot("testSetCoverPlot");

    }
    /**
     * 水印开关测试
     *测试开关水印
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testWaterMarkDelete() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();

        //findObjects(By.clazz(android.widget.CheckBox.class));
        boolean isChecked=getObject2ByClass(CheckBox.class).isChecked();
       // List<UiObject2> checkBoxs=getObjectsByClassname("android.widget.CheckBox");
        //boolean isChecked=checkBoxs.get(1).isChecked();
        logger.info(""+isChecked);
        if (isChecked==false){
            clickByClass("android.widget.CheckBox",1);
            waitTime(2);
            boolean avtiveisChecked=getObject2ByClass(CheckBox.class).isChecked();
            boolean expect;
            expect=true;
            Asst.assertEquals("打开水印开关",expect,avtiveisChecked);
            Spoon.screenshot("testWaterMarkDelete");
        }if (isChecked==true){
            //clickByClass("android.widget.RelativeLayout");
            clickByClass("android.widget.CheckBox",1);
            waitTime(2);
            boolean avtiveisChecked=getObject2ByClass(CheckBox.class).isChecked();
            boolean expect;
            expect=false;
            Asst.assertEquals("关闭水印开关",expect,avtiveisChecked);
            Spoon.screenshot("testWaterMarkDelete");
        }

    }

}
