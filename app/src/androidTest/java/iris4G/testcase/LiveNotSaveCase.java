package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;

/**
 * @Author elon
 * @Description
 * 默认live&save 开关-关闭状态
 * 所有不同设置下发起直播
 * 没有视频文件生成
 * 所有视频质量[72030|72060|108030]*视频角度[Super Wide|Wide|Medium]
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class LiveNotSaveCase extends VP2 {
    private String navConfig_LiveStream=Iris4GPage.nav_menu[0];
    private String navConfig_Video=Iris4GPage.nav_menu[1];
    private static Logger logger = Logger.getLogger(LiveNotSaveCase.class.getName());

    @BeforeClass
    public static void AccountAndLiveSave() throws Exception {
        initDevice();
        //清除app数据  包括登录的账号
        Iris4GAction.pmClear();
        //启动 camera
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        //登录账号
        AccountAction.loginAccount(useName, password);
        //默认live&save 开关-关闭状态
    }
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }

    private void Live(String quality, String angle) throws Exception {
        //String quality = Iris4GPage.video_quality[0];
        //String angle =Iris4GPage.video_Angle[0];
        CameraAction.configVideoQuality(navConfig_Video,quality);
        CameraAction.configVideoAngle(navConfig_LiveStream,angle);
        CameraAction.navConfig(navConfig_LiveStream);
        waitTime(2);
        //验证设置的显示,当前处于直播模式，左顶部显示直播图标
        Asst.assertEquals("Live", true, id_exists(Iris4GPage.camera_mode_label_live));

        HashSet<String> beforeTakeVideoList = Iris4GAction.FileList("/sdcard/video");
        Iris4GAction.cameraKey();
        CameraAction.cameraRecordTime();
        waitTime(20);
        CameraAction.cameraRecordTime();
        Iris4GAction.cameraKey();
        waitTime(5);
        HashSet<String> afterTakeVideoList = Iris4GAction.FileList("/sdcard/Video");
        HashSet<String> resultHashSet = Iris4GAction.result(afterTakeVideoList, beforeTakeVideoList);
        getObject2ById(Iris4GPage.camera_setting_shortcut_id);
        if (resultHashSet.size() == 0) {

        } else {
            Asst.fail("default Live and save error");
        }
    }

    @Test
    public void testL72030fpsSuperWide() throws Exception {
        Live(Iris4GPage.video_quality[0], Iris4GPage.video_Angle[0]);
    }

    @Test
    public void testL72060fpsSuperWide() throws Exception {
        Live(Iris4GPage.video_quality[1], Iris4GPage.video_Angle[0]);
    }

    @Test
    public void testL108030fpsSuperWide() throws Exception {
        Live(Iris4GPage.video_quality[2], Iris4GPage.video_Angle[0]);
    }

    @Test
    public void testL72030fpsWide() throws Exception {
        Live(Iris4GPage.video_quality[0], Iris4GPage.video_Angle[1]);
    }

    @Test
    public void testL72060fpsWide() throws Exception {
        Live(Iris4GPage.video_quality[1], Iris4GPage.video_Angle[1]);
    }

    @Test
    public void testL108030fpsWide() throws Exception {
        Live(Iris4GPage.video_quality[2], Iris4GPage.video_Angle[1]);
    }

    @Test
    public void testL72030fpsMedium() throws Exception {
        Live(Iris4GPage.video_quality[0], Iris4GPage.video_Angle[2]);
    }

    @Test
    public void testL72060fpsMedium() throws Exception {
        Live(Iris4GPage.video_quality[1], Iris4GPage.video_Angle[2]);
    }

    @Test
    public void testL108030fpsMedium() throws Exception {
        Live(Iris4GPage.video_quality[2], Iris4GPage.video_Angle[2]);
    }
}
