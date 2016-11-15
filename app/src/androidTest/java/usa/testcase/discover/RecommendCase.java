package usa.testcase.discover;

import android.graphics.Rect;
import android.os.RemoteException;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
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
import java.util.logging.Logger;

import ckt.base.VP2;
import usa.action.DiscoverAction;
import usa.action.MeAction;
import usa.page.App;
import usa.page.Discover;
import usa.page.Me;
import usa.testcase.me.ActivityCase;

/**
 * Created by user on 2016/11/05   .
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class RecommendCase extends VP2 {
    Logger logger = Logger.getLogger(RecommendCase.class.getName());
    @Before
    public void setup() {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }

    @Test
    /**case1 单击推荐列表头像
     * 唤出对应对象的个人资料页面；
     * 单击推送列表好友后检查弹窗好友信息
     */
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
    /**case2 双击推荐列表头像
     * 唤出对应对象的个人资料页面；
     * 双击推送列表好友后检查弹窗好友信息
     */
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
    /**case3添加推荐列表第一个为好友
     * 检查该用户followers有没有增加;
     * 检查自己好友列表有没有增加该好友
     * 最后取消关注新添加的好友
     */
    public void testAddFriendsRecommand0() throws UiObjectNotFoundException{
        String expect_name=DiscoverAction.navToRecommendList(1,1);
        //两个参数分别为推荐列表第几个用户（0-3）和点击几次（1-2）
        DiscoverAction.checkMiniProfileNumFollowerAddOneAfterFollow();
        DiscoverAction.checkAddFriendsInMyFollowing(expect_name);
        gDevice.pressBack();
        DiscoverAction.deleteNewFollowing(expect_name);
    }
    @Test
    /**case4 滑动推荐列表后关注第二个推荐用户
     * 检查该用户followers有没有增加;
     *  并到Me关注好友去寻找是否添加成功
     *  最后取消关注该好友
     */
    public void testSwipRecommandAanADD1() throws UiObjectNotFoundException{
        DiscoverAction.scrollRecommandList();
        String expect_name=DiscoverAction.navToRecommendList(1,1);
        DiscoverAction.checkMiniProfileNumFollowerAddOneAfterFollow();
        DiscoverAction.checkAddFriendsInMyFollowing(expect_name);
        gDevice.pressBack();
        DiscoverAction.deleteNewFollowing(expect_name);
    }
    @Test
    /**case5 检查nick name长度是否≤30
     * [字体颜色，字体大小]无法实现，请检查截图或者
     */
    public void testCheckRecommandProfileMiniName() throws UiObjectNotFoundException{
        String expect_name=DiscoverAction.navToRecommendList(1,1);
        if (expect_name.length()>30){
            Spoon.screenshot("testCheckRecommandProfileMiniName","Fail-Nick Name的字符串大于30个");
            Assert.fail("Nick Name的字符串大于30个");
        }
        clickByClass("android.widget.ImageView",2);
        //关闭弹出框
    }
    @Test
    /**case6 刷新discover后检查推荐follow列表是否更新
     * 检查刷新前后，前三个用户是否完全一致，判断是否刷新成功
     */
    public void testRefreshRecommandlist() throws UiObjectNotFoundException{
        String recommand_list_first_nickname_original=DiscoverAction.navToRecommendList(0,1);
        clickByClass("android.widget.ImageView",2);
        String recommand_list_second_nickname_original=DiscoverAction.navToRecommendList(1,1);
        clickByClass("android.widget.ImageView",2);
        String recommand_list_3rd_nickname_original=DiscoverAction.navToRecommendList(2,1);
        clickByClass("android.widget.ImageView",2);
        Spoon.screenshot("original_recommand_list","刷新前推荐列表");
        getObjectById(Discover.ID_DISCOVER_MAIN_CONTENT).swipeDown(50);
        waitTime(2);
        String recommand_list_first_nickname_new=DiscoverAction.navToRecommendList(0,1);
        clickByClass("android.widget.ImageView",2);
        String recommand_list_second_nickname_new=DiscoverAction.navToRecommendList(1,1);
        clickByClass("android.widget.ImageView",2);
        String recommand_list_3rd_nickname_new=DiscoverAction.navToRecommendList(2,1);
        clickByClass("android.widget.ImageView",2);
        Spoon.screenshot("new_recommand_list","刷新后推荐列表");
        if (recommand_list_first_nickname_original.equals(recommand_list_first_nickname_new)){
            if (recommand_list_second_nickname_original.equals(recommand_list_second_nickname_new)){
                if (recommand_list_3rd_nickname_original.equals(recommand_list_3rd_nickname_new)){
                    Assert.fail("刷新后的推荐列表前三位与刷新前一致，请检查图片");
                }
            }
        }
    }
    @Test
    /**case 7 Follow一个推荐列表好友后，刷新discover，
     * 检查该好友是否从discover消失
     * 最后取消关注该好友
     */
    public void testFolledwRecommandThenRefresh() throws UiObjectNotFoundException{
        String expect_name=DiscoverAction.navToRecommendList(1,1);
        //两个参数分别为推荐列表第几个用户（0-3）和点击几次（1-2）
        DiscoverAction.checkMiniProfileNumFollowerAddOneAfterFollow();
        DiscoverAction.checkAddFriendsInMyFollowing(expect_name);
        gDevice.pressBack();
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        Spoon.screenshot("recommand_list","推荐列表中有一个被Floowed");
        getObjectById(Discover.ID_DISCOVER_MAIN_CONTENT).swipeDown(50);
        waitTime(2);
        String new_name=DiscoverAction.navToRecommendList(1,1);
        if (expect_name.equals(new_name)){
            Spoon.screenshot("recommand_list","推荐列表不应该有被Follwed");
            Assert.fail("刷新后的推荐列表中被follow的用户没有消失");
        }
        gDevice.pressBack();
        DiscoverAction.deleteNewFollowing(expect_name);
    }
    @Test
    /*
    关闭推荐列表
    点击推荐列表右上角关闭按钮
    检查推荐列表是否存在
     */
    public void testCloseRecommandList() throws UiObjectNotFoundException{
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        waitUntilFind(Discover.ID_MAIN_TAB_RECOMMAND_LIST,6000);
        List<UiObject2> imageView=getObject2ById(Discover.ID_SWIPE_TARGET).findObjects(By.clazz(android.widget.ImageView.class));
        logger.info(imageView.size()+"");
        imageView.get(9).click();
        waitTime(1);
        if(getObjectById(Discover.ID_MAIN_TAB_RECOMMAND_LIST).exists()){
            Spoon.screenshot("CloseRecommandList_Failed","关闭推荐列表失败");
            Assert.fail("关闭推荐列表失败");
        }
        //Rect closeRectIcon =closeIcon.getVisibleBounds();
        //clickRect(closeRectIcon);
    }
}
