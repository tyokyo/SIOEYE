package cn.testcase.discover;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.StaleObjectException;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.logging.Logger;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.DiscoverAction;
import cn.page.App;
import cn.page.DiscoverPage;
import cn.page.MePage;

/**
 * Created by caibing.yin on 2016/11/7.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class CountPersonCase extends VP2 {
    Logger logger = Logger.getLogger(CountPersonCase.class.getName());
    @Before
    public void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    /**
     * case10:
     *历史观看人数统计
     *1.反复进入退出视频播放界面后回到封面检查历史观看人数
     *  Result：每次进入播放视频界面后退出视频封面界面，历史观看人数加1
     * */
    @Test
    public void testCountWatchPerson() throws UiObjectNotFoundException {
        int clickBeforeNumber = DiscoverAction.getPersonNumber();
        DiscoverAction.navToPlayVideo();
        logger.info("点击观看前的人数是"+clickBeforeNumber+"人");
        Spoon.screenshot("person",""+clickBeforeNumber);
        gDevice.pressBack();
        waitTime(5);
        int clickAfterNumber =DiscoverAction.getPersonNumber();
        logger.info("点击观看后的人数"+clickAfterNumber+"人");
        Spoon.screenshot("person",""+clickAfterNumber);
        if((clickBeforeNumber+1)!=clickAfterNumber){
            String error=clickBeforeNumber+"-点击观看后的人数-"+clickAfterNumber;
            logger.info(error);
            Spoon.screenshot("fail",error);
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
        int zanBeforeNumber =DiscoverAction.getZanNumber();
        logger.info("赞前人数是"+zanBeforeNumber+"人");
        Spoon.screenshot("before_zan",""+zanBeforeNumber);
        DiscoverAction.navToPlayVideo();
        for(int times=0;times<5;times++){
            clickById(MePage.BROADCAST_VIEW_ZAN);
            waitTime(2);
        }
        waitTime(3);
        gDevice.pressBack();
        waitTime(3);
        int zanAfterNumber =DiscoverAction.getZanNumber();
        logger.info("赞后人数"+zanAfterNumber+"人");
        Spoon.screenshot("after_zan",""+zanBeforeNumber);
        if((zanBeforeNumber+5)!=zanAfterNumber){
            String error=zanBeforeNumber+"-点击5次前赞后-"+zanAfterNumber;
            logger.info(error);
            Spoon.screenshot("fail",error);
            Assert.fail("Zan_CountPersonCase_was_fail");
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
        String LocationInfo =DiscoverAction.getLocationInfo();
        logger.info(LocationInfo);
        if(LocationInfo==null){
            Spoon.screenshot(gDevice,"No_LocationInfo;");
            logger.info("testHasLocationServiceCase_fail");
        }
    }
}
