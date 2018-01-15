package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Asst;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;

/**
 * @time Created on 2017/11/16.
 * @Author jiali.liu
 * @Description 打开延时直播开关
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class LapseLiveSwitchCase extends VP2 {
    Logger logger = Logger.getLogger(LapseLiveSwitchCase.class.getName());

    @BeforeClass
    public static void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    //检查开关状态的方法
    //检查状态栏信息的方法

    /**
     * @throws Exception
     * @Author jiali.liu
     * @description 未登录账号打开延时直播开关       OK
     */
    @Test
    public  void TestOpenLapseLiveSwitchWhenLogout() throws Exception {
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
    public  void TestOpenLapseLiveSwitchWhenLogin() throws Exception {
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
     *  @description 自定义任意直播分辨率，打开延时开关后注销账号,检查状态栏信息是否恢复普通直播
     *  @throws Exception
     */
    @Test
    public  void TestOpenLapseLiveSwitchThenLogout()throws Exception {
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        CameraAction.cameraSetting();
        //自定义分辨率
        CameraAction.navToCustomResolution();
        waitTime(1);
        Iris4GAction.scrollTextIntoView("User Defined(720@30FPS Bitrate0.2-10.0Mbps)");
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        AccountAction.loginAccount(useName, password);
        Asst.assertEquals("账号是否登录：",true,AccountAction.isLogin());
        CameraAction.openCompoundButton("time-lapse broadcast");
        Asst.assertEquals("状态栏信息合理：",true,CameraAction.checkLiveModeInfo("480@30"));
    }


}


