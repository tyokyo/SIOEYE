package testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import ckt.base.VP2;
import page.App;
import page.Me;

/**
 * Created by elon on 2016/10/13.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class EmailCase extends VP2 {
    String result="";
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    public String getEmailAddress() throws UiObjectNotFoundException {
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(Me.EMAIL_ID));
        String em = u.getChild(new UiSelector().resourceId(Me.ABOUT_ME_CONTENT_TEXT)).getText();
        return  em;
    }
    @Test
    public void testModifyEmailAddress() throws UiObjectNotFoundException {
        clickByText("Me");
        String user_id = getObjectById(Me.SIOEYE_USER_ID).getText();
        Spoon.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        String email = getEmailAddress();
        clickByText("Email");
        getObjectById(Me.SAMPLE_CONTENT).clearTextField();
        String modifyAddress = "add"+email;
        getObjectById(Me.SAMPLE_CONTENT).setText(modifyAddress);
        clickByText("Done");
        String afterModify=getEmailAddress();
        Assert.assertEquals("email address modify success",afterModify,modifyAddress);

        //log out
        gDevice.pressBack();
        clickByText("Settings");
        clickById(Me.LOG_OUT);
        clickByText("OK");
        gDevice.wait(Until.gone(By.res(Me.ID_IMAGE_CHANGE)),20000);
        //login with new email address
        clickByText("Me");
        clickByText("Login");
        getObjectById(Me.INPUT_USERNAME).setText(modifyAddress);
        getObjectById(Me.INPUT_PASSWORD).setText("123456789");
        clickById(Me.LOGIN_SIGN_UP);
        gDevice.wait(Until.gone(By.res(Me.LOGIN_SIGN_UP)),20000);
        Assert.assertEquals(true,getUiObjectByText("Me").exists());
    }
    @Test
    public void testModifyEmailAddressAlreadyExist() throws UiObjectNotFoundException, IOException, InterruptedException {
        clickByText("Me");
        String user_id = getObjectById(Me.SIOEYE_USER_ID).getText();
        Spoon.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        String email = getEmailAddress();
        clickByText("Email");
        getObjectById(Me.SAMPLE_CONTENT).clearTextField();
        String modifyAddress = "ge.liu@ckt.com";
        getObjectById(Me.SAMPLE_CONTENT).setText(modifyAddress);
        clickByText("Done");
        Assert.assertTrue("same emali address can not be set",getObjectById(Me.SAMPLE_CONTENT).exists());
        gDevice.pressBack();
    }
}
