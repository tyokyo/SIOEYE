package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiSelector;

import org.hamcrest.Asst;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.GalleryAction;
import iris4G.action.Iris4GAction;
import iris4G.action.SettingAction;

/**
 * @time Created on 2017/11/16.
 * @Author jiali.liu
 * @Description 打开延时直播开关
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class LapseLiveCase extends VP2 {
    Logger logger = Logger.getLogger(LapseLiveCase.class.getName());

    @BeforeClass
    public static void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    //检查开关状态的方法
    //检查状态栏信息的方法

    /**
     * @throws Exception
     * @Author jiali.liu
     * @description 未登录账号打开延时直播开关
     */
    @Test
    public void TestOpenLapseLiveSwitchWhenLogout() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        waitTime(5);
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        //登录账号
        AccountAction.loginAccount(useName, password);
        Asst.assertEquals("账号是否登录：",true,AccountAction.isLogin());
        //注销账号
        AccountAction.logOut();
        Asst.assertEquals("账号是否登录：",false,AccountAction.isLogin());
        gDevice.pressBack();
        CameraAction.cameraSetting();
        CameraAction.openCompoundButton("time-lapse broadcast");
        Asst.assertEquals("是否弹出扫码框：",true, CameraAction.id_exists("com.hicam:id/accountlogin"));
        gDevice.pressBack();
        //检查延时直播开关状态、状态栏信息、其他开关状态
        CameraAction.cameraSetting();
        Asst.assertEquals("状态栏信息合理：",false,CameraAction.checkLiveModeInfo("480@30"));
    }

    /**
     * @description 登录账号后打开延时直播开关
     * @Author jiali.liu
     * @throws Exception
     */
    @Test
    public void testOpenLapseLiveSwitchWhenLogin() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(useName, password);
        Asst.assertEquals("账号是否登录：",true,AccountAction.isLogin());
        CameraAction.openCompoundButton("time-lapse broadcast");
        Asst.assertEquals("状态栏信息合理：",true,CameraAction.checkLiveModeInfo("480@30"));
        //发起直播,待添加检查特效直播的method
    }
    /**
     *  @Author jiali.liu
     *  @description 打开延时直播开关后注销账号
     *  @throws Exception
     */
    @Test
    public void testOpenLapseLiveSwitchThenLogout()throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(useName, password);
        Asst.assertEquals("账号是否登录：",true,AccountAction.isLogin());
        CameraAction.openCompoundButton("time-lapse broadcast");
        Asst.assertEquals("状态栏信息合理：",true,CameraAction.checkLiveModeInfo("480@30"));
    }

    /**
     *  @Author jiali.liu
     *  @description 关闭延时直播开关后检查其他选项是否恢复可点击状态
     *  @throws Exception
     */
    @Test
    public void testCloseLapseLiveSwitch() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(useName, password);
        Asst.assertEquals("账号是否登录：",true,AccountAction.isLogin());
        CameraAction.openCompoundButton("time-lapse broadcast");
        Asst.assertEquals("状态栏信息合理：",true,CameraAction.checkLiveModeInfo("480@30"));
        //检查自动重连、直播保存、定位服务、速度计、高度计、语音交互、静音直播开关是否可点击
        gDevice.pressBack();
        CameraAction.cameraSetting();
        CameraAction.navToMoreSettings();
        Asst.assertEquals("自动重连开关是否置灰：",false,CameraAction.checkCompoundButtonIsEnabled("Auto reconnect(beta)"));
        Asst.assertEquals("直播保存开关是否置灰：",false,CameraAction.checkCompoundButtonIsEnabled("Live&Save"));
        Asst.assertEquals("定位服务开关是否置灰：",false,CameraAction.checkCompoundButtonIsEnabled("Live&Location"));
        Asst.assertEquals("速度计开关是否置灰：",false,CameraAction.checkCompoundButtonIsEnabled("Speedometer"));
        Asst.assertEquals("高度计开关是否置灰：",false,CameraAction.checkCompoundButtonIsEnabled("Altimeter"));
        Asst.assertEquals("语音交互开关是否置灰：",false,CameraAction.checkCompoundButtonIsEnabled("Voice interaction"));
        Asst.assertEquals("静音直播开关是否置灰：",false,CameraAction.checkCompoundButtonIsEnabled("Live Mute"));
    }
    /**
     *  @Author jiali.liu
     *  @description 延时视频录制中停止
     *  @throws Exception
     */
    @Test
    public void testLapseRecordingToStop() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(useName, password);
        Asst.assertEquals("账号是否登录：",true,AccountAction.isLogin());
        CameraAction.openCompoundButton("time-lapse broadcast");
        Asst.assertEquals("状态栏信息合理：",true,CameraAction.checkLiveModeInfo("480@30"));
        Iris4GAction.cameraKey();
        Iris4GAction.waitTime(5);
        Iris4GAction.cameraKey();
        Asst.assertEquals("是否在预览界面：",false,getObjectById("com.hicam:id/camera_live_shortcut").exists());
    }
    /**
     *  @Author jiali.liu
     *  @description 延时视频直播中停止
     *  @throws Exception
     */
    @Test
    public void testLapseLivingToStop() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(useName, password);
        Asst.assertEquals("账号是否登录：",true,AccountAction.isLogin());
        CameraAction.openCompoundButton("time-lapse broadcast");
        Asst.assertEquals("状态栏信息合理：",true,CameraAction.checkLiveModeInfo("480@30"));
        Iris4GAction.cameraKey();
        //判断是否发起特效直播
        waitTime(110);
        Asst.assertEquals("是否发起直播：",true,gDevice.findObject(new UiSelector().text("broadcasting")).exists());
        Iris4GAction.cameraKey();
        Asst.assertEquals("是否在预览界面：",false,getObjectById("com.hicam:id/camera_live_shortcut").exists());
    }
    /**
     *  @Author jiali.liu
     *  @description 设置普通视角
     *  @throws Exception
     */
    @Test
    public void testLapseLiveSetMedium() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(useName, password);
        Asst.assertEquals("账号是否登录：",true,AccountAction.isLogin());
        CameraAction.openCompoundButton("time-lapse broadcast");
        Asst.assertEquals("状态栏信息合理：",true,CameraAction.checkLiveModeInfo("480@30"));
        gDevice.pressBack();
        CameraAction.setLiveAngle("Medium");
        Iris4GAction.cameraKey();
        waitTime(110);
        //判断是否发起特效直播
        Asst.assertEquals("是否发起直播：",true,gDevice.findObject(new UiSelector().text("broadcasting")).exists());
    }
    /**
     *  @Author jiali.liu
     *  @description 设置宽视角
     *  @throws Exception
     */
    @Test
    public void testLapseLiveSetWide() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(useName, password);
        Asst.assertEquals("账号是否登录：",true,AccountAction.isLogin());
        CameraAction.openCompoundButton("time-lapse broadcast");
        Asst.assertEquals("状态栏信息合理：",true,CameraAction.checkLiveModeInfo("480@30"));
        gDevice.pressBack();
        CameraAction.setLiveAngle("Wide");
        Iris4GAction.cameraKey();
        waitTime(110);
        //判断是否发起特效直播
        Asst.assertEquals("是否发起直播：",true,gDevice.findObject(new UiSelector().text("broadcasting")).exists());
    }
    /**
     *  @Author jiali.liu
     *  @description 设置超宽视角
     *  @throws Exception
     */
    @Test
    public void testLapseLiveSetSuperWide() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(useName, password);
        Asst.assertEquals("账号是否登录：",true,AccountAction.isLogin());
        CameraAction.openCompoundButton("time-lapse broadcast");
        Asst.assertEquals("状态栏信息合理：",true,CameraAction.checkLiveModeInfo("480@30"));
        gDevice.pressBack();
        CameraAction.setLiveAngle("Super Wide");
        Iris4GAction.cameraKey();
        waitTime(110);
        //判断是否发起特效直播
        Asst.assertEquals("是否发起直播：",true,gDevice.findObject(new UiSelector().text("broadcasting")).exists());
    }
    /**
     *  @Author jiali.liu
     *  @description 延时直播保存的录像视频在相册中再次发起直播
     *  @throws Exception
     */
    @Test
    public void testLapseLiveVideoInGallery() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String userName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(userName, password);
        Asst.assertEquals("账号是否登录：",true,AccountAction.isLogin());
        CameraAction.openCompoundButton("time-lapse broadcast");
        Asst.assertEquals("状态栏信息合理：",true,CameraAction.checkLiveModeInfo("480@30"));
        gDevice.pressBack();
        CameraAction.setLiveAngle("Super Wide");
        Iris4GAction.cameraKey();
        waitTime(110);
        //判断是否发起特效直播
        Asst.assertEquals("是否发起直播：",true,gDevice.findObject(new UiSelector().text("broadcasting")).exists());
        //进入相册发起直播
        Iris4GAction.startGallery();
        GalleryAction.startGalleryLive();
    }
    /**
     *  @Author jiali.liu
     *  @description 开启远程控制，检查灭屏时间和其他设置项
     *  @throws Exception
     */
    @Test
    public void testOpenRemoteControl() throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String userName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(userName, password);
        Asst.assertEquals("账号是否登录：",true,AccountAction.isLogin());
        CameraAction.openCompoundButton("time-lapse broadcast");
        Asst.assertEquals("状态栏信息合理：",true,CameraAction.checkLiveModeInfo("480@30"));
        gDevice.pressBack();
        CameraAction.cameraSetting();
        Iris4GAction.scrollTextIntoView("Remote control");
        CameraAction.openCompoundButton("Remote control");
        clickByText("OK");
        waitTime(20);
        CameraAction.checkScreenStatus();
        Iris4GAction.makeScreenOn();
        clickByText("Manual");
        Iris4GAction.stopCamera();
        SettingAction.setNeverTime();
        Iris4GAction.stopSettings();
        Iris4GAction.startCamera();
        CameraAction.cameraSetting();
        CameraAction.navToMoreSettings();
        Asst.assertEquals("自动重连开关是否置灰：",true,CameraAction.checkCompoundButtonIsEnabled("Auto reconnect(beta)"));
        Asst.assertEquals("直播保存开关是否置灰：",true,CameraAction.checkCompoundButtonIsEnabled("Live&Save"));
        Asst.assertEquals("定位服务开关是否置灰：",true,CameraAction.checkCompoundButtonIsEnabled("Live&Location"));
        Asst.assertEquals("速度计开关是否置灰：",true,CameraAction.checkCompoundButtonIsEnabled("Speedometer"));
        Asst.assertEquals("高度计开关是否置灰：",true,CameraAction.checkCompoundButtonIsEnabled("Altimeter"));
        Asst.assertEquals("语音交互开关是否置灰：",true,CameraAction.checkCompoundButtonIsEnabled("Voice interaction"));
        Asst.assertEquals("静音直播开关是否置灰：",true,CameraAction.checkCompoundButtonIsEnabled("Live Mute"));
    }
}


