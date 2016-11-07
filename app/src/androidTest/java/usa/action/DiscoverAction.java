package usa.action;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.spoon.Spoon;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import ckt.base.VP2;
import cn.page.Me;
import cn.page.Watch;
import usa.page.App;
import usa.page.Discover;
import usa.testcase.me.ActivityCase;
/**
 * Created by admin on 2016/11/5.
 */

public class DiscoverAction extends VP2 {

        public void navToSearch(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
        public void navToAd(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
            clickById(Discover.ID_MAIN_TAB_AD_SPALSH);

        }
        //index=0-3
        public static String navToRecommendList(int index,int click_time){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
            //into discover
            List<UiObject2> linearLayout_avatars =getObject2ById(Discover.ID_MAIN_TAB_RECOMMAND_LIST).findObjects(By.clazz(LinearLayout.class));
            UiObject2 linearLayout = linearLayout_avatars.get(index);
            //get RecommendList
            String trend_name=linearLayout.findObject(By.clazz(TextView.class)).getText();
            //get RecommendList name
            for (int i = 0;i<click_time;i++) {
                linearLayout.click();
            }
            waitUntilFind(Discover.ID_MAIN_TAB_PROFILE_MINI_HOME,30);
            Spoon.screenshot("navToRecommendList");
            return trend_name;
        }
        public void scrollAdSplash(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
        public void scrollRecommendList(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
}

