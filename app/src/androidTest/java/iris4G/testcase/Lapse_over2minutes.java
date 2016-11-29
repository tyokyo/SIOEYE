package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.action.VideoNode;
import iris4G.page.Iris4GPage;

/**
 * @Author yun.yang
 * @Description
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class Lapse_over2minutes extends VP2 {
    Logger logger = Logger.getLogger(Lapse_over2minutes.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void test() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[5]);
        waitTime(5);

        HashSet<String> beforeTakeVideoList = Iris4GAction.FileList("/sdcard/video");
        Iris4GAction.cameraKey();
        waitTime(128);
        Iris4GAction.cameraKey();
        waitTime(2);
        HashSet<String> afterTakeVideoList = Iris4GAction.FileList("/sdcard/Video");
        HashSet<String> resultHashSet = Iris4GAction.result(afterTakeVideoList, beforeTakeVideoList);

        if (resultHashSet.size()==1) {
            String videopath = resultHashSet.iterator().next();
            logger.info("new file:"+videopath);
            VideoNode videoNode = Iris4GAction.VideoInfo(videopath);
            if(videoNode.getDuration()<120){
                Asst.fail("max duration is 120 seconds");
            }
        }else {
            Asst.fail("Video-Count=1:Error");
        }
    }
}
