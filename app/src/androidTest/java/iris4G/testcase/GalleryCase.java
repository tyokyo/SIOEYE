package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.GalleryAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;

import static iris4G.action.CameraAction.cameraSetting;
import static iris4G.action.CameraAction.navConfig;

/**
 * @Author yun.yang
 * @Description

 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class GalleryCase extends VP2 {
    Logger logger = Logger.getLogger(GalleryCase.class.getName());

    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
        if (!AccountAction.isLogin()) {AccountAction.loginAccount(Constant.getUserName("sioeye_id"),Constant.getPassword("sioeye_password"));}
    }
    @Test
    public void testCheckSupport() throws Exception {
        waitTime(1);
        navConfig(Iris4GPage.nav_menu[1]);
        cameraSetting();
        clickByText("Video Quality");
        Iris4GAction.ScrollViewByText(Iris4GPage.video_quality[5]);
        UiObject2 scrollView = getObject2ByClass(android.widget.LinearLayout.class);
        List<UiObject2> relatives = scrollView.findObjects(By.clazz(android.widget.RelativeLayout.class));
        String supportLiveString=null;
        for (UiObject2 relateLayout : relatives) {
            boolean textQuality = relateLayout.hasObject(By.text(Iris4GPage.video_quality[5]));
            boolean textSupport = relateLayout.hasObject(By.depth(3));
            if (textQuality && textSupport){
                supportLiveString=relateLayout.findObject(By.depth(3)).getText();
                logger.info("theStringIs:"+supportLiveString);
            }else {
                Assert.fail("failed");
            }
        }
    }
    @Test
    /*
    检查慢速录制的大于10秒和小于10秒视频，相册是否有live选项；大于10秒的视频发起相册直播
     */
    public void testCheckSlo_MoVideoLiveButton() throws Exception {
        CameraAction.makeSlo_MoSomeTime(5);
        CameraAction.makeSlo_MoSomeTime(1);
        Iris4GAction.startGallery();
        if (!GalleryAction.checkLiveBottom()){
            getObjectById("com.hicam.gallery:id/gl_root_view").swipeLeft(60);
            if (GalleryAction.checkLiveBottom()){
                if (!GalleryAction.makeGalleryLive()){
                    Assert.fail("makeGalleryLiveFailed");
                }
            }else {Assert.fail("Slo_MoVideoMoreThan10sButAbsentLiveBottom");}
        }else {Assert.fail("Slo_MoVideoLessThan10sButExistLiveBottom");}
    }
    @Test
    /*
    检查延时录制的大于10秒和小于10秒视频，相册是否有live选项；大于10秒的视频发起相册直播
     */
    public void testCheckLapseVideoLiveButton() throws Exception {
        CameraAction.makeLapseSomeTime(12);
        CameraAction.makeLapseSomeTime(2);
        Iris4GAction.startGallery();
        if (!GalleryAction.checkLiveBottom()){
            getObjectById("com.hicam.gallery:id/gl_root_view").swipeLeft(60);
            if (GalleryAction.checkLiveBottom()){
                if (!GalleryAction.makeGalleryLive()){
                    Assert.fail("makeGalleryLiveFailed");
                }
            }else {Assert.fail("Slo_MoVideoMoreThan10sButAbsentLiveBottom");}
        }else {Assert.fail("Slo_MoVideoLessThan10sButExistLiveBottom");}
    }
}