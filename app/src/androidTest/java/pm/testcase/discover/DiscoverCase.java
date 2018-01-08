package pm.testcase.discover;

import android.graphics.Rect;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import ckt.base.VP2;
import pm.action.AccountAction;
import pm.action.BroadcastAction;
import pm.action.DiscoverAction;
import pm.action.FollowersAction;
import pm.action.MainAction;
import pm.page.AccountPage;
import pm.page.App;
import pm.page.Constant;
import pm.page.DiscoverPage;
import pm.page.MePage;
import pm.page.Other;


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

    //生成一个随机子字符串
    public String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    @Before
    public void setup() throws UiObjectNotFoundException {
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
        if (!getObjectById(DiscoverPage.ID_SEARCH_FILTER_INPUT).exists()) {
            Spoon.screenshot("Go to SearchActivity Fail", "跳转失败");
            makeToast("跳转失败", 5);
            Assert.fail("跳转失败");
        }
    }

    @Test
    /**
     * case2、双击搜索图标
     */
    public void testDoubleClickSearch() throws IOException, UiObjectNotFoundException {
        MainAction.clickDiscover();
        UiObject2 frameLayout = getObject2ById(DiscoverPage.ID_HOT_RECOMMEND).getParent().getParent();
        UiObject2 searchObject = frameLayout.findObject(By.clazz(android.widget.ImageView.class));
        Rect searchRect = searchObject.getVisibleBounds();
        //double click
        clickRect(searchRect);
        clickRect(searchRect);
        waitTime(1);
        //验证进入搜索界面
        if (!id_exists(DiscoverPage.ID_SEARCH_FILTER_INPUT)) {
            Spoon.screenshot("navToSearchPage", "跳转失败");
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
        if (!Result) {
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
        for (int i = 0; i < 10; i++) {
            MainAction.navToDiscover();
            MainAction.navToMe();
            MainAction.navToLive();
            MainAction.navToDevice();
            MainAction.navToDiscover();
        }
        Asst.assertEquals("navToDiscover", true, id_exists(MePage.ID_MAIN_TAB_DISCOVER));
    }

    //play the first video in discover page
    @Test
    public void testViewVideo() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        UiObject2 swipe_target = getObject2ById(DiscoverPage.ID_SWIPE_TARGET);
        List<UiObject2> linearLayouts = swipe_target.findObjects(By.clazz(android.widget.RelativeLayout.class));
        int size = linearLayouts.size();
        linearLayouts.get(size - 1).click();
        BroadcastAction.waitBroadcastLoading();
        gDevice.wait(Until.gone(By.res(MePage.BROADCAST_VIEW_VIDEO_LOADING)), 120000);
        Asst.assertEquals("加载2分钟", false, id_exists(MePage.BROADCAST_VIEW_VIDEO_LOADING));
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
    public void testClickInput() throws UiObjectNotFoundException {
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
        waitUntilFind(AccountPage.ACCOUNT_WEIXIN, 5000);
        Spoon.screenshot("loginIn_page");
        Asst.assertFalse("ClickInputFail", !id_exists(AccountPage.ACCOUNT_WEIXIN));
    }

    @Test
    /**
     * 已登录点击输入框
     *1、已登录状态下在discover界面点击任意视频进入观看，点击输入框
     * Result：正常弹出输入字符界面
     * */
    public void testClickInputSuccess() throws UiObjectNotFoundException, IOException {
        //账号登录
        AccountAction.logInAccount("13688169291", "123456");
        //进入发现界面
        MainAction.clickDiscover();
        //播放一个视频
        DiscoverAction.navToPlayVideo();
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
    /**
     * 未登陆点击关注主播
     *1.未登陆状态下，在观看视频界面点击关注主播
     *Result:弹出登陆界面
     * */
    public void testUnLoginFollowAnchor() throws UiObjectNotFoundException {
        //注销账号
        AccountAction.logOutAccount();
        //进入-发现界面
        MainAction.clickDiscover();
        //播放一个视频
        DiscoverAction.navToPlayVideo();
        //点击主播
        FollowersAction.clickToAnchor();
        //点击关注
        clickByText("Follow");
        //弹出登录界面
        waitUntilFind(AccountPage.ACCOUNT_WEIXIN, 5000);
        Spoon.screenshot("loginIn_page");
        Asst.assertFalse("ClickInputFail", !id_exists(AccountPage.ACCOUNT_WEIXIN));
    }

    @Test
    /**
     *
     *1.已登录状态下，在观看界面点击任意键关注主播
     *Result:
     * */
    public void testLoginFollowAnchor() throws UiObjectNotFoundException {
        //账号登录
        //AccountAction.logInAccount("13688169291", "123456");
        //进入发现界面
        MainAction.clickDiscover();
        //播放一个视频
        DiscoverAction.navToPlayVideo();
        //点击主播
        FollowersAction.clickToAnchor();
        waitUntilFind(DiscoverPage.ID_ANCHOR_FOLLOW,10000);
        //判断是否关注
        if (text_exists("Follow")) {
            //点击已关注
            clickByText("Follow");
            //取消关注成功，变为关注
            waitUntilFindText("Following", 3000);
            Spoon.screenshot("cancel_follow");
            Asst.assertFalse("LoginFollowAnchor", !id_exists(Other.anchor));
        } else {
            //点击关注
            clickByText("Following");
            //关注成功，变为已关注
            waitUntilFindText("Follow", 3000);
            Spoon.screenshot("addsuccess");
            Asst.assertFalse("LoginFollowAnchor", !id_exists(Other.anchor));
        }
    }

    @Test
    /**
     * 单击广告封面
     *1、点击广告封面
     *Result:跳转至广告链接，在网络良好的情况下不应卡顿，延迟
     * */
    public void testClickAD() throws UiObjectNotFoundException {
        DiscoverAction.navToAd();
        waitUntilFind(DiscoverPage.ID_PROFILE_AVATOR, 0);
        Spoon.screenshot("JumpSuccess");
        Asst.assertFalse("LoginFollowAnchor", id_exists(DiscoverPage.ID_PROFILE_AVATOR));
    }

    @Test
    /**
     * 广告内容页面点返回上级
     *1、点击广告页面里的返回键
     *Result：迅速响应，返回上一级界面
     * */
    public void testClickADBack() {
        //DiscoverAction.clickADBack();
    }

    @Test
    /**
     * 未登录点击follow
     *1、点击任一推荐对象，点击follow
     *Result:弹出登陆界面
     * */
    public void testUnLoginClickFollow() throws UiObjectNotFoundException {
        //注销账号
        AccountAction.logOutAccount();
        //进入-发现
        MainAction.clickDiscover();
        //点击任一推荐对象
        DiscoverAction.navToRecommendList(1, 1);
        //点击follow
        clickById(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_NUM_FOLLOW);
        waitTime(1);
        Spoon.screenshot("loginIn_page");
        Asst.assertFalse("testUnLoginClickFollowFail", !id_exists(AccountPage.ACCOUNT_WEIXIN));
    }

    @Test
    /**
     *未登陆单击推荐人头像
     *1、点击推荐头像
     *Result:唤出对应对象的个人资料页面
     * */
    public void testUnLoginClickAvatar() throws UiObjectNotFoundException {
        //注销账号
        AccountAction.logOutAccount();
        //进入-发现
        MainAction.clickDiscover();
        //点击推荐对象
        DiscoverAction.navToRecommendList(0, 1);
        //弹出个人头像页面
        waitUntilFind(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_NUM_FOLLOW, 1000);
        Spoon.screenshot("Profile_page");
        Asst.assertFalse("testUnLoginClickAvatarFail", !id_exists(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_NUM_FOLLOW));
    }

    @Test
    /**
     *向上迅速滑动视频列表
     *1、迅速滑动推荐视频列表
     *Result:APP响应迅速不会出现延迟卡顿carsh闪退现象
     * */
    public void testSwipeUpQuickly() throws UiObjectNotFoundException {
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        //迅速滑动推荐视频列表
        waitUntilFind(DiscoverPage.ID_Swipe_target,10000);
        UiObject2 swipe_target = getObject2ById(DiscoverPage.ID_Swipe_target);
        swipe_target.swipe(Direction.UP, 0.6f);
        swipe_target.swipe(Direction.UP, 0.6f);
        swipe_target.swipe(Direction.UP, 0.6f);
        swipe_target.swipe(Direction.UP, 0.6f);
        swipe_target.swipe(Direction.UP, 0.6f);
        swipe_target.swipe(Direction.UP, 0.6f);
        swipe_target.swipe(Direction.UP, 0.6f);
        swipe_target.swipe(Direction.UP, 0.6f);
        swipe_target.swipe(Direction.UP, 0.6f);
        swipe_target.swipe(Direction.UP, 0.6f);
        Spoon.screenshot("DiscoverPage");
        Asst.assertFalse("testSwipeUpQuicklyFail", !id_exists(DiscoverPage.ID_MAIN_TAB_DISCOVER));
    }

    /*@Test
     * 在discover界面待手机自动灭屏后静置一段时间后，重新唤醒手机
     *1、唤醒后APP停留在灭屏前的界面，APP不会出现carsh ,闪退
    public void testSleepThenWakeUp() throws RemoteException, UiObjectNotFoundException {
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        gDevice.sleep();
        gDevice.wakeUp();
        //需要先解锁
        Spoon.screenshot("DiscoverPage");
        Asst.assertFalse("testSwipeUpQuicklyFail",!id_exists(DiscoverPage.ID_MAIN_TAB_DISCOVER));
    } */
    @Test
    /**
     *进入搜索界面，检查默认推荐联系人状态
     *1、已经关注的不再显示在默认推荐人列表
     * */
    public void testCheckDefaultRecommendListStatus() {
        DiscoverAction.navToSearch();
    }

    @Test
    /**
     *1.进入搜索界面
     *2.输入邮箱地址后点击搜索
     *Result:结果匹配搜索内容，成功搜索出该ID的联系人
     * */
    public void testToSearchByEmail() throws UiObjectNotFoundException, IOException {
        //AccountAction.logInAccount("YCB123", "123456");
        DiscoverAction.navToSearch();
        shellInputText(Constant.userName);
        Spoon.screenshot("testToSearchByEmail");
        waitTime(2);
        waitUntilFindText(Constant.CORRECT_SIO_EYE_ID,20000);
        Asst.assertTrue(text_exists(Constant.CORRECT_SIO_EYE_ID));
    }

    @Test
    /**
     *1.进入搜索界面
     *2.输入Sioeye Id后点击搜索
     *Result:结果匹配搜索内容，成功搜索出该ID的联系人
     * */
    public void testToSearchBySioeyeID() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        shellInputText("tyokyo");
        Spoon.screenshot("testToSearchBySioeyeID");
        waitTime(2);
        Asst.assertTrue(text_exists("tyo000"));
    }

    @Test
    /**
     *1.进入搜索界面
     *2.输入昵称后点击搜索
     *Result:结果匹配搜索内容，成功搜索出该ID的联系人
     * */
    public void testToSearchByNickname() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        shellInputText("xiaoxiao");
        Spoon.screenshot("testToSearchByNickname");
        waitTime(2);
        //Asst.assertTrue(text_exists("你是谁"));//看ID对应的昵称是否存在，如果账号修改昵称可能会导致用例执行失败
    }

    @Test
    /**
     *1.进入搜索界面
     *2.输入手机号码后点击搜索
     *Result:结果匹配搜索内容，成功搜索出该ID的联系人
     * */
    public void testToSearchByPhoneNumber() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        shellInputText("13688169291");
        Spoon.screenshot("testToSearchByPhoneNumber");
        waitTime(2);
        Asst.assertTrue(text_exists("123123123"));
    }
    @Test
    /**
     *1.在搜索界面点击Video切换到视频搜索
     *Result:成功切换到视频搜索
     * */
    public void testToSearchVideo() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        shellInputText("a");
        clickByText("Video");
        waitTime(2);
        UiObject2 SP = getUiObject2ByText("Video");
        Boolean Actual = SP.isChecked() && (text_exists_contain("Oops,There's nothing") || text_exists_contain("a"));
        Asst.assertTrue(Actual);
    }

    @Test
    /**
     *1.在搜索输入框输入一些数据
     *2.点击输入框中的×按钮清空数据
     *Result:成功清空输入的数据，返回推荐
     * */
    public void testToSearchByData() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        shellInputText("1234");
        clickById(Other.filter_clear);
        Spoon.screenshot("AfterClickFilter_clear");
        Asst.assertTrue(text_exists("Search"));
    }

    @Test
    /**
     *1.在搜索界面点击取消按钮
     *Result:退出搜索界面，返回到上一界面
     * */
    public void testToSearchClickCancle() throws UiObjectNotFoundException {
        DiscoverAction.navToSearch();
        clickByText("Cancel");
        Asst.assertTrue(text_exists("Discover"));
    }

    @Test
    /**
     *1.在输入界面输入视频名称
     *2.点击搜索
     *Result:成功搜索出该名称的视频，绿色高亮显示
     * */
    public void testToSearchByVideoName() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        shellInputText("a");
        clickByText("Video");
        waitTime(2);
        Asst.assertTrue(text_exists_contain("a"));
    }

    @Test
    /**
     *1.在输入框输入随意字符
     *Result:自动匹配出包含这些字符的视频，匹配字符绿色高亮显示
     * */
    public void testToSearchByRandChar() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        for (int i = 0; i < 5; i++) {
            //将由5个字符组成的字符串逐个输入搜索框内。备注：此处未输入中文
            shellInputText(getRandomString(5).charAt(i));
            clickByText("Video");
            waitTime(1);
            String s = getObjectById(DiscoverPage.ID_SEARCH_FILTER_INPUT).getText();
            if (text_exists_contain(s) || text_exists_contain("Oops,There's nothing")) {
                continue;
            }
            Asst.fail();
        }
    }
}


