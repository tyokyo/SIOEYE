package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;

/**
 * @Caibing
 * @连拍的上下翻转
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class BurstUpToDown extends VP2{
    Logger logger = Logger.getLogger(BurstUpToDown.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }

    @Test
    public void testBurstUpToDown() throws Exception{
        if(!gDevice.isScreenOn()){
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            logger.info("点击POWER使屏幕点亮");
        }
        logger.info( "*****Start to run testBurstUpToDownCase*****");
        CameraAction.navConfig(Iris4GPage.nav_menu[3]);
        CameraAction.cameraSetting();
        clickByText("Up/Down/Auto");
        clickByText("Down");
        clickByText("Up/Down/Auto");
        //获取relative
        UiCollection pic = new UiCollection(new UiSelector().className("android.widget.ListView"));
        //int count = pic.getChildCount(new UiSelector().className("android.widget.RelativeLayout"));
        //for (int i=0;i<count;i++){
        UiObject ui = pic.getChildByInstance(new UiSelector().className("android.widget.RelativeLayout"), 1);
        UiObject uo = ui.getChild(new UiSelector().className("android.widget.TextView"));
        UiObject gx = ui.getChild(new UiSelector().className("android.widget.ImageView"));
        String text = uo.getText();
        if (ui.exists() && uo.exists() && "Down".equals(text) && gx.exists()){
            logger.info("testBurstUpToDownCase_pass");
            //使相机回到初始状态Up
            clickByText("Up");
        }else{
            logger.info("testBurstUpToDownCase_fail");
            Asst.fail();
        }
        logger.info( "*****End to run testBurstUpToDownCase*****");
    }
}
