package usa.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.spoon.Spoon;
import org.junit.Assert;
import java.util.List;
import ckt.base.VP2;
import usa.page.Discover;
import usa.page.Me;

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
        public static void checkAddFriendsInMyFollowing(String target_nick_name) throws UiObjectNotFoundException {
            clickById(Discover.ID_MAIN_TAB_ME);
            clickById(Me.ID_ME_FOLLOWING);
            waitUntilFind(Me.FOLLOWERING_VIEW,6000);
            UiObject expectObj=scrollAndGetUIObject(target_nick_name);
            if (expectObj!=null){
                if (!expectObj.exists()){
                    Spoon.screenshot("swip_to_find",target_nick_name+"Failed");
                    Assert.fail("AddFriendsRecommand"+target_nick_name+"Failed");
                }
            }else{
                Assert.fail("出现异常nick name获取为空");
            }
        }
        //得到UIO对象里面的数字
        private static int getPersonNumber(UiObject UIO) throws UiObjectNotFoundException {
            String CountPerson = UIO.getChild(new UiSelector().index(0)).getText();
            int PersonNumber = Integer.getInteger(CountPerson);
            return  PersonNumber;
        }
}

