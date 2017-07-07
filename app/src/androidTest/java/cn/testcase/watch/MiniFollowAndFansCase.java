package cn.testcase.watch;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.page.App;
import cn.page.MePage;
import cn.page.WatchPage;

/**
 * Created by jiali.liu on 2016/12/29.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class MiniFollowAndFansCase extends VP2{
    private Logger logger = Logger.getLogger(MiniFollowAndFansCase.class.getName());
    @Before
    public void setup() throws Exception {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        //AccountAction.isLogin();
    }
    /**
     * 个人弹出框变化：
     * 步骤：1.随机点击watch界面的任一播主头像
     *      2.点击“直播”
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testMiniToLive() throws IOException, UiObjectNotFoundException {
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
