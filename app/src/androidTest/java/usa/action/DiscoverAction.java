package usa.action;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import usa.page.App;
import usa.testcase.me.ActivityCase;
import  usa.page.Discover;
/**
 * Created by admin on 2016/11/5.
 */

public class DiscoverAction extends VP2 {
    public static void navToDiscover(){

    }

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

        public void NavtoDiscover(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
            waitTime(1);


        }
        public void NavtoSearch(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }

        public void NavtoSearch(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
        public void Navtoad_splash(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
        public void Navtorecommend_list(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
        public void Scrollad_splash(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
        public void Scrollrecommend_list(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
        public void ClickVideo(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);

        }


    }
}
