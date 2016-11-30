package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;

import static iris4G.page.Iris4GPage.lapse_time;
import static iris4G.page.Iris4GPage.video_Angle;
import static iris4G.page.Iris4GPage.video_quality;

/**
 * @Caibing
 * @随机执行Lapse的设置里面所有的操作,然后切换为Video，再切换回Lapse时查看之前的设置是否保存成功
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class ExchangeSetting_ReturnToLapse extends VP2{
    Logger logger = Logger.getLogger(ExchangeSetting_ReturnToLapse.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }

    public String getRightValue(String text) throws UiObjectNotFoundException {
        String value="";
        UiCollection videos = new UiCollection(
                new UiSelector().className("android.widget.ScrollView"));
        int count = videos.getChildCount(new UiSelector()
                .className("android.widget.RelativeLayout"));
        for (int instance = 0; instance < count; instance++) {
            UiObject uiObject = videos.getChildByInstance(
                    new UiSelector().className("android.widget.RelativeLayout"),
                    instance);
            UiObject sObject = uiObject.getChild(new UiSelector().className("android.widget.TextView"));
            if (uiObject.exists() && uiObject.isEnabled() == true&& sObject.exists()) {
                if (sObject.getText().equals(text)) {
                    UiObject dataObj = uiObject.getChild(new UiSelector().className("android.widget.TextView").index(1));
                    value=dataObj.getText();
                }
            }
        }
        return value;
    }
    public boolean isExistVideoQuality(String vquality){
        boolean isexist =false;

        return isexist;
    }
    @Test
    public void  testExchangeSetting_ReturnToLapse() throws Exception  {
        logger.info("*****Start to run testExchangeSetting_ReturnToLapseCase *****");
        CameraAction.navConfig(Iris4GPage.nav_menu[5]);

        for(int i=1;i<20;i++){
            logger.info("第"+i+"次循环开始");
            int lapsesize = lapse_time.length;
            int anglesize = video_Angle.length;
            int qualitysize = video_quality.length;
            int l =(int)(Math.random()*(lapsesize-1));
            int a =(int)(Math.random()*(anglesize-1));
            int q =(int)(Math.random()*(qualitysize-1));

            //value to set
            String expect_lapse_quality =video_quality[q];
            String expect_lapse_angle = video_Angle[a];
            String expect_lapse_lapsetime = lapse_time[l];
            //随即的视频质量当前机器是否支持,如果不支持，跳过
            if (CameraAction.isExistVideoQuality(expect_lapse_quality)) {
                //action
                CameraAction.configVideoQuality(5,expect_lapse_quality);
                CameraAction.configVideoAngle(5,expect_lapse_angle);
                CameraAction.configTimeLapse(5,expect_lapse_lapsetime);

                CameraAction.navConfig(Iris4GPage.nav_menu[5]);
                CameraAction.cameraSetting();

                //value get
                String active_lapse_lapsetime = getRightValue("Time Lapse");
                String active_lapse_angle = getRightValue("Video Angle");
                String active_lapse_quality = getRightValue("Video Quality");

                logger.info("quality|"+expect_lapse_quality+"|"+active_lapse_quality);
                logger.info("angle|"+expect_lapse_angle+"|"+active_lapse_angle);
                logger.info("lapsetime|"+expect_lapse_lapsetime+"|"+active_lapse_lapsetime);

                if (!expect_lapse_lapsetime.equals(active_lapse_lapsetime)){
                    logger.info("Time Lapse和之前设置的不一样，不通过");
                    logger.info("expect is:"+"["+expect_lapse_lapsetime+"]");
                    logger.info("active is:"+"["+active_lapse_lapsetime+"]");
                    logger.info("testlapse_lapsetime_ReturnToLapseCaseCase_fail");
                    break;
                }else {
                    if (!expect_lapse_angle.equals(active_lapse_angle)){
                        logger.info("Video Angle和之前设置的不一样，不通过");
                        logger.info("expect is:"+"["+expect_lapse_angle+"]");
                        logger.info("active is:"+"["+active_lapse_angle+"]");
                        logger.info("testlapse_angle_ReturnToLapseCase_fail");
                        break;
                    }else {
                        if (!expect_lapse_quality.equals(active_lapse_quality)){
                            logger.info("Video Quality和之前设置的不一样，不通过");
                            logger.info("expect is:"+"["+expect_lapse_quality+"]");
                            logger.info("active is:"+"["+active_lapse_quality+"]");
                            logger.info("testlapse_quality_ReturnToLapseCase_fail");
                            break;
                        }
                    }
                }
                logger.info("第"+i+"次循环结束");
            }
            //no exception
            logger.info("testExchangeSetting_ReturnToLapseCase_PASS");
            logger.info( "*****End to run testExchangeSetting_ReturnToLapseCase*****");
        }
    }
}
