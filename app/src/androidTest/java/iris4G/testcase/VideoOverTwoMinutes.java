package iris4G.testcase;

import android.os.RemoteException;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.HashSet;
import java.util.logging.Logger;

import ckt.base.VP;
import ckt.base.VP2;
import ckt.tools.Common;
import iris4G.action.CameraAction;
import iris4G.action.FileManagerAction;
import iris4G.action.Iris4GAction;
import iris4G.action.VideoNode;
import iris4G.page.NavPage;

/**
 * @Author caiBin
 * @Description
 * case：普通录像超过两分钟，录制结果无异常
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class VideoOverTwoMinutes extends VP2 {
    public static int WaitTime = 125;
    private static Logger logger = Logger.getLogger(VideoOverTwoMinutes.class.getName());

    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void testVideoOverTwoMinutes() throws Exception {
        if(!gDevice.isScreenOn()){
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            logger.info("make screen on");
        }
        initDevice();
        Iris4GAction.startCamera();
        HashSet<String> beforeTakeVideoList = Iris4GAction.FileList("/sdcard/video");

       //增加切换到Video模式的操作
        CameraAction.navConfig(NavPage.navConfig_Video);
        Iris4GAction.cameraKey();
        CameraAction.cameraRecordTime();
        waitTime(WaitTime);
        CameraAction.cameraRecordTime();
        Iris4GAction.cameraKey();
        waitTime(2);
        HashSet<String> afterTakeVideoList = Iris4GAction.FileList("/sdcard/Video");
        HashSet<String> resultHashSet = Iris4GAction.result(afterTakeVideoList, beforeTakeVideoList);

        if (resultHashSet.size()==1 ) {
            String videoPath = resultHashSet.iterator().next();
            logger.info("new file:"+videoPath);
            String videoName = new File(videoPath).getName();
            Iris4GAction.VideoInfo(videoPath);
            VideoNode videoNode = Iris4GAction.VideoInfo(videoPath);
            FileManagerAction.playVideoByFileManager(videoName);

            if (text_exists_match("^Can't play this video.*")) {
                logger.info(videoName+" play fail " + "-Can't play this video");
                clickById("android:id/button1");
                Asst.fail("Can't play this video");
            }
            else if(videoNode.getDuration()<(WaitTime-2)){
                logger.info("expect video duration "+WaitTime);
                logger.info("active video duration"+videoNode.getDuration());
                Asst.fail("total video duration-Error");
            }else {
                logger.info(videoName+" play success");
            }
        }else {
            Asst.fail("Video-Count=1;Error");
        }
    }
}
