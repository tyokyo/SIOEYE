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
import iris4G.page.NavPage;

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
    Logger logger = Logger.getLogger(ImageSizeCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void testCapture4M() throws Exception {
        //Capture - 修改ImageSize 4M
        CameraAction.configImageSize(NavPage.navConfig_Capture,NavPage.imageSize4M);
        //Lapse - 修改ImageSize 3M
        CameraAction.configImageSize(NavPage.navConfig_Burst,NavPage.imageSize3M);
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

        //验证Capture - 修改ImageSize 4M
        CameraAction.checkImageSize(NavPage.navConfig_Capture,NavPage.imageSize4M);
        //验证Lapse - 修改ImageSize 3M
        CameraAction.checkImageSize(NavPage.navConfig_Burst,NavPage.imageSize3M);
    }
    @Test
    public void testCapture3M() throws Exception {
        //Capture - 修改ImageSize 3M
        CameraAction.configImageSize(NavPage.navConfig_Capture,NavPage.imageSize3M);
        //Burst - 修改ImageSize 2M
        CameraAction.configImageSize(NavPage.navConfig_Burst,NavPage.imageSize2M);
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

        //验证Capture - 修改ImageSize 3M
        CameraAction.checkImageSize(NavPage.navConfig_Capture,NavPage.imageSize3M);
        //验证Burst - 修改ImageSize 2M
        CameraAction.checkImageSize(NavPage.navConfig_Burst,NavPage.imageSize2M);
    }
    @Test
    public void testCapture2M() throws Exception {
        //Capture - 修改ImageSize 2M
        CameraAction.configImageSize(NavPage.navConfig_Capture,NavPage.imageSize2M);
        //Lapse - 修改ImageSize 8M
        CameraAction.configImageSize(NavPage.navConfig_Burst,NavPage.imageSize8M);
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

        //验证Capture - 修改ImageSize 2M
        CameraAction.checkImageSize(NavPage.navConfig_Capture,NavPage.imageSize2M);
        //验证Lapse - 修改ImageSize 8M
        CameraAction.checkImageSize(NavPage.navConfig_Burst,NavPage.imageSize8M);
    }

    @Test
    public void testCapture8M() throws Exception {
        //Capture - 修改ImageSize 8M
        CameraAction.configImageSize(NavPage.navConfig_Capture,NavPage.imageSize8M);
        //Lapse - 修改ImageSize 4M
        CameraAction.configImageSize(NavPage.navConfig_Burst,NavPage.imageSize2M);
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

        //验证Capture - 修改ImageSize 8M
        CameraAction.checkImageSize(NavPage.navConfig_Capture,NavPage.imageSize8M);
        //验证Lapse - 修改ImageSize 4M
        CameraAction.checkImageSize(NavPage.navConfig_Burst,NavPage.imageSize2M);
    }
}
