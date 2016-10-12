package testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
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
public class HeadImgCase extends VP2 {
    private Logger logger =  Logger.getLogger(HeadImgCase.this.getClass().getName());
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

        if (getObjectById(Me.ID_CAMERA_SELECT).exists()){
            clickById(Me.ID_CAMERA_SELECT);
        }
        if (getUiObjectByText("OK").exists()){
            clickByText("OK");
        }

        if (getUiObjectByText("OK").exists()){
            clickByText("OK");
        }
        if (getUiObjectByText("Done").exists()){
            clickByText("Done");
        }
        gDevice.wait(Until.gone(By.res(Me.ID_IMAGE_CHANGE)),20000);
        waitTime(5);
        Spoon2.screenshot(gDevice,"change_head_img");
        //killAppByPackage(App.SIOEYE_PACKAGE_NAME);
    }
    @Test
    public void editHeadImgFromCameraCancel() throws InterruptedException {
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickById(Me.ID_HEAD_IMAGE);
        clickByText("Camera");
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        if (getObjectById(Me.ID_CAMERA_SELECT).exists()){
            clickById(Me.ID_CAMERA_SELECT);
        }
        if (getUiObjectByText("OK").exists()){
            clickByText("OK");
        }
        gDevice.pressBack();
        waitTime(5);
        Spoon2.screenshot(gDevice,"change_head_img");
        //killAppByPackage(App.SIOEYE_PACKAGE_NAME);
    }
    @Test
    public void editHeadImgFromAlbum() throws UiObjectNotFoundException, InterruptedException {
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");

        clickById(Me.ID_USER_EDIT);
        clickById(Me.ID_HEAD_IMAGE);
        clickByText("Album");
        waitTime(5);
        gDevice.click(500,500);
        waitTime(5);
        gDevice.click(500,500);
        waitTime(5);
        if (getUiObjectByText("OK").exists()){
            clickByText("OK");
        }
        if (getUiObjectByText("Done").exists()){
            clickByText("Done");
        }
        waitTime(5);
        Spoon2.screenshot(gDevice,"change_head_img");
        //killAppByPackage(App.SIOEYE_PACKAGE_NAME);
    }
    @Test
    public void editHeadImgFromAlbumCancel() throws UiObjectNotFoundException, InterruptedException {
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickById(Me.ID_HEAD_IMAGE);
        clickByText("Album");
        waitTime(5);
        gDevice.click(500,500);
        waitTime(5);
        gDevice.click(500,500);
        waitTime(5);
        gDevice.pressBack();

        waitTime(5);
        Spoon2.screenshot(gDevice,"change_head_img");
        //killAppByPackage(App.SIOEYE_PACKAGE_NAME);
    }


}
