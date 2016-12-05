package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;

/**
 * @Author yun.yang
 * @Description
 * 20min慢速录制自动停止
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class Video_Slo_Mo_20m extends VP2{
    private static Logger logger = Logger.getLogger(Video_Slo_Mo_20m.class.getName());

    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void test() throws Exception {
        String navMenu = Iris4GPage.nav_menu[4];
        CameraAction.navConfig(navMenu);
        HashSet<String> beforeTakeVideoList = Iris4GAction.FileList("/sdcard/video");
        Iris4GAction.cameraKey();
        logger.info("wait 20min");
        waitTime(1201);
        HashSet<String> afterTakeVideoList = Iris4GAction.FileList("/sdcard/Video");
        HashSet<String> resultHashSet = Iris4GAction.result(afterTakeVideoList, beforeTakeVideoList);
        boolean lx1= id_exists("com.hicam:id/recording_time2");
        if (lx1 == true || resultHashSet.size() != 1)
        {
            logger.info("close failed or save video failed");
            Iris4GAction.cameraKey();
            Asst.fail("close failed or save video failed");
        }
        Spoon.screenshot("max_record","Maximum length os SLO-MO has been reached");
    }
}
