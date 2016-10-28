package testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.runner.RunWith;

import ckt.base.VP2;
import page.App;

/**
 * Created by elon on 2016/10/28.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class LogOutCase extends VP2 {
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    public void testLogOut_Cancel(){

    }
    public void testLogOut_OK(){
        //log out

        //log in
    }
}
