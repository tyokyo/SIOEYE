package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import java.io.File;
import java.util.HashSet;
import org.hamcrest.Asst;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.logging.Logger;
import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.FileManagerAction;
import iris4G.action.Iris4GAction;
import iris4G.action.VideoNode;
import iris4G.page.Iris4GPage;

/**
 * @Author yun.yang
 * @Description
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class Lapse_on_off_ScreenCase extends VP2 {
    Logger logger = Logger.getLogger(Lapse_on_off_ScreenCase.class.getName());

    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }

    @Test
    public void testLapse_on_off_Screen() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[5]);
        HashSet<String> beforeTakeVideoList = Iris4GAction.FileList("/sdcard/Video");
        Iris4GAction.cameraKey();
        waitTime(1);
        for (int i = 0; i <= 19; i++){
            waitTime(1);
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);//按电源键20次
            }
        waitTime(10);
        Iris4GAction.cameraKey();
        waitTime(3);
        HashSet<String> afterTakeVideoList = Iris4GAction.FileList("/sdcard/Video");
        HashSet<String> resultHashSet = Iris4GAction.result(afterTakeVideoList, beforeTakeVideoList);
        if (resultHashSet.size() == 1) {
            String videoPath = resultHashSet.iterator().next();
            logger.info("new file:" + videoPath);
            String videoName = new File(videoPath).getName();
            VideoNode activeNode = Iris4GAction.VideoInfo(videoPath);
            FileManagerAction.playVideoByFileManager(videoName);
            if (activeNode.getDuration() <30) {
                Asst.fail("max duration is 30 seconds");
            }else {
                logger.info("this_case_is_pass");
            }
        } else {
            Asst.fail("Lapse_Video_Not_Exist");
        }
        gDevice.pressBack();
    }
}
