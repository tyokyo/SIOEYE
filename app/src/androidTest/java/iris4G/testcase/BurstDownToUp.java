package iris4G.testcase;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;
import java.util.logging.Logger;
import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.filters.SdkSuppress;

/**
 * @ Caibing
 * @自动翻转
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class BurstDownToUp extends VP2 {
    Logger logger = Logger.getLogger(AccountCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void testDownToUp() throws Exception{

        if(!gDevice.isScreenOn()){
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            logger.info("点击POWER使屏幕点亮");
        }
        Iris4GAction.initDevice();
        Iris4GAction.startCamera();
        CameraAction.navConfig(Iris4GPage.nav_menu[3]);
        clickById(Iris4GPage.camera_setting_shortcut_id);
        //先切换到Down，再测试Down到Up
        clickByText("Up/Down/Auto");
        clickByText("Down");
        clickByText("Up/Down/Auto");
        clickByText("Up");
        clickByText("Up/Down/Auto");

        UiCollection pic = new UiCollection(new UiSelector().className("android.widget.ListView"));
        UiObject ui = pic.getChildByInstance(new UiSelector().className("android.widget.RelativeLayout"), 0);
        UiObject uo = ui.getChild(new UiSelector().className("android.widget.TextView"));
        UiObject gx = ui.getChild(new UiSelector().className("android.widget.ImageView"));
        String text = uo.getText();
        if (ui.exists() && uo.exists() && "Up".equals(text) && gx.exists()){
            logger.info("testDownToUpCase_pass");
            //使相机回到初始状态Up
            clickByText("Up");
        }else{
            logger.info("testDownToUpCase_fail");
        }

    }
}
