package testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import ckt.base.VP2;
import page.App;

/**
 * Created by elon on 2016/10/14.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestDemo extends VP2{
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME);
    }
    @Test
    public void testA() throws IOException {
        makeToast("sadfsafsa",10);
    }
}

