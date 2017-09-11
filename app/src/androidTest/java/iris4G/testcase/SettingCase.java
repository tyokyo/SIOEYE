package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.Iris4GAction;
import iris4G.action.SettingAction;

/**
 * @Author yun.yang
 * @Description
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class SettingCase extends VP2 {
    Logger logger = Logger.getLogger(SettingCase.class.getName());
    @Before
    public void setup() throws Exception {
        try {
            initDevice();
            Iris4GAction.makeScreenOn();
            Iris4GAction.stopCamera();
            Iris4GAction.stopFileManager();
            Iris4GAction.stopGallery();
//            Iris4GAction.deleteVideo();
//            Iris4GAction.deletePhoto();
//            Iris4GAction.startCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testLinkCktWifi() throws Exception {

        Iris4GAction.startSettings();
        gDevice.waitForWindowUpdate("com.android.settings", 5000);
        clickByText("Device");
        Iris4GAction.ScrollViewByText("Display");
        clickByText("Display");
        clickByText("Sleep");
        Iris4GAction.ScrollViewByText("Never");
        clickByText("Never");

        //返回setting界面
        for(int i=0;i<3;i++){
        gDevice.pressBack();
        }
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
        UiObject passwd = getObjectById("com.android.settings:id/password");
        if (passwd!=null&&passwd.exists()) {
            passwd.setText("ck88888!");
        }
        if (text_exists("Connect")) {
            clickByText("Connect");
        }

        waitTime(1);
        UiObject linkok=getObjectById("com.android.settings:id/state");
        if(linkok.exists()){
            logger.info("first link ckt success");
        }else {
            logger.info("link ckt wifi failed ");
        }
        Iris4GAction.stopSettings();
    }
    @Test
    /**case 1
     * yun.yang
     * 录制一段视频检查设置存储是否更新
     */
    public void testStorageUpdate()throws Exception{
        Iris4GAction.startSettings();
        SettingAction.navToStorage();
        float originalUsed=SettingAction.getUesd();
        float originalFree=SettingAction.getFree();
        int makeVideoTime=50;
        Iris4GAction.markVideoSomeTime(makeVideoTime);
        waitTime(1);
        gDevice.pressMenu();
        SettingAction.navToStorage();
        float updateUsed=SettingAction.getUesd();
        float updateFree=SettingAction.getFree();
        float result=SettingAction.floatAbs(updateUsed,originalUsed,updateFree,originalFree);
        if (originalUsed==updateUsed||originalFree==updateFree||result>=0.02){
            Assert.fail("storageUsedOrFreeNotUpdate");
        }else
            logger.info("TheCaseStorageUpdatePassed");
    }
    @Test
    /*case2
    取消格式化后检查storage使用量不会变化
     */
    public void testCancelFormat() throws Exception {
        Iris4GAction.startSettings();
        SettingAction.navToStorage();
        float originalUsed=SettingAction.getUesd();
        float originalFree=SettingAction.getFree();
        SettingAction.cancelFormat();
        float updateUsed=SettingAction.getUesd();
        float updateFree=SettingAction.getFree();
        if (originalFree!=updateFree||originalUsed!=updateUsed){
            Assert.fail("storageFreeOrUesdDataChangedAfterCancelFormat");
        }else {
            logger.info("TheTestCancelFormatPassed");
        }
    }
    @Test
    /*
    case3
    格式化检查storage是否更新
     */
    public void testFormat() throws Exception {
        Iris4GAction.markVideoSomeTime(25);
        Iris4GAction.startSettings();
        SettingAction.navToStorage();
        float originalUsed=SettingAction.getUesd();
        float originalFree=SettingAction.getFree();
        SettingAction.format();
        float updateUsed=SettingAction.getUesd();
        float updateFree=SettingAction.getFree();
        if (updateFree<originalFree||updateUsed>originalUsed){
            Assert.fail("TheCaseTestFormatFailed");
        }else {
            logger.info("TheCaseTestFormatPassed");
        }
    }
    @Test
    /*
    case 4
    连续3次格式化，检查storage是否不变
     */
    public void testMultiFormat() throws Exception {
        Iris4GAction.startSettings();
        SettingAction.navToStorage();
        SettingAction.format();
        float originalUsed=SettingAction.getUesd();
        float originalFree=SettingAction.getFree();
        for (int i=0;i<3;i++){
            SettingAction.format();
            float formatUsed=SettingAction.getUesd();
            float formatFree=SettingAction.getFree();
            if (formatUsed==originalUsed&&formatFree==originalFree){
                logger.info("TheCaseTestMultiFormatPassed");
            }else {
                Assert.fail("TheCaseTestMultiFormatFailed");
            }
        }
    }
    @Test
    /*
    case 5
    无网络检查更新
     */
    public void testCheckUpdateOfNoAvailableNetwork() throws Exception {
        Iris4GAction.startSettings();
        SettingAction.closeWifi();
        SettingAction.disableSimData();
        SettingAction.navToUpdate();
        UiObject noAvailableNetwork =getUiObjectByText("no available network");
        if (noAvailableNetwork.exists()){
            logger.info("testCheckUpdateOfNoAvailableNetworkPass");
            SettingAction.openWifi();
            SettingAction.enableSimData();
        }else {
            SettingAction.openWifi();
            SettingAction.enableSimData();
            Assert.fail("notFound'NoAvailableNetwork'");
        }
    }
}
