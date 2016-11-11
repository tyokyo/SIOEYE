package cn.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;
import com.squareup.spoon.Spoon;
import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
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
public class NickNameCase extends VP2 {
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        AccountAction.inLogin();
    }
    @Test
    public void testChangeNickNameLessThan4Character() throws UiObjectNotFoundException {
        MeAction.navToNickName();
        clearText(MePage.SAMPLE_CONTENT);
        setText(MePage.SAMPLE_CONTENT,getRandomString(3));
        clickById(MePage.USER_EDIT_DONE);
        clickById(MePage.USER_EDIT_DONE);
        clickById(MePage.USER_EDIT_DONE);
        Spoon.screenshot(gDevice,"change_nick_name");
    }
    @Test
    public void testChangeNickNameMoreThan4Character() throws UiObjectNotFoundException {
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
    @Test
    public void testChangeNickNameMaxCharacter() throws UiObjectNotFoundException, IOException {
        MeAction.navToNickName();
        clearText(MePage.SAMPLE_CONTENT);
        String nickname = getRandomString(60);
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
    public void testChangeNickNameNotSave() throws UiObjectNotFoundException, IOException {
        clickById(MePage.ID_MAIN_TAB_ME);
        Spoon.screenshot(gDevice,"Me");
        clickById(MePage.ID_USER_EDIT);
        String nicknameBefore = getTex(MePage.GETNICKNAMECONTENT);
        clickById(MePage.NAV_EDIT_NICKNAME);
        clearText(MePage.SAMPLE_CONTENT);
        String nickname = getRandomString(20);
        shellInputText(nickname);
        gDevice.pressBack();
        waitTime(3);
        String currentNick = getTex(MePage.GETNICKNAMECONTENT);
        if (!nicknameBefore.equals(currentNick)){
            Assert.fail("change nick but not save it");
        }
        Spoon.screenshot(gDevice,"change_nick_name");
    }
}
