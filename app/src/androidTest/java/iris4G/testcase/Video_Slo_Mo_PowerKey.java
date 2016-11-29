package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
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
 * 睡眠时间设置15s 60s 10min Never
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class Video_Slo_Mo_PowerKey extends VP2{
    private static Logger logger = Logger.getLogger(Video_Slo_Mo_PowerKey.class.getName());

    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void testVideo_Slo_Mo_PowerKey() throws Exception {
        Iris4GAction.startCamera();
        CameraAction.navConfig(Iris4GPage.nav_menu[4]);
        Iris4GAction.cameraKey();
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        if (gDevice.isScreenOn() == false &&
                text_exists_match("com.hicam:id/recording_time2")== true)
        {
            logger.info("灭屏成功");
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            waitTime(3);
            if(gDevice.isScreenOn() == true &&
                    clickById("com.hicam:id/recording_time2")== true)
            {
                System.out.println("灭屏成功");
                Iris4GAction.cameraKey();
                logger.info("成功点亮");
            }
            else {
                Spoon.screenshot("isScreenOn","isScreenOn=true");
                Asst.fail("expect screen=true,but is false");
            }
        }
        else {
            Spoon.screenshot("isScreenOn","isScreenOn=false");
            Asst.fail("expect screen=false,but is true");
        }
    }
}
