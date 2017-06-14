package pm.testcase.me;

import android.graphics.Rect;
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
import bean.WatcherBean;
import ckt.base.VP2;
import pm.action.AccountAction;
import pm.action.BroadcastAction;
import pm.action.FollowingAction;
import pm.action.MeAction;
import pm.page.App;
import pm.page.MePage;

/**
 * Created by elon on 2016/11/9.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
/*关注 播放视频 评论 点赞
* */
public class FollowingCase extends VP2 {
    Logger logger = Logger.getLogger(BroadCastsCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        //确保App 处于登录状态
        AccountAction.inLogin();
    }
    //关注 ：关注列表里随机选择一个直播的视频-播放视频60秒
    @Test
    public void testPlayFollowingVideo() throws UiObjectNotFoundException, IOException {
        MeAction.navToFollowing();
        int following_size= FollowingAction.getFollowingSize();
        if (following_size>=1){
            //获取随机的一个用户信息
            FollowingBean followingBean = FollowingAction.randomFollowingUser();
            logger.info(followingBean.toString());
            int index=followingBean.getIndex_linearLayout();
            //选择一个用户
            FollowingAction.clickRandomFollower(followingBean);
            //随机播放一个视频
            int broadcast_size= FollowingAction.hasBroadcasts();
            if (broadcast_size>=1){
                FollowingAction.clickFollowingBroadcast();
                BroadcastAction.waitBroadcastLoading();
                waitUntilGone(MePage.BROADCAST_VIEW_VIDEO_LOADING,60000);
                //play video
                waitTime(20);
                Asst.assertTrue("time out 60 seconds.",!getObjectById(MePage.BROADCAST_VIEW_VIDEO_LOADING).exists());
                Spoon.screenshot("play_video");
                gDevice.pressBack();
                Spoon.screenshot("play_20_seconds");
            }else {
                Spoon.screenshot("no_video");
            }
        }
    }
    //进入视频回放界面-直接点赞
    // 验证点赞数+1
    @Test
    public void testFollowingZanKAdd() throws UiObjectNotFoundException, IOException {
        //进入关注
        MeAction.navToFollowing();
        int following_size= FollowingAction.getFollowingSize();
        if (following_size>=1){
            //获取随机的一个用户信息
            FollowingBean followingBean = FollowingAction.randomFollowingUser();
            logger.info(followingBean.toString());
            int index=followingBean.getIndex_linearLayout();
            //选择一个用户
            FollowingAction.clickRandomFollower(followingBean);
            int broadcast_size= FollowingAction.hasBroadcasts();
            if (broadcast_size>=1){
                //随机播放一个视频
                FollowingAction.clickFollowingBroadcast();
                BroadcastAction.waitBroadcastLoading();
                waitUntilGone(MePage.BROADCAST_VIEW_VIDEO_LOADING,60000);
                //获取当前的点赞数目
                WatcherBean bean_before_zan = BroadcastAction.getWatcher();
                String zan_before = bean_before_zan.getZan();
                boolean K=false;
                int zan_before_int = 0;
                if (zan_before.contains("K")){
                    K=true;
                }else{
                    zan_before_int=Integer.parseInt(zan_before);
                }
                //进行点赞操作
                clickById(MePage.BROADCAST_VIEW_ZAN);
                //获取点赞操作之后的点赞数目
                WatcherBean bean_after_zan = BroadcastAction.getWatcher();
                String zan_after = bean_after_zan.getZan();
                int zan_after_int= Integer.parseInt(zan_after);
                //验证点赞数+1
                if (K){
                    Asst.assertEquals("check zan +1",zan_before,zan_after);
                }else{
                    Asst.assertEquals("check zan +1",zan_before_int+1,zan_after_int);
                }
                //截取屏幕
                Spoon.screenshot("testBroadcastsZanKAdd");
            }else {
                logger.info("no_video");
                Spoon.screenshot("no_video","没有直播的视频");
            }
        }else{
            logger.info("no_user");
            Spoon.screenshot("no_user","没有关注的用户");
        }
    }
    //进入视频回放界面-弹出的输入框中点赞
    // 验证点赞数+1
    @Test
    public void testFollowingZanKAddByPopup() throws UiObjectNotFoundException, IOException {
        //进入关注
        MeAction.navToFollowing();
        int following_size= FollowingAction.getFollowingSize();
        if (following_size>=1){
            //获取随机的一个用户信息
            FollowingBean followingBean = FollowingAction.randomFollowingUser();
            logger.info(followingBean.toString());
            int index=followingBean.getIndex_linearLayout();
            //选择一个用户
            FollowingAction.clickRandomFollower(followingBean);
            int broadcast_size= FollowingAction.hasBroadcasts();
            if (broadcast_size>=1){
                //随机播放一个视频
                FollowingAction.clickFollowingBroadcast();
                BroadcastAction.waitBroadcastLoading();
                waitUntilGone(MePage.BROADCAST_VIEW_VIDEO_LOADING,60000);
                //获取当前的点赞数目
                WatcherBean bean_before_zan = BroadcastAction.getWatcher();
                String zan_before = bean_before_zan.getZan();
                boolean K=false;
                int zan_before_int = 0;
                if (zan_before.contains("K")){
                    K=true;
                }else{
                    zan_before_int=Integer.parseInt(zan_before);
                }
                //弹出评论输入框-点赞
                clickById(MePage.BROADCAST_VIEW_TIPTEXT);
                waitTime(2);
                //进行点赞操作
                clickById(MePage.BROADCAST_VIEW_ZAN_FLOAT_LIKE);
                gDevice.pressBack();
                //获取点赞操作之后的点赞数目
                WatcherBean bean_after_zan = BroadcastAction.getWatcher();
                String zan_after = bean_after_zan.getZan();
                int zan_after_int= Integer.parseInt(zan_after);
                //验证点赞数+1
                if (K){
                    Asst.assertEquals("check zan +1",zan_before,zan_after);
                }else{
                    Asst.assertEquals("check zan +1",zan_before_int+1,zan_after_int);
                }
                //截取屏幕
                Spoon.screenshot("testBroadcastsZanKAdd");
            }else {
                logger.info("no_video");
                Spoon.screenshot("no_video","没有直播的视频");
            }
        }else{
            logger.info("no_user");
            Spoon.screenshot("no_user","没有关注的用户");
        }
    }
    /****
     上下滑动直播列表
     选择一个视频观看
     * */
    @Test
    public void testSwipeToViewVideo() throws UiObjectNotFoundException, IOException {
        MeAction.navToFollowing();
        int following_size= FollowingAction.getFollowingSize();
        if (following_size>=1){
            //获取随机的一个用户信息
            FollowingBean followingBean = FollowingAction.randomFollowingUser();
            logger.info(followingBean.toString());
            int index=followingBean.getIndex_linearLayout();
            //选择一个用户
            FollowingAction.clickRandomFollower(followingBean);
            //随机播放一个视频
            int broadcast_size= FollowingAction.hasBroadcasts();
            if (broadcast_size>=1){
                //滑动操作
                MeAction.swipeUpDown(MePage.USER_FOLLOW_LIST,10);
                FollowingAction.clickFollowingBroadcast();
                BroadcastAction.waitBroadcastLoading();
                waitUntilGone(MePage.BROADCAST_VIEW_VIDEO_LOADING,60000);
                //play video
                waitTime(20);
                Asst.assertTrue("time out 60 seconds.",!getObjectById(MePage.BROADCAST_VIEW_VIDEO_LOADING).exists());
                Spoon.screenshot("play_video");
                gDevice.pressBack();
                Spoon.screenshot("play_20_seconds");
            }else {
                Spoon.screenshot("no_video");
            }
        }
    }
}
