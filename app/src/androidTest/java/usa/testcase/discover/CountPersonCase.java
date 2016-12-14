package usa.testcase.discover;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;
import com.squareup.spoon.Spoon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.logging.Logger;
import ckt.base.VP2;
import usa.action.DiscoverAction;
import usa.page.App;
import usa.page.Discover;
import usa.testcase.me.ActivityCase;

import static usa.action.DiscoverAction.getLocationInfo;
import static usa.action.DiscoverAction.getPersonNumber;
import static usa.action.DiscoverAction.getZanNumber;


/**
 * Created by caibing.yin on 2016/11/7.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class CountPersonCase extends VP2 {
    Logger logger = Logger.getLogger(ActivityCase.class.getName());
    @Before
    public void setup() {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    /**
     * case10:
     *历史观看人数统计
     *1.反复进入退出视频播放界面后回到封面检查历史观看人数
     *  Result：每次进入播放视频界面后退出视频封面界面，历史观看人数加1
     * */
    @Test
    public void testCountWatchPerson() throws UiObjectNotFoundException {
        String ClickBeforeNumber =DiscoverAction.getPersonNumber();
        DiscoverAction.navtoVideo();
        logger.info("点击观看前的人数是"+ClickBeforeNumber+"人");
        Spoon.screenshot(gDevice,ClickBeforeNumber);
        gDevice.pressBack();
        waitTime(8);
        String ClickAfterNumber =getPersonNumber();
        logger.info("点击观看后的人数"+ClickAfterNumber+"人");
        Spoon.screenshot(gDevice,ClickAfterNumber);
        Boolean Result = (Integer.parseInt(ClickAfterNumber)==(Integer.parseInt(ClickBeforeNumber))+1);
        if(!Result){
            Spoon.screenshot(gDevice,"testCountWatchPersonCase_was_fail");
            logger.info("点击观看前后人数不一致");
            Assert.fail("testCountWatchPersonCase_was_fail");
        }
    }
    /**
     * case12:
     *多次点赞后，点赞数统计
     *1.对同一视频用不同账户进行点赞后，返回视频封面检查点赞总数
     *  Result：点赞数目增加与操作的点赞次数对应
     *备注：用不同账户暂无法实现，暂时只能用本账户的点赞；
     * */
    @Test
    public void testCountZanPerson() throws UiObjectNotFoundException{
        String ZanBeforeNumber =getZanNumber();
        logger.info("赞前人数是"+ZanBeforeNumber+"人");
        Spoon.screenshot(gDevice,ZanBeforeNumber);
        DiscoverAction.navtoVideo();
        for(int times=0;times<5;times++){
            clickById(Discover.ID_Zan_Icon);
            waitTime(2);
        }
        waitTime(10);
        gDevice.pressBack();
        waitTime(8);
        String ZanAfterNumber =getZanNumber();
        logger.info("赞后人数"+ZanAfterNumber+"人");
        Spoon.screenshot(gDevice,ZanAfterNumber);
        Boolean Result = (Integer.parseInt(ZanAfterNumber)==(Integer.parseInt(ZanBeforeNumber))+5);
        if(!Result){
            Spoon.screenshot(gDevice,"testCountZanPerson_was_fail");
            logger.info("点击前赞的人数不一致");
            //Assert.fail("Zan_CountPersonCase_was_fail");
        }
    }

    /**case14:
     *位置信息显示
     *1.检查位置信息显示字体颜色尺寸长度位置
     *   Result：与给出的UI设计保持一致
     *备注：只能检查是否有位置信息
     */
    @Test
    public void testHasLocationService(){
        String LocationInfo =getLocationInfo();
        logger.info(LocationInfo);
        if(LocationInfo==null){
            Spoon.screenshot(gDevice,"No_LocationInfo;");
            logger.info("testHasLocationServiceCase_fail");
        }
    }



}
