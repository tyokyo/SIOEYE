package iris4G.testcase;

import android.os.RemoteException;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.hamcrest.Asst;
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
 * @Author yun.yang
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class VideoSettings extends VP2{
    private static Logger logger = Logger.getLogger(VideoSettings.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    /*1：Capture  - capture设置为4M(16:9)
    * 2：Lapse -延时设置为2s
    * 2：Video -视频质量设置为720@60FPS
    * 4：Video -视频角度设置Wide
    * 5：各种模式之间切换
    * 6：验证设置的参数
    * */
    @Test
    public void testValueSettings() throws Exception {
        //video_quality 720@60FPS
        String quality = NavPage.quality720_60;
        //video_Angle Wide
        String angle = NavPage.angleWide;
        //lapse_time 2s
        String lapse_time = NavPage.lapseTime_2s;
        //imsge_size 4M(16:9)
        String image_size = NavPage.imageSize4M;

        //capture设置为4M(16:9)
        CameraAction.configImageSize(NavPage.navConfig_Capture,image_size);
        //延时设置为2s
        CameraAction.configTimeLapse(NavPage.navConfig_Lapse,lapse_time);
        //视频质量设置为720@60FPS
        CameraAction.configVideoQuality(NavPage.navConfig_Video,quality);
        //视频角度设置Wide
        CameraAction.configVideoAngle(NavPage.navConfig_Video,angle);

        //切换到延时录像再切换到普通录像
        CameraAction.navConfig(NavPage.navConfig_LiveStream);
        CameraAction.navConfig(NavPage.navConfig_Video);
        CameraAction.navConfig(NavPage.navConfig_Burst);
        CameraAction.navConfig(NavPage.navConfig_Capture);
        CameraAction.navConfig(NavPage.navConfig_Slo_Mo);

        //验证参数设置-capture
        CameraAction.checkImageSize(NavPage.navConfig_Capture,image_size);
        //验证参数设置-video quality
        CameraAction.checkVideoQuality(NavPage.navConfig_Video,quality);
        //验证参数设置-video angle
        CameraAction.checkVideoAngle(NavPage.navConfig_Video,angle);
        //验证参数设置-lapse
        CameraAction.checkTimeLapse(NavPage.navConfig_Lapse,lapse_time);
    }
}
