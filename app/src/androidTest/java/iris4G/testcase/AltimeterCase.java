package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;

/**
 * Created by admin on 2016/12/14.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class AltimeterCase extends VP2{
    private static Logger logger = Logger.getLogger(BurstCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    @Test
    /**Case 1:
     * 步骤：
     * 1.进入相机设置中开启Altimeter，关闭Altimeter5次
     * 2.重新进入相机检查Altimeter开关状态
     * 期望：
     * 1.能够成功开启Altimeter
     */
    public void testOpenAltimeterCase() throws Exception {
        CameraAction.initDevice();
        Iris4GAction.pmClear();
        Iris4GAction.initIris4G();
        CameraAction.navToAltimeter();
        for(int i=1;i<=5;i++){
            CameraAction.openCompoundButton("Altimeter");
            System.out.println("i:"+i);
            if (i%2==0){
                logger.info("Altimeter service is closed");
                Spoon.screenshot("Altimeter_close");
            }else{
                logger.info("Altimeter service is opened");
                Spoon.screenshot("Altimeter_on");
            }
            waitTime(1);
            gDevice.pressBack();
            CameraAction.navToAltimeter();
        }
        gDevice.pressBack();
        gDevice.pressBack();
        waitTime(2);
        Iris4GAction.startCamera();
        CameraAction.navToAltimeter();
        //添加验证开关是否打开的验证方法
    }
    @Test
    /**
     * Case 2:
     * 打开高度计开关后发起直播
     */
    public void testOpenAltimeterAndLive() throws Exception {
        //清除缓存数据
        CameraAction.initDevice();
        Iris4GAction.pmClear();
        Iris4GAction.initIris4G();
        CameraAction.navToAltimeter();
        CameraAction.openCompoundButton("Altimeter");
        //添加验证开关是否打开的方法
        //登录直播账号
        String username= Constant.getUserName("email");
        String password= Constant.getPassword("email_password");
        AccountAction.loginAccount(username,password);
        Iris4GAction.cameraKey();
        Asst.assertEquals("Live_Succeed",true,CameraAction.checkLiveSuccess());
        waitTime(30);
        Iris4GAction.cameraKey();
    }
    @Test
    /**
     * Case 3:
     * 关闭高度计后发起直播
     */
    public void testCloseAltimeterAndLive() throws Exception {
        //清除缓存数据
        CameraAction.initDevice();
        Iris4GAction.pmClear();
        Iris4GAction.initIris4G();
        CameraAction.cameraLive();
        //添加验证开关是否打开的方法
        //登录直播账号
        String username= Constant.getUserName("email");
        String password= Constant.getPassword("email_password");
        AccountAction.loginAccount(username,password);
        Iris4GAction.cameraKey();
        Asst.assertEquals("Live_Succeed",true,CameraAction.checkLiveSuccess());
        waitTime(30);
        Iris4GAction.cameraKey();
    }

}
