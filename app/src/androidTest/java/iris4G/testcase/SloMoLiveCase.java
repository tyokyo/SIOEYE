package iris4G.testcase;

import android.support.test.uiautomator.UiObject;;
import android.support.test.uiautomator.UiSelector;
import com.squareup.spoon.Spoon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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



private  static boolean checkOtherliveSettingButton() throws Exception {
    CameraAction.navConfig(Iris4GPage.nav_menu[0]);
    CameraAction.cameraSetting();
    CameraAction.scrollAndGetUIObject("Video Quality").click();
    boolean  qualityBtton=getObjectById("com.hicam:id/settingImageView").exists();
    if (qualityBtton==false){
        logger.info("Video Quality  unavailable ");
        return false;
    }
   else {
        logger.info("Video Quality  available");
        return true;
   }

}


    private  static boolean checkOtherliveSettingButton2() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
        CameraAction.cameraSetting();
        waitTime(1);
        CameraAction.scrollAndGetUIObject("More settings");
        clickByText("More settings");
        CameraAction.scrollAndGetUIObject("Auto reconnect(beta)");
        UiObject clickButton=new UiObject(new UiSelector().className("android.widget.RelativeLayout"));
        boolean isClickButton=clickButton.isEnabled();
        if (isClickButton) {
            logger.info("Auto reconnect(beta)  available");
            return true;
        } else {
            logger.info("Auto reconnect(beta)  unavailable ");
            return false;
        }

    }



}
