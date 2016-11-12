package usa.testcase.discover;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.v4.provider.DocumentFile;

import com.squareup.spoon.Spoon;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ckt.base.VP2;
import usa.page.App;
import usa.page.Discover;

import static ckt.base.VP2.openAppByPackageName;

/**
 * Created by admin on 2016/11/12   .
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class SwipeUpAtDiscoverCase extends VP2{
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    @Test
    //上滑推荐视频
    public void swipeUpAtDiscover() throws UiObjectNotFoundException {
        getObjectById(Discover.ID_DISCOVER_MAIN_CONTENT).swipeUp(12);
        waitTime(1);
        getObjectById(Discover.ID_DISCOVER_MAIN_CONTENT).swipeUp(12);
        waitTime(1);
        if (getObjectById(Discover.ID_MAIN_TAB_RECOMMAND_LIST).exists()){
            Spoon.screenshot("Swipeupatdiscoverfail","在discover界面下滑失败");
            Assert.fail("在discover界面下滑失败");
        }
        if(!getObjectById(Discover.ID_DISCOVER_MAIN_CONTENT).exists()){
            Spoon.screenshot("Swipeupatdiscoverfail","在discover界面下滑失败");
            Assert.fail("在discover界面下滑失败");
        }
    }
}
