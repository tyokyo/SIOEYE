package cn.testcase.me;

import android.graphics.Point;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
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
            BroadcastAction.clickRandomBroadcastsVideo(index);
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            Asst.assertTrue("time out 60 seconds.",!getObjectById(PlayPage.BROADCAST_VIEW_VIDEO_LOADING).exists());
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
            int index=BroadcastAction.getRandomBroadcastsWithNoRoomIndex();
            UiObject2 broadcast=BroadcastAction.getRandomBroadcastsElement(index);
            BroadcastAction.navEdit(broadcast);
            String expect_title=getTex(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY);
            //修改title
            getUiObjectById(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).clearTextField();
            String input_title=getRandomString(3);
            getUiObjectById(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).setText(input_title);
            //取消
            clickById(MePage.BROADCAST_EDIT_CANCEL);
            //must wait some seconds
            waitTime(5);
            //check
            BroadcastBean activeBean = BroadcastAction.getBroadcastBean(index);
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
            int index=BroadcastAction.getRandomBroadcastsWithNoRoomIndex();
            UiObject2 broadcast=BroadcastAction.getRandomBroadcastsElement(index);
            BroadcastAction.navEdit(broadcast);
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
            BroadcastBean activeBean = BroadcastAction.getBroadcastBean(index);
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
            int index=BroadcastAction.getRandomBroadcastsWithNoRoomIndex();
            UiObject2 broadcast=BroadcastAction.getRandomBroadcastsElement(index);
            BroadcastAction.navEdit(broadcast);
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
            BroadcastBean activeBean = BroadcastAction.getBroadcastBean(index);
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
            int index=BroadcastAction.getRandomBroadcastsWithNoRoomIndex();
            UiObject2 broadcast=BroadcastAction.getRandomBroadcastsElement(index);
            BroadcastAction.navEdit(broadcast);
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
            BroadcastBean activeBean = BroadcastAction.getBroadcastBean(index);
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
            UiObject2 broadcast=BroadcastAction.getRandomBroadcastsElement(index);
            BroadcastAction.deleteBroadcast(broadcast);
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
            BroadcastAction.clickRandomBroadcastsVideo(index);
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
            if (after_comments<1000){
                Asst.assertEquals(comments_before+1,after_comments);
                Spoon.screenshot("testComments_Length_120",input_comments);
            }
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
            BroadcastAction.clickRandomBroadcastsVideo(index);
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
            BroadcastAction.clickRandomBroadcastsVideo(index);
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
            BroadcastAction.clickRandomBroadcastsVideo(index);
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
            BroadcastAction.clickRandomBroadcastsVideo(index);
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
            if (zan_after<1000){
                //验证点赞数+1
                Asst.assertEquals("check zan +1",zan_before+1,zan_after);
                //截取屏幕
                Spoon.screenshot("testBroadcastsZanKAdd");
            }
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
            MeAction.swipeUpDown(MePage.BROADCASTS_LIST,1);
            int index=BroadcastAction.getRandomBroadcastsIndex();
            BroadcastAction.clickRandomBroadcastsVideo(index);
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            Asst.assertTrue("time out 60 seconds.",!getObjectById(PlayPage.BROADCAST_VIEW_VIDEO_LOADING).exists());
            Spoon.screenshot("play_video");
            gDevice.pressBack();
        }
    }
    /**
     * 分享直播列表中的视频
     * zhangyajuan 2017.9.19
     * */
    @Test
    @SanityTest
    @PerformanceTest
    public void testShareBroadcast() throws UiObjectNotFoundException {
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsIndex();
            UiObject2 object=BroadcastAction.getRandomBroadcastsElement(index);
            UiObject2 view=BroadcastAction.getVideoAction(object);
            view.getChildren().get(1).click(); //点击分享
            waitUntilFind(MePage.BROADCAST_SHARE,3000);
            Asst.assertTrue(id_exists(MePage.BROADCAST_SHARE));
        }
    }
    /**
     * 播放直播间视频
     *  zhangyajuan 2017.9.27
     *  */
    @Test
    @SanityTest
    @PerformanceTest
    public void testPlayRoomVideo() throws UiObjectNotFoundException {
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsRoomIndex();
            logger.info("Index-"+index);
            BroadcastAction.clickRandomBroadcastsVideo(index);
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            Asst.assertTrue("time out 60 seconds.",!getObjectById(PlayPage.BROADCAST_VIEW_VIDEO_LOADING).exists());
            Spoon.screenshot("play_video");
        }
    }
    /**
     * 修改普通视频封面（只能调出相机或相册选项）
     *  zhangyajuan 2017.9.27
     *  */
    @Test
    @SanityTest
    @PerformanceTest
    public void testChangeVideoCover() throws UiObjectNotFoundException {
        MeAction.navToBroadcasts();
        int broadcast_size=BroadcastAction.getBroadcastsSize();
        if (broadcast_size>=1){
            int index=BroadcastAction.getRandomBroadcastsWithNoRoomIndex();
            UiObject2 object=BroadcastAction.getRandomBroadcastsElement(index);
            UiObject2 view=BroadcastAction.getVideoAction(object);
            view.getChildren().get(2).click(); //点击封面
            waitUntilFind(MePage.COVER_PLOT_BTN_DIALOG_PHOTO,10000);
            Asst.assertTrue(id_exists(MePage.COVER_PLOT_BTN_DIALOG_PHOTO)); // 相机
            Asst.assertTrue(id_exists(MePage.COVER_PLOT_BTN_DIALOG_ALBUM)); // 相册
            clickById(MePage.BROADCAST_CANCEL);
        }
    }


}
