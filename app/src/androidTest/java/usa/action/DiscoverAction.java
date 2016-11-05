package usa.action;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.spoon.Spoon;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.logging.Logger;
import ckt.base.VP2;
import usa.page.App;
import usa.page.Discover;
import usa.testcase.me.ActivityCase;
/**
 * Created by admin on 2016/11/5.
 */

public class DiscoverAction extends VP2 {
    Logger logger = Logger.getLogger(ActivityCase.class.getName());
    public void navToDiscover(){
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        waitTime(1);
        logger.info("navToDiscover");
    }
    //
    public static void navToSearch(){
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        click_search_btn();
    }
    //
    private static void click_search_btn(){
        List<UiObject2> frameLayout_lists =gDevice.findObjects(By.clazz(FrameLayout.class));
        for (UiObject2 frameLayout:frameLayout_lists) {
            boolean sio_eye = frameLayout.hasObject(By.res("com.sioeye.sioeyeapp:id/title"));
            if (sio_eye){
                UiObject2 search_btn=frameLayout.findObject(By.clazz(ImageView.class));
                search_btn.click();
                break;
            }
        }
    }
    public void navToAd() throws UiObjectNotFoundException {
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        scrollAndGetUIObject("WHO TO FOLLOW");
        clickById(Discover.ID_MAIN_TAB_AD_SPALSH);
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
    public boolean isExistedDiscover() throws UiObjectNotFoundException {
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        Boolean Results = scrollAndGetUIObject("WHO TO FOLLOW").getText().equals("WHO TO FOLLOW");
        if (Results) {
            Spoon.screenshot("Discover", "Click DiscoverCase");
            Log.d("passed", "click Discover was passed");
            return true;
        } else {
            Log.d("failed", "click 'Discover' to Discover failed!");
            return false;
        }
    }
}
