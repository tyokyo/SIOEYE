package cn.testcase.me;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import bean.MeBean;
import bean.PopInfoBean;
import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.MainAction;
import cn.action.MeAction;
import cn.action.NotificationAction;
import cn.page.App;
import cn.page.MePage;
import cn.page.NotificationPage;
import cn.page.WatchPage;
import usa.page.Me;

/**
 * Created by elon on 2016/10/31.
 * Changed by jqx on 2017/8/29
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class NotificationCase extends VP2 {
    private Logger logger = Logger.getLogger(NotificationCase.class.getName());
    public String username;
    public int followed;
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    @Test
    @SanityTest
    @PerformanceTest
    public void testNotificationAD() throws UiObjectNotFoundException {
        MeAction.navToNotifications();
        waitTime(3);
        int f_users = findObjects(Me.NOTIFICATIONS_USER_HEAD).size();
        if (f_users>=1) {
            Rect rect = getObjectById(Me.NOTIFICATIONS_USER_FOLLOW).getBounds();
            int width = gDevice.getDisplayWidth();
            gDevice.click(width-rect.centerX(),rect.centerY());
            waitUntilFind(WatchPage.WATCH_USER_MINI_NAME,40000);
            username=getTex(WatchPage.WATCH_USER_MINI_NAME);
            gDevice.pressBack();
            clickById(Me.NOTIFICATIONS_USER_FOLLOW);
            waitTime(2);
            Spoon.screenshot("delete_all_who_followed_me_"+username);
            gDevice.pressBack();
            MeAction.navToFans();
            followed = findObjects(Me.USER_FOLLOW).size();

            Asst.assertEquals("delete_followed_user",f_users-1,followed);
            Spoon.screenshot("delete_all_who_followed_me_"+username);
            gDevice.pressBack();

        }
    }
    /**
     * Created by jqx on 2017/8/29.
     * 检查团队消息
     * 1.检查加载团队消息是否成功
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testSioeyeTeam() throws UiObjectNotFoundException{
        MeAction.navToNotifications();
        waitTime(3);
        boolean loadMessage =  NotificationAction.loadSioeyeMessage();
        if(loadMessage==true){
            Asst.assertTrue(loadMessage);
        }else{
            Spoon.screenshot("load_error");
            Asst.fail("Loading Sioeye message fail.");
        }
    }
    /**
     * Created by jqx on 2017/8/29.
     * 检查消息回复功能
     * 1.回复消息
     * 2.进入视频检查回复的消息
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testNotificationReply() throws UiObjectNotFoundException, IOException {
        Point point=MeAction.getPointToDoComment();
        MeAction.navToNotifications();
        waitTime(3);
        boolean isComment = false;
        int lisComment = NotificationAction.replyCommentSize();
        if (lisComment>2){
            clickByText("Reply");
            String input_title=getRandomString(10);
            setText(MePage.NOTIFICATION_REPLY_COMMENT,input_title);
            clickByPoint(point);
            waitTime(4);
            isComment = NotificationAction.getVideoComment(input_title);
            if (text_exists_contain("The video has been deleted")){

            }else{
                Spoon.screenshot("has_comment");
                Asst.assertTrue(isComment);
            }
        }else{
            Spoon.screenshot("no_comment_video");
            Asst.fail("Reply comment fail.");
        }
    }
    /**
     * Created by jqx on 2017/9/7.
     * 关注/取关评论者
     * 1.如果已经关注评论者，则先取消关注，并在关注者中确认已经取关
     * 2.关注评论者
     * 3.在关注者中查找已经关注的人
     */
    @Test
    @SanityTest
    @PerformanceTest
    //add first follow
    public void testAddFollowByNotification() throws UiObjectNotFoundException, IOException{
        MainAction.clickMe();
        waitTime(3);
        //get me bean data
        MeBean mb_before=MeAction.getMeBean();
        clickByText("Notifications");
        waitUntilFind(NotificationPage.NTF_VIDEO_INFO,40000);

        waitTime(3);
        List<UiObject2> notifications= NotificationAction.getAllNotificationsCanBeFollowed();
        if (notifications.size()>=1){
            UiObject2 follow = notifications.get(0);
            UiObject2 avatar=NotificationAction.getNotificationIcon(follow);
            //click user avatar
            avatar.click();
            //get follower count
            PopInfoBean popInfoBeanBefore =NotificationAction.getUserPopInfo();
            //add follow
            clickById(NotificationPage.NTF_FOLLOW);
            waitTime(3);
            PopInfoBean popInfoBeanAfter =NotificationAction.getUserPopInfo();
            waitTime(3);
            //follower +1
            Asst.assertEquals("getFollower+1",popInfoBeanBefore.getFollower()+1,popInfoBeanAfter.getFollower());
            gDevice.pressBack();
            gDevice.pressBack();
            MeBean mb_after=MeAction.getMeBean();
            //follower +1
            Asst.assertEquals("getFollowing+1",mb_before.getFollowing()+1,mb_after.getFollowing());
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    //delete user has been followed
    public void testDelFollowByNotification() throws UiObjectNotFoundException, IOException{
        MainAction.clickMe();
        waitTime(3);
        //get me bean data
        MeBean mb_before=MeAction.getMeBean();
        clickByText("Notifications");
        waitUntilFind(NotificationPage.NTF_VIDEO_INFO,40000);

        waitTime(3);
        List<UiObject2> notifications= NotificationAction.getAllNotificationsHasBeFollowed();
        if (notifications.size()>=1){
            UiObject2 follow = notifications.get(0);
            UiObject2 avatar=NotificationAction.getNotificationIcon(follow);
            //click user avatar
            avatar.click();
            //get follower count
            PopInfoBean popInfoBeanBefore =NotificationAction.getUserPopInfo();
            //add follow
            clickById(NotificationPage.NTF_FOLLOW);
            waitTime(3);
            PopInfoBean popInfoBeanAfter =NotificationAction.getUserPopInfo();
            waitTime(3);
            //follower -1
            Asst.assertEquals("change nick but not save it",popInfoBeanBefore.getFollower(),popInfoBeanAfter.getFollower()+1);
            gDevice.pressBack();
            gDevice.pressBack();
            MeBean mb_after=MeAction.getMeBean();
            //follower -1
            Asst.assertEquals("change nick but not save it",mb_before.getFollowing(),mb_after.getFollowing()+1);
        }
    }
    /**
     * Created by jqx on 2017/9/7.
     * 关注/取关点赞者
     * 1.如果已经关注点赞者，则先取消关注，并在关注者中确认已经取关
     * 2.关注点赞者
     * 3.在关注者中查找已经关注的人
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testLikesFollow() throws UiObjectNotFoundException, IOException{
        MeAction.navToNotifications();
        waitTime(3);
        NotificationAction.navToLikes();
        boolean isFollow = false;
        isFollow = NotificationAction.getFollowLikes();
        Asst.assertTrue(isFollow);
        Spoon.screenshot("is_follow_likes_success");
    }
}
