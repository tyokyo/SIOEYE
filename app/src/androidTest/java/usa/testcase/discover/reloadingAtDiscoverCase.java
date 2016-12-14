package usa.testcase.discover;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ckt.base.VP2;
import usa.page.App;
import usa.page.Discover;

/**
 * Created by admin on 2016/11/10   .
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class reloadingAtDiscoverCase extends VP2 {
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    @Test
    public  void reloadingAtDiscover() throws UiObjectNotFoundException {
        getObjectById(Discover.ID_DISCOVER_MAIN_CONTENT).swipeDown(12);
        if (!getObjectById(Discover.ID_Reloading_gif).exists()){
            Spoon.screenshot("reloadingatdiscoverfail","在discover界面下拉刷新失败");
            Assert.fail("刷新失败");
        }

    }
}
