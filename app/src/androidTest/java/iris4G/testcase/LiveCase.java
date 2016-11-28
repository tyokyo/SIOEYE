package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Asst;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.FileManagerAction;
import iris4G.action.Iris4GAction;
import iris4G.action.VideoNode;
import iris4G.page.Iris4GPage;

/**
 * @Author
 * @Description
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
/*所有视频质量*视频角度*/
public class LiveCase extends VP2{
    /*testL48030fpsSuperWide()
testL48060fpsSuperWide()
testL480120fpsSuperWide()
testL72030fpsSuperWide()
testL72060fpsSuperWide()
testL108030fpsSuperWide()*/
    private void Live(String quality,String angle) throws Exception {
        //String quality = Iris4GPage.video_quality[0];
        //String angle =Iris4GPage.video_Angle[0];
        CameraAction.configVideoQuality(quality);
        CameraAction.configVideoAngle(angle);
        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
        HashSet<String> beforeTakeVideoList = Iris4GAction.FileList("/sdcard/video");
        Iris4GAction.cameraKey();
        CameraAction.cameraRecordTime();
        waitTime(20);
        CameraAction.cameraRecordTime();
        Iris4GAction.cameraKey();
        waitTime(5);
        HashSet<String> afterTakeVideoList = Iris4GAction.FileList("/sdcard/Video");
        HashSet<String> resultHashSet = Iris4GAction.result(afterTakeVideoList, beforeTakeVideoList);
        getObject2ById(Iris4GPage.camera_setting_shortcut_id);
        if (resultHashSet.size()==1) {
            String videoPath = resultHashSet.iterator().next();
            logger.info("new file:"+videoPath);
            String videoName = new File(videoPath).getName();
            VideoNode activeNode = Iris4GAction.VideoInfo(videoPath);
            int height=Integer.parseInt(quality.split("@")[0]);
            if (Iris4GAction.checkVideoInfo(height, activeNode)) {
                logger.info("video info check success-"+videoPath);
                FileManagerAction.playVideoByFileManager(videoName);
                if (text_exists_match("^Can't play this video.*")) {
                    logger.info(videoName+" 播放失败" + "-Can't play this video");
                    clickById("android:id/button1");
                    Asst.fail("Can't play this video");
                }else {
                    logger.info(videoName+" 播放成功");
                }
            }else {
                logger.info("video info check failed"+videoPath);
            }
        }else {
            Asst.fail("video not exist");
        }
    }
    public void testL48030fpsSuperWide() throws Exception {
        Live(Iris4GPage.video_quality[0],Iris4GPage.video_Angle[0]);
    }
    @Test
    public void testA() throws IOException {
        initDevice();
        gDevice.executeShellCommand("am start -n cn.sioeye.sioeyeapp/com.sioeye.sioeyeapp.main.SplashActivity");
    }
}
