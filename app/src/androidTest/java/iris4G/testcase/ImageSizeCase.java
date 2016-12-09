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
    "4M(16:9)",
    "3M(4:3)",
    "2M(16:9)",
    "8M((4:3))"};
 * 修改Video LiveStream Lapse Slo_Mo模式下的Image size
 * 模式切换
 * 验证参数修改成功
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class ImageSizeCase extends VP2{
    private String navConfig_LiveStream=Iris4GPage.nav_menu[0];
    private String navConfig_Video=Iris4GPage.nav_menu[1];
    private String navConfig_Capture=Iris4GPage.nav_menu[2];
    private String navConfig_Burst=Iris4GPage.nav_menu[3];
    private String navConfig_Slo_Mo=Iris4GPage.nav_menu[4];
    private String navConfig_Lapse=Iris4GPage.nav_menu[5];

    Logger logger = Logger.getLogger(ImageSizeCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void testCapture4M() throws Exception {
        //Capture - 修改ImageSize 4M
        CameraAction.configImageSize(navConfig_Capture,Iris4GPage.imsge_size[0]);
        //Lapse - 修改ImageSize 3M
        CameraAction.configImageSize(navConfig_Burst,Iris4GPage.imsge_size[1]);
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

        //验证Capture - 修改ImageSize 4M
        CameraAction.configImageSize(navConfig_Capture,Iris4GPage.imsge_size[0]);
        //验证Lapse - 修改ImageSize 3M
        CameraAction.configImageSize(navConfig_Burst,Iris4GPage.imsge_size[1]);
    }
    @Test
    public void testCapture3M() throws Exception {
        //Capture - 修改ImageSize 3M
        CameraAction.configImageSize(navConfig_Capture,Iris4GPage.imsge_size[1]);
        //Lapse - 修改ImageSize 4M
        CameraAction.configImageSize(navConfig_Burst,Iris4GPage.imsge_size[2]);
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

        //验证Capture - 修改ImageSize 3M
        CameraAction.configImageSize(navConfig_Capture,Iris4GPage.imsge_size[1]);
        //验证Lapse - 修改ImageSize 4M
        CameraAction.configImageSize(navConfig_Burst,Iris4GPage.imsge_size[2]);
    }
    @Test
    public void testCapture2M() throws Exception {
        //Capture - 修改ImageSize 2M
        CameraAction.configImageSize(navConfig_Capture,Iris4GPage.imsge_size[2]);
        //Lapse - 修改ImageSize 8M
        CameraAction.configImageSize(navConfig_Burst,Iris4GPage.imsge_size[3]);
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

        //验证Capture - 修改ImageSize 2M
        CameraAction.configImageSize(navConfig_Capture,Iris4GPage.imsge_size[2]);
        //验证Lapse - 修改ImageSize 8M
        CameraAction.configImageSize(navConfig_Burst,Iris4GPage.imsge_size[3]);
    }

    @Test
    public void testCapture8M() throws Exception {
        //Capture - 修改ImageSize 8M
        CameraAction.configImageSize(navConfig_Capture,Iris4GPage.imsge_size[3]);
        //Lapse - 修改ImageSize 4M
        CameraAction.configImageSize(navConfig_Burst,Iris4GPage.imsge_size[0]);
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

        //验证Capture - 修改ImageSize 8M
        CameraAction.configImageSize(navConfig_Capture,Iris4GPage.imsge_size[3]);
        //验证Lapse - 修改ImageSize 4M
        CameraAction.configImageSize(navConfig_Burst,Iris4GPage.imsge_size[0]);
    }
}
