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
import iris4G.page.NavPage;

import static iris4G.page.Iris4GPage.lapse_time;
import static iris4G.page.Iris4GPage.video_Angle;
import static iris4G.page.Iris4GPage.video_quality;

/**
 * @Caibing
 * @随机执行Lapse的设置里面所有的操作,然后切换为Video，再切换回Lapse时查看之前的设置是否保存成功
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class ExchangeSetting_ReturnToLapse extends VP2 {
    Logger logger = Logger.getLogger(ExchangeSetting_ReturnToLapse.class.getName());

    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    //获取随机的视频质量
    private String randomVideoQuality() {
        int qualityLength = video_quality.length;
        int qualityIndex = (int) (Math.random() * (qualityLength - 1));
        return video_quality[qualityIndex];
    }

    //获取随机的视频角度
    private String randomVideoAngle() {
        int angleLength = video_Angle.length;
        int angleIndex = (int) (Math.random() * (angleLength - 1));
        return video_Angle[angleIndex];
    }

    //获取随机的延时时间
    private String randomLapseSize() {
        int lapseLength = lapse_time.length;
        int lapseLengthIndex = (int) (Math.random() * (lapseLength - 1));
        return lapse_time[lapseLengthIndex];
    }

    @Test
    public void testExchangeSetting_ReturnToLapse() throws Exception {
        CameraAction.navConfig(NavPage.navConfig_Lapse);
        //总共测试3次
        for (int iteration = 1; iteration <=3; iteration++) {
            String quality=randomVideoQuality();
            String angle=randomVideoAngle();
            String lapse_time=randomLapseSize();
            logger.info("[iteration] - " + iteration);
            //配置随机的视频质量 角度 延时时间
            CameraAction.configVideoQuality(NavPage.navConfig_Lapse,quality );
            CameraAction.configVideoAngle(NavPage.navConfig_Lapse, angle);
            CameraAction.configTimeLapse(NavPage.navConfig_Lapse,lapse_time);
            //切换到Video
            CameraAction.navConfig(NavPage.navConfig_Video);
            //切换回Lapse
            CameraAction.navConfig(NavPage.navConfig_Lapse);
            //验证设置
            CameraAction.checkVideoQuality(NavPage.navConfig_Lapse, quality);
            CameraAction.checkVideoAngle(NavPage.navConfig_Lapse, angle);
            CameraAction.checkLapseTime(NavPage.navConfig_Lapse, lapse_time);
        }
    }
}
