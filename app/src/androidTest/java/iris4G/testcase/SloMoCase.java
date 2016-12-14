package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import com.squareup.spoon.Spoon;

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
import iris4G.action.VideoNode;
import iris4G.page.Iris4GPage;
import iris4G.page.NavPage;

/**
 * @Author elon
 * @Description
 * 慢速录像*视频角度
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class SloMoCase extends VP2 {
    Logger logger = Logger.getLogger(SloMoCase.class.getName());

    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }

    private void sloMo(String angle) throws Exception {
        boolean result = true;
        //String angle = Iris4GPage.video_Angle[0];
        Iris4GAction.startCamera();
        CameraAction.configVideoAngle(NavPage.navConfig_Slo_Mo,angle);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);

        //验证视频质量显示
        Asst.assertEquals("480@120FPS","480@120",getTex(Iris4GPage.info).trim());

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
            if (resultHashSet.size() == 1) {
                String videoPath = resultHashSet.iterator().next();
                logger.info("new file:" + videoPath);
                String videoName = new File(videoPath).getName();
                VideoNode activeNode = Iris4GAction.VideoInfo(videoPath);
                if (Iris4GAction.checkVideoInfo(480, activeNode)) {
                    logger.info("video info check success-" + videoPath);
                    FileManagerAction.playVideoByFileManager(videoName);
                    if (text_exists_match("^Can't play this video.*")) {
                        logger.info(videoName + " play fail " + "-Can't play this video");
                        clickById("android:id/button1");
                        Asst.fail("Can't play this video");
                        throw new Exception("FindObject" + "Can't play this video");
                    } else {
                        logger.info(videoName + " play success");
                        result = true;
                    }
                } else {
                    logger.info("video info check failed" + videoPath);
                    result = false;
                }
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        if (!result) {
            Asst.fail("check-fail");
        }
    }

    @Test
    public void testSloMoSuperWide() throws Exception {
        sloMo(NavPage.angleSuperWide);
    }

    @Test
    public void testSloMoWide() throws Exception {
        sloMo(NavPage.angleWide);
    }

    @Test
    public void testSloMoMedium() throws Exception {
        sloMo(NavPage.angleMedium);
    }
    //* 20min慢速录制自动停止
    @Test
    public void testVideoSloMo20m() throws Exception {
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
    //亮灭屏操作
    @Test
    public void testVideoSloMoPowerKey() throws Exception {
        Iris4GAction.startCamera();
        CameraAction.navConfig(Iris4GPage.nav_menu[4]);
        Iris4GAction.cameraKey();
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        if (gDevice.isScreenOn() == false &&
                id_exists("com.hicam:id/recording_time2")== true)
        {
            logger.info("screen 0ff success");
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            waitTime(3);
            if(gDevice.isScreenOn() == true &&
                    id_exists("com.hicam:id/recording_time2")== true)
            {
                logger.info("screen 0ff success");
                Iris4GAction.cameraKey();
                logger.info("screen on");
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
    //多次亮灭屏操作
    @Test
    public void  testSloMoScreenOnOff() throws Exception {
        gDevice.wakeUp();
        initDevice();
        Iris4GAction.startCamera();
        CameraAction.navConfig(Iris4GPage.nav_menu[4]);
        Iris4GAction.cameraKey();
        waitTime(3);
        for(int i= 1;i<11;i++){
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            waitTime(3);
        }
        Iris4GAction.cameraKey();
    }
    @Test
    public void testSetSloMoSuperWide() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Slo_Mo, NavPage.angleSuperWide);
        Iris4GAction.cameraKey();
        waitTime(5);
        Iris4GAction.cameraKey();
        CameraAction.checkVideoAngle(NavPage.navConfig_Slo_Mo,NavPage.angleSuperWide);
    }
    @Test
    public void testSetSloMoWide() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Slo_Mo, NavPage.angleWide);
        Iris4GAction.cameraKey();
        waitTime(5);
        Iris4GAction.cameraKey();
        CameraAction.checkVideoAngle(NavPage.navConfig_Slo_Mo,NavPage.angleWide);
    }
    @Test
    public void testSetSloMoMedium() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Slo_Mo,NavPage.angleMedium);
        Iris4GAction.cameraKey();
        waitTime(5);
        Iris4GAction.cameraKey();
        CameraAction.checkVideoAngle(NavPage.navConfig_Slo_Mo,NavPage.angleMedium);
    }
    /**
     *1.修改视角为Wide
     2.修改上下颠倒为auto
     3.切换到普通录像模式后再切换为慢速摄影
     *Result:所有设置项都修改成功，更改的设置项都没有改变
     * */
    @Test
    public void testChangeOtherSettingReturnToSloMo() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Slo_Mo,NavPage.angleWide);
        CameraAction.configAutoButton(NavPage.navConfig_Video);
        CameraAction.navConfig(NavPage.navConfig_Video);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);
        CameraAction.checkVideoAngle(NavPage.navConfig_Slo_Mo,NavPage.angleWide);
        //检查自动翻转是否打开
    }
}
