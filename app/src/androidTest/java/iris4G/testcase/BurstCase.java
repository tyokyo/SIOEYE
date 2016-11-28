package iris4G.testcase;

import android.os.Environment;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.HashSet;
import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;

/**
 * @Author elon
 * @Description
 */
@RunWith(AndroidJUnit4.class)
/*连拍测试*/
@SdkSuppress(minSdkVersion = 16)
public class BurstCase extends VP2{
    Logger logger = Logger.getLogger(BurstCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    private void Burst(String imageSize,String burstSetting,double expectWH) throws Exception {
        //"4M(16:9)",
        CameraAction.configImageSize(imageSize);
        //"10P",
        CameraAction.configBurst(burstSetting);
        HashSet<String> beforeTakeVideoList = Iris4GAction.FileList("/sdcard/Photo");
        Iris4GAction.cameraKey();
        waitTime(10);
        HashSet<String> afterTakeVideoList = Iris4GAction.FileList("/sdcard/Photo");
        HashSet<String> resultHashSet = Iris4GAction.result(afterTakeVideoList, beforeTakeVideoList);
        int expect_picture_burst_size=Integer.parseInt(burstSetting.replace("P",""));
        getObject2ById(Iris4GPage.camera_setting_shortcut_id);
        int active_picture_count = resultHashSet.size();
        if (active_picture_count!=expect_picture_burst_size) {
            logger.info(String.format("expect图片总数：%s张-实际图片数量为：%s",
                    expect_picture_burst_size,active_picture_count));
        }else {
            logger.info(String.format("expect图片总数：%s张-Success",expect_picture_burst_size));
            for (String photoPath : resultHashSet) {
                double hw = Iris4GAction.getPicHeightWidth(photoPath);
                if (hw==expectWH) {
                    logger.info(photoPath+" -图片比列验证成功");
                }else {
                    logger.info(photoPath+" -图片比列验证失败");
                    logger.info("expect is "+expectWH);
                    logger.info("active is "+hw);
                    String message=String.format("failed");
                    Asst.fail();
                }
            }
        }
    }
    /* burst  - " "4M(16:9)","3M(4:3)","2M(16:9)";
       image  -   "480@30FPS","480@60FPS","480@120FPS",
                  "720@30FPS","720@60FPS","1080@30FPS";
        * */
    @Test
    public void testBurst10P4M169() throws Exception {
        Burst(Iris4GPage.imsge_size[0],Iris4GPage.burst[0],16/9);
    }
    @Test
    public void testBurst20P4M169() throws Exception {
        Burst(Iris4GPage.imsge_size[0],Iris4GPage.burst[1],16/9);
    }
    /*

    testBurst30P4M169()
    testBurst10P3M43()
    testBurst20P3M43()
    testBurst30P3M43()
    testBurst10P2M169()
    testBurst20P2M169()
    testBurst30P2M169()*/
}
