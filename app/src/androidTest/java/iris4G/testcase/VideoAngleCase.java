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
 * 1.设置为wide
 * 2.进入Video Angle设置界面查看
 * 1.设置一级界面video Angle显示为wide;
 * 2.Video Angle设置界面，wide为勾选状态
 * 修改Video LiveStream Lapse Slo_Mo模式下的视频角度
 * 模式切换
 * 验证参数修改成功
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class VideoAngleCase extends VP2{
    private String navConfig_LiveStream=Iris4GPage.nav_menu[0];
    private String navConfig_Video=Iris4GPage.nav_menu[1];
    private String navConfig_Capture=Iris4GPage.nav_menu[2];
    private String navConfig_Burst=Iris4GPage.nav_menu[3];
    private String navConfig_Slo_Mo=Iris4GPage.nav_menu[4];
    private String navConfig_Lapse=Iris4GPage.nav_menu[5];

    Logger logger = Logger.getLogger(LiveUpDown.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void testLiveStreamSuperWide() throws Exception {
        //LiveStream - 修改视频角度Wide
        CameraAction.configVideoAngle(navConfig_LiveStream,Iris4GPage.video_Angle[0]);
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
        CameraAction.checkVideoAngle(navConfig_LiveStream,Iris4GPage.video_Angle[0]);
    }
    @Test
    public void testLiveStreamWide() throws Exception {
        CameraAction.configVideoAngle(navConfig_LiveStream,Iris4GPage.video_Angle[1]);

        CameraAction.navConfig(navConfig_Burst);
        CameraAction.navConfig(navConfig_Lapse);
        CameraAction.navConfig(navConfig_Capture);
        CameraAction.navConfig(navConfig_LiveStream);
        CameraAction.navConfig(navConfig_Slo_Mo);
        CameraAction.navConfig(navConfig_Video);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(navConfig_LiveStream,Iris4GPage.video_Angle[1]);
    }
    @Test
    public void testLiveStreamSuperMedium() throws Exception {
        CameraAction.configVideoAngle(navConfig_LiveStream,Iris4GPage.video_Angle[2]);

        CameraAction.navConfig(navConfig_Burst);
        CameraAction.navConfig(navConfig_Lapse);
        CameraAction.navConfig(navConfig_Capture);
        CameraAction.navConfig(navConfig_LiveStream);
        CameraAction.navConfig(navConfig_Slo_Mo);
        CameraAction.navConfig(navConfig_Video);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(navConfig_LiveStream,Iris4GPage.video_Angle[2]);
    }

    @Test
    public void testVideoSuperWide() throws Exception {
        CameraAction.configVideoAngle(navConfig_Video,Iris4GPage.video_Angle[0]);

        CameraAction.navConfig(navConfig_Burst);
        CameraAction.navConfig(navConfig_Lapse);
        CameraAction.navConfig(navConfig_Capture);
        CameraAction.navConfig(navConfig_Video);
        CameraAction.navConfig(navConfig_LiveStream);
        CameraAction.navConfig(navConfig_Slo_Mo);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(navConfig_Video,Iris4GPage.video_Angle[0]);
    }
    @Test
    public void testVideoWide() throws Exception {
        CameraAction.configVideoAngle(navConfig_Video,Iris4GPage.video_Angle[1]);

        CameraAction.navConfig(navConfig_Burst);
        CameraAction.navConfig(navConfig_Lapse);
        CameraAction.navConfig(navConfig_Capture);
        CameraAction.navConfig(navConfig_Video);
        CameraAction.navConfig(navConfig_LiveStream);
        CameraAction.navConfig(navConfig_Slo_Mo);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(navConfig_Video,Iris4GPage.video_Angle[1]);
    }
    @Test
    public void testVideoMedium() throws Exception {
        CameraAction.configVideoAngle(navConfig_Video,Iris4GPage.video_Angle[2]);

        CameraAction.navConfig(navConfig_Burst);
        CameraAction.navConfig(navConfig_Lapse);
        CameraAction.navConfig(navConfig_Capture);
        CameraAction.navConfig(navConfig_Video);
        CameraAction.navConfig(navConfig_LiveStream);
        CameraAction.navConfig(navConfig_Slo_Mo);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(navConfig_Video,Iris4GPage.video_Angle[2]);
    }

    @Test
    public void testSloMoSuperWide() throws Exception {
        CameraAction.configVideoAngle(navConfig_Slo_Mo,Iris4GPage.video_Angle[0]);

        CameraAction.navConfig(navConfig_Burst);
        CameraAction.navConfig(navConfig_Lapse);
        CameraAction.navConfig(navConfig_Capture);
        CameraAction.navConfig(navConfig_Slo_Mo);
        CameraAction.navConfig(navConfig_Video);
        CameraAction.navConfig(navConfig_LiveStream);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(navConfig_Slo_Mo,Iris4GPage.video_Angle[0]);
    }
    @Test
    public void testSloMoWide() throws Exception {
        CameraAction.configVideoAngle(navConfig_Slo_Mo,Iris4GPage.video_Angle[1]);

        CameraAction.navConfig(navConfig_Burst);
        CameraAction.navConfig(navConfig_Lapse);
        CameraAction.navConfig(navConfig_Capture);
        CameraAction.navConfig(navConfig_Slo_Mo);
        CameraAction.navConfig(navConfig_Video);
        CameraAction.navConfig(navConfig_LiveStream);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(navConfig_Slo_Mo,Iris4GPage.video_Angle[1]);
    }
    @Test
    public void testSloMoMedium() throws Exception {
        CameraAction.configVideoAngle(navConfig_Slo_Mo,Iris4GPage.video_Angle[2]);

        CameraAction.navConfig(navConfig_Burst);
        CameraAction.navConfig(navConfig_Lapse);
        CameraAction.navConfig(navConfig_Capture);
        CameraAction.navConfig(navConfig_Slo_Mo);
        CameraAction.navConfig(navConfig_Video);
        CameraAction.navConfig(navConfig_LiveStream);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(navConfig_Slo_Mo,Iris4GPage.video_Angle[2]);
    }

    @Test
    public void testLapseSuperWide() throws Exception {
        CameraAction.configVideoAngle(navConfig_Lapse,Iris4GPage.video_Angle[0]);

        CameraAction.navConfig(navConfig_Burst);
        CameraAction.navConfig(navConfig_Lapse);
        CameraAction.navConfig(navConfig_Capture);
        CameraAction.navConfig(navConfig_Slo_Mo);
        CameraAction.navConfig(navConfig_Video);
        CameraAction.navConfig(navConfig_LiveStream);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(navConfig_Lapse,Iris4GPage.video_Angle[0]);
    }
    @Test
    public void testLapseWide() throws Exception {
        CameraAction.configVideoAngle(navConfig_Lapse,Iris4GPage.video_Angle[1]);

        CameraAction.navConfig(navConfig_Burst);
        CameraAction.navConfig(navConfig_Lapse);
        CameraAction.navConfig(navConfig_Capture);
        CameraAction.navConfig(navConfig_Slo_Mo);
        CameraAction.navConfig(navConfig_Video);
        CameraAction.navConfig(navConfig_LiveStream);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(navConfig_Lapse,Iris4GPage.video_Angle[1]);
    }
    @Test
    public void testLapseMedium() throws Exception {
        CameraAction.configVideoAngle(navConfig_Lapse,Iris4GPage.video_Angle[2]);

        CameraAction.navConfig(navConfig_Burst);
        CameraAction.navConfig(navConfig_Lapse);
        CameraAction.navConfig(navConfig_Capture);
        CameraAction.navConfig(navConfig_Slo_Mo);
        CameraAction.navConfig(navConfig_Video);
        CameraAction.navConfig(navConfig_LiveStream);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(navConfig_Lapse,Iris4GPage.video_Angle[2]);
    }

}
