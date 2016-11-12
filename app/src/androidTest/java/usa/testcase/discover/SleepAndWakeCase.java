package usa.testcase.discover;

import android.os.RemoteException;
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
 * Created by admin on 2016/11/12   .
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class SleepAndWakeCase extends VP2 {
    @Before
    public void setup() {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }

    @Test
    public void SleepAndWake() throws RemoteException {
        Nav.navToBrodcasts();
        while(gDevice.isScreenOn()){
            waitTime(1);
        }
        waitTime(3);
        gDevice.wakeUp();
        if (!getObjectById(Discover.ID_DISCOVER_MAIN_CONTENT).exists()) {
            Spoon.screenshot("sleepandwakefail", "自动锁屏亮屏失败");
            Assert.fail("自动锁屏亮屏失败");
        }
    }
}
