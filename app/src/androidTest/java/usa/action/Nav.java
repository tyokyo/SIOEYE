package usa.action;

import android.graphics.Point;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import ckt.base.VP2;
import usa.page.App;
import usa.page.Device;
import usa.page.Discover;
import usa.page.Me;
import usa.page.Watch;

/**
 * Created by elon on 2016/10/20.
 */
public class Nav extends VP2{
    public static void navToFollowing(){
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.ID_ME_FOLLOWING);
        waitTime(2);
        gDevice.wait(Until.gone(By.res(Me.LOADING_FOLLOWERS)),40000);
    }
    public static void navToBrodCasts(){
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.ID_ME_BROADCAST);
        waitTime(2);
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW)),40000);
        Spoon.screenshot("broadcasts");
    }

    public static void navToFollowers(){
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.ID_ME_FOLLOWERS);
        waitTime(2);
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW_VIDEO_LOADING)),40000);
    }
    public static void navToWatchSearch(){
        clickById(Watch.WATCH_NAVIGATOR);
        clickById(Watch.WATCH_SEARCH_BTN);
        waitTime(2);
        gDevice.wait(Until.findObject(By.res(Watch.WATCH_SEARCH_TRENDING_TITLE)),40000);
    }
    /**
     *获取点赞图标的X Y,作为键盘输入的确定按钮
     * @param
     */
    public static Point getSearchLocation() throws UiObjectNotFoundException, IOException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        Point point = new Point();
        Nav.navToBrodCasts();
        MeAction.getRandomBroadcasts().click();
        MeAction.waitBroadcastLoading();
        UiObject zan = getUiObjectById(Me.BROADCAST_VIEW_ZAN);
        int x = zan.getBounds().centerX();
        int y = zan.getBounds().centerY();
        point.set(x,y);
        makeToast(point.x+"|"+point.y,3);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        return  point;
    }
    //进入watch tab
    public static void navToWatch(){
        clickById(Watch.WATCH_NAVIGATOR);
    }
    //进入Me tab
    public static void navToMe(){
        clickById(Me.ID_MAIN_TAB_ME);
    }
    //进入device tab
    public static void navToDevice(){
        clickById(Device.ID_device_Tab);
    }
    public static void navToBrodcasts() {
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
    }
}
