package cn.testcase.watch;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.WatchAction;
import cn.page.App;
import cn.page.MePage;
import cn.page.WatchPage;

/**
 * Created by jiali.liu on 2016/12/29.
 * changed by qiuxia.jian on 2017/12/29.
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
    /**
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
    /**
     * 个人弹出框变化：
     * 步骤：1.随机点击watch界面的任一播主头像
     *      2.点击“直播”
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
        watch_list.swipeDown(size);
        /*//UiObject2 follow_view = getObject2ByClass(LinearLayout.class);
        *//*Random r = new Random();
        int int_r =r.nextInt(20);*//*
        UiObject2 follow_view = getObject2ByClass(RelativeLayout.class);
        List<UiObject2> LinearLayoutList = follow_view.findObjects(By.clazz(LinearLayout.class));
        UiObject2 touxiang = LinearLayoutList.get(0);
        touxiang.click();*/

    }
}
