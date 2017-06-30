package cn.testcase.me;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

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
            FollowingBean followingBean =FollowingAction.randomFollowingUser();
            logger.info(followingBean.toString());
            int index=followingBean.getIndex_linearLayout();
            //选择一个用户
            FollowingAction.clickRandomFollower(followingBean);
            //随机播放一个视频
            int broadcast_size=FollowingAction.hasBroadcasts();
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
    //验证-评论，允许的最大字符数
    @Test
    public void testFwCmt120c() throws UiObjectNotFoundException, IOException {
        Point point=MeAction.getPointToDoComment();
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsIndex();
            BroadcastAction.getRandomBroadcasts(index).click();
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(MePage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            //当前的评论数
            WatcherBean watcherBean1 = BroadcastAction.getWatcher();
            String comments_before = watcherBean1.getComments();
            String input_comments = getRandomString(120);
            //评论数
            int comments_count_before=cover(comments_before);
            //输入评论内容
            clickById(MePage.BROADCAST_VIEW_TIPTEXT);
            shellInputText(input_comments);
            //点击评论
            clickByPoint(point);
            gDevice.pressBack();
            waitTime(2);
            //滑动显示最新消息
            MeAction.displayNewMessages();
            waitTime(2);
            Asst.assertEquals("comments success",true,getUiObjectByTextContains(input_comments).exists());
            //验证评论数+1
            WatcherBean watcherBean_after = BroadcastAction.getWatcher();
            String after_comments = watcherBean_after.getComments();
            int comments_count_after=cover(after_comments);
            if (comments_count_before>1000){
                Asst.assertEquals(comments_count_before,comments_count_after);
            }else{
                Asst.assertEquals(comments_count_before+1,comments_count_after);
            }
            Spoon.screenshot("testComments_Length_120",input_comments);
            gDevice.pressBack();
        }
    }
    //验证-评论 超过最大的字符限制
    @Test
    public void testFwCmt130c() throws UiObjectNotFoundException, IOException {
        Point point=MeAction.getPointToDoComment();
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsIndex();
            BroadcastAction.getRandomBroadcasts(index).click();
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(MePage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            //当前的评论数
            WatcherBean watcherBean1 = BroadcastAction.getWatcher();
            String comments_before = watcherBean1.getComments();
            String input_comments = getRandomString(130);
            int comments_count_before=cover(comments_before);
            //输入评论内容
            clickById(MePage.BROADCAST_VIEW_TIPTEXT);
            shellInputText(input_comments);
            //点击评论
            clickByPoint(point);
            gDevice.pressBack();
            waitTime(2);
            //滑动显示最新消息
            MeAction.displayNewMessages();
            waitTime(2);
            input_comments=input_comments.substring(0,120);
            Asst.assertEquals("comments success",true,getUiObjectByTextContains(input_comments).exists());
            //验证评论数+1
            WatcherBean watcherBean_after = BroadcastAction.getWatcher();
            String after_comments = watcherBean_after.getComments();
            int comments_count_after=cover(after_comments);
            if (comments_count_before>1000){
                Asst.assertEquals(comments_count_before,comments_count_after);
            }else{
                Asst.assertEquals(comments_count_before+1,comments_count_after);
            }
            Spoon.screenshot("testComments_Length_130",input_comments);
            gDevice.pressBack();
        }
    }
    //评论-关注的好友中的直播视频
    @Test
    public void testFwCmt20c() throws UiObjectNotFoundException, IOException {
        //进入关注
        MeAction.navToFollowing();
        int following_size= FollowingAction.getFollowingSize();
        if (following_size>=1){
            //获取随机的一个用户信息
            FollowingBean followingBean =FollowingAction.randomFollowingUser();
            logger.info(followingBean.toString());
            int index=followingBean.getIndex_linearLayout();
            //选择一个用户
            FollowingAction.clickRandomFollower(followingBean);
            int broadcast_size=FollowingAction.hasBroadcasts();
            if (broadcast_size>=1){
                //随机播放一个视频
                FollowingAction.clickFollowingBroadcast();
                BroadcastAction.waitBroadcastLoading();
                waitUntilGone(MePage.BROADCAST_VIEW_VIDEO_LOADING,60000);
                //点赞对象的坐标
                Rect rect=getRect(MePage.BROADCAST_VIEW_ZAN);
                //当前的评论数
                WatcherBean watcherBean1 = BroadcastAction.getWatcher();
                String comments_before = watcherBean1.getComments();
                String input_comments = getRandomString(20);
                int comments_count_before=cover(comments_before);
                //输入评论内容
                clickById(MePage.BROADCAST_VIEW_TIPTEXT);
                shellInputText(input_comments);
                //点击评论
                clickRect(rect);
                waitTime(2);
                //滑动显示最新消息
                MeAction.displayNewMessages();
                //input_comments=input_comments.substring(0,120);
                Asst.assertEquals("comments success",true,getUiObjectByTextContains(input_comments).exists());
                //验证评论数+1
                gDevice.pressBack();
                WatcherBean watcherBean_after = BroadcastAction.getWatcher();
                String after_comments = watcherBean_after.getComments();
                int comments_count_after=cover(after_comments);
                if (comments_count_before>1000){
                    Asst.assertEquals(comments_count_before,comments_count_after);
                }else{
                    Asst.assertEquals(comments_count_before+1,comments_count_after);
                }
                Spoon.screenshot("testFollowingComments_Length_20",input_comments);
                gDevice.pressBack();
                Spoon.screenshot("play_20_seconds");
            }else {
                Spoon.screenshot("no_video","没有直播的视频");
            }
        }else{
            Spoon.screenshot("no_user","没有关注的用户");
        }
    }
    //进入视频回放界面-直接点赞
    // 验证点赞数+1
    @Test
    public void testFollowingZanKAdd() throws UiObjectNotFoundException, IOException {
        //进入关注
        MeAction.navToFollowing();
        waitTime(3);
        int following_size= FollowingAction.getFollowingSize();
        if (following_size>=1){
            //获取随机的一个用户信息
            FollowingBean followingBean =FollowingAction.randomFollowingUser();
            logger.info(followingBean.toString());
            int index=followingBean.getIndex_linearLayout();
            //选择一个用户
            FollowingAction.clickRandomFollower(followingBean);
            int broadcast_size=FollowingAction.hasBroadcasts();
            if (broadcast_size>=1){
                //随机播放一个视频
                FollowingAction.clickFollowingBroadcast();
                BroadcastAction.waitBroadcastLoading();
                waitUntilGone(MePage.BROADCAST_VIEW_VIDEO_LOADING,60000);
                //获取当前的点赞数目
                WatcherBean bean_before_zan = BroadcastAction.getWatcher();
                String zan_before = bean_before_zan.getZan();
                int zan_before_int = cover(zan_before);

                //进行点赞操作
                clickById(MePage.BROADCAST_VIEW_ZAN);
                //获取点赞操作之后的点赞数目
                WatcherBean bean_after_zan = BroadcastAction.getWatcher();
                String zan_after = bean_after_zan.getZan();
                int zan_after_int= cover(zan_after);
                //验证点赞数+1
                if (zan_before_int>1000){
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
            FollowingBean followingBean =FollowingAction.randomFollowingUser();
            logger.info(followingBean.toString());
            int index=followingBean.getIndex_linearLayout();
            //选择一个用户
            FollowingAction.clickRandomFollower(followingBean);
            int broadcast_size=FollowingAction.hasBroadcasts();
            if (broadcast_size>=1){
                //随机播放一个视频
                FollowingAction.clickFollowingBroadcast();
                BroadcastAction.waitBroadcastLoading();
                waitUntilGone(MePage.BROADCAST_VIEW_VIDEO_LOADING,60000);
                //获取当前的点赞数目
                WatcherBean bean_before_zan = BroadcastAction.getWatcher();
                String zan_before = bean_before_zan.getZan();
                int zan_before_int = cover(zan_before);
                //弹出评论输入框-点赞
                clickById(MePage.BROADCAST_VIEW_TIPTEXT);
                waitTime(2);
                //进行点赞操作
                clickById(MePage.BROADCAST_VIEW_ZAN_FLOAT_LIKE);
                gDevice.pressBack();
                //获取点赞操作之后的点赞数目
                WatcherBean bean_after_zan = BroadcastAction.getWatcher();
                String zan_after = bean_after_zan.getZan();
                int zan_after_int=cover(zan_after);
                //验证点赞数+1
                if (zan_before_int>1000){
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
        waitTime(3);
        int following_size= FollowingAction.getFollowingSize();
        if (following_size>=1){
            //获取随机的一个用户信息
            FollowingBean followingBean =FollowingAction.randomFollowingUser();
            logger.info(followingBean.toString());
            int index=followingBean.getIndex_linearLayout();
            //选择一个用户
            FollowingAction.clickRandomFollower(followingBean);
            //随机播放一个视频
            int broadcast_size=FollowingAction.hasBroadcasts();
            if (broadcast_size>=1){
                //滑动操作
                MeAction.swipeUpDown(MePage.USER_FOLLOW_LIST,10);
                //播放视频
                FollowingAction.clickFollowingBroadcast();
                //等待视频加载完成
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
