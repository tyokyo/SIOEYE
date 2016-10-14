package testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import ckt.base.VP2;
import ckt.tools.Spoon2;
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
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME);
    }
    @Test
    public void testChangeNickNameLessThan4Character() throws UiObjectNotFoundException {
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickByText("Nick Name");
        getObjectById(Me.SAMPLE_CONTENT).clearTextField();
        getObjectById(Me.SAMPLE_CONTENT).setText(getRandomString(3));
        clickByText("Done");
        getUiObjectByText("Done");
        getUiObjectByText("Done");
        Spoon2.screenshot(gDevice,"change_nick_name");
    }
    @Test
    public void testChangeNickNameMoreThan4Character() throws UiObjectNotFoundException {
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickByText("Nick Name");
        getObjectById(Me.SAMPLE_CONTENT).clearTextField();
        String nickname = getRandomString(4);
        getObjectById(Me.SAMPLE_CONTENT).setText(nickname);
        clickByText("Done");
        waitTime(3);
        String currentNick = getObjectById(Me.GETNICKNAMECONTENT).getText();
        if (!nickname.equals(currentNick)){
            Assert.fail("nick name change failed");
        }
        Spoon2.screenshot(gDevice,"change_nick_name");
    }
    @Test
    public void testChangeNickNameMaxCharacter() throws UiObjectNotFoundException {
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickByText("Nick Name");
        getObjectById(Me.SAMPLE_CONTENT).clearTextField();
        String nickname = getRandomString(60);
        try {
            gDevice.executeShellCommand("input text "+nickname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        clickByText("Done");
        waitTime(3);
        String currentNick = getObjectById(Me.GETNICKNAMECONTENT).getText();

        if (currentNick.length()!=30){
            Assert.fail("max length is 30");
        }
        Spoon2.screenshot(gDevice,"change_nick_name");
    }
    @Test
    public void testChangeNickNameNotSave() throws UiObjectNotFoundException {
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);

        String nicknameBefore = getObjectById(Me.GETNICKNAMECONTENT).getText();

        clickByText("Nick Name");
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
        Spoon2.screenshot(gDevice,"change_nick_name");
    }
}
