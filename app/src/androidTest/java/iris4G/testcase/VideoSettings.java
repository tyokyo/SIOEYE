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
 * @Description
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class VideoSettings extends VP2{
    private static Logger logger = Logger.getLogger(VideoSettings.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void test() throws Exception {
        String quality = Iris4GPage.video_quality[4];
        String angle = Iris4GPage.video_Angle[1];
        int flag1 = 0;
        int flag2 = 0;

        CameraAction.configVideoQuality(quality);
        CameraAction.configVideoAngle(angle);
        //将Up改为Auto
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Up/Down/Auto");
        clickByText("Auto");
        gDevice.pressBack();
        //切换到延时录像再切换到普通录像
        CameraAction.navConfig(Iris4GPage.nav_menu[5]);
        if (text_exists("3s")) {
            //通过检查当前页面是否有3s存在，若存在，则正确，并记录flag1=1即true
            flag1 = 1;
        } else {
            flag1 = 0;
            Asst.fail("SLO_MO record fail");
        }
        CameraAction.navConfig(Iris4GPage.nav_menu[1]);
        if (text_exists("720@60FPS")) {
            flag2 = 1;
        } else {
            flag2 = 0;
            Asst.fail("normal record fail");
        }
        logger.info("flag2=" + flag2);
        if (flag1 == 1 && flag2 == 1) {

        } else {
            Asst.fail("====");
        }
    }
}
