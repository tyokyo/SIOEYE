package cn.testcase.discover;

import android.graphics.Point;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import bean.VideoBean;
import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.BroadcastAction;
import cn.action.DiscoverAction;
import cn.action.FollowersAction;
import cn.action.MainAction;
import cn.action.MeAction;
import cn.action.NewAction;
import cn.action.PlayAction;
import cn.action.WatchAction;
import cn.page.AccountPage;
import cn.page.App;
import cn.page.Constant;
import cn.page.DiscoverPage;
import cn.page.NewPage;
import cn.page.Other;
import cn.page.PlayPage;

import static cn.action.PlayAction.addFollow;
import static cn.action.PlayAction.clickFollow;

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
        //点击切换到最新列表，根据有无换页判断
        waitTime(3);
        Asst.assertEquals("testToNewListFail",false,id_exists(DiscoverPage.ID_PAGE_INDICATOR));
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
        //jqx--加等待时间
        waitTime(2);
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
        //点击搜索按钮，根据有无输入框判断
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
        //跳转到最新列表，获取到主播头像
        UiObject object = getObjectById(NewPage.ID_NEW_AVATOR);
        waitTime(3);
        object.click();
        //点击主播头像，弹出详情框
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
        waitTime(3);
        UiObject object = getObjectById(NewPage.ID_NEW_AVATOR);
        object.click();
        //点击主播头像，弹出详情框
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
        waitTime(3);
        object.click();
        waitUntilFind(NewPage.ID_NEW_PROFILE_MINI_NUM_FOLLOWER, 1000);
        Asst.assertTrue(id_exists(NewPage.ID_NEW_PROFILE_MINI_NUM_FOLLOWER));
        Spoon.screenshot("Profile_page");
        //点击直播列表，选择视频播放
        clickByText("Video");
        //获取当前直播视频数，播放第一个视频
        WatchAction.playProfileVideo();
        gDevice.pressBack();
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
        getObjectById(NewPage.ID_NEW_VIDEO).swipeDown(50);
        int index=NewAction.getRandomVideoIndex();
        NewAction.getRandomVideo(index).click();
        waitTime(5);
        BroadcastAction.waitBroadcastLoading();
        gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),30000);
        Asst.assertTrue("time out 60 seconds.",!getObjectById(PlayPage.BROADCAST_VIEW_VIDEO_LOADING).exists());
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
        //获取到视频列表后向上滑动四次加载视频
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
        getObjectById(NewPage.ID_NEW_VIDEO).swipeDown(50);
        //获取第一次进入的观看数
        int index = NewAction.getRandomVideoIndex();
        NewAction.getRandomVideo(index).click();
        VideoBean watch_before = PlayAction.getNumberPlayVideo();
        int watchnum_before = watch_before.getWatch();
        gDevice.pressBack();
        //获取第二次进入的观看数
        NewAction.getRandomVideo(index).click();
        VideoBean watch_after = PlayAction.getNumberPlayVideo();
        int watchnum_after = watch_after.getWatch();
        Asst.assertEquals("观看数+1",watchnum_after,watchnum_before+1);
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**
     * case10:视频封面观看人数统计
     * */
    public void testNewCoverCountWatch() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        DiscoverAction.navToNew();
        UiObject2 swipe_target = getObject2ById(NewPage.ID_NEW_VIDEO);
        swipe_target.swipe(Direction.DOWN,0.5f);
        waitTime(3);
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
        int  zan_before= PlayAction.getNewZanNumber();
        clickById(PlayPage.TV_CHAT_ROOM_ID);
        waitUntilFind(PlayPage.BROADCAST_VIEW_ZAN,5000);
        for(int i=1;i<=10;i++) {
            clickById(PlayPage.BROADCAST_VIEW_ZAN);
        }
        //点赞后再获取点赞数
        clickById(PlayPage.TV_AUCHOR_ID);
        waitUntilFind(PlayPage.VIDEO_CHAT_NUMBER,10000);
        int zan_after = cover(getObject2ById(PlayPage.VIDEO_CHAT_NUMBER).getText());
        Spoon.screenshot("after_zan"+zan_after);
        if (zan_after>1000){

        }else{
            Asst.assertEquals("点赞数+10",zan_after,zan_before+10);
        }
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
        UiObject2 swipe_target = getObject2ById(NewPage.ID_NEW_VIDEO);
        swipe_target.swipe(Direction.DOWN,0.5f);
        waitTime(3);
        //获取点赞前的点赞数
        int  zan_before= NewAction.getZanNumber();
        List<UiObject2> relativeLayouts = gDevice.findObjects(By.res(NewPage.ID_NEW_VIDEO));
        relativeLayouts.get(0).click();
        waitUntilFind(PlayPage.BROADCAST_VIEW_ZAN,10000);
        for(int i=1;i<=5;i++) {
            clickById(PlayPage.BROADCAST_VIEW_ZAN);
        }
        gDevice.pressBack();
        //点赞后再获取点赞数
        int zan_after = NewAction.getZanNumber();
        waitTime(5);
        Spoon.screenshot("after_zan"+zan_after);
        Asst.assertEquals("点赞数加5",zan_before+5,zan_after);
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
        waitTime(3);
        getObject2ById(NewPage.ID_NEW_VIDEO).swipe(Direction.DOWN,0.5f);
        waitTime(3);
        Boolean videoLocation = false;
        for (int i = 1; i <= 10; i++) {
            //获取位置信息
            String Location= NewAction.getLocation();
            if (Location!=null) {
                videoLocation =true;
                    break;
            } else {
                //继续滑动查找
                getObject2ById(NewPage.ID_NEW_VIDEO).swipe(Direction.UP,0.5f);
                waitTime(5);
            }
        }
        //验证是否存在位置信息
        Asst.assertEquals("10轮查找-findLocationInVideo",true,videoLocation);
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**
      *  case14.评论视频后检查评论数显示
      */
     public void testNewCommentCount() throws UiObjectNotFoundException,IOException {
        //获取点赞按钮的坐标作为键盘的确认按钮
        Point point= MeAction.getPointToDoComment();
        MainAction.navToDiscover();
        DiscoverAction.navToNew();
        waitTime(2);
        getObject2ById(NewPage.ID_NEW_VIDEO).swipe(Direction.DOWN,0.5f);
        waitTime(3);
        //获取第一次进入的评论数
        waitUntilFind(NewPage.ID_NEW_VIDEO,5000);
        int index=NewAction.getRandomVideoIndex();
        NewAction.getRandomVideo(index).click();
        gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
        VideoBean comment_before = PlayAction.getNumberPlayVideo();
        int cmtnum_before = comment_before.getComment();
        FollowersAction.clickToChatRoom();
        //随机输入20个字符
        String comment = getRandomString(20);
        clickById(PlayPage.BROADCAST_VIEW_TIPTEXT);
        shellInputText(comment);
        clickByPoint(point);
        waitTime(2);
        //滑动显示最新消息
        MeAction.displayNewMessages();
        waitTime(3);
        Asst.assertTrue("comments success",text_exists_contain(comment));
        //验证评论数+1
        VideoBean videoBean_after = PlayAction.getNumberPlayVideo();
        int  cmtnum_after = videoBean_after.getComment();
        Asst.assertEquals(cmtnum_before+1,cmtnum_after);
        Spoon.screenshot("testComments_Length_20",comment);
        gDevice.pressBack();
    }

    @Test
    @SanityTest
    @PerformanceTest
    /** zhengziongfan
     * case15、未登陆点击输入框
     * 1、未登录状态下在new界面点击任意视频进入观看
     * 2、点击输入框
     * Result:弹出登陆界面
     * */
    public void testClickInput() throws UiObjectNotFoundException {
        //注销账号
        AccountAction.logOutAccount();
        //进入-发现
        MainAction.clickDiscover();
        //点击最新TAB
        clickById(DiscoverPage.ID_NEW_RECOMMEND);
        //滑动视频列表
        //播放一个视频
        NewAction.navToPlayNewlistVideo();
        //等待连接聊天室
        BroadcastAction.waitBroadcastLoading();
        //点击评论框
        clickById(Other.chattextfield);
        //弹出登陆界面
        waitUntilFind(AccountPage.ACCOUNT_WEIXIN, 5000);
        Spoon.screenshot("loginIn_page");
        Asst.assertFalse("ClickInputFail", !id_exists(AccountPage.ACCOUNT_WEIXIN));
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**
     * case16、已登录点击输入框
     *1、已登录状态下在new界面点击任意视频进入观看，点击输入框
     * Result：正常弹出输入字符界面
     * */
    public void testClickInputSuccess() throws UiObjectNotFoundException, IOException {
        //账号登录
        AccountAction.logInAccount("46@qq.com", "123456");
        //进入发现界面
        MainAction.clickDiscover();
        //点击最新TAB
        clickById(DiscoverPage.ID_NEW_RECOMMEND);
        //播放一个视频
        NewAction.navToPlayNewlistVideo();
        //等待连接聊天室
        BroadcastAction.waitBroadcastLoading();
        //点击评论框
        clickById(Other.chattextfield);
        //弹出输入框界面
        waitUntilFind(Other.chattextfield, 2000);
        Spoon.screenshot("Input_page");
        Asst.assertFalse("ClickInputSuccess_Fail", !id_exists(Other.chattextfield_tanchu));
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**
     * case17、New界面未登陆点击关注主播
     *1.未登陆状态下，在观看视频界面点击关注主播
     *  * creat by yajuan 2017.8.8
     *Result:弹出登陆界面
     * */
    public void testUnLoginFollowAnchor() throws UiObjectNotFoundException {
        //注销账号
        AccountAction.logOutAccount();
        //进入-发现界面
        MainAction.clickDiscover();
        //点击最新TAB
        DiscoverAction.navToNew();
        //播放一个视频
        int index=NewAction.getRandomVideoIndex();
        NewAction.getRandomVideo(index).click();
        //点击主播
        FollowersAction.clickToAbout();
        waitTime(3);
        //点击关注
        clickFollow();
        waitUntilFind(AccountPage.ACCOUNT_WEIXIN, 5000);
        Spoon.screenshot("loginIn_page");
        Asst.assertFalse("ClickInputFail", !id_exists(AccountPage.ACCOUNT_WEIXIN));
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**
     *
     *1.case18、
     *2.已登录状态下，在观看界面点击关注图标关注主播
     * creat by yajuan 2017.8.8
     *Result:关注成功
     * */
    public void testLoginFollowAnchor() throws UiObjectNotFoundException {
        //进入发现界面
        MainAction.clickDiscover();
        DiscoverAction.navToNew();
        waitTime(3);
        getObject2ById(NewPage.ID_NEW_VIDEO).swipe(Direction.DOWN,0.5f);
        waitTime(3);
        //播放一个视频
        int index=NewAction.getRandomVideoIndex();
        NewAction.getRandomVideo(index).click();
        waitTime(5);
        //点击主播
        FollowersAction.clickToAbout();
        waitUntilFind(PlayPage.PLAY_ABOUT,3000);
        addFollow();

    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case19
     *1.进入搜索界面
     *2.输入邮箱地址后点击搜索
     *Result:结果匹配搜索内容，成功搜索出该ID的联系人
     * */
    public void testToSearchByEmail() throws UiObjectNotFoundException, IOException {
        NewAction.navToNewSearch();
        shellInputText(Constant.userName);
        Spoon.screenshot("testToSearchByEmail");
        waitTime(2);
        waitUntilFindText(Constant.CORRECT_SIO_EYE_ID,20000);
        Asst.assertTrue(text_exists(Constant.CORRECT_SIO_EYE_ID));
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case20
     *1.进入搜索界面
     *2.输入Sioeye Id后点击搜索
     *Result:结果匹配搜索内容，成功搜索出该ID的联系人
     * */
    public void testToSearchBySioeyeID() throws UiObjectNotFoundException, IOException {
        NewAction.navToNewSearch();
        shellInputText("tyokyo");
        Spoon.screenshot("testToSearchBySioeyeID");
        waitTime(2);
        Asst.assertTrue(text_exists("tyo000"));
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case21、
     *1.进入搜索界面
     *2.输入昵称后点击搜索
     *Result:结果匹配搜索内容，成功搜索出该ID的联系人
     * */
    public void testToSearchByNickname() throws UiObjectNotFoundException, IOException {
        NewAction.navToNewSearch();
        shellInputText("xiaoxiao");
        Spoon.screenshot("testToSearchByNickname");
        waitTime(2);
        //Asst.assertTrue(text_exists("你是谁"));//看ID对应的昵称是否存在，如果账号修改昵称可能会导致用例执行失败
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case22、
     *1.进入搜索界面
     *2.输入手机号码后点击搜索
     *Result:结果匹配搜索内容，成功搜索出该ID的联系人
     * */
    public void testToSearchByPhoneNumber() throws UiObjectNotFoundException, IOException {
        NewAction.navToNewSearch();
        shellInputText("13183883473");
        Spoon.screenshot("testToSearchByPhoneNumber");
        waitTime(2);
        Asst.assertTrue(text_exists("1350"));
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case23
     *1.在搜索界面点击Video切换到视频搜索
     *Result:成功切换到视频搜索
     * */
    public void testToSearchVideo() throws UiObjectNotFoundException, IOException {
        NewAction.navToNewSearch();
        shellInputText("a");
        clickByText("Video");
        waitTime(2);
        UiObject2 SP = getUiObject2ByText("Video");
        Boolean Actual = SP.isChecked() && (text_exists_contain("Oops,There's nothing") || text_exists_contain("a"));
        Asst.assertTrue(Actual);
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case24
     *1.在搜索输入框输入一些数据
     *2.点击输入框中的×按钮清空数据
     *Result:成功清空输入的数据，返回推荐
     * */
    public void testToSearchByData() throws UiObjectNotFoundException, IOException {
        NewAction.navToNewSearch();
        shellInputText("1234");
        clickById(Other.filter_clear);
        Spoon.screenshot("AfterClickFilter_clear");
        Asst.assertTrue(text_exists("Search"));
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case25
     *1.在搜索界面点击取消按钮
     *Result:退出搜索界面，返回到上一界面
     * */
    public void testToSearchClickCancle() throws UiObjectNotFoundException {
        NewAction.navToNewSearch();
        clickByText("Cancel");
        Asst.assertTrue(text_exists("Discover"));
    }
    @Test
    @SanityTest
    @PerformanceTest
    /**case26
     *1.在输入界面输入视频名称
     *2.点击搜索
     *Result:成功搜索出该名称的视频，绿色高亮显示
     * */
    public void testToSearchByVideoName() throws UiObjectNotFoundException, IOException {
        NewAction.navToNewSearch();
        shellInputText("a");
        clickByText("Video");
        waitTime(2);
        Asst.assertTrue(text_exists_contain("a"));

    }

















}
