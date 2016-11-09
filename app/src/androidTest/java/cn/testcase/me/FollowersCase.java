package cn.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.logging.Logger;

import bean.FollowingBean;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.BroadcastAction;
import cn.action.FollowingAction;
import cn.action.MeAction;
import cn.page.App;
import cn.page.MePage;

/**
 * Created by elon on 2016/11/9.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class FollowersCase extends VP2 {
    Logger logger = Logger.getLogger(BroadCastsCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        //确保App 处于登录状态
        AccountAction.inLogin();
    }
    //粉丝 ：列表里随机选择一个直播的视频-播放视频60秒
    @Test
    public void testPlayFansVideo() throws UiObjectNotFoundException, IOException {
        MeAction.navToFans();
        int following_size= FollowingAction.getFollowingSize();
        if (following_size>=1){
            waitTime(3);
            FollowingBean followingBean =FollowingAction.randomFansUser();
            int index=followingBean.getIndex_linearLayout();
            FollowingAction.clickRandomFollower(followingBean);
            //直播列表
            int brd_size= BroadcastAction.getFansBroadcastsSize();
            if (brd_size>=1){
                //播放一个视频
                FollowingAction.clickBroadcast();
                BroadcastAction.waitBroadcastLoading();
                waitUntilFind(MePage.BROADCAST_VIEW_VIDEO_LOADING,30000);
                Asst.assertTrue("time out 60 seconds.",!getObjectById(MePage.BROADCAST_VIEW_VIDEO_LOADING).exists());
                //click play screen center
                clickById(MePage.BROADCAST_VIEW_WATCHER_COUNT,0,100);
                Spoon.screenshot("play_video");
                gDevice.pressBack();
            }else{
                Spoon.screenshot("no_video");
            }
        }
    }
}
