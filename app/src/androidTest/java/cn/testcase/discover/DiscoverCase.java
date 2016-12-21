package cn.testcase.discover;

import android.graphics.Rect;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.ImageView;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.BroadcastAction;
import cn.action.DiscoverAction;
import cn.action.MainAction;
import cn.page.AccountPage;
import cn.page.App;
import cn.page.DiscoverPage;
import cn.page.MePage;
import cn.page.Other;
import iris4G.page.NavPage;
import usa.action.Nav;


/**
 * Created by Caibing.yin on 2016/11/5.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
/*发现
播放视频
滑动视频
验证推荐列表
* */
public class DiscoverCase extends VP2 {
    Logger logger = Logger.getLogger(DiscoverCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    @Test
   /**
    * case1、单击搜索图标
    * */
    public void testOneClickSearch() throws IOException {
        DiscoverAction.navToSearch();
        waitTime(1);
        if(!getObjectById(DiscoverPage.ID_SEARCH_FILTER_INPUT).exists()){
            Spoon.screenshot("Go to SearchActivity Fail","跳转失败");
            makeToast("跳转失败",5);
            Assert.fail("跳转失败");
        }
    }
    @Test
    /**
     * case2、双击搜索图标
     */
    public void testDoubleClickSearch() throws IOException, UiObjectNotFoundException {
        MainAction.clickDiscover();
        UiObject2 frameLayout=getObject2ById(DiscoverPage.APP_TITLE).getParent();
        UiObject2 searchObject=frameLayout.findObject(By.clazz(android.widget.ImageView.class));
        Rect searchRect=searchObject.getVisibleBounds();
        //double click
        clickRect(searchRect);
        clickRect(searchRect);
        waitTime(1);
        //验证进入搜索界面
        if(!id_exists(DiscoverPage.ID_SEARCH_FILTER_INPUT)){
            Spoon.screenshot("navToSearchPage","跳转失败");
            Assert.fail("跳转失败");
        }
    }
    @Test
    /**
     *case3、下拉刷新、在discover界面手指从上往下滑动
     * 思路：通过判断刷新前后的推介列表的头像昵称是否相同来判定是否刷新，因为刷新后，
     * 第一个头像的昵称是不一样的
     */
    public void testFlush() throws UiObjectNotFoundException, IOException {
        MainAction.clickDiscover();
        waitTime(2);
        // UiObject Discover
        getObjectById(DiscoverPage.ID_DISCOVER_MAIN_CONTENT).swipeDown(50);
        getObjectById(DiscoverPage.ID_DISCOVER_MAIN_CONTENT).swipeDown(50);
        getObjectById(DiscoverPage.ID_DISCOVER_MAIN_CONTENT).swipeDown(50);
        getObjectById(DiscoverPage.ID_DISCOVER_MAIN_CONTENT).swipeDown(50);
        waitTime(2);
        Spoon.screenshot("swipe_Down");
        UiObject recommend_list = getObjectById(DiscoverPage.ID_MAIN_TAB_RECOMMAND_LIST);
        String Expect_nickname = recommend_list.getChild(new UiSelector().index(0)).getText();
        getObjectById(DiscoverPage.ID_DISCOVER_MAIN_CONTENT).swipeDown(50);
        waitTime(2);
        String Active_nickname = recommend_list.getChild(new UiSelector().index(0)).getText();
        Boolean Result = Expect_nickname.equals(Active_nickname);
        if(!Result) {
            Spoon.screenshot("testFlush_fail", "下拉刷新失败");
            makeToast("下拉刷新失败", 3);
            Assert.fail("下拉刷新失败");
        }
        getObjectById(DiscoverPage.ID_DISCOVER_MAIN_CONTENT).swipeUp(50);
        getObjectById(DiscoverPage.ID_DISCOVER_MAIN_CONTENT).swipeUp(50);
        getObjectById(DiscoverPage.ID_DISCOVER_MAIN_CONTENT).swipeUp(50);
        getObjectById(DiscoverPage.ID_DISCOVER_MAIN_CONTENT).swipeUp(50);
        Spoon.screenshot("swipe_Up");
    }
    @Test
    /**
     *
     *case4.来回频繁切换主界面
     *Result：观察APP响应情况， APP迅速响应对应操作
     */
    public void testSwipe() throws UiObjectNotFoundException {
        for (int i = 0; i <10 ; i++) {
            MainAction.navToDiscover();
            MainAction.navToMe();
            MainAction.navToLive();
            MainAction.navToDevice();
            MainAction.navToDiscover();
        }
        Asst.assertEquals("navToDiscover",true,id_exists(MePage.ID_MAIN_TAB_DISCOVER));
    }
    //play the first video in discover page
    @Test
    public void testViewVideo() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        UiObject2 swipe_target=getObject2ById(DiscoverPage.ID_SWIPE_TARGET);
        List<UiObject2> linearLayouts=swipe_target.findObjects(By.clazz(android.widget.RelativeLayout.class));
        int size=linearLayouts.size();
        linearLayouts.get(size-1).click();
        BroadcastAction.waitBroadcastLoading();
        gDevice.wait(Until.gone(By.res(MePage.BROADCAST_VIEW_VIDEO_LOADING)),120000);
        Asst.assertEquals("加载2分钟",false,id_exists(MePage.BROADCAST_VIEW_VIDEO_LOADING));
        Spoon.screenshot("testViewVideo");
        gDevice.pressBack();
    }
    @Test
    /**
     * 未登陆点击输入框
     * 1、未登录状态下在discover界面点击任意视频进入观看
     * 2、点击输入框
     * Result:弹出登陆界面
     * */
    public void testClickInput () throws UiObjectNotFoundException {
        //注销账号
        AccountAction.logOutAccount();
        //进入-发现
        MainAction.clickDiscover();
        //播放一个视频
        DiscoverAction.navToPlayVideo();
        //等待连接聊天室
        BroadcastAction.waitBroadcastLoading();
        //点击评论框
        clickById(Other.chattextfield);
        //弹出登陆界面
        waitUntilFind(AccountPage.ACCOUNT_WEIXIN,5000);
        Spoon.screenshot("loginIn_page");
        Asst.assertFalse("ClickInputFail",!id_exists(AccountPage.ACCOUNT_WEIXIN));
    }
}

