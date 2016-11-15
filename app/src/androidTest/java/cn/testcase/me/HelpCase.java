package cn.testcase.me;

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
import cn.page.App;
import cn.page.MePage;

/**
 * Created by elon on 2016/10/28.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class HelpCase extends VP2{
    Logger logger = Logger.getLogger(HelpCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        AccountAction.inLogin();
    }
    //Feedback为空
    @Test
    public void testFeedback_Space() throws UiObjectNotFoundException, IOException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.HELP);
        clickById(MePage.HELP_FEEDBACK);
        getObjectById(MePage.HELP_FEEDBACK_CONTENT).clearTextField();
        //String input = getRandomString(40);
        //shellInputText(input);
        clickById(MePage.HELP_DONE);
        boolean feedback_status=getObjectById(MePage.HELP_FEEDBACK).exists();
        Asst.assertEquals("Submit success",true,feedback_status);
        Spoon.screenshot("space");
    }
    //Feedback 输入字符长度100
    @Test
    public void testFeedback_100c() throws UiObjectNotFoundException, IOException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.HELP);
        clickById(MePage.HELP_FEEDBACK);
        getObjectById(MePage.HELP_FEEDBACK_CONTENT).clearTextField();
        String input = getRandomString(100);
        shellInputText(input);
        Spoon.screenshot("100c");
        clickById(MePage.HELP_DONE);
        boolean feedback_status=getObjectById(MePage.HELP_FEEDBACK).exists();
        Asst.assertEquals("Submit success",true,feedback_status);
    }
    //Feedback 输入字符长度1000
    @Test
    public void testFeedback_1000c() throws UiObjectNotFoundException, IOException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.HELP);
        clickById(MePage.HELP_FEEDBACK);
        getObjectById(MePage.HELP_FEEDBACK_CONTENT).clearTextField();
        String input = getRandomString(1000);
        shellInputText(input);
        logger.info("input char:"+getObjectById(MePage.HELP_FEEDBACK_CONTENT).getText().length());
        Spoon.screenshot("1000c");
        clickById(MePage.HELP_DONE);
        boolean feedback_status=getObjectById(MePage.HELP_FEEDBACK).exists();
        Asst.assertEquals("Submit success",true,feedback_status);
    }
    //Feedback 输入字符长度500
    @Test
    public void testFeedback_500c() throws UiObjectNotFoundException, IOException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.HELP);
        clickById(MePage.HELP_FEEDBACK);
        getObjectById(MePage.HELP_FEEDBACK_CONTENT).clearTextField();
        String input = getRandomString(500);
        shellInputText(input);
        logger.info("input char:"+getObjectById(MePage.HELP_FEEDBACK_CONTENT).getText().length());
        Spoon.screenshot("500c");
        clickById(MePage.HELP_DONE);
        waitHasObject(MePage.HELP_FEEDBACK,10000);
        boolean feedback_status=getObjectById(MePage.HELP_FEEDBACK).exists();
        Asst.assertEquals("Submit success",true,feedback_status);
    }
    //TermOfService
    @Test
    public void testTermOfService() throws UiObjectNotFoundException, IOException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.HELP);
        clickById(MePage.HELP_SERVICE);
        Spoon.screenshot("TermOfService");
        clickById(MePage.HELP_BACK);
        boolean feedback_status=getObjectById(MePage.HELP_SERVICE).exists();
        Asst.assertEquals("back logo success",true,feedback_status);
    }
    //PrivacyPolicy
    @Test
    public void testPrivacyPolicy() throws UiObjectNotFoundException, IOException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.HELP);
        clickById(MePage.HELP_POLICY);
        Spoon.screenshot("POLICY");
        clickById(MePage.HELP_BACK);
        boolean feedback_status=getObjectById(MePage.HELP_POLICY).exists();
        Asst.assertEquals("back logo success",true,feedback_status);
    }
    //check for updates
    @Test
    public void testCheckForUpdates() throws UiObjectNotFoundException, IOException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.HELP);
        clickById(MePage.HELP_VERSION_CHECK);
        Spoon.screenshot("HELP_VERSION_CHECK");
        //clickById(Me.HELP_BACK);
        boolean feedback_status=getObjectById(MePage.HELP_VERSION_CHECK).exists();
        Asst.assertEquals("back logo success",true,feedback_status);
    }
    //EULA
    @Test
    public void testEULA() throws UiObjectNotFoundException, IOException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.HELP);
        clickById(MePage.HELP_EMULA);
        Spoon.screenshot("HELP_EMULA");
        clickById(MePage.HELP_BACK);
        boolean feedback_status=getObjectById(MePage.HELP_EMULA).exists();
        Asst.assertEquals("back logo success",true,feedback_status);
    }
    //navigate for need help
    @Test
    public void testNeedHelp() throws UiObjectNotFoundException, IOException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.HELP);
        clickById(MePage.HELP_HELP);
        gDevice.wait(Until.findObject(By.res(MePage.HELP_HELP_NAV_WEB)),20000);
        Spoon.screenshot("HELP_HELP");
        waitTime(3);
        if(id_exists("android:id/button1")){
            clickById("android:id/button1");
        }
        waitTime(3);
        //String webAddress=getObjectById(MePage.HELP_HELP_NAV_WEB).getText();
        Spoon.screenshot("Support_sioEye");
        Asst.assertEquals("back logo success",true,text_exists("Support - Sioeye 喜爱直播"));
    }
    //back btn for need help domain
    @Test
    public void testNeedHelpBack() throws UiObjectNotFoundException, IOException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.HELP);
        clickById(MePage.HELP_HELP);
        gDevice.wait(Until.findObject(By.res(MePage.HELP_HELP_NAV_WEB)),20000);
        Spoon.screenshot("HELP_HELP");
        waitTime(3);
        if(id_exists("android:id/button1")){
            clickById("android:id/button1");
        }
        waitTime(3);
        //String webAddress=getObjectById(MePage.HELP_HELP_NAV_WEB).getText();
        Spoon.screenshot("Support_sioEye");
        Asst.assertEquals("back logo success",true,text_exists("Support - Sioeye 喜爱直播"));
        gDevice.pressBack();
        boolean feedback_status=getObjectById(MePage.HELP_HELP).exists();
        Asst.assertEquals("back logo success",true,feedback_status);
    }
    //about page check
    @Test
    public void testAbout() throws UiObjectNotFoundException, IOException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.HELP);
        clickById(MePage.HELP_ABOUT);
        gDevice.wait(Until.findObject(By.res(MePage.HELP_ABOUT_VERSION)),20000);
        Spoon.screenshot("HELP_ABOUT");
        clickById(MePage.HELP_BACK);
        boolean feedback_status=getObjectById(MePage.HELP_ABOUT).exists();
        Asst.assertEquals("HELP_ABOUT",true,feedback_status);
    }
}
