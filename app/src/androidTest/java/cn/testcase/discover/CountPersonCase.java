package cn.testcase.discover;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import bean.VideoBean;
import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.DiscoverAction;
import cn.action.MainAction;
import cn.page.App;
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
    @SanityTest
    @PerformanceTest
    public void testCountWatchPerson() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        DiscoverAction.scrollVideoList();
        //1.滑动视频列表 2.播放视频(true)
        VideoBean before_playback=DiscoverAction.playBackVideo(true);
        logger.info(before_playback.toString());
        int clickBeforeNumber =before_playback.getLike();
        logger.info("点击观看前"+before_playback.toString());
        Spoon.screenshot("person",""+clickBeforeNumber);
        gDevice.pressBack();
        waitTime(5);
        VideoBean after_playback=DiscoverAction.playBackVideo(false);
        logger.info("点击观看后"+after_playback.toString());
        logger.info(after_playback.toString());
        int clickAfterNumber =after_playback.getLike();

        Spoon.screenshot("person",""+clickAfterNumber);
        if (clickBeforeNumber>1000){

        }else{
            if((clickBeforeNumber+1)!=clickAfterNumber){
                String error=clickBeforeNumber+"-点击观看后的人数-"+clickAfterNumber;
                logger.info(error);
                Spoon.screenshot("fail",error);
                Asst.fail(clickBeforeNumber+"|"+clickAfterNumber);
            }
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
    @SanityTest
    @PerformanceTest
    public void testCountZanPerson() throws UiObjectNotFoundException{
        MainAction.navToDiscover();
        DiscoverAction.scrollVideoList();
        //1.滑动视频列表 2.播放视频(true)
        VideoBean before_playback=DiscoverAction.playBackVideo(true);
        logger.info(before_playback.toString());
        int clickBeforeZan =before_playback.getZan();
        logger.info("点击Zan前"+before_playback.toString());
        Spoon.screenshot("Zan",""+clickBeforeZan);

        //now zan operation
        for(int times=0;times<5;times++){
            clickById(MePage.BROADCAST_VIEW_ZAN);
            waitTime(2);
        }
        gDevice.pressBack();
        waitTime(2);

        VideoBean after_playback=DiscoverAction.playBackVideo(false);
        logger.info("点击Zan后"+after_playback.toString());
        int clickAfterZan =after_playback.getZan();

        logger.info("5赞后人数"+clickAfterZan+"人");
        Spoon.screenshot("after_zan",""+clickAfterZan);
        if (clickAfterZan>1000){

        }else{
            if((clickBeforeZan+5)!=clickAfterZan){
                String error=clickBeforeZan+"-点击5次前赞后-"+clickAfterZan;
                logger.info(error);
                Spoon.screenshot("fail",error);
                Asst.fail(clickBeforeZan+"|"+clickAfterZan);
            }
        }
    }
    /**case14:
     *位置信息显示
     *1.检查位置信息显示字体颜色尺寸长度位置
     *   Result：与给出的UI设计保持一致
     *备注：只能检查是否有位置信息
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testHasLocationService() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        boolean findVideoHasLocation=false;
        //进行10轮查找
        for (int i = 0; i <10 ; i++) {
            //获取当前界面的视频的位置信息
            VideoBean videoInfo=DiscoverAction.playBackVideo(false);
            //位置信息不为空
            if (!"".equals(videoInfo.getAddress())){
                findVideoHasLocation=true;
                break;
            }else {
                //继续滑动查找
                DiscoverAction.scrollVideoList();
            }
        }
        //验证是否存在位置信息
        Asst.assertEquals("10轮查找-findLocationInVideo",true,findVideoHasLocation);
    }
}
