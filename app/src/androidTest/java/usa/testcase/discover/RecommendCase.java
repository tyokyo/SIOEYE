package usa.testcase.discover;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.util.List;

import ckt.base.VP2;
import usa.action.DiscoverAction;
import usa.action.MeAction;
import usa.page.App;
import usa.page.Discover;
import usa.page.Me;

/**
 * Created by user on 2016/11/05   .
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class RecommendCase extends VP2 {
    @Before
    public void setup() {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }

    @Test
    public void testSingleClickRecommandList0() throws UiObjectNotFoundException {
        //Sginle Check Recommend list 0单击推荐列表第一个人，检查弹出框PROFILE_MINI_HOME是否正确
        String expect_name=DiscoverAction.navToRecommendList(0,1);
        //两个参数分别为推荐列表第几个用户（0-3）和点击几次（1-2）
        String active_name=getObjectById(Discover.ID_MAIN_TAB_PROFILE_MINI_NAME).getText();
        Spoon.screenshot("testSingleClickRecommendList0");
        Asst.assertEquals("点击的推荐用户名与弹出信息框是否一致",expect_name,active_name);
        clickByClass("android.widget.ImageView",2);
        //关闭弹出框
        }
    @Test
    public void testDoubleClickRecommand0() throws UiObjectNotFoundException {
        //double Check Recommend list 0双击推荐列表第二个人，检查是否正确弹出框PROFILE_MINI_HOME是否正确
        String expect_name=DiscoverAction.navToRecommendList(1,2);
        //两个参数分别为推荐列表第几个用户（0-3）和点击几次（1-2）
        String active_name=getObjectById(Discover.ID_MAIN_TAB_PROFILE_MINI_NAME).getText();
        Spoon.screenshot("testDoubleClickRecommend0");
        Asst.assertEquals("点击的推荐用户名与弹出信息框是否一致",expect_name,active_name);
        clickByClass("android.widget.ImageView",2);
        //关闭弹出框
    }
    @Test
    //将第一个推荐用户加为好友，检查该用户followers有没有增加;检查自己好友列表有没有增加该好友
    public void testAddFriendsRecommand0() throws UiObjectNotFoundException{
        String expect_name=DiscoverAction.navToRecommendList(1,1);
        //两个参数分别为推荐列表第几个用户（0-3）和点击几次（1-2）
        DiscoverAction.checkMiniProfileNumFollowerAddOneAfterFollow();
        DiscoverAction.checkAddFriendsInMyFollowing(expect_name);
    }
    @Test
    //滑动
    public void testSwipRecommandAanADD1() throws UiObjectNotFoundException{
        DiscoverAction.scrollRecommandList();
        String expect_name=DiscoverAction.navToRecommendList(1,1);
        DiscoverAction.checkMiniProfileNumFollowerAddOneAfterFollow();
        DiscoverAction.checkAddFriendsInMyFollowing(expect_name);
    }
}
