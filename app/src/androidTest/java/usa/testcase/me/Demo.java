package usa.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import ckt.base.VP2;
import usa.action.AccountAction;
import usa.page.Account;
import usa.page.App;
import usa.page.Discover;

import static usa.action.AccountAction.logOutAccount;
import static usa.action.AccountAction.navToSignUp_ByEmail;

/**
 * Created by admin on 2016/11/16.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Demo extends VP2 {
    Logger logger = Logger.getLogger(usa.testcase.me.Demo.class.getName());
    @Before
    public void setup() {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }


    public String getZanNumber() {
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        UiObject2 swip = getObject2ById(Discover.ID_Swipe_target);
        waitTime(5);
        List<UiObject2> linearLayouts = swip.findObjects(By.clazz(android.widget.LinearLayout.class));
        logger.info(linearLayouts.size() + "");
        String temp ="100";
        for (UiObject2 linearLayout : linearLayouts) {
            List<UiObject2> textViews = linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
            if (textViews.size() == 3) {
                return textViews.get(0).getText();
            }
        }
     return temp;
    }

    @Test
    public void  testA(){
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        UiObject2 swip=getObject2ById("com.sioeye.sioeyeapp:id/swipe_target");
        List<UiObject2> linearLayouts= swip.findObjects(By.clazz(android.widget.LinearLayout.class));
        logger.info(linearLayouts.size()+"");

        for (UiObject2 linearLayout:linearLayouts){
            List<UiObject2> textViews=linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
            if (textViews.size()==3){
                logger.info(textViews.get(0).getText());
                logger.info(textViews.get(1).getText());
                logger.info(textViews.get(2).getText());
                logger.info("==========================");
            }
        }
    }
    @Test
    public void testB(){
        logger.info(usa.action.DiscoverAction.getZanNumber());
    }

    public void testSupLongUseNameAndPassword() throws UiObjectNotFoundException{
        if (AccountAction.judgelogin()) {
            AccountAction.logout();
        }
        AccountAction.navToLogin();
        clearText(Account.LOGIN_ET_INPUT_USERNAME);
        clearText(Account.LOGIN_ET_INPUT_PASSWORD);
        String usename130=AccountAction.getRandomEmail(120,130);
        String password130=AccountAction.getStringRandom(130);
        AccountAction.justLogIn(usename130,password130);
        if (!getUiObjectByText("Login").exists()) {
            Assert.fail("错误账号点击登陆后页面变化");
        }


    }
}
