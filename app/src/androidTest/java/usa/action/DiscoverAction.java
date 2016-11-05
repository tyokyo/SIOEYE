package usa.action;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import usa.page.App;
import usa.testcase.me.ActivityCase;

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
            clickById(com.sioeye.sioeyeapp:id/main_tab_discover);
            waitTime(1);


        }
        public void NavtoSearch(){
            clickById(com.sioeye.sioeyeapp:id/main_tab_discover);
        }

        public void NavtoSearch(){
            clickById(com.sioeye.sioeyeapp:id/main_tab_discover);
        }
        public void Navtoad_splash(){
            clickById(com.sioeye.sioeyeapp:id/main_tab_discover);
        }
        public void Navtorecommend_list(){
            clickById(com.sioeye.sioeyeapp:id/main_tab_discover);
        }
        public void Scrollad_splash(){
            clickById(com.sioeye.sioeyeapp:id/main_tab_discover);
        }
        public void Scrollrecommend_list(){
            clickById(com.sioeye.sioeyeapp:id/main_tab_discover);
        }
        public void ClickVideo(){
            clickById(com.sioeye.sioeyeapp:id/main_tab_discover);
        }


    }
}
