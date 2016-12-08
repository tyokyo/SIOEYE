package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.FileManagerAction;
import iris4G.action.Iris4GAction;
import iris4G.action.VideoNode;
import iris4G.page.Iris4GPage;

/**
 * @Author elon
 * @Description
   时间间隔设置为2S/5s/10s
   开始延时录像
   录像过程中原始视频2S对应输出视频的1S
 */
/*所有视频质量*视频角度*延时的组合*/
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class LapseCase extends VP2 {
    Logger logger = Logger.getLogger(LapseCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    private void Lapse(String quality,String lapse_time,int angleIndex) throws Exception {
        boolean result = true;
        //String quality = Iris4GPage.video_quality[0];
        //int angleSize = Iris4GPage.video_Angle.length;
        //for (int t = 0; t < angleSize; t++) {

        String angle = Iris4GPage.video_Angle[angleIndex];
        logger.info("start to test angle-" + angle);
        Iris4GAction.initIris4G();
        CameraAction.configVideoQuality(quality);
        CameraAction.configVideoAngle(angleIndex);
        //CameraAction.configTimeLapse(Iris4GPage.lapse_time[0]);
        CameraAction.configTimeLapse(lapse_time);

        //更改成功，相机左上角显示2s/5s/10s
        Asst.assertEquals(lapse_time,lapse_time,getTex(Iris4GPage.camera_mode_label).trim());

        int int_Lapse=Integer.parseInt(lapse_time.replace("s",""));
        HashSet<String> beforeTakeVideoList = Iris4GAction.FileList("/sdcard/video");
        Iris4GAction.cameraKey();
        waitTime(10);
        CameraAction.cameraRecordTime();
        waitTime(5);
        boolean lapStatus = true;
        ArrayList<Boolean> results = new ArrayList<>();
        int total = 20;
        for (int i = 0; i < total; i++) {
            lapStatus = CameraAction.checkLapseValue((int_Lapse));
            results.add(lapStatus);
            waitTime(2);
        }
        CameraAction.cameraRecordTime();
        Iris4GAction.cameraKey();
        waitTime(3);
        int countPass=0;
        int countFail=0;
        for (boolean status:results) {
            if (status==true){
                countPass=countPass+1;
            }else {
                countFail=countFail+1;
            }
        }
        logger.info(String.format("%s|%s",countPass,total));
        double ret =countPass/(double)total;
        if (ret>0.9) {
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
                        logger.info(videoName + " play fail" + "-Can't play this video");
                        clickById("android:id/button1");
                        Asst.fail("Can't play this video");
                    } else {
                        logger.info(videoName + " play success");
                        result = true;
                    }
                } else {
                    logger.info("video info check failed" + videoPath);
                    Asst.fail("video info check failed" + videoPath);
                }
            } else {
                Asst.fail("video file error");
            }
        } else {
            Asst.fail("checkLapseValue:"+ret);
        }
    }

    /* 720@30FPS  -2s - All Angle[SuperWide Wide Medium]*/
    @Test
    public void testTLap2s72030SuperWide() throws Exception {
        Lapse(Iris4GPage.video_quality[0],Iris4GPage.lapse_time[0],0);
    }
    @Test
    public void testTLap2s72030Wide() throws Exception {
        Lapse(Iris4GPage.video_quality[0],Iris4GPage.lapse_time[0],1);
    }
    @Test
    public void testTLap2s72030Medium() throws Exception {
        Lapse(Iris4GPage.video_quality[0],Iris4GPage.lapse_time[0],2);
    }
    @Test
    /* 720@30FPS  -3s - All Angle[SuperWide Wide Medium]*/
    public void testTLap3s72030SuperWide() throws Exception {
        Lapse(Iris4GPage.video_quality[0],Iris4GPage.lapse_time[1],0);
    }
    @Test
    public void testTLap3s72030Wide() throws Exception {
        Lapse(Iris4GPage.video_quality[0],Iris4GPage.lapse_time[1],1);
    }
    @Test
    public void testTLap3s72030Medium() throws Exception {
        Lapse(Iris4GPage.video_quality[0],Iris4GPage.lapse_time[1],2);
    }

    /* 720@30FPS  -5s - All Angle[SuperWide Wide Medium]*/
    @Test
    public void testTLap5s72030SuperWide() throws Exception {
        Lapse(Iris4GPage.video_quality[0],Iris4GPage.lapse_time[2],0);
    }
    @Test
    public void testTLap5s72030Wide() throws Exception {
        Lapse(Iris4GPage.video_quality[0],Iris4GPage.lapse_time[2],1);
    }
    @Test
    public void testTLap5s72030Medium() throws Exception {
        Lapse(Iris4GPage.video_quality[0],Iris4GPage.lapse_time[2],2);
    }
    /* 720@30FPS  -10s - All Angle[SuperWide Wide Medium]*/
    @Test
    public void testTLap10s72030SuperWide() throws Exception {
        Lapse(Iris4GPage.video_quality[0],Iris4GPage.lapse_time[3],0);
    }
    @Test
    public void testTLap10s72030Wide() throws Exception {
        Lapse(Iris4GPage.video_quality[0],Iris4GPage.lapse_time[3],1);
    }
    @Test
    public void testTLap10s72030Medium() throws Exception {
        Lapse(Iris4GPage.video_quality[0],Iris4GPage.lapse_time[3],2);
    }

    /* 720@60FPS  -2s - All Angle[SuperWide Wide Medium]*/
    @Test
    public void testTLap2s72060SuperWide() throws Exception {
        Lapse(Iris4GPage.video_quality[1],Iris4GPage.lapse_time[0],0);
    }
    @Test
    public void testTLap2s72060Wide() throws Exception {
        Lapse(Iris4GPage.video_quality[1],Iris4GPage.lapse_time[0],1);
    }
    @Test
    public void testTLap2s72060Medium() throws Exception {
        Lapse(Iris4GPage.video_quality[1],Iris4GPage.lapse_time[0],2);
    }
    /* 720@60FPS  -3s - All Angle[SuperWide Wide Medium]*/
    @Test
    public void testTLap3s72060SuperWide() throws Exception {
        Lapse(Iris4GPage.video_quality[1],Iris4GPage.lapse_time[1],0);
    }
    @Test
    public void testTLap3s72060Wide() throws Exception {
        Lapse(Iris4GPage.video_quality[1],Iris4GPage.lapse_time[1],1);
    }
    @Test
    public void testTLap3s72060Medium() throws Exception {
        Lapse(Iris4GPage.video_quality[1],Iris4GPage.lapse_time[1],2);
    }

    /* 720@60FPS  -5s - All Angle[SuperWide Wide Medium]*/
    @Test
    public void testTLap5s72060SuperWide() throws Exception {
        Lapse(Iris4GPage.video_quality[1],Iris4GPage.lapse_time[2],0);
    }
    @Test
    public void testTLap5s72060Wide() throws Exception {
        Lapse(Iris4GPage.video_quality[1],Iris4GPage.lapse_time[2],1);
    }
    @Test
    public void testTLap5s72060Medium() throws Exception {
        Lapse(Iris4GPage.video_quality[1],Iris4GPage.lapse_time[2],2);
    }
    /* 720@60FPS  -10s - All Angle[SuperWide Wide Medium]*/
    @Test
    public void testTLap10s72060SuperWide() throws Exception {
        Lapse(Iris4GPage.video_quality[1],Iris4GPage.lapse_time[3],0);
    }
    @Test
    public void testTLap10s72060Wide() throws Exception {
        Lapse(Iris4GPage.video_quality[1],Iris4GPage.lapse_time[3],1);
    }
    @Test
    public void testTLap10s72060Medium() throws Exception {
        Lapse(Iris4GPage.video_quality[1],Iris4GPage.lapse_time[3],2);
    }

    /* 1080@30FPS  -2s - All Angle[SuperWide Wide Medium]*/
    @Test
    public void testTLap2s108030SuperWide() throws Exception {
        Lapse(Iris4GPage.video_quality[2],Iris4GPage.lapse_time[0],0);
    }
    @Test
    public void testTLap2s108030Wide() throws Exception {
        Lapse(Iris4GPage.video_quality[2],Iris4GPage.lapse_time[0],1);
    }
    @Test
    public void testTLap2s108030Medium() throws Exception {
        Lapse(Iris4GPage.video_quality[2],Iris4GPage.lapse_time[0],2);
    }
    /* 1080@30FPS  -3s - All Angle[SuperWide Wide Medium]*/
    @Test
    public void testTLap3s108030SuperWide() throws Exception {
        Lapse(Iris4GPage.video_quality[2],Iris4GPage.lapse_time[1],0);
    }
    @Test
    public void testTLap3s108030Wide() throws Exception {
        Lapse(Iris4GPage.video_quality[2],Iris4GPage.lapse_time[1],1);
    }
    @Test
    public void testTLap3s108030Medium() throws Exception {
        Lapse(Iris4GPage.video_quality[2],Iris4GPage.lapse_time[1],2);
    }

    /* 1080@30FPS  -5s - All Angle[SuperWide Wide Medium]*/
    @Test
    public void testTLap5s108030SuperWide() throws Exception {
        Lapse(Iris4GPage.video_quality[2],Iris4GPage.lapse_time[2],0);
    }
    @Test
    public void testTLap5s108030Wide() throws Exception {
        Lapse(Iris4GPage.video_quality[2],Iris4GPage.lapse_time[2],1);
    }
    @Test
    public void testTLap5s108030Medium() throws Exception {
        Lapse(Iris4GPage.video_quality[2],Iris4GPage.lapse_time[2],2);
    }
    /* 1080@30FPS  -10s - All Angle[SuperWide Wide Medium]*/
    @Test
    public void testTLap10s108030SuperWide() throws Exception {
        Lapse(Iris4GPage.video_quality[2],Iris4GPage.lapse_time[3],0);
    }
    @Test
    public void testTLap10s108030Wide() throws Exception {
        Lapse(Iris4GPage.video_quality[2],Iris4GPage.lapse_time[3],1);
    }
    @Test
    public void testTLap10s108030Medium() throws Exception {
        Lapse(Iris4GPage.video_quality[2],Iris4GPage.lapse_time[3],2);
    }
    @Test
    public void testOnOffScreen() throws Exception {
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

    @Test
    public void testLapseOver2m() throws Exception {
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
    /*
testTLap2s72030AllAngle()
testTLap2s72060AllAngle()
testTLap2s108030AllAngle()

testTLap3s72030AllAngle()
testTLap3s72060AllAngle()
testTLap3s108030AllAngle()

testTLap5s72030AllAngle()
testTLap5s72060AllAngle()
testTLap5s108030AllAngle()

testTLap10s72030AllAngle()
testTLap10s72060AllAngle()
testTLap10s108030AllAngle()*/
}
