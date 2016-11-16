package usa.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.spoon.Spoon;
import org.hamcrest.Asst;
import org.junit.Assert;
import java.util.List;
import ckt.base.VP2;
import usa.page.Discover;
import usa.page.Me;

/**
 * Created by caibing.yin on 2016/11/5.
 */

public class DiscoverAction extends VP2 {

        public static void clickSearchBtn(){
            gDevice.findObject(By.res("com.sioeye.sioeyeapp:id/title")).getParent().findObject(By.clazz(ImageView.class)).click();
        }
        public static void navToSearch(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
            waitTime(3);
            clickSearchBtn();
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
        public static void scrollRecommandList() throws UiObjectNotFoundException {
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
            getObjectById(Discover.ID_MAIN_TAB_RECOMMAND_LIST).swipeLeft(2);
            //向左滑动2步
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
        public static void checkMiniProfileNumFollowerAddOneAfterFollow() throws UiObjectNotFoundException{
            int NumFollower=Integer.parseInt(getTex(Discover.ID_MAIN_TAB_PROFILE_MINI_NUM_FOLLOWER));
            int expect_NumFollower=NumFollower+1;
            //该目标用户的Follower的数量，+1表示点击关注后该用户的Follower实际数量
            clickById(Discover.ID_MAIN_TAB_PROFILE_MINI_NUM_FOLLOW);
            //关注操作
            waitTime(3);
            int active_NumFollower=Integer.parseInt(getTex(Discover.ID_MAIN_TAB_PROFILE_MINI_NUM_FOLLOWER));
            //关注后该目标用户的Follower的数量，
            Spoon.screenshot("testAddFriendsRecommend0","该用户followers没有加1");
            Asst.assertEquals("添加推荐用户为好友后，该用户followers没有加1",expect_NumFollower,active_NumFollower);
            //断言该用户followers有没有+1
            clickByClass("android.widget.ImageView",2);
            //关闭弹出框
        }
         //得到观看人数
        public static String getPersonNumber() throws UiObjectNotFoundException {
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
            waitTime(10);
            List<UiObject2> textViews=getObject2ById(Discover.ID_SWIPE_TARGET).findObjects(By.clazz(TextView.class));
            return textViews.get(9).getText();
        }
        //得到点赞人数
        public static String getZanNumber() throws UiObjectNotFoundException {
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
            waitTime(10);
            List<UiObject2> textViews=getObject2ById(Discover.ID_SWIPE_TARGET).findObjects(By.clazz(TextView.class));
            return textViews.get(10).getText();
        }
        public static void deleteNewFollowing (String target_nick_name) throws UiObjectNotFoundException {
            clickById(Discover.ID_MAIN_TAB_ME);
            clickById(Me.ID_ME_FOLLOWING);
            waitUntilFind(Me.FOLLOWERING_VIEW,6000);
            UiObject targetObj=scrollAndGetUIObject(target_nick_name);
            clickByText(target_nick_name);
            clickById(Me.FOLLOWERING_DELETE);
            Spoon.screenshot("deleteNewFollowing","删除新加好友");
        }
        //点击Discover界面的视频观看
        public static void navtoVideo(){
            clickById(Discover.ID_MAIN_TAB_DISCOVER);
            waitTime(2);
            UiObject2 u1=gDevice.findObject(By.res("com.sioeye.sioeyeapp:id/swipe_target"));
            List<UiObject2> views=u1.findObjects(By.clazz(android.view.View.class));
            UiObject2 u3=views.get(5);
            u3.click();
            waitTime(30);
        }
}

