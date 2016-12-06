package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;

/**
 * @ caibing
 * @连拍的自动翻转
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class BurstDownUpToAuto extends VP2{
    Logger logger = Logger.getLogger(BurstDownUpToAuto.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }

    int flag1;
    int flag2;
    @Test
    public void testBurstDownUpToAuto() throws Exception{
        if(!gDevice.isScreenOn()){
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        }
        Iris4GAction.initDevice();
        Iris4GAction.startCamera();
        CameraAction.navConfig(Iris4GPage.nav_menu[3]);
        CameraAction.cameraSetting();
        //Up To Auto
        clickByText("Up/Down/Auto");
        clickByText("Auto");
        clickByText("Up/Down/Auto");

        UiCollection pic = new UiCollection(new UiSelector().className("android.widget.ListView"));
        UiObject ui = pic.getChildByInstance(new UiSelector().className("android.widget.RelativeLayout"), 2);
        UiObject uo = ui.getChild(new UiSelector().className("android.widget.TextView"));
        UiObject gx = ui.getChild(new UiSelector().className("android.widget.ImageView"));
        String text = uo.getText();
        if (ui.exists() && uo.exists() && "Auto".equals(text) && gx.exists()){
            //使相机回到初始状态Up
            flag1 = 1;
            logger.info("flag1 pass = "+flag1);
            clickByText("Up");
        }else{
            flag1 = 0;
            logger.info("flag1 failed = "+flag1);
            Asst.fail();
        }

        //Down To Auto
        clickByText("Up/Down/Auto");
        clickByText("Down");
        clickByText("Up/Down/Auto");
        clickByText("Auto");
        clickByText("Up/Down/Auto");

        UiCollection pic1 = new UiCollection(new UiSelector().className("android.widget.ListView"));
        UiObject ui1 = pic1.getChildByInstance(new UiSelector().className("android.widget.RelativeLayout"), 2);
        UiObject uo1 = ui1.getChild(new UiSelector().className("android.widget.TextView"));
        UiObject gx1 = ui1.getChild(new UiSelector().className("android.widget.ImageView"));
        String text1 = uo.getText();
        if (ui1.exists() && uo1.exists() && "Auto".equals(text1) && gx1.exists()){
            //使相机回到初始状态Up
            flag2 = 1;
            logger.info("flag2 pass = "+ flag2);
            clickByText("Up");
        }else{
            flag2 = 0;
            logger.info("flag2 failed = " +flag2);
            Asst.fail();
        }
        if (flag1 == 1 && flag2 == 1){
            logger.info("testBurstDownUpToAuto_pass");
        }else{
            logger.info("testBurstDownUpToAuto_fail");
            Asst.fail();
        }
    }
}
