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
public class FeedBackCase extends VP2{
    Logger logger = Logger.getLogger(FeedBackCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        AccountAction.inLogin();
    }
    //Feedback为空
    @Test
    public void testFeedback_Space() throws UiObjectNotFoundException, IOException {
        SettingAction.navToFeedBack();
        getObjectById(MePage.HELP_FEEDBACK_CONTENT).clearTextField();
        //String input = getRandomString(40);
        //shellInputText(input);
        clickById(MePage.HELP_DONE);
        waitHasObject(MePage.SETTINGS_CB_DIRECTOR,10000);
        boolean feedback_status=getObjectById(MePage.SETTINGS_CB_DIRECTOR).exists();
        Asst.assertEquals("Submit success",true,feedback_status);
        Spoon.screenshot("feedback");
    }
    //Feedback 输入字符长度100
    @Test
    public void testFeedback_100c() throws UiObjectNotFoundException, IOException {
        SettingAction.navToFeedBack();
        getObjectById(MePage.HELP_FEEDBACK_CONTENT).clearTextField();
        String input = getRandomString(100);
        shellInputText(input);
        Spoon.screenshot("100c");
        clickById(MePage.HELP_DONE);
        waitHasObject(MePage.SETTINGS_CB_DIRECTOR,10000);
        boolean feedback_status=getObjectById(MePage.SETTINGS_CB_DIRECTOR).exists();
        Asst.assertEquals("Submit success",true,feedback_status);
        Spoon.screenshot("feedback");
    }
    //Feedback 输入字符长度1000
    @Test
    public void testFeedback_1000c() throws UiObjectNotFoundException, IOException {
        SettingAction.navToFeedBack();
        getObjectById(MePage.HELP_FEEDBACK_CONTENT).clearTextField();
        String input = getRandomString(1000);
        shellInputText(input);
        logger.info("input char:"+getObjectById(MePage.HELP_FEEDBACK_CONTENT).getText().length());
        Spoon.screenshot("1000c");
        clickById(MePage.HELP_DONE);
        waitHasObject(MePage.SETTINGS_CB_DIRECTOR,10000);
        boolean feedback_status=getObjectById(MePage.SETTINGS_CB_DIRECTOR).exists();
        Asst.assertEquals("Submit success",true,feedback_status);
        Spoon.screenshot("feedback");
    }
    //Feedback 输入字符长度500
    @Test
    public void testFeedback_500c() throws UiObjectNotFoundException, IOException {
        SettingAction.navToFeedBack();
        getObjectById(MePage.HELP_FEEDBACK_CONTENT).clearTextField();
        String input = getRandomString(500);
        shellInputText(input);
        logger.info("input char:"+getObjectById(MePage.HELP_FEEDBACK_CONTENT).getText().length());
        Spoon.screenshot("500c");
        clickById(MePage.HELP_DONE);
        waitHasObject(MePage.SETTINGS_CB_DIRECTOR,10000);
        boolean feedback_status=getObjectById(MePage.SETTINGS_CB_DIRECTOR).exists();
        Asst.assertEquals("Submit success",true,feedback_status);
        Spoon.screenshot("feedback");
    }
}
