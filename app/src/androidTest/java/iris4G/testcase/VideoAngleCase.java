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
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class VideoAngleCase extends VP2{
    Logger logger = Logger.getLogger(LiveUpDown.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void testSuperWide() throws Exception {
        CameraAction.configVideoAngle(Iris4GPage.video_Angle[0]);
        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(Iris4GPage.video_Angle[0]);
    }
    @Test
    public void testWide() throws Exception {
        CameraAction.configVideoAngle(Iris4GPage.video_Angle[1]);
        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(Iris4GPage.video_Angle[1]);
    }
    @Test
    public void testSuperMedium() throws Exception {
        CameraAction.configVideoAngle(Iris4GPage.video_Angle[2]);
        Iris4GAction.stopCamera();
        Iris4GAction.startCamera();
        CameraAction.checkVideoAngle(Iris4GPage.video_Angle[2]);
    }
}
