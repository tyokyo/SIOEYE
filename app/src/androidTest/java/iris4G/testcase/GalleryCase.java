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
import iris4G.page.GalleryPage;
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
        Iris4GAction.initIris4GWithoutDelete();
        if (!AccountAction.isLogin()) {
            AccountAction.loginAccount(Constant.getUserName("sioeye_id"),Constant.getPassword("sioeye_password"));}
    }
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4GWithoutDelete();
    }
    /*
    录制两段延时视频，在相册中，第一段无法直播；第二段可以直播；即先录制可以直播的视频再录制不能直播的视频
    1.检查live标志是否正确；
    2.检查第二段是否能够发起相册直播
     */
    public void checkTwoLapseVideoLiveButtonAndLive(String VideoQuality,int timeLapse) throws Exception {
        CameraAction.makeLapseSomeTime(12,VideoQuality,timeLapse);
        CameraAction.makeLapseSomeTime(1,timeLapse);
        GalleryAction.checkTowVideoLiveButtonAndLive();
    }
    @Test
    /*
    case 1
    检查慢速录制的大于10秒和小于10秒视频，相册是否有live选项；大于10秒的视频发起相册直播
     */
    public void testCheckSlo_MoVideoLiveButton() throws Exception {
        CameraAction.makeSlo_MoSomeTime(5);
        CameraAction.makeSlo_MoSomeTime(1);
        GalleryAction.checkTowVideoLiveButtonAndLive();
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
    检查录制的大于10秒延时1080P延时视频是否可直播
     */
    public void testCheckLapse1080PVideoLiveButton() throws Exception {
        CameraAction.makeLapseSomeTime(12,Iris4GPage.lapse_quality[1],timeLapse[0]);
        Iris4GAction.startGallery();
        if (GalleryAction.checkLiveBottom()){
            Assert.fail("1080PAbsentLiveBottom");
        }
    }
    @Test
    /* Case 8
    检查普通720P录像视频
     */
    public void testCheckVideo720PLiveButton() throws Exception {
        CameraAction.makeVideoSomeTime(12,Iris4GPage.video_quality[5]);//720P25FPS
        CameraAction.makeVideoSomeTime(2);
        GalleryAction.checkTowVideoLiveButtonAndLive();
    }
    @Test
    /* Case 9
    检查普通480P录像视频
     */
    public void testCheckVideo480PLiveButton() throws Exception {
        CameraAction.makeVideoSomeTime(12,Iris4GPage.video_quality[6]);//480P25FPS
        CameraAction.makeVideoSomeTime(2);
        GalleryAction.checkTowVideoLiveButtonAndLive();
    }
    @Test
    /* case 10
    检查1080P25FPS 720P60FPS、480P120FPS 普通录像视频是否无live标志
     */
    public void testCheckVideoWrongResolutionLiveButton() throws Exception {
        CameraAction.makeVideoSomeTime(11,Iris4GPage.video_quality[4]);//1080P
        CameraAction.makeVideoSomeTime(11,Iris4GPage.video_quality[1]);//720P60FPS
        CameraAction.makeVideoSomeTime(11,Iris4GPage.video_quality[3]);//480P120FPS
        Iris4GAction.startGallery();
        if (!GalleryAction.checkLiveBottom()){
            getObjectById(GalleryPage.gallery_root_view).swipeLeft(60);
            if (!GalleryAction.checkLiveBottom()){
                getObjectById(GalleryPage.gallery_root_view).swipeLeft(60);
                if (GalleryAction.checkLiveBottom()){Assert.fail("1080PExistLiveBottom");}
            }else {Assert.fail("720P60FPSExistLiveBottom");}
        }else {Assert.fail("480P120FPSPExistLiveBottom");}
    }
    @Test
    /*case 11
    相册直播中按键操作;返回键、电源键、拍照键、S键；不包括长按S键
     */
    public void testClickKeyDuringGalleryLive() throws Exception {
        Iris4GAction.startGallery();
        while (!GalleryAction.checkLiveBottom()) {
            getObjectById(GalleryPage.gallery_root_view).swipeLeft(60);}
        GalleryAction.startGalleryLive();
        GalleryAction.checkKeyDuringGalleryLive();
        GalleryAction.stopGalleryLive();
    }
    @Test
    public void testCheckSupport() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[1]);
        CameraAction.cameraSetting();
        clickByText("Video Quality");
        Iris4GAction.ScrollViewByText(Iris4GPage.video_quality[5]);
        if (!GalleryAction.checkResolutionRightString(Iris4GPage.video_quality[5])){
            Assert.fail("theResolutionRightStringNotFindSupportLive");
        }
    }
}