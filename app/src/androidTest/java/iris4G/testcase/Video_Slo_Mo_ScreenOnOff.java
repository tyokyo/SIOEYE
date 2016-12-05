package iris4G.testcase;

import android.os.RemoteException;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;

/**
 * @Author yun.yang
 * @Description
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class Video_Slo_Mo_ScreenOnOff extends VP2 {
    private static Logger logger = Logger.getLogger(Video_Slo_Mo_ScreenOnOff.class.getName());

    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void  testAlo_Mo_ScreenOnOff() throws Exception {
        gDevice.wakeUp();
        initDevice();
        Iris4GAction.startCamera();
        CameraAction.navConfig(Iris4GPage.nav_menu[4]);
        Iris4GAction.cameraKey();
        waitTime(3);
        for(int i= 1;i<11;i++){
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            waitTime(3);
        }
        Iris4GAction.cameraKey();
    }
}
