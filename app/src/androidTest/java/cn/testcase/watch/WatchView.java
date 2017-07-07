package cn.testcase.watch;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

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

/**
 * Created by elon on 2016/11/14.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class WatchView extends VP2 {
    private Logger logger = Logger.getLogger(WatchView.class.getName());
    @Before
    public void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    /*播放关注列表的视频文件*/
    @Test
    @SanityTest
    @PerformanceTest
    public void testViewWatchVideo() throws UiObjectNotFoundException, ParseException {
        //进入Watch
        WatchAction.navToWatch();
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
}
