package usa.testcase.watch;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ckt.base.VP2;
import usa.action.Nav;
import usa.action.WatchAction;
import usa.page.App;
import usa.page.Me;
import usa.page.Watch;

/**
 * Created by admin on 2016/11/5.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class WatchSearchCase extends VP2 {
    @Before
    public void setup() {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }

    /*验证搜索功能
    有搜索图标
    2、进入搜索界面，可正确搜索
    * */
    //验证真实昵称
    @Test
    public void test_search_NickName_CORRECT() throws UiObjectNotFoundException, InterruptedException {
        //进入Watch
        Nav.navToWatchSearch();
        String search_content = WatchAction.CORRECT_NICK_NAME;
        //输入搜索内容
        setText(Watch.WATCH_INPUT_SEARCH, search_content);
        //等待搜索完成
        waitUntilFind(Watch.WATCH_WAIT_SEARCH_LOADING, 20);
        waitUntilGone(Watch.WATCH_WAIT_SEARCH_LOADING, 60);
        waitTime(5);
        //验证搜索结果
        Spoon.screenshot("search_nickname_result", search_content);
        boolean active_result = getObjectByTextContains(search_content).exists();
        Asst.assertEquals("search result", true, active_result);
    }

    //验证虚假昵称
    @Test
    public void test_search_NickName_WRONG() throws UiObjectNotFoundException, InterruptedException {
        //进入Watch
        Nav.navToWatchSearch();
        String search_content = WatchAction.WRONG_NICK_NAME;
        //输入搜索内容
        setText(Watch.WATCH_INPUT_SEARCH, search_content);
        //等待搜索完成
        waitUntilFind(Watch.WATCH_WAIT_SEARCH_LOADING, 20);
        waitUntilGone(Watch.WATCH_WAIT_SEARCH_LOADING, 60);
        waitTime(3);
        //验证搜索结果
        Spoon.screenshot("search_nickname_result", search_content);
        boolean active_result = getObjectById(Watch.WATCH_SEARCH_NORESULT).exists();
        Asst.assertEquals("search_no_result", true, active_result);
        // 验证搜索数目
        int avatars = getObjectsById(Watch.WATCH_SEARCH_RESULT_USER_AVATAR).size();
        if (avatars!=0) {
            Asst.fail("expect search result is 0,but active result is " + avatars);
        }
    }

    //验证真实ID
    @Test
    public void test_search_sioeyeID_CORRECT() throws UiObjectNotFoundException, InterruptedException {
        //进入Watch
        Nav.navToWatchSearch();
        String search_content = WatchAction.CORRECT_SIO_EYE_ID;
        //输入搜索内容
        setText(Watch.WATCH_INPUT_SEARCH, search_content);
        //等待搜索完成
        waitUntilFind(Watch.WATCH_WAIT_SEARCH_LOADING, 20);
        waitUntilGone(Watch.WATCH_WAIT_SEARCH_LOADING, 60);
        waitTime(5);
        //验证搜索结果
        Spoon.screenshot("search_sioEyeID_result", search_content);
        boolean active_result = getObjectByTextContains(search_content).exists();
        Asst.assertEquals("search result", true, active_result);
    }

    //验证虚假ID
    @Test
    public void test_search_sioeyeID_WRONG() throws UiObjectNotFoundException, InterruptedException {
        //进入Watch
        Nav.navToWatchSearch();
        String search_content = WatchAction.WRONG_SIO_EYE_ID;
        //输入搜索内容
        setText(Watch.WATCH_INPUT_SEARCH, search_content);
        //等待搜索完成
        waitUntilFind(Watch.WATCH_WAIT_SEARCH_LOADING, 20);
        waitUntilGone(Watch.WATCH_WAIT_SEARCH_LOADING, 60);
        waitTime(5);
        //验证搜索结果
        Spoon.screenshot("search_sioEyeID_result", search_content);
        boolean active_result = getObjectById(Watch.WATCH_SEARCH_NORESULT).exists();
        Asst.assertEquals("search_No_result", true, active_result);
        // 验证搜索数目
        int avatars = getObjectsById(Watch.WATCH_SEARCH_RESULT_USER_AVATAR).size();
        if (avatars!=0) {
            Asst.fail("expect search result is 0,but active result is " + avatars);
        }
    }

    //搜索真实email
    @Test
    public void test_search_email_CORRECT() throws UiObjectNotFoundException, InterruptedException {
        //进入Watch
        Nav.navToWatchSearch();
        String search_content = WatchAction.CORRECT_EMAIL;
        //输入搜索内容
        setText(Watch.WATCH_INPUT_SEARCH, search_content);
        //等待搜索完成
        waitUntilFind(Watch.WATCH_WAIT_SEARCH_LOADING, 20);
        waitUntilGone(Watch.WATCH_WAIT_SEARCH_LOADING, 60);
        //验证搜索结果
        Spoon.screenshot("search_Email_result_1");
        waitUntilFind(Watch.WATCH_SEARCH_RESULT_USER_AVATAR,10);
        //验证搜索数目
        int avatars = getObjectsById(Watch.WATCH_SEARCH_RESULT_USER_AVATAR).size();
        if (avatars == 1) {
            clickById(Watch.WATCH_SEARCH_RESULT);
            Spoon.screenshot("search_Email_result_2", search_content);
            //验证搜到的信息
            boolean active_result = getUiObjectByTextContains("@hif000").exists();
            Asst.assertEquals("search_result", true, active_result);
        } else if (avatars > 1) {
            Asst.fail("expect search result is 1,but active result is " + avatars);
        } else {
            Asst.fail("expect search result is 1,but active result is " + avatars);
        }
    }

    //搜索虚假的email
    @Test
    public void test_search_email_WRONG() throws UiObjectNotFoundException, InterruptedException {
        //进入Watch
        Nav.navToWatchSearch();
        String search_content = WatchAction.WRONG_EMAIL;
        //输入搜索内容
        setText(Watch.WATCH_INPUT_SEARCH, search_content);
        //等待搜索完成
        waitUntilFind(Watch.WATCH_WAIT_SEARCH_LOADING, 20);
        waitUntilGone(Watch.WATCH_WAIT_SEARCH_LOADING, 60);
        waitTime(5);
        //验证搜索结果
        Spoon.screenshot("search_sioEyeID_result", search_content);
        boolean active_result = getObjectById(Watch.WATCH_SEARCH_NORESULT).exists();
        Asst.assertEquals("search_No_result", true, active_result);
        //验证搜索数目
        int avatars = getObjectsById(Watch.WATCH_SEARCH_RESULT_USER_AVATAR).size();
        if (avatars!=0) {
            Asst.fail("expect search result is 0,but active result is " + avatars);
        }
    }
}
