package cn.testcase.discover;

import android.graphics.Rect;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.DiscoverAction;
import cn.page.App;
import cn.page.DiscoverPage;

/**
 * Created by user on 2016/11/05   .
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class RecommendCase extends VP2 {
    Logger logger = Logger.getLogger(RecommendCase.class.getName());
    @Before
    public void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }

    @Test
    @SanityTest
    @PerformanceTest
    /**case1 单击推荐列表头像
     * 唤出对应对象的个人资料页面；
     * 单击推送列表好友后检查弹窗好友信息
     */
    public void testSingleClickRecommendList0() throws UiObjectNotFoundException {
        //Single Check Recommend list 0单击推荐列表第一个人，检查弹出框PROFILE_MINI_HOME是否正确
        String expect_name = DiscoverAction.navToRecommendList(0, 1);
        //两个参数分别为推荐列表第几个用户（0-3）和点击几次（1-2）
        String active_name = getObjectById(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_NAME).getText();
        Spoon.screenshot("testSingleClickRecommendList0");
        Asst.assertEquals("点击的推荐用户名与弹出信息框是否一致", expect_name, active_name);
        clickByClass("android.widget.ImageView", 2);
        //关闭弹出框
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case2 双击推荐列表头像
     * 唤出对应对象的个人资料页面；
     * 双击推送列表好友后检查弹窗好友信息
     */
    public void testDoubleClickRecommend0() throws UiObjectNotFoundException {
        //double Check Recommend list 0双击推荐列表第二个人，检查是否正确弹出框PROFILE_MINI_HOME是否正确
        String expect_name=DiscoverAction.navToRecommendList(1,2);
        //两个参数分别为推荐列表第几个用户（0-3）和点击几次（1-2）
        String active_name=getObjectById(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_NAME).getText();
        Spoon.screenshot("testDoubleClickRecommend0");
        Asst.assertEquals("点击的推荐用户名与弹出信息框是否一致",expect_name,active_name);
        clickByClass("android.widget.ImageView",2);
        //关闭弹出框
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case3添加推荐列表第一个为好友
     * 检查该用户followers有没有增加;
     * 检查自己好友列表有没有增加该好友
     */
    public void testAddDelFriendsRed() throws UiObjectNotFoundException{
        DiscoverAction.scrollRecommendList();
        String expect_name=DiscoverAction.navToRecommendList(1,1);
        //两个参数分别为推荐列表第几个用户（0-3）和点击几次（1-2）
        DiscoverAction.checkMiniProfileNumFollowerAddOneAfterFollow();
        //验证是否添加成功
        DiscoverAction.checkAddFriendsInMyFollowing(expect_name);
        //取消关注
        DiscoverAction.deleteFollowing(expect_name);
        if (scrollAndGetUIObject(expect_name)!=null){
            Spoon.screenshot("delete_user",expect_name);
            Asst.fail("can not delete user success");
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case4 滑动推荐列表后关注第二个推荐用户
     * 检查该用户followers有没有增加;
     *  并到Me关注好友去寻找是否添加成功
     */
    public void testSwipeRecommendAD() throws UiObjectNotFoundException{
        DiscoverAction.scrollRecommendList();
        String expect_name=DiscoverAction.navToRecommendList(1,1);
        DiscoverAction.checkMiniProfileNumFollowerAddOneAfterFollow();
        DiscoverAction.checkAddFriendsInMyFollowing(expect_name);
        //取消关注
        DiscoverAction.deleteFollowing(expect_name);
        if (scrollAndGetUIObject(expect_name)!=null){
            Spoon.screenshot("delete_user",expect_name);
            Asst.fail("can not delete user success");
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case5 检查nick name长度是否≤30
     * [字体颜色，字体大小]无法实现，请检查截图或者
     */
    public void testCheckProfileMiniName() throws UiObjectNotFoundException{
        String expect_name=DiscoverAction.navToRecommendList(1,1);
        if (expect_name.length()>30){
            Spoon.screenshot("testCheckrecommendProfileMiniName","Fail-Nick Name的字符串大于30个");
            Assert.fail("Nick Name的字符串大于30个");
        }
        clickByClass("android.widget.ImageView",2);
        //关闭弹出框
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case6 刷新discover后检查推荐follow列表是否更新
     * 检查刷新前后，前三个用户是否完全一致，判断是否刷新成功
     */
    public void testRefreshRecommendList() throws UiObjectNotFoundException{
        String recommend_list_first_nickname_original=DiscoverAction.navToRecommendList(0,1);
        clickByClass("android.widget.ImageView",2);
        String recommend_list_second_nickname_original=DiscoverAction.navToRecommendList(1,1);
        clickByClass("android.widget.ImageView",2);
        String recommend_list_3rd_nickname_original=DiscoverAction.navToRecommendList(2,1);
        clickByClass("android.widget.ImageView",2);
        Spoon.screenshot("original_recommend_list","刷新前推荐列表");
        getObjectById(DiscoverPage.ID_DISCOVER_MAIN_CONTENT).swipeDown(50);
        waitTime(2);
        String recommend_list_first_nickname_new=DiscoverAction.navToRecommendList(0,1);
        clickByClass("android.widget.ImageView",2);
        String recommend_list_second_nickname_new=DiscoverAction.navToRecommendList(1,1);
        clickByClass("android.widget.ImageView",2);
        String recommend_list_3rd_nickname_new=DiscoverAction.navToRecommendList(2,1);
        clickByClass("android.widget.ImageView",2);
        Spoon.screenshot("original_recommend_list","刷新后推荐列表");
        if (recommend_list_first_nickname_original.equals(recommend_list_first_nickname_new)
                &&recommend_list_second_nickname_original.equals(recommend_list_second_nickname_new)
                &&recommend_list_3rd_nickname_original.equals(recommend_list_3rd_nickname_new)){
            Spoon.screenshot("after_refresh");
        }else{
            Asst.fail("刷新后的推荐列表前三位与刷新前不一致，请检查图片");
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case 7 Follow一个推荐列表好友后，刷新discover，
     * 检查该好友是否从discover消失
     */
    public void testFollowThenRefresh() throws UiObjectNotFoundException{
        DiscoverAction.scrollRecommendList();
        String expect_name=DiscoverAction.navToRecommendList(1,1);
        //两个参数分别为推荐列表第几个用户（0-3）和点击几次（1-2）
        DiscoverAction.checkMiniProfileNumFollowerAddOneAfterFollow();
        DiscoverAction.checkAddFriendsInMyFollowing(expect_name);
        gDevice.pressBack();
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        Spoon.screenshot("recommend_list","推荐列表中有一个被Followed");
        getObjectById(DiscoverPage.ID_DISCOVER_MAIN_CONTENT).swipeDown(50);
        waitTime(2);
        String new_name=DiscoverAction.navToRecommendList(1,1);
        if (expect_name.equals(new_name)){
            Spoon.screenshot("recommend_list","推荐列表不应该有被Follwed");
            Asst.fail("刷新后的推荐列表中被follow的用户没有消失");
        }
        //取消关注
        DiscoverAction.deleteFollowing(expect_name);
        if (scrollAndGetUIObject(expect_name)!=null){
            Spoon.screenshot("delete_user",expect_name);
            Asst.fail("can not delete user success");
        }
    }
    @Test
    @SanityTest
    @PerformanceTest
    /*
    关闭推荐列表
    点击推荐列表右上角关闭按钮
    检查推荐列表是否存在
     */
    public void testCloseRedList() throws UiObjectNotFoundException{
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        waitTime(3);
        String expect_name=DiscoverAction.navToRecommendList(1,1);
        //关闭弹出框
        clickByClass("android.widget.ImageView",2);
        logger.info(expect_name);
        Rect rect=getUiObjectByText("WHO TO FOLLOW").getVisibleBounds();
        int y=rect.centerY();
        int x=gDevice.getDisplayWidth();
        //你可能感兴趣-关闭按钮
        gDevice.click(x-5,y);
        boolean expect_name_exist=text_exists(expect_name);
        Asst.assertEquals("关闭-你可能感兴趣",false,expect_name_exist);
    }
    @Test
    @SanityTest
    @PerformanceTest
    /*
    关闭推荐列表
    点击推荐列表右上角关闭按钮
    检查推荐列表是否存在
    刷新列表
    推荐列表恢复显示
     */
    public void testRecoverRedList() throws UiObjectNotFoundException{
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        waitTime(3);
        String expect_name=DiscoverAction.navToRecommendList(1,1);
        //关闭弹出框
        clickByClass("android.widget.ImageView",2);
        logger.info(expect_name);
        Rect rect=getUiObjectByText("WHO TO FOLLOW").getVisibleBounds();
        int y=rect.centerY();
        int x=gDevice.getDisplayWidth();
        //你可能感兴趣-关闭按钮
        gDevice.click(x-5,y);
        waitUntilRegexGone("WHO TO FOLLOW",10000);
        boolean expect_name_exist=text_exists("WHO TO FOLLOW");
        Asst.assertEquals("关闭-你可能感兴趣",false,expect_name_exist);
        getObjectById(DiscoverPage.ID_DISCOVER_MAIN_CONTENT).swipeDown(50);
        waitTime(5);
        expect_name_exist=text_exists("WHO TO FOLLOW");
        Asst.assertEquals("恢复-你可能感兴趣",true,expect_name_exist);
    }
}
