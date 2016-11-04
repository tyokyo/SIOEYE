package usa.action;

import android.graphics.Point;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import android.widget.TextView;

import com.squareup.spoon.Spoon;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import bean.FollowingBean;
import bean.UserBean;
import ckt.base.VP2;
import page.App;
import page.Me;
import page.Watch;

/**
 * Created by elon on 2016/10/27.
 */
public class FollowingAction extends VP2{
    public static Logger logger =Logger.getLogger("FollowingAction");
    public static void add_sioEyeMedia() throws UiObjectNotFoundException, IOException {
        boolean follow = isUserHasFollowing("SioeyeMedia");
        if (!follow){
            openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        }
        searchFollowingUser("med000");
        clickById(Watch.WATCH_SEARCH_USER_FOLLOW);
        waitTime(3);
        gDevice.pressBack();
    }
    public static void user_add_from_following_following() throws UiObjectNotFoundException, IOException {
        Nav.navToFollowing();
        add_sioEyeMedia();
        Nav.navToFollowing();
        scrollAndGetUIObject("SioeyeMedia").clickAndWaitForNewWindow();
    }

    public static UserBean getUserInfo() throws UiObjectNotFoundException {
        UserBean userBean = new UserBean();
        String name = getUiObjectById(Me.FOLLOWERING_USERNAME).getText();
        String id = getUiObjectById(Me.SIOEYE_USER_ID).getText();
        String zan = getUiObjectById(Me.SIOEYE_USER_LIKE_COUNT).getText();
        String following = getUiObjectById(Me.USER_FOLLOWING).getText();
        String followers = getUiObjectById(Me.USER_FOLLOWERS).getText();
        String broadcasts = getUiObjectById(Me.USER_BROADCASTS).getText();
        userBean.setName(name);
        userBean.setId(id);
        userBean.setZan(zan);
        userBean.setFollowing(following);
        userBean.setFollowers(followers);
        userBean.setBroadcasts(broadcasts);
        return  userBean;
    }
    //随机选择一个Following 对象
    public static FollowingBean randomFollowingUser() throws IOException, UiObjectNotFoundException {
        FollowingBean followingBean = new FollowingBean();
        List<UiObject2> list = gDevice.findObjects(By.res(Me.FOLLOWERING_AVATAR));
        int avatar =list.size();
        logger.info("Following size is :"+avatar);
        Random r = new Random();
        int int_r =r.nextInt(avatar);
        UiObject2 r_UiObject2 =list.get(int_r);
        UiObject2 p_UiObject2 = r_UiObject2.getParent();
        List<UiObject2> list_text=p_UiObject2.findObjects(By.clazz(TextView.class));
        String name = list_text.get(0).getText();
        String followers=list_text.get(1).getText();
        String videos=list_text.get(2).getText();
        followingBean.setAvatar(r_UiObject2);
        followingBean.setName(name);
        followingBean.setFollowers(followers);
        followingBean.setVideos(videos);
        followingBean.setIndex_linearLayout(int_r);
        return  followingBean;
    }
    public static boolean isUserHasFollowing(String f_name) throws UiObjectNotFoundException {
        boolean af = false;
        UiObject find = scrollAndGetUIObject(f_name);
        if (find!=null){
            af =scrollAndGetUIObject(f_name).exists();
        }
        return  af;
    }
    /**
     *搜索指定sioeye id 的用户
     * @param sioEyeId
     */
    public static void searchFollowingUser(String sioEyeId) throws IOException, UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        Point p = Nav.getSearchLocation();
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);

        clickByText("Watch");
        clickById(Watch.WATCH_SEARCH_BTN);
        gDevice.wait(Until.findObject(By.res(Watch.WATCH_SEARCH_TRENDING_TITLE)),40000);
        if (!getUiObjectById(Watch.WATCH_SEARCH_TRENDING_LIST).exists()){
            clickById(Watch.WATCH_SEARCH_FILTER_INPUT);
            waitTime(2);
            gDevice.click(p.x,p.y);
            gDevice.wait(Until.findObject(By.res(Watch.WATCH_SEARCH_TRENDING_TITLE)),40000);
        }
        Spoon.screenshot(gDevice,"trending_list");
        clickById(Watch.WATCH_SEARCH_FILTER_INPUT);
        shellInputText(sioEyeId);
        gDevice.click(p.x,p.y);
        gDevice.wait(Until.gone(By.res(Watch.WATCH_SEARCH_DATA_LOADING)),60000);
        Spoon.screenshot(gDevice,"search_list_"+sioEyeId);
    }
}
