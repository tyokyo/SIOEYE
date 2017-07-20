package cn.testcase.me;

import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObjectNotFoundException;
import org.junit.Before;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.page.App;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.spoon.Spoon;
import org.hamcrest.Asst;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import cn.action.MeAction;
import cn.action.SettingAction;
import cn.page.MePage;
import android.support.test.uiautomator.UiObject2;

/**
 * Created by chendaofa on 2017/07/18.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)

public class CameraCase  extends VP2 {
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageNameInLogin(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    /**
     * 跳转添加相机
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testToAddCamera() throws UiObjectNotFoundException {
        MeAction.navToCamera();
        waitTime(3);
        String active_name1=getObjectById(MePage.CAMERA_LEARN_DEVICE).getText();
        if("Learn about Sioeye device".equals(active_name1)){
            clickById(MePage.CAMERA_ADD_DEVICE);
            waitTime(3);
            String active_name2=getObjectById(MePage.CAMERA_ADD_DEVICE_TAG).getText();
            String expect_name2="IRIS4G V3";
            Asst.assertEquals("是否跳转到添加相机界面", expect_name2, active_name2);
            Spoon.screenshot("testToAddCamera");
            }

        }
    /**
     * 跳转扫码界面
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testToConnectDevice() throws UiObjectNotFoundException {
        MeAction.navToCamera();

        clickById(MePage.CAMERA_ADD_DEVICE);
        Spoon.screenshot("testToConnectDevice");
        waitTime(3);
        clickById(MePage.CAMERA_BTN_ADD_DEVICE);
        String active_name2=getObjectById(MePage.CAMERA_CONNECT_DEVICES).getText();
        String expect_name2="Connect Device";
        Asst.assertEquals("是否跳转到扫码相机界面", expect_name2, active_name2);
        Spoon.screenshot("testToConnectDevice");
        gDevice.pressBack();
    }
    /**
     * 点击按钮滑动相机
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testToClickSwipeDevice() throws UiObjectNotFoundException {
        MeAction.navToCamera();
        clickById(MePage.CAMERA_ADD_DEVICE);
        Spoon.screenshot("testToSwipeDevice");
        waitTime(3);
        clickById(MePage.CAMERA_INACTIVE);
        String active_name2=getObjectById(MePage.CAMERA_ADD_DEVICE_TAG).getText();
        String expect_name2="IRIS4G Blink";
        Asst.assertEquals("是否跳转到扫码相机界面", expect_name2, active_name2);
        Spoon.screenshot("testToSwipeDevice");
        clickById(MePage.CAMERA_INACTIVE);
        String active_name3=getObjectById(MePage.CAMERA_ADD_DEVICE_TAG).getText();
        String expect_name3="IRIS4G V3";
        Asst.assertEquals("是否跳转到扫码相机界面", expect_name3, active_name3);
        Spoon.screenshot("testToSwipeDevice");
        gDevice.pressBack();
    }
    /**
     * 滑动相机
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testToSwipeDevice1() throws UiObjectNotFoundException {
        MeAction.navToCamera();
        clickById(MePage.CAMERA_ADD_DEVICE);
        Spoon.screenshot("testToSwipeDevice");
        waitTime(3);
        UiObject2 swipe_target = getObject2ById(MePage.CAMERA_DEVICE_IMAGE);
        //clickById(MePage.CAMERA_INACTIVE);
        swipe_target.swipe(Direction.LEFT,0.85f,2000);


        String active_name2=getObjectById(MePage.CAMERA_ADD_DEVICE_TAG).getText();
        String expect_name2="IRIS4G Blink";
        Asst.assertEquals("是否滑动相机", expect_name2, active_name2);
        Spoon.screenshot("testToSwipeDevice");
        waitTime(3);
        UiObject2 swipe_target2 = getObject2ById(MePage.CAMERA_DEVICE_IMAGE_BLINK);

        swipe_target2.swipe(Direction.RIGHT,0.85f,2000);
        String active_name3=getObjectById(MePage.CAMERA_ADD_DEVICE_TAG).getText();
        String expect_name3="IRIS4G V3";
        Asst.assertEquals("是否滑动相机", expect_name3, active_name3);
        Spoon.screenshot("testToSwipeDevice");
        gDevice.pressBack();
    }


    }

