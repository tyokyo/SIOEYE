package testcase.usa.me;

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
import page.App;
import page.Me;

/**
 * Created by elon on 2016/10/28.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class HelpCase extends VP2{
    Logger logger = Logger.getLogger(HelpCase.class.getName());
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    //Feedback为空
    @Test
    public void testFeedback_Space() throws UiObjectNotFoundException, IOException {
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.HELP);
        clickById(Me.HELP_FEEDBACK);
        getObjectById(Me.HELP_FEEDBACK_CONTENT).clearTextField();
        //String input = getRandomString(40);
        //shellInputText(input);
        clickById(Me.HELP_DONE);
        boolean feedback_status=getObjectById(Me.HELP_FEEDBACK).exists();
        Asst.assertEquals("Submit success",true,feedback_status);
        Spoon.screenshot("space");
    }
    //Feedback 输入字符长度100
    @Test
    public void testFeedback_100c() throws UiObjectNotFoundException, IOException {
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.HELP);
        clickById(Me.HELP_FEEDBACK);
        getObjectById(Me.HELP_FEEDBACK_CONTENT).clearTextField();
        String input = getRandomString(100);
        shellInputText(input);
        Spoon.screenshot("100c");
        clickById(Me.HELP_DONE);
        boolean feedback_status=getObjectById(Me.HELP_FEEDBACK).exists();
        Asst.assertEquals("Submit success",true,feedback_status);
    }
    //Feedback 输入字符长度1000
    @Test
    public void testFeedback_1000c() throws UiObjectNotFoundException, IOException {
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.HELP);
        clickById(Me.HELP_FEEDBACK);
        getObjectById(Me.HELP_FEEDBACK_CONTENT).clearTextField();
        String input = getRandomString(1000);
        shellInputText(input);
        logger.info("input char:"+getObjectById(Me.HELP_FEEDBACK_CONTENT).getText().length());
        Spoon.screenshot("1000c");
        clickById(Me.HELP_DONE);
        boolean feedback_status=getObjectById(Me.HELP_FEEDBACK).exists();
        Asst.assertEquals("Submit success",true,feedback_status);
    }
    //Feedback 输入字符长度500
    @Test
    public void testFeedback_500c() throws UiObjectNotFoundException, IOException {
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.HELP);
        clickById(Me.HELP_FEEDBACK);
        getObjectById(Me.HELP_FEEDBACK_CONTENT).clearTextField();
        String input = getRandomString(500);
        shellInputText(input);
        logger.info("input char:"+getObjectById(Me.HELP_FEEDBACK_CONTENT).getText().length());
        Spoon.screenshot("500c");
        clickById(Me.HELP_DONE);
        boolean feedback_status=getObjectById(Me.HELP_FEEDBACK).exists();
        Asst.assertEquals("Submit success",true,feedback_status);
    }
    //TermOfService
    @Test
    public void testTermOfService() throws UiObjectNotFoundException, IOException {
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.HELP);
        clickById(Me.HELP_SERVICE);
        Spoon.screenshot("TermOfService");
        clickById(Me.HELP_BACK);
        boolean feedback_status=getObjectById(Me.HELP_SERVICE).exists();
        Asst.assertEquals("back logo success",true,feedback_status);
    }
    //PrivacyPolicy
    @Test
    public void testPrivacyPolicy() throws UiObjectNotFoundException, IOException {
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.HELP);
        clickById(Me.HELP_POLICY);
        Spoon.screenshot("POLICY");
        clickById(Me.HELP_BACK);
        boolean feedback_status=getObjectById(Me.HELP_POLICY).exists();
        Asst.assertEquals("back logo success",true,feedback_status);
    }
    //check for updates
    @Test
    public void testCheckForUpdates() throws UiObjectNotFoundException, IOException {
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.HELP);
        clickById(Me.HELP_VERSION_CHECK);
        Spoon.screenshot("HELP_VERSION_CHECK");
        //clickById(Me.HELP_BACK);
        boolean feedback_status=getObjectById(Me.HELP_VERSION_CHECK).exists();
        Asst.assertEquals("back logo success",true,feedback_status);
    }
    //EULA
    @Test
    public void testEULA() throws UiObjectNotFoundException, IOException {
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.HELP);
        clickById(Me.HELP_EMULA);
        Spoon.screenshot("HELP_EMULA");
        clickById(Me.HELP_BACK);
        boolean feedback_status=getObjectById(Me.HELP_EMULA).exists();
        Asst.assertEquals("back logo success",true,feedback_status);
    }
    //navigate for need help
    @Test
    public void testNeedHelp() throws UiObjectNotFoundException, IOException {
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.HELP);
        clickById(Me.HELP_HELP);
        gDevice.wait(Until.findObject(By.res(Me.HELP_HELP_NAV_WEB)),20000);
        Spoon.screenshot("HELP_HELP");
        waitTime(3);
        String webAddress=getObjectById(Me.HELP_HELP_NAV_WEB).getText();
        Asst.assertEquals("back logo success","support.sioeye.com/support/home",webAddress);
    }
    //back btn for need help domain
    @Test
    public void testNeedHelpBack() throws UiObjectNotFoundException, IOException {
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.HELP);
        clickById(Me.HELP_HELP);
        gDevice.wait(Until.findObject(By.res(Me.HELP_HELP_NAV_WEB)),20000);
        Spoon.screenshot("HELP_HELP");
        String webAddress=getObjectById(Me.HELP_HELP_NAV_WEB).getText();
        Asst.assertEquals("web address","support.sioeye.com/support/home",webAddress);
        gDevice.pressBack();
        boolean feedback_status=getObjectById(Me.HELP_HELP).exists();
        Asst.assertEquals("back logo success",true,feedback_status);
    }
    //about page check
    @Test
    public void testAbout() throws UiObjectNotFoundException, IOException {
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.HELP);
        clickById(Me.HELP_ABOUT);
        gDevice.wait(Until.findObject(By.res(Me.HELP_ABOUT_VERSION)),20000);
        Spoon.screenshot("HELP_ABOUT");
        clickById(Me.HELP_BACK);
        boolean feedback_status=getObjectById(Me.HELP_ABOUT).exists();
        Asst.assertEquals("HELP_ABOUT",true,feedback_status);
    }
}
