package usa.testcase.discover;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import ckt.base.VP2;
import usa.action.DiscoverAction;
import usa.page.App;
import usa.page.Discover;

/**
 * Created by caibing.yin on 2016/11/5.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class DiscoverCase extends VP2 {
    @Before
    public  void setup(){
        openAppByPackageNameInLogin(App.SIOEYE_PACKAGE_NAME_USA);
    }
    @Test
   /**
    * 1、单击搜索图标
    * */
    public void testOneClickSearch(){
        DiscoverAction.navToSearch();
        waitTime(1);
    }
    @Test
    /**
     * 2、双击搜索图标
     */
    public void testDoubleClickSearch(){
        DiscoverAction.navToSearch();
        waitTime(1);
    }
    @Test
    /**
     *3、下拉刷新、在discover界面手指从上往下滑动
     */
    public void testFlush() throws UiObjectNotFoundException, IOException {
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        waitTime(2);
        // UiObject Discover
        getObjectById(Discover.ID_DISCOVER_MAIN_CONTENT).swipeDown(50);
        waitTime(2);
    }

}
