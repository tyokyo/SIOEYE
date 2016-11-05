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
    //navigate to  Discover
    public static final String ID_MAIN_TAB_DISCOVER = "com.sioeye.sioeyeapp:id/main_tab_discover";

    //navigate to ad_splash
    public static final String ID_MAIN_TAB_AD_SPALSH = "com.sioeye.sioeyeapp:id/discover_ad_splash_pager";
    //recommend_list
    public static final String ID_MAIN_TAB_RECOMMAND_LIST = "com.sioeye.sioeyeapp:id/recommend_list";


}
