package iris4G.testcase;

import android.os.RemoteException;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.Iris4GAction;

/**
 * @Author caiBin
 * @Description
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class testReview_Screen_on_off extends VP2{
    Logger logger = Logger.getLogger(testReview_Screen_on_off.class.getName());

    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void test() throws RemoteException {
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);         //按电源键6次
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);   		//灭屏1分钟
        waitTime(60);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        if(gDevice.isScreenOn()){
            System.out.println("testReview_Screen_onoff case was passed");
        }else {
            Asst.fail("testReview_Screen_onoff failed");
        }
    }
}
