package usa.testcase.discover;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import com.squareup.spoon.Spoon;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.logging.Logger;

import ckt.base.VP2;
import usa.action.DiscoverAction;
import usa.page.App;
import usa.page.Device;
import usa.page.Discover;
import usa.page.Me;
import usa.page.Watch;
import usa.testcase.me.ActivityCase;

/**
 * Created by caibing.yin on 2016/11/5.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class DiscoverCase extends VP2 {
    Logger logger = Logger.getLogger(ActivityCase.class.getName());
    @Before
    public  void setup(){
        openAppByPackageNameInLogin(App.SIOEYE_PACKAGE_NAME_USA);
    }
    @Test
   /**
    * case1、单击搜索图标
    * */
    public void testOneClickSearch() throws IOException {
        DiscoverAction.navToSearch();
        waitTime(1);
        if(!getObjectById(Discover.ID_SEARCH_FILTER_INPUT).exists()){
            Spoon.screenshot("Go to SearchActivity Fail","跳转失败");
            makeToast("跳转失败",5);
            Assert.fail("跳转失败");
        }
    }
    @Test
    /**
     * case2、双击搜索图标
     */
    public void testDoubleClickSearch() throws IOException {
        DiscoverAction.navToSearch();
        DiscoverAction.navToSearch();
        waitTime(1);
        if(!getObjectById(Discover.ID_SEARCH_FILTER_INPUT).exists()){
            Spoon.screenshot("Go to SearchActivity Fail","跳转失败");
            makeToast("跳转失败",5);
            Assert.fail("跳转失败");
        }
    }
    @Test
    /**
     *case3、下拉刷新、在discover界面手指从上往下滑动
     * 思路：通过判断刷新前后的Recommand_list的头像昵称是否相同来判定是否刷新，因为刷新后，
     * 第一个头像的昵称是不一样的
     */
    public void testFlush() throws UiObjectNotFoundException, IOException {
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        waitTime(2);
        // UiObject Discover
        getObjectById(Discover.ID_DISCOVER_MAIN_CONTENT).swipeDown(50);
        waitTime(2);
        UiObject Recommand_list = getObjectById(Discover.ID_MAIN_TAB_RECOMMAND_LIST);
        String Expect_nickname = Recommand_list.getChild(new UiSelector().index(0)).getText();
        getObjectById(Discover.ID_DISCOVER_MAIN_CONTENT).swipeDown(50);
        waitTime(2);
        String Active_nickname = Recommand_list.getChild(new UiSelector().index(0)).getText();
        Boolean Result = Expect_nickname.equals(Active_nickname);
        if(!Result) {
            Spoon.screenshot("testFlush_fail", "下拉刷新失败");
            makeToast("下拉刷新失败", 3);
            Assert.fail("下拉刷新失败");
        }
    }
    @Test
    /**
     *
     *case4.来回频繁切换主界面
     *Result：观察APP响应情况， APP迅速响应对应操作
     */
    public void testSwipe() {
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        clickById(Discover.ID_MAIN_TAB_WATCH);
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Discover.ID_MAIN_TAB_DECICE);
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        clickById(Discover.ID_MAIN_TAB_WATCH);
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Discover.ID_MAIN_TAB_DECICE);
    }

}

