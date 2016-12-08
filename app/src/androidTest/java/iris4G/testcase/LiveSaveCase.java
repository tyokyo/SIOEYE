package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.HashSet;
import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.FileManagerAction;
import iris4G.action.Iris4GAction;
import iris4G.action.VideoNode;
import iris4G.page.Iris4GPage;

/**
 * @Author elon
 * @Description 所有视频质量[72030|72060|108030]*视频角度[Super Wide|Wide|Medium]
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class LiveSaveCase extends VP2 {
    private static Logger logger = Logger.getLogger(LiveSaveCase.class.getName());

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
        //打开live&save 开关
        Iris4GAction.clickLiveAndSave();
    }
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }

    private void Live(String quality, String angle) throws Exception {
        //String quality = Iris4GPage.video_quality[0];
        //String angle =Iris4GPage.video_Angle[0];
        CameraAction.configVideoQuality(quality);
        CameraAction.configVideoAngle(angle);
        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
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
        if (resultHashSet.size() == 1) {
            String videoPath = resultHashSet.iterator().next();
            logger.info("new file:" + videoPath);
            String videoName = new File(videoPath).getName();
            VideoNode activeNode = Iris4GAction.VideoInfo(videoPath);
            int height = Integer.parseInt(quality.split("@")[0]);
            if (Iris4GAction.checkVideoInfo(height, activeNode)) {
                logger.info("video info check success-" + videoPath);
                FileManagerAction.playVideoByFileManager(videoName);
                if (text_exists_match("^Can't play this video.*")) {
                    logger.info(videoName + " play fail " + "-Can't play this video");
                    clickById("android:id/button1");
                    Asst.fail("Can't play this video");
                } else {
                    logger.info(videoName + " play success");
                }
            } else {
                logger.info("video info check failed" + videoPath);
            }
        } else {
            Asst.fail("video not exist");
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
