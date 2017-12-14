package iris4G.action;

import android.os.RemoteException;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

import com.squareup.spoon.Spoon;

import org.junit.Assert;

import ckt.base.VP2;
import iris4G.page.GalleryPage;

/**
 * Created by user on 2017/12/06   .
 */
public class GalleryAction extends VP2 {
    public static Boolean checkLiveBottom() throws Exception {
        waitTime(2);
        if (!gDevice.findObject(new UiSelector().resourceId(GalleryPage.gallery_live_bottom)).exists()) {
            clickByPonit(60,60);;//点击屏幕
            waitTime(2);
            if (!gDevice.findObject(new UiSelector().resourceId(GalleryPage.gallery_live_bottom)).exists()){
                Spoon.screenshot("absentLiveBottom");
                return false;
            }
            return true;
        }
        return true;
    }
    /*
    检查相册发起直播，发起成功后即停止
     */
    public static Boolean makeGalleryLive() throws UiObjectNotFoundException {
        clickById(GalleryPage.gallery_live_bottom);
        waitTime(2);
        clickById(GalleryPage.gallery_live);
        waitTime(1);
        clickById(GalleryPage.gallery_skip);
        waitUntilFindText("broadcasting",15000);
        if (!text_exists("broadcasting")){
            clickById(GalleryPage.gallery_live_bottom);
            waitUntilFindText("broadcasting",15000);
            if (!text_exists("broadcasting")){
                return false;
            }
            gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);//结束直播
            clickByText("Yes");
            gDevice.pressBack();
            gDevice.pressBack();
            return true;
        }
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);//结束直播
        clickByText("Yes");
        gDevice.pressBack();
        gDevice.pressBack();
        return true;
    }
    /*
    发起相册直播，不停止；失败会尝试两次
     */
    public static void startGalleryLive() throws UiObjectNotFoundException {
        clickById(GalleryPage.gallery_live_bottom);
        waitTime(2);
        clickById(GalleryPage.gallery_live);
        waitTime(1);
        clickById(GalleryPage.gallery_skip);
        waitUntilFindText("broadcasting",15000);
        if (!text_exists("broadcasting")){
            clickById(GalleryPage.gallery_live_bottom);
            waitUntilFindText("broadcasting",15000);
            if (!text_exists("broadcasting")){
                Spoon.screenshot("GalleyLiveFailed");
            }
        }
    }
    /*
    结束相册直播
     */
    public static void stopGalleryLive() throws UiObjectNotFoundException {
        if (text_exists("broadcasting")) {
            gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
            clickByText("Yes");
            gDevice.pressBack();
            gDevice.pressBack();
        }
        if (text_exists("Confirm to exit live?")){
            clickByText("Yes");
            gDevice.pressBack();
            gDevice.pressBack();
        }
        if (text_exists("Already Lived")){
            logger.info("galleryLiveHasStopped");
        }
    }
    /*
    在相册直播中检查按键功能
     */
    public static void checkKeyDuringGalleryLive() throws UiObjectNotFoundException, RemoteException {
        if (!text_exists("broadcasting")) {
            Spoon.screenshot("GalleryLiveHasStopped");
            startGalleryLive();
        }
        gDevice.pressBack();//返回键弹出结束提示
        if (!gDevice.findObject(new UiSelector().text("Confirm to exit live?")).exists()){
            Assert.fail("backKeyNoResponse1DuringGalleryLive");}
        gDevice.pressBack();//返回键消除结束直播提示
        if (gDevice.findObject(new UiSelector().text("Confirm to exit live?")).exists()){
            Assert.fail("backKeyNoResponse2DuringGalleryLive");}
        gDevice.pressBack();
        if (!gDevice.findObject(new UiSelector().text("Confirm to exit live?")).exists()){
            Assert.fail("backKeyNoResponse1DuringGalleryLive");}
        clickByText("No");//cancel
        if (!text_exists("broadcasting")) {
            Assert.fail("cancelStopGalleryLiveFailed");}
        gDevice.pressBack();
        if (!gDevice.findObject(new UiSelector().text("Confirm to exit live?")).exists()){
            Assert.fail("backKeyNoResponse1DuringGalleryLive");}
        clickByText("Yes");//cancel
        if (!text_exists("Already Lived")){
            Assert.fail("backKeyStopGalleryLive");}
        startGalleryLive();
        gDevice.pressMenu();//menu键
        if (!text_exists("broadcasting")) {
            Assert.fail("cancelStopGalleryLiveFailed");}
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        if (!gDevice.findObject(new UiSelector().text("Confirm to exit live?")).exists()){
            Assert.fail("backKeyNoResponse1DuringGalleryLive");}
        clickByText("No");//cancel
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(1);
        if (gDevice.isScreenOn()) {
            Assert.fail("powerKeyOffFailed");}
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(1);
        if (!gDevice.isScreenOn()) {
            Assert.fail("powerKeyOnFailed");}
        gDevice.pressKeyCode(276);//S键
        if (!text_exists("Confirm to exit live?")) {
            Assert.fail("cancelStopGalleryLiveFailed");}
        clickByText("No");//cancel
        gDevice.pressKeyCode(276);
        if (!text_exists("Confirm to exit live?")) {
            Assert.fail("cancelStopGalleryLiveFailed");}
        clickByText("Yes");//通过S键结束
    }

    /*
    检查相册中位于第一和第二的两个视频的live标志，第一个应该没有直播标志，第二个有，并且发起相册直播
     */
    public static void checkTowVideoLiveButtonAndLive() throws Exception {
        Iris4GAction.startGallery();
        if (!GalleryAction.checkLiveBottom()){
            getObjectById(GalleryPage.gallery_root_view).swipeLeft(60);
            if (GalleryAction.checkLiveBottom()){
                if (!GalleryAction.makeGalleryLive()){
                    Assert.fail("makeGalleryLiveFailed");
                }
            }else {Assert.fail("VideoMoreThan10sButAbsentLiveBottom");}
        }else {Assert.fail("VideoLessThan10sButExistLiveBottom");}
    }
}
