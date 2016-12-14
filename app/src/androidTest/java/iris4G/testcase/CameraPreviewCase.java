package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import android.view.KeyEvent;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;


/**
 * Created by jiali on 2016/12/10.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class CameraPreviewCase extends VP2{
    Logger logger =Logger.getLogger(CameraPreviewCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    public static boolean checkUIisExist(){
        UiObject liveObject = getObjectById(Iris4GPage.camera_live_shortcut_id);
        UiObject capObject = getObjectById(Iris4GPage.camera_cap_shortcut_id);
        UiObject videoObject = getObjectById(Iris4GPage.camera_video_shortcut_id);
        UiObject settingObject = getObjectById(Iris4GPage.camera_setting_shortcut_id);
        Asst.assertEquals("UIobject is exist",true,liveObject.exists());
        Asst.assertEquals("UIobject is exist",true,capObject.exists());
        Asst.assertEquals("UIobject is exist",true,videoObject.exists());
        Asst.assertEquals("UIobject is exist",true,settingObject.exists());
        if(liveObject.exists()&&capObject.exists()&&videoObject.exists()&&settingObject.exists()){
            VP2.logger.info("UI is correct");
            return true;
        }else {
            VP2.logger.info("UI is Wrong");
            return false;
        }

    }
    @Test
    /** 预览界面灭屏-亮屏*/
    public void Test() throws Exception {
        gDevice.pressHome();
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        Iris4GAction.waitTime(5);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(2);
        checkUIisExist();
    }
   /** 预览界面灭屏-1min-亮屏,亮屏后无异常*/
    @Test
    public void Test1()throws Exception{
        gDevice.pressHome();
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        Iris4GAction.waitTime(60);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        checkUIisExist();
    }
    /** 预览界面灭屏-5min-亮屏，亮屏后无异常*/
    @Test
    public void Test2()throws Exception{
        gDevice.pressHome();
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        Iris4GAction.waitTime(300);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        checkUIisExist();
    }
}
