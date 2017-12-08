package cn.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import bean.PopInfoBean;
import ckt.base.VP2;
import cn.page.App;
import cn.page.DiscoverPage;
import cn.page.MePage;
import cn.page.NotificationPage;

/**
 * Created by jqx on 2017/8/18.
 */
public class NotificationAction extends VP2 {
    private static Logger logger = Logger.getLogger(NotificationAction.class.getName());
    //获取评论数
    public static int replyCommentSize(){
        waitHasObject(MePage.BROADCAST_CONTENT,50000);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz("android.widget.LinearLayout"));
        int size = lisCollect.size();
        logger.info("getCommentsSize:"+size);
        return  size;
    }
    public static PopInfoBean getUserPopInfo() throws UiObjectNotFoundException {
        PopInfoBean pBean = new PopInfoBean();
        String nickName=getUiObjectById(NotificationPage.NTF_NICK_NAME).getText();
        String video=getUiObjectById(NotificationPage.MINI_NUM_BROADCAST).getText();
        String following=getUiObjectById(NotificationPage.MINI_NUM__FOLLOWING).getText();
        String follower=getUiObjectById(NotificationPage.MINI_NUM__FOLLOWER).getText();
        pBean.setNickName(nickName);
        pBean.setVideo(cover(video));
        pBean.setFollower(cover(follower));
        pBean.setFollowing(cover(following));
        logger.info(pBean.toString());
        return  pBean;
    }
    //获取所有的notification(未关注的) 不包括Sioeye Team
    public static List<UiObject2> getAllNotificationsCanBeFollowed() throws UiObjectNotFoundException {
        List<UiObject2> notifications=null;
        List<UiObject2> results=new ArrayList<UiObject2>();
        notifications=gDevice.findObject(By.res(NotificationPage.NTF_LIST)).findObjects(By.depth(1).clazz(android.widget.LinearLayout.class));
        logger.info(notifications.size()+"");
        int size = notifications.size();
        for (int i = 0; i < size; i++) {
            UiObject2 not = notifications.get(i);
            if ( not.hasObject(By.res(NotificationPage.NTF_NICK_NAME))){
                String nickName=not.findObject(By.res(NotificationPage.NTF_NICK_NAME)).getText();
                if (!nickName.startsWith("Sioeye")){
                    UiObject2 follow =getNotificationAddDelFollowBtn(not);
                    if (follow!=null){
                        results.add(not);
                    }
                }
            }
        }
        return results;
    }
    //获取所有的notification(关注的) 不包括Sioeye Team
    public static List<UiObject2> getAllNotificationsHasBeFollowed() throws UiObjectNotFoundException {
        List<UiObject2> notifications=null;
        List<UiObject2> results=new ArrayList<UiObject2>();
        notifications=gDevice.findObject(By.res(NotificationPage.NTF_LIST)).findObjects(By.depth(1).clazz(android.widget.LinearLayout.class));
        logger.info(notifications.size()+"");
        int size = notifications.size();
        for (int i = 0; i < size; i++) {
            UiObject2 not = notifications.get(i);
            if ( not.hasObject(By.res(NotificationPage.NTF_NICK_NAME))){
                String nickName=not.findObject(By.res(NotificationPage.NTF_NICK_NAME)).getText();
                if (!nickName.startsWith("Sioeye")){
                    UiObject2 follow =getNotificationAddDelFollowBtn(not);
                    if (follow==null){
                        results.add(not);
                    }
                }
            }
        }
        return results;
    }
    //获取通知对象
    public static UiObject2 getRandomNotificationObject(int index){
        List<UiObject2> lisCollect =gDevice.findObject(By.res(NotificationPage.NTF_LIST)).findObjects(By.depth(1).clazz(android.widget.LinearLayout.class));
        UiObject2 notification = lisCollect.get(index);
        return  notification;
    }
    //获取通知->头像
    public static UiObject2 getNotificationIcon(UiObject2 notification){
        //第一个imageView
        UiObject2 avatar = notification.findObject(By.depth(1).clazz(android.widget.ImageView.class));
        return  avatar;
    }
    //获取通知->关注/取消关注按钮
    public static UiObject2 getNotificationAddDelFollowBtn(UiObject2 notification){
        //ViewGroup ->第一个imageView
        UiObject2 followBtn=null;
        try {
            followBtn = notification.findObject(By.clazz(android.view.ViewGroup.class)).getParent().findObject(By.depth(1).clazz(android.widget.ImageView.class));
        }catch (Exception e){
            e.printStackTrace();
        }
        return  followBtn;
    }
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
        UiObject2 miniActor = childPart2.get(0);
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
