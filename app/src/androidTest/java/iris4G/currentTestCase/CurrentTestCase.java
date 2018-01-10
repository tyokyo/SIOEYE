package iris4G.currentTestCase;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;
import com.squareup.spoon.Spoon;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.logging.Logger;
import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.action.SettingAction;
import iris4G.page.Iris4GPage;
import iris4G.page.SettingPage;

/**
 * Created by yun.yang on 2017/4/6.
 * 执行前手动设置
 * 1.系统语言为英语；
 * 2.灭屏时间为永不；
 * 3.插上SIM卡；
 * 4.插上SD卡；
 * 5.连接1个信号网速良好的WiFi；
 * 6.将config.properties账号信息配置文件放置在相机中
 * 7.单条测试时间配置testTime
 */
public class CurrentTestCase extends VP2 {
    Logger logger = Logger.getLogger(CurrentTestCase.class.getName());
    private int testTime=120;

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
    public static int get4GSignalStrength() throws IOException {
        String [] dumpInformation=gDevice.executeShellCommand("dumpsys telephony.registry").split
                ("mSignalStrength=SignalStrength: ");
        int signalStrength=Integer.valueOf(getStr(dumpInformation[1]," ",9))+3;
        String signalStrengthString= "4G信号："+String.valueOf(signalStrength)+"需要大于-79";
        String command = String.format("am broadcast -a com.sioeye.alert.action -e message %s -e time %d",signalStrengthString,8);
        gDevice.executeShellCommand(command);
        return signalStrength;
    }

    /**
     * 格式化存储空间
     */
    private void storageFormat() throws Exception {
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
    private void closeWifi() throws Exception {
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
    private void openWifi() throws Exception {
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
    private void launchCamera() throws Exception {
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
    private void makeScreenOff() throws RemoteException {
        if (gDevice.isScreenOn()) {
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            logger.info("make screen off");
        }
        waitTime(2);
    }

    /**
     *亮屏
     */
    private void makeScreenOn() throws RemoteException {
        waitTime(1);
        if (!gDevice.isScreenOn()) {
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            logger.info("make screen on");
        }
        waitTime(2);
    }

    /**
     *结束直播
     */
    private void makeLiveStop() throws Exception {
        waitTime(1);
        makeScreenOn();
        if (text_exists("OK")) {
            logger.info("Bad network, livestream has ended");
            Spoon.screenshot("BadNetworkLiveHasEnded","BadNetworkLiveHasEnded");
            clickByText("OK");
            logger.info("SawtoothWaveNoteLiveBreakOff");
            for (int i=0;i<3;i++){
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
     *灭屏录像
     */
    private void p2pScreenOff() throws RemoteException {
        makeScreenOn();
        Iris4GAction.cameraKey();
        logger.info("LaunchCameraKeyForStart");
        waitTime(2);
        makeScreenOff();
        logger.info("已灭屏");
        waitTime(3);
        waitTime(testTime);
        makeScreenOn();
        Iris4GAction.cameraKey();
        logger.info("LaunchCameraKeyForEnd");
        waitTime(5);
    }

    /**
     *亮屏录像
     */
    private void p2pScreenOn() throws RemoteException, IOException {
        makeScreenOn();
        Iris4GAction.cameraKey();
        logger.info("LaunchCameraKeyForStart");
        waitTime(3);
        waitTime(testTime);
        Iris4GAction.cameraKey();
        logger.info("LaunchCameraKeyForEnd");
        waitTime(5);
    }

    /**
     *亮屏直播
     */
    private void live2ScreenOn() throws Exception {
        makeScreenOn();
        Iris4GAction.cameraKey();
        logger.info("LaunchCameraKeyForStart");
        checkLiveStatusAndTry2s(1);//0---灭屏；1---亮屏
        waitTime(1);
    }

    /**
     *灭屏屏直播
     */
    private void live2ScreenOff() throws Exception {
        makeScreenOn();
        Iris4GAction.cameraKey();
        logger.info("LaunchCameraKeyForStart");
        waitTime(2);
        checkLiveStatusAndTry2s(0);
        waitTime(1);
    }

    /**
     *检查发起直播是否成功，并会重试一次
     * 两次失败会产生一个两分钟的电流锯齿波
     */
    private void checkLiveStatusAndTry2s(int a) throws Exception {
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
                if (a==0){makeScreenOff();}
                waitTime(testTime);
                makeLiveStop();
            }
        } else {
            logger.info("Live succeed");
            if (a==0){makeScreenOff();}
            waitTime(testTime);
            makeLiveStop();
        }
    }

    /**
     * 相册亮屏直播，相册第一个视频可以直播
     */
    private void galleryLiveScreenOn() throws Exception {
        clickById(SettingPage.video_timeText);//点击屏幕
        clickById(SettingPage.gallery_live_bottom);//点击live
        waitTime(2);
        clickById(SettingPage.gallery_live);
        waitTime(1);
        clickById(SettingPage.gallery_skip);
        checkGalleryToLiveStatusAndTryAgain(1);
        stopGalleryLive();
        waitTime(2);
    }

    /**
     * 相册灭屏直播，相册第一个视频可以直播
     */
    private void galleryLiveScreenOff() throws Exception {
        clickById(SettingPage.video_timeText);//点击屏幕
        clickById(SettingPage.gallery_live_bottom);//点击live
        waitTime(2);
        clickById(SettingPage.gallery_live);
        waitTime(1);
        clickById(SettingPage.gallery_skip);
        checkGalleryToLiveStatusAndTryAgain(0);
        makeScreenOn();
        stopGalleryLive();
        waitTime(2);
    }

    /**
     * 检查相册直播是否发起成功，并会重试一次
     * 若两次均失败会产生一个两分钟的电流锯齿波
     */
    private void checkGalleryToLiveStatusAndTryAgain(int screenStatus ) throws Exception {
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
                if (screenStatus==0){makeScreenOff();}//判断用例该亮灭屏
                waitTime(testTime-2);
            }
        }else {
            logger.info("LiveSuccess");
            Spoon.screenshot("galleryLiveSuccess","galleryLiveSuccess");
            if (screenStatus==0){makeScreenOff();}
            waitTime(testTime-2);
        }
    }

    /**
     * 停止相册直播
     */
    private void stopGalleryLive()throws Exception{
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
    private void configVideoQuality(String videoQuality) throws Exception {
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

    private void configUserDefinedLiveQuality(String resolution,String minBitrate,String maxBitrate) throws
            Exception {
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

    private void configVideoAngle(String VideoAngle) throws Exception {
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

    private void clickLiveMute() throws Exception {
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

    private void changeUpDownTo(String UpDown) throws Exception {
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

    private void switchTo3G() throws Exception {
        makeScreenOn();
        SettingAction.navToPreferredNetworkType();
        clickByText("3G");
        waitTime(3);
        logger.info("已设置网络模式为3G");
        Iris4GAction.stopSettings();
        waitTime(2);
    }

    private void switchTo4G() throws Exception {
        makeScreenOn();
        SettingAction.navToPreferredNetworkType();
        clickByText("4G (recommended)");
        waitTime(3);
        logger.info("已设置网络模式为4G");
        Iris4GAction.stopSettings();
        waitTime(2);
    }

    private void changeSleepTime(String sleepTime) throws Exception {
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

    private void makeToasts(String message,int time) throws IOException {
//        initDevice();
        String command = String.format("am broadcast -a com.sioeye.alert.action -e message %s -e time %d",message,time);
        logger.info(command);
        gDevice.executeShellCommand(command);
    }

    private void clickSwitch(String switchName) throws Exception {
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

    private void clickSwitchForVideo(String switchName) throws Exception {
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

    private void liveOfBiggerZoom() throws Exception {
        makeScreenOn();
        Iris4GAction.cameraKey();
        makeLive();
        clickById(Iris4GPage.recording_time_id);
        clickById(Iris4GPage.recording_time_id);
        getObjectById(Iris4GPage.content_id).swipeRight(80);
        makeScreenOff();
        waitTime(testTime);
        makeScreenOn();
        makeLiveStop();
    }

    private void makeLive() throws Exception {
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
            }
        } else {
            logger.info("Live succeed");
        }

    }

    @Before
    public void setup() throws Exception {
        initDevice();
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        //登录账号
        AccountAction.loginAccount(useName, password);
    }

    @Test
    public void testForCurrent() throws Exception {
        String liveQuality480="480@25FPS(Bitrate0.3-4Mbps)",
                liveQuality720HD="720@25FPS(Bitrate1.3-6Mbps)",
                liveQualityUserDefined="User Defined(720@30FPS Bitrate0.4-10.0Mbps)";
        String videoQuality1080P25="1080@25FPS",
                videoQuality720P60="720@60FPS",
                videoQuality720P25="720@25FPS",
                videoQuality480P120="480@120FPS",
                videoQuality480P25="480@25FPS";
        String switchName[]={
                "Altimeter",//高度计0
                "Speedometer",//速度计1
                "Video&Live(beta)",//录播2
                "Anti-shake",//防抖3
                "Voice interaction",//语音交互4
        };
        String videoAngle[]={
                "Medium",
                "Wide",
                "Super Wide"
        };
        for (int i = 1;i<=1;i++){
            makeScreenOn();
            gDevice.executeShellCommand("dumpsys battery set level 100");//修改电量显示
//            changeSleepTime("Never");
            switchTo4G();
            storageFormat();//格式化储存空间
            closeWifi();
            launchCamera();
            int time=0;
            while (time<12){//要求4G信号强度连续1分钟大于-79dbm
                if (get4GSignalStrength()>-79){
                    time=time+1;
                }else {time=0;}
                logger.info("time:"+time*5+"秒");
                waitTime(5);
            }
            makeToasts("Start"+i,5);
            waitTime(2);
            //开始录像测试
            CameraAction.navConfig(Iris4GPage.nav_menu[1]);//Video Modem
            waitTime(2);
            //1080P25
            configVideoQuality(videoQuality1080P25);
            p2pScreenOn();
            p2pScreenOff();
            //720P60
            configVideoQuality(videoQuality720P60);
            p2pScreenOff();
            //720P25
            configVideoQuality(videoQuality720P25);
            p2pScreenOn();
            p2pScreenOff();
            //4G相册直播720P
            Iris4GAction.stopCamera();
            waitTime(2);
            Iris4GAction.startGallery();
            galleryLiveScreenOn();
            galleryLiveScreenOff();
            //WIFI相册直播720P
            Iris4GAction.stopGallery();
            openWifi();
            Iris4GAction.startGallery();
            galleryLiveScreenOff();
            //480P120FPS
            Iris4GAction.stopGallery();
            closeWifi();
            launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[1]);//Video Modem
            waitTime(2);
            configVideoQuality(videoQuality480P120);
            p2pScreenOff();
            //480P25FPS
            configVideoQuality(videoQuality480P25);
            p2pScreenOff();
            //4G相册直播480P
            Iris4GAction.stopCamera();
            waitTime(2);
            Iris4GAction.startGallery();
            galleryLiveScreenOff();
            //WIFI相册直播480P
            Iris4GAction.stopGallery();
            openWifi();
            Iris4GAction.startGallery();
            galleryLiveScreenOff();
            //慢速+延时录像
            closeWifi();
            launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[4]);//"Slo_Mo" Modem
            waitTime(2);
            p2pScreenOff();
            CameraAction.navConfig(Iris4GPage.nav_menu[5]);//"Lapse" Modem
            waitTime(2);
            p2pScreenOff();
            //相机预览界面亮屏
            CameraAction.navConfig(Iris4GPage.nav_menu[1]);//Video Modem
            waitTime(2);
            configVideoQuality(videoQuality720P25);
            waitTime(testTime+20);
            //录播
            clickSwitchForVideo(switchName[2]);//开启录播
            p2pScreenOn();
            p2pScreenOff();
            clickSwitchForVideo(switchName[2]);//关闭录播
            //主屏幕亮屏待机 10分钟
            gDevice.pressBack();
            gDevice.pressBack();
            gDevice.pressBack();
            waitTime(testTime);
            waitTime(testTime);
            waitTime(testTime);
            //4G 不保存直播
            launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[0]);//Live Modem
            waitTime(2);
            configVideoQuality(liveQuality480);
            live2ScreenOn();
            live2ScreenOff();
            configVideoQuality(liveQuality720HD);
            live2ScreenOn();
            live2ScreenOff();
            //4G 保存直播
            Iris4GAction.clickLiveAndSave();//开启直播保存
            waitTime(2);
            configVideoQuality(liveQuality480);
            live2ScreenOn();
            live2ScreenOff();
            configVideoQuality(liveQuality720HD);
            live2ScreenOn();
            live2ScreenOff();
            Iris4GAction.clickLiveAndSave();//关闭直播保存
            waitTime(1);
            //自定义直播质量
            configUserDefinedLiveQuality("480P","200","200");
            live2ScreenOff();
            configUserDefinedLiveQuality("480P","5000","5000");
            live2ScreenOff();
            configUserDefinedLiveQuality("720P","400","400");
            live2ScreenOff();
            configUserDefinedLiveQuality("720P","10000","10000");
            live2ScreenOff();

            //其他设置项 高度计
            configVideoQuality(liveQuality480);
            waitTime(2);
            clickSwitch(switchName[0]);//开启高度计
            live2ScreenOff();
            clickSwitch(switchName[0]);//关闭高度计
            //速度计
            clickSwitch(switchName[1]);//开启速度计
            live2ScreenOff();
            clickSwitch(switchName[1]);//关闭速度计
            //静音
            clickLiveMute();  //开启静音开关
            live2ScreenOff();
            clickLiveMute();//关闭静音开关
            //防抖
            clickSwitch(switchName[3]); //开启防抖开关
            live2ScreenOff();
            clickSwitch(switchName[3]);//关闭防抖开关
            //语音交互
            clickSwitch(switchName[4]);//开启语音交互
            live2ScreenOff();
            clickSwitch(switchName[4]);//关闭语音交互
            //Down
            changeUpDownTo("Down"); //修改为倒置
            live2ScreenOff();
            changeUpDownTo("Up");//修改为Up
            //视场角
            configVideoAngle(videoAngle[0]);//视场角为普通
            live2ScreenOff();
            configVideoAngle(videoAngle[1]);//视场角为宽
            live2ScreenOff();
            configVideoAngle(videoAngle[2]);//视场角为超级
            live2ScreenOff();
            configVideoAngle(videoAngle[0]);//视场角为普通(默认)
            liveOfBiggerZoom();

            //3G直播不保存
            gDevice.pressBack();
            gDevice.pressBack();
            switchTo3G();
            launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[0]);//Live Modem
            waitTime(2);
            configVideoQuality(liveQuality480);
            live2ScreenOn();
            live2ScreenOff();
            //3G直播保存
            Iris4GAction.clickLiveAndSave();//开启直播保存
            live2ScreenOn();
            live2ScreenOff();
            Iris4GAction.clickLiveAndSave();//关闭直播保存
            waitTime(2);
            //WIFI不保存
            gDevice.pressBack();
            gDevice.pressBack();
            openWifi();
            launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[0]);//Live Modem
            waitTime(2);
            configVideoQuality(liveQuality480);
            live2ScreenOn();
            live2ScreenOff();
            configVideoQuality(liveQuality720HD);
            live2ScreenOn();
            live2ScreenOff();
            //WIFI保存
            Iris4GAction.clickLiveAndSave();//开启直播保存
            waitTime(1);
            configVideoQuality(liveQuality480);
            live2ScreenOn();
            live2ScreenOff();
            Iris4GAction.clickLiveAndSave();//关闭直播保存
            gDevice.pressBack();
            makeToast("end-"+i,5);
        }
        makeToast("10秒后关机......",5);
        waitTime(10);
        gDevice.executeShellCommand("reboot -p ");
    }
//    @Test
//    public void testGalleryLive() throws Exception {
//        configUserDefinedLiveQuality("480P","200","200");
//        live2ScreenOff();
//        configUserDefinedLiveQuality("480P","5000","5000");
//        live2ScreenOff();
//        configUserDefinedLiveQuality("720P","400","400");
//        live2ScreenOff();
//        configUserDefinedLiveQuality("720P","10000","10000");
//    }
}