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

/**
 * @Author yun.yang
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class VideoSettings extends VP2{
    private String navConfig_LiveStream=Iris4GPage.nav_menu[0];
    private String navConfig_Video=Iris4GPage.nav_menu[1];
    private String navConfig_Capture=Iris4GPage.nav_menu[2];
    private String navConfig_Burst=Iris4GPage.nav_menu[3];
    private String navConfig_Slo_Mo=Iris4GPage.nav_menu[4];
    private String navConfig_Lapse=Iris4GPage.nav_menu[5];
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
        String quality = Iris4GPage.video_quality[2];
        //video_Angle Wide
        String angle = Iris4GPage.video_Angle[1];
        //lapse_time 2s
        String lapse_time = Iris4GPage.lapse_time[0];
        //imsge_size 4M(16:9)
        String image_size = Iris4GPage.imsge_size[0];

        //capture设置为4M(16:9)
        CameraAction.configImageSize(navConfig_Capture,image_size);
        //延时设置为2s
        CameraAction.configTimeLapse(navConfig_Lapse,lapse_time);
        //视频质量设置为720@60FPS
        CameraAction.configVideoQuality(navConfig_Video,quality);
        //视频角度设置Wide
        CameraAction.configVideoAngle(navConfig_Video,angle);

        //切换到延时录像再切换到普通录像
        CameraAction.navConfig(navConfig_LiveStream);
        CameraAction.navConfig(navConfig_Video);
        CameraAction.navConfig(navConfig_Burst);
        CameraAction.navConfig(navConfig_Capture);
        CameraAction.navConfig(navConfig_Slo_Mo);

        //验证参数设置-capture
        CameraAction.checkImageSize(navConfig_Capture,image_size);
        //验证参数设置-video quality
        CameraAction.checkVideoQuality(navConfig_Video,quality);
        //验证参数设置-video angle
        CameraAction.checkVideoAngle(navConfig_Video,angle);
        //验证参数设置-lapse
        CameraAction.checkTimeLapse(navConfig_Lapse,lapse_time);
    }
}
