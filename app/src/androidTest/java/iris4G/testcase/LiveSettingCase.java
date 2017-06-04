package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;

import static ckt.base.VP.initDevice;

/**
 * Created by yun.yang on 2017/6/2.
 * @Description 所有直播视频质量[72030HD|48030HD|48030CD]*视频角度[Super Wide|Wide|Medium][up/down]
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class LiveSettingCase {
    private static Logger logger = Logger.getLogger(LiveSettingCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @BeforeClass
    public static void loginAccountWithDefault() throws Exception {
        initDevice();
        //清除app数据  包括登录的账号
        Iris4GAction.pmClear();
        //启动 camera
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        //登录账号
        AccountAction.loginAccount(useName, password);
    }
    @Test
    /**
     * case1
     * 连续修改10次直播视频质量
     * 在预览界面检查是否设置成功
     * LiveVideoQuality为"480@30FPS(CD)","480@30FPS(HD)","720@30FPS(HD)",
     *
     */
    public void testSettingLiveQuality() throws Exception {
        String LiveQuality480SD="480@30FPS(SD)",checkLiveQuality480SD="480@30SD";
        String LiveQuality480HD="480@30FPS(HD)",checkLiveQuality480HD="480@30HD";
        String LiveQuality720HD="720@30FPS(HD)",checkLiveQuality720HD="720@30HD";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        for (int i=0;i<10;i++) {
            CameraAction.configLiveVideoQuality( LiveQuality480SD);
            CameraAction.checkLiveVideoQualityStatus(checkLiveQuality480SD);
            CameraAction.configLiveVideoQuality(LiveQuality480HD);
            CameraAction.checkLiveVideoQualityStatus(checkLiveQuality480HD);
            CameraAction.configLiveVideoQuality(LiveQuality720HD);
            CameraAction.checkLiveVideoQualityStatus(checkLiveQuality720HD);
        }
    }
    @Test
    /**
     * case2
     * 480@30FPS(SD)和"Super Wide"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"480@30FPS(SD)",
     *
     */
    public void test48030SDSuperWideLiveForAllAngle() throws Exception {
        String LiveQuality480SD="480@30FPS(SD)",CheckLiveQuality480SD="480@30SD";
        String LiveAngleSW="Super Wide";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality480SD,LiveAngleSW,CheckLiveQuality480SD);
    }
    @Test
    /**
     * case3
     * 480@30FPS(SD)和"Wide"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"480@30FPS(SD)",
     *
     */
    public void test48030SDWideLiveForAllAngle() throws Exception {
        String LiveQuality480SD="480@30FPS(SD)",CheckLiveQuality480SD="480@30SD";
        String LiveAngleWide="Wide";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality480SD,LiveAngleWide,CheckLiveQuality480SD);
    }
    @Test
    /**
     * case4
     * 480@30FPS(SD)和"Medium"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"480@30FPS(SD)",
     *
     */
    public void test48030SDMediumLiveForAllAngle() throws Exception {
        String LiveQuality480SD="480@30FPS(SD)",CheckLiveQuality480SD="480@30SD";
        String LiveAngleWide="Medium";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality480SD,LiveAngleWide,CheckLiveQuality480SD);
    }
    @Test
    /**
     * case5
     * 480@30FPS(HD)和"Super Wide"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"480@30FPS(HD)",
     *
     */
    public void test48030HDSuperWideLiveForAllAngle() throws Exception {
        String LiveQuality480HD="480@30FPS(HD)",CheckLiveQuality480HD="480@30HD";
        String LiveAngleSW="Super Wide";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality480HD,LiveAngleSW,CheckLiveQuality480HD);
    }
    @Test
    /**
     * case6
     * 480@30FPS(HD)和"Wide"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"480@30FPS(HD)",
     *
     */
    public void test48030HDWideLiveForAllAngle() throws Exception {
        String LiveQuality480HD="480@30FPS(HD)",CheckLiveQuality480HD="480@30HD";
        String LiveAngleWide="Wide";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality480HD,LiveAngleWide,CheckLiveQuality480HD);
    }
    @Test
    /**
     * case7
     * 480@30FPS(HD)和"Medium"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"480@30FPS(HD)",
     *
     */
    public void test48030HDMediumLiveForAllAngle() throws Exception {
        String LiveQuality480HD="480@30FPS(HD)",CheckLiveQuality480HD="480@30HD";
        String LiveAngleWide="Medium";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality480HD,LiveAngleWide,CheckLiveQuality480HD);
    }
    @Test
    /**
     * case8
     * 720@30FPS(HD)和"Super Wide"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"720@30FPS(HD)",
     *
     */
    public void test72030HDSuperWideLiveForAllAngle() throws Exception {
        String LiveQuality720HD="720@30FPS(HD)",CheckLiveQuality720HD="720@30HD";
        String LiveAngleSW="Super Wide";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality720HD,LiveAngleSW,CheckLiveQuality720HD);
    }
    @Test
    /**
     * case9
     * 720@30FPS(HD)和"Wide"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"720@30FPS(HD)",
     *
     */
    public void test72030HDWideLiveForAllAngle() throws Exception {
        String LiveQuality720HD="720@30FPS(HD)",CheckLiveQuality720HD="720@30HD";
        String LiveAngleWide="Wide";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality720HD,LiveAngleWide,CheckLiveQuality720HD);
    }
    @Test
    /**
     * case10
     * 720@30FPS(HD)和"Medium"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"720@30FPS(HD)",
     *
     */
    public void test72030HDMediumLiveForAllAngle() throws Exception {
        String LiveQuality720HD="720@30FPS(HD)",CheckLiveQuality720HD="720@30HD";
        String LiveAngleWide="Medium";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality720HD,LiveAngleWide,CheckLiveQuality720HD);
    }
}
