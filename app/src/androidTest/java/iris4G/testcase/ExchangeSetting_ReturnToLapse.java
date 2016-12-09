package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import org.hamcrest.Asst;
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
    private String navConfig_LiveStream=Iris4GPage.nav_menu[0];
    private String navConfig_Video=Iris4GPage.nav_menu[1];
    private String navConfig_Capture=Iris4GPage.nav_menu[2];
    private String navConfig_Burst=Iris4GPage.nav_menu[3];
    private String navConfig_Slo_Mo=Iris4GPage.nav_menu[4];
    private String navConfig_Lapse=Iris4GPage.nav_menu[5];

    Logger logger = Logger.getLogger(ExchangeSetting_ReturnToLapse.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
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
            logger.info("iteration-"+i);
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
                String nacConfig =Iris4GPage.nav_menu[5];
                CameraAction.configVideoQuality(nacConfig,expect_lapse_quality);
                CameraAction.configVideoAngle(nacConfig,expect_lapse_angle);
                CameraAction.configTimeLapse(nacConfig,expect_lapse_lapsetime);

                CameraAction.navConfig(nacConfig);
                CameraAction.cameraSetting();

                //value get
                String active_lapse_lapsetime = Iris4GAction.getRightValue("Time Lapse");
                String active_lapse_angle = Iris4GAction.getRightValue("Video Angle");
                String active_lapse_quality = Iris4GAction.getRightValue("Video Quality");

                logger.info("quality|"+expect_lapse_quality+"|"+active_lapse_quality);
                logger.info("angle|"+expect_lapse_angle+"|"+active_lapse_angle);
                logger.info("lapsetime|"+expect_lapse_lapsetime+"|"+active_lapse_lapsetime);

                if (!expect_lapse_lapsetime.equals(active_lapse_lapsetime)){
                    logger.info(String.format("expect|active-[%s | %s]",
                            expect_lapse_lapsetime,active_lapse_lapsetime));
                    logger.info("testlapse_lapsetime_ReturnToLapseCaseCase_fail");
                    Asst.fail(String.format("%s|%s",expect_lapse_lapsetime,active_lapse_lapsetime));
                    break;
                }else {
                    if (!expect_lapse_angle.equals(active_lapse_angle)){
                        logger.info("Video Angle error");
                        logger.info("expect is:"+"["+expect_lapse_angle+"]");
                        logger.info("active is:"+"["+active_lapse_angle+"]");
                        logger.info("testlapse_angle_ReturnToLapseCase_fail");
                        Asst.fail();
                        break;
                    }else {
                        if (!expect_lapse_quality.equals(active_lapse_quality)){
                            logger.info("Video Quality error");
                            logger.info("expect is:"+"["+expect_lapse_quality+"]");
                            logger.info("active is:"+"["+active_lapse_quality+"]");
                            logger.info("testlapse_quality_ReturnToLapseCase_fail");
                            Asst.fail();
                            break;
                        }
                    }
                }
                logger.info("end to iteration- "+i);
            }
            //no exception
            logger.info("testExchangeSetting_ReturnToLapseCase_PASS");
            logger.info( "*****End to run testExchangeSetting_ReturnToLapseCase*****");
        }
    }
}
