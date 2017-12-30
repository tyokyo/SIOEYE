package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

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
        CameraAction.makeSlo_MoSomeTime(5);
        Iris4GAction.startGallery();
        GalleryAction.makeGalleryLive();
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
    case编号：SI-1863:中断直播
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
    /* case 12
    case编号：SI-2001:检查‘支持直播’标志
    检查“支持直播”标志；普通录像模式720P25FPS；480P25FPS   延时录像720P30FPS；480P30FPS
     */
    public void testCheckSupport() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[1]);//普通录像
        CameraAction.cameraSetting();
        clickByText("Video Quality");
        if (!GalleryAction.checkResolutionRightString(Iris4GPage.video_quality[5])){//720P25FPS
            Assert.fail("theResolutionNotFindSupportLive");
        }
        if (!GalleryAction.checkResolutionRightString(Iris4GPage.video_quality[6])){//480P25FPS
            Assert.fail("theResolutionNotFindSupportLive");
        }
        CameraAction.navConfig(Iris4GPage.nav_menu[5]);//延时
        CameraAction.cameraSetting();
        clickByText("Video Quality");
        if (!GalleryAction.checkResolutionRightString(Iris4GPage.lapse_quality[0])){//720P30FPS
            Assert.fail("theResolutionNotFindSupportLive");
        }
        if (!GalleryAction.checkResolutionRightString(Iris4GPage.lapse_quality[2])){//480P30FPS
            Assert.fail("theResolutionNotFindSupportLive");
        }
    }
    @Test
    /*case 13
    case编号：SI-1865:录播
    检查录播视频，是否存在“已直播”标志，以及发起直播
     */
    public void testVideoAndLiveMakeGalleryLive() throws Exception {
        CameraAction.makeVideoAndLiveSomeTime(13);
        Iris4GAction.startGallery();
        if (!gDevice.findObject(new UiSelector().text("Already Lived")).exists()){
            if (!GalleryAction.makeGalleryLive()){
                Assert.fail("MakeGalleryLiveFailed");
            }
        }else {
            Assert.fail("VideoAndLiveVideoExistLiveBottom");
        }
    }
    @Test
    /*case 14
    case编号：SI-1874:删除视频
    检查删除已直播的视频
     */
    public void testDeleteAlreadyLivedVideo() throws Exception {
        Iris4GAction.startGallery();
        while (!gDevice.findObject(new UiSelector().text("Already Lived")).exists()) {
            getObjectById(GalleryPage.gallery_root_view).swipeLeft(60);
            waitTime(1);
        }
        int originalRank=GalleryAction.getRankOfGallery();
        int originalTotal=GalleryAction.getTotalOfGallery();
        GalleryAction.deleteOneVideo();
        if (originalTotal==GalleryAction.getTotalOfGallery()+1){
            if (originalRank==GalleryAction.getRankOfGallery()+1||(originalRank==GalleryAction.getRankOfGallery()
                    &&originalRank==1)){
                logger.info("Pass");
            }else {Assert.fail("rankNumNotUpdate");}
        }else {Assert.fail("totalNumNotUpdate");}
    }
    @Test
    /*case 15
    用例编号：SI-2287:设置为固定码率（码率上下限相等）
     1.随机设置一个固定码率；及前后码率值设置为一样；
     */
    public void testCheckGalleryFixedBitrate() throws Exception {
        GalleryAction.navToGalleryBitrateSetting();
        String inputNum = GalleryAction.getRandomBitrate(GalleryAction.getGalleryBitrateMin(),
                GalleryAction.getGalleryBitrateMax());
        setText(GalleryPage.gallery_bitrate_custom_min,inputNum);
        setText(GalleryPage.gallery_bitrate_custom_max,inputNum);
        clickById(GalleryPage.gallery_ok);
        if (gDevice.findObject(new UiSelector().text("Skip")).exists()){
            Assert.fail("FixedBitrateSettingFailed");
        }
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        clickByText("Yes");
    }
    @Test
    /*case 16
    用例编号：SI-2286:检查码率边界值
    1设置比最小码率更小的值  ； 2.设置比最大码率更大的值 3.设置允许的范围值
     */
    public void testCheckGalleryBitrateBoundaryValue() throws Exception {
        GalleryAction.navToGalleryBitrateSetting();
        String small = GalleryAction.getRandomBitrate(0,GalleryAction.getGalleryBitrateMin());
        String bigger= GalleryAction.getRandomBitrate(GalleryAction.getGalleryBitrateMax(),99999999);
        setText(GalleryPage.gallery_bitrate_custom_min,small);
        if (!gDevice.findObject(new UiSelector().resourceId(GalleryPage.gallery_bitrate_error_tips)).exists()){
            Assert.fail("notFindGalleryBitrateErrorTips");
        }
        setText(GalleryPage.gallery_bitrate_custom_min,GalleryAction.getRandomBitrate
                (GalleryAction.getGalleryBitrateMin(), GalleryAction.getGalleryBitrateMax()));
        if (gDevice.findObject(new UiSelector().resourceId(GalleryPage.gallery_bitrate_error_tips)).exists()){
            Assert.fail("FindGalleryBitrateErrorTips");
        }
        setText(GalleryPage.gallery_bitrate_custom_max,bigger);
        if (!gDevice.findObject(new UiSelector().resourceId(GalleryPage.gallery_bitrate_error_tips)).exists()){
            Assert.fail("notFindGalleryBitrateErrorTips");
        }
        setText(GalleryPage.gallery_bitrate_custom_max,GalleryAction.getRandomBitrate
                (GalleryAction.getGalleryBitrateMin(), GalleryAction.getGalleryBitrateMax()));
        if (gDevice.findObject(new UiSelector().resourceId(GalleryPage.gallery_bitrate_error_tips)).exists()){
            Assert.fail("FindGalleryBitrateErrorTips");
        }
    }
    @Test
    /* case 17
    case编号：SI-1870:直播中亮灭屏   相册直播中亮灭屏， 亮灭屏同相机熄屏设置一致  5次
     */
    public void testCheckGalleryLiveWithScreenOffOn() throws Exception {
        Iris4GAction.startGallery();
        while (!GalleryAction.checkLiveBottom()) {
            getObjectById(GalleryPage.gallery_root_view).swipeLeft(60);}
        GalleryAction.startGalleryLive();
        for (int i=0;i<10;i++){
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            waitTime(1);
        }
        waitTime(2);
        if (gDevice.isScreenOn()){GalleryAction.stopGalleryLive();
        }else {
            Assert.fail("ScreenIsOff");
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);//亮屏
            GalleryAction.stopGalleryLive();
        }
    }
    /* case 18
     case编号：SI-1883:未登录账号      1录制一段720@25、或者480@25的普通视频 2点击直播button  3 登录账号  4 正常跳转
到剪辑直播和立即直播界面
     */
    /*case 19
    case编号：SI-2006:延时视频剪辑后直播  1延时视频剪辑后发起直播
     */
}