package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

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
import iris4G.page.Iris4GPage;
import iris4G.page.NavPage;

/**
 * @Author elon
 * @Description 所有视频质量[72030|72060|108030] * Video Angle
 * 1.更改视频质量参数为480@120FPS
   2.录制设置过参数的普通录像视频
   1.可以成功设置为对应的参数
   2.可以成功录制
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class VideoCase extends VP2{
    Logger logger = Logger.getLogger(VideoCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    private void Video(String quality,String angle) throws Exception {
        //String quality = Iris4GPage.video_quality[0];
        //String angle =Iris4GPage.video_Angle[0];
        CameraAction.configVideoQuality(NavPage.navConfig_Video,quality);
        CameraAction.configVideoAngle(NavPage.navConfig_Video,angle);
        CameraAction.cameraVideo();

        //更改成功，取景界面左上角显示修改后的视频质量
        String screen_display=CameraAction.replaceFps(quality);
        Asst.assertEquals(quality,screen_display,getTex(Iris4GPage.info));

        HashSet<String> beforeTakeVideoList = Iris4GAction.FileList("/sdcard/video");
        Iris4GAction.cameraKey();
        CameraAction.cameraRecordTime();
        waitTime(10);
        CameraAction.cameraRecordTime();
        Iris4GAction.cameraKey();
        waitTime(5);
        CameraAction.openPlayVideoAndCheck(quality,beforeTakeVideoList);
    }
    @Test
    public void testV72030fpsSuperWide() throws Exception {
        Video(NavPage.quality720_30,NavPage.angleSuperWide);
    }
    @Test
    public void testV72060fpsSuperWide() throws Exception {
        Video(NavPage.quality720_60,NavPage.angleSuperWide);
    }
    @Test
    public void testV108030fpsSuperWide() throws Exception {
        Video(NavPage.quality1080_30,NavPage.angleSuperWide);
    }
    @Test
    public void testV72030fpsWide() throws Exception {
        Video(NavPage.quality720_30,NavPage.angleWide);
    }
    @Test
    public void testV72060fpsWide() throws Exception {
        Video(NavPage.quality720_60,NavPage.angleWide);
    }
    @Test
    public void testV108030fpsWide() throws Exception {
        Video(NavPage.quality1080_30,NavPage.angleWide);
    }
    @Test
    public void testV72030fpsMedium() throws Exception {
        Video(NavPage.quality720_30,NavPage.angleMedium);
    }
    @Test
    public void testV72060fpsMedium() throws Exception {
        Video(NavPage.quality720_60,NavPage.angleMedium);
    }
    @Test
    public void testV108030fpsMedium() throws Exception {
        Video(NavPage.quality1080_30,NavPage.angleMedium);
    }

}
