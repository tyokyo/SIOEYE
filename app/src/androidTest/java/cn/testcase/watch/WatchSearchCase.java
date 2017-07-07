package cn.testcase.watch;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.WatchAction;
import cn.page.App;
import cn.page.Constant;
import cn.page.WatchPage;

/**
 * Created by admin on 2016/11/5.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class WatchSearchCase extends VP2 {
    @Before
    public void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }

    /*验证搜索功能
    有搜索图标
    2、进入搜索界面，可正确搜索
    * */
    //验证真实昵称
    @Test
    @SanityTest
    @PerformanceTest
    public void test_search_NickName_CORRECT() throws UiObjectNotFoundException, InterruptedException {
        //进入Watch
        WatchAction.navToWatchSearch();
        String search_content = Constant.CORRECT_NICK_NAME;
        //输入搜索内容
        setText(WatchPage.WATCH_INPUT_SEARCH,search_content);
        //等待搜索完成
        waitUntilFind(WatchPage.WATCH_WAIT_SEARCH_LOADING, 20);
        waitUntilGone(WatchPage.WATCH_WAIT_SEARCH_LOADING, 60);
        waitTime(5);
        //验证搜索结果
        Spoon.screenshot("search_nickname_result", search_content);
        boolean active_result = getObjectByTextContains(search_content).exists();
        Asst.assertEquals("search result", true, active_result);
    }

    //验证虚假昵称
    @Test
    @SanityTest
    @PerformanceTest
    public void test_search_NickName_WRONG() throws UiObjectNotFoundException, InterruptedException {
        //进入Watch
        WatchAction.navToWatchSearch();
        String search_content =Constant.WRONG_NICK_NAME;
        //输入搜索内容
        setText(WatchPage.WATCH_INPUT_SEARCH, search_content);
        //等待搜索完成
        waitUntilFind(WatchPage.WATCH_WAIT_SEARCH_LOADING, 20);
        waitUntilGone(WatchPage.WATCH_WAIT_SEARCH_LOADING, 60);
        waitTime(3);
        //验证搜索结果
        Spoon.screenshot("search_nickname_result", search_content);
        boolean active_result = getObjectById(WatchPage.WATCH_SEARCH_NORESULT).exists();
        Asst.assertEquals("search_no_result", true, active_result);
        // 验证搜索数目
        int avatars = getObjectsById(WatchPage.WATCH_SEARCH_RESULT_USER_AVATAR).size();
        if (avatars!=0) {
            Asst.fail("expect search result is 0,but active result is " + avatars);
        }
    }

    //验证真实ID
    @Test
    @SanityTest
    @PerformanceTest
    public void test_search_sioeyeID_CORRECT() throws UiObjectNotFoundException, InterruptedException {
        //进入Watch
        WatchAction.navToWatchSearch();
        String search_content = Constant.CORRECT_SIO_EYE_ID;
        //输入搜索内容
        setText(WatchPage.WATCH_INPUT_SEARCH, search_content);
        //等待搜索完成
        waitUntilFind(WatchPage.WATCH_WAIT_SEARCH_LOADING, 20);
        waitUntilGone(WatchPage.WATCH_WAIT_SEARCH_LOADING, 60);
        waitTime(5);
        //验证搜索结果
        Spoon.screenshot("search_sioEyeID_result", search_content);
        boolean active_result = getObjectByTextContains(search_content).exists();
        Asst.assertEquals("search result", true, active_result);
    }

    //验证虚假ID
    @Test
    @SanityTest
    @PerformanceTest
    public void test_search_sioeyeID_WRONG() throws UiObjectNotFoundException, InterruptedException {
        //进入Watch
        WatchAction.navToWatchSearch();
        String search_content = Constant.WRONG_SIO_EYE_ID;
        //输入搜索内容
        setText(WatchPage.WATCH_INPUT_SEARCH, search_content);
        //等待搜索完成
        waitUntilFind(WatchPage.WATCH_WAIT_SEARCH_LOADING, 20);
        waitUntilGone(WatchPage.WATCH_WAIT_SEARCH_LOADING, 60);
        waitTime(5);
        //验证搜索结果
        Spoon.screenshot("search_sioEyeID_result", search_content);
        boolean active_result = getObjectById(WatchPage.WATCH_SEARCH_NORESULT).exists();
        Asst.assertEquals("search_No_result", true, active_result);
        // 验证搜索数目
        int avatars = getObjectsById(WatchPage.WATCH_SEARCH_RESULT_USER_AVATAR).size();
        if (avatars!=0) {
            Asst.fail("expect search result is 0,but active result is " + avatars);
        }
    }

    //搜索真实email
    @Test
    @SanityTest
    @PerformanceTest
    public void test_search_email_CORRECT() throws UiObjectNotFoundException, InterruptedException {
        //进入Watch
        WatchAction.navToWatchSearch();
        String search_content = Constant.CORRECT_EMAIL;
        //输入搜索内容
        setText(WatchPage.WATCH_INPUT_SEARCH, search_content);
        //等待搜索完成
        waitUntilGone(WatchPage.WATCH_WAIT_SEARCH_LOADING, 60000);
        //验证搜索结果
        Spoon.screenshot("search_Email_result_1");
        waitUntilFind(WatchPage.WATCH_SEARCH_RESULT_USER_AVATAR,10000);
        //验证搜索数目
        int avatars = getObjectsById(WatchPage.WATCH_SEARCH_RESULT_USER_AVATAR).size();
        if (avatars == 1) {
            clickById(WatchPage.WATCH_SEARCH_RESULT);
            waitTime(2);
            Spoon.screenshot("search_Email_result_2", search_content);
            //验证搜到的信息
            boolean active_result = getUiObjectByTextContains(Constant.CORRECT_EMAIL_SIOEYE_ID).exists();
            Asst.assertEquals("search_result", true, active_result);
        } else if (avatars > 1) {
            Asst.fail("expect search result is 1,but active result is " + avatars);
        } else {
            Asst.fail("expect search result is 1,but active result is " + avatars);
        }
    }

    //搜索虚假的email地址
    @Test
    @SanityTest
    @PerformanceTest
    public void test_search_email_WRONG() throws UiObjectNotFoundException, InterruptedException {
        //进入Watch
        WatchAction.navToWatchSearch();
        String search_content = Constant.WRONG_EMAIL;
        //输入搜索内容
        setText(WatchPage.WATCH_INPUT_SEARCH, search_content);
        //等待搜索完成
        waitUntilFind(WatchPage.WATCH_WAIT_SEARCH_LOADING, 20);
        waitUntilGone(WatchPage.WATCH_WAIT_SEARCH_LOADING, 60);
        waitTime(5);
        //验证搜索结果
        Spoon.screenshot("search_sioEyeID_result", search_content);
        boolean active_result = getObjectById(WatchPage.WATCH_SEARCH_NORESULT).exists();
        Asst.assertEquals("search_No_result", true, active_result);
        //验证搜索数目
        int avatars = getObjectsById(WatchPage.WATCH_SEARCH_RESULT_USER_AVATAR).size();
        if (avatars!=0) {
            Asst.fail("expect search result is 0,but active result is " + avatars);
        }
    }
}
