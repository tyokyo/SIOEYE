package cn.testcase.me;

import android.app.Notification;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
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

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.MeAction;
import cn.action.NotificationAction;
import cn.page.App;
import cn.page.MePage;
import cn.page.WatchPage;
import usa.page.Me;

/**
 * Created by elon on 2016/10/31.
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
     * 检查团队消息
     * 1.回复消息
     * 2.进入视频检查回复的消息
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
            Asst.fail("加载Sioeye团队消息失败");
        }
    }
    /**
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
            isComment = NotificationAction.getVideoComment(input_title);
            Spoon.screenshot("has_comment");
            Asst.assertTrue(isComment);
        }else{
            Spoon.screenshot("no_comment_video");
            Asst.fail("没有人评论过你的视频");
        }
    }
}
