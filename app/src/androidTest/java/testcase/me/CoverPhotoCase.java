package testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ckt.base.VP2;
import ckt.tools.Spoon2;
import page.App;
import page.Me;

/**
 * Created by elon on 2016/10/11.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class CoverPhotoCase extends VP2 {
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME);
    }
    @Test
    public void testChangeCoverPhotoByCamera(){
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickById(Me.CHANGECOVERPHOTO);
        clickByText("Camera");
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        clickById(Me.ID_CAMERA_SELECT);
        clickByText("Done");
        gDevice.wait(Until.gone(By.res(Me.ID_IMAGE_CHANGE)),20000);
        waitTime(5);
        Spoon2.screenshot(gDevice,"change_head_img");
    }
}
