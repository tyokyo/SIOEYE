package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.uiautomator.UiObject;
import android.view.KeyEvent;

import org.hamcrest.Asst;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;
import iris4G.page.NavPage;
import usa.page.Account;

/**
 * Created by jiali.liu on 2016/12/10.
 */
@RunWith(JUnit4.class)
@SdkSuppress(minSdkVersion = 16)

public class LocationCase extends VP2{
    @BeforeClass
    public static void initConfig(){
        initDevice();
        Iris4GAction.pmClear();
    }
    @AfterClass
    public static void clearConfig(){
        initDevice();
        Iris4GAction.pmClear();
    }
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    /**Case 1:
     * 打开直播Location功能：
     * 1.清除Camera缓存数据，即包含的设置配置数据，
     * 2.进入相机location开关默认打开,多次开启或关闭location*/
    @Test
    public void testOpenLocation() throws Exception {
        initDevice();
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        CameraAction.navToLocation();
        for(int i=1;i<=5;i++){
            CameraAction.openCompoundButton("Live&Location");
            System.out.println("i:"+i);
            if (i%2==1){
                logger.info("Location service is closed");
            }else{
                logger.info("Location service is opened");
            }
            waitTime(1);
            gDevice.pressBack();
            CameraAction.navToLocation();
        }
    }
    /**Case 2:
     * 开启location后，进行直播:
     * 1.打开Live&Location开关
     * 2.判断是否已登录账号
     * 3.发起直播
     * */
    @Test
    public void testLiveAndLocation() throws Exception {
        //Loation默认打开，添加Location 开关打开验证方法
        initDevice();
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        if (AccountAction.isLogin()){
            Iris4GAction.cameraKey();
        }else {
            CameraAction.navToAccount();
            String username= Constant.getUserName("email");
            String password= Constant.getPassword("email_password");
            AccountAction.loginAccount(username,password);
            Iris4GAction.cameraKey();
            if (!CameraAction.checkLiveSuccess()){
                Asst.assertEquals("Live is success",true,CameraAction.checkLiveSuccess());
            }else{
                logger.info("Live is success");
            }
            waitTime(20);
            Iris4GAction.cameraKey();
        }
    }
    /**Case 4:
     * 关闭location发起直播
     */
    @Test
    public void testLiveAndCloseLocation() throws Exception {
        //Loation默认打开，添加Location 关闭状态验证方法
        initDevice();
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        CameraAction.navToLocation();
        //关闭location开关
        CameraAction.openCompoundButton("Live&Location");
        if (AccountAction.isLogin()){
            Iris4GAction.cameraKey();
        }else {
            CameraAction.navToAccount();
            String username= Constant.getUserName("email");
            String password= Constant.getPassword("email_password");
            AccountAction.loginAccount(username,password);
            Iris4GAction.cameraKey();
            if (!CameraAction.checkLiveSuccess()){
                Asst.assertEquals("Live is success",true,CameraAction.checkLiveSuccess());
            }else{
                logger.info("Live is success");
            }
            waitTime(20);
            Iris4GAction.cameraKey();
        }
    }
}
