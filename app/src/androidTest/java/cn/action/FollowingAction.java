package cn.action;

import android.graphics.Point;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.spoon.Spoon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import bean.FollowingBean;
import bean.UserBean;
import ckt.base.VP2;
import cn.page.App;
import cn.page.MePage;
import cn.page.WatchPage;
import cn.action.*;

/**
 * Created by elon on 2016/10/27.
 */
public class FollowingAction extends VP2{
    public static Logger logger =Logger.getLogger("FollowingAction");
    //选择一个视频 播放视频
    public static void clickFollowingBroadcast() throws UiObjectNotFoundException {
        UiObject2 list =getObject2ById(MePage.USER_FOLLOW_LIST);
        List<UiObject2> frameLayoutList=list.findObjects(By.clazz(FrameLayout.class));
        List<UiObject2> videoList=new ArrayList<>();
        for (UiObject2 frameLayout:frameLayoutList){
            if (frameLayout.findObjects(By.clazz(RelativeLayout.class)).size()==2){
                videoList.add(frameLayout);
            }
        }
        int size = videoList.size();
        Random random = new Random();
        int index = random.nextInt(size);
        videoList.get(index).click();
    }
    //选择一个用户 查看用户
    public static void clickRandomFollower(FollowingBean followingBean) throws UiObjectNotFoundException {
        scrollAndGetUIObject(followingBean.getName()).clickAndWaitForNewWindow();
    }
    //关注数目
    public static int getFollowingSize(){
        List<UiObject2> lisCollect = gDevice.findObjects(By.res(MePage.FOLLOWERING_AVATAR));
        int size = lisCollect.size();
        return  size;
    }
    public static UserBean getUserInfo() throws UiObjectNotFoundException {
        UserBean userBean = new UserBean();
        String name = getUiObjectById(MePage.FOLLOWERING_USERNAME).getText();
        String id = getUiObjectById(MePage.SIOEYE_USER_ID).getText();
        String zan = getUiObjectById(MePage.SIOEYE_USER_LIKE_COUNT).getText();
        String following = getUiObjectById(MePage.USER_FOLLOWING).getText();
        String followers = getUiObjectById(MePage.USER_FOLLOWERS).getText();
        String broadcasts = getUiObjectById(MePage.USER_BROADCASTS).getText();
        userBean.setName(name);
        userBean.setId(id);
        userBean.setZan(zan);
        userBean.setFollowing(following);
        userBean.setFollowers(followers);
        userBean.setBroadcasts(broadcasts);
        return  userBean;
    }
    //随机选择一个Following 对象
    public static FollowingBean randomFollowingUser() throws IOException, UiObjectNotFoundException {
        FollowingBean followingBean = new FollowingBean();
        waitUntilFind(MePage.FOLLOWERING_VIEW,30000);
        UiObject2 follow_view = getObject2ById(MePage.FOLLOWERING_VIEW);
        List<UiObject2> LinearLayoutList = follow_view.findObjects(By.clazz(LinearLayout.class));
        List<UiObject2> LinearLayoutList_filter=new ArrayList<>();
        int size=0;
        for (UiObject2 linear:LinearLayoutList){
            if (linear.hasObject(By.res(MePage.FOLLOWERING_AVATAR))){
                size=size+1;
                LinearLayoutList_filter.add(linear);
            }
        }
        Random r = new Random();
        int int_r =r.nextInt(size);
        logger.info("size:"+size);
        UiObject2 linearLayout_UiObject2 =LinearLayoutList_filter.get(int_r);
        List<UiObject2> textViews =linearLayout_UiObject2.findObjects(By.clazz(TextView.class));
        String followers_videos=textViews.get(1).getText();
        String name=textViews.get(0).getText();
        followingBean.setName(name);
        followingBean.setAvatar(linearLayout_UiObject2);
        followingBean.setInfo(followers_videos);
        followingBean.setIndex_linearLayout(int_r);
        return  followingBean;
    }
    //随机选择一个fans对象
    public static FollowingBean randomFansUser() throws IOException, UiObjectNotFoundException {
        FollowingBean followingBean = new FollowingBean();
        waitUntilFind(MePage.FANS_VIEW_LIST,30000);
        UiObject2 follow_view = getObject2ById(MePage.FANS_VIEW_LIST);
        List<UiObject2> LinearLayoutList = follow_view.findObjects(By.clazz(LinearLayout.class));
        List<UiObject2> LinearLayoutList_filter=new ArrayList<>();
        int size=0;
        for (UiObject2 linear:LinearLayoutList){
            if (linear.hasObject(By.res(MePage.FOLLOWERING_AVATAR))){
                size=size+1;
                LinearLayoutList_filter.add(linear);
            }
        }
        Random r = new Random();
        int int_r =r.nextInt(size);
        logger.info("size:"+size);
        UiObject2 linearLayout_UiObject2 =LinearLayoutList_filter.get(int_r);
        List<UiObject2> textViews =linearLayout_UiObject2.findObjects(By.clazz(TextView.class));
        String followers_videos=textViews.get(1).getText();
        String name=textViews.get(0).getText();
        followingBean.setName(name);
        followingBean.setAvatar(linearLayout_UiObject2);
        followingBean.setInfo(followers_videos);
        followingBean.setIndex_linearLayout(int_r);
        return  followingBean;
    }
    public static int hasBroadcasts(){
        int following_broadcast=getObject2ById(MePage.USER_FOLLOW_LIST).findObjects(By.clazz(RelativeLayout.class)).size();
        return following_broadcast;
    }
}
