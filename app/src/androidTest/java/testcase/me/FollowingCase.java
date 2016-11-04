package testcase.me;

import android.graphics.Point;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import action.FollowingAction;
import action.Nav;
import bean.FollowingBean;
import ckt.base.VP2;
import page.App;
import page.Me;
import page.Watch;

/**
 * Created by elon on 2016/10/18.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class FollowingCase extends VP2{
    private Logger logger =Logger.getLogger(FollowingCase.this.getClass().getName());
    //private Point p;
    @Before
    public  void setup() throws UiObjectNotFoundException, IOException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);

    }
    //验证-删除一个关注的用户
    @Test
    public void testA1DeleteFollowingUser() throws IOException, UiObjectNotFoundException {
        Nav.navToFollowing();
        if (getObjectById(Me.FOLLOWERING_AVATAR).exists()){
            //取消关注第一个
            clickById(Me.FOLLOWERING_AVATAR);
            String name = getUiObjectById(Me.FOLLOWERING_USERNAME).getText();
            //取消关注按钮
            clickById(Me.FOLLOWERING_DELETE);
            gDevice.pressBack();
            if (getUiObjectByText(name).exists()){
                Spoon.screenshot("delete_"+name);
                Assert.fail("delete failed");
            }
        }else{
            logger.warning("no user to be following");
            Spoon.screenshot("no_data");
        }
    }
    //验证-删除多个关注的用户 只剩下3个用户
    @Test
    public void testA1DeleteFollowingUsersExcept3() throws IOException, UiObjectNotFoundException {
        Nav.navToFollowing();
        while (true){
            int avatars =gDevice.findObjects(By.res(Me.FOLLOWERING_AVATAR)).size();
            if (avatars>=4){
                //取消关注第一个
                clickById(Me.FOLLOWERING_AVATAR);
                String name = getUiObjectById(Me.FOLLOWERING_USERNAME).getText();
                //取消关注按钮
                clickById(Me.FOLLOWERING_DELETE);
                gDevice.pressBack();
                waitTime(2);
                UiObject find_ui =scrollAndGetUIObject(name);
                if (find_ui!=null&&find_ui.exists()){
                    Spoon.screenshot("delete_"+name);
                    Assert.fail("delete failed");
                }else{
                    logger.info("delete-"+name);
                }
            }else{
                break;
            }
        }

    }
    //验证-添加trending关注
    @Test
    public void testA2AddTrendingUserFromAvatar() throws IOException, UiObjectNotFoundException {
        Nav.navToWatchSearch();
        //添加关注第一个
        clickById(Watch.WATCH_USER_AVATAR);
        String name = getUiObjectById(Watch.WATCH_USER_MINI_NAME).getText();
        //关注按钮
        clickById(Watch.WATCH_USER_MINI_FOLLOW);
        gDevice.pressBack();
        getObjectById(Watch.WATCH_SEARCH_TRENDING_LIST).swipeUp(2);
        waitTime(3);
        makeToast(name,3);

        gDevice.pressBack();
        Nav.navToWatchSearch();
        waitTime(3);
        if (getUiObjectByText(name).exists()){
            Spoon.screenshot(gDevice,"add"+name.replaceAll("@",""));
            Assert.fail("add failed");
        }
    }
    //验证-添加trending关注
    @Test
    public void testA3AddTrendingUserFromUserFollow() throws IOException, UiObjectNotFoundException {
        Nav.navToWatchSearch();
        //取消关注第一个
        clickById(Watch.WATCH_USER_AVATAR);
        String name = getUiObjectById(Watch.WATCH_USER_MINI_NAME).getText();
        //关注按钮
        clickById(Watch.WATCH_USER_MINI_FOLLOW);
        gDevice.pressBack();
        getObjectById(Watch.WATCH_SEARCH_TRENDING_LIST).swipeUp(2);
        waitTime(3);
        gDevice.pressBack();
        Nav.navToWatchSearch();
        waitTime(3);

        if (getUiObjectByText(name).exists()){
            Spoon.screenshot(gDevice,"add_"+name);
            Assert.fail("add failed");
        }
    }
    //验证-搜索功能(sioeye id)
    @Test
    public void testFollowingSearchSioEyeID() throws IOException, UiObjectNotFoundException {
        String account_id="tyo000";
        FollowingAction.searchFollowingUser(account_id);
        int follows=getObjectsById(Watch.WATCH_SEARCH_USER_FOLLOW).size();
        makeToast("search "+follows,4);
        //Assert.assertEquals("search tyo000",follows,1);
        clickById(Watch.WATCH_USER_AVATAR);
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW_VIDEO_LOADING)),40000);
        boolean isFind=getUiObjectByText("@"+account_id).exists();
        Asst.assertTrue("account",isFind);
        Spoon.screenshot(gDevice,"tyo000");
    }
    //验证-搜索功能(sioeye id)
    @Test
    public void testFollowingSearchName() throws IOException, UiObjectNotFoundException {
        String name="River";
        FollowingAction.searchFollowingUser(name);
        int follows=getObjectsById(Watch.WATCH_SEARCH_USER_FOLLOW).size();
        makeToast("search "+follows,4);
        //Assert.assertEquals("search name "+name,follows,1);
        clickById(Watch.WATCH_USER_AVATAR);
        String userName=getObjectById(Watch.WATCH_USER_MINI_NAME).getText();
        Assert.assertEquals("account",userName,name);
        gDevice.pressBack();
        gDevice.pressBack();
    }

    //添加关注：following->following->add user
    @Test
    public void testAddFollowingFrom2Following() throws UiObjectNotFoundException {
        //进入Following
        Nav.navToFollowing();
        //如果有用户可以被添加
        if (getObjectById(Me.FOLLOWERING_AVATAR).exists()){
            //点击第一个用户头像
            clickById(Me.FOLLOWERING_AVATAR);
            //获取用户名字
            String following_name = getObjectById(Me.FOLLOWERING_USERNAME).getText();
            logger.info("following_name-"+following_name);
            //进入关注用户的关注列表
            clickById(Me.ID_ME_TYPE_FOLLOWING);
            //是否存在关注用户
            waitTime(2);
            if (getObjectById(Me.USER_FOLLOW).exists()){
                //选择一个用户,添加关注
                clickById(Me.USER_FOLLOW);
                //点击一个用户头像
                clickById(Me.FOLLOWERING_AVATAR);
                //获取用户名字
                gDevice.wait(Until.findObject(By.res(Me.FOLLOWERING_USERNAME)),40000);
                String following_following_name = getObjectById(Me.FOLLOWERING_USERNAME).getText();
                logger.info("following_following_name-"+following_following_name);
                //获取用户id
                String following_following_id = getObjectById(Me.SIOEYE_USER_ID).getText();
                logger.info("following_following_id-"+following_following_id);

                openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
                Nav.navToFollowing();
                Asst.assertTrue("关注成功",FollowingAction.isUserHasFollowing(following_following_name));
            }
        }
    }
    //观看following 视频
    @Test
    public void testViewVideoFromFollowing() throws UiObjectNotFoundException, IOException {
        Nav.navToFollowing();
        //随机选择一个Follow用户
        FollowingBean f_bean = FollowingAction.randomFollowingUser();
        logger.info(f_bean.toString());
        f_bean.click();
        waitUntilFind(Me.USER_FOLLOW_LIST,60);
        gDevice.wait(Until.findObject(By.res(Me.USER_FOLLOW_LIST)),60000);
        if (getObjectById(Me.BROADCAST_INVISIBLE).exists()){
            Spoon.screenshot("invisible");
            Asst.fail("Privacy broadcasts are invisible");
        }else {
            getUiObjectById(Me.USER_FOLLOW_LIST).swipeUp(100);
            getUiObjectById(Me.USER_FOLLOW_LIST).swipeUp(100);
            int videos =getObject2ById(Me.USER_FOLLOW_LIST).findObjects(By.clazz("android.widget.ImageView")).size();
            //播放一个视频
            if (videos>=1){
                getObject2ById(Me.USER_FOLLOW_LIST).findObject(By.clazz("android.widget.ImageView")).click();
                gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW_VIDEO_LOADING)),60000);
                waitTime(20);
                Spoon.screenshot("play_video","play 20s");
                gDevice.pressBack();
            }
        }
    }
}


