package iris4G.testcase;

import org.hamcrest.Asst;

import java.io.File;
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
/*所有视频质量*视频角度*延时的组合*/
public class LapseCase extends VP2 {
    /*testTLap2s48030AllAngle()
testTLap2s48060AllAngle()
testTLap2s480120AllAngle()
testTLap2s72030AllAngle()
testTLap2s72060AllAngle()
testTLap2s108030AllAngle()
testTLap3s48030AllAngle()
testTLap3s48060AllAngle()
testTLap3s480120AllAngle()
testTLap3s72030AllAngle()
testTLap3s72060AllAngle()
testTLap3s108030AllAngle()
testTLap5s48030AllAngle()
testTLap5s48060AllAngle()
testTLap5s480120AllAngle()
testTLap5s72030AllAngle()
testTLap5s72060AllAngle()
testTLap5s108030AllAngle()
testTLap10s48030AllAngle()
testTLap10s48060AllAngle()
testTLap10s480120AllAngle()
testTLap10s72030AllAngle()
testTLap10s72060AllAngle()
testTLap10s108030AllAngle()*/
    private void Lapse(String quality,String lapse_time) throws Exception {
        boolean result = true;
        //String quality = Iris4GPage.video_quality[0];
        int angleSize = Iris4GPage.video_Angle.length;
        for (int t = 0; t < angleSize; t++) {
            String angle = Iris4GPage.video_Angle[t];
            logger.info("start to test angle-" + angle);
            Iris4GAction.initIris4G();
            CameraAction.configVideoQuality(quality);
            CameraAction.configVideoAngle(t);
            //CameraAction.configTimeLapse(Iris4GPage.lapse_time[0]);
            CameraAction.configTimeLapse(lapse_time);
            int int_Lapse=Integer.parseInt(lapse_time.replace("s",""));
            HashSet<String> beforeTakeVideoList = Iris4GAction.FileList("/sdcard/video");
            Iris4GAction.cameraKey();
            waitTime(10);
            CameraAction.cameraRecordTime();
            waitTime(5);
            boolean lapStatus = true;
            for (int i = 0; i < 20; i++) {
                lapStatus = CameraAction.checkLapseValue((int_Lapse));
                waitTime(2);
            }
            CameraAction.cameraRecordTime();
            Iris4GAction.cameraKey();
            waitTime(3);
            if (lapStatus) {
                HashSet<String> afterTakeVideoList = Iris4GAction.FileList("/sdcard/Video");
                HashSet<String> resultHashSet = Iris4GAction.result(afterTakeVideoList, beforeTakeVideoList);
                if (resultHashSet.size() == 1) {
                    String videoPath = resultHashSet.iterator().next();
                    logger.info("new file:" + videoPath);
                    String videoName = new File(videoPath).getName();
                    VideoNode activeNode = Iris4GAction.VideoInfo(videoPath);
                    int height=Integer.parseInt(quality.split("@")[0]);
                    if (Iris4GAction.checkVideoInfo(height, activeNode)) {
                        logger.info("video info check success-" + videoPath);
                        FileManagerAction.playVideoByFileManager(videoName);
                        if (text_exists_match("^Can't play this video.*")) {
                            logger.info(videoName + " 播放失败" + "-Can't play this video");
                            clickById("android:id/button1");
                            Asst.fail("Can't play this video");
                        } else {
                            logger.info(videoName + " 播放成功");
                            result = true;
                        }
                    } else {
                        logger.info("video info check failed" + videoPath);
                        result = false;
                        break;
                    }
                } else {
                    result = false;
                    break;
                }
            } else {
                result = false;
                break;
            }
        }
        if (result) {

        } else {
            Asst.fail("not all pass");
        }
    }
    public void testTLap2s48030AllAngle() throws Exception {
        /* 480@30FPS  -2s  */
        Lapse(Iris4GPage.video_quality[0],Iris4GPage.lapse_time[0]);
    }
}
