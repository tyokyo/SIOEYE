package cn.testcase.other;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.runner.RunWith;
import ckt.base.VP2;
import cn.page.App;

/**
 * Created by elon on 2016/11/4.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class AccountCase extends VP2{
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
    }

}
