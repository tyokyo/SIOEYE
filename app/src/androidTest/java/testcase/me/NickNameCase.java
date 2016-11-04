package testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import action.MeAction;
import action.Nav;
import ckt.base.VP2;
import page.App;
import page.Me;

/**
 * Created by elon on 2016/10/11.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class NickNameCase  extends VP2 {
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    @Test
    public void testChangeNickNameLessThan4Character() throws UiObjectNotFoundException {
        MeAction.navToNickName();
        getObjectById(Me.SAMPLE_CONTENT).clearTextField();
        getObjectById(Me.SAMPLE_CONTENT).setText(getRandomString(3));
        clickById(Me.NICKNAME_DONE);
        getUiObjectByText("Done");
        getUiObjectByText("Done");
        Spoon.screenshot(gDevice,"change_nick_name");
    }
    @Test
    public void testChangeNickNameMoreThan4Character() throws UiObjectNotFoundException {
        MeAction.navToNickName();
        getObjectById(Me.SAMPLE_CONTENT).clearTextField();
        String nickname = getRandomString(4);
        getObjectById(Me.SAMPLE_CONTENT).setText(nickname);
        clickById(Me.NICKNAME_DONE);
        waitTime(3);
        String currentNick = getObjectById(Me.GETNICKNAMECONTENT).getText();
        if (!nickname.equals(currentNick)){
            Assert.fail("nick name change failed");
        }
        Spoon.screenshot(gDevice,"change_nick_name");
    }
    @Test
    public void testChangeNickNameMaxCharacter() throws UiObjectNotFoundException {
        MeAction.navToNickName();
        getObjectById(Me.SAMPLE_CONTENT).clearTextField();
        String nickname = getRandomString(60);
        try {
            gDevice.executeShellCommand("input text "+nickname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        clickById(Me.NICKNAME_DONE);
        waitTime(3);
        String currentNick = getObjectById(Me.GETNICKNAMECONTENT).getText();

        if (currentNick.length()!=30){
            Assert.fail("max length is 30");
        }
        Spoon.screenshot(gDevice,"change_nick_name");
    }
    @Test
    public void testChangeNickNameNotSave() throws UiObjectNotFoundException {
        clickById(Me.ID_MAIN_TAB_ME);
        Spoon.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        String nicknameBefore = getObjectById(Me.GETNICKNAMECONTENT).getText();
        clickById(Me.NAV_EDIT_NICKNAME);

        getObjectById(Me.SAMPLE_CONTENT).clearTextField();
        String nickname = getRandomString(20);
        try {
            gDevice.executeShellCommand("input text "+nickname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gDevice.pressBack();
        waitTime(3);
        String currentNick = getObjectById(Me.GETNICKNAMECONTENT).getText();

        if (!nicknameBefore.equals(currentNick)){
            Assert.fail("change nick but not save it");
        }
        Spoon.screenshot(gDevice,"change_nick_name");
    }
}
