package cn.testcase.watch;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.util.logging.Logger;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.BroadcastAction;
import cn.action.WatchAction;
import cn.page.App;
import cn.page.MePage;
import cn.page.WatchPage;

/**
 * Created by elon on 2016/11/14.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class WatchViewCase extends VP2 {
    private Logger logger = Logger.getLogger(WatchViewCase.class.getName());
    @Before
    public void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    /**
     * case1.播放关注列表的视频文件
     * */
    @Test
    @SanityTest
    @PerformanceTest
    public void testViewWatchVideo() throws UiObjectNotFoundException, ParseException {
        //进入Watch
        WatchAction.navToWatch();
        waitUntilFind(WatchPage.WATCH_LIST,10000);
        getObjectById(WatchPage.WATCH_LIST).swipeUp(2);
        waitTime(3);
        try {
            UiObject2 timeObj=WatchAction.getDurationObjects().get(0);
            String dateStr=timeObj.getText();
            timeObj.click();
            BroadcastAction.waitBroadcastLoading();
            //Asst.assertTrue("time out 60 seconds.",!getObjectById(MePage.BROADCAST_VIEW_VIDEO_LOADING).exists());
            //click play screen center
            waitTime(dateInSeconds(dateStr));
            Spoon.screenshot("play_video");
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    /**
     * case2.点击好友头像，在弹出框中取消关注好友
     * 1、把自己的昵称与点击头像主播昵称对比
     * 2、不为同一个人则取消关注，相同则截图
     * creat by yajuan 2017.8.17
     **/
    @Test
    @SanityTest
    @PerformanceTest
    public void testCancelFollow() throws UiObjectNotFoundException{
        String nick_name= getObject2ById(MePage.WATCH_USER_MINI_NAME).getText();
        WatchAction.navToWatch();
        WatchAction.ClickImageObjects();
        String profile_name= getObject2ById(WatchPage.WATCH_USER_MINI_NAME).getText();
        int follower_before=0;
        int follower_after=0;
        if (!nick_name.equals(profile_name)){
            follower_before=WatchAction.getFollower();
            Spoon.screenshot("Following");
            WatchAction.unFollow();
            follower_after=WatchAction.getFollower();
            Spoon.screenshot("Follow");
            if(follower_after==follower_before-1){
                Asst.assertTrue(true);
            }else{
                WatchAction.unFollow();
            }
            Spoon.screenshot("Following");
            gDevice.pressBack();
            getObjectById(WatchPage.WATCH_LIST).swipeDown(50);
            Spoon.screenshot("watch");
        }
        else{
            Spoon.screenshot(nick_name);
        }

    }
    /**
     * case3.点击关注好友头像，播放弹出框中视频
     * creat by yajuan 2017.8.18
     * */
    @Test
    @SanityTest
    @PerformanceTest
    public void testWatchProfileVideo() throws UiObjectNotFoundException{
        WatchAction.navToWatch();
        WatchAction.ClickImageObjects();
        WatchAction.clickMiniLive();
        WatchAction.playProfileVideo();
        gDevice.pressBack();
    }

}
