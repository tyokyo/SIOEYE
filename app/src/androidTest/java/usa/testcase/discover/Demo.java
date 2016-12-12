package usa.testcase.discover;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import usa.page.App;
import usa.page.Discover;
import usa.testcase.me.ActivityCase;

/**
 * Created by admin on 2016/11/12.
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Demo extends VP2 {
    Logger logger = Logger.getLogger(ActivityCase.class.getName());
    @Before
    public void setup() {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    /**
     * case10:
     *历史观看人数统计
     *1.反复进入退出视频播放界面后回到封面检查历史观看人数
     *  Result：每次进入播放视频界面后退出视频封面界面，历史观看人数加1
     * */

    @Test
    public void testCountPerson() throws UiObjectNotFoundException {
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        waitTime(2);
/////////
    }


}
