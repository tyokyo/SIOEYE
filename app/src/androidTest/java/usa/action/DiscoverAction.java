package usa.action;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.runner.RunWith;
import java.util.logging.Logger;
import ckt.base.VP2;
import usa.page.App;
import usa.page.Discover;
import usa.testcase.me.ActivityCase;
/**
 * Created by admin on 2016/11/5.
 */

public class DiscoverAction extends VP2 {
    /**
     * Created by caibing,yin on 2016/11/5.
     */
    @RunWith(AndroidJUnit4.class)
    @SdkSuppress(minSdkVersion = 18)
    public static class ToDiscoverCase extends VP2 {
        Logger logger = Logger.getLogger(ActivityCase.class.getName());
        @Before
        public  void setup(){
            openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        }
        public void navToDiscover(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
            waitTime(1);
        }
        public void navToSearch(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
        public void navToAd(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
        public void navToRecommendList(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
        public void scrollAdSplash(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
        public void scrollRecommendList(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
    }
}
