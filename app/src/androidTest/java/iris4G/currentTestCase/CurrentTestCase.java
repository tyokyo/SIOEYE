package iris4G.currentTestCase;

import android.os.RemoteException;
import android.support.test.uiautomator.UiObject;
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
        Iris4GAction.startCamera();
        waitTime(10);
        logger.info("launchCamera");
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
    private void p2pScreenOn() throws RemoteException {
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
        checkGalleryToLiveStatusAndTryAgain(0);
        makeScreenOn();
        stopGalleryLive();
        waitTime(2);
    }
    /**
     * 检查相册直播是否发起成功，并会重试一次
     * 若两次均失败会产生一个两分钟的电流锯齿波
     */
    private void checkGalleryToLiveStatusAndTryAgain(int a) throws Exception {
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
                if (a==0){makeScreenOff();}//判断用例该亮灭屏
                waitTime(testTime-2);
            }
        }else {
            logger.info("LiveSuccess");
            Spoon.screenshot("galleryLiveSuccess","galleryLiveSuccess");
            if (a==0){makeScreenOff();}
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
        gDevice.pressBack();
        Spoon.screenshot("configLiveVideoQuality",videoQuality);
        logger.info(" -configLiveVideoQuality - "+videoQuality);
        waitTime(2);
    }
    private void configVideoAngle(String VideoAngle) throws Exception {
        makeScreenOn();
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("Video Angle");
        clickByText("Video Angle");
        waitTime(2);
        clickByText(VideoAngle);
        Spoon.screenshot("VideoAngle", "changeVideoAngleTo"+VideoAngle);
        waitTime(2);
        gDevice.pressBack();
        logger.info("已修改视场角为："+VideoAngle);
        waitTime(2);
    }
    private void clickLiveMute() throws Exception {
        makeScreenOn();
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("Live Mute");
        CameraAction.openCompoundButton("Live Mute");
        Spoon.screenshot("LiveMute", "clickLiveMute");
        waitTime(2);
        waitUntilFindText("OK", 5000);
        if (text_exists("OK")) {
            clickByText("OK");
        }
        gDevice.pressBack();
        logger.info("已点击静音开关");
        waitTime(2);
    }
    private void changeUpDownTo(String UpDown) throws Exception {
        makeScreenOn();
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("Up/Down");
        clickByText("Up/Down");
        waitTime(3);
        clickByText(UpDown);
        Spoon.screenshot("UpDown", "changeUpDownTo"+UpDown);
        waitTime(2);
        gDevice.pressBack();
        logger.info("已修改为："+UpDown);
        waitTime(2);
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
        Iris4GAction.ScrollViewByText(switchName);
        CameraAction.openCompoundButton(switchName);
        logger.info("已点击" + switchName);
        waitTime(2);
        gDevice.pressBack();
        waitTime(2);
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
//    @Test
//    public void testGalleryLive() throws Exception {
//        live2ScreenOn();
//        Iris4GAction.startGallery();
//        galleryLiveScreenOff();
//    }

    @Test
    public void testForCurrent() throws Exception {
        String liveQuality480SD="480@25FPS(SD)",
                liveQuality480HD="480@25FPS(HD)",
                liveQuality720HD="720@25FPS(HD)";
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
//            changeSleepTime("Never");
            switchTo4G();
            storageFormat();//格式化储存空间
            closeWifi();
            //开启相机
            launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[0]);//Live Modem
            makeToasts("start"+i,5);
            waitTime(2);

            configVideoQuality(liveQuality480SD);
            //4G亮屏直播不保存480SD
            live2ScreenOn();
            //4G灭屏直播不保存480SD
            live2ScreenOff();
            //4G亮屏直播不保存480HD
            configVideoQuality(liveQuality480HD);
            live2ScreenOn();
            //4G灭屏直播不保存480HD
            live2ScreenOff();
            //4G亮屏直播不保存720HD
            configVideoQuality(liveQuality720HD);
            live2ScreenOn();
            //4G灭屏直播不保存720HD
            live2ScreenOff();
            Iris4GAction.clickLiveAndSave();//开启直播保存
            waitTime(2);
            configVideoQuality(liveQuality480SD);
            //4G亮屏直播保存480SD
            logger.info("case：4G亮屏直播保存480SD");
            live2ScreenOn();
            //4G灭屏直播保存480SD
            live2ScreenOff();
            //4G亮屏直播保存480HD
            logger.info("case:4G灭屏直播保存480HD");
            configVideoQuality(liveQuality480HD);
            live2ScreenOn();
            //4G灭屏直播保存480HD
            live2ScreenOff();
            //4G亮屏直播保存720HD
            configVideoQuality(liveQuality720HD);
            live2ScreenOn();
            //4G灭屏直播保存720HD
            live2ScreenOff();
            Iris4GAction.clickLiveAndSave();//关闭直播保存
            //高度4G灭屏直播480SD

            waitTime(1);
            clickSwitch(switchName[0]);//开启高度计
            live2ScreenOff();
            //速度计4G灭屏直播480SD
            configVideoQuality(liveQuality480SD);
            clickSwitch(switchName[0]);//关闭高度计
            waitTime(2);
            clickSwitch(switchName[1]);//开启速度计
            logger.info("case:开启速度计4G灭屏直播保存720HD");
            live2ScreenOff();
            clickSwitch(switchName[1]);//关闭速度计
            waitTime(2);
            clickLiveMute();  //开启静音开关
            live2ScreenOff();
            clickLiveMute();//关闭静音开关
            clickSwitch(switchName[3]); //开启防抖开关
            live2ScreenOff();
            clickSwitch(switchName[3]);//关闭防抖开关
            clickSwitch(switchName[4]);//开启语音交互
            live2ScreenOff();
            clickSwitch(switchName[4]);//关闭语音交互
            changeUpDownTo("Down"); //修改为倒置
            live2ScreenOff();
            changeUpDownTo("Up");//修改为Up
            configVideoAngle(videoAngle[0]);//视场角为普通
            live2ScreenOff();
            configVideoAngle(videoAngle[1]);//视场角为宽
            live2ScreenOff();
            configVideoAngle(videoAngle[2]);//视场角为超级
            live2ScreenOff();
            configVideoAngle(videoAngle[0]);//视场角为普通(默认)
            gDevice.pressBack();
            gDevice.pressBack();
            //切换网络模式为3G
            switchTo3G();
            launchCamera();
            //3G亮屏直播不保存480SD
            logger.info("case:3G灭屏直播不保存480SD");
            CameraAction.navConfig(Iris4GPage.nav_menu[0]);//Live Modem
            waitTime(2);
            configVideoQuality(liveQuality480SD);
            live2ScreenOn();
            //3G灭屏直播不保存480SD
            logger.info("case:3G亮屏直播不保存480SD");
            live2ScreenOff();
            //3G亮屏直播保存480SD
            Iris4GAction.clickLiveAndSave();//开启直播保存
            logger.info("3G灭屏直播保存480SD");
            live2ScreenOn();
            //3G灭屏直播保存480SD
            logger.info("3G亮屏直播保存480SD");
            live2ScreenOff();
            Iris4GAction.clickLiveAndSave();//关闭直播保存
            waitTime(2);
            //连接wifi相机预览界面亮屏
            gDevice.pressBack();
            gDevice.pressBack();
            openWifi();
            gDevice.executeShellCommand("dumpsys battery set level 100");//修改电量显示
            launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[0]);//Live Modem
            waitTime(2);
            Iris4GAction.clickLiveAndSave();//开启直播保存
            configVideoQuality(liveQuality480SD);
            //WIFI亮屏直播保存480SD
            live2ScreenOn();
            //WIFI灭屏直播保存480SD
            live2ScreenOff();
            Iris4GAction.clickLiveAndSave();//关闭直播保存
            waitTime(1);
            //WIFI亮屏直播不保存480SD
            live2ScreenOn();
            //WIFI灭屏直播不保存480SD
            live2ScreenOff();
            configVideoQuality(liveQuality480HD);
            //wifi 480HD 亮屏
            live2ScreenOn();
            //wifi 480HD 灭屏
            live2ScreenOff();
            configVideoQuality(liveQuality720HD);
            //wifi 720HD 亮屏
            live2ScreenOn();
            //wifi 720HD 灭屏
            live2ScreenOff();
            gDevice.pressHome();
            gDevice.pressHome();
            switchTo4G();
            closeWifi();
            //开启相机
            launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[1]);//Video Modem
            waitTime(2);
            //四种分辨率灭屏录像2分钟
            configVideoQuality(videoQuality1080P25);//1080P30FPS
            logger.info("case:1080PVideoScreenOn");
            p2pScreenOn();
            logger.info("case:1080PVideoScreenOff");
            p2pScreenOff();
            configVideoQuality(videoQuality720P60);//720P60FPS
            p2pScreenOff();
            configVideoQuality(videoQuality720P25);//720P30FPS
            logger.info("case:720P30FPSVideoScreenOn");
            p2pScreenOn();
            logger.info("case:720P30FPSVideoScreenOff");
            p2pScreenOff();
            Iris4GAction.stopCamera();
            waitTime(2);
            Iris4GAction.startGallery();
            galleryLiveScreenOn();//4G相册亮屏直播720P
            galleryLiveScreenOff();//4G相册灭屏直播720P
            Iris4GAction.stopGallery();
            openWifi();
            Iris4GAction.startGallery();
//            galleryToLiveScreenOn();//WIFI相册亮屏直播720P
            galleryLiveScreenOff();//WIFI相册灭屏直播720P
            Iris4GAction.stopGallery();
            closeWifi();
            launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[1]);//Video Modem
            waitTime(2);
            configVideoQuality(videoQuality480P120);//480P120FPS
            p2pScreenOff();
            //亮屏录播
            configVideoQuality(videoQuality480P25);//480P25FPS
            p2pScreenOff();
            Iris4GAction.stopCamera();
            waitTime(2);
            Iris4GAction.startGallery();
            galleryLiveScreenOff();//4G相册灭屏直播480P
            openWifi();
            Iris4GAction.startGallery();
            galleryLiveScreenOff();//WIFI相册灭屏直播480P
            closeWifi();
            launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[1]);//Video Modem
            waitTime(2);
            clickSwitch(switchName[2]);//开启录播
            p2pScreenOn();
            //灭屏录播
            p2pScreenOff();
            clickSwitch(switchName[2]);//关闭录播
            //灭屏慢速录像2min
            CameraAction.navConfig(Iris4GPage.nav_menu[4]);//"Slo_Mo" Modem
            waitTime(2);
            p2pScreenOff();
            //灭屏延时录像2min
            CameraAction.navConfig(Iris4GPage.nav_menu[5]);//"Lapse" Modem
            waitTime(2);
            logger.info("case：灭屏延时录像");
            p2pScreenOff();
            //相机预览界面亮屏
            logger.info("case:相机预览界面亮屏待机");
            CameraAction.navConfig(Iris4GPage.nav_menu[1]);//Video Modem
            waitTime(2);
            configVideoQuality(videoQuality1080P25);//1080P30FPS
            waitTime(2);
            waitTime(testTime);
            gDevice.pressBack();
            gDevice.pressBack();
            gDevice.pressBack();
            logger.info("主屏幕亮屏待机");
            waitTime(testTime);
            makeToast("end-"+i,5);
        }
        makeToast("10秒后关机......",5);
        logger.info("10秒后关机....");
        waitTime(10);
        gDevice.executeShellCommand("reboot -p ");
    }
//    @Test
//    public void testClickAirModem() throws Exception {
//        gDevice.pressBack();
//        gDevice.pressBack();
//        waitTime(3);
//        galleryLiveScreenOff();
//    }
}


//            //4G开启运程控制灭屏待机
//            logger.info("case:4G开启运程控制灭屏待机");
//            openRemoteControl();
//            makeScreenOff();
//            waitTime(testStandbyTime);
//            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
//            waitTime(1);
//            makeScreenOn();
//            closeRemoteControl();
//            连接wifi相机预览界面灭屏待机
//            makeScreenOff();
//            logger.info("连接wifi相机预览界面灭屏待机");
//            waitTime(testStandbyTime);
//            //WIFI灭屏直播不保存480SD
//            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
//            configVideoQuality(liveQuality720HD);
//            live2ScreenOn();
//            live2ScreenOff();
//            //WIFI开启运程控制灭屏待机
//            openRemoteControl();
//            makeScreenOff();
//            logger.info("WIFI开启运程控制灭屏待机");
//            waitTime(testStandbyTime);
//            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
//            waitTime(2);
//            makeScreenOn();
//            closeRemoteControl();

//    public boolean sendKey(int keyCode, int metaState) {
//        if(DEBUG) {
//            Log.d(LOG_TAG, "sendKey (" + keyCode + ", " + metaState + ")");
//        }
//
//        long eventTime = SystemClock.uptimeMillis();
//        KeyEvent downEvent = new KeyEvent(eventTime, eventTime, 0, keyCode, 0, metaState, -1, 0, 0, 257);
//        if(this.injectEventSync(downEvent)) {
//            KeyEvent upEvent = new KeyEvent(eventTime, eventTime, 1, keyCode, 0, metaState, -1, 0, 0, 257);
//            if(this.injectEventSync(upEvent)) {
//                return true;
//            }
//        }
//        return false;
//    }
//private void openRemoteControl() throws Exception {
//    makeScreenOn();
//    CameraAction.cameraSetting();
//    waitTime(1);
//    Iris4GAction.ScrollViewByText("Remote control");
//    CameraAction.openCompoundButton("Remote control");
//    waitUntilFindText("OK", 5000);
//    if (text_exists("OK")) {
//        clickByText("OK");
//    }
//    Spoon.screenshot("RemoteControl", "RemoteControl");
//    waitTime(1);
//    waitUntilFindText("You are in remote control:",5000);
//    if (text_exists("You are in remote control:")){
//        logger.info("远程控制已连接服务器");
//        waitTime(2);
//    }else {
//        waitTime(20);
//    }
//}
//    private void closeRemoteControl() throws RemoteException {
//        makeScreenOn();
//        clickById(Iris4GPage.btn_manual);
//        waitTime(2);
//    }
//private void clickAirModem() throws Exception {
//    Iris4GAction.startSettings();
//    clickByText("Advance");
//    waitTime(1);
//    clickByText("Developer options");
//    Iris4GAction.scrollTextIntoView("Airplane mode");
//    clickByText("Airplane mode");
//    waitTime(1);
//    gDevice.pressBack();
//    waitTime(1);
//    gDevice.pressBack();
//    waitTime(1);
//    gDevice.pressBack();
//}
