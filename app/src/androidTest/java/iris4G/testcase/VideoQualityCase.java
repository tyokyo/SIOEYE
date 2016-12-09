package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;

/**
 * @Author elon
 * @Description
 * 给个模式的video quality 参数验证
 * "720@30FPS",
   "720@60FPS",
    "1080@30FPS"
 * 模式切换
 * 验证参数修改成功
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class VideoQualityCase extends VP2{
    private String navConfig_LiveStream=Iris4GPage.nav_menu[0];
    private String navConfig_Video=Iris4GPage.nav_menu[1];
    private String navConfig_Capture=Iris4GPage.nav_menu[2];
    private String navConfig_Burst=Iris4GPage.nav_menu[3];
    private String navConfig_Slo_Mo=Iris4GPage.nav_menu[4];
    private String navConfig_Lapse=Iris4GPage.nav_menu[5];


    Logger logger = Logger.getLogger(VideoQualityCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void test720_30() throws Exception {
        //Video - 修改视频质量 1080@30FPS
        CameraAction.configVideoQuality(navConfig_Video,Iris4GPage.video_quality[0]);
        //Lapse - 修改视频质量 720@60FPS
        CameraAction.configVideoQuality(navConfig_Lapse,Iris4GPage.video_quality[1]);

        //切换到Burst
        CameraAction.navConfig(navConfig_Burst);
        //切换到Lapse
        CameraAction.navConfig(navConfig_Lapse);
        //切换到Capture
        CameraAction.navConfig(navConfig_Capture);
        //切换到LiveStream
        CameraAction.navConfig(navConfig_LiveStream);
        //切换到Slo_Mo
        CameraAction.navConfig(navConfig_Slo_Mo);
        //切换到Video
        CameraAction.navConfig(navConfig_Video);

        //重启camera
        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        //验证LiveStream 的视频角度
        CameraAction.checkVideoAngle(navConfig_Video,Iris4GPage.video_Angle[0]);
        CameraAction.checkVideoAngle(navConfig_Lapse,Iris4GPage.video_Angle[1]);
    }
    @Test
    public void test720_60() throws Exception {
        //Video - 修改视频质量 1080@30FPS
        CameraAction.configVideoQuality(navConfig_Video,Iris4GPage.video_quality[1]);
        //Lapse - 修改视频质量 720@60FPS
        CameraAction.configVideoQuality(navConfig_Lapse,Iris4GPage.video_quality[2]);

        //切换到Burst
        CameraAction.navConfig(navConfig_Burst);
        //切换到Lapse
        CameraAction.navConfig(navConfig_Lapse);
        //切换到Capture
        CameraAction.navConfig(navConfig_Capture);
        //切换到LiveStream
        CameraAction.navConfig(navConfig_LiveStream);
        //切换到Slo_Mo
        CameraAction.navConfig(navConfig_Slo_Mo);
        //切换到Video
        CameraAction.navConfig(navConfig_Video);

        //重启camera
        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        //验证LiveStream 的视频角度
        CameraAction.checkVideoAngle(navConfig_Video,Iris4GPage.video_Angle[1]);
        CameraAction.checkVideoAngle(navConfig_Lapse,Iris4GPage.video_Angle[2]);
    }
    @Test
    public void test1080_30() throws Exception {
        //Video - 修改视频质量 1080@30FPS
        CameraAction.configVideoQuality(navConfig_Video,Iris4GPage.video_quality[2]);
        //Lapse - 修改视频质量 720@60FPS
        CameraAction.configVideoQuality(navConfig_Lapse,Iris4GPage.video_quality[1]);

        //切换到Burst
        CameraAction.navConfig(navConfig_Burst);
        //切换到Lapse
        CameraAction.navConfig(navConfig_Lapse);
        //切换到Capture
        CameraAction.navConfig(navConfig_Capture);
        //切换到LiveStream
        CameraAction.navConfig(navConfig_LiveStream);
        //切换到Slo_Mo
        CameraAction.navConfig(navConfig_Slo_Mo);
        //切换到Video
        CameraAction.navConfig(navConfig_Video);

        //重启camera
        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        //验证LiveStream 的视频角度
        CameraAction.checkVideoAngle(navConfig_Video,Iris4GPage.video_Angle[2]);
        CameraAction.checkVideoAngle(navConfig_Lapse,Iris4GPage.video_Angle[1]);
    }
}
