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
import iris4G.page.NavPage;

import static ckt.base.VP.initDevice;

/**
 * Created by yun.yang on 2017/6/2.
 * @Description 所有直播视频质量[72025HD|48025HD|48025CD]*视频角度[Super Wide|Wide|Medium][up/down]
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class LiveSettingCase {
    private static Logger logger = Logger.getLogger(LiveSettingCase.class.getName());

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

    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }

    @Test
    /**
     * case1
     * 连续修改10次直播视频质量
     * 在预览界面检查是否设置成功
     * LiveVideoQuality为"自定义","480@25FPS","720@25FPS",
     *
     */
    public void testSettingLiveQuality() throws Exception {
        String LiveQualityUserDefined=NavPage.live_qualityUserDefined,checkLiveQualityUserDefined="720@30(0.4-10.0)";
        String LiveQuality480=NavPage.live_quality480,checkLiveQuality480="480@25(0.3-4.0)";
        String LiveQuality720=NavPage.live_quality720,checkLiveQuality720="720@25(1.3-6.0)";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        for (int i=0;i<10;i++) {
            CameraAction.configLiveVideoQuality( LiveQualityUserDefined);
            CameraAction.checkLiveVideoQualityStatus(checkLiveQualityUserDefined);
            logger.info("next");
            CameraAction.configLiveVideoQuality(LiveQuality480);
            CameraAction.checkLiveVideoQualityStatus(checkLiveQuality480);
            CameraAction.configLiveVideoQuality(LiveQuality720);
            CameraAction.checkLiveVideoQualityStatus(checkLiveQuality720);
        }
    }
    @Test
    /**
     * case2
     * 480@25FPS(SD)和"Super Wide"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"480@25FPS(SD)",
     *
     */
    public void testUserDefinedSuperWideLiveForAllAngle() throws Exception {
        String LiveQualityUserDefined=NavPage.live_qualityUserDefined,CheckLiveQualityUserDefined="720@30";
        String LiveAngleSW="Super Wide";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQualityUserDefined,LiveAngleSW,CheckLiveQualityUserDefined);
    }
    @Test
    /**
     * case3
     * 480@25FPS(SD)和"Wide"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"480@25FPS(SD)",
     *
     */
    public void testUserDefinedWideLiveForAllAngle() throws Exception {
        String LiveQualityUserDefined=NavPage.live_qualityUserDefined,CheckLiveQualityUserDefined="720@30";
        String LiveAngleWide="Wide";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQualityUserDefined,LiveAngleWide,CheckLiveQualityUserDefined);
    }
    @Test
    /**
     * case4
     * 480@25FPS(SD)和"Medium"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"480@25FPS(SD)",
     *
     */
    public void testUserDefinedMediumLiveForAllAngle() throws Exception {
        String LiveQualityUserDefined=NavPage.live_qualityUserDefined,CheckLiveQualityUserDefined="720@30";
        String LiveAngleWide="Medium";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQualityUserDefined,LiveAngleWide,CheckLiveQualityUserDefined);
    }
    @Test
    /**
     * case5
     * 480@25FPS(HD)和"Super Wide"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"480@25FPS(HD)",
     *
     */
    public void test48025SuperWideLiveForAllAngle() throws Exception {
        String LiveQuality480=NavPage.live_quality480,CheckLiveQuality480="480@25";
        String LiveAngleSW="Super Wide";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality480,LiveAngleSW,CheckLiveQuality480);
    }
    @Test
    /**
     * case6
     * 480@25FPS(HD)和"Wide"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"480@25FPS(HD)",
     *
     */
    public void test48025WideLiveForAllAngle() throws Exception {
        String LiveQuality480=NavPage.live_quality480,CheckLiveQuality480="480@25";
        String LiveAngleWide="Wide";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality480,LiveAngleWide,CheckLiveQuality480);
    }
    @Test
    /**
     * case7
     * 480@25FPS(HD)和"Medium"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"480@25FPS(HD)",
     *
     */
    public void test48025HDMediumLiveForAllAngle() throws Exception {
        String LiveQuality480=NavPage.live_quality480,CheckLiveQuality480="480@25";
        String LiveAngleWide="Medium";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality480,LiveAngleWide,CheckLiveQuality480);
    }
    @Test
    /**
     * case8
     * 720@25FPS(HD)和"Super Wide"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"720@25FPS(HD)",
     *
     */
    public void test72025SuperWideLiveForAllAngle() throws Exception {
        String LiveQuality720=NavPage.live_quality720,CheckLiveQuality720="720@25";
        String LiveAngleSW="Super Wide";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality720,LiveAngleSW,CheckLiveQuality720);
    }
    @Test
    /**
     * case9
     * 720@25FPS(HD)和"Wide"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"720@25FPS(HD)",
     *
     */
    public void test72025WideLiveForAllAngle() throws Exception {
        String LiveQuality720=NavPage.live_quality720,CheckLiveQuality720="720@25";
        String LiveAngleWide="Wide";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality720,LiveAngleWide,CheckLiveQuality720);
    }
    @Test
    /**
     * case10
     * 720@25FPS(HD)和"Medium"
     * 设置成功后检查预览界面视频质量并发起直播，检查双击屏幕开启和关闭变焦功能
     * LiveVideoQuality为"720@25FPS(HD)",
     *
     */
    public void test72025HDMediumLiveForAllAngle() throws Exception {
        String LiveQuality720=NavPage.live_quality720,CheckLiveQuality720="720@25";
        String LiveAngleWide="Medium";
        String LiveModem="Live Stream";
        CameraAction.navConfig(LiveModem);
        CameraAction.checkLiveQualityAndAngleLiveAndZoom(LiveQuality720,LiveAngleWide,CheckLiveQuality720);
    }
}
