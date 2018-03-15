package iris4G.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.page.Iris4GPage;
import iris4G.page.NavPage;

public class CameraAction extends VP2 {
    private static Logger logger = Logger.getLogger(CameraAction.class.getName());
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
    /**
     * Click android.widget.CompoundButton按钮
     * 检查录像并直播(同类型按钮)右边的按钮是否可点击，返回boolean类型值
     */
    public static boolean checkCompoundButtonIsEnabled(String textViewName) throws UiObjectNotFoundException {
        UiScrollable settingsViews = new UiScrollable(new UiSelector().scrollable(true));
        settingsViews.scrollTextIntoView(textViewName);
        waitUntilFindText(textViewName,3000);
        UiObject2 scrollView = getObject2ByClass(android.widget.ScrollView.class);
        List<UiObject2> relatives = scrollView.findObjects(By.clazz(android.widget.RelativeLayout.class));
        UiObject2 clickBtn=null;
        for (UiObject2 relateLayout : relatives) {
            boolean compoundButton = relateLayout.hasObject(By.clazz(android.widget.CompoundButton.class));
            boolean textView = relateLayout.hasObject(By.text(textViewName));
            if (compoundButton == true && textView == true) {
                clickBtn = relateLayout.findObject(By.clazz(android.widget.CompoundButton.class));
                Spoon.screenshot("openCompoundButton",textViewName);
                break;
            }
        }
        boolean buttonResult =clickBtn.isEnabled();
        return buttonResult;

    }
    /**
     * 点击自动翻转按钮
     */

    public static void configAutoButton(String navPage) throws Exception {
        CameraAction.navConfig(navPage);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Auto");
        openCompoundButton("Auto");
    }

    /**
     * 设置直播视角
     *
     */
    public static void setLiveAngle(String angleName) throws Exception {
        CameraAction.cameraSetting();
        CameraAction.navToMoreSettings();
        Iris4GAction.scrollTextIntoView("Video Angle");
        waitTime(2);
        Iris4GAction.clickByText("Video Angle");
        Iris4GAction.clickByText(angleName);
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
            if (connTooSlow.exists()){
                logger.info("connection too slow");
            }
        }
        Spoon.screenshot("checkLiveStatus");
    }
    public static boolean checkLiveSuccess() {
        gDevice.wait(Until.gone(By.textStartsWith("Initializing Live Stream")), 60000);
        waitTime(30);
        UiObject recordingTimeId=getObjectById(Iris4GPage.recording_time_id);
        if (!recordingTimeId.exists()) {
            logger.info("Live Failed");
            Spoon.screenshot("Live_Failed");
            return false;
        }
        else {
            logger.info("Live succeed");
            Spoon.screenshot("Live_succeed");
            return true;
        }
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
        waitUntilFind(Iris4GPage.camera_setting_shortcut_id,2000);
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
        Spoon.screenshot("configTimeLapse",timeLapse);
        logger.info(navConfig+" -configTimeLapse - "+timeLapse);
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
        Iris4GAction.ScrollViewByText("Video Quality");
        waitTime(1);
        clickByText("Video Quality");
        Iris4GAction.ScrollViewByText(quality);
        clickByText(quality);
        waitTime(1);
        gDevice.pressBack();
        Spoon.screenshot("configVideoQuality",quality);
        logger.info(navConfig+" -configVideoQuality - "+quality);
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
     *video,慢动作和lapse的 Angle设置
     * {"Super Wide","Wide","Medium"};
     */
    public static void configVideoAngle(String navConfig,String angle) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[1]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("Video Angle");
        clickByText("Video Angle");
        clickByText(angle);
        logger.info("Video Angle set to :" + angle);
        Spoon.screenshot("configVideoAngle",angle);
        logger.info(navConfig+" -configVideoAngle - "+angle);
        gDevice.pressBack();
    }

    /**
     *  live模式下的 Angle设置
     * {"Super Wide","Wide","Medium"};
     */
    public static void configLiveAngle(String navConfig,String angle) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[1]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("More settings");
        clickByText("More settings");
        waitTime(1);
        Iris4GAction.ScrollViewByText("Video Angle");
        clickByText("Video Angle");
        clickByText(angle);
        logger.info("Video Angle set to :" + angle);
        Spoon.screenshot("configVideoAngle",angle);
        logger.info(navConfig+" -configVideoAngle - "+angle);
        gDevice.pressBack();
    }
    public static void makeSlo_MoSomeTime(int time) throws Exception {
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(time);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(2);
    }
    public static void makeLapseSomeTime(int time ,String VideoQuality,int timeLapse) throws Exception {
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        configVideoQuality(VideoQuality);
        waitTime(1);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Time Lapse");
        clickByText("Time Lapse");
        String timeLapseS= String.valueOf(timeLapse)+"s";
        logger.info("timeLapseS:"+timeLapseS);
        Iris4GAction.ScrollViewByText(timeLapseS);
        clickByText(timeLapseS);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(time*timeLapse);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(2);
    }
    /*
    视频质量等需要配置的项都已经配置好；仅录像，不配置
     */
    public static void makeLapseSomeTime(int time,int timeLapse){
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(time*timeLapse);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(2);
    }
    public static void makeVideoSomeTime(int time ,String VideoQuality) throws Exception {
        CameraAction.navConfig(NavPage.navConfig_Video);
        configVideoQuality(VideoQuality);
        waitTime(1);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(time);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(2);
    }
    /*
    仅录像；不设置
     */
    public static void makeVideoSomeTime(int time ) throws Exception {
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(time);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(2);
    }
    /*
    录播一段时间
     */
    public static void makeVideoAndLiveSomeTime(int time ) throws Exception {
        CameraAction.navConfig(NavPage.navConfig_Video);
        cameraSetting();
        waitTime(1);
        openCompoundButton("Video&Live(beta)");
        waitTime(1);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(time);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(2);
        cameraSetting();
        waitTime(1);
        openCompoundButton("Video&Live(beta)");
        waitTime(1);
    }
    public static void configVideoQuality(String videoQuality) throws Exception {
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("Video Quality");
        clickByText("Video Quality");
        waitTime(2);
        Iris4GAction.ScrollViewByText(videoQuality);
        clickByText(videoQuality);
        waitTime(2);
        Spoon.screenshot("configLiveVideoQuality",videoQuality);
        logger.info(" -configLiveVideoQuality - "+videoQuality);
        gDevice.pressBack();
        waitTime(1);
    }





    //某一个选项处于选中状态-  如 视频角度 - Wide
    public static boolean hasObjectSelected(String text) throws Exception {
        Iris4GAction.ScrollViewByText(text);
        UiObject2 textObject = getUiObject2ByText(text);
        return textObject.getParent().hasObject(By.clazz(android.widget.ImageView.class));
    }
    public static void checkVideoAngle(String navConfig,String angle) throws Exception {
        //等待加载完成
        gDevice.wait(Until.findObject(By.pkg("com.hicam")),20000);
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

    /**
     * 检查live的视场角设置
     * @param navConfig
     * @param angle
     * @throws Exception
     */
    public static void checkLiveAngle(String navConfig,String angle) throws Exception {
        //等待加载完成
        gDevice.wait(Until.findObject(By.pkg("com.hicam")),20000);
        //CameraAction.navConfig(Iris4GPage.nav_menu[1]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("More settings");
        clickByText("More settings");
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



    public static void checkLapseTime(String navConfig,String Time) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[1]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Time Lapse");
        String active_angle = Iris4GAction.getRightValue("Time Lapse");
        Spoon.screenshot("currentTimeLapse",Time);
        Asst.assertEquals("TimeLapse",Time,active_angle);
        clickByText("Time Lapse");
        if (!hasObjectSelected(Time)){
            Asst.fail(Time+" not selected");
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
        gDevice.pressBack();
    }
    public static void checkImageSize(String navConfig,String size) throws Exception {
        //CameraAction.navConfig(Iris4GPage.nav_menu[2]);
        CameraAction.navConfig(navConfig);
        CameraAction.cameraSetting();

        String active_size = Iris4GAction.getRightValue("Image Size");
        Spoon.screenshot("checkVideoAngle",size);
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
        waitTime(1);
        gDevice.pressMenu();
        waitTime(1);
        Iris4GAction.ScrollViewByText(text);
        clickByText(text);
        //waitTime(2);
        //Spoon.screenshot("navConfig",text);
    }
    //720@60FPS - return 720@60
    public static String replaceFps(String quality){
        return quality.replace("FPS","");
    }
    //打开文件管理器-播放视频
    public static void openPlayVideoAndCheck(String quality,HashSet<String> beforeTakeVideoList) throws Exception {
        HashSet<String> afterTakeVideoList = Iris4GAction.FileList("/sdcard/Video");
        HashSet<String> resultHashSet = Iris4GAction.result(afterTakeVideoList, beforeTakeVideoList);
        //只有一个视频存在于video文件夹中
        if (resultHashSet.size()==1) {
            String videoPath = resultHashSet.iterator().next();
            logger.info("new file:"+videoPath);
            String videoName = new File(videoPath).getName();
            VideoNode activeNode=Iris4GAction.VideoInfo(videoPath);
            //视频质量为quality.split("@")[0]
            int height = Integer.parseInt(quality.split("@")[0]);
            //验证视频信息
            if (Iris4GAction.checkVideoInfo(height, activeNode)) {
                //播放视频
                FileManagerAction.playVideoByFileManager(videoName);
                if (text_exists_match("^Can't play this video.*")) {
                    logger.info(videoName+" play fail " + "-Can't play this video");
                    clickById("android:id/button1");
                    Asst.fail("Can't play this video");
                }else {
                    logger.info(videoName+" play success");
                }
            }else{
                Asst.fail("checkVideoInfo-error");
            }
        }else {
            Asst.fail("expect only one video in folder");
        }
    }
    public static void navToAccount() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Account");
        clickByText("Account");
    }
    public static void navToMoreSettings() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("More settings");
        clickByText("More settings");
    }
    public static void navToLocation() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("More settings");
        clickByText("More settings");
        waitTime(1);
        Iris4GAction.ScrollViewByText("Live&Location");
    }
    public static void navToAltimeter() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("More settings");
        clickByText("More settings");
        waitTime(1);
        Iris4GAction.ScrollViewByText("Altimeter");
    }
    public static void navToCustomResolution() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Video Quality");

    }
    /**
     * 直播视频质量设置
     * "480@30FPS(CD)",
     * "480@30FPS(HD)",
     * "720@30FPS(HD)",
     */
    public static void configLiveVideoQuality(String quality) throws Exception {
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Video Quality");
        clickByText("Video Quality");
        Iris4GAction.ScrollViewByText(quality);
        clickByText(quality);
        waitTime(1);
        gDevice.pressBack();
        Spoon.screenshot("configLiveVideoQuality",quality);
        logger.info(" -configLiveVideoQuality - "+quality);
    }

    public static void checkLiveVideoQualityStatus(String LiveQuality) throws Exception{
        if (!getUiObjectByText(LiveQuality).exists()){
            Assert.fail("LiveQualityNotIs"+LiveQuality);
        }
        logger.info("直播预览界面视频质量显示正确："+LiveQuality);
    }

    /**
     * Live Angle设置
     * {"Super Wide","Wide","Medium"};
     */
    public static void configLiveAngle(String angle) throws Exception {
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("More settings");
        clickByText("More settings");
        Iris4GAction.ScrollViewByText("Video Angle");
        clickByText("Video Angle");
        clickByText(angle);
        logger.info("Video Angle set to :" + angle);
        Spoon.screenshot("configLiveAngle",angle);
        logger.info(" -configLiveAngle - "+angle);
        gDevice.pressBack();
        waitTime(1);
    }
    /**
     * 设置每种直播质量和视场角对应并发起直播
     * 检查预览界面直播分辨率显示和是否成功发起直播
     */
    public static void checkLiveQualityAndAngleLiveAndZoom(String LiveQuality,String LiveAngle,String CheckLiveQuality)throws Exception{
        CameraAction.configLiveVideoQuality( LiveQuality);
        logger.info("设置直播视频质量为："+LiveQuality);
        CameraAction.configLiveAngle(LiveAngle);
        logger.info("设置直播视场角为："+LiveAngle);
        CameraAction.checkLiveVideoQualityStatus(CheckLiveQuality);
        logger.info("正在发起直播中...");
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        CameraAction.checkLiveStatus(1);
        if (!CameraAction.checkLiveSuccess()){
            Assert.fail(LiveQuality+LiveAngle+"LiveFailed");
        }
        logger.info("发起直播成功");
        CameraAction.openAndCloseSeekBarForZoom();
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(2);
        logger.info("已停止直播");
    }
    public static void openAndCloseSeekBarForZoom() throws Exception{
        clickByPonit(178,98);
        clickByPonit(178,98);
        waitTime(2);
        if (!gDevice.findObject(new UiSelector().resourceId(Iris4GPage.seekbarforzoom_id)).exists()){
            Assert.fail("OpenSeekBarForZoomFailed");
        }
        logger.info("打开变焦功能成功");
        clickByPonit(178,98);
        clickByPonit(188,99);
        waitTime(2);
        if (gDevice.findObject(new UiSelector().resourceId(Iris4GPage.seekbarforzoom_id)).exists()){
            Assert.fail("CloseSeekBarForZoomFailed");
        }
        logger.info("关闭变焦功能成功");
    }
    /**
     * check 状态栏信息
     */
    public static boolean checkLiveModeInfo(String videoType) throws UiObjectNotFoundException {
        UiObject camera_mode = gDevice.findObject(new UiSelector().resourceId("com.hicam:id/camera_mode"));
        UiObject video_info = gDevice.findObject(new UiSelector().resourceId("com.hicam:id/info"));
        if (camera_mode.exists() && video_info.getText().equals(videoType)) {
            return true;
        } else {
            return false;
        }
    }

    /*
    清除账号信息后通过账号登陆，然后开启录播开关
     */
    public static void loginAndOpenVideoLiveButton() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(useName, password);
        CameraAction.navConfig(NavPage.navConfig_Video);
        clickById(Iris4GPage.camera_setting_shortcut_id);
        waitTime(1);
        CameraAction.openCompoundButton(Iris4GPage.videoAndLive);
        waitTime(2);
    }
    /*
    停止直播录像等
     */
    public static void stopVideoOrLive() throws UiObjectNotFoundException {
        if (id_exists(Iris4GPage.recording_time_id)){
            gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        }
    }
    public static boolean checkVideoAndLiveButton() throws Exception {
        clickById(Iris4GPage.camera_setting_shortcut_id);
        waitTime(1);
        Iris4GAction.ScrollViewByText("Video Quality");
        clickByText("Video Quality");
        if (text_exists(Iris4GPage.video_quality[4])){
            gDevice.pressBack();
            waitTime(1);
            gDevice.pressBack();
            return false;
        }else {
            return true;
        }
    }
    public static void loginByVideoAndLive() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        VideoNode.navToVideoAndLiveLoginPage();
        clickById(Iris4GPage.account_login);
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.login(useName, password);
    }
    /*
    可设置默认录像分辨率---然后发起起录播，8秒未发起录播的话判为失败
     */
    public static void makeVideoAndLiveButtonWithDR(String videoQuality) throws Exception {
        CameraAction.configVideoQuality(videoQuality);
        clickById(Iris4GPage.camera_setting_shortcut_id);
        waitTime(1);
        CameraAction.openCompoundButton(Iris4GPage.videoAndLive);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitUntilFind(Iris4GPage.video_and_live_recording_live,10000);
        if (!id_exists(Iris4GPage.video_and_live_recording_live)){
            Assert.fail("makeVideoAndLiveFailed");
        }
        stopVideoOrLive();
        waitTime(2);
        clickById(Iris4GPage.camera_setting_shortcut_id);
        waitTime(1);
        CameraAction.openCompoundButton(Iris4GPage.videoAndLive);
        waitTime(1);
        gDevice.pressBack();
        waitTime(1);
    }
    /*
    配置视频质量后检查是否配置成功，+状态栏
     */
    public static void checkConfigVideoQuality(String navPage ,String videoQuality) throws Exception {
        CameraAction.configVideoQuality(navPage,videoQuality);
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("Video Quality");
        waitTime(1);
        String videoQualityInStandardBar=null;
        if (navPage.equals("Video")){
            videoQualityInStandardBar=videoQuality.substring(0,videoQuality.length()-3);
        }else {
            videoQualityInStandardBar=videoQuality.substring(0,videoQuality.length()-21);
            videoQuality=videoQuality.substring(0,10)+videoQuality.substring(17,videoQuality.length());
        }
        String videoQualityBar=getTex(Iris4GPage.info);
        if (!videoQualityBar.equals(videoQualityInStandardBar)){
            Assert.fail("statusBarNotUpdate");
        }else if (!text_exists(videoQuality)){
            Assert.fail("videoSettingNotUpdate");
        }
    }
    /*
    lapse配置视频质量后检查是否配置成功，+状态栏
     */
    public static void checkConfigLapseQuality(String videoQuality) throws Exception {
        CameraAction.configVideoQuality(NavPage.navConfig_Lapse,videoQuality);
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("Video Quality");
        waitTime(1);
        if (!text_exists(videoQuality)){
            Assert.fail("videoSettingNotUpdate");
        }
    }

    /*public static void specialLive(String liveType) throws Exception {
        CameraAction.openCompoundButton(liveType);
        waitTime(5);
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        //登录账号
        AccountAction.loginAccount(useName, password);
        //检查延时直播开关状态、状态栏信息、其他开关状态
        Asst.assertEquals("状态栏信息合理：",true,CameraAction.checkLiveModeInfo("480@30"));
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
        if (resultHashSet.size() == 1) {
            String videoPath = resultHashSet.iterator().next();
            logger.info("new file:" + videoPath);
            String videoName = new File(videoPath).getName();
            VideoNode activeNode = Iris4GAction.VideoInfo(videoPath);
            int height = 480;
            if (Iris4GAction.checkVideoInfo(height, activeNode)) {
                logger.info("video info check success-" + videoPath);
                FileManagerAction.playVideoByFileManager(videoName);
                if (text_exists_match("^Can't play this video.*")) {
                    logger.info(videoName + " play fail " + "-Can't play this video");
                    clickById("android:id/button1");
                    Asst.fail("Can't play this video");
                } else {
                    logger.info(videoName + " play success");
                }
            } else {
                logger.info("video info check failed" + videoPath);
                Asst.fail("video info check failed");
            }
        } else {
            Asst.fail("video not exist");
        }
    }*/
}
