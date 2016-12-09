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
import iris4G.page.NavPage;

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
    Logger logger = Logger.getLogger(VideoAngleCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void testLiveStreamSuperWide() throws Exception {
        //LiveStream - 修改视频角度Wide
        CameraAction.configVideoAngle(NavPage.navConfig_LiveStream,NavPage.angleSuperWide);
        //切换到Burst
        CameraAction.navConfig(NavPage.navConfig_Burst);
        //切换到Lapse
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        //切换到Capture
        CameraAction.navConfig(NavPage.navConfig_Capture);
        //切换到LiveStream
        CameraAction.navConfig(NavPage.navConfig_LiveStream);
        //切换到Slo_Mo
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);
        //切换到Video
        CameraAction.navConfig(NavPage.navConfig_Video);

        //重启camera
        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        //验证LiveStream 的视频角度
        CameraAction.checkVideoAngle(NavPage.navConfig_LiveStream,NavPage.angleSuperWide);
    }
    @Test
    public void testLiveStreamWide() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_LiveStream,NavPage.angleWide);

        CameraAction.navConfig(NavPage.navConfig_Burst);
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        CameraAction.navConfig(NavPage.navConfig_Capture);
        CameraAction.navConfig(NavPage.navConfig_LiveStream);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);
        CameraAction.navConfig(NavPage.navConfig_Video);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(NavPage.navConfig_LiveStream,NavPage.angleWide);
    }
    @Test
    public void testLiveStreamSuperMedium() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_LiveStream,NavPage.angleMedium);

        CameraAction.navConfig(NavPage.navConfig_Burst);
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        CameraAction.navConfig(NavPage.navConfig_Capture);
        CameraAction.navConfig(NavPage.navConfig_LiveStream);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);
        CameraAction.navConfig(NavPage.navConfig_Video);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(NavPage.navConfig_LiveStream,NavPage.angleMedium);
    }

    @Test
    public void testVideoSuperWide() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Video,NavPage.angleSuperWide);

        CameraAction.navConfig(NavPage.navConfig_Burst);
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        CameraAction.navConfig(NavPage.navConfig_Capture);
        CameraAction.navConfig(NavPage.navConfig_Video);
        CameraAction.navConfig(NavPage.navConfig_LiveStream);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(NavPage.navConfig_Video,NavPage.angleSuperWide);
    }
    @Test
    public void testVideoWide() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Video,NavPage.angleWide);

        CameraAction.navConfig(NavPage.navConfig_Burst);
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        CameraAction.navConfig(NavPage.navConfig_Capture);
        CameraAction.navConfig(NavPage.navConfig_Video);
        CameraAction.navConfig(NavPage.navConfig_LiveStream);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(NavPage.navConfig_Video,NavPage.angleWide);
    }
    @Test
    public void testVideoMedium() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Video,NavPage.angleMedium);

        CameraAction.navConfig(NavPage.navConfig_Burst);
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        CameraAction.navConfig(NavPage.navConfig_Capture);
        CameraAction.navConfig(NavPage.navConfig_Video);
        CameraAction.navConfig(NavPage.navConfig_LiveStream);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(NavPage.navConfig_Video,NavPage.angleMedium);
    }

    @Test
    public void testSloMoSuperWide() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Slo_Mo,NavPage.angleSuperWide);

        CameraAction.navConfig(NavPage.navConfig_Burst);
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        CameraAction.navConfig(NavPage.navConfig_Capture);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);
        CameraAction.navConfig(NavPage.navConfig_Video);
        CameraAction.navConfig(NavPage.navConfig_LiveStream);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(NavPage.navConfig_Slo_Mo,NavPage.angleSuperWide);
    }
    @Test
    public void testSloMoWide() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Slo_Mo,NavPage.angleWide);

        CameraAction.navConfig(NavPage.navConfig_Burst);
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        CameraAction.navConfig(NavPage.navConfig_Capture);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);
        CameraAction.navConfig(NavPage.navConfig_Video);
        CameraAction.navConfig(NavPage.navConfig_LiveStream);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(NavPage.navConfig_Slo_Mo,NavPage.angleWide);
    }
    @Test
    public void testSloMoMedium() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Slo_Mo,NavPage.angleMedium);

        CameraAction.navConfig(NavPage.navConfig_Burst);
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        CameraAction.navConfig(NavPage.navConfig_Capture);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);
        CameraAction.navConfig(NavPage.navConfig_Video);
        CameraAction.navConfig(NavPage.navConfig_LiveStream);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(NavPage.navConfig_Slo_Mo,NavPage.angleMedium);
    }

    @Test
    public void testLapseSuperWide() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Lapse,NavPage.angleSuperWide);

        CameraAction.navConfig(NavPage.navConfig_Burst);
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        CameraAction.navConfig(NavPage.navConfig_Capture);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);
        CameraAction.navConfig(NavPage.navConfig_Video);
        CameraAction.navConfig(NavPage.navConfig_LiveStream);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(NavPage.navConfig_Lapse,NavPage.angleSuperWide);
    }
    @Test
    public void testLapseWide() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Lapse,NavPage.angleWide);

        CameraAction.navConfig(NavPage.navConfig_Burst);
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        CameraAction.navConfig(NavPage.navConfig_Capture);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);
        CameraAction.navConfig(NavPage.navConfig_Video);
        CameraAction.navConfig(NavPage.navConfig_LiveStream);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(NavPage.navConfig_Lapse,NavPage.angleWide);
    }
    @Test
    public void testLapseMedium() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Lapse,NavPage.angleMedium);

        CameraAction.navConfig(NavPage.navConfig_Burst);
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        CameraAction.navConfig(NavPage.navConfig_Capture);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);
        CameraAction.navConfig(NavPage.navConfig_Video);
        CameraAction.navConfig(NavPage.navConfig_LiveStream);

        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(NavPage.navConfig_Lapse,NavPage.angleMedium);
    }
    @Test
    public void testSetVideoAngleSuperWide() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Video,NavPage.angleSuperWide);
        Iris4GAction.cameraKey();
        waitTime(5);
        Iris4GAction.cameraKey();
        CameraAction.checkVideoAngle(NavPage.navConfig_Video,NavPage.angleSuperWide);
    }
    @Test
    public void testSetVideoAngleWide() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Video,NavPage.angleWide);
        Iris4GAction.cameraKey();
        waitTime(5);
        Iris4GAction.cameraKey();
        CameraAction.checkVideoAngle(NavPage.navConfig_Video,NavPage.angleWide);
    }
    @Test
    public void testSetVideoAngleMedium() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Video,NavPage.angleMedium);
        Iris4GAction.cameraKey();
        waitTime(5);
        Iris4GAction.cameraKey();
        CameraAction.checkVideoAngle(NavPage.navConfig_Video,NavPage.angleMedium);
    }
    @Test
    public void testChangeOtherSettingReturnToVideo() throws Exception {
        CameraAction.configVideoAngle(NavPage.navConfig_Video,NavPage.quality720_60);
        CameraAction.configVideoAngle(NavPage.navConfig_Video,NavPage.angleWide);
        CameraAction.cameraSetting();
        //Change Auto
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        CameraAction.navConfig(NavPage.navConfig_Video);
        CameraAction.checkVideoAngle(NavPage.navConfig_Video,NavPage.quality720_60);
        CameraAction.checkVideoAngle(NavPage.navConfig_Video,NavPage.angleWide);
    }
}

