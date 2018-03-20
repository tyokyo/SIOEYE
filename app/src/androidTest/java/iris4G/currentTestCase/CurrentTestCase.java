package iris4G.currentTestCase;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.action.AccountAction;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;

/**
 * Created by yun.yang on 2017/4/6.
 * 执行前手动设置
 * 1.系统语言为英语；
 * 2.灭屏时间为永不；
 * 3.插上SIM卡；
 * 4.插上SD卡；
 * 5.连接1个信号网速良好的WiFi；
 * 6.将config.properties账号信息配置文件放置在相机中
 * 7.单条测试时间配置testTime
 */
public class CurrentTestCase extends VP2 {
    Logger logger = Logger.getLogger(CurrentTestCase.class.getName());

    private int standbyTime=180;

    private String liveQuality480="480@25FPS(Bitrate0.3-2Mbps)",
            liveQuality720HD="720@25FPS(Bitrate1.3-6Mbps)";
    private String videoQuality1080P25="1080@25FPS",
            videoQuality720P60="720@60FPS",
            videoQuality720P25="720@25FPS",
            videoQuality480P120="480@120FPS",
            videoQuality480P25="480@25FPS";
    private String switchName[]={
            "Altimeter",//高度计0
            "Speedometer",//速度计1
            "Video&Live(beta)",//录播2
            "Anti-shake",//防抖3
            "Voice interaction",//语音交互4
    };
    private String videoAngle[]={
            "Medium",
            "Wide",
            "Super Wide"
    };


    @Before
    public void setup() throws Exception {
        initDevice();
        Iris4GAction.pmClear();
        Iris4GAction.startCamera();
        String useName = Constant.getUserName();
        String password = Constant.getPassword();
        //登录账号
        AccountAction.loginAccount(useName, password);
    }

    @Test
    public void testForCurrent() throws Exception {
        for (int i = 1;i<=1;i++){
            CurrenTestAction.makeScreenOn();
            gDevice.executeShellCommand("dumpsys battery set level 100");//修改电量显示
            CurrenTestAction.changeSleepTime("Never");
            CurrenTestAction.switchTo4G();
            CurrenTestAction.storageFormat();//格式化储存空间
            CurrenTestAction.closeWifi();
            CurrenTestAction.launchCamera();
            int checkSignalStrengthTime=0;
            while (checkSignalStrengthTime<12){//要求4G信号强度连续1分钟大于-79dbm
                if (CurrenTestAction.getAndShow4GSignalStrength()>-79){
                    checkSignalStrengthTime=checkSignalStrengthTime+1;
                }else {checkSignalStrengthTime=0;}
                waitTime(5);
            }
            /*
            记录4G信号强度
             */
            String fileName = "4G_SignalStrength.txt";
            File signalStrength = new File("/mnt/sdcard/" + fileName);
            signalStrength.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(signalStrength));
            CurrenTestAction.makeToasts("Start"+i,5);
            waitTime(2);
            //开始录像测试
            CameraAction.navConfig(Iris4GPage.nav_menu[1]);//Video Modem
            waitTime(2);
            //1080P25
            CurrenTestAction.configVideoQuality(videoQuality1080P25);
            CurrenTestAction.makeVideo("ON");//亮屏
            CurrenTestAction.makeVideo("OFF");//灭屏
            //720P60
            CurrenTestAction.configVideoQuality(videoQuality720P60);
            CurrenTestAction.makeVideo("OFF");
            //720P25
            CurrenTestAction.configVideoQuality(videoQuality720P25);
            CurrenTestAction.makeVideo("ON");
            CurrenTestAction.makeVideo("OFF");
            //4G相册直播720P
            Iris4GAction.stopCamera();
            waitTime(2);
            Iris4GAction.startGallery();
            CurrenTestAction.makeGalleryLive("ON",out,"YES");//亮屏、BufferedWriter、4G模式
            CurrenTestAction.makeGalleryLive("OFF",out,"YES");
            //WIFI相册直播720P
            Iris4GAction.stopGallery();
            CurrenTestAction.openWifi();
            Iris4GAction.startGallery();
            CurrenTestAction.makeGalleryLive("OFF",out,"NO");//灭屏、BufferedWriter、不是4G模式
            //480P120FPS
            Iris4GAction.stopGallery();
            CurrenTestAction.closeWifi();
            CurrenTestAction.launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[1]);//Video Modem
            waitTime(2);
            CurrenTestAction.configVideoQuality(videoQuality480P120);
            CurrenTestAction.makeVideo("OFF");
            //480P25FPS
            CurrenTestAction.configVideoQuality(videoQuality480P25);
            CurrenTestAction.makeVideo("OFF");
            //4G相册直播480P
            Iris4GAction.stopCamera();
            waitTime(2);
            Iris4GAction.startGallery();
            CurrenTestAction.makeGalleryLive("OFF",out,"YES");
            //WIFI相册直播480P
            Iris4GAction.stopGallery();
            CurrenTestAction.openWifi();
            Iris4GAction.startGallery();
            CurrenTestAction.makeGalleryLive("OFF",out,"NO");
            //慢速+延时录像
            CurrenTestAction.closeWifi();
            CurrenTestAction.launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[4]);//"Slo_Mo" Modem
            waitTime(2);
            CurrenTestAction.makeVideo("OFF");
            CameraAction.navConfig(Iris4GPage.nav_menu[5]);//"Lapse" Modem
            waitTime(2);
            CurrenTestAction.makeVideo("OFF");
            //相机预览界面亮屏
            CameraAction.navConfig(Iris4GPage.nav_menu[1]);//Video Modem
            waitTime(2);
            CurrenTestAction.configVideoQuality(videoQuality720P25);
            waitTime(standbyTime);
            //录播
            CurrenTestAction.clickSwitchForVideo(switchName[2]);//开启录播
            CurrenTestAction.makeVideo("ON");
            CurrenTestAction.makeVideo("OFF");
            CurrenTestAction.clickSwitchForVideo(switchName[2]);//关闭录播
            //主屏幕亮屏待机 10分钟
            gDevice.pressBack();
            gDevice.pressBack();
            gDevice.pressBack();
            waitTime(standbyTime);
            //4G 不保存直播
            CurrenTestAction.launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[0]);//Live Modem
            waitTime(2);
            CurrenTestAction.configVideoQuality(liveQuality480);
            CurrenTestAction.makeLive("ON",out,"YES");
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.configVideoQuality(liveQuality720HD);
            CurrenTestAction.makeLive("ON",out,"YES");
            CurrenTestAction.makeLive("OFF",out,"YES");
            //4G 保存直播
            Iris4GAction.clickLiveAndSave();//开启直播保存
            waitTime(2);
            CurrenTestAction.configVideoQuality(liveQuality480);
            CurrenTestAction.makeLive("ON",out,"YES");
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.configVideoQuality(liveQuality720HD);
            CurrenTestAction.makeLive("ON",out,"YES");
            CurrenTestAction.makeLive("OFF",out,"YES");
            Iris4GAction.clickLiveAndSave();//关闭直播保存
            waitTime(1);
            //自定义直播质量
            CurrenTestAction.configUserDefinedLiveQuality("480P","200","200");
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.configUserDefinedLiveQuality("480P","5000","5000");
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.configUserDefinedLiveQuality("720P","400","400");
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.configUserDefinedLiveQuality("720P","10000","10000");
            CurrenTestAction.makeLive("OFF",out,"YES");

            //其他设置项 高度计
            CurrenTestAction.configVideoQuality(liveQuality480);
            waitTime(2);
            CurrenTestAction.clickSwitch(switchName[0]);//开启高度计
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.clickSwitch(switchName[0]);//关闭高度计
            //速度计
            CurrenTestAction.clickSwitch(switchName[1]);//开启速度计
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.clickSwitch(switchName[1]);//关闭速度计
            //静音
            CurrenTestAction.clickLiveMute();  //开启静音开关
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.clickLiveMute();//关闭静音开关
            //防抖
            CurrenTestAction.clickSwitch(switchName[3]); //开启防抖开关
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.clickSwitch(switchName[3]);//关闭防抖开关
            //语音交互
            CurrenTestAction.clickSwitch(switchName[4]);//开启语音交互
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.clickSwitch(switchName[4]);//关闭语音交互
            //Down
            CurrenTestAction.changeUpDownTo("Down"); //修改为倒置
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.changeUpDownTo("Up");//修改为Up
            //视场角
            CurrenTestAction.configVideoAngle(videoAngle[0]);//视场角为普通
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.configVideoAngle(videoAngle[1]);//视场角为宽
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.configVideoAngle(videoAngle[2]);//视场角为超级
            CurrenTestAction.makeLive("OFF",out,"YES");
            CurrenTestAction.configVideoAngle(videoAngle[0]);//视场角为普通(默认)
            CurrenTestAction.liveWithBiggerZoom("OFF",out,"YES");

            //3G直播不保存
            gDevice.pressBack();
            gDevice.pressBack();
            CurrenTestAction.switchTo3G();
            CurrenTestAction.launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[0]);//Live Modem
            waitTime(2);
            CurrenTestAction.configVideoQuality(liveQuality480);
            CurrenTestAction.makeLive("ON",out,"ON");
            CurrenTestAction.makeLive("OFF",out,"ON");
            //3G直播保存
            Iris4GAction.clickLiveAndSave();//开启直播保存
            CurrenTestAction.makeLive("ON",out,"ON");
            CurrenTestAction.makeLive("OFF",out,"ON");
            Iris4GAction.clickLiveAndSave();//关闭直播保存
            waitTime(2);
            //WIFI不保存
            gDevice.pressBack();
            gDevice.pressBack();
            CurrenTestAction.openWifi();
            CurrenTestAction.launchCamera();
            CameraAction.navConfig(Iris4GPage.nav_menu[0]);//Live Modem
            waitTime(2);
            CurrenTestAction.configVideoQuality(liveQuality480);
            CurrenTestAction.makeLive("ON",out,"ON");
            CurrenTestAction.makeLive("OFF",out,"ON");
            CurrenTestAction.configVideoQuality(liveQuality720HD);
            CurrenTestAction.makeLive("ON",out,"ON");
            CurrenTestAction.makeLive("OFF",out,"ON");
            //WIFI保存
            Iris4GAction.clickLiveAndSave();//开启直播保存
            waitTime(1);
            CurrenTestAction.configVideoQuality(liveQuality480);
            CurrenTestAction.makeLive("ON",out,"ON");
            CurrenTestAction.makeLive("OFF",out,"ON");
            Iris4GAction.clickLiveAndSave();//关闭直播保存
            gDevice.pressBack();
            out.close();
            makeToast("end-"+i,5);
        }
        makeToast("10秒后关机......",5);
        waitTime(10);
        gDevice.executeShellCommand("reboot -p ");
    }
//    @Test
//    public void testGalleryLive() throws Exception {
//    }
}