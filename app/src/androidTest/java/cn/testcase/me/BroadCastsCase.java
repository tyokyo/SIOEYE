package cn.testcase.me;

import android.graphics.Point;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.logging.Logger;

import bean.BroadcastBean;
import bean.VideoBean;
import bean.WatcherBean;
import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.BroadcastAction;
import cn.action.FollowersAction;
import cn.action.MeAction;
import cn.action.PlayAction;
import cn.page.App;
import cn.page.MePage;
import cn.page.PlayPage;

import static cn.action.PlayAction.getNumberPlayVideo;

/**
 * Created by elon on 2016/10/13.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
/*
直播-基本功能验证
* 回放直播视频
* 回放评论
* 回放点赞
* 编辑直播视频标题
* */
public class BroadCastsCase extends VP2{
    Logger logger = Logger.getLogger(BroadCastsCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        //确保App 处于登录状态
        AccountAction.inLogin();
    }

    //直播 ：随机选择一个直播列表的视频-播放视频60秒
    @Test
    @SanityTest
    @PerformanceTest
    public void testPlayBroadcastVideo() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsIndex();
            logger.info("Index-"+index);
            BroadcastAction.getRandomBroadcasts(index).click();
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            Asst.assertTrue("time out 60 seconds.",!getObjectById(PlayPage.BROADCAST_VIEW_VIDEO_LOADING).exists());
            //click play screen center
            //clickById(MePage.BROADCAST_VIEW_WATCHER_COUNT,0,100);
            Spoon.screenshot("play_video");
        }
    }
    //1:编辑title 2:放弃保存
    @Test
    @SanityTest
    @PerformanceTest
    public void testEditTitleCancel() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsIndex();
            UiObject2 broadcast=BroadcastAction.getRandomBroadcasts(index);
            broadcast.swipe(Direction.LEFT,0.9f);
            BroadcastAction.navEdit();
            String expect_title=getTex(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY);
            //修改title
            getUiObjectById(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).clearTextField();
            String input_title=getRandomString(3);
            getUiObjectById(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).setText(input_title);
            //取消
            clickById(MePage.BROADCAST_EDIT_CANCEL);
            //must wait some seconds
            waitTime(5);
            BroadcastAction.getRandomBroadcasts(index).swipe(Direction.RIGHT,0.9f);
            //check
            BroadcastBean activeBean = BroadcastAction.getChinaBean(index);
            String active_title = activeBean.getBroadcast_title();
            Asst.assertEquals("modify title cancel",expect_title,active_title);
            Spoon.screenshot(gDevice,"modify_title_cancel");
        }
    }
    //title 输入字符长度3
    @Test
    @SanityTest
    @PerformanceTest
    public void testEditTitle3() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsIndex();
            UiObject2 broadcast=BroadcastAction.getRandomBroadcasts(index);
            broadcast.swipe(Direction.LEFT,0.9f);
            BroadcastAction.navEdit();
            String expect_title=getTex(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY);
            //修改title
            getUiObjectById(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).clearTextField();
            String input_title=getRandomString(3);
            //clickById(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY);
            clearText(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY);
            setText(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY,input_title);
            //确认
            clickById(MePage.BROADCAST_EDIT_OK);
            //wait time
            waitTime(5);
            waitUntilFind(MePage.BROADCAST_TITLE,10000);
            Spoon.screenshot("modify_title_complete");
            //check
            BroadcastBean activeBean = BroadcastAction.getChinaBean(index);
            String active_title = activeBean.getBroadcast_title();
            Asst.assertEquals("modify title",input_title,active_title);
            Spoon.screenshot("testEditTitle3",input_title);
        }
    }
    //title 输入字符长度35
    @Test
    @SanityTest
    @PerformanceTest
    public void testEditTitle70() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsIndex();
            UiObject2 broadcast=BroadcastAction.getRandomBroadcasts(index);
            broadcast.swipe(Direction.LEFT,0.9f);
            BroadcastAction.navEdit();
            String expect_title=getTex(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY);
            //修改title
            getUiObjectById(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).clearTextField();
            String input_title=getRandomString(35);
            //clickById(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY);
            clearText(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY);
            //setText(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY,input_title);
            clickById(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY);
            shellInputText(input_title);
            gDevice.pressBack();
            Spoon.screenshot("modify_title",input_title);
            //确认
            clickById(MePage.BROADCAST_EDIT_OK);
            //wait time
            waitTime(5);
            waitUntilFind(MePage.BROADCAST_TITLE,10000);
            Spoon.screenshot("modify_title_complete");
            //check
            BroadcastBean activeBean = BroadcastAction.getChinaBean(index);
            String active_title = activeBean.getBroadcast_title();
            logger.info(active_title.length()+"");
            Asst.assertEquals("modify title",input_title,active_title);
            Spoon.screenshot("modify_title",input_title);
        }
    }
    //title 输入字符长度>35
    @Test
    @SanityTest
    @PerformanceTest
    public void testEditTitleMoreThan70() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsIndex();
            UiObject2 broadcast=BroadcastAction.getRandomBroadcasts(index);
            broadcast.swipe(Direction.LEFT,0.9f);
            BroadcastAction.navEdit();
            String expect_title=getTex(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY);
            //修改title
            getUiObjectById(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).clearTextField();
            String input_title=getRandomString(80);
            logger.info("input-80:"+input_title);
            clickById(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY);
            shellInputText(input_title);
            gDevice.pressBack();
            //确认
            clickById(MePage.BROADCAST_EDIT_OK);
            //wait time
            waitTime(5);
            //check
            BroadcastBean activeBean = BroadcastAction.getChinaBean(index);
            String active_title = activeBean.getBroadcast_title();
            input_title=input_title.substring(0,70);
            logger.info("expect:"+input_title);
            logger.info("active:"+active_title);
            Asst.assertEquals("modify title",input_title,active_title);
            Spoon.screenshot("modify_title",input_title);
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    public void testDeleteBroadcastsVideo() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsIndex();
            UiObject2 broadcast=BroadcastAction.getRandomBroadcasts(index);
            broadcast.swipe(Direction.LEFT,0.9f);
            waitUntilFind(MePage.BROADCAST_DELETE,10000);
            clickById(MePage.BROADCAST_DELETE);
            //编辑视频标题
            clickByText("Edit the title");
            String expect_title=getTex(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY);
            //修改title
            getUiObjectById(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).clearTextField();
            String input_title=getRandomString(15);
            clickById(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY);
            shellInputText(input_title);
            gDevice.pressBack();
            //确认
            clickById(MePage.BROADCAST_EDIT_OK);
            waitTime(5);
            BroadcastBean activeBean = BroadcastAction.getChinaBean(index);
            String active_title = activeBean.getBroadcast_title();
            Asst.assertEquals("modify title",input_title,active_title);
            Spoon.screenshot("modify_title",input_title);
            BroadcastAction.deleteBroadcast(input_title);
            Asst.assertEquals("delete success",false,text_exists(input_title));
            Spoon.screenshot("delete_broadcast",input_title);
        }
    }
    //验证-评论，允许的最大字符数
    @Test
    @PerformanceTest
    public void testCmts120c() throws UiObjectNotFoundException, IOException {
        Point point=MeAction.getPointToDoComment();
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsIndex();
            BroadcastAction.getRandomBroadcasts(index).click();
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            //当前的评论数
            VideoBean videoBean1 =PlayAction.getNumberPlayVideo();
            int  comments_before = videoBean1.getComment();
            FollowersAction.clickToChat();
            String input_comments = getRandomString(120);
            //输入评论内容
            clickById(PlayPage.BROADCAST_VIEW_TIPTEXT);
            shellInputText(input_comments);
            //点击评论
            clickByPoint(point);
            gDevice.pressBack();
            waitTime(2);
            //滑动显示最新消息
            MeAction.displayNewMessages();
            Asst.assertTrue("comments success",getUiObjectByTextContains(input_comments).exists());
            //验证评论数+1
            VideoBean videoBean_after = PlayAction.getNumberPlayVideo();
            int  after_comments = videoBean_after.getComment();
            Asst.assertEquals(comments_before+1,after_comments);
            Spoon.screenshot("testComments_Length_120",input_comments);
            gDevice.pressBack();
        }
    }
    //验证-评论 超过最大的字符限制
    @Test
    @SanityTest
    @PerformanceTest
    public void testCmts130c() throws UiObjectNotFoundException, IOException {
        Point point=MeAction.getPointToDoComment();
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsIndex();
            BroadcastAction.getRandomBroadcasts(index).click();
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            //当前的评论数
            VideoBean videoBean1 =PlayAction.getNumberPlayVideo();
            int comments_before = videoBean1.getComment();
            FollowersAction.clickToChat();
            String input_comments = getRandomString(130);
            //输入评论内容
            clickById(PlayPage.BROADCAST_VIEW_TIPTEXT);
            shellInputText(input_comments);
            //点击评论
            clickByPoint(point);
            gDevice.pressBack();
            waitTime(2);
            input_comments=input_comments.substring(0,120);
            //滑动显示最新消息
            MeAction.displayNewMessages();
            Asst.assertEquals("comments success",true,getUiObjectByTextContains(input_comments).exists());
            //验证评论数+1
            VideoBean videoBean_after =PlayAction.getNumberPlayVideo();
            int  after_comments = videoBean_after.getComment();
            Asst.assertEquals(comments_before+1,after_comments);
            Spoon.screenshot("testComments_Length_130",input_comments);
            gDevice.pressBack();
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    public void testCmts20c() throws UiObjectNotFoundException, IOException {
        Point point=MeAction.getPointToDoComment();
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsIndex();
            BroadcastAction.getRandomBroadcasts(index).click();
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            //当前的评论数
            VideoBean videoBean =PlayAction.getNumberPlayVideo();
            int comments_before = videoBean.getComment();
            String input_comments = getRandomString(20);
            //输入评论内容
            FollowersAction.clickToChat();
            clickById(PlayPage.BROADCAST_VIEW_TIPTEXT);
            shellInputText(input_comments);
            //点击评论
            clickByPoint(point);
            gDevice.pressBack();
            waitTime(2);
            //滑动显示最新消息
            MeAction.displayNewMessages();
            Asst.assertEquals("comments success",true,getUiObjectByTextContains(input_comments).exists());
            //验证评论数+1
            VideoBean videoBean_after =PlayAction.getNumberPlayVideo();
            int after_comments = videoBean_after.getComment();
            Asst.assertEquals(comments_before+1,after_comments);
            Spoon.screenshot("testComments_Length_20",input_comments);
        }
    }
    //进入视频回放界面-直接点赞
    // 验证点赞数+1
    @Test
    @SanityTest
    @PerformanceTest
    public void testZanKAdd() throws UiObjectNotFoundException, IOException {
        //进入broadcasts
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        //是否存在可以回放的视频
        if (broadcast_size>=1){
            //随机选择一个视频
            int index=BroadcastAction.getRandomBroadcastsIndex();
            BroadcastAction.getRandomBroadcasts(index).click();
            //等待视频加载完成
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            //获取当前的点赞数目
            VideoBean bean_before_zan = PlayAction.getNumberPlayVideo();
            int  zan_before = bean_before_zan.getZan();
            //进行点赞操作
            FollowersAction.clickToChat();
            clickById(PlayPage.BROADCAST_VIEW_ZAN);
            //获取点赞操作之后的点赞数目
            VideoBean bean_after_zan = PlayAction.getNumberPlayVideo();
            int zan_after = bean_after_zan.getZan();
            //验证点赞数+1
            Asst.assertEquals("check zan +1",zan_before+1,zan_after);
            //截取屏幕
            Spoon.screenshot("testBroadcastsZanKAdd");
        }
    }
    //进入视频回放界面-弹出的输入框中点赞
    // 验证点赞数+1
    @Test
    @SanityTest
    @PerformanceTest
    public void tesZanKAddByPopup() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsIndex();
            BroadcastAction.getRandomBroadcasts(index).click();
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            //获取当前的点赞数目
            VideoBean bean_before_zan = getNumberPlayVideo();
            int  zan_before = bean_before_zan.getZan();
            //弹出评论输入框-点赞
            FollowersAction.clickToChat();
            clickById(PlayPage.BROADCAST_VIEW_TIPTEXT);
            waitTime(2);
            //进行点赞操作
            clickById(PlayPage.BROADCAST_VIEW_ZAN_FLOAT_LIKE);
            gDevice.pressBack();
            FollowersAction.clickToAnchor();
            //获取点赞操作之后的点赞数目
            VideoBean bean_after_zan = getNumberPlayVideo();
            int  zan_after = bean_after_zan.getZan();
            //验证点赞数+1
            Asst.assertEquals("check zan +1",zan_before+1,zan_after);
            //截取屏幕
            Spoon.screenshot("testBroadcastsZanKAdd");
        }
    }
    /****
    上下滑动直播列表
     选择一个视频观看
    * */
    @Test
    @SanityTest
    @PerformanceTest
    public void testSwipeToViewVideo() throws UiObjectNotFoundException {
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            MeAction.swipeUpDown(MePage.BROADCASTS_LIST,10);
            int index=BroadcastAction.getRandomBroadcastsIndex();
            BroadcastAction.getRandomBroadcasts(index).click();
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            Asst.assertTrue("time out 60 seconds.",!getObjectById(PlayPage.BROADCAST_VIEW_VIDEO_LOADING).exists());
            Spoon.screenshot("play_video");
            gDevice.pressBack();
        }
    }

}
