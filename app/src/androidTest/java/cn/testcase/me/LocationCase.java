package cn.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import com.squareup.spoon.Spoon;
import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.MeAction;
import cn.page.App;
import usa.page.Me;

/**
 * Created by elon on 2016/10/11.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class LocationCase extends VP2{
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        AccountAction.inLogin();
    }
    //搜索位置
    @Test
    public void testSearchLocation() throws UiObjectNotFoundException {
        MeAction.navToLocation();
        if (getObjectById(Me.IS_LOCATING).exists()){
            gDevice.pressBack();
        }
        getObjectById(Me.SEARCH_LOCATE).setText("new");
        gDevice.pressSearch();
    }
    //定位位置
    @Test
    public void testLocating() throws UiObjectNotFoundException {
        MeAction.navToLocation();
        waitTime(2);
        if (getObjectById(Me.IS_LOCATING).exists()){
            gDevice.pressBack();
        }
        if (getUiObjectByText("locating").exists()){
            clickByText("locating");
            waitTime(2);
            gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),60000);
            Asst.assertTrue("locating time out in 60 seconds",!getObjectById(Me.IS_LOCATING).exists());
            Spoon.screenshot(gDevice,"locate_result");
        }
    }
}
