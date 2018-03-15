package iris4G.testcase;

import  android.support.test.filters.SdkSuppress;
import  android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;


import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;

import iris4G.page.Iris4GPage;
import usa.page.Constant;


/**
 * Created by ruixiangxu on 2017/12/5.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class CustomLiveBitRateCase extends VP2 {
     private  static Logger logger=Logger.getLogger(CustomLiveBitRateCase.class.getName());
     @BeforeClass
    public  static  void LoginAccount() throws Exception {
         initDevice();
         Iris4GAction.pmClear();
         Iris4GAction.startCamera();
         String username= Constant.getUserName();
         String password=Constant.getPassword();
         iris4G.action.AccountAction.loginAccount(username,password);
     }

     @Before
    public  void setup() throws Exception {
         Iris4GAction.initIris4G();
     }

    /**
     * 配置自定义直播的分辨率、帧率、和码率范围
     * @param resolution
     * @param Frame
     * @param minBitrate
     * @param maxBitrate
     * @throws Exception
     */
    private void configUserDefinedLiveQuality(String resolution,String Frame,String minBitrate,String maxBitrate) throws Exception {
        CameraAction.navConfig("Live Stream");
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("Video Quality");
        clickByText("Video Quality");
        waitTime(2);
        clickById(Iris4GPage.user_defined_setting_image_view);
        waitTime(2);
        Iris4GAction.ScrollViewByText(Iris4GPage.user_defined_scrollView,"Resolution");
        clickById(Iris4GPage.user_defined_resolution_options);
        waitTime(1);
        clickByText(resolution);
        logger.info("setting Live Resolution");
        Iris4GAction.ScrollViewByText(Iris4GPage.user_defined_scrollView,"Frame Rate");
        clickById(Iris4GPage.user_defined_frame_rate);
        waitTime(1);
        clickByText(Frame);
        logger.info("setting Custom Live Frame");
        if (resolution.equals("480P")){
            Iris4GAction.ScrollViewByText(Iris4GPage.user_defined_scrollView,"(range: 200Kbps ~ 4,000Kbps)");
        }else {
            Iris4GAction.ScrollViewByText(Iris4GPage.user_defined_scrollView,"(range: 400Kbps ~ 6,000Kbps)");
        }

        Iris4GAction.setText(Iris4GPage.user_defined_min_bitrate,minBitrate);
        Iris4GAction.setText(Iris4GPage.user_defined_max_bitrate,maxBitrate);
        waitTime(1);
        logger.info(" -configLiveBitrateRange - ");
        clickById(Iris4GPage.user_defined_sure);
        logger.info("setting Custom Live Done");
        gDevice.pressBack();
        waitTime(2);
    }

        /*
        Case 1
        配置自定义480@25（0.2-4M），并检查配置是否正确
         */
     @Test
    public  void testCustom480P() throws Exception {
         String resolution=Iris4GPage.User_defined_resolution[0];
         String Frame=Iris4GPage.User_defined_rate[0];
         String minBitrate="200";
         String maxBitrate="4000";
        configUserDefinedLiveQuality( resolution,Frame,minBitrate,maxBitrate);
         String checkLiveQuality="480@25";
         CameraAction.checkLiveVideoQualityStatus(checkLiveQuality);
     }



    /*
    Case 2
       配置自定义720@30（0.4-6.0M），并检查配置是否正确
      */
    @Test
    public  void testCustom720P() throws Exception {
        String resolution=Iris4GPage.User_defined_resolution[1];
        String Frame=Iris4GPage.User_defined_rate[1];
        String minBitrate="400";
        String maxBitrate="6000";
        configUserDefinedLiveQuality( resolution,Frame,minBitrate,maxBitrate);
        String checkLiveQuality="720@30";
        CameraAction.checkLiveVideoQualityStatus(checkLiveQuality);
    }

    /*
    Case 3
       配置自定义480@25（0.2-4.0M），并检查是否直播成功
         */
    @Test
    public  void testCustom480P30LiveSucess() throws Exception {
        String resolution=Iris4GPage.User_defined_resolution[0];
        String Frame=Iris4GPage.User_defined_rate[0];
        String minBitrate="200";
        String maxBitrate="4000";
        configUserDefinedLiveQuality( resolution,Frame,minBitrate,maxBitrate);
        logger.info("setting userDefined 480@25(0.2-4.0M)");
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        logger.info("start live...");
        CameraAction.checkLiveStatus(1);
        if (!CameraAction.checkLiveSuccess()){
            Assert.fail("start live failed");
        }
        logger.info("start live success");
        waitTime(10);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(2);
        logger.info("stop live success");
        Spoon.screenshot("stopLiveSuccess");
    }

    /*
    Case 4
     配置自定义7200@30（0.4-6.0M），并检查是否直播成功
     */
    @Test
    public  void testCustom720P30LiveSucess() throws Exception {
        String resolution=Iris4GPage.User_defined_resolution[1];
        String Frame=Iris4GPage.User_defined_rate[1];
        String minBitrate="400";
        String maxBitrate="6000";
        configUserDefinedLiveQuality( resolution,Frame,minBitrate,maxBitrate);
        logger.info("setting userDefined 480@30(0.4-6.0M)");
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        logger.info("start live...");
        CameraAction.checkLiveStatus(1);
        if (!CameraAction.checkLiveSuccess()){
            Assert.fail("start live failed");

        }
        logger.info("start live success");
        waitTime(10);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(2);
        logger.info("stop live success");
        Spoon.screenshot("stopLiveSuccess");

    }
    /*
    Case 5
     配置自定义720@25,固定码率（2.0M），并检查是否直播成功
     */
    @Test
    public  void testCustomFixedBitRate() throws Exception {
        String resolution=Iris4GPage.User_defined_resolution[1];
        String Frame=Iris4GPage.User_defined_rate[0];
        String minBitrate="2000";
        String maxBitrate="2000";
        configUserDefinedLiveQuality( resolution,Frame,minBitrate,maxBitrate);
        String checkLiveQuality="720@25";
        CameraAction.checkLiveVideoQualityStatus(checkLiveQuality);
        logger.info("setting userDefined 720@25(2.0M)");
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        CameraAction.checkLiveStatus(1);
        if (!CameraAction.checkLiveSuccess()){
            Assert.fail("start live failed");

        }
        waitTime(10);
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        waitTime(2);
        logger.info("stop live success");
        Spoon.screenshot("stopLiveSuccess");

    }

    /*
    Case 6
     输入自定义的码率大小值相反，检查能否设置成功
     */
    @Test
    public  void testCustomInputBigSmallReverse() throws Exception {
        String resolution=Iris4GPage.User_defined_resolution[1];
        String Frame=Iris4GPage.User_defined_rate[0];
        String minBitrate="900";
        String maxBitrate="500";
        configUserDefinedLiveQuality( resolution,Frame,minBitrate,maxBitrate);
       gDevice.pressBack();
        logger.info("setting big and small reverse");
        CameraAction.cameraSetting();
        waitTime(1);
        Iris4GAction.ScrollViewByText("Video Quality");
        String activeBitrate=Iris4GAction.getRightValue("Video Quality");
        Spoon.screenshot("InputBitrateBigAndSmallReverse",activeBitrate);
        String expect1="720@25FPS (0.5-0.9Mbps)";
        Asst.assertEquals("user_defined_Bitrate",expect1,activeBitrate);
        clickByText("Video Quality");
        String userDefined="User Defined(720@25FPS Bitrate0.5-0.9Mbps)";
        if (!CameraAction.hasObjectSelected(userDefined)){
            Assert.fail("CustomInputBigSmallReverse Failed");
        }

    }

    //    @Test
//    public  void testCustomBoundaryValue(){
//
//
//    }
//
//    @Test
//    public  void testCustomInputIllegalChar(){
//
//    }
//
//    @Test
//    public  void testCustomInputLongNumber(){
//
//    }



}
