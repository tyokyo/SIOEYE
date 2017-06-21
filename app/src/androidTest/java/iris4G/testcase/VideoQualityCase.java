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
    Logger logger = Logger.getLogger(VideoQualityCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void test720_25() throws Exception {
        //Video - 修改视频质量 720@30FPS
        CameraAction.configVideoQuality(NavPage.navConfig_Video,NavPage.quality720_25);
     /*  延时录像去掉了720@60FPS
       //Lapse - 修改视频质量 720@60FPS
        CameraAction.configVideoQuality(NavPage.navConfig_Lapse,NavPage.quality720_60);*/

       //Lapse - 修改视频质量 480@30FPS
        CameraAction.configVideoQuality(NavPage.navConfig_Lapse,NavPage.quality480_30);


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
        //验证Video视频质量 720@30FPS
        CameraAction.checkVideoQuality(NavPage.navConfig_Video,NavPage.quality720_25);
     /*   //验证Lapse视频质量 720@60FPS
        CameraAction.checkVideoQuality(NavPage.navConfig_Lapse,NavPage.quality720_60);*/

        //验证Lapse视频质量 480@30FPS
        CameraAction.checkVideoQuality(NavPage.navConfig_Lapse,NavPage.quality480_30);

    }
    @Test
    public void test720_60() throws Exception {
        //Video - 修改视频质量 720@60FPS
        CameraAction.configVideoQuality(NavPage.navConfig_Video,NavPage.quality720_60);
        //Lapse - 修改视频质量 1080@30FPS
        CameraAction.configVideoQuality(NavPage.navConfig_Lapse,NavPage.quality1080_30);

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
        //验证Lapse视频质量 720@60FPS
        CameraAction.checkVideoQuality(NavPage.navConfig_Video,NavPage.quality720_60);
        //验证Lapse视频质量 1080@30FPS
        CameraAction.checkVideoQuality(NavPage.navConfig_Lapse,NavPage.quality1080_30);
    }
    @Test
    public void test1080_25() throws Exception {
        //Video - 修改视频质量 1080@30FPS
        CameraAction.configVideoQuality(NavPage.navConfig_Video,NavPage.quality1080_25);
        //Lapse - 修改视频质量 1080@30FPS
        CameraAction.configVideoQuality(NavPage.navConfig_Lapse,NavPage.quality1080_30);

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
        //验证Lapse视频质量 1080@30FPS
        CameraAction.checkVideoQuality(NavPage.navConfig_Video,NavPage.quality1080_25);
        //验证Lapse视频质量 1080@30FPS
        CameraAction.checkVideoQuality(NavPage.navConfig_Lapse,NavPage.quality1080_30);
    }
    @Test
    public void test480_120() throws Exception {
        //Video - 修改视频质量 480@120FPS
        CameraAction.configVideoQuality(NavPage.navConfig_Video,NavPage.quality480_120);
        //Lapse - 修改视频质量 720@30FPS
        CameraAction.configVideoQuality(NavPage.navConfig_Lapse,NavPage.quality720_30);

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
        //验证Lapse视频质量 480@120FPS
        CameraAction.checkVideoQuality(NavPage.navConfig_Video,NavPage.quality480_120);
        //验证Lapse视频质量 720@30FPS
        CameraAction.checkVideoQuality(NavPage.navConfig_Lapse,NavPage.quality720_30);
    }
}
