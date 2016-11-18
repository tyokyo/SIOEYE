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
public class HelpCenterCase extends VP2 {
    Logger logger = Logger.getLogger(HelpCenterCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    //TermOfService
    @Test
    public void testTermOfService() throws UiObjectNotFoundException, IOException {
        SettingAction.navToHP_TermService();
        Spoon.screenshot("TermOfService");
        clickById(MePage.HELP_BACK);
        boolean status=getObjectById(MePage.HELP_SERVICE).exists();
        Asst.assertEquals("back logo success",true,status);
    }
    //PrivacyPolicy
    @Test
    public void testPrivacyPolicy() throws UiObjectNotFoundException, IOException {
        SettingAction.navToHP_Privacy();
        clickById(MePage.HELP_BACK);
        boolean status=getObjectById(MePage.HELP_POLICY).exists();
        Asst.assertEquals("back logo success",true,status);
    }
    @Test
    public void testEULA() throws UiObjectNotFoundException, IOException {
        SettingAction.navToHP_UserProtocol();
        clickById(MePage.HELP_BACK);
        boolean feedback_status=getObjectById(MePage.HELP_EMULA).exists();
        Asst.assertEquals("back logo success",true,feedback_status);
    }
    @Test
    public void testNeedHelp() throws UiObjectNotFoundException, IOException {
        SettingAction.navToHP_Help();
        gDevice.wait(Until.findObject(By.text("www.sioeye.cn")),20000);
        Spoon.screenshot("HELP_HELP");
        waitTime(3);
        if(id_exists("android:id/button1")){
            clickById("android:id/button1");
        }
        waitTime(3);
        //String webAddress=getObjectById(MePage.HELP_HELP_NAV_WEB).getText();
        Spoon.screenshot("Support_sioEye");
        //Asst.assertEquals("back logo success",true,text_exists("Support - Sioeye 喜爱直播"));
    }
    @Test
    public void testNeedHelpBack() throws UiObjectNotFoundException, IOException {
        SettingAction.navToHP_Help();
        gDevice.wait(Until.findObject(By.text("www.sioeye.cn")),20000);
        Spoon.screenshot("HELP_HELP");
        waitTime(3);
        if(id_exists("android:id/button1")){
            clickById("android:id/button1");
        }
        waitTime(3);
        //String webAddress=getObjectById(MePage.HELP_HELP_NAV_WEB).getText();
        Spoon.screenshot("Support_sioEye");
        gDevice.pressBack();
        boolean status=getObjectById(MePage.HELP_HELP).exists();
        Asst.assertEquals("back logo success",true,status);
    }
}
