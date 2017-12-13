package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.GalleryAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;

/**
 * @Author yun.yang
 * @Description

 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class GalleryCase extends VP2 {
    public int timeLapse[]={2,3,5,10};
    Logger logger = Logger.getLogger(GalleryCase.class.getName());

    @BeforeClass
    public static void beforeClassLogin() throws Exception {
        Iris4GAction.initIris4G();
        if (!AccountAction.isLogin()) {AccountAction.loginAccount(Constant.getUserName("sioeye_id"),Constant.getPassword("sioeye_password"));}
    }
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
//    @Test
//    public void testCheckSupport() throws Exception {
//        waitTime(1);
//        navConfig(Iris4GPage.nav_menu[1]);
//        cameraSetting();
//        clickByText("Video Quality");
//        Iris4GAction.ScrollViewByText(Iris4GPage.video_quality[5]);
//        UiObject2 scrollView = getObject2ByClass(android.widget.LinearLayout.class);
//        List<UiObject2> relatives = scrollView.findObjects(By.clazz(android.widget.RelativeLayout.class));
//        String supportLiveString=null;
//        for (UiObject2 relateLayout : relatives) {
//            boolean textQuality = relateLayout.hasObject(By.text(Iris4GPage.video_quality[5]));
//            boolean textSupport = relateLayout.hasObject(By.depth(3));
//            if (textQuality && textSupport){
//                supportLiveString=relateLayout.findObject(By.depth(3)).getText();
//                logger.info("theStringIs:"+supportLiveString);
//            }else {
//                Assert.fail("failed");
//            }
//        }
//    }
    /*
    录制两段延时视频，在相册中，第一段无法直播；第二段可以直播；即先录制可以直播的视频再录制不能直播的视频
    1.检查live标志是否正确；
    2.检查第二段是否能够发起相册直播
     */
    public void checkTwoLapseVideoLiveButtonAndLive(String VideoQuality,int timeLapse) throws Exception {
        CameraAction.makeLapseSomeTime(12,VideoQuality,timeLapse);
        CameraAction.makeLapseSomeTime(1,timeLapse);
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
    case 1
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
    /* case 2
    检查录制的10s延时录制的大于10秒和小于10秒视频，相册是否有live选项；大于10秒的视频发起相册直播
     */
    public void testCheckLapse10sVideoLiveButton() throws Exception {
        checkTwoLapseVideoLiveButtonAndLive(Iris4GPage.lapse_quality[2],timeLapse[3]);//480P、10s延时
    }
    @Test
    /* case 3
    检查录制的2s延时录制的大于10秒和小于10秒视频，相册是否有live选项；大于10秒的视频发起相册直播
     */
    public void testCheckLapse2sVideoLiveButton() throws Exception {
        checkTwoLapseVideoLiveButtonAndLive(Iris4GPage.lapse_quality[2],timeLapse[0]);//480P、2s延时
    }
    @Test
    /* case 4
    检查录制的3s延时录制的大于10秒和小于10秒视频，相册是否有live选项；大于10秒的视频发起相册直播
     */
    public void testCheckLapse3sVideoLiveButton() throws Exception {
        checkTwoLapseVideoLiveButtonAndLive(Iris4GPage.lapse_quality[2],timeLapse[1]);//480P、3s延时
    }
    @Test
    /* case 5
    检查录制的5s延时录制的大于10秒和小于10秒视频，相册是否有live选项；大于10秒的视频发起相册直播
     */
    public void testCheckLapse5sVideoLiveButton() throws Exception {
        checkTwoLapseVideoLiveButtonAndLive(Iris4GPage.lapse_quality[2],timeLapse[2]);//480P、5s延时
    }
    @Test
    /* case 6
    检查720P延时录制的大于10秒和小于10秒视频，相册是否有live选项；大于10秒的视频发起相册直播
     */
    public void testCheckLapse720PVideoLiveButton() throws Exception {
        checkTwoLapseVideoLiveButtonAndLive(Iris4GPage.lapse_quality[0],timeLapse[0]);//720P、2s延时
    }
    @Test
    /* case 7
    检查录制的大于10秒1080P延时视频是否可直播
     */
    public void testCheckLapse1080PVideoLiveButton() throws Exception {
        CameraAction.makeLapseSomeTime(11,Iris4GPage.lapse_quality[1],timeLapse[0]);
        Iris4GAction.startGallery();
        if (GalleryAction.checkLiveBottom()){
            Assert.fail("makeGalleryLiveFailed");
        }
    }
//    @Test
//    /* case 8
//    检查1080P25FPS 720P60FPS普通录像视频是否无live标志
//     */
//    public void testCheckVideoWrongResolutionLiveButton(){
//    }
}