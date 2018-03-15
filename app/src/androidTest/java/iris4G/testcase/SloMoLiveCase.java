package iris4G.testcase;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import com.squareup.spoon.Spoon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import ckt.base.VP2;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;



/**
 * Created by ruixiangxu on 2018/1/12.
 */

public class SloMoLiveCase extends VP2 {

    @Before
    public void  setup() throws Exception {
        Iris4GAction.initIris4G();

    }

    private  void NavToSloMoLive() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
        CameraAction.cameraSetting();
        CameraAction.scrollAndGetUIObject("Slow motion broadcast");
        CameraAction.openCompoundButton("Slow motion broadcast");
    }

    private  void  loginAndOpenSloMoLiveButton() throws Exception {
        initDevice();
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String username = cn.page.Constant.getUserName();
        String password = cn.page.Constant.getPassword();
        iris4G.action.AccountAction.loginAccount(username,password);
        NavToSloMoLive();

    }

    /*

    Case 1
    未登录账号打开慢镜头直播开关（手动登陆）

    */
    @Test
    public  void testNotLoginOpenSloMoLiveButton() throws Exception {
        if (AccountAction.isLogin()) {
            AccountAction.logOut();
        }
        waitTime(1);
        NavToSloMoLive();
        waitUntilFind("com.hicam:id/preview", 2000);
        CameraAction.clickById("com.hicam:id/accountlogin");
        String username = cn.page.Constant.getUserName();
        String password = cn.page.Constant.getPassword();
        setText("com.hicam:id/login_id_input", username);
        setText("com.hicam:id/login_password_input", password);
        clickById("com.hicam:id/login_btn_login");
        waitTime(1);
        boolean islogin = AccountAction.isLogin();
        if (islogin) {
            logger.info("Open SloMOLiveButton login Success");
            if (getUiObjectByText("480@30").exists()) {
                logger.info("Open SloMOLiveButton Success");
            } else {
                Assert.fail("Open SloMOLiveButton failed");
            }
            Spoon.screenshot("OpenSloMOLiveButtonFailed");
        } else {
            Assert.fail("Open SloMOLiveButton login Failed");
            Spoon.screenshot("OpenSloMOLiveButtonloginFailed");
        }
    }

/*
   Case 2
     已登录账号打开慢镜头直播开关
     */
        @Test
        public  void testOpenSloMoLiveButton() throws Exception {
            loginAndOpenSloMoLiveButton();
            waitTime(1);
            if (getUiObjectByText("480@30").exists()){
                    logger.info("Open SloMOLiveButton Success");
                }
            else {Assert.fail("Open SloMOLiveButton failed");}
                Spoon.screenshot("OpenSloMOLiveButtonFailed");
            logger.info("will check other button status");
}

    /*
       Case 3
       慢镜头直播
         */
        @Test
        public  void  testSloMoLive() throws Exception {
            loginAndOpenSloMoLiveButton();
            Iris4GAction.cameraKey();
            waitTime(60);
            boolean isLive=getUiObjectByText("broadcasting").exists();
            if (isLive==true){
                logger.info("SloMOLiveFailed");
                Spoon.screenshot("SloMOLiveFailed");
            }else {
                logger.info("SloMOLivePass");
            }
        }

    /*
    Case 4
    打开慢镜头直播开关后，注销账号
      */
    @Test
    public  void  testOpenSloMoLiveThenLogout() throws Exception {
        loginAndOpenSloMoLiveButton();
        CameraAction.scrollAndGetUIObject("Account");
        clickByText("Account");
        waitUntilFindText("Log out",500);
        clickById("com.hicam:id/logout_btn");
        waitTime(1);
        if ( AccountAction.isLogin()){
            logger.info("SloMoLivelogOutFailed");
            Spoon.screenshot("SloMoLivelogOutFailed");
        }else {
            logger.info("SloMOLive logout success");
        }
    }

    /*
     Case5
    打开慢镜头直播开关后，检查状态栏
     */
    @Test
    public  void testStatusBarOfSloMoLive() throws Exception {
        loginAndOpenSloMoLiveButton();
        if(!getUiObjectByText("480@25").exists() && getUiObjectByText("480@30").exists()){
            logger.info("StatusBarOfSloMoLivePass");
        }else {
            logger.info("StatusBarOfSloMoLiveFailed");
            Spoon.screenshot("StatusBarOfSloMoLiveFailed");
        }
    }

    /*
     Case6
    打开慢镜头直播开关后，检查置灰的直播设置选项
     */
    @Test
    public  void testSettingStatusAfterOpenSloMoLiveButton() throws Exception {
        loginAndOpenSloMoLiveButton();
        Assert.assertEquals("VideoQualityIsNotClickable",false,checkVideoQualityButtonIsClikable());
        CameraAction.navToMoreSettings();
        Assert.assertEquals("Auto reconnect(beta)IsNotEnable",false,checkButtonStatusIsEnable("Auto reconnect(beta)"));
        Assert.assertEquals("Live&SaveIsNotEnable",false,checkButtonStatusIsEnable("Live&Save"));
        Assert.assertEquals("Live&LocationIsNotEnable",false,checkButtonStatusIsEnable("Live&Location"));
        Assert.assertEquals("SpeedometerIsNotEnable",false,checkButtonStatusIsEnable("Speedometer"));
        Assert.assertEquals("AltimeterIsNotEnable",false,checkButtonStatusIsEnable("Altimeter"));
        Assert.assertEquals("Voice interactionIsNotEnable",false,checkButtonStatusIsEnable("Voice interaction"));
        Assert.assertEquals("Live MuteIsNotEnable",false,checkButtonStatusIsEnable("Live Mute"));
    }

    /*
    Case7
   关闭慢镜头直播开关后，检查状态栏
    */
    @Test
    public  void testStatusBarAfterCloseSloMoLiveButton() throws Exception {
        loginAndOpenSloMoLiveButton();
        waitTime(2);
        CameraAction.openCompoundButton("Slow motion broadcast");
        if(!getUiObjectByText("480@30").exists()&&getUiObjectByText("480@25").exists()){
            logger.info("StatusBarAfterCloseSloMoLiveButtonPass");
        }else{
            logger.info("StatusBarAfterCloseSloMoLiveButtonFailed");
            Spoon.screenshot("StatusBarAfterCloseSloMoLiveButtonFailed");
        }
    }

    /*
     Case8
    关闭慢镜头直播开关后，检查置灰的直播设置选项是否恢复可用
     */
    @Test
    public  void testSettingStatusAfterCloseSloMoLiveButton() throws Exception {
        loginAndOpenSloMoLiveButton();
        waitTime(2);
        CameraAction.openCompoundButton("Slow motion broadcast");
//        Assert.assertEquals("VideoQualityIsClickable",true,checkVideoQualityButtonIsClikable());
        CameraAction.navToMoreSettings();
        Assert.assertEquals("Auto reconnect(beta)IsEnable",true,checkButtonStatusIsEnable("Auto reconnect(beta)"));
        Assert.assertEquals("Live&SaveIsEnable",true,checkButtonStatusIsEnable("Live&Save"));
        Assert.assertEquals("Live&LocationIsEnable",true,checkButtonStatusIsEnable("Live&Location"));
        Assert.assertEquals("SpeedometerIsEnable",true,checkButtonStatusIsEnable("Speedometer"));
        Assert.assertEquals("AltimeterIsEnable",true,checkButtonStatusIsEnable("Altimeter"));
        Assert.assertEquals("Voice interactionIsEnable",true,checkButtonStatusIsEnable("Voice interaction"));
        Assert.assertEquals("Live MuteIsEnable",true,checkButtonStatusIsEnable("Live Mute"));
    }




    /*
    检查直播设置Video Quality的clickable属性，以判断直播是否质量是否置灰
     */
    private  static boolean checkVideoQualityButtonIsClikable() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
        CameraAction.cameraSetting();
        Iris4GAction.scrollTextIntoView("Video Quality");
        boolean VideoQuality=Iris4GAction.scrollTextIntoView("Video Quality").isClickable();
        return VideoQuality;
    }

    /*
    检查直播设置中的moreSetting中类似录播开关的enable属性，以判断开关是否置灰
     */
    private  static boolean checkButtonStatusIsEnable(String textViewName) throws Exception {
        Iris4GAction.scrollTextIntoView(textViewName);
        UiObject2 scrollView = getObject2ByClass(android.widget.ScrollView.class);
        List<UiObject2> relatives = scrollView.findObjects(By.clazz(android.widget.RelativeLayout.class));
        UiObject2 clickBtn=null;
        for (UiObject2 relateLayout : relatives) {
            boolean compoundButton = relateLayout.hasObject(By.clazz(android.widget.CompoundButton.class));
            boolean textView = relateLayout.hasObject(By.text(textViewName));
            if (compoundButton == true && textView == true) {
                clickBtn = relateLayout.findObject(By.clazz(android.widget.CompoundButton.class));
                Spoon.screenshot("CompoundButtonNameIs",textViewName);
                break;
            }
        }
        boolean buttonResult =clickBtn.isEnabled();
        return buttonResult;
    }


}
