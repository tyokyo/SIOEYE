package iris4G.testcase;

import android.os.RemoteException;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import android.view.KeyEvent;

import junit.framework.Assert;

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
import iris4G.page.NavPage;

/**
 * Created by elon on 2016/12/8.
 */
@RunWith(AndroidJUnit4.class)
public class ScreenCase extends VP2 {
    public static int WaitTime = 10;
    Logger logger = Logger.getLogger(ScreenCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void testScreenOnOff() throws RemoteException {
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);         //按电源键6次
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);   		//灭屏1分钟
        waitTime(60);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        if(gDevice.isScreenOn()){
            System.out.println("testReview_Screen_onoff case was passed");
        }else {
            Asst.fail("testReview_Screen_onoff failed");
        }
    }
    /**
     * @Author caiBin
     * @Description
     *  case:普通录像中，单击power键，熄屏，正常录制；
     * 	再次点击power键，亮屏，正常录制
     */
    @Test
    public void testSleepTimeNever() throws Exception {
        Iris4GAction.startSettings();

        gDevice.waitForWindowUpdate("com.android.settings", 5000);
        clickByText("Device");
        Iris4GAction.ScrollViewByText("Display");
        clickByText("Display");
        clickByText("Sleep");
        Iris4GAction.ScrollViewByText("Never");
        clickByText("Never");

        gDevice.pressBack();
        gDevice.pressBack();
        gDevice.pressBack();
        //gDevice.pressMenu();
        waitTime(3);
        clickByText("Connection");
        clickByText("Wi-Fi");
        waitTime(3);
        UiObject addNewNetWork = getObjectByTextContains("Add new network...");
        if (addNewNetWork.exists()) {
            clickById("android:id/switchWidget");
            addNewNetWork.waitUntilGone(10000);
        }
        Iris4GAction.ScrollViewByText("CKT");
        clickByText("CKT");
        UiObject password = getObjectById("com.android.settings:id/password");
        if (password!=null&&password.exists()) {
            password.setText("ck88888!");
        }
        if (text_exists("Connect")) {
            clickByText("Connect");
        }

        Iris4GAction.stopSettings();
    }
    @Test
    public void testVideoClickPower() throws Exception {
        if(!gDevice.isScreenOn()){
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            logger.info("make screen on");
        }
        initDevice();
        Iris4GAction.startCamera();
        HashSet<String> beforeTakeVideoList = Iris4GAction.FileList("/sdcard/video");
        logger.info("record when screen is on "+WaitTime+" seconds");
       //增加切换到video模式
        CameraAction.navConfig(NavPage.navConfig_Video);
        Iris4GAction.cameraKey();

        CameraAction.cameraRecordTime();
        waitTime(WaitTime);

        logger.info("record when screen is off "+WaitTime+" seconds");
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(WaitTime);

        logger.info("record when screen is on "+WaitTime+" seconds");
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(WaitTime);

        CameraAction.cameraRecordTime();
        Iris4GAction.cameraKey();

        logger.info("total record "+3*WaitTime+" seconds");
        waitTime(3);
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
                logger.info(videoName+" play success ");
            }
        }else {
            Asst.fail("Video-Count=1:Error");
        }
    }
}
