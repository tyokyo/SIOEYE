package cn.testcase.watch;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.NotificationAction;
import cn.action.WatchAction;
import cn.page.App;
import cn.page.MePage;
import cn.page.WatchPage;
import usa.page.Watch;

/**
 * Created by jiali.liu on 2016/12/29.
 * changed by qiuxia.jian on 2017/12/1.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class MiniPageCase extends VP2{
    private Logger logger = Logger.getLogger(MiniPageCase.class.getName());
    @Before
    public void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    /**liujiali
     * 个人弹出框变化：
     * 步骤：1.随机点击watch界面的任一播主头像
     *      2.点击“直播”
     *      3.点击关注和粉丝
     * 期望：1.弹出该播主的个人详情窗口
     *      2.显示该播主对你可见的视频列表
     *      3.切换到关注或粉丝列表
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testMiniToLive() throws Exception {
        //进入Watch界面
        WatchAction.navToWatch();
        //找到视频列表对象下滑刷新视频
        waitUntilFind(WatchPage.WATCH_LIST,10000);
        UiObject watch_list = gDevice.findObject(new UiSelector().resourceId(WatchPage.WATCH_LIST));
        logger.info(watch_list.toString());
        //未找到视频列表对象则失败
        Asst.assertEquals("watch_list",true,watch_list.exists());
        //下滑10个步长
        watch_list.swipeDown(10);
        waitUntilFind(WatchPage.WATCH_LIST,10000);
        waitTime(3);
        //随机向上滑动三次
        for(int i=1;i<=3;i++){
            waitUntilFind(WatchPage.WATCH_LIST,10000);
            Random random = new Random();
            int num = random.nextInt(10);
            if(num>0){
                watch_list.swipeUp(4);
            }else{
                watch_list.swipeUp(3);
            }
            waitTime(10);
        }
        //点击当前视频列表的头像
        waitTime(5);
        WatchAction.ClickImageObjects();
        //判断弹出框是否被点出来
        boolean result = WatchAction.isOpenedMiniPage();
        Asst.assertEquals(true,result);
        waitTime(3);
        WatchAction.clickMiniLive();
        //UiObject2 liveObj = getObject2ById(WatchPage.WATCH_LIST).getChildren().get(0);
        //检查是否显示视频列表
        Asst.assertEquals("显示视频列表",true,WatchAction.LiveList_isExist());
        waitTime(10);
        WatchAction.clickMiniFollow();
        //检查是否显示关注列表
        Asst.assertEquals("显示关注列表",true,WatchAction.FollowList_isExist());
        WatchAction.clickMiniFans();
        //检查是否显示粉丝列表
        Asst.assertEquals("显示粉丝列表",true,WatchAction.FansList_isExist());
    }
    /**jqx-2017/12/1
     * 个人弹出框变化：
     * 步骤：1.随机点击watch界面的任一播主头像
     *      2.点击“直播”列表中的视频播放
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testMiniPlayVideo() throws IOException, UiObjectNotFoundException {
        //进入Watch界面
        clickById(MePage.ID_MAIN_TAB_LIVE);
        waitTime(1);
        UiObject watch_list = getObjectById(WatchPage.WATCH_LIST);
        Random random = new Random();
        int size = random.nextInt(20);
        watch_list.swipeUp(size);
        //随机滑动列表后，点击头像
        clickById(WatchPage.WATCH_AVATAR);
        //点击Video列表
        clickByText("Video");
        //滑动Video列表
        watch_list.swipeUp(size);
        boolean isPlaySuccess = false;
        UiObject2 videoPlay = getObject2ById(WatchPage.WATCH_LIST).getChildren().get(0);
        //如果视频可以点击播放，则通过
        if(videoPlay!=null){
            videoPlay.click();
            isPlaySuccess = true;
        }else {
            logger.info("No video");
        }
        Asst.assertEquals("播放视频成功",true,isPlaySuccess);
    }
    /**jqx-2017/12/4
     * 对个人弹出框Followers列表中用户的关注：
     * 步骤：1.随机点击watch界面的任一播主头像
     *      2.对“Followers”列表中未关注的用户进行关注
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testMiniFollowPerson() throws IOException, UiObjectNotFoundException {
        //进入Watch界面
        clickById(MePage.ID_MAIN_TAB_LIVE);
        waitTime(1);
        UiObject watch_list = getObjectById(WatchPage.WATCH_LIST);
        Random random = new Random();
        int size = random.nextInt(20);
        watch_list.swipeUp(size);
        clickById(WatchPage.WATCH_AVATAR);
        clickByText("Follower");
        while (!id_exists(WatchPage.WATCH_SEARCH_USER_FOLLOW)) {
            watch_list.swipeUp(size);
        }
        //获取关注按钮
        UiObject2 obj = getObject2ById(WatchPage.WATCH_SEARCH_USER_FOLLOW);
        //关注人的昵称
        String NickName = obj.getParent().getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getText();
        obj.click();
        Spoon.screenshot("follow_success1",NickName);
        waitTime(2);
        //查找在关注列表是否存在
        boolean isPlaySuccess = NotificationAction.followOrUnFollow(NickName);
        Spoon.screenshot("follow_success2");
        Asst.assertEquals("关注成功",true,isPlaySuccess);
    }
    /**jqx-2017/12/4
     * 对个人弹出框Following列表中用户的取消关注：
     * 步骤：1.随机点击watch界面的任一播主头像
     *      2.对“Following”列表中关注的用户取消关注
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testMiniCancelFollow() throws IOException, UiObjectNotFoundException {
        //进入Watch界面
        clickById(MePage.ID_MAIN_TAB_LIVE);
        waitTime(1);
        UiObject2 watch_list = getObject2ById(WatchPage.WATCH_LIST);
        watch_list.scroll(Direction.DOWN, 2f);
        clickById(WatchPage.WATCH_AVATAR);
        clickByText("Following");
        //获取目前已经关注的某一个用户
        String NickName = null;
        List<UiObject2> followArr = getObject2ById(WatchPage.WATCH_LIST).getChildren();
        logger.info("FollowArr:"+followArr.size());
        for(UiObject2 obj1:followArr){
            if(obj1.getChildren().size()==2){
                NickName = obj1.getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getText();
                obj1.click();
                break;
            }
        }
        //有可能遇到点到自己时，没有WatchPage.WATCH_USER_MINI_FOLLOW的情况。下面是处理这种情况的代码。
        if(!id_exists(WatchPage.WATCH_USER_MINI_FOLLOW)){
            clickByText("Following");
            List<UiObject2> followArr2 = getObject2ById(WatchPage.WATCH_LIST).getChildren();
            for(UiObject2 obj1:followArr2){
                if(obj1.getChildren().size()==2){
                    NickName = obj1.getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getText();
                    obj1.click();
                    break;
                }
            }
        }
        clickById(WatchPage.WATCH_USER_MINI_FOLLOW);
        Spoon.screenshot("cancel_follow1",NickName);
        waitTime(2);
        //查找在关注列表是否存在
        boolean isPlaySuccess = NotificationAction.followOrUnFollow(NickName);
        Spoon.screenshot("cancel_follow2");
        Asst.assertEquals("取消关注成功",true,isPlaySuccess);
    }
    /**jqx-2017/12/4
     * 对个人弹出框video列表快速滑动：
     * 步骤：1.随机点击watch界面的任一播主头像
     *      2.对“video”列表中的视频快速进行滑动
     *      3.滑动后，点击视频，仍可以播放，则通过
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testMiniSwipeVideo() throws IOException, UiObjectNotFoundException {
        //进入Watch界面
        clickById(MePage.ID_MAIN_TAB_LIVE);
        waitTime(1);
        UiObject2 watch_list = getObject2ById(WatchPage.WATCH_LIST);
        //滑动watch界面视频列表
        watch_list.scroll(Direction.DOWN, 2f);
        clickById(WatchPage.WATCH_AVATAR);
        clickByText("Video");
        //快速滑动视频列表
        UiObject2 swipe_target = getObject2ById(WatchPage.WATCH_LIST);
        for(int i =0;i<10;i++){
            swipe_target.swipe(Direction.UP, 0.6f);
        }
        Spoon.screenshot("MiniVideoPage");
        boolean isPlaySuccess = false;
        UiObject2 videoPlay = getObject2ById(WatchPage.WATCH_LIST).getChildren().get(0);
        //如果滑动列表后，点击视频，可以播放，则通过
        if(videoPlay!=null){
            videoPlay.click();
            isPlaySuccess = true;
        }else {
            logger.info("No video");
        }
        Asst.assertEquals("QuicklySwipeVideoSuccess",true,isPlaySuccess);
    }
}
