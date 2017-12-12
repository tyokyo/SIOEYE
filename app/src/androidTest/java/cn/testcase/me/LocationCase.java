package cn.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.MeAction;
import cn.page.App;
import cn.page.MePage;

/**
 * Created by elon on 2016/10/11.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class LocationCase extends VP2{
    private Logger logger = Logger.getLogger(LocationCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    //搜索位置
    @Test
    @SanityTest
    @PerformanceTest
    public void testSearchLocation() throws UiObjectNotFoundException {
        MeAction.navToLocation();
        waitUntilGone(MePage.IS_LOCATING,60000);
        setText(MePage.SEARCH_LOCATE,"yibin");
        clickById(MePage.LOCATION_NAME);
        waitTime(5);
        if (text_exists_contain("ALLOW")){
            clickTextContain("ALLOW");
        }
        waitUntilGone(MePage.IS_LOCATING,120000);
        waitUntilGone(By.clazz(android.widget.ProgressBar.class),120000);
        String locate_result=getTex(MePage.LOCATION_NAME);
        if ("Locating".equals(locate_result)){
            Asst.fail("can not locate");
        }
        Spoon.screenshot("locate_result");
    }
    //定位位置
    @Test
    @SanityTest
    @PerformanceTest
    public void testLocating() throws UiObjectNotFoundException {
        MeAction.navToLocation();
        waitUntilGone(MePage.IS_LOCATING,60000);
        if (getUiObjectByText("Locating").exists()){
            clickByText("Locating");
            waitTime(2);
            waitUntilGone(MePage.IS_LOCATING,120000);
            Asst.assertTrue("locating time out in 120 seconds",!getObjectById(MePage.IS_LOCATING).exists());
            Spoon.screenshot("locate_result");
        }
        String locate_result=getTex(MePage.LOCATION_NAME);
        if ("Locating".equals(locate_result)){
            Asst.fail("can not locate");
        }
    }
}
