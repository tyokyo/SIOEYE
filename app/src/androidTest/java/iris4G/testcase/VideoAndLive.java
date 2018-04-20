package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import com.squareup.spoon.Spoon;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.action.VideoNode;
import iris4G.page.Iris4GPage;
import iris4G.page.NavPage;

/**
 * Created by yun.yang on 2018/01/27   .
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class VideoAndLive extends VP2 {
    Logger logger = Logger.getLogger(VideoAndLive.class.getName());
    @AfterClass
    public static void loginAccountWithDefault() throws Exception {
        initDevice();
        //清除app数据  包括登录的账号
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(useName, password);
    }
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    /*
    case1: SI-1529:录播界面输入账号登陆
    前提
    1.进入相机，选择普通录像模式 2.sioeye账号未登陆
    步骤：
    点击录播开关，进入账号输入界面，输入正确账号
    目标：
    登陆成功，进入模式选择界面，界面上显示倒计时进度条，显示“仅录像”或“切换账号”，同时显示“正在使用XXX账号进行直播”
     */
    @Test
    public void testLoginWithVideoAndLive() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        VideoNode.navToVideoAndLiveLoginPage();
        clickById(Iris4GPage.account_login);
        String useName = Constant.getUserName("sioeye_id");
        String password = Constant.getPassword();
        String useSioeyeIdLive="will use sioeye ID: "+useName+" to live steam";
        logger.info(useSioeyeIdLive);
        AccountAction.login(useName, password);
        if (text_exists("RecordingOnly")&text_exists("SwitchSioeyeID")&text_exists(useSioeyeIdLive)){
            Spoon.screenshot("LoginSuccessWithVideoAndLive");
        }
        waitTime(3);
        CameraAction.stopVideoOrLive();//结束录播
    }
    /*
    Case2:SI-1530:录播界面取消登陆
    摘要
        录播界面取消登陆
    前提
    未登录直播账号
    1. 打开录播开关，进入扫码界面，点击退出键  检查录播开关
    2.  打开录播开关，切换到账号输入界面，点击取消或者退出键  检查录播开关
     */
    @Test
    public void testCancelLoginWithVideoAndLive() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        VideoNode.navToVideoAndLiveLoginPage();
        gDevice.pressBack();
        waitTime(1);
        if (!id_exists(Iris4GPage.camera_video_shortcut_id)){
            Assert.fail("backFail");}
        VideoNode.navToVideoAndLiveLoginPage();
        clickById(Iris4GPage.account_login);
        waitTime(1);
        clickByText("Cancel");
        waitTime(1);
        if (!id_exists(Iris4GPage.camera_video_shortcut_id)){
            Assert.fail("backFail");}
    }

    /*
    Case3：SI-1535:错误账号正确密码
    前提
    切换到账号登陆界面
    步骤
     手动输入错误的账号和密码，点击登录  提示用户不存在
     */
    @Test
    public void testLoginErrorIDWithVideoAndLive() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        VideoNode.navToVideoAndLiveLoginPage();
        clickById(Iris4GPage.account_login);
        String useName = Constant.randomStringGenerator(10);
        String password = Constant.getPassword();
        AccountAction.login(useName, password);
        if (!id_exists(Iris4GPage.login_btn_login)){
            Assert.fail("ErrorIDLoginSucceed");
        }
    }

    /*
    Case4：SI-1536:正确账号错误密码
    前提
    切换到账号登陆界面
    手动输入正确的账号和错误的密码，点击登录   提示密码不匹配，页面显示正确
     */
    @Test
    public void testLoginErrorPasswordWithVideoAndLive() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        VideoNode.navToVideoAndLiveLoginPage();
        clickById(Iris4GPage.account_login);
        String useName = Constant.getUserName();
        String password = Constant.randomStringGenerator(10);
        AccountAction.login(useName, password);
        waitTime(1);
        if (!id_exists(Iris4GPage.login_btn_login)){
            Assert.fail("ErrorPasswordLoginSucceed");
        }
    }

    @Test
    /*
     Case5：SI-1531:直播界面登陆后发起录播
     前提
    直播设置中登陆直播账号
    步骤：
     打开录播开关，点击拍摄键
     发起直播
     */
    public void testMakeVideoAndLiveAfterLogin() throws Exception {
        CameraAction.loginAndOpenVideoLiveButton();
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitUntilFind(Iris4GPage.video_and_live_recording_live,10000);
        if (!id_exists(Iris4GPage.video_and_live_recording_live)){
            gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
            Assert.fail("Live&VideoFailed");
        }
        CameraAction.stopVideoOrLive();
    }

    @Test
    /*
    Case6:SI-1537:录播登陆成功后模式选择仅录像
     1.在倒计时内选择仅录像  正常开始录像，
     2.停止后检查录播开关是否开启  录播开关关闭状态
     */
    public void testVideoAndLiveJustVideo() throws Exception {
        CameraAction.loginByVideoAndLive();
        waitUntilFind(Iris4GPage.video_and_live_recordingonly,5000);
        clickById(Iris4GPage.video_and_live_recordingonly);
        waitUntilFind(Iris4GPage.video_and_live_recording_live,10000);
        if (id_exists(Iris4GPage.video_and_live_recording_live)){
            Assert.fail("recordingOnlyHaveLiveId");
        }
        CameraAction.stopVideoOrLive();
        if (CameraAction.checkVideoAndLiveButton()){
            Assert.fail("VideoAndLiveButtonIsOpen");
        }
    }
    @Test
    /*
    Case7 SI-1538:录播登陆成功后模式选择切换账号
     在倒计时内选择切换账号     返回账号登陆界面
     */
    public void testVideoAndLiveChangeAccount() throws Exception {
        CameraAction.loginByVideoAndLive();
        waitUntilFind(Iris4GPage.video_and_live_changedaccount,5000);
        clickById(Iris4GPage.video_and_live_changedaccount);
        waitUntilFind(Iris4GPage.recording_time_id,3);
        if (id_exists(Iris4GPage.recording_time_id)){
            Assert.fail("recordingOnlyHaveLiveId");
        }
        if (!text_exists("Sioeye Account")){
            Assert.fail("AccountExist");
        }
        CameraAction.stopVideoOrLive();

    }
    @Test
    /*
     Case8 SI-1539:账号登陆成功后不选择模式
     在3s倒计时内不做操作  自动发起录播
     */
    public void testAutoLiveAfterLogin() throws Exception {
        CameraAction.loginByVideoAndLive();
        waitUntilFind(Iris4GPage.video_and_live_progressbar,5000);
        waitUntilFind(Iris4GPage.video_and_live_recording_live,10000);
        if (!id_exists(Iris4GPage.video_and_live_recording_live)){
            Assert.fail("AutoLiveFailed");
        }
        CameraAction.stopVideoOrLive();
    }
    @Test
    /*
    Case9 SI-1540:账号登陆成功后不选择模式
    1.在倒计时3s内不做选择  1.相机开始录像，并伴有提示音 2.相机在录像后开始直播，并伴有提示音
    3.录播中，双击拍照键（亮屏下）      录制中拍照，有有提示语
    2.点击拍照键（亮屏下）  相机直播、录像均停止，并伴有提示音
     */
    public void testKeyDuringVideoAndLive() throws Exception {
        CameraAction.loginByVideoAndLive();
        waitUntilFind(Iris4GPage.video_and_live_recording_live,10000);
        waitTime(2);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        Thread.sleep(70);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(2);
        if (!id_exists(Iris4GPage.video_and_live_recording_live)){
            Assert.fail("VideoAndLiveStopAfterDoubleClick");
        }
        waitTime(1);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(2);
        if (id_exists(Iris4GPage.recording_time_id)){
            Spoon.screenshot("StopVideoAndLive");
            Assert.fail("StopVideoAndLiveFailed");
        }
    }
    @Test
    /*
    Case10 SI-1542:不同视频质量下发起录播
    录像中依次设置不同的视频质量，开始录播  录播正常
    检查录像的视频质量      均为720@25fps
     */
    public void testDifferentResolutionStartVideoAndLive() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(useName, password);
        CameraAction.navConfig(NavPage.navConfig_Video);
        CameraAction.makeVideoAndLiveButtonWithDR(Iris4GPage.video_quality[6]);//480P25
        CameraAction.makeVideoAndLiveButtonWithDR(Iris4GPage.video_quality[3]);//480P120
        CameraAction.makeVideoAndLiveButtonWithDR(Iris4GPage.video_quality[1]);//720P60
        CameraAction.makeVideoAndLiveButtonWithDR(Iris4GPage.video_quality[4]);//1080P25
    }

}
