package testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import com.sioeye.MainActivity;
import com.squareup.spoon.Spoon;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.logging.Logger;

import ckt.tools.Spoon2;
import ckt.base.VP2;
import page.App;
import page.Me;

/**
 * Created by elon on 2016/10/11.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class PersonalCase extends VP2 {
    private Logger logger =  Logger.getLogger(PersonalCase.this.getClass().getName());
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME);
    }

    @Test
    public void editHeadImgFromCamera() throws InterruptedException {
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickById(Me.ID_HEAD_IMAGE);
        clickByText("Camera");
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        clickById(Me.ID_CAMERA_SELECT);
        clickByText("Done");
        gDevice.wait(Until.gone(By.res(Me.ID_IMAGE_CHANGE)),20000);
        waitTime(5);
        Spoon2.screenshot(gDevice,"change_head_img");
        killAppByPackage(App.SIOEYE_PACKAGE_NAME);
    }
    @Test
    public void editHeadImgFromAlbum() throws UiObjectNotFoundException, InterruptedException {
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickById(Me.ID_HEAD_IMAGE);
        clickByText("Album");
        if (getUiObjectByText("Photos").exists()){
            clickByText("Photos");
            scrollAndGetUIObject("screen");
            clickByClass("android.view.View",2);
        }
        clickByText("Done");
        gDevice.wait(Until.gone(By.res(Me.ID_IMAGE_CHANGE)),20000);
        waitTime(5);
        Spoon2.screenshot(gDevice,"change_head_img");
        killAppByPackage(App.SIOEYE_PACKAGE_NAME);
    }
}
