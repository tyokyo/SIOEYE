package cn.action;

import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.SystemClock;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.App;
import cn.page.DiscoverPage;
import cn.page.MePage;
import usa.page.Discover;

/**
 * Created by jqx on 2017/8/18.
 */
public class NotificationAction extends VP2 {
    private static Logger logger = Logger.getLogger(MeAction.class.getName());
    //获取评论数
    public static int replyCommentSize(){
        waitHasObject(MePage.BROADCAST_CONTENT,50000);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz("android.widget.LinearLayout"));
        int size = lisCollect.size();
        logger.info("getCommentsSize:"+size);
        return  size;
    }
    //随机获取一个comments对象的index
    public static int getRandomCommentsIndex(){
        List<UiObject2> lisCollect = gDevice.findObjects(By.res(MePage.NOTIFICATION_WATCH_VIDEO));
        int size = lisCollect.size();
        logger.info("getRandomCommentsIndex-size:"+size);
        Random random = new Random();
        int rd = random.nextInt(size);
        logger.info("size-"+size+"random:"+rd);
        return  rd==0?rd:rd-1;
    }
    //随机获取一个Comments对象
    public static UiObject2 getRandomComments(int index){
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz("android.widget.LinearLayout"));
        logger.info("getRandomBroadcasts:"+lisCollect.size());
        UiObject2 comment = lisCollect.get(index);
        return  comment;
    }
    //获取评论视频的父元素
    public static UiObject2 getCommentsParent() throws UiObjectNotFoundException {
        UiObject2 commentObject = getObject2ById(MePage.NOTIFICATION_WATCH_VIDEO).getParent().getParent();
        return commentObject;
    }
    //获取输入的视频评论
    public static boolean getVideoComment(String comments) throws UiObjectNotFoundException {
        clickById(MePage.NOTIFICATION_WATCH_VIDEO);
        waitTime(3);
        MeAction.displayNewMessages();
        if(getObjectByTextContains(comments).exists()){
            return true;
        }else{
            return false;
        }
    }
    //获取Sioeye团队消息
    public static boolean loadSioeyeMessage() throws UiObjectNotFoundException{
        String errorStr = "network connect time out, please retry.";
        String errorStr2 = "Web page not available";
        String errorStr3 = "No Internet";
        boolean loadmessage = false;
        clickByText("Sioeye Team");
        clickByClass("android.widget.LinearLayout");
        waitUntilGone("BROADCAST_VIEW_STATUS_IMAGE",10000);
        if(text_exists(errorStr)||text_exists(errorStr2)||text_exists(errorStr3)){
            loadmessage=false;
        }else{
            loadmessage=true;
        }
        return loadmessage;
    }
    //获取关注按钮
    public static boolean getFollowButton() throws UiObjectNotFoundException{
        cancelFollow();
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToNotifications();
        List<UiObject2> childPart = getCommentsParent().getChildren();
        List<UiObject2> litterChild = childPart.get(1).getChildren();
        List<UiObject2> litterChild2 = litterChild.get(0).getChildren();
        UiObject2 followButton = litterChild2.get(1);
        if(followButton!=null){
            followButton.click();
        }
        List<UiObject2> childPart2 = getCommentsParent().getChildren();
        UiObject2 miniActor = childPart2.get(0);
        miniActor.click();
        String s = getObjectById(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_NAME).getText();
        clickByClass("android.widget.ImageView", 2);
        return followOrUnFollow(s);
    }
    //查找是否关注/取消某人
    public static boolean followOrUnFollow(String target_nick_name) throws UiObjectNotFoundException{
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        boolean isFollow = false;
        MeAction.navToFollowing();
        waitUntilFind(MePage.FOLLOWERING_VIEW, 6000);
        UiObject expectObj = scrollAndGetUIObject(target_nick_name);
        if (expectObj != null) {
            if (expectObj.exists()) {
                Spoon.screenshot("swip_to_find", target_nick_name + "已经关注");
                isFollow = true;
            }else {
                isFollow = false;
                Asst.fail("Follow_Fail_"+target_nick_name);
            }
        } else {
            Spoon.screenshot("swip_to_find", target_nick_name + "已经取消关注");
            isFollow = true;
        }
        return isFollow;
    }
    //取消关注评论者
    public static boolean cancelFollow() throws UiObjectNotFoundException{
        List<UiObject2> childPart = getCommentsParent().getChildren();
        UiObject2 miniActor = childPart.get(0);
        miniActor.click();
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_FOLLOW));
        UiObject follow = u.getChild(new UiSelector().textContains("Follow"));
        if(follow.getText().equals("Following")){
            clickById(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_FOLLOW);
        }
        clickByClass("android.widget.ImageView", 2);
        String s = getObjectById(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_NAME).getText();
        return followOrUnFollow(s);
    }
    //跳转到Likes界面
    public static void navToLikes(){
        clickById(MePage.NOTIFICATION_OTHER);
        waitTime(3);
        clickByText("Likes");
    }
    //获取点赞界面关注按钮
    public static boolean getFollowLikes() throws UiObjectNotFoundException{
        cancelFollow();
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToNotifications();
        navToLikes();
        waitTime(5);
        List<UiObject2> childPart = getCommentsParent().getChildren();
        List<UiObject2> litterChild = childPart.get(1).getChildren().get(0).getChildren();
        UiObject2 followButton = litterChild.get(1);
        if(followButton!=null){
            followButton.click();
        }
        List<UiObject2> childPart2 = getCommentsParent().getChildren();
        UiObject2 miniActor = childPart2.get(1);
        miniActor.click();
        String s = getObjectById(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_NAME).getText();
        clickByClass("android.widget.ImageView", 2);
        return followOrUnFollow(s);
    }
    //跳转到Notification界面
    public static void navToNotification(){
        clickById(MePage.NOTIFICATION_OTHER);
    }
}
