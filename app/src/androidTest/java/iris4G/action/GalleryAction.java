package iris4G.action;

import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

import com.squareup.spoon.Spoon;

import ckt.base.VP2;
import iris4G.page.GalleryPage;

/**
 * Created by user on 2017/12/06   .
 */
public class GalleryAction extends VP2 {
    public static Boolean checkLiveBottom() throws Exception {
        clickById(GalleryPage.video_timeText);//点击屏幕
        waitTime(1);
        if (!gDevice.findObject(new UiSelector().resourceId(GalleryPage.gallery_live_bottom)).exists()) {
            Spoon.screenshot("absentLiveBottom");
            return false;
        }
        Spoon.screenshot("existLiveBottom");
        return true;
    }
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
            //结束直播
            gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
            clickByText("Yes");
            gDevice.pressBack();
            gDevice.pressBack();
            return true;
        }
        //结束直播
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        clickByText("Yes");
        gDevice.pressBack();
        gDevice.pressBack();
        return true;
    }
}
