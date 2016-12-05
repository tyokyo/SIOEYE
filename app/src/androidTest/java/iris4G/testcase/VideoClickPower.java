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

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.FileManagerAction;
import iris4G.action.Iris4GAction;

/**
 * @Author caiBin
 * @Description
 *  case:普通录像中，单击power键，熄屏，正常录制；
 * 	再次点击power键，亮屏，正常录制
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class VideoClickPower extends VP2{
    public static int WaitTime = 10;
    private static Logger logger = Logger.getLogger(VideoClickPower.class.getName());

    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void testVideoClickPower() throws Exception {
        if(!gDevice.isScreenOn()){
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            logger.info("make screen on");
        }
        initDevice();
        Iris4GAction.startCamera();
        HashSet<String> beforeTakeVideoList = Iris4GAction.FileList("/sdcard/video");
        logger.info("record when screen is on "+WaitTime+" seconds");
        Iris4GAction.cameraKey();

        CameraAction.cameraRecordTime();
        waitTime(WaitTime);

        logger.info("record when screen is off "+WaitTime+" seconds");
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(WaitTime);

        logger.info("record when screen is on "+WaitTime+" seconds");
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(WaitTime);

        CameraAction.cameraRecordTime();
        Iris4GAction.cameraKey();

        logger.info("total record "+3*WaitTime+" seconds");
        waitTime(3);
        HashSet<String> afterTakeVideoList = Iris4GAction.FileList("/sdcard/Video");
        HashSet<String> resultHashSet = Iris4GAction.result(afterTakeVideoList, beforeTakeVideoList);

        if (resultHashSet.size()==1) {
            String videoPath = resultHashSet.iterator().next();
            logger.info("new file:"+videoPath);
            String videoName = new File(videoPath).getName();
            Iris4GAction.VideoInfo(videoPath);
            FileManagerAction.playVideoByFileManager(videoName);

            if (text_exists_match("^Can't play this video.*")) {
                logger.info(videoName+" play fail " + "-Can't play this video");
                clickById("android:id/button1");
                Asst.fail("Can't play this video");
            }else {
                logger.info(videoName+" play success ");
            }
        }else {
            Asst.fail("Video-Count=1:Error");
        }
    }
}
