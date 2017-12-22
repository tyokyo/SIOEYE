package cn.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.logging.Logger;

import bean.FollowingBean;
import ckt.annotation.PerformanceTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.DiscoverAction;
import cn.action.FollowersAction;
import cn.action.FollowingAction;
import cn.action.MainAction;
import cn.action.MeAction;
import cn.action.SettingAction;
import cn.page.App;
import cn.page.MePage;
import cn.page.PlayPage;

/**
 * Created by zyj on 2017/12/20.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class BlackListCase extends VP2{
    Logger logger = Logger.getLogger(BroadCastsCase.class.getName());

    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        //确保App 处于登录状态
        AccountAction.inLogin();
    }
    /**推荐关注加入到黑名单
     * created by zyj 2017.12.20
     * */
    @Test
    @PerformanceTest
    public void testAddRecommendToBlackList() throws UiObjectNotFoundException {
        MainAction.clickDiscover();
       int count= DiscoverAction.countRecommendList();
       if (count>=1){
           String expect_name = DiscoverAction.navToRecommendList(0, 1);
           DiscoverAction.addBlackList();
           MainAction.clickMe();
           SettingAction.navToBlackList(); //进入黑名单检查
           Asst.assertTrue(text_exists(expect_name));
       }
    }
    /**
     * 从黑名单移除
     *created by zyj 2017.12.21 */
    @Test
    @PerformanceTest
    public void testRemoveFromBlacklist() throws UiObjectNotFoundException {
        SettingAction.navToBlackList();
        int remove_before=getObject2ById(MePage.ID_BLACKLIST).getChildCount();
        if(remove_before>1){    //黑名单中有好友
            SettingAction.removeFromBlackList();
            int remove_after=getObject2ById(MePage.ID_BLACKLIST).getChildCount();
            Asst.assertEquals("移除成功",remove_before-1,remove_after);
        }
    }
    /**
     * 进入黑名单用户主页移除
     * *created by zyj 2017.12.21 */
    @Test
    @PerformanceTest
    public void testRemoveFromProfile() throws UiObjectNotFoundException {
        SettingAction.navToBlackList();
        int count= getObject2ById(MePage.ID_BLACKLIST).getChildCount();
        if (count>1){
            getObject2ById(MePage.ID_BLACKLIST).getChildren().get(1).click();  //进入主页
            waitTime(3);
            UiObject2 object=getObject2ById(MePage.ID_PROFILE_FOLLOW).getParent().getChildren().get(0);
            object.click(); //点击更多
            waitTime(3);
            getObject2ById(MePage.MORE_OPTION_LIST).getChildren().get(1).click();
            waitTime(3);  //点击移除黑名单
            object.click(); //再次点击更多
            waitTime(3);
            Asst.assertTrue(text_exists("Add to blacklist"));
        }
    }
    /**拉黑关注列表中的好友
     *created by zyj 2017.12.21 */
    @Test
    @PerformanceTest
    public void testFollowBlackList() throws UiObjectNotFoundException, IOException {
        MeAction.navToFollowing();
        int following_size= FollowingAction.getFollowingSize();
        if (following_size>=1){
            FollowingBean followingBean =FollowingAction.randomFollowingUser();
            //选择一个用户
            followingBean.click();
            waitTime(3);
            UiObject2 object=getObject2ById(MePage.ID_PROFILE_FOLLOW).getParent().getChildren().get(0);
            object.click(); //点击更多
            waitTime(3);
            getObject2ById(MePage.MORE_OPTION_LIST).getChildren().get(1).click(); //点击拉黑
            waitTime(3);
            object.click(); //再次点击更多
            waitTime(3);
            Asst.assertTrue(text_exists("Remove from blacklist"));
          }
    }
    /**拉黑粉丝
    *created by zyj 2017.12.22 */
    @Test
    @PerformanceTest
    public void testFollowerBlackList() throws UiObjectNotFoundException, IOException {
        MeAction.navToFans();
        int follower_size= FollowingAction.getFollowingSize();
        if (follower_size>=1){
            //滑动选择粉丝
            MeAction.swipeUpDown(MePage.FANS_SWIPE_LAYOUT,10);
            waitTime(3);
            FollowingBean followingBean =FollowingAction.randomFansUser();
            //随机选择一个fans
            followingBean.click();
            waitTime(3);
            UiObject2 object=getObject2ById(MePage.ID_PROFILE_FOLLOW).getParent().getChildren().get(0);
            object.click(); //点击更多
            waitTime(3);
            getObject2ById(MePage.MORE_OPTION_LIST).getChildren().get(1).click(); //点击拉黑
            waitTime(3);
            object.click(); //再次点击更多
            waitTime(3);
            Asst.assertTrue(text_exists("Remove from blacklist"));
        }
    }
    /**观看发现界面视频，在简介中拉黑主播
     * *created by zyj 2017.12.22 */
    @Test
    @PerformanceTest
    public void testWatchVideoAddBlack() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        DiscoverAction.navToPlayVideo();
        //点击简介
        FollowersAction.clickToAbout();
        waitUntilFind(PlayPage.PLAY_ABOUT,3000);
        String expect_name=getObject2ById(PlayPage.ABOUT_NICKNAME).getText(); //获取主播昵称
        UiObject2 object=getObject2ById(PlayPage.ABOUT_SIOEYE_ID);
        object.getParent().getChildren().get(0).click();//点击头像
        waitTime(3);
        DiscoverAction.addBlackList();
        gDevice.pressBack();
        MainAction.clickMe();
        SettingAction.navToBlackList(); //进入黑名单检查
        waitTime(3);
        Asst.assertTrue(text_exists(expect_name));
    }

}
