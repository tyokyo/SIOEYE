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
 * /*
 * a.普通录像模式
 * 1.修改视频质量为720@60FPS
 * 2.修改视角为Super Wide
 * 3.修改上下颠倒为auto
 * 4.切换到延时录像模式后再切换为普通录像
 * b.所有设置项都修改成功，更改的设置项都没有改变
 * @Description
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
    @Test
    public void test() throws Exception {
        String quality = Iris4GPage.video_quality[2];
        String angle = Iris4GPage.video_Angle[1];
        String lapse_time = Iris4GPage.lapse_time[0];

        CameraAction.configTimeLapse(navConfig_Lapse,lapse_time);
        CameraAction.configVideoQuality(navConfig_Video,quality);
        CameraAction.configVideoAngle(navConfig_Video,angle);

        //将Up改为Auto
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Up/Down/Auto");
        clickByText("Auto");
        gDevice.pressBack();
        //切换到延时录像再切换到普通录像
        CameraAction.navConfig(navConfig_Lapse);
        if (text_exists(lapse_time)) {
            //通过检查当前页面是否有3s存在，若存在，则正确，并记录flag1=1即true
        } else {
            Asst.fail("SLO_MO record fail");
        }
        CameraAction.navConfig(navConfig_LiveStream);
        if (text_exists(CameraAction.replaceFps(quality))) {

        } else {
            Asst.fail("normal record fail");
        }
    }
}
