package usa.page;
import android.graphics.Point;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import com.squareup.spoon.Spoon;
import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import ckt.base.VP2;
import usa.action.MeAction;
import usa.action.Nav;
import usa.page.App;
import usa.page.Me;

/**
 * Created by caibing.yin on 2016/11/05.
 */
public class Discover {
    //search
    public static final String ID_SEARCH_FILTER_INPUT = "com.sioeye.sioeyeapp:id/search_filter_input";
    //Activity of Discover
    public static final String ID_DISCOVER_MAIN_CONTENT="com.sioeye.sioeyeapp:id/discover_swipe_view";

    //navigate to  Discover
    public static final String ID_MAIN_TAB_DISCOVER = "com.sioeye.sioeyeapp:id/main_tab_discover";
    //navigate to ad_splash
    public static final String ID_MAIN_TAB_AD_SPALSH = "com.sioeye.sioeyeapp:id/discover_ad_splash_pager";
    //recommend_list
    public static final String ID_MAIN_TAB_RECOMMAND_LIST = "com.sioeye.sioeyeapp:id/recommend_list";
    public static final String ID_MAIN_TAB_PROFILE_MINI_HOME ="com.sioeye.sioeyeapp:id/profile_mini_home";
    public static final String ID_MAIN_TAB_PROFILE_MINI_NAME ="com.sioeye.sioeyeapp:id/profile_mini_name";
    public static final String ID_MAIN_TAB_PROFILE_MINI_NUM_FOLLOWER ="com.sioeye.sioeyeapp:id/profile_mini_num_follower";
    public static final String ID_MAIN_TAB_PROFILE_MINI_NUM_FOLLOW ="com.sioeye.sioeyeapp:id/profile_mini_follow";
    //navigate to  Device
    public static final String ID_MAIN_TAB_DECICE = "com.sioeye.sioeyeapp:id/main_tab_device";
    //navigate to  Watch
    public static final String ID_MAIN_TAB_WATCH="com.sioeye.sioeyeapp:id/main_tab_live ";
    public static final String ID_WATCHER_COUNT = "com.sioeye.sioeyeapp:id/watch_player_portrait_watcher_count";
    public static final String ID_MAIN_TAB_ME = "com.sioeye.sioeyeapp:id/table_me";
    //下拉刷新小人
    public static final String ID_Reloading_gif ="com.sioeye.sioeyeapp:id/status_image";
}
