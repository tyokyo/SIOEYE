package iris4G.currentTestCase;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import com.squareup.spoon.Spoon;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.action.SettingAction;
import iris4G.page.Iris4GPage;
import iris4G.page.SettingPage;

/**
 * Created by user on 2018/01/27   .
 */
public class CurrenTestAction extends VP2 {
    private static Logger logger = Logger.getLogger(CurrenTestAction.class.getName());


    private static int testTime=120;

    private static String[] videoAngle={
            "Medium",
            "Wide",
            "Super Wide"
    };
    /**
     * 格式化存储空间
     */
    public static void storageFormat() throws Exception {
        Iris4GAction.startSettings();
        SettingAction.navToStorage();
        waitTime(1);
        clickById(SettingPage.storage_delete_img);
        clickByText("FORMAT");
        clickByText("FORMAT");
        waitTime(10);
        logger.info("格式化完成！");
        Iris4GAction.stopSettings();
        waitTime(1);
    }

    /**
     *关闭wifi
     */
    public static void closeWifi() throws Exception {
        SettingAction.navToWifi();
        if (text_exists("Add new network...")){
            logger.info("wifi状态为关闭");
        }else {
            clickById("android:id/switchWidget");
            logger.info("关闭wifi...");
            waitTime(5);
        }
        Iris4GAction.stopSettings();
        waitTime(2);
    }

    /**
     *开启wifi
     */
    public static void openWifi() throws Exception {
        SettingAction.navToWifi();
        if (text_exists("Add new network...")){
            clickById("android:id/switchWidget");
            logger.info("开启wifi中..");
            waitTime(10);
            logger.info("wifi自动连接中..");
        }else {
            logger.info("wifi为开启状态");
        }
        Iris4GAction.stopSettings();
        waitTime(2);
    }

    /**
     *进入相机
     */
    public static void launchCamera() throws Exception {
        gDevice.pressHome();
        waitTime(2);
        gDevice.executeShellCommand("am start -n com.hicam/.application.HiCam");
        gDevice.wait(Until.findObject(By.pkg("com.hicam")), 40000);
        if (text_exists("Update")) {
            clickByText("Cancel");
        }
        if (id_exists("android:id/button2")) {
            clickById("android:id/button2");
        }
        String pkg = gDevice.getCurrentPackageName();
        logger.info("current-package:" + pkg);
    }

    /**
     *灭屏
     */
    private static void makeScreenOff() throws RemoteException {
        if (gDevice.isScreenOn()) {
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            logger.info("make screen off");
        }
        waitTime(2);
    }

    /**
     *亮屏
     */
    public static void makeScreenOn() throws RemoteException {
        waitTime(1);
        if (!gDevice.isScreenOn()) {
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            logger.info("make screen on");
        }
        waitTime(2);
    }

    public static void switchTo3G() throws Exception {
        makeScreenOn();
        SettingAction.navToPreferredNetworkType();
        clickByText("3G");
        waitTime(3);
        logger.info("已设置网络模式为3G");
        Iris4GAction.stopSettings();
        waitTime(2);
    }

    public static void switchTo4G() throws Exception {
        makeScreenOn();
        SettingAction.navToPreferredNetworkType();
        clickByText("4G (recommended)");
        waitTime(3);
        logger.info("已设置网络模式为4G");
        Iris4GAction.stopSettings();
        waitTime(2);
    }

    public static void changeSleepTime(String sleepTime) throws Exception {
        makeScreenOn();
        SettingAction.navToSleepTime();
        waitTime(1);
        makeScreenOn();
        Iris4GAction.ScrollViewByText(sleepTime);
        makeScreenOn();
        clickByText(sleepTime);
        waitTime(1);
        logger.info("设置灭屏时间为："+sleepTime);
        Iris4GAction.stopSettings();
        waitTime(2);
    }

    public static void makeToasts(String message, int time) throws IOException {
//        initDevice();
        String command = String.format("am broadcast -a com.sioeye.alert.action -e message %s -e time %d",message,time);
        logger.info(command);
        gDevice.executeShellCommand(command);
    }



    /**
     *灭屏录像
     */
    public static void makeVideo(String screenStatus) throws RemoteException {
        makeScreenOn();
        Iris4GAction.cameraKey();
        logger.info("LaunchCameraKeyForStart");
        waitTime(2);
        if (screenStatus.equals("OFF")){
            makeScreenOff();
            logger.info("已灭屏");
            waitTime(3);
        }
        waitTime(testTime);
        makeScreenOn();
        Iris4GAction.cameraKey();
        logger.info("LaunchCameraKeyForEnd");
        waitTime(3);
    }


    public static void makeLive(String screenStatus ,BufferedWriter out,String is4G) throws Exception {
        makeScreenOn();
        Iris4GAction.cameraKey();
        waitUntilFind(Iris4GPage.recording_time_id,38000);
        waitTime(3);
        UiObject recordingTimeId = getObjectById(Iris4GPage.recording_time_id);
        if (!recordingTimeId.exists()) {
            gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);//消除对话框
            waitTime(2);
            gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
            logger.info("Try again");
            waitUntilFind(Iris4GPage.recording_time_id,38000);
            waitTime(3);
            UiObject recordingTimeIdAgain = getObjectById(Iris4GPage.recording_time_id);
            if (!recordingTimeIdAgain.exists()) {
                gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);//消除对话框
                logger.info("Live Failed");
                Spoon.screenshot("LiveFailed");
                logger.info("SawtoothWaveNoteMakeLiveFailed");
                for (int i=0;i<4;i++) {
                    waitTime(15);
                    gDevice.pressBack();
                    gDevice.pressBack();
                    waitTime(15);
                    Iris4GAction.startCamera();
                }
                CameraAction.navConfig(Iris4GPage.nav_menu[0]);//Live Modem
            }else {
                logger.info("Live succeed");
                if (screenStatus.equals("OFF")){makeScreenOff();}
                if (is4G.equals("YES")){liveTime(out);}
                else {waitTime(testTime);}
                makeLiveStop();
            }
        } else {
            logger.info("Live succeed");
            if (screenStatus.equals("OFF")){makeScreenOff();}
            if (is4G.equals("YES")){liveTime(out);}
            else {waitTime(testTime);}
            makeLiveStop();
        }

    }

    public static void liveWithBiggerZoom(String screenOnOff ,BufferedWriter out,String is4G) throws Exception {
        makeScreenOn();
        clickById(Iris4GPage.recording_time_id);
        clickById(Iris4GPage.recording_time_id);
        getObjectById(Iris4GPage.content_id).swipeRight(80);
        makeLive(screenOnOff,out,is4G);
        makeLiveStop();
        configVideoAngle(videoAngle[0]);//恢复变焦
    }

    /**
     *结束直播
     */
    public static void makeLiveStop() throws Exception {
        waitTime(1);
        makeScreenOn();
        if (text_exists("OK")) {
            logger.info("Bad network, livestream has ended");
            Spoon.screenshot("BadNetworkLiveHasEnded","BadNetworkLiveHasEnded");
            clickByText("OK");
            logger.info("SawtoothWaveNoteLiveBreakOff");
            for (int i=0;i<4;i++){
                gDevice.pressBack();
                gDevice.pressBack();
                waitTime(15);
                launchCamera();
                waitTime(15);
            }
        }else {
            gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        }
        logger.info("LiveHasStop");
        waitTime(2);
    }

    /**
     * 相册亮屏直播，相册第一个视频可以直播
     */
    public static void makeGalleryLive(String screenStatus,BufferedWriter out,String is4G) throws Exception {
        clickById(SettingPage.video_timeText);//点击屏幕
        clickById(SettingPage.gallery_live_bottom);//点击live
        waitTime(2);
        clickById(SettingPage.gallery_live);
        waitTime(1);
        clickById(SettingPage.gallery_skip);
        waitUntilFindText("broadcasting",15000);
        if (!text_exists("broadcasting")){
            waitUntilFind(SettingPage.gallery_live_retry,3000);
            clickById(SettingPage.gallery_live_retry);
            waitUntilFindText("broadcasting",15000);
            if (!text_exists("broadcasting")){
                logger.info("galleryLiveFailed");
                Spoon.screenshot("galleryLiveFailed","galleryLiveFailed");
                waitUntilFind(SettingPage.gallery_live_cancel,3000);
                clickById(SettingPage.gallery_live_cancel);
                Iris4GAction.stopGallery();
                waitTime(1);
                gDevice.pressBack();
                gDevice.pressBack();
                waitTime(1);
                //直播失败后会出现锯齿状电流波形
                logger.info("SawtoothWaveNoteMakeGalleryLiveFailed");
                launchCamera();
                for (int i=0;i<4;i++) {
                    waitTime(15);
                    gDevice.pressBack();
                    gDevice.pressBack();
                    waitTime(15);
                    Iris4GAction.startCamera();
                }
                gDevice.pressBack();
                gDevice.pressBack();
                waitTime(2);
                Iris4GAction.startGallery();
            }else {
                logger.info("retryLiveSuccess");
                Spoon.screenshot("galleryRetryLiveSuccess","galleryRetryLiveSuccess");
                if (screenStatus.equals("OFF")){makeScreenOff();}//判断用例该亮灭屏
                if (is4G.equals("YES")){galleryLiveTime(out);}
                else {
                    waitTime(testTime-5);
                }
            }
        }else {
            logger.info("LiveSuccess");
            Spoon.screenshot("galleryLiveSuccess","galleryLiveSuccess");
            if (screenStatus.equals("OFF")){makeScreenOff();}
            if (is4G.equals("YES")){galleryLiveTime(out);}
            else {
                waitTime(testTime-5);
            }
        }
        stopGalleryLive();
        waitTime(2);
    }

    /**
     * 停止相册直播
     */
    public static void stopGalleryLive()throws Exception{
        makeScreenOn();
        if (text_exists("broadcasting")) {
            gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
            waitTime(1);
            clickByText("Yes");
            waitTime(2);
            logger.info("galleryLiveHasStop");
        }
        if (id_exists(SettingPage.gallery_live_cancel)){
            logger.info("galleryLiveBreakOff");
            Spoon.screenshot("galleryLiveBreakOff","galleryLiveBreakOff");
            clickById(SettingPage.gallery_live_cancel);
            waitTime(2);
            gDevice.pressBack();
            gDevice.pressBack();
            waitTime(2);
            gDevice.pressBack();
            gDevice.pressBack();
            waitTime(1);
            logger.info("SawtoothWaveNoteGalleryLiveBreakOff");
            //出现锯齿状电流波形
            launchCamera();
            for (int i=0;i<4;i++) {
                waitTime(15);
                gDevice.pressBack();
                gDevice.pressBack();
                waitTime(15);
                launchCamera();
            }
            gDevice.pressBack();
            gDevice.pressBack();
            waitTime(2);
            Iris4GAction.startGallery();
        }
    }


    /**
     * 配置视频质量
     */
    public static void configVideoQuality(String videoQuality) throws Exception {
        makeScreenOn();
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
        waitTime(2);
    }

    public static void configUserDefinedLiveQuality(String resolution,String minBitrate,String maxBitrate) throws Exception {
        makeScreenOn();
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("Video Quality");
        clickByText("Video Quality");
        waitTime(2);
        clickById(Iris4GPage.user_defined_setting_image_view);
        waitTime(2);
        Iris4GAction.ScrollViewByText(Iris4GPage.user_defined_scrollView,"Resolution");
        clickById(Iris4GPage.user_defined_resolution_options);
        waitTime(1);
        clickByText(resolution);
        Iris4GAction.ScrollViewByText(Iris4GPage.user_defined_scrollView,"Frame Rate");
        clickById(Iris4GPage.user_defined_frame_rate);
        waitTime(1);
        clickByText("25");
        if (resolution.equals("480P")){
            Iris4GAction.ScrollViewByText(Iris4GPage.user_defined_scrollView,"(range: 200Kbps ~ 5,000Kbps)");
        }else {
            Iris4GAction.ScrollViewByText(Iris4GPage.user_defined_scrollView,"(range: 400Kbps ~ 10,000Kbps)");
        }

        Iris4GAction.setText(Iris4GPage.user_defined_min_bitrate,minBitrate);
        Iris4GAction.setText(Iris4GPage.user_defined_max_bitrate,maxBitrate);
        waitTime(1);
//        Spoon.screenshot("configLiveVideoQuality");
        logger.info(" -configLiveVideoQuality - ");
        clickById(Iris4GPage.user_defined_sure);
        waitTime(2);
        gDevice.pressBack();
        waitTime(1);
        gDevice.pressBack();
        waitTime(1);
    }

    public static void configVideoAngle(String VideoAngle) throws Exception {
        makeScreenOn();
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("More settings");
        clickByText("More settings");
        waitTime(1);
        Iris4GAction.ScrollViewByText("Video Angle");
        clickByText("Video Angle");
        waitTime(2);
        clickByText(VideoAngle);
        Spoon.screenshot("VideoAngle", "changeVideoAngleTo"+VideoAngle);
        waitTime(1);
        logger.info("已修改视场角为："+VideoAngle);
        gDevice.pressBack();
        waitTime(1);
        gDevice.pressBack();
        waitTime(1);
    }

    public static void clickLiveMute() throws Exception {
        makeScreenOn();
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("More settings");
        clickByText("More settings");
        waitTime(1);
        Iris4GAction.ScrollViewByText("Live Mute");
        CameraAction.openCompoundButton("Live Mute");
        Spoon.screenshot("LiveMute", "clickLiveMute");
        waitTime(2);
        waitUntilFindText("OK", 5000);
        if (text_exists("OK")) {
            clickByText("OK");
        }
        logger.info("已点击静音开关");
        waitTime(1);
        gDevice.pressBack();
        waitTime(1);
        gDevice.pressBack();
        waitTime(1);
    }

    public static void changeUpDownTo(String UpDown) throws Exception {
        makeScreenOn();
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("More settings");
        clickByText("More settings");
        waitTime(1);
        Iris4GAction.ScrollViewByText("Up/Down");
        clickByText("Up/Down");
        waitTime(3);
        clickByText(UpDown);
        Spoon.screenshot("UpDown", "changeUpDownTo"+UpDown);
        waitTime(1);
        logger.info("已修改为："+UpDown);
        gDevice.pressBack();
        waitTime(1);
        gDevice.pressBack();
        waitTime(1);
    }

    public static void clickSwitch(String switchName) throws Exception {
        makeScreenOn();
        clickById(Iris4GPage.camera_setting_shortcut_id);
        waitTime(1);
        Iris4GAction.ScrollViewByText("More settings");
        clickByText("More settings");
        waitTime(1);
        Iris4GAction.ScrollViewByText(switchName);
        CameraAction.openCompoundButton(switchName);
        logger.info("已点击" + switchName);
        waitTime(2);
        gDevice.pressBack();
        waitTime(1);
        gDevice.pressBack();
        waitTime(1);
    }

    public static void clickSwitchForVideo(String switchName) throws Exception {
        makeScreenOn();
        clickById(Iris4GPage.camera_setting_shortcut_id);
        waitTime(1);
        Iris4GAction.ScrollViewByText(switchName);
        CameraAction.openCompoundButton(switchName);
        logger.info("已点击" + switchName);
        waitTime(2);
        gDevice.pressBack();
        waitTime(1);
    }

    public static String getStr(String str,String target, int times) {
        int i = 0,s = 0,a =0;
        while (i++ < times-1) {s = str.indexOf(target, s + 1);}
        i=0;
        while (i++ < times) {a = str.indexOf(target, a + 1);}
        return str.substring(s+1,a);
    }
    /*
    获取4G信号强度，并在屏幕上显示出来toast；返回
      */
    public static int getAndShow4GSignalStrength() throws IOException {
        String [] dumpInformation=gDevice.executeShellCommand("dumpsys telephony.registry").split
                ("mSignalStrength=SignalStrength: ");
        int signalStrength=Integer.valueOf(getStr(dumpInformation[1]," ",9))+3;
        String signalStrengthString= "4G信号："+String.valueOf(signalStrength)+"需要大于-79";
        String command = String.format("am broadcast -a com.sioeye.alert.action -e message %s -e time %d",signalStrengthString,8);
        gDevice.executeShellCommand(command);
        return signalStrength;
    }
    /*
    获取4G信号强度，返回
     */
    public static int get4GSignalStrength() throws IOException {
        String [] dumpInformation=gDevice.executeShellCommand("dumpsys telephony.registry").split
                ("mSignalStrength=SignalStrength: ");
        int signalStrength=Integer.valueOf(getStr(dumpInformation[1]," ",9))+3;
        return signalStrength;
    }

    /*
    直播时间，包涵输出当时的4G信号强度
     */
    public static void liveTime(BufferedWriter out) throws IOException {
        String writeString = System.currentTimeMillis() + ":" + get4GSignalStrength()+"\n";
        out.write(writeString);
        out.flush(); // 把缓存区内容压入文件
        waitTime(60);
        writeString = System.currentTimeMillis() + ":" + get4GSignalStrength()+"\n";
        out.write(writeString);
        out.flush(); // 把缓存区内容压入文件
        waitTime(60);
        writeString = System.currentTimeMillis() + ":" + get4GSignalStrength()+"\n";
        out.write(writeString);
        out.flush(); // 把缓存区内容压入文件
    }
    public static void galleryLiveTime(BufferedWriter out) throws IOException {
        String writeString = System.currentTimeMillis() + ":" + get4GSignalStrength()+"\n";
        out.write(writeString);
        out.flush(); // 把缓存区内容压入文件
        waitTime(58);
        writeString = System.currentTimeMillis() + ":" + get4GSignalStrength()+"\n";
        out.write(writeString);
        out.flush(); // 把缓存区内容压入文件
        waitTime(58);
        writeString = System.currentTimeMillis() + ":" + get4GSignalStrength()+"\n";
        out.write(writeString);
        out.flush(); // 把缓存区内容压入文件
    }

}
