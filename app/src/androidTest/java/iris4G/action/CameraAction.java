package iris4G.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import ckt.base.VP2;
import iris4G.page.Iris4GPage;

public class CameraAction extends VP2 {
    /**
     * Click android.widget.CompoundButton按钮
     * 录像并直播(同类型按钮)右边的按钮
     */
    public static void openCompoundButton(String textViewName) {
        UiObject2 scrollView = getObject2ByClass(android.widget.ScrollView.class);
        List<UiObject2> relatives = scrollView.findObjects(By.clazz(android.widget.RelativeLayout.class));
        UiObject2 clickBtn = null;
        for (UiObject2 relateLayout : relatives) {
            boolean compoundButton = relateLayout.hasObject(By.clazz(android.widget.CompoundButton.class));
            boolean textView = relateLayout.hasObject(By.text(textViewName));
            if (compoundButton == true && textView == true) {
                clickBtn = relateLayout.findObject(By.clazz(android.widget.CompoundButton.class));
                clickBtn.click();
                Spoon.screenshot("openCompoundButton",textViewName);
                break;
            }
        }
    }

    //是否成功开启直播
    public static void checkLiveStatus(int retryTimes) {
        for (int i = 0; i < retryTimes; i++) {
            UiObject init = getUiObjectByTextMatches("^Initializing Live Stream*");
            gDevice.wait(Until.gone(By.textStartsWith("Initializing Live Stream")), 60000);
            UiObject connFail = getUiObjectByTextMatches("^Connection fail, please try again*");
            UiObject connTooSlow = getUiObjectByTextMatches("^connection too slow*");
            if (connFail.exists()) {
                logger.info("Connection fail, please try again");
            }
        }
        Spoon.screenshot("checkLiveStatus");
    }

    /**
     * Click launcher_application_id按钮
     */
    public static void LaunchCamera() throws Exception {
        clickById(Iris4GPage.launcher_application_id);
    }

    /**
     * Click camera_live_shortcut_id按钮
     */
    public static void cameraLive() throws Exception {
        clickById(Iris4GPage.camera_live_shortcut_id);
    }

    /**
     * Click camera_video_shortcut_id按钮
     */
    public static void cameraVideo() throws Exception {
        clickById(Iris4GPage.camera_video_shortcut_id);
    }

    /**
     * Click camera_cap_shortcut_id按钮
     */
    public static void cameraCap() throws Exception {
        clickById(Iris4GPage.camera_cap_shortcut_id);
    }

    /**
     * Click camera_setting_shortcut_id按钮
     */
    public static void cameraSetting() throws Exception {
        clickById(Iris4GPage.camera_setting_shortcut_id);
    }

    /**
     * 等待开始录制-60秒
     */
    public static String cameraRecordTime() throws Exception {
        waitUntilFind(Iris4GPage.recording_time_id, 60000);
        String time = getTex(Iris4GPage.recording_time_id);
        logger.info("recording_time:" + time);
        Spoon.screenshot("recording_time");
        return time;
    }

    /**
     * 获取当前视频质量
     */
    public static UiObject currentQuality() throws Exception {
        UiObject qualityObject = getObjectById(Iris4GPage.currentvideoquliaty);
        logger.info("Current Video Quality :" + qualityObject.getText());
        Spoon.screenshot("currentQuality");
        return qualityObject;
    }

    /**
     * Uiobject video-paly 按钮
     */
    public static UiObject playVideoBtn() throws Exception {
        UiObject videoObject = getObjectById(Iris4GPage.playvideobtn);
        logger.info("video play object :" + videoObject.getText());
        return videoObject;
    }

    public static int dateInSeconds(String recordTime) {
        int seconds = 0;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = format.parse(recordTime);
            cal.setTime(date);
            int hour = cal.get(Calendar.HOUR);//小时
            int minute = cal.get(Calendar.MINUTE);//分
            int second = cal.get(Calendar.SECOND);//秒
            seconds = hour * 60 * 60 + minute * 60 + second;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return seconds;
    }

    public static boolean checkLapseValue(int lap) throws Exception {
        String lapse1 = getTex(Iris4GPage.lapsetime1);
        String lapse2 = getTex(Iris4GPage.lapsetime2);
        int lapse1secs = dateInSeconds(lapse1);
        int lapse2secs = dateInSeconds(lapse2);
        logger.info(lapse1secs + "-" + lapse2secs+"-"+lap);
        if (lapse1secs / lapse2secs/ lap ==1) {
            logger.info("Time Lapse验证结果-PASS  " + lapse1secs + "-" + lapse2secs);
            return true;
        } else {
            logger.info("Time Lapse验证结果-Failed  " + lapse1secs + "-" + lapse2secs);
            return false;
        }
    }

    public static boolean checkMoValue(int mo) throws Exception {
        String mo1 = getTex(Iris4GPage.lapsetime1);
        String mo2 = getTex(Iris4GPage.lapsetime2);
        int mo1secs = dateInSeconds(mo1);
        int mo22secs = dateInSeconds(mo2);
        logger.info(mo1secs + "-" + mo22secs);
        Spoon.screenshot("checkMoValue",""+mo);
        if (mo22secs / mo1secs == mo) {
            logger.info("Slo-Mo验证结果-PASS  " + mo1secs + "-" + mo22secs);
            return true;
        } else {
            logger.info("Slo-Mo验证结果-Failed  " + mo1secs + "-" + mo22secs);
            return false;
        }
    }

    /**
     * TimeLapse设置
     * "2 3 5 10";
     */
    public static void configTimeLapse(String navConfig,String timeLapse) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[5]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Time Lapse");
        clickByText("Time Lapse");
        Iris4GAction.ScrollViewByText(timeLapse);
        clickByText(timeLapse);
        logger.info("Time Lapse set to :" + getUiObjectByText(timeLapse).getText());
        Spoon.screenshot("configTimeLapse",timeLapse);
        gDevice.pressBack();
    }
    public static void checkTimeLapse(String navConfig,String timeLapse) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[5]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Time Lapse");
        String active_timeLapse = Iris4GAction.getRightValue("Time Lapse");
        Spoon.screenshot("currentTimeLapse",active_timeLapse);
        Asst.assertEquals("TimeLapse",timeLapse,active_timeLapse);
        clickByText("Time Lapse");
        Iris4GAction.ScrollViewByText(timeLapse);
        //是否处于选中状态
        if (!hasObjectSelected(timeLapse)){
            Asst.fail(timeLapse+" not selected");
        }
        gDevice.pressBack();
    }
    public static void configTimeLapse(int index, String timeLapse) throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[index]);
        CameraAction.cameraSetting();
        clickByText("Time Lapse");
        Iris4GAction.ScrollViewByText(timeLapse);
        clickByText(timeLapse);
        logger.info("Time Lapse set to :" + getUiObjectByText(timeLapse).getText());
        Spoon.screenshot("configTimeLapse",timeLapse);
        gDevice.pressBack();
    }

    /**
     * 视频质量设置
     * "480@30FPS",
     * "480@60FPS",
     * "480@120FPS",
     * "720@30FPS",
     * "720@60FPS",
     * "1080@30FPS"};
     */
    public static void configVideoQuality(String navConfig,String quality) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[1]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();
        clickByText("Video Quality");
        Iris4GAction.ScrollViewByText(quality);
        clickByText(quality);
        waitTime(1);
        gDevice.pressBack();
        Spoon.screenshot("configVideoQuality",quality);
    }
    public static void checkVideoQuality(String navConfig,String quality) throws Exception {
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Video Quality");
        String active_quality = Iris4GAction.getRightValue("Video Quality");
        Spoon.screenshot("currentVideoQuality",quality);
        Asst.assertEquals("VideoQuality",quality,active_quality);
        clickByText("Video Quality");
        //是否处于选中状态
        if (!hasObjectSelected(quality)){
            Asst.fail(quality+" not selected");
        }
        gDevice.pressBack();
    }

    //给定的视频参数是否支持
    public static boolean isExistVideoQuality(String quality) throws Exception {
        boolean isExist = false;
        CameraAction.navConfig(Iris4GPage.nav_menu[5]);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Video Quality");
        clickByText("Video Quality");
        UiObject findObj = scrollAndGetUIObject(quality);
        if (findObj == null) {
            logger.info("Video Quality set to :" + quality);
        } else {
            isExist = true;
        }
        gDevice.pressBack();
        return isExist;
    }

    public static void configVideoQuality(int index, String quality) throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[index]);
        CameraAction.cameraSetting();
        clickByText("Video Quality");
        Iris4GAction.ScrollViewByText(quality);
        clickByText(quality);
        logger.info("Video Quality set to :" + getUiObjectByText(quality).getText());
        Spoon.screenshot("configVideoQuality",quality);
        gDevice.pressBack();
    }

    /**
     * 连拍数设置
     * "10P","20P","30P;
     */
    public static void configBurst(String navConfig,String burst) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[3]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();
        clickByText("Burst Rate");
        clickByText(burst);
        logger.info("Burst Rate set to :" + burst);
        Spoon.screenshot("configBurst",burst);
        waitTime(1);
        gDevice.pressBack();
    }

    /**
     * Angle设置
     * {"Super Wide","Wide","Medium"};
     */
    public static void configVideoAngle(String navConfig,String angle) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[1]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Video Angle");
        clickByText("Video Angle");
        clickByText(angle);
        logger.info("Video Angle set to :" + angle);
        Spoon.screenshot("configVideoAngle",angle);
        gDevice.pressBack();
    }
    //某一个选项处于选中状态-  如 视频角度 - Wide
    public static boolean hasObjectSelected(String text) throws Exception {
        Iris4GAction.ScrollViewByText(text);
        UiObject2 textObject = getUiObject2ByText(text);
        return textObject.getParent().hasObject(By.clazz(android.widget.ImageView.class));
    }
    public static void checkVideoAngle(String navConfig,String angle) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[1]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Video Angle");
        String active_angle = Iris4GAction.getRightValue("Video Angle");
        Spoon.screenshot("currentVideoAngle",angle);
        Asst.assertEquals("VideoAngle",angle,active_angle);
        clickByText("Video Angle");
        if (!hasObjectSelected(angle)){
            Asst.fail(angle+" not selected");
        }
        gDevice.pressBack();
        gDevice.pressBack();
    }

    public static void configVideoAngle(String navConfig,int index, String angle) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[index]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Video Angle");
        clickByText("Video Angle");
        clickByText(angle);
        logger.info("Video Angle set to :" + angle);
        Spoon.screenshot("configVideoAngle",angle);
        gDevice.pressBack();
    }

    public static void configVideoAngle(String navConfig,int angle) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[1]);
        CameraAction.navConfig(Iris4GPage.nav_menu[1]);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Video Angle");
        clickByText("Video Angle");
        //common.clickViewByText(angle);
        clickByClass("android.widget.RelativeLayout", angle);
        logger.info("Video Angle set to :" + angle);
        Spoon.screenshot("configVideoAngle",angle+"");
        gDevice.pressBack();
    }

    /**
     * 图片设置
     * "4M(16:9)",
     * "3M(4:3)",
     * "2M(16:9)"};
     */
    public static void configImageSize(String navConfig,String size) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[2]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();
        clickByText("Image Size");
        Iris4GAction.ScrollViewByText(size);
        clickByText(size);
        logger.info("Image Size set to :" + size);
        Spoon.screenshot("configImageSize",size);
        waitTime(1);
        gDevice.pressBack();
    }
    public static void checkImageSize(String navConfig,String size) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[2]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();

        String active_size = Iris4GAction.getRightValue("Image Size");
        Spoon.screenshot("currentVideoAngle",size);
        Asst.assertEquals("Image Size",size,active_size);

        clickByText("Image Size");
        if (!hasObjectSelected(size)){
            Asst.fail(size+" not selected");
        }
        gDevice.pressBack();
    }

    /**
     * 进入对应的模块
     * 0： "Live Stream",
     * 1:  "Video",
     * 2: "Capture",
     * 3:"Burst",
     * 4:"Slo_Mo",
     * 5:"Lapse"
     */
    public static void navConfig(String text) throws Exception {
        gDevice.pressMenu();
        waitTime(1);
        Iris4GAction.ScrollViewByText(text);
        clickByText(text);
        waitTime(2);
        //Spoon.screenshot("navConfig",text);
    }
    //720@60FPS - return 720@60
    public static String replaceFps(String quality){
        return quality.replace("FPS","");
    }
    //打开文件管理器-播放视频
    public static void openPlayVideo(HashSet<String> beforeTakeVideoList) throws Exception {
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
                logger.info(videoName+" play success");
            }
        }else {
            Asst.fail("expect only one video in folder");
        }
    }
}
