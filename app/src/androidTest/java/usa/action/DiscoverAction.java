package usa.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.spoon.Spoon;
import java.util.List;
import ckt.base.VP2;
import usa.page.Discover;
/**
 * Created by caibing.yin on 2016/11/5.
 */

public class DiscoverAction extends VP2 {

        public static void navToSearch(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);

        }
        public static void navToAd(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
            clickById(Discover.ID_MAIN_TAB_AD_SPALSH);

        }
        //index=0-3
        public static String navToRecommendList(int index,int click_time){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
            waitUntilFind(Discover.ID_MAIN_TAB_RECOMMAND_LIST,30000);
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

        //取得Discover页面中RecommendList中头像对应的的昵称
        public static String getnickname() throws UiObjectNotFoundException {
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
            UiObject Recommand_list = getObjectById(Discover.ID_MAIN_TAB_RECOMMAND_LIST);
            String nickname = Recommand_list.getChild(new UiSelector().index(0)).getText();
            return nickname;
    }
        public static void scrollAdSplash(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
        public static void scrollRecommendList(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
        }
}

