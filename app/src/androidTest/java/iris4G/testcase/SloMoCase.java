package iris4G.testcase;

import org.hamcrest.Asst;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.FileManagerAction;
import iris4G.action.Iris4GAction;
import iris4G.action.VideoNode;
import iris4G.page.Iris4GPage;

/**
 * @Author elon
 * @Description
 *
 */
/*慢速录像*视频角度*/
public class SloMoCase extends VP2 {
    private void sloMo(String angle) throws Exception {
        boolean result = true;
        //String angle = Iris4GPage.video_Angle[0];
        Iris4GAction.startCamera();
        CameraAction.configVideoAngle(angle);
        CameraAction.navConfig(Iris4GPage.nav_menu[4]);

        HashSet<String> beforeTakeVideoList = Iris4GAction.FileList("/sdcard/video");
        Iris4GAction.cameraKey();
        waitTime(10);
        CameraAction.cameraRecordTime();
        waitTime(5);
        boolean moStatus = true;
        for (int i = 0; i < 20; i++) {
            moStatus = CameraAction.checkMoValue(4);
            waitTime(2);
        }
        CameraAction.cameraRecordTime();
        Iris4GAction.cameraKey();
        waitTime(3);

        if (moStatus) {
            HashSet<String> afterTakeVideoList = Iris4GAction.FileList("/sdcard/Video");
            HashSet<String> resultHashSet = Iris4GAction.result(afterTakeVideoList, beforeTakeVideoList);
            if (resultHashSet.size()==1) {
                String videoPath = resultHashSet.iterator().next();
                logger.info("new file:"+videoPath);
                String videoName = new File(videoPath).getName();
                VideoNode activeNode = Iris4GAction.VideoInfo(videoPath);
                if (Iris4GAction.checkVideoInfo(480, activeNode)) {
                    logger.info("video info check success-"+videoPath);
                    FileManagerAction.playVideoByFileManager(videoName);
                    if (text_exists_match("^Can't play this video.*")) {
                        logger.info(videoName+" 播放失败" + "-Can't play this video");
                        clickById("android:id/button1");
                        Asst.fail("Can't play this video");
                        throw new Exception("FindObject" + "Can't play this video");
                    }else {
                        logger.info(videoName+" 播放成功");
                        result= true;
                    }
                }else {
                    logger.info("video info check failed"+videoPath);
                    result= false;
                }
            }else {
                result= false;
            }
        }else {
            result= false;
        }
        if (!result) {
            Asst.fail("check-fail");
        }
    }
    @Test
    public void testSloMoSuperWide() throws Exception {
        sloMo(Iris4GPage.video_Angle[0]);
    }
    @Test
    public void testSloMoWide() throws Exception {
        sloMo(Iris4GPage.video_Angle[1]);
    }
    @Test
    public void testSloMoMedium() throws Exception {
        sloMo(Iris4GPage.video_Angle[2]);
    }
}
