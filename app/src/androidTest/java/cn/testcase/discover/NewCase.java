package cn.testcase.discover;

import java.io.IOException;
import java.util.List;

import android.graphics.Point;
import android.support.test.filters.SdkSuppress;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import bean.VideoBean;
import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.Until;
import android.widget.TextView;

import org.hamcrest.Asst;
import org.junit.Assert;
import java.util.Random;
import java.util.logging.Logger;
import com.squareup.spoon.Spoon;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.BroadcastAction;
import cn.action.DiscoverAction;
import cn.action.MainAction;
import cn.action.MeAction;
import cn.action.NewAction;
import cn.page.App;
import cn.page.DiscoverPage;
import cn.page.MePage;
import cn.page.NewPage;

import static cn.action.NewAction.getNewZanNumber;

/**
 * Created by yajuan on 2017/7/12.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)

/**发现界面
*  最新列表
*  功能验证
 **/
public class NewCase  extends VP2{
    Logger logger = Logger.getLogger(NewCase.class.getName());

    @Before
    public void setup() throws UiObjectNotFoundException{
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }

    @Test
    @SanityTest
    @PerformanceTest
    /**
    *case1.在discover界面切换到最新列表
    */

    public void testToNewList() throws UiObjectNotFoundException{
        MainAction.clickDiscover();
        DiscoverAction.navToNew();
        UiObject object=getUiObjectById(DiscoverPage.ID_MAIN_TAB_AD_SPALSH);
        if(object!=null){
            Asst.assertFalse("testToNewListFail",id_exists(DiscoverPage.ID_MAIN_TAB_AD_SPALSH));
        }
        else{
            Asst.assertTrue(!id_exists(DiscoverPage.ID_MAIN_TAB_AD_SPALSH));
        }
        Spoon.screenshot("New","Popular");
    }

    @Test
    @SanityTest
    @PerformanceTest
    /**
    *case2.频繁切换最新和推荐列表
    */
    public void testSwitchList() throws UiObjectNotFoundException{
        MainAction.clickDiscover();
        for (int i=0;i<20;i++){
            DiscoverAction.navToNew();
            DiscoverAction.navToPopular();
            DiscoverAction.navToNew();
        }
        UiObject object=getUiObjectById(DiscoverPage.ID_MAIN_TAB_AD_SPALSH);
        if(object!=null){
            Asst.assertFalse("testToNewListFail",id_exists(DiscoverPage.ID_MAIN_TAB_AD_SPALSH));
        }
        else{
            Asst.assertTrue(!id_exists(DiscoverPage.ID_MAIN_TAB_AD_SPALSH));
        }
        Spoon.screenshot("New","Popular");

    }

    @Test
    @SanityTest
    @PerformanceTest
    /**
    *case3.单击搜索图标
    */
    public void testClickSearch() throws UiObjectNotFoundException{
        MainAction.navToDiscover();
        DiscoverAction.navToNew();
        DiscoverAction.navToSearch();
        UiObject object= getObjectById(NewPage.ID_NEW_SEARCH_INPUT);
        Spoon.screenshot("Search");
        if (object!=null) {
            Assert.assertTrue(true);
        }
        else{
            Assert.fail("跳转失败");
        }
        Spoon.screenshot("Search","Trending");
    }

    @Test
    @SanityTest
    @PerformanceTest
    /**
    * case4.最新列表单击播主头像弹出个人详情框
    */
    public void testClickAnchor() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        DiscoverAction.navToNew();
        UiObject object = getObjectById(NewPage.ID_NEW_AVATOR);
        object.click();
        waitUntilFind(NewPage.ID_NEW_PROFILE_MINI_NUM_FOLLOWER, 1000);
        Asst.assertTrue(id_exists(NewPage.ID_NEW_PROFILE_MINI_NUM_FOLLOWER));
        Spoon.screenshot("Profile_page");
    }

    @Test
    @SanityTest
    @PerformanceTest
    /**
    * case5.最新列表单击播主头像弹出个人详情框，点击切换直播关注粉丝列表
    */
    public void testProfileSwipeList() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        DiscoverAction.navToNew();
        UiObject object = getObjectById(NewPage.ID_NEW_AVATOR);
        object.click();
        waitUntilFind(NewPage.ID_NEW_PROFILE_MINI_NUM_FOLLOWER, 1000);
        Asst.assertTrue(id_exists(NewPage.ID_NEW_PROFILE_MINI_NUM_FOLLOWER));
        Spoon.screenshot("Profile_page");
        //点击直播列表，切换到关注、粉丝列表
        clickByText("Video");
        Spoon.screenshot("Video");
        clickByText("Following");
        Asst.assertTrue(id_exists(NewPage.ID_NEW_PROFILE_MINI_NAME));
        Spoon.screenshot("Following");
        clickByText("Follower");
        Asst.assertTrue(id_exists(NewPage.ID_NEW_PROFILE_MINI_NAME));
        Spoon.screenshot("Follower");

    }
    @Test
    @SanityTest
    @PerformanceTest
    /**
    * case6.最新列表单击播主头像弹出个人详情框,播放直播列表中第一个视频
    */
    public void testProfilePlayVideo() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        DiscoverAction.navToNew();
        UiObject object = getObjectById(NewPage.ID_NEW_AVATOR);
        object.click();
        waitUntilFind(NewPage.ID_NEW_PROFILE_MINI_NUM_FOLLOWER, 1000);
        Asst.assertTrue(id_exists(NewPage.ID_NEW_PROFILE_MINI_NUM_FOLLOWER));
        Spoon.screenshot("Profile_page");
        //点击直播列表，选择视频播放
        clickByText("Video");
        //获取当前直播视频数，播放第一个视频
        List<UiObject2> elements=gDevice.findObject(By.res("cn.sioeye.sioeyeapp:id/list")).findObjects(By.clazz("android.widget.LinearLayout"));
        UiObject2 select = elements.get(0);
        select.click();
        Spoon.screenshot("PlayVideo");

    }

    @Test
    @SanityTest
    @PerformanceTest
    /**
    case7.随机播放最新列表的视频
    */
    public void testWatchNewVideo() throws UiObjectNotFoundException {
        MainAction.clickDiscover();
        DiscoverAction.navToNew();
        //随机选择一个最新列表的视频
        UiObject2 randomVideo=NewAction.getRandomVideo();
        randomVideo.click();
        waitTime(5);
        BroadcastAction.waitBroadcastLoading();
        gDevice.wait(Until.gone(By.res(MePage.BROADCAST_VIEW_VIDEO_LOADING)),30000);
        Asst.assertTrue("time out 60 seconds.",!getObjectById(MePage.BROADCAST_VIEW_VIDEO_LOADING).exists());
        Spoon.screenshot("testWatchNewVideo");
        gDevice.pressBack();
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**
    case8.上拉加载最新列表的视频
    */
    public void testSwipeUp() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        DiscoverAction.navToNew();
        waitUntilFind(NewPage.ID_NEW_VIDEO,10000);
        UiObject2 swipe_target = getObject2ById(NewPage.ID_NEW_VIDEO);
        swipe_target.swipe(Direction.UP,0.5f);
        swipe_target.swipe(Direction.UP,0.5f);
        swipe_target.swipe(Direction.UP,0.5f);
        swipe_target.swipe(Direction.UP,0.5f);
        Spoon.screenshot("New_page");
        Asst.assertFalse("testSwipeUpFail", !id_exists(DiscoverPage.ID_MAIN_TAB_DISCOVER));
    }

    @Test
    @SanityTest
    @PerformanceTest
    /**
    * case9:聊天室中观看人数统计
    * 思路：在聊天室中检查观看数，再退出重进再次检查
    * */
    public void testNewCountWatchPerson() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        DiscoverAction.navToNew();
        //获取第一次进入的观看数
        VideoBean watch_before = NewAction.getNumberPlayVideo();
        int watchnum_before = watch_before.getLike();
        gDevice.pressBack();
        //获取第二次进入的观看数
        VideoBean watch_after = NewAction.getNumberPlayVideo();
        int watchnum_after = watch_after.getLike();
        Asst.assertEquals("点赞数+10",watchnum_after,watchnum_before+1);
//        if (watchnum_after==(watchnum_before+1)){
//            Assert.assertTrue(true);
//        }else{
//            Asst.fail("AssertionError");
//        }

    }
    @Test
    @SanityTest
    @PerformanceTest
    /**
     * case10:视频封面观看人数统计
     *
     * */
    public void testNewCoverCountWatch() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        DiscoverAction.navToNew();
        //获取视频封面的观看数
        int watchnum_before = NewAction.getNewWatchNumber();
        List<UiObject2> linearLayouts = gDevice.findObjects(By.res(NewPage.ID_NEW_VIDEO));
         linearLayouts.get(0).click();
         waitTime(2);
        gDevice.pressBack();
        //获取进入之后的观看数
        int watchnum_after = NewAction.getNewWatchNumber();
        Asst.assertEquals("观看数+1",watchnum_after,watchnum_before+1);

    }
    @Test
    @SanityTest
    @PerformanceTest
    /**
    * case11:在聊天室统计历史点赞数
    * 思路：从聊天室中获取点赞数，点赞后再次获取点赞数
    **/
    public void testNewCountZan() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        DiscoverAction.navToNew();
        //获取点赞前的点赞数
        int  zan_before= NewAction.getNewZanNumber();
        clickById(MePage.TV_CHAT_ROOM_ID);
        waitUntilFind(MePage.BROADCAST_VIEW_ZAN,5000);
        for(int i=1;i<=10;i++) {
            clickById(MePage.BROADCAST_VIEW_ZAN);
        }
        //点赞后再获取点赞数
        clickById(MePage.TV_AUCHOR_ID);
        waitUntilFind(MePage.VIDEO_CHAT_NUMBER,10000);
        int zan_after = cover(getObject2ById(MePage.VIDEO_CHAT_NUMBER).getText());
        Spoon.screenshot("after_zan"+zan_after);
        Asst.assertEquals("点赞数+10",zan_after,zan_before+10);
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**
     * case12:在视频封面统计历史点赞数
     * 思路：从视频封面中获取点赞数，点赞后再次获取点赞数
     **/
    public void testNewCoverCountZan() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        DiscoverAction.navToNew();
        //获取点赞前的点赞数
        int  zan_before= NewAction.getZanNumber();
        List<UiObject2> relativeLayouts = gDevice.findObjects(By.res(NewPage.ID_NEW_VIDEO));
        relativeLayouts.get(0).click();
        waitUntilFind(MePage.BROADCAST_VIEW_ZAN,10000);
        for(int i=1;i<=5;i++) {
            clickById(MePage.BROADCAST_VIEW_ZAN);
        }
        gDevice.pressBack();
        //点赞后再获取点赞数
        int zan_after = NewAction.getZanNumber();
        waitTime(5);
        Spoon.screenshot("after_zan"+zan_after);
        Asst.assertEquals("点赞数+5",zan_after,zan_before+5);

    }

    @Test
    @SanityTest
    @PerformanceTest
    /**
    *case13.检查位置信息显示.查找位置信息是否存在
    */
    public void testLocation() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        DiscoverAction.navToNew();
        Boolean videoLocation = false;
        for (int i = 1; i <= 5; i++) {
            String Location= NewAction.getLocation();
            if (Location!=null) {
                videoLocation =true;
                    break;
            } else {
                //继续滑动查找
                getObject2ById(NewPage.ID_NEW_VIDEO).swipe(Direction.UP,0.3f);
                waitTime(5);
            }
        }
        //验证是否存在位置信息
        Asst.assertEquals("5轮查找-findLocationInVideo",true,videoLocation);
    }

    @Test
    @SanityTest
    @PerformanceTest
    /**
     * case14.评论视频后检查评论数显示
     */
     public void testNewCommentCount() throws UiObjectNotFoundException,IOException {
        //获取点赞按钮的坐标作为键盘的确认按钮
        Point point= MeAction.getPointToDoComment();
        MainAction.navToDiscover();
        DiscoverAction.navToNew();
        //获取第一次进入的评论数
        VideoBean comment_before = NewAction.getNumberPlayVideo();
        int cmtnum_before = comment_before.getComment();
        clickById(MePage.TV_CHAT_ROOM_ID);
        clickById(MePage.BROADCAST_VIEW_TIPTEXT);
        //清空输入框后随机输入十个字符
        getObjectById(MePage.EDIT_COMMENT_TEXT).clearTextField();
        String comment = getRandomString(10);
        getObjectById(MePage.EDIT_COMMENT_TEXT).setText(comment);
        shellInputText(comment);
        clickByPoint(point);
        gDevice.pressBack();
        //发送评论后返回主播界面检查评论数显示
        clickById(MePage.TV_AUCHOR_ID);
        waitUntilFind(MePage.VIDEO_LIKE_NUMBER,5000);
        int  cmtnum_after = Integer.parseInt(getObject2ById(MePage.VIDEO_LIKE_NUMBER).getText());
        if (cmtnum_after==(cmtnum_before+1)){
            Assert.assertTrue(true);
        }else{
            Asst.fail("AssertionError");
        }

    }


}
