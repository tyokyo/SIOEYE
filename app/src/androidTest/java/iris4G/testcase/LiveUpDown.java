package iris4G.testcase;

import android.os.RemoteException;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;

/**
 * @Author yun.yang
 * @Description
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class LiveUpDown extends VP2 {
    Logger logger = Logger.getLogger(LiveUpDown.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    public void test() throws Exception {
        if(!gDevice.isScreenOn()){
            gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
            logger.info("make screen on");
        }
        CameraAction.cameraLive();
        int jd1 = gDevice.getDisplayRotation();
        if(jd1 == 1){
            CameraAction.cameraSetting();
            Iris4GAction.ScrollViewByText("Up/Down/Auto");
            clickByText("Up/Down/Auto");
            clickByText("Down");
            int jd2 = gDevice.getDisplayRotation();
            if(jd2 == 3){
                //Up设置为Down成功
                logger.info("Up-Down");
                //更改某些不能够初始化的操纵后,应该还原，以便用例继续执行，以下是还原刚才的步骤
                clickByText("Up/Down/Auto");
                clickByText("Up");
                int jd3 = gDevice.getDisplayRotation();
                if(jd3 == 1){
                    logger.info("re-set success");
                }else{
                    logger.info("re-set fail");
                }
            }else{
                logger.info("Up-Down fail");
                Asst.fail("Up-Down fail");
            }
        }else{
            //初始条件不为Up
            Asst.fail("initialize value is not Up");
        }
    }
}
