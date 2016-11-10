package usa.testcase.discover;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.spoon.Spoon;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ckt.base.VP2;
import usa.action.Nav;
import usa.page.App;
import usa.page.Discover;

/**
 * Created by admin on 2016/11/10   .
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class SwitchTab extends VP2 {
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    @Test
    public void SwitchTab(){
        Nav.navToDevice();
        Nav.navToBrodcasts();
        Nav.navToMe();
        Nav.navToWatch();
        Nav.navToDevice();
        Nav.navToMe();
        Nav.navToBrodcasts();
        if (!getObjectById(Discover.ID_MAIN_TAB_AD_SPALSH).exists()){
            Spoon.screenshot("switchtabfail","切换tab失败");
            Assert.fail("切换tab失败");
        }
    }
}
