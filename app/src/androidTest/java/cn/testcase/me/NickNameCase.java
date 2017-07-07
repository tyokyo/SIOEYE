package cn.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.test.suitebuilder.annotation.LargeTest;

import com.squareup.spoon.Spoon;
import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.util.logging.Logger;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.MeAction;
import cn.page.App;
import cn.page.MePage;


/**
 * Created by elon on 2016/10/11.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
/*
* 昵称
* 4-40个字符*/
public class NickNameCase extends VP2 {
    private Logger logger = Logger.getLogger(NickNameCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    @Test
    @SanityTest
    @PerformanceTest
    public void testChangeNmc4() throws UiObjectNotFoundException {
        MeAction.navToNickName();
        clearText(MePage.SAMPLE_CONTENT);
        setText(MePage.SAMPLE_CONTENT,getRandomString(3));
        clickById(MePage.USER_EDIT_DONE);
        Spoon.screenshot(gDevice,"change_nick_name");
    }
    @Test
    @SanityTest
    @PerformanceTest
    public void testChangeMoreThanC4() throws UiObjectNotFoundException {
        MeAction.navToNickName();
        clearText(MePage.SAMPLE_CONTENT);
        String nickname = getRandomString(4);
        setText(MePage.SAMPLE_CONTENT,nickname);
        clickById(MePage.USER_EDIT_DONE);
        waitTime(3);
        String currentNick =getTex(MePage.GETNICKNAMECONTENT);
        if (!nickname.equals(currentNick)){
            Assert.fail("nick name change failed");
        }
        Spoon.screenshot(gDevice,"change_nick_name");
    }
    @SanityTest
    @PerformanceTest
    @Test
    public void testChangeNnMaxC() throws UiObjectNotFoundException, IOException {
        MeAction.navToNickName();
        clearText(MePage.SAMPLE_CONTENT);
        String nickname = getRandomString(100);
        shellInputText(nickname);
        clickById(MePage.USER_EDIT_DONE);
        waitTime(3);
        String currentNick = getTex(MePage.GETNICKNAMECONTENT);
        if (currentNick.length()!=30){
            Asst.fail("max length is 30");
        }
        Spoon.screenshot(gDevice,"change_nick_name");
    }
    @Test
    @SanityTest
    @PerformanceTest
    @LargeTest
    public void testChangeNnNotSave() throws UiObjectNotFoundException, IOException {
        clickById(MePage.ID_MAIN_TAB_ME);
        Spoon.screenshot(gDevice,"Me");
        clickById(MePage.ID_USER_EDIT);
        waitUntilFind(MePage.GETNICKNAMECONTENT,10000);
        String expectNick = getTex(MePage.GETNICKNAMECONTENT);
        clickById(MePage.NAV_EDIT_NICKNAME);
        clearText(MePage.SAMPLE_CONTENT);
        String nickname = getRandomString(10);
        shellInputText(nickname);
        clickById(MePage.ID_IMG_BACK);
        waitTime(3);
        String activeNick = getTex(MePage.GETNICKNAMECONTENT);
        Asst.assertEquals("change nick but not save it",expectNick,activeNick);
        Spoon.screenshot(gDevice,"change_nick_name");
    }
}
