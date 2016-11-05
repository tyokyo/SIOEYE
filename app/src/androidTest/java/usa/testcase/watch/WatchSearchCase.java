package usa.testcase.watch;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ckt.base.VP2;
import usa.action.Nav;
import usa.page.App;
import usa.page.Watch;

/**
 * Created by admin on 2016/11/5.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class WatchSearchCase extends VP2{
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    /*验证搜索功能
    有搜索图标
    2、进入搜索界面，可正确搜索
    * */
    @Test
    public  void test_search(){
        Nav.navToWatchSearch();
        clickById(Watch.WATCH_INPUT_SEARCH);


    }

}
