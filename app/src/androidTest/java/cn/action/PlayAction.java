package cn.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.StaleObjectException;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.widget.RelativeLayout;

import com.squareup.spoon.Spoon;

import java.util.List;
import java.util.logging.Logger;

import bean.VideoBean;
import ckt.base.VP2;
import cn.page.MePage;
import cn.page.NewPage;
import cn.page.PlayPage;

/**
 * Created by yajuan on 2017/8/7.
 */

public class PlayAction extends VP2 {
    private static Logger logger = Logger.getLogger(NewAction.class.getName());

    //聊天室里获取点赞数
    public static int getNewZanNumber() {

        // UiObject2 swipe = getObject2ById(NewPage.ID_NEW_VIDEO);
        List<UiObject2> relativeLayouts = getObject2ById(NewPage.ID_NEW_VIDEO).findObjects(By.clazz(RelativeLayout.class).depth(1));
        waitTime(5);
        logger.info(relativeLayouts.size() + "");
        relativeLayouts.get(0).click();
        waitUntilFind(PlayPage.TV_AUCHOR_ID,5000);
        clickById(PlayPage.TV_AUCHOR_ID);
        waitUntilFind(PlayPage.VIDEO_CHAT_NUMBER,5000);
        int like = cover(getObject2ById(PlayPage.VIDEO_CHAT_NUMBER).getText());
        return like;
    }
    //观看视频统计点赞数、观看人数、评论数
    public static VideoBean getNumberPlayVideo() throws UiObjectNotFoundException {
        VideoBean videoBean = new VideoBean();
        FollowersAction.clickToAnchor();
        try {
            waitUntilFind(PlayPage.VIDEO_WATCH_NUMBER,5000);
            waitUntilFind(PlayPage.VIDEO_CHAT_NUMBER,5000);
            waitUntilFind(PlayPage.VIDEO_LIKE_NUMBER,5000);
            //获取观看数
            int watch = cover(getObject2ById(PlayPage.VIDEO_WATCH_NUMBER).getText());
            //获取点赞数
            int zan = cover(getObject2ById(PlayPage.VIDEO_CHAT_NUMBER).getText());
            // 获取评论数
            int comment =cover(getObject2ById(PlayPage.VIDEO_LIKE_NUMBER).getText());
            videoBean.setWatch(watch);
            videoBean.setZan(zan);
            videoBean.setComment(comment);
            //获取点赞数
            logger.info("getNumberPlayVideo-" + videoBean.toString());
            Spoon.screenshot("About");
        } catch (StaleObjectException e) {
            e.printStackTrace();
        }
        return videoBean;
    }
    //聊天室简介中检查时间显示
    public static String getTime() {

        // UiObject2 swipe = getObject2ById(NewPage.ID_NEW_VIDEO);
        List<UiObject2> relativeLayouts = getObject2ById(NewPage.ID_NEW_VIDEO).findObjects(By.clazz(RelativeLayout.class).depth(1));
        waitTime(5);
        logger.info(relativeLayouts.size() + "");
        relativeLayouts.get(0).click();
        waitUntilFind(PlayPage.TV_AUCHOR_ID,5000);
        clickById(PlayPage.TV_AUCHOR_ID);
        waitUntilFind(PlayPage.VIDEO_CHAT_NUMBER,5000);
        String  time = (getObject2ById(PlayPage.PLAY_ABOUT_TIME).getText());
        return time;
    }
    //聊天室简介中获取主播关注信息
    public static String  getFollowing() {

        List<UiObject2> relativeLayouts = getObject2ById(PlayPage.PLAY_ABOUT).findObjects(By.clazz(RelativeLayout.class).depth(2));
        waitTime(5);
        logger.info(relativeLayouts.size() + "");
        int follower_begin;
        int follower_after;
        String follow="";
        for (UiObject2 relativeLayout : relativeLayouts) {
            List<UiObject2> textViews = relativeLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
            if (textViews.size() ==6) {
                follower_begin=cover (textViews.get(5).getText());
                List<UiObject2> imageViews = relativeLayout.findObjects(By.depth(1).clazz(android.widget.ImageView.class));
                if (imageViews.size() ==2) {
                    imageViews.get(1).click();
                    follower_after = cover(textViews.get(5).getText());
                      if (follower_after == follower_begin + 1) {
                         Spoon.screenshot("Following"); //已关注
                          follow="Following";
                          break;
                      }else if(follower_begin == follower_after + 1){
                          Spoon.screenshot("Follow");
                          follow="Follow"; //没有关注
                          break;
                      }
                 }
            }
        }
        return follow;
    }
    //聊天室简介中点击关注图标
    public static void clickFollow(){
        List<UiObject2> relativeLayouts = getObject2ById(PlayPage.PLAY_ABOUT).findObjects(By.clazz(RelativeLayout.class).depth(2));
        waitTime(5);
        logger.info(relativeLayouts.size() + "");
        for (UiObject2 relativeLayout : relativeLayouts) {
            List<UiObject2> imageViews = relativeLayout.findObjects(By.depth(1).clazz(android.widget.ImageView.class));
            if (imageViews.size() ==2) {
                imageViews.get(1).click();
                break;
            }
        }
    }
    //聊天室简介中关注主播
    public static void addFollow(){
       String str= getFollowing();
        if(str=="Follow"){
           clickFollow();   //关注主播
           Spoon.screenshot("Following");
        }else if(str=="Following"){
           Spoon.screenshot("Following");
        }
    }
    //聊天室简介中取消关注主播
    public static void cancelFollow(){
        String str= getFollowing();
        if(str=="Following"){
            clickFollow();   //取消关注主播
            Spoon.screenshot("Follow");
        }else if(str=="Follow"){
            Spoon.screenshot("Follow");
        }
    }
    //全屏播放视频
    public static void fullSreenPlay(){
      //  UiObject2 viewPlay = getObject2ById(PlayPage.)

    }
}
